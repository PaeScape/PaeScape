package com.arlania.world.content.combat;

import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.*;
import com.arlania.model.Locations.Location;
import com.arlania.model.container.impl.Equipment;
import com.arlania.model.definitions.WeaponAnimations;
import com.arlania.util.RandomUtility;
import com.arlania.world.World;
import com.arlania.world.content.Sounds;
import com.arlania.world.content.achievements.AchievementData;
import com.arlania.world.content.combat.CombatContainer.CombatHit;
import com.arlania.world.content.globalevents.GlobalEventHandler;
import com.arlania.world.content.globalevents.GlobalEvent;
import com.arlania.world.entity.impl.Character;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.npc.NPCMovementCoordinator.CoordinateState;
import com.arlania.world.entity.impl.player.Player;

import java.util.Iterator;
import java.util.List;

/**
 * A {@link Task} implementation that deals a series of hits to an entity after
 * a delay.
 *
 * @author lare96
 */
public class CombatHitTask extends Task {

    /**
     * The attacker instance.
     */
    private final Character attacker;

    /**
     * The victim instance.
     */
    private final Character victim;

    /**
     * The attacker's combat builder attached to this task.
     */
    private final CombatBuilder builder;

    /**
     * The attacker's combat container that will be used.
     */
    private final CombatContainer container;

    /**
     * The total damage dealt during this hit.
     */
    private int damage;

    private int initialDelay;
    private int delay;

    /**
     * Create a new {@link CombatHit}.
     *
     * @param builder    the combat builder attached to this task.
     * @param container  the combat hit that will be used.
     * @param delay      the delay in ticks before the hit will be dealt.
     * @param initialRun if the task should be ran right away.
     */
    public CombatHitTask(CombatBuilder builder, CombatContainer container,
                         int delay, boolean initialRun) {
        super(delay, builder.getCharacter(), initialRun);
        this.builder = builder;
        this.container = container;
        this.attacker = builder.getCharacter();
        this.victim = builder.getVictim();
    }

    public CombatHitTask(CombatBuilder builder, CombatContainer container) { //Instant attack, no task
        this.builder = builder;
        this.container = container;
        this.attacker = builder.getCharacter();
        this.victim = builder.getVictim();
    }

    @Override
    public void execute() {

        handleAttack();

        this.stop();
    }

    public void handleEntityInterface(Character attacker, Character victim, int damage) {
        if (attacker.isPlayer()) {
            Player p = (Player) attacker;

            if (victim.isPlayer()) {//plrs
                Player v = (Player) victim;
                int maximumHealth = v.getSkillManager().getMaxLevel(Skill.CONSTITUTION);
                int currentHealth = v.getSkillManager().getCurrentLevel(Skill.CONSTITUTION);
                String entityName = v.getUsername();
                p.getPacketSender().sendEntityInterface(victim.isPlayer() ? 1 : 0, maximumHealth, currentHealth, entityName);
            } else if (victim.isNpc()) {//npcs
                NPC v = (NPC) victim;
                int maximumHealth = v.getDefaultConstitution();
                int currentHealth = v.getConstitution();
                String entityName = v.getDefinition().getName();
                p.getPacketSender().sendEntityInterface(victim.isPlayer() ? 1 : 0, maximumHealth, currentHealth, entityName);
            }
        }
    }

