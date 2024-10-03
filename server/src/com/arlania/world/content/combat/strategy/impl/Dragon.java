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

public class Dragon implements CombatStrategy {

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
        NPC dragon = (NPC) entity;
        if (dragon.isChargingAttack() || dragon.getConstitution() <= 0) {
            dragon.getCombatBuilder().setAttackTimer(attackDelay(dragon));
            return true;
        }
        if (Locations.goodDistance(dragon.getPosition().copy(), victim.getPosition().copy(), 1) && RandomUtility.inclusiveRandom(5) <= 3) {
            dragon.performAnimation(new Animation(dragon.getDefinition().getAttackAnimation()));
            dragon.getCombatBuilder().setContainer(new CombatContainer(dragon, victim, 1, 1, CombatType.MELEE, true));
        } else {
            dragon.setChargingAttack(true);
            dragon.performAnimation(new Animation(getAnimation(dragon.getId())));
            dragon.getCombatBuilder().setContainer(new CombatContainer(dragon, victim, 1, 3, CombatType.DRAGON_FIRE, true));
            TaskManager.submit(new Task(1, dragon, false) {
                int tick = 0;

                @Override
                public void execute() {
                    if (tick == 1 && dragon.getId() == 50) {
                        new Projectile(dragon, victim, 393 + RandomUtility.inclusiveRandom(3), 44, 3, 43, 43, 0).sendProjectile();
                    } else if (tick == 2) {
                        victim.performGraphic(new Graphic(5));
                    } else if (tick == 3) {
                        victim.performGraphic(new Graphic(5));
                        dragon.setChargingAttack(false).getCombatBuilder().setAttackTimer(attackDelay(dragon));
                        stop();
                    }
                    tick++;
                }
            });

        }
        return true;
    }

    public static int getAnimation(int npc) {
        int anim = 12259;
        if (npc == 50 || npc == 23033)
            anim = 81;
        else if (npc == 5363 || npc == 1590 || npc == 1591 || npc == 1592)
            anim = 14246;
        else if (npc == 51)
            anim = 13152;
        return anim;
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
