package com.arlania.world.content.combat.strategy.impl;

import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.Animation;
import com.arlania.model.Projectile;
import com.arlania.util.RandomUtility;
import com.arlania.world.content.combat.CombatContainer;
import com.arlania.world.content.combat.CombatType;
import com.arlania.world.content.combat.strategy.CombatStrategy;
import com.arlania.world.entity.impl.Character;
import com.arlania.world.entity.impl.npc.NPC;

public class PlaneFreezer implements CombatStrategy {

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
        NPC lakra = (NPC) entity;
        if (victim.getConstitution() <= 0) {
            return true;
        }
        if (lakra.isChargingAttack()) {
            return true;
        }
        lakra.setChargingAttack(true);
        lakra.performAnimation(new Animation((13770)));
        final CombatType attkType = RandomUtility.inclusiveRandom(5) <= 2 ? CombatType.RANGED : CombatType.MAGIC;
        lakra.getCombatBuilder().setContainer(new CombatContainer(lakra, victim, 2, 4, attkType, RandomUtility.inclusiveRandom(5) > 1));
        TaskManager.submit(new Task(1, lakra, false) {
            int tick = 0;

            @Override
            public void execute() {
                if (tick == 2) {
                    new Projectile(lakra, victim, (attkType == CombatType.RANGED ? 605 : 473), 44, 3, 43, 43, 0).sendProjectile();
                    lakra.setChargingAttack(false);
                    stop();
                }
                tick++;
            }
        });
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
