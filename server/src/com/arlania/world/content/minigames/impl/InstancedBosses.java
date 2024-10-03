package com.arlania.world.content.minigames.impl;

import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.Locations.Location;
import com.arlania.model.Position;
import com.arlania.model.RegionInstance;
import com.arlania.model.RegionInstance.RegionInstanceType;
import com.arlania.world.World;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.Player;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Gabriel Hannason
 * Wrote this quickly!!
 * Handles the RFD quest
 */
public class InstancedBosses {

    private static final Set<Integer> BOSSES = new HashSet<>(Arrays.asList(2882, 2881, 2883, 50, 1999, 6203, 6222, 6247, 6260,
            8133, 13447, 5886, 8349, 7286, 2042, 2043, 2044, 1158,
            1159, 4540, 1580, 3334, 3340, 499, 7287, 2745, 21554,
            22359, 22360, 22388, 22340, 22374, 23425,
            2000, 2001, 2006, 2009, 3200, 3334, 8349, 22061));

    public static void enter(Player player) {

        player.getPacketSender().sendInterfaceRemoval();
        player.moveTo(new Position(2910, 4385, player.getIndex() * 4));
        //player.moveTo(new Position(1700, 9880, 0));
        player.setRegionInstance(new RegionInstance(player, RegionInstanceType.INSTANCEDBOSSES));

        player.minionSpawn1 = true;
        player.minionSpawn2 = true;
        player.minionSpawn3 = true;

        if (!player.instancedBosses) {
            player.instancedBosses = true;
            player.instanceSpawns = 0;
            player.instanceKC = 0;
            spawnWave(player, true);
        }
    }

