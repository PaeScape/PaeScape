package com.arlania.world.content.combat.strategy.impl;

import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.Animation;
import com.arlania.model.Projectile;
import com.arlania.world.content.combat.CombatContainer;
import com.arlania.world.content.combat.CombatType;
import com.arlania.world.content.combat.strategy.CombatStrategy;
import com.arlania.world.entity.impl.Character;
import com.arlania.world.entity.impl.npc.NPC;

public class Grimspike implements CombatStrategy {

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
        NPC grimspike = (NPC) entity;
        if (grimspike.isChargingAttack() || victim.getConstitution() <= 0) {
            return true;
        }

        grimspike.performAnimation(new Animation(grimspike.getDefinition().getAttackAnimation()));
        grimspike.setChargingAttack(true);

        grimspike.getCombatBuilder().setContainer(new CombatContainer(grimspike, victim, 1, 3, CombatType.RANGED, true));

        TaskManager.submit(new Task(1, grimspike, false) {
            int tick = 0;

            @Override
            public void execute() {
                if (tick == 1) {
                    new Projectile(grimspike, victim, 37, 44, 3, 43, 43, 0).sendProjectile();
                    grimspike.setChargingAttack(false);
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
        return 6;
    }

    @Override
    public CombatType getCombatType() {
        return CombatType.RANGED;
    }
}
