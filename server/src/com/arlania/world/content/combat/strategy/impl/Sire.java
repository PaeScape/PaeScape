package com.arlania.world.content.combat.strategy.impl;

import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.Graphic;
import com.arlania.model.Locations;
import com.arlania.model.Projectile;
import com.arlania.model.Skill;
import com.arlania.util.RandomUtility;
import com.arlania.world.content.combat.CombatContainer;
import com.arlania.world.content.combat.CombatType;
import com.arlania.world.content.combat.strategy.CombatStrategy;
import com.arlania.world.entity.impl.Character;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.Player;

public class Sire implements CombatStrategy {

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
        NPC sire = (NPC) entity;
        if (sire.isChargingAttack() || victim.getConstitution() <= 0) {
            return true;
        }
        if (Locations.goodDistance(sire.getPosition().copy(), victim.getPosition().copy(), 3) && RandomUtility.inclusiveRandom(5) <= 3) {
            sire.getCombatBuilder().setContainer(new CombatContainer(sire, victim, 1, 1, CombatType.MAGIC, true));
            if (RandomUtility.inclusiveRandom(10) <= 2) {
                victim.performGraphic(new Graphic(1847));
                new Projectile(sire, victim, 2183, 44, 3, 43, 40, 0).sendProjectile();
            }
        } else {
            //sire.setChargingAttack(true);
            sire.getCombatBuilder().setContainer(new CombatContainer(sire, victim, 1, 3, CombatType.MAGIC, true));
            TaskManager.submit(new Task(1, sire, false) {
                int tick = 0;

                @Override
                public void execute() {
                    if (tick == 0) {
                        new Projectile(sire, victim, 146, 44, 3, 41, 40, 0).sendProjectile();
                    } else if (tick == 1) {
                        //sire.setChargingAttack(false);
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
        return 3;
    }

    @Override
    public CombatType getCombatType() {
        return CombatType.MIXED;
    }
}