    public static void spawnWave(final Player p, boolean firstWave) {
        if (p.getRegionInstance() == null)
            return;

        int respawnTime = p.getRespawnTime(false);

        if(p.inKQ){
            KalphiteQueenInstance.enter(p);
            return;
        }

        TaskManager.submit(new Task(respawnTime, p, false) {
            @Override
            public void execute() {
                if (p.getRegionInstance() == null) {
                    stop();
                    return;
                }

                if (p.instancedBosses && p.instanceSpawns == 0 && p.instanceKC < 250) {

                    p.instanceKC++;
                    int[] boss = {0, 0, 0};


                    if (p.inMole) {
                        boss[0] = 3340;
                        boss[1] = 0;
                        boss[2] = 0;
                        p.minionID1 = 0;
                        p.minionID2 = 0;
                        p.minionID3 = 0;
                    } else if (p.inKQ) {
                        boss[0] = 1158;
                        boss[1] = 0;
                        boss[2] = 0;
                        p.minionID1 = 0;
                        p.minionID2 = 0;
                        p.minionID3 = 0;
                    } else if (p.inDKS) {
                        boss[0] = 2881;
                        boss[1] = 2882;
                        boss[2] = 2883;
                        p.minionID1 = 0;
                        p.minionID2 = 0;
                        p.minionID3 = 0;
                    } else if (p.inSaradomin) {
                        boss[0] = 6247;
                        boss[1] = 0;
                        boss[2] = 0;
                        p.minionID1 = 6248;
                        p.minionID2 = 6250;
                        p.minionID3 = 6252;
                    } else if (p.inZamorak) {
                        boss[0] = 6203;
                        boss[1] = 0;
                        boss[2] = 0;
                        p.minionID1 = 6204;
                        p.minionID2 = 6206;
                        p.minionID3 = 6208;
                    } else if (p.inArmadyl) {
                        boss[0] = 6222;
                        boss[1] = 0;
                        boss[2] = 0;
                        p.minionID1 = 6223;
                        p.minionID2 = 6225;
                        p.minionID3 = 6227;
                    } else if (p.inBandos) {
                        boss[0] = 6260;
                        boss[1] = 0;
                        boss[2] = 0;
                        p.minionID1 = 6261;
                        p.minionID2 = 6263;
                        p.minionID3 = 6265;
                    } else if (p.inCerberus) {
                        boss[0] = 1999;
                        boss[1] = 0;
                        boss[2] = 0;
                        p.minionID1 = 0;
                        p.minionID2 = 0;
                        p.minionID3 = 0;
                    } else if (p.inSire) {
                        boss[0] = 5886;
                        boss[1] = 0;
                        boss[2] = 0;
                        p.minionID1 = 0;
                        p.minionID2 = 0;
                        p.minionID3 = 0;
                    } else if (p.inThermo) {
                        boss[0] = 499;
                        boss[1] = 0;
                        boss[2] = 0;
                        p.minionID1 = 0;
                        p.minionID2 = 0;
                        p.minionID3 = 0;
                    } else if (p.inCorporeal) {
                        boss[0] = 8133;
                        boss[1] = 0;
                        boss[2] = 0;
                        p.minionID1 = 0;
                        p.minionID2 = 0;
                        p.minionID3 = 0;
                    } else if (p.inKBD) {
                        boss[0] = 50;
                        boss[1] = 0;
                        boss[2] = 0;
                        p.minionID1 = 0;
                        p.minionID2 = 0;
                        p.minionID3 = 0;
                    }


                    if (boss[0] != 0) {
                        NPC n = new NPC(boss[0], new Position(spawnPos0.getX(), spawnPos0.getY(), p.getPosition().getZ())).setSpawnedFor(p);
                        p.instanceSpawns++;
                        World.register(n);

                        if (p.difficulty > 0)
                            n.difficultyPerks(n, p.difficulty);

                        p.getRegionInstance().getNpcsList().add(n);
                        n.getCombatBuilder().attack(p);
                    }
                    if (boss[1] != 0) {
                        NPC n = new NPC(boss[1], new Position(spawnPos1.getX(), spawnPos1.getY(), p.getPosition().getZ())).setSpawnedFor(p);
                        p.instanceSpawns++;
                        World.register(n);

                        if (p.difficulty > 0)
                            n.difficultyPerks(n, p.difficulty);

                        p.getRegionInstance().getNpcsList().add(n);
                        n.getCombatBuilder().attack(p);
                    }
                    if (boss[2] != 0) {
                        NPC n = new NPC(boss[2], new Position(spawnPos2.getX(), spawnPos2.getY(), p.getPosition().getZ())).setSpawnedFor(p);
                        p.instanceSpawns++;
                        World.register(n);

                        if (p.difficulty > 0)
                            n.difficultyPerks(n, p.difficulty);


                        p.getRegionInstance().getNpcsList().add(n);
                        n.getCombatBuilder().attack(p);
                    }

                    if (p.minionID1 != 0 && p.minionSpawn1) {
                        NPC n = new NPC(p.minionID1, new Position(minionSpawnPos0.getX(), minionSpawnPos0.getY(), p.getPosition().getZ())).setSpawnedFor(p);
                        if (!p.getRegionInstance().getNpcsList().contains(n)) {
                            World.register(n);

                            if (p.difficulty > 0)
                                n.difficultyPerks(n, p.difficulty);

                            p.getRegionInstance().getNpcsList().add(n);
                            n.getCombatBuilder().attack(p);
                            p.minionSpawn1 = false;
                        }
                    }

                    if (p.minionID2 != 0 && p.minionSpawn2) {
                        NPC n = new NPC(p.minionID2, new Position(minionSpawnPos1.getX(), minionSpawnPos1.getY(), p.getPosition().getZ())).setSpawnedFor(p);
                        if (!p.getRegionInstance().getNpcsList().contains(n)) {
                            World.register(n);

                            if (p.difficulty > 0)
                                n.difficultyPerks(n, p.difficulty);

                            p.getRegionInstance().getNpcsList().add(n);
                            n.getCombatBuilder().attack(p);
                            p.minionSpawn2 = false;
                        }
                    }

                    if (p.minionID3 != 0 && p.minionSpawn3) {
                        NPC n = new NPC(p.minionID3, new Position(minionSpawnPos2.getX(), minionSpawnPos2.getY(), p.getPosition().getZ())).setSpawnedFor(p);
                        if (!p.getRegionInstance().getNpcsList().contains(n)) {
                            World.register(n);

                            if (p.difficulty > 0)
                                n.difficultyPerks(n, p.difficulty);

                            p.getRegionInstance().getNpcsList().add(n);
                            n.getCombatBuilder().attack(p);
                            p.minionSpawn3 = false;
                        }
                    }
                }

                stop();
            }
        });
    }

    public static void handleNPCDeath(final Player player, NPC n) {
        if (player.getRegionInstance() != null)
            player.getRegionInstance().getNpcsList().remove(n);

        if (n.getLocation() != Location.INSTANCEDBOSSES)
            return;

        if (n.getId() == 23426 || n.getId() == 103)
            return;

        if (n.getId() == player.minionID1)
            player.minionSpawn1 = true;

        if (n.getId() == player.minionID2)
            player.minionSpawn2 = true;

        if (n.getId() == player.minionID3)
            player.minionSpawn3 = true;


        if (player.instancedBosses) {

            int delay = 3;
            boolean immediate = false;

            if(player.flashbackTime > 0) {
                delay = 0;
                immediate = true;
            }

            TaskManager.submit(new Task(delay, player, immediate) {
                @Override
                public void execute() {
                    if (n.getLocation() != Location.INSTANCEDBOSSES)
                        return;

                    if (BOSSES.contains(n.getId()))
                        player.instanceSpawns--;

                    if (player.instanceSpawns < 1)
                        spawnWave(player, false);

                    stop();
                }
            });
        }
    }


    private static final Position spawnPos0 = new Position(2910, 4386);
    private static final Position spawnPos1 = new Position(2905, 4390);
    private static final Position spawnPos2 = new Position(2915, 4390);
    private static final Position minionSpawnPos0 = new Position(2905, 4394);
    private static final Position minionSpawnPos1 = new Position(2915, 4394);
    private static final Position minionSpawnPos2 = new Position(2910, 4382);

}
