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

public class Jad implements CombatStrategy {

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
        NPC jad = (NPC) entity;
        if (victim.getConstitution() <= 0 || victim.getConstitution() <= 0) {
            return true;
        }
        if (jad.getConstitution() <= 1200 && !jad.hasHealed()) {
            jad.performAnimation(anim1);
            jad.performGraphic(gfx1);
            jad.setConstitution(jad.getConstitution() + RandomUtility.inclusiveRandom(1600));
            jad.setHealed(true);
        }
        if (jad.isChargingAttack()) {
            return true;
        }
        int random = RandomUtility.inclusiveRandom(10);
        if (random <= 8 && Locations.goodDistance(jad.getPosition().getX(), jad.getPosition().getY(), victim.getPosition().getX(), victim.getPosition().getY(), 3)) {
            jad.performAnimation(anim2);
            jad.getCombatBuilder().setContainer(new CombatContainer(jad, victim, 1, 2, CombatType.MELEE, true));
        } else if (random <= 4 || !Locations.goodDistance(jad.getPosition().getX(), jad.getPosition().getY(), victim.getPosition().getX(), victim.getPosition().getY(), 14)) {
            jad.getCombatBuilder().setContainer(new CombatContainer(jad, victim, 1, 6, CombatType.MAGIC, true));
            jad.performAnimation(anim3);
            jad.performGraphic(gfx3);
            jad.setChargingAttack(true);
            TaskManager.submit(new Task(2, jad, false) {
                int tick = 0;

                @Override
                public void execute() {
                    switch (tick) {
                        case 1:
                            new Projectile(jad, victim, gfx5.getId(), 44, 3, 43, 31, 0).sendProjectile();
                            jad.setChargingAttack(false);
                            stop();
                            break;
                    }
                    tick++;
                }
            });
        } else {
            jad.getCombatBuilder().setContainer(new CombatContainer(jad, victim, 1, 5, CombatType.RANGED, true));
            jad.performAnimation(anim4);
            jad.performGraphic(gfx2);
            jad.setChargingAttack(true);
            TaskManager.submit(new Task(2, jad, false) {
                @Override
                public void execute() {
                    victim.performGraphic(gfx4);
                    jad.setChargingAttack(false);
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
        return 10;
    }

    private static final Animation anim1 = new Animation(9254);
    private static final Animation anim2 = new Animation(9277);
    private static final Animation anim3 = new Animation(9300);
    private static final Animation anim4 = new Animation(9276);
    private static final Graphic gfx1 = new Graphic(444);
    private static final Graphic gfx2 = new Graphic(1625);
    private static final Graphic gfx3 = new Graphic(1626);
    private static final Graphic gfx4 = new Graphic(451);
    private static final Graphic gfx5 = new Graphic(1627);

    @Override
    public CombatType getCombatType() {
        return CombatType.MIXED;
    }
}
