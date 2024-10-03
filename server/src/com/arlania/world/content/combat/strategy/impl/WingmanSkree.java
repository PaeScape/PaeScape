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

public class WingmanSkree implements CombatStrategy {

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
        NPC skree = (NPC) entity;
        if (victim.getConstitution() <= 0) {
            return true;
        }
        if (skree.isChargingAttack()) {
            return true;
        }

        skree.performAnimation(new Animation(skree.getDefinition().getAttackAnimation()));
        skree.setChargingAttack(true);

        skree.getCombatBuilder().setContainer(new CombatContainer(skree, victim, 1, 3, CombatType.MAGIC, true));

        TaskManager.submit(new Task(1, skree, false) {
            int tick = 0;

            @Override
            public void execute() {
                if (tick == 1) {
                    new Projectile(skree, victim, 1505, 44, 3, 43, 43, 0).sendProjectile();
                    skree.setChargingAttack(false);
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
        return CombatType.MAGIC;
    }
}