    public void handleAttack() {
        if (attacker.getConstitution() <= 0 || !attacker.isRegistered()) {
            return;
        }

        // Do any hit modifications to the container here first.
        if (container.getModifiedDamage() > 0) {
            container.allHits(context -> {
                context.getHit().setDamage(container.getModifiedDamage());
                context.setAccurate(true);
            });
        }

        // Now we send the hitsplats if needed! We can't send the hitsplats
        // there are none to send, or if we're using magic and it splashed.
        if (container.getHits().length != 0 && container.getCombatType() != CombatType.MAGIC || container.isAccurate()) {

            /**
             * PRAYERS *
             */
            CombatFactory.applyPrayerProtection(container, builder);

            this.damage = container.getDamage();
            victim.getCombatBuilder().addDamage(attacker, damage);
            container.dealDamage();

            handleEntityInterface(attacker, victim, damage);

            /**
             * DAMAGE ABSORB *
             */
            CombatFactory.applyDamageAbsorb(container, builder);

            /**
             * NPC Perks *
             */
            if(attacker.isNpc())
                CombatFactory.handleNPCperks(attacker, victim, damage);

            /**
             * MISC *
             */
            if (attacker.isPlayer()) {
                Player p = (Player) attacker;
                if (damage > 0) {

                    /**
                     * ACHIEVEMENTS *
                     */
                    if (container.getCombatType() == CombatType.MELEE) {
                        p.getAchievementTracker().progress(AchievementData.DEAL_1000_MELEE_DMG, damage);
                        p.getAchievementTracker().progress(AchievementData.DEAL_100K_MELEE_DMG, damage);
                        p.getAchievementTracker().progress(AchievementData.DEAL_1M_MELEE_DMG, damage);
                        p.getAchievementTracker().progress(AchievementData.DEAL_10M_MELEE_DMG, damage);

                        if (damage > 60) {
                        }

                    } else if (container.getCombatType() == CombatType.RANGED) {
                        p.getAchievementTracker().progress(AchievementData.DEAL_1000_RANGED_DMG, damage);
                        p.getAchievementTracker().progress(AchievementData.DEAL_100K_RANGED_DMG, damage);
                        p.getAchievementTracker().progress(AchievementData.DEAL_1M_RANGED_DMG, damage);
                        p.getAchievementTracker().progress(AchievementData.DEAL_10M_RANGED_DMG, damage);
                    } else if (container.getCombatType() == CombatType.MAGIC) {
                        p.getAchievementTracker().progress(AchievementData.DEAL_1000_MAGIC_DMG, damage);
                        p.getAchievementTracker().progress(AchievementData.DEAL_100K_MAGIC_DMG, damage);
                        p.getAchievementTracker().progress(AchievementData.DEAL_1M_MAGIC_DMG, damage);
                        p.getAchievementTracker().progress(AchievementData.DEAL_10M_MAGIC_DMG, damage);
                    }


                }
            } else {
                if (victim.isPlayer() && container.getCombatType() == CombatType.DRAGON_FIRE) {
                    Player p = (Player) victim;
                    if (RandomUtility.inclusiveRandom(20) <= 15 && p.getEquipment().getItems()[Equipment.SHIELD_SLOT].getId() == 11283 || p.getEquipment().getItems()[Equipment.SHIELD_SLOT].getId() == 11613
                            || p.getEquipment().getItems()[Equipment.SHIELD_SLOT].getId() == 1540 || p.getEquipment().getItems()[Equipment.SHIELD_SLOT].getId() == 20564) {
                        p.setPositionToFace(attacker.getPosition().copy());
                        CombatFactory.chargeDragonFireShield(p);
                    }
                    if (damage >= 160) {
                        ((Player) victim).getPacketSender().sendMessage("You are badly burnt by the dragon's fire!");
                    }
                }
            }

        }


        // Give experience based on the hits.
        CombatFactory.giveExperience(builder, container, damage);

        if (!container.isAccurate()) {
            if (container.getCombatType() == CombatType.MAGIC && attacker.getCurrentlyCasting() != null) {
                victim.performGraphic(new Graphic(85, GraphicHeight.MIDDLE));
                attacker.getCurrentlyCasting().finishCast(attacker, victim, false, 0);
                attacker.setCurrentlyCasting(null);
            } else if (container.getCombatType() == CombatType.RANGED) {
                applyAOE(attacker, victim, false, 0, CombatType.RANGED);
            } else if (container.getCombatType() == CombatType.MELEE) {
                applyAOE(attacker, victim, false, 0, CombatType.MELEE);
            }
        } else if (container.isAccurate()) {

            CombatFactory.handleArmorEffects(attacker, victim, damage, container.getCombatType());
            CombatFactory.handlePrayerEffects(attacker, victim, damage, container.getCombatType());
            CombatFactory.handleSpellEffects(attacker, victim, damage, container.getCombatType());

            attacker.poisonVictim(victim, container.getCombatType());

            // Finish the magic spell with the correct end graphic.
            if (container.getCombatType() == CombatType.MAGIC && attacker.getCurrentlyCasting() != null) {
                attacker.getCurrentlyCasting().endGraphic().ifPresent(victim::performGraphic);
                attacker.getCurrentlyCasting().finishCast(attacker, victim, true, damage); //handles AoE
                attacker.setCurrentlyCasting(null);
            }else if (container.getCombatType() == CombatType.RANGED) {
                applyAOE(attacker, victim, true, damage, CombatType.RANGED);
            } else if (container.getCombatType() == CombatType.MELEE) {
                applyAOE(attacker, victim, true, damage, CombatType.MELEE);
            }
        }

        // Send the defensive animations.
        if (victim.getCombatBuilder().getAttackTimer() <= 2) {
            if (victim.isPlayer()) {
                victim.performAnimation(new Animation(WeaponAnimations.getBlockAnimation(((Player) victim))));
                //if (((Player) victim).getInterfaceId() > 0) {
                //((Player) victim).getPacketSender().sendInterfaceRemoval();
                //}
            } else if (victim.isNpc()) {
                if (!(((NPC) victim).getId() >= 6142 && ((NPC) victim).getId() <= 6145)) {
                    victim.performAnimation(new Animation(((NPC) victim).getDefinition().getDefenceAnimation()));
                }
            }
        }

        // Fire the container's dynamic hit method.
        container.onHit(damage, container.isAccurate());

        // And finally auto-retaliate if needed.
        if (!victim.getCombatBuilder().isAttacking() || victim.getCombatBuilder().isCooldown() || victim.isNpc() && ((NPC) victim).findNewTarget()) {
            if (victim.isPlayer() && ((Player) victim).isAutoRetaliate() && !victim.getMovementQueue().isMoving() && ((Player) victim).getWalkToTask() == null) {
                victim.getCombatBuilder().setDidAutoRetaliate(true);
                victim.getCombatBuilder().attack(attacker);
            } else if (victim.isNpc()) {
                if (!(attacker.isNpc() && ((NPC) attacker).isSummoningNpc())) {
                    NPC npc = (NPC) victim;
                    if (npc.getMovementCoordinator().getCoordinateState() == CoordinateState.HOME) {
                        victim.getCombatBuilder().attack(attacker);
                        npc.setFindNewTarget(false);
                    }
                }
            }
        }

        if (attacker.isNpc() && victim.isPlayer()) {
            NPC npc = (NPC) attacker;
            Player p = (Player) victim;
            if (npc.switchesVictim() && RandomUtility.inclusiveRandom(6) <= 1) {
                if (npc.getDefinition().isAggressive()) {
                    npc.setFindNewTarget(true);
                } else {
                    if (p.getLocalPlayers().size() >= 1) {
                        List<Player> list = p.getLocalPlayers();
                        Player c = list.get(RandomUtility.inclusiveRandom(list.size() - 1));
                        npc.getCombatBuilder().attack(c);
                    }
                }
            }

            Sounds.sendSound(p, Sounds.getPlayerBlockSounds(p.getEquipment().get(Equipment.WEAPON_SLOT).getId()));
            /**
             * CUSTOM ON DAMAGE STUFF *
             */
            /*if (victim.isPlayer() && npc.getId() == 13447) {
                Nex.dealtDamage(((Player) victim), damage);
            }*/

        } else if (attacker.isPlayer()) {
            Player player = (Player) attacker;

            /**
             * SKULLS *
             */
            if (player.getLocation() == Location.WILDERNESS && victim.isPlayer()) {
                if (!player.getCombatBuilder().isBeingAttacked() && !player.getCombatBuilder().didAutoRetaliate() || player.getCombatBuilder().isBeingAttacked() && player.getCombatBuilder().getLastAttacker() != victim && Location.inMulti(player)) {
                    CombatFactory.skullPlayer(player);
                }
            }

            player.setLastCombatType(container.getCombatType());

            Sounds.sendSound(player, Sounds.getPlayerAttackSound(player));

            /**
             * CUSTOM ON DAMAGE STUFF *
             */
            if (victim.isPlayer()) {
                Sounds.sendSound((Player) victim, Sounds.getPlayerBlockSounds(((Player) victim).getEquipment().get(Equipment.WEAPON_SLOT).getId()));
            }

            /*int barrowsEffect = RandomUtility.getRandom(8);

            if (player.getEquipment().contains(1))
                barrowsEffect -= 4;

            if (CombatFactory.fullTorags(player) && (barrowsEffect <= 1)) {
                int skillDrain = 1;
                int damageDrain = (int) (damage * 0.1);

                if (victim.isPlayer()) {
                    ((Player) victim).getSkillManager().setCurrentLevel(Skill.forId(skillDrain), player.getSkillManager().getCurrentLevel(Skill.forId(skillDrain)) - damageDrain);
                    if (((Player) victim).getSkillManager().getCurrentLevel(Skill.forId(skillDrain)) < 1) {
                        ((Player) victim).getSkillManager().setCurrentLevel(Skill.forId(skillDrain), 1);
                    }
                    player.getPacketSender().sendMessage("The power of Torag drains " + ((Player) victim).getUsername() + "'s " + Misc.formatText(Skill.forId(skillDrain).toString().toLowerCase()) + " level by " + damageDrain + ".");
                    ((Player) victim).getPacketSender().sendMessage("Your " + Misc.formatText(Skill.forId(skillDrain).toString().toLowerCase()) + " level has been drained.");
                } else if (victim.isNpc()) {
                    victim.getAsNpc().getDefenceWeakened()[1] = true;
                }
            }//end torag

            if (CombatFactory.fullAhrims(player) && (barrowsEffect <= 1)) {

                player.getPacketSender().sendMessage("The power of Ahrim has entangled your target!");
                ((Player) victim).getPacketSender().sendMessage("You've been entangled by the power of Ahrim!");
                victim.getMovementQueue().freeze(12);
            }

            if (CombatFactory.fullKarils(player) && (barrowsEffect <= 1)) {

                victim.dealDamage(new Hit(damage, Hitmask.RED, CombatIcon.RANGED));
                //player.forceChat("Get Rekt!");

                player.getPacketSender().sendMessage("The power of Karil fires an additional bolt at your target!");

            }

            if (CombatFactory.fullGuthans(attacker) && (barrowsEffect <= 1)) {
                player.performGraphic(new Graphic(398));
                attacker.heal(damage);
                player.getPacketSender().sendMessage("The power of Guthans drains life from your target.");
                return;
            }*/
        }

    }

