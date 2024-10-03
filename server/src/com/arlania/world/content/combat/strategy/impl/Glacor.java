package com.arlania.world.content.combat.strategy.impl;

import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.Animation;
import com.arlania.model.Graphic;
import com.arlania.model.Locations;
import com.arlania.model.Projectile;
import com.arlania.util.RandomUtility;
import com.arlania.world.content.combat.CombatContainer;
import com.arlania.world.content.combat.CombatType;
import com.arlania.world.content.combat.strategy.CombatStrategy;
import com.arlania.world.entity.impl.Character;
import com.arlania.world.entity.impl.npc.NPC;

public class Glacor implements CombatStrategy {


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
        NPC glacor = (NPC) entity;
        if (glacor.isChargingAttack() || victim.getConstitution() <= 0) {
            return true;
        }
        if (Locations.goodDistance(glacor.getPosition().copy(), victim.getPosition().copy(), 1) && RandomUtility.inclusiveRandom(5) <= 3) {
            glacor.performAnimation(new Animation(glacor.getDefinition().getAttackAnimation()));
            glacor.getCombatBuilder().setContainer(new CombatContainer(glacor, victim, 1, 1, CombatType.MELEE, true));
        } else {
            glacor.performAnimation(new Animation(9952));
            glacor.setChargingAttack(true);
            glacor.getCombatBuilder().setContainer(new CombatContainer(glacor, victim, 1, 2, CombatType.MAGIC, RandomUtility.inclusiveRandom(10) > 2));
            TaskManager.submit(new Task(1, glacor, false) {
                int tick = 0;

                @Override
                protected void execute() {
                    switch (tick) {
                        case 0:
                            new Projectile(glacor, victim, 1017, 44, 3, 43, 31, 0).sendProjectile();
                            break;
                        case 1:
                            victim.performGraphic(new Graphic(504));
                            glacor.setChargingAttack(false);
                            stop();
                            break;
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
        return 8;
    }

    @Override
    public CombatType getCombatType() {
        return CombatType.MIXED;
    }
}
