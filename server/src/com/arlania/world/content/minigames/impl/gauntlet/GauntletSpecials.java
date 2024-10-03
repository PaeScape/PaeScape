package com.arlania.world.content.minigames.impl.gauntlet;

import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.*;
import com.arlania.util.RandomUtility;
import com.arlania.world.World;
import com.arlania.world.clip.region.RegionClipping;
import com.arlania.world.content.CustomObjects;
import com.arlania.world.content.combat.prayer.CurseHandler;
import com.arlania.world.content.combat.prayer.PrayerHandler;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.Player;

public class GauntletSpecials {

    private static final int restedSpikes = 323549;
    private static final int raisedSpikes = 323550;
    private static final int healingFlame = 320001;

    public static void spikes(Player player) {

        //object 320000 (gray fire)
        //object 323549 and 323550 (spikes and raised spikes)

        int height = player.instanceHeight;

        TaskManager.submit(new Task(1, player, true) {
            int tick = 0;

            @Override
            public void execute() {

                //rested spikes
                if (tick == 5) {
                    player.getPacketSender().sendMessage("rested spikes");
                    for (int i = -1; i < 2; i++) {
                        for (int j = -1; j < 2; j++) {
                            Position pos = new Position(player.gauntletSpikes.getX() + i, player.gauntletSpikes.getY() + j, height);

                            if (!CustomObjects.objectExists(pos)) {
                                GameObject restedSpikesObj = new GameObject(restedSpikes, pos);
                                CustomObjects.spawnObject(player, restedSpikesObj);
                                RegionClipping.addObject(restedSpikesObj);
                            }
                        }
                    }
                }

                //raised spikes
                if (tick == 20) {
                    player.getPacketSender().sendMessage("raised spikes");
                    for (int i = -1; i < 2; i++) {
                        for (int j = -1; j < 2; j++) {
                            Position pos = new Position(player.gauntletSpikes.getX() + i, player.gauntletSpikes.getY() + j, height);

                            if (pos.getX() == 1873 || pos.getX() == 1886 || pos.getY() == 5649 || pos.getY() == 5662) {
                                continue;
                            }

                            GameObject restedSpikesObj = new GameObject(raisedSpikes, pos);
                            CustomObjects.deleteGlobalObject(restedSpikesObj);
                            RegionClipping.removeObject(restedSpikesObj);

                            GameObject raisedSpikesObj = new GameObject(raisedSpikes, pos);
                            CustomObjects.spawnObject(player, raisedSpikesObj);
                            RegionClipping.addObject(raisedSpikesObj);


                            if (player.getPosition().equals(pos) && CustomObjects.objectExists(pos) && CustomObjects.getGameObject(pos).getId() == raisedSpikes) {
                                player.getMovementQueue().freeze(2);
                                PrayerHandler.deactivateAll(player);
                                CurseHandler.deactivateAll(player);
                                player.sendMessage("You've been injured by the Spikes!");
                                player.sendMessage("Your Prayers have been deactivated!");
                                player.dealDamage(new Hit(RandomUtility.inclusiveRandom(250, 500), Hitmask.RED, CombatIcon.NONE));
                            }
                        }
                    }
                }

                //delete spikes
                if (tick == 25) {
                    player.getPacketSender().sendMessage("delete spikes");
                    for (int i = -1; i < 2; i++) {
                        for (int j = -1; j < 2; j++) {
                            Position pos = new Position(player.gauntletSpikes.getX() + i, player.gauntletSpikes.getY() + j, height);

                            if (pos.getX() == 1873 || pos.getX() == 1886 || pos.getY() == 5649 || pos.getY() == 5662) {
                                continue;
                            }

                            GameObject raisedSpikesObj = new GameObject(raisedSpikes, pos);
                            CustomObjects.deleteGlobalObject(raisedSpikesObj);
                            RegionClipping.removeObject(raisedSpikesObj);

                            GameObject blank = new GameObject(-1, raisedSpikesObj.getPosition());
                            player.getPacketSender().sendObject(blank);
                            CustomObjects.deleteGlobalObject(blank);
                            RegionClipping.removeObject(blank);

                        }
                    }
                }

                if (tick == 30) {
                    stop();
                }
                tick++;
            }
        });

    }

    public static void healingFlame(Player player) {

        //object 320001 (purple fire)

        int height = player.instanceHeight;

        TaskManager.submit(new Task(1, player, true) {
            int tick = 0;

            @Override
            public void execute() {

                if (tick == 5) {
                    player.getPacketSender().sendMessage("healing flames");
                    for (int i = 0; i < 2; i++) {
                        Position pos = new Position(1877, 5655 + i, height);
                        GameObject healingFlameObj1 = new GameObject(healingFlame, pos);
                        CustomObjects.spawnObject(player, healingFlameObj1);
                        RegionClipping.addObject(healingFlameObj1);

                        Position pos2 = new Position(1882, 5655 + i, height);
                        GameObject healingFlameObj2 = new GameObject(healingFlame, pos2);
                        CustomObjects.spawnObject(player, healingFlameObj2);
                        RegionClipping.addObject(healingFlameObj2);
                    }
                }
                if (tick == 30) {
                    player.getPacketSender().sendMessage("healing effect");

                    int heal = 0;

                    if (CustomObjects.objectExists(new Position(1877, 5655, height))) {
                        heal++;
                    }
                    if (CustomObjects.objectExists(new Position(1877, 5656, height))) {
                        heal++;
                    }
                    if (CustomObjects.objectExists(new Position(1882, 5655, height))) {
                        heal++;
                    }
                    if (CustomObjects.objectExists(new Position(1882, 5656, height))) {
                        heal++;
                    }

                    final int healQty = heal;

                    World.getNpcs().forEach(n -> healSpec(n, healQty));

                }

                if (tick == 35) {
                    player.getPacketSender().sendMessage("delete flames");
                    for (int i = 0; i < 2; i++) {
                        Position pos = new Position(1877, 5655 + i, height);
                        GameObject healingFlameObj1 = new GameObject(healingFlame, pos);
                        CustomObjects.deleteGlobalObject(healingFlameObj1);
                        player.getPacketSender().sendObject(new GameObject(-1, healingFlameObj1.getPosition()));

                        Position pos2 = new Position(1882, 5655 + i, height);
                        GameObject healingFlameObj2 = new GameObject(healingFlame, pos2);
                        CustomObjects.deleteGlobalObject(healingFlameObj2);
                        player.getPacketSender().sendObject(new GameObject(-1, healingFlameObj2.getPosition()));
                    }

                }

                if (tick == 40) {
                    stop();
                }
                tick++;
            }
        });

    }

    public static void healSpec(NPC n, int heal) {

        if (n.getId() != 23021)
            return;

        n.setConstitution(n.getConstitution() + (heal * 10000));

    }


}
