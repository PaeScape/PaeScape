package com.arlania.world.content.minigames.impl.chambersofxeric.greatolm.attacks.special;


import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.CombatIcon;
import com.arlania.model.Position;
import com.arlania.model.Projectile;
import com.arlania.util.RandomUtility;
import com.arlania.world.World;
import com.arlania.world.content.combat.CombatType;
import com.arlania.world.content.minigames.impl.chambersofxeric.greatolm.OlmAnimations;
import com.arlania.world.content.minigames.impl.chambersofxeric.greatolm.attacks.Attacks;
import com.arlania.world.content.minigames.impl.raidsparty.RaidsParty;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.Player;

public class FireWall {

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
                if (tick == 1) {
                    int random = RandomUtility.inclusiveRandom(party.getPlayersInRaids().size() - 1);
                    party.setFireWallPlayer(party.getPlayersInRaids().get(random));
                    party.getFireWallPlayer().getMovementQueue().setLockMovement(true);
                }
                if (tick >= 2 && (party.getFireWallPlayer().getPosition().getY() < 5732
                        || party.getFireWallPlayer().getPosition().getY() > 5747)) {
                    OlmAnimations.resetAnimation(party);
                    stop();
                }
                if (tick == 2) {
                    OlmAnimations.resetAnimation(party);
                }

                if (tick == 2) {
                    if (party.getFireWallPlayer().getMinigameAttributes().getRaidsAttributes().isInsideRaid()) {
                        Position pos = party.getFireWallPlayer().getPosition();
                        party.setFireWallSpawn(NPC.of(27558, new Position(3228, pos.getY() + 1, height)));
                        party.setFireWallSpawn1(NPC.of(27558, new Position(3228, pos.getY() - 1, height)));

                        NPC decoy = NPC.of(5090, new Position(3228, pos.getY() + 1, height));
                        NPC decoy1 = NPC.of(5090, new Position(3228, pos.getY() - 1, height));
                        TaskManager.submit(new Task(1, party, true) {
                            @Override
                            public void execute() {
                                new Projectile(party.getGreatOlmNpc(), decoy, Attacks.FIRE_BLAST, 60, 8, 70, 31, 0)
                                        .sendProjectile();
                                new Projectile(party.getGreatOlmNpc(), decoy1, Attacks.FIRE_BLAST, 60, 8, 70, 31, 0)
                                        .sendProjectile();
                                World.register(party.getFireWallSpawn());
                                World.register(party.getFireWallSpawn1());

                                stop();
                            }
                        });

                    }

                }

                if (tick == 5) {
                    party.getFireWallPlayer().getMovementQueue().setLockMovement(false);

                    Position pos = party.getFireWallPlayer().getPosition();
                    if (party.getFireWallPlayer().getMinigameAttributes().getRaidsAttributes().isInsideRaid()) {

                        int x = 3229;
                        for (int i = 0; i < 9; i++) {
                            Position position = new Position(x++, pos.getY() - 1, height);
                            party.getFireWallNpcs().add(NPC.of(27558, position));
                            NPC decoy = NPC.of(27558, position);
                            World.register(decoy);
                            new Projectile(party.getFireWallSpawn1(), decoy, Attacks.SMALL_FIRE_BLAST, 60, 8, 30, 31, 0)
                                    .sendProjectile();
                            TaskManager.submit(new Task(2, party, true) {
                                @Override
                                public void execute() {
                                    World.deregister(decoy);
                                    stop();
                                }
                            });
                        }
                        x = 3229;
                        for (int i = 9; i < 18; i++) {
                            Position position = new Position(x++, pos.getY() + 1, height);
                            party.getFireWallNpcs().add(NPC.of(27558, position));
                            NPC decoy = NPC.of(27558, position);
                            World.register(decoy);
                            new Projectile(party.getFireWallSpawn1(), decoy, Attacks.SMALL_FIRE_BLAST, 60, 8, 30, 31, 0)
                                    .sendProjectile();
                            TaskManager.submit(new Task(2, party, true) {
                                @Override
                                public void execute() {
                                    World.deregister(decoy);
                                    stop();
                                }
                            });
                        }

                        TaskManager.submit(new Task(1, party, true) {
                            @Override
                            public void execute() {

                                party.sendMessage("" + party.getFireWallNpcs().size());
                                for (int i = 0; i < 9; i++) {
                                    World.register(party.getFireWallNpcs().get(i));
                                }
                                for (int i = 9; i < 18; i++) {
                                    World.register(party.getFireWallNpcs().get(i));
                                }
                                stop();
                            }
                        });

                    }

                }
                if (tick == 7) {
                    Position pos = party.getFireWallPlayer().getPosition();
                    if (party.getFireWallPlayer().getMinigameAttributes().getRaidsAttributes().isInsideRaid()) {

                        int x = 3229;
                        for (int i = 0; i < 9; i++) {
                            Position position = new Position(x++, pos.getY() - 1, height);
                            party.getFireWallNpcs().add(NPC.of(27558, position));
                            World.register(party.getFireWallNpcs().get(i));
                        }
                        x = 3229;
                        for (int i = 9; i < 18; i++) {
                            Position position = new Position(x++, pos.getY() + 1, height);
                            party.getFireWallNpcs().add(NPC.of(27558, position));
                            World.register(party.getFireWallNpcs().get(i));
                        }

                    }
                }

                if (tick == 15) {
                    for (Player member : party.getPlayers()) {
                        if (member != null && member.getMinigameAttributes().getRaidsAttributes().isInsideRaid()) {
                            if (member.getPosition().getY() == party.getFireWallPlayer().getPosition().getY())
                                Attacks.hitPlayer(member, 400, 600, CombatType.MAGIC, CombatIcon.MAGIC, 0, false);
                        }
                    }
                }

                if (tick == 20) {
                    for (int i = 0; i < 18; i++) {
                        World.deregister(party.getFireWallNpcs().get(i));
                    }
                    World.deregister(party.getFireWallSpawn());
                    World.deregister(party.getFireWallSpawn1());
                    party.getFireWallNpcs().clear();
                    stop();
                }
                tick++;
            }
        });

    }

}
