package com.arlania.world.content.combat;

import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.container.impl.Equipment;
import com.arlania.world.content.Sounds;
import com.arlania.world.content.combat.range.CombatRangedAmmo.RangedWeaponData;
import com.arlania.world.content.combat.strategy.impl.DefaultRangedCombatStrategy;
import com.arlania.world.content.combat.weapon.CombatSpecial;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.Player;

/**
 * A {@link Task} implementation that handles every combat 'hook' or 'turn'
 * during a combat session.
 *
 * @author lare96
 */
public class CombatHookTask extends Task {

    /**
     * The builder assigned to this task.
     */
    private final CombatBuilder builder;

    /**
     * Create a new {@link CombatHookTask}.
     *
     * @param builder the builder assigned to this task.
     */
    public CombatHookTask(CombatBuilder builder) {
        super(1, builder, false);
        this.builder = builder;
    }

    @Override
    public void execute() {

        if (builder.isCooldown()) {
            builder.cooldown--;
            builder.attackTimer--;

            if (builder.cooldown == 0) {
                builder.reset(true);
            }
            return;
        }

        if (!CombatFactory.checkHook(builder.getCharacter(), builder.getVictim())) {
            return;
        }

        // If the entity is an player we redetermine the combat strategy before
        // attacking.
        if (builder.getCharacter().isPlayer()) {
            builder.determineStrategy();
        }

        // Decrement the attack timer.
        builder.attackTimer--;

        // The attack timer is below 1, we can attack.
        if (builder.attackTimer < 1) {
            // Check if the attacker is close enough to attack.
            if (!CombatFactory.checkAttackDistance(builder)) {
                if (builder.getCharacter().isNpc() && builder.getVictim().isPlayer()) {
                    if (builder.getLastAttack().elapsed(4500)) {
                        ((NPC) builder.getCharacter()).setFindNewTarget(true);
                    }
                }
                return;
            }

            // Check if the attack can be made on this hook
            if (!builder.getStrategy().canAttack(builder.getCharacter(), builder.getVictim())) {
                builder.getCharacter().getCombatBuilder().reset(builder.getCharacter().isNpc());
                return;
            }

            // Do all combat calculations here, we create the combat containers
            // using the attacking entity's combat strategy.

            boolean customContainer = builder.getStrategy().customContainerAttack(builder.getCharacter(),
                    builder.getVictim());
            CombatContainer container = builder.getContainer();

            builder.getCharacter().setEntityInteraction(builder.getVictim());

            if (builder.getCharacter().isPlayer()) {
                Player player = (Player) builder.getCharacter();
                //player.getPacketSender().sendInterfaceRemoval();

                if (player.isSpecialActivated() && player.getCastSpell() == null) {
                    container = player.getCombatSpecial().container(player, builder.getVictim());
                    boolean magicShortbowSpec = player.getCombatSpecial() != null
                            && player.getCombatSpecial() == CombatSpecial.MAGIC_SHORTBOW;
                    CombatSpecial.drain(player, player.getCombatSpecial().getDrainAmount());

                    Sounds.sendSound(player,
                            Sounds.specialSounds(player.getEquipment().get(Equipment.WEAPON_SLOT).getId()));

                    if (player.getCombatSpecial().getCombatType() == CombatType.RANGED) {
                        if (player.getEquipment().get(Equipment.WEAPON_SLOT).getId() == 12926) {
                            if (player.getBlowpipeLoading().getContents().isEmpty()) {
                                return;
                            }
                        }
                        DefaultRangedCombatStrategy.decrementAmmo(player, builder.getVictim().getPosition());
                        if (CombatFactory.darkBow(player)
                                || player.getRangedWeaponData() == RangedWeaponData.MAGIC_SHORTBOW
                                && magicShortbowSpec) {
                            DefaultRangedCombatStrategy.decrementAmmo(player, builder.getVictim().getPosition());
                        }
                    }
                }
            }

            // If there is no hit type the combat turn is ignored.
            if (container != null && container.getCombatType() != null) {
                // If we have hit splats to deal, we filter them through combat
                // prayer effects now. If not then we still send the hit tasks
                // next to handle any effects.

                // An attack is going to be made for sure, set the last attacker
                // for this victim.
                builder.getVictim().getCombatBuilder().setLastAttacker(builder.getCharacter());
                builder.getVictim().getLastCombat().reset();

                // Start cooldown if we're using magic and not autocasting.
                if (container.getCombatType() == CombatType.MAGIC && builder.getCharacter().isPlayer()) {
                    Player player = (Player) builder.getCharacter();

                    if (!player.isAutocast()) {
                        if (!player.isSpecialActivated())
                            player.getCombatBuilder().cooldown = 10;
                        player.setCastSpell(null);
                        player.getMovementQueue().setFollowCharacter(null);
                        builder.determineStrategy();
                    }
                }
                if (container.getHitDelay() == 0) { // An instant attack
                    new CombatHitTask(builder, container).handleAttack();
                } else {
                    TaskManager.submit(new CombatHitTask(builder, container, container.getHitDelay(), false));
                }

                builder.setContainer(null); // Fetch a brand new container on
                // next attack
            }

            // Reset the attacking entity.
            builder.attackTimer = builder.getStrategy() != null
                    ? builder.getStrategy().attackDelay(builder.getCharacter())
                    : builder.getCharacter().getAttackSpeed();
            builder.getLastAttack().reset();
            builder.getCharacter().setEntityInteraction(builder.getVictim());
        }
    }
}
