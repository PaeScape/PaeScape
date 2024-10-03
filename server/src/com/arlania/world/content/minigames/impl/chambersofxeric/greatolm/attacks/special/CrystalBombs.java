package com.arlania.world.content.minigames.impl.chambersofxeric.greatolm.attacks.special;

import com.arlania.GameSettings;
import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.*;
import com.arlania.util.RandomUtility;
import com.arlania.world.World;
import com.arlania.world.content.CustomObjects;
import com.arlania.world.content.minigames.impl.chambersofxeric.greatolm.OlmAnimations;
import com.arlania.world.content.minigames.impl.chambersofxeric.greatolm.attacks.Attacks;
import com.arlania.world.content.minigames.impl.raidsparty.RaidsParty;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.Player;

public class CrystalBombs {

    public static void performAttack(RaidsParty party, int height) {
        party.getGreatOlmNpc().performGreatOlmAttack(party);
        party.setOlmAttackTimer(6);

        TaskManager.submit(new Task(1, party, true) {
            int tick = 0;

            @Override
            public void execute() {
                if (party.getGreatOlmNpc().isDying() || party.isSwitchingPhases()) {
                    stop();
                }
                for (int i = 0; i < 2; i++) {
                    if (tick == 1) {
                        Position pos = Attacks.randomLocation(height);
                        GameObject
                                bomb = new GameObject(329766, pos);
                        NPC spawn = NPC.of(5090, pos);
                        World.register(spawn);
                        new Projectile(party.getGreatOlmNpc(), spawn, Attacks.FALLING_CRYSTAL, 60, 8, 70, 10, 0)
                                .sendProjectile();
                        TaskManager.submit(new Task(1) {
                            @Override
                            public void execute() {
                                CustomObjects.spawnTempObject(bomb, party.getOwner(), 6);
                                stop();
                            }
                        });

                        TaskManager.submit(new Task(6) {
                            @Override
                            public void execute() {
                                for (Player player : party.getPlayers()) {
                                    player.getPacketSender().sendObject(new GameObject(-1, pos, 10, 3));
                                }
                                stop();
                            }
                        });


                        TaskManager.submit(new Task(7) {
                            @Override
                            public void execute() {
                                party.getOwner().getPacketSender()
                                        .sendGlobalGraphic(new Graphic(40 + GameSettings.OSRS_GFX_OFFSET, GraphicHeight.LOW), pos);

                                for (Player member : party.getPlayers()) {
                                    if (member != null && member.getMinigameAttributes().getRaidsAttributes().isInsideRaid()) {
                                        if (member.getPosition().sameAs(pos)) {
                                            member.dealDamage(
                                                    new Hit(RandomUtility.inclusiveRandom(150, 250), Hitmask.RED, CombatIcon.NONE));
                                        } else if (Locations.goodDistance(member.getPosition(), pos, 1)) {
                                            member.dealDamage(
                                                    new Hit(RandomUtility.inclusiveRandom(100, 150), Hitmask.RED, CombatIcon.NONE));
                                        } else if (Locations.goodDistance(member.getPosition(), pos, 2)) {
                                            member.dealDamage(
                                                    new Hit(RandomUtility.inclusiveRandom(75, 100), Hitmask.RED, CombatIcon.NONE));
                                        }
                                    }
                                }
                                World.deregister(spawn);

                                stop();
                            }
                        });

                    }
                }
                if (tick == 10) {
                    stop();
                }
                if (tick == 2) {
                    OlmAnimations.resetAnimation(party);
                }

                tick++;
            }
        });

    }

}
