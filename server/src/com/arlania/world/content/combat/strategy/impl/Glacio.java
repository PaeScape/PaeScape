package com.arlania.world.content.combat.strategy.impl;

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

public class Glacio implements CombatStrategy {

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
        NPC glacio = (NPC) entity;
        if (glacio.isChargingAttack() || victim.getConstitution() <= 0) {
            return true;
        }
        if (Locations.goodDistance(glacio.getPosition().copy(), victim.getPosition().copy(), 1) && RandomUtility.inclusiveRandom(5) <= 3) {
            glacio.performAnimation(new Animation(glacio.getDefinition().getAttackAnimation()));
            glacio.getCombatBuilder().setContainer(new CombatContainer(glacio, victim, 1, 1, CombatType.MELEE, true));
        } else if (!Locations.goodDistance(glacio.getPosition().copy(), victim.getPosition().copy(), 3) && RandomUtility.inclusiveRandom(5) == 1) {
            glacio.setChargingAttack(true);
            final Position pos = new Position(victim.getPosition().getX() - 2, victim.getPosition().getY());
            ((Player) victim).getPacketSender().sendGlobalGraphic(new Graphic(1549), pos);
            glacio.performAnimation(new Animation(11246));
            glacio.forceChat("You shall perish!");
            TaskManager.submit(new Task(2) {
                @Override
                protected void execute() {
                    glacio.performAnimation(new Animation(glacio.getDefinition().getAttackAnimation()));
                    glacio.getCombatBuilder().setContainer(new CombatContainer(glacio, victim, 1, 1, CombatType.MELEE, false));
                    glacio.setChargingAttack(false);
                    glacio.getCombatBuilder().setAttackTimer(0);
                    stop();
                }
            });
        } else {
            glacio.setChargingAttack(true);
            boolean barrage = RandomUtility.inclusiveRandom(4) <= 2;
            glacio.performAnimation(new Animation(barrage ? 11245 : 11252));
            glacio.getCombatBuilder().setContainer(new CombatContainer(glacio, victim, 1, 3, CombatType.MAGIC, true));
            TaskManager.submit(new Task(1, glacio, false) {
                int tick = 0;

                @Override
                public void execute() {
                    if (tick == 0 && !barrage) {
                        new Projectile(glacio, victim, 2706, 44, 3, 43, 43, 0).sendProjectile();
                    } else if (tick == 1) {
                        if (barrage && victim.isPlayer() && RandomUtility.inclusiveRandom(10) <= 5) {
                            victim.getMovementQueue().freeze(15);
                            victim.performGraphic(new Graphic(369));
                        }
                        if (barrage && RandomUtility.inclusiveRandom(6) <= 3) {
                            glacio.performAnimation(new Animation(11245));
                            for (Player toAttack : Misc.getCombinedPlayerList((Player) victim)) {
                                if (toAttack != null && Locations.goodDistance(glacio.getPosition(), toAttack.getPosition(), 7) && toAttack.getConstitution() > 0) {
                                    glacio.forceChat("DIE!");
                                    new CombatHitTask(glacio.getCombatBuilder(), new CombatContainer(glacio, toAttack, 2, CombatType.MAGIC, false)).handleAttack();
                                    toAttack.performGraphic(new Graphic(1556));
                                }
                            }
                        }
                        glacio.setChargingAttack(false).getCombatBuilder().setAttackTimer(attackDelay(glacio) - 2);
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
        if (npc == 50)
            anim = 81;
        else if (npc == 5362 || npc == 5363)
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
