package com.arlania.world.content.combat.strategy.impl;

import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.*;
import com.arlania.util.Misc;
import com.arlania.util.RandomUtility;
import com.arlania.world.World;
import com.arlania.world.content.combat.CombatContainer;
import com.arlania.world.content.combat.CombatHitTask;
import com.arlania.world.content.combat.CombatType;
import com.arlania.world.content.combat.strategy.CombatStrategy;
import com.arlania.world.entity.impl.Character;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.npc.NPCMovementCoordinator.Coordinator;
import com.arlania.world.entity.impl.player.Player;

import java.util.List;

public class KalphiteQueen implements CombatStrategy {

    public static NPC KALPHITE_QUEEN;

    public static void spawn(int id, Position pos) {
        KALPHITE_QUEEN = new NPC(id, pos);
        KALPHITE_QUEEN.getMovementCoordinator().setCoordinator(new Coordinator(true, 3));
        World.register(KALPHITE_QUEEN);
    }

    public static void death(final int id, final Position pos, Player player) {

        NPC n = new NPC(id, pos);
        int respawn = 10;

        if (player.getStaffRights().getStaffRank() > 1)
            respawn /= 2;

        if(player.flashbackTime > 0)
            respawn = 1;

        TaskManager.submit(new Task(respawn) {
            @Override
            protected void execute() {
                spawn(id == 1160 ? 1158 : 1160, pos);
                stop();
            }
        });
    }

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

        KALPHITE_QUEEN = entity.getAsNpc();

        if (victim.getConstitution() <= 0 || KALPHITE_QUEEN.getConstitution() <= 0) {
            return true;
        }
        if (KALPHITE_QUEEN.getAsNpc().isChargingAttack() || !victim.isPlayer()) {
            return true;
        }
        Player p = (Player) victim;
        final List<Player> list = Misc.getCombinedPlayerList(p);
        if (Locations.goodDistance(KALPHITE_QUEEN.getPosition().copy(), victim.getPosition().copy(), 1) && RandomUtility.inclusiveRandom(6) <= 2) {
            KALPHITE_QUEEN.performAnimation(new Animation(KALPHITE_QUEEN.getDefinition().getAttackAnimation()));
            KALPHITE_QUEEN.getCombatBuilder().setContainer(new CombatContainer(KALPHITE_QUEEN, victim, 1, 1, CombatType.MELEE, true));
        } else {
            KALPHITE_QUEEN.setChargingAttack(true);
            KALPHITE_QUEEN.performAnimation(new Animation(secondForm() ? 6234 : 6240));
            TaskManager.submit(new Task(1, KALPHITE_QUEEN, false) {
                int tick = 0;

                @Override
                protected void execute() {
                    if (tick == 1) {
                        for (Player toAttack : list) {
                            if (toAttack != null && Locations.goodDistance(KALPHITE_QUEEN.getPosition(), toAttack.getPosition(), 7) && toAttack.getConstitution() > 0) {
                                new Projectile(KALPHITE_QUEEN, toAttack, secondForm() ? 279 : 280, 44, 3, 43, 43, 0).sendProjectile();
                            }
                        }
                    } else if (tick == 3) {
                        for (Player toAttack : list) {
                            if (toAttack != null && Locations.goodDistance(KALPHITE_QUEEN.getPosition(), toAttack.getPosition(), 7) && toAttack.getConstitution() > 0) {
                                toAttack.performGraphic(new Graphic(secondForm() ? 278 : 279));
                            }
                        }
                    } else if (tick == 5) {
                        for (Player toAttack : list) {
                            if (toAttack != null && Locations.goodDistance(KALPHITE_QUEEN.getPosition(), toAttack.getPosition(), 7) && toAttack.getConstitution() > 0) {
                                KALPHITE_QUEEN.setEntityInteraction(toAttack);
                                CombatType cbType = (secondForm() && RandomUtility.inclusiveRandom(5) <= 3 ? CombatType.RANGED : CombatType.MAGIC);
                                KALPHITE_QUEEN.getCombatBuilder().setVictim(toAttack);
                                new CombatHitTask(KALPHITE_QUEEN.getCombatBuilder(), new CombatContainer(KALPHITE_QUEEN, toAttack, 1, cbType, true)).handleAttack();
                            }
                        }
                        KALPHITE_QUEEN.getCombatBuilder().attack(victim);
                        stop();
                    }
                    tick++;
                }

                @Override
                public void stop() {
                    setEventRunning(false);
                    KALPHITE_QUEEN.setChargingAttack(false);
                }
            });
        }
        return true;
    }

    public static boolean secondForm() {
        return KALPHITE_QUEEN.getId() == 1160;
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
