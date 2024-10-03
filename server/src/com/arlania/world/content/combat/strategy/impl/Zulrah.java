package com.arlania.world.content.combat.strategy.impl;

import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.*;
import com.arlania.model.Locations.Location;
import com.arlania.util.Misc;
import com.arlania.util.RandomUtility;
import com.arlania.world.content.combat.CombatContainer;
import com.arlania.world.content.combat.CombatHitTask;
import com.arlania.world.content.combat.CombatType;
import com.arlania.world.content.combat.strategy.CombatStrategy;
import com.arlania.world.entity.impl.Character;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.Player;

import java.util.List;

public class Zulrah implements CombatStrategy {

    public static NPC ZULRAH;

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

        ZULRAH = entity.getAsNpc();

        if (victim.getConstitution() <= 0 || ZULRAH.getConstitution() <= 0) {
            return true;
        }
        if (ZULRAH.isChargingAttack() || !victim.isPlayer()) {
            return true;
        }
        Player p = (Player) victim;
        final List<Player> list = Misc.getCombinedPlayerList(p);
        if (Locations.goodDistance(ZULRAH.getPosition().copy(), victim.getPosition().copy(), 5) && RandomUtility.inclusiveRandom(6) <= 2) {
            ZULRAH.performAnimation(new Animation(ZULRAH.getDefinition().getAttackAnimation()));
            if (ZULRAH.getId() == 2042)
                ZULRAH.getCombatBuilder().setContainer(new CombatContainer(ZULRAH, victim, 1, 1, CombatType.MAGIC, true));
            else if (ZULRAH.getId() == 2043)
                ZULRAH.getCombatBuilder().setContainer(new CombatContainer(ZULRAH, victim, 1, 1, CombatType.RANGED, true));
        } else {
            ZULRAH.setChargingAttack(true);
            ZULRAH.performAnimation(new Animation(5069));
            TaskManager.submit(new Task(1, ZULRAH, false) {
                int tick = 0;

                @Override
                protected void execute() {
                    if (tick == 1) {
                        for (Player toAttack : list) {
                            if (toAttack != null && Locations.goodDistance(ZULRAH.getPosition(), toAttack.getPosition(), 7) && toAttack.getConstitution() > 0) {
                                new Projectile(ZULRAH, toAttack, 1044, 44, 3, 43, 31, 0).sendProjectile();
                            }
                        }
                    } else if (tick == 3) {
                        for (Player toAttack : list) {
                            if (toAttack != null && Locations.goodDistance(ZULRAH.getPosition(), toAttack.getPosition(), 7) && toAttack.getConstitution() > 0) {
                                toAttack.performGraphic(new Graphic(1044));
                            }
                        }
                    } else if (tick == 5) {
                        for (Player toAttack : list) {
                            if (toAttack != null && Locations.goodDistance(ZULRAH.getPosition(), toAttack.getPosition(), 7) && toAttack.getConstitution() > 0) {
                                ZULRAH.setEntityInteraction(toAttack);
                                CombatType cbType = CombatType.MAGIC;
                                if (ZULRAH.getId() == 2042)
                                    cbType = CombatType.MAGIC;
                                else if (ZULRAH.getId() == 2043)
                                    cbType = CombatType.RANGED;
                                ZULRAH.getCombatBuilder().setVictim(toAttack);
                                new CombatHitTask(ZULRAH.getCombatBuilder(), new CombatContainer(ZULRAH, toAttack, 1, cbType, true)).handleAttack();
                            }
                        }
                        ZULRAH.getCombatBuilder().attack(victim);
                        stop();
                    }
                    tick++;
                }

                @Override
                public void stop() {
                    setEventRunning(false);
                    ZULRAH.setChargingAttack(false);
                }
            });
        }
        return true;
    }

    public static boolean secondForm() {
        return ZULRAH.getId() == 2042;
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