    public void applyAOE(Character attacker, Character victim, boolean accurate, int damage, CombatType combatType) {

        if (!GlobalEventHandler.effectActive(GlobalEvent.Effect.COLLATERAL)) {
            return;
        }

        if (!accurate || damage <= 0) {
            return;
        }

        if (!Locations.Location.inMulti(victim)) {
            return;
        }

        int radius = 3;

        // Do the gfx here.
        if (combatType == CombatType.RANGED) {

        } else if (combatType == CombatType.MELEE) {

        }


        // We passed the checks, so now we do multiple target stuff.
        Iterator<? extends Character> it = null;
        if (attacker.isPlayer() && victim.isPlayer()) {
            it = ((Player) attacker).getLocalPlayers().iterator();
        } else if (attacker.isPlayer() && victim.isNpc()) {
            it = ((Player) attacker).getLocalNpcs().iterator();
        } else if (attacker.isNpc() && victim.isNpc()) {
            it = World.getNpcs().iterator();
        } else if (attacker.isNpc() && victim.isPlayer()) {
            it = World.getPlayers().iterator();
        }

        for (Iterator<? extends Character> $it = it; $it.hasNext(); ) {
            Character next = $it.next();

            if (next == null) {
                continue;
            }

            if (next.isNpc()) {
                NPC n = (NPC) next;
                if (!n.getDefinition().isAttackable() || n.isSummoningNpc()) {
                    continue;
                }
            } else {
                Player p = (Player) next;
                if (p.getLocation() != Locations.Location.WILDERNESS || !Locations.Location.inMulti(p)) {
                    continue;
                }
            }


            if (next.getPosition().isWithinDistance(victim.getPosition(), radius) && !next.equals(attacker) && !next.equals(victim) && next.getConstitution() > 0 && next.getConstitution() > 0) {

                int calc = RandomUtility.inclusiveRandom(0, damage);
                next.dealDamage(new Hit(calc, Hitmask.RED, CombatIcon.NONE));
                next.performGraphic(new Graphic(576, GraphicHeight.HIGH));
                next.getCombatBuilder().addDamage(attacker, calc);
                next.getCombatBuilder().attack(attacker);
            }
        }
    }

    public boolean shouldRetaliate() {
        if (victim.isPlayer()) {
            if (attacker.isNpc()) {
                if (!((NPC) attacker).getDefinition().isAttackable()) {
                    return false;
                }
            }
            return victim.isPlayer() && ((Player) victim).isAutoRetaliate() && !victim.getMovementQueue().isMoving() && ((Player) victim).getWalkToTask() == null;
        } else if (!(attacker.isNpc() && ((NPC) attacker).isSummoningNpc())) {
            NPC npc = (NPC) victim;
            return npc.getMovementCoordinator().getCoordinateState() == CoordinateState.HOME;
        }
        return false;
    }

    public void retaliate() {
        if (victim.isPlayer()) {
            victim.getCombatBuilder().setDidAutoRetaliate(true);
            victim.getCombatBuilder().attack(attacker);
        } else if (victim.isNpc()) {
            NPC npc = (NPC) victim;
            npc.getCombatBuilder().attack(attacker);
            npc.setFindNewTarget(false);
        }
    }

    private boolean initialRun() {
        return this.delay == 0;
    }

}

