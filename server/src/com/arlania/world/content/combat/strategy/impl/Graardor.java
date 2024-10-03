package com.arlania.world.content.combat.strategy.impl;

import com.arlania.GameSettings;
import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.*;
import com.arlania.util.Misc;
import com.arlania.util.RandomUtility;
import com.arlania.world.content.combat.CombatContainer;
import com.arlania.world.content.combat.CombatHitTask;
import com.arlania.world.content.combat.CombatType;
import com.arlania.world.content.combat.strategy.CombatStrategy;
import com.arlania.world.entity.impl.Character;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.Player;

public class Graardor implements CombatStrategy {

    private static final Animation attack_anim = new Animation(7063);
    private static final Animation osrs_attack_anim = new Animation(7021 + GameSettings.OSRS_ANIM_OFFSET);
    private static final Graphic graphic1 = new Graphic(1200, GraphicHeight.MIDDLE);

    @Override
    public boolean canAttack(Character entity, Character victim) {
        //return victim.isPlayer() && ((Player)victim).getMinigameAttributes().getGodwarsDungeonAttributes().hasEnteredRoom();
        return victim.isPlayer();
    }

    @Override
    public CombatContainer attack(Character entity, Character victim) {
        return null;
    }

    @Override
    public boolean customContainerAttack(Character entity, Character victim) {
        NPC graardor = (NPC) entity;
        if (graardor.isChargingAttack() || graardor.getConstitution() <= 0) {
            return true;
        }
        CombatType style = RandomUtility.inclusiveRandom(4) <= 1 && Locations.goodDistance(graardor.getPosition(), victim.getPosition(), 1) ? CombatType.MELEE : CombatType.RANGED;
        if (style == CombatType.MELEE) {
            graardor.performAnimation(new Animation(graardor.getDefinition().getAttackAnimation()));
            graardor.getCombatBuilder().setContainer(new CombatContainer(graardor, victim, 1, 1, CombatType.MELEE, true));
        } else {
            if (graardor.getId() < 14000)
                graardor.performAnimation(attack_anim);
            else
                graardor.performAnimation(osrs_attack_anim);

            graardor.setChargingAttack(true);
            Player target = (Player) victim;
            for (Player t : Misc.getCombinedPlayerList(target)) {
                if (t == null || t.isTeleporting())
                    continue;
                if (t.getPosition().distanceToPoint(graardor.getPosition().getX(), graardor.getPosition().getY()) > 20)
                    continue;
                new Projectile(graardor, target, graphic1.getId(), 44, 3, 43, 43, 0).sendProjectile();

            }//for
            TaskManager.submit(new Task(2, target, false) {
                @Override
                public void execute() {
                    for (Player t : Misc.getCombinedPlayerList(target)) {
                        if (t == null)
                            continue;
                        graardor.getCombatBuilder().setVictim(t);
                        new CombatHitTask(graardor.getCombatBuilder(), new CombatContainer(graardor, t, 1, CombatType.RANGED, true)).handleAttack();

                    }//for
                    graardor.setChargingAttack(false);
                    stop();
                }
            });
        }
        return true;
    }

    @Override
    public int attackDelay(Character entity) {
        return entity.getAttackSpeed();
    }

    @Override
    public int attackDistance(Character entity) {
        return 4;
    }

    @Override
    public CombatType getCombatType() {
        return CombatType.MIXED;
    }
}
