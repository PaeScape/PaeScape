package com.arlania.world.content.combat.strategy.impl;

import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.Animation;
import com.arlania.model.Locations;
import com.arlania.model.Projectile;
import com.arlania.util.RandomUtility;
import com.arlania.world.content.combat.CombatContainer;
import com.arlania.world.content.combat.CombatType;
import com.arlania.world.content.combat.strategy.CombatStrategy;
import com.arlania.world.entity.impl.Character;
import com.arlania.world.entity.impl.npc.NPC;

public class Skotizo implements CombatStrategy {

    @Override
    public boolean canAttack(Character entity, Character victim) {
        return true;
    }

    @Override
    public CombatContainer attack(Character entity, Character victim) {
        return null;
    }

    @Override
    public boolean customContainerAttack(Character entity, Character victim) {
        NPC skotizo = (NPC) entity;
        if (skotizo.isChargingAttack() || victim.getConstitution() <= 0) {
            return true;
        }
        if (Locations.goodDistance(skotizo.getPosition().copy(), victim.getPosition().copy(), 3) && RandomUtility.inclusiveRandom(5) <= 3) {
            skotizo.performAnimation(new Animation(skotizo.getDefinition().getAttackAnimation()));
            skotizo.getCombatBuilder().setContainer(new CombatContainer(skotizo, victim, 1, 1, CombatType.MELEE, true));
            if (RandomUtility.inclusiveRandom(10) <= 2) {
                new Projectile(skotizo, victim, 971, 44, 3, 43, 43, 0).sendProjectile();
                //victim.moveTo(new Position(3156 + RandomUtility.getRandom(3), 3804 + RandomUtility.getRandom(3)));
                //victim.performAnimation(new Animation(534));
            }
        } else {
            skotizo.setChargingAttack(true);
            skotizo.performAnimation(new Animation(64));
            skotizo.getCombatBuilder().setContainer(new CombatContainer(skotizo, victim, 1, 8, CombatType.MAGIC, true));
            TaskManager.submit(new Task(1, skotizo, false) {
                int tick = 0;

                @Override
                public void execute() {
                    if (tick == 0) {
                        new Projectile(skotizo, victim, 971, 44, 3, 41, 43, 0).sendProjectile();
                    } else if (tick == 1) {
                        skotizo.setChargingAttack(false);
                        stop();
                    }
                    tick++;
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
        return 5;
    }

    @Override
    public CombatType getCombatType() {
        return CombatType.MIXED;
    }
}
