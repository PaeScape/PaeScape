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

public class Growler implements CombatStrategy {

    private static final Animation anim = new Animation(7019);
    private static final Graphic graphic = new Graphic(384);

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
        NPC growler = (NPC) entity;
        if (growler.isChargingAttack() || victim.getConstitution() <= 0) {
            return true;
        }
        if (Locations.goodDistance(growler.getPosition().copy(), victim.getPosition().copy(), 1) && RandomUtility.inclusiveRandom(5) <= 3) {
            growler.performAnimation(new Animation(growler.getDefinition().getAttackAnimation()));
            growler.getCombatBuilder().setContainer(new CombatContainer(growler, victim, 1, 1, CombatType.MELEE, true));
        } else {
            growler.setChargingAttack(true);
            growler.performAnimation(anim);
            growler.getCombatBuilder().setContainer(new CombatContainer(growler, victim, 1, 3, CombatType.MAGIC, true));
            TaskManager.submit(new Task(1, growler, false) {
                int tick = 0;

                @Override
                public void execute() {
                    if (tick == 1) {
                        new Projectile(growler, victim, graphic.getId(), 44, 3, 43, 43, 0).sendProjectile();
                        growler.setChargingAttack(false);
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
        return 8;
    }

    @Override
    public CombatType getCombatType() {
        return CombatType.MIXED;
    }
}
