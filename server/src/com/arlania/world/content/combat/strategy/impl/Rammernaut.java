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

public class Rammernaut implements CombatStrategy {

    private static final Animation attack_anim = new Animation(13703);
    private static final Animation attack_anim2 = new Animation(13705);
    private static final Graphic graphic1 = new Graphic(1629, GraphicHeight.MIDDLE);

    @Override
    public boolean canAttack(Character entity, Character victim) {
        return victim.isPlayer();
    }

    @Override
    public CombatContainer attack(Character entity, Character victim) {
        return null;
    }

    @Override
    public boolean customContainerAttack(Character entity, Character victim) {
        NPC rammernaut = (NPC) entity;
        if (rammernaut.isChargingAttack() || rammernaut.getConstitution() <= 0) {
            return true;
        }
        if(!Locations.goodDistance(rammernaut.getPosition(), victim.getPosition(), 1))
            rammernaut.walkToPosition(victim.getPosition());

        CombatType style = RandomUtility.inclusiveRandom(2) >= 1 && Locations.goodDistance(rammernaut.getPosition(), victim.getPosition(), 1) ? CombatType.MELEE : CombatType.RANGED;
        if (style == CombatType.MELEE) {
            rammernaut.performAnimation(attack_anim);
            rammernaut.getCombatBuilder().setContainer(new CombatContainer(rammernaut, victim, 1, 1, CombatType.MELEE, true));
        } else {
            rammernaut.performAnimation(attack_anim2);

            Player target = (Player) victim;
            for (Player t : Misc.getCombinedPlayerList(target)) {
                if (t == null || t.isTeleporting())
                    continue;
                if (t.getPosition().distanceToPoint(rammernaut.getPosition().getX(), rammernaut.getPosition().getY()) > 40)
                    continue;
                new Projectile(rammernaut, target, graphic1.getId(), 44, 3, 43, 43, 0).sendProjectile();

            }//for
            TaskManager.submit(new Task(2, target, false) {
                @Override
                public void execute() {
                    for (Player t : Misc.getCombinedPlayerList(target)) {
                        if (t == null)
                            continue;
                        rammernaut.getCombatBuilder().setVictim(t);
                        new CombatHitTask(rammernaut.getCombatBuilder(), new CombatContainer(rammernaut, t, 1, CombatType.RANGED, true)).handleAttack();

                        if(Locations.goodDistance(rammernaut.getPosition(), victim.getPosition(), 1)) {
                            new CombatHitTask(rammernaut.getCombatBuilder(), new CombatContainer(rammernaut, t, 1, CombatType.MELEE, true)).handleAttack();
                        }

                    }//for
                    rammernaut.setChargingAttack(false);
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
