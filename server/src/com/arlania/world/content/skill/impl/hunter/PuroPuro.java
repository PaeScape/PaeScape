package com.arlania.world.content.skill.impl.hunter;

import com.arlania.GameSettings;
import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.engine.task.impl.NPCRespawnTask;
import com.arlania.model.*;
import com.arlania.model.container.impl.Bank;
import com.arlania.util.RandomUtility;
import com.arlania.world.World;
import com.arlania.world.content.achievements.AchievementData;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.npc.NPCMovementCoordinator.Coordinator;
import com.arlania.world.entity.impl.player.Player;

public class PuroPuro {

    public static final int[][] implings = {
            /**
             * Baby imps
            */
            {6055, 2612, 4318},
            {6055, 2602, 4314},
            {6055, 2610, 4338},
            {6055, 2582, 4344},
            {6055, 2578, 4344},
            {6055, 2568, 4311},
            {6055, 2583, 4295},
            {6055, 2582, 4330},
            {6055, 2600, 4303},
            {6055, 2611, 4301},
            {6055, 2618, 4329},
            {6055, 2612, 4318},
            {6055, 2602, 4314},
            {6055, 2610, 4338},
            {6055, 2582, 4344},
            {6055, 2578, 4344},
            {6055, 2568, 4311},
            {6055, 2583, 4295},
            {6055, 2582, 4330},
            {6055, 2600, 4303},
            {6055, 2611, 4301},
            {6055, 2618, 4329},
            {6055, 2612, 4318},
            {6055, 2602, 4314},
            {6055, 2610, 4338},
            {6055, 2582, 4344},
            {6055, 2578, 4344},
            {6055, 2568, 4311},
            {6055, 2583, 4295},
            {6055, 2582, 4330},
            {6055, 2600, 4303},
            {6055, 2611, 4301},
            {6055, 2618, 4329},

            /**
             * Young imps
            */
            {6056, 2591, 4332},
            {6056, 2600, 4338},
            {6056, 2595, 4345},
            {6056, 2610, 4327},
            {6056, 2617, 4314},
            {6056, 2619, 4294},
            {6056, 2599, 4294},
            {6056, 2575, 4303},
            {6056, 2570, 4299},
            {6056, 2591, 4332},
            {6056, 2600, 4338},
            {6056, 2595, 4345},
            {6056, 2610, 4327},
            {6056, 2617, 4314},
            {6056, 2619, 4294},
            {6056, 2599, 4294},
            {6056, 2575, 4303},
            {6056, 2570, 4299},
            {6056, 2591, 4332},
            {6056, 2600, 4338},
            {6056, 2595, 4345},
            {6056, 2610, 4327},
            {6056, 2617, 4314},
            {6056, 2619, 4294},
            {6056, 2599, 4294},
            {6056, 2575, 4303},
            {6056, 2570, 4299},

            /**
             * Gourment imps
            */
            {6057, 2573, 4339},
            {6057, 2567, 4328},
            {6057, 2593, 4297},
            {6057, 2618, 4305},
            {6057, 2605, 4316},
            {6057, 2596, 4333},
            {6057, 2573, 4339},
            {6057, 2567, 4328},
            {6057, 2593, 4297},
            {6057, 2618, 4305},
            {6057, 2605, 4316},
            {6057, 2596, 4333},
            {6057, 2573, 4339},
            {6057, 2567, 4328},
            {6057, 2593, 4297},
            {6057, 2618, 4305},
            {6057, 2605, 4316},
            {6057, 2596, 4333},

            /**
             * Earth imps
            */
            {6058, 2592, 4338},
            {6058, 2611, 4345},
            {6058, 2617, 4339},
            {6058, 2614, 4301},
            {6058, 2606, 4295},
            {6058, 2581, 4299},
            {6058, 2592, 4338},
            {6058, 2611, 4345},
            {6058, 2617, 4339},
            {6058, 2614, 4301},
            {6058, 2606, 4295},
            {6058, 2581, 4299},
            {6058, 2592, 4338},
            {6058, 2611, 4345},
            {6058, 2617, 4339},
            {6058, 2614, 4301},
            {6058, 2606, 4295},
            {6058, 2581, 4299},

            /**
             * Essence imps
            */
            {6059, 2602, 4328},
            {6059, 2608, 4333},
            {6059, 2609, 4296},
            {6059, 2581, 4304},
            {6059, 2570, 4318},
            {6059, 2602, 4328},
            {6059, 2608, 4333},
            {6059, 2609, 4296},
            {6059, 2581, 4304},
            {6059, 2570, 4318},
            {6059, 2602, 4328},
            {6059, 2608, 4333},
            {6059, 2609, 4296},
            {6059, 2581, 4304},
            {6059, 2570, 4318},

            /**
             * Eclectic imps
            */
            {6060, 2611, 4310},
            {6060, 2617, 4319},
            {6060, 2600, 4347},
            {6060, 2570, 4326},
            {6060, 2579, 4310},
            {6060, 2611, 4310},
            {6060, 2617, 4319},
            {6060, 2600, 4347},
            {6060, 2570, 4326},
            {6060, 2579, 4310},
            {6060, 2611, 4310},
            {6060, 2617, 4319},
            {6060, 2600, 4347},
            {6060, 2570, 4326},
            {6060, 2579, 4310},

            /**
             * Spirit imps
            */

            /**
             * Nature imps
            */
            {6061, 2581, 4310},
            {6061, 2581, 4310},
            {6061, 2603, 4333},
            {6061, 2576, 4335},
            {6061, 2588, 4345},
            {6061, 2581, 4310},
            {6061, 2581, 4310},
            {6061, 2603, 4333},
            {6061, 2576, 4335},
            {6061, 2588, 4345},
            {6061, 2581, 4310},
            {6061, 2581, 4310},
            {6061, 2603, 4333},
            {6061, 2576, 4335},
            {6061, 2588, 4345},

            /**
             * Magpie imps
            */
            {6062, 2612, 4324},
            {6062, 2602, 4323},
            {6062, 2587, 4348},
            {6062, 2564, 4320},
            {6062, 2566, 4295},
            {6062, 2612, 4324},
            {6062, 2602, 4323},
            {6062, 2587, 4348},
            {6062, 2564, 4320},
            {6062, 2566, 4295},
            {6062, 2612, 4324},
            {6062, 2602, 4323},
            {6062, 2587, 4348},
            {6062, 2564, 4320},
            {6062, 2566, 4295},

            /**
             * Ninja imps
            */
            {6063, 2570, 4347},
            {6063, 2572, 4327},
            {6063, 2578, 4318},
            {6063, 2610, 4312},
            {6063, 2594, 4341},
            {6063, 2570, 4347},
            {6063, 2572, 4327},
            {6063, 2578, 4318},
            {6063, 2610, 4312},
            {6063, 2594, 4341},
            {6063, 2570, 4347},
            {6063, 2572, 4327},
            {6063, 2578, 4318},
            {6063, 2610, 4312},
            {6063, 2594, 4341},

            /**
             * Dragon imps
            */
            {6064, 2613, 4341},
            {6064, 2585, 4337},
            {6064, 2576, 4319},
            {6064, 2576, 4294},
            {6064, 2592, 4305},
            {6064, 2613, 4341},
            {6064, 2585, 4337},
            {6064, 2576, 4319},
            {6064, 2576, 4294},
            {6064, 2592, 4305},
            {6064, 2613, 4341},
            {6064, 2585, 4337},
            {6064, 2576, 4319},
            {6064, 2576, 4294},
            {6064, 2592, 4305},

    };

    public static void spawn() {


        for (int i = 0; i < implings.length; i++) {
            NPC n = new NPC(implings[i][0], new Position(implings[i][1] - 270, implings[i][2] - 750));
            n.getMovementCoordinator().setCoordinator(new Coordinator().setCoordinate(true).setRadius(4));
            World.register(n);
        }

        /**
         * Kingly imps
         * Randomly spawned
         */
        int random = RandomUtility.inclusiveRandom(6);
        Position pos = new Position(2326, 3601);
        switch (random) {
            case 1:
                pos = new Position(2350, 3598);
                break;
            case 2:
                pos = new Position(2357, 3551);
                break;
            case 3:
                pos = new Position(2318, 3539);
                break;
            case 4:
                pos = new Position(2306, 3555);
                break;
        }
        NPC n = new NPC(7903, pos);
        n.getMovementCoordinator().setCoordinator(new Coordinator().setCoordinate(true).setRadius(4));
        World.register(n);

    }

    /**
     * Catches an Impling
     *
     * @param player The player catching an Imp
     * @param npc    The NPC (Impling) to catch
     */
    public static void catchImpling(Player player, final NPC imp) {
        ImpData implingData = ImpData.forId(imp.getId());
        if (player.getInterfaceId() > 0 || player == null || imp == null || implingData == null || !imp.isRegistered() || !player.getClickDelay().elapsed(1000))
            return;
        if (player.getSkillManager().getCurrentLevel(Skill.HUNTER) < implingData.levelReq) {
            player.getPacketSender().sendMessage("You need a Hunter level of at least " + implingData.levelReq + " to catch this impling.");
            return;
        }
        if (!player.getInventory().contains(10010) && !player.getEquipment().contains(10010)) {
            player.getPacketSender().sendMessage("You do not have any net to catch this impling with.");
            return;
        }
        if (!player.getInventory().contains(11260)) {
            player.getPacketSender().sendMessage("You do not have any empty jars to hold this impling with.");
            return;
        }
        player.performAnimation(new Animation(6605));
        boolean success = player.getSkillManager().getCurrentLevel(Skill.HUNTER) <= 8 || RandomUtility.inclusiveRandom(player.getSkillManager().getCurrentLevel(Skill.HUNTER) / 2) > 1;
        if (success) {
            if (imp.isRegistered()) {
                World.deregister(imp);

                int respawnTimer = imp.getDefinition().getRespawnTime();

                respawnTimer /= 2;

                int addxp = implingData.XPReward;

                if (player.getEquipment().contains(10069))
                    addxp *= 1.25;

                if (implingData.npcId == player.getSkiller().getSkillerTask().getObjId()[0]) {
                    for (int k = 0; k < player.acceleratedResources(); k++) {
                        player.getSkiller().handleSkillerTaskGather(true);
                        player.getSkillManager().addExperience(Skill.SKILLER, player.getSkiller().getSkillerTask().getXP());
                    }
                }

                int maxJars = player.acceleratedResources();

                if (player.getInventory().getAmount(11260) < maxJars)
                    maxJars = player.getInventory().getAmount(11260);

                TaskManager.submit(new NPCRespawnTask(imp, respawnTimer));
                player.getInventory().delete(11260, maxJars);
                player.getInventory().add(implingData.impJar, maxJars);
                player.getPacketSender().sendMessage("You successfully catch the impling.");
                player.getSkillManager().addExperience(Skill.HUNTER, addxp * maxJars);
                if (implingData == ImpData.BABY) {
                    player.getAchievementTracker().progress(AchievementData.CATCH_10_BABY_IMPLINGS, player.acceleratedResources());
                } else if (implingData == ImpData.ESSENCE) {
                    player.getAchievementTracker().progress(AchievementData.CATCH_25_ESSENCE_IMPLINGS, player.acceleratedResources());
                } else if (implingData == ImpData.NATURE) {
                    player.getAchievementTracker().progress(AchievementData.CATCH_100_NATURE_IMPLINGS, player.acceleratedResources());
                } else if (implingData == ImpData.NINJA) {
                    player.getAchievementTracker().progress(AchievementData.CATCH_250_NINJA_IMPLINGS, player.acceleratedResources());
                } else if (implingData == ImpData.DRAGON) {
                    player.getAchievementTracker().progress(AchievementData.CATCH_500_DRAGON_IMPLINGS, player.acceleratedResources());
                } else if (implingData == ImpData.KINGLY) {
                    player.getAchievementTracker().progress(AchievementData.CATCH_1000_KINGLY_IMPLINGS, player.acceleratedResources());
                }

            }
        } else
            player.getPacketSender().sendMessage("You failed to catch the impling.");
        player.getClickDelay().reset();
    }

    /**
     * Handles pushing through walls in Puro puro
     *
     * @param player The player pushing a wall
     */
    public static void goThroughWheat(final Player player, GameObject object) {
        if (!player.getClickDelay().elapsed(800))
            return;
        player.getClickDelay().reset();
        int x = player.getPosition().getX(), x2 = x;
        int y = player.getPosition().getY(), y2 = y;
        if (x == 2584) {
            x2 = 2582;
        } else if (x == 2582) {
            x2 = 2584;
        } else if (x == 2599) {
            x2 = 2601;
        } else if (x == 2601) {
            x2 = 2599;
        }
        if (y == 4312) {
            y2 = 4310;
        } else if (y == 4310) {
            y2 = 4312;
        } else if (y == 4327) {
            y2 = 4329;
        } else if (y == 4329) {
            y2 = 4327;
        }
        x2 -= x;
        y2 -= y;
        player.getPacketSender().sendMessage("You use your strength to push through the wheat.");
        final int goX = x2, goY = y2;
        TaskManager.submit(new Task(1, player, false) {
            int tick = 0;

            @Override
            protected void execute() {
                if (tick == 1) {
                    player.setSkillAnimation(6594).setCrossingObstacle(true);
                    player.getUpdateFlag().flag(Flag.APPEARANCE);
                    player.getMovementQueue().walkStep(goX, goY);
                } else if (tick == 2)
                    stop();
                tick++;
            }

            @Override
            public void stop() {
                setEventRunning(false);
                player.setSkillAnimation(-1).setCrossingObstacle(false);
                player.getUpdateFlag().flag(Flag.APPEARANCE);
            }
        });
    }

    /**
     * Handles Impling Jars looting
     *
     * @param player The player looting the jar
     * @param itemId The jar the player is looting
     */
    public static void lootJar(final Player player, Item jar, JarData jarData) {
        if (player == null || jar == null || jarData == null || (!player.looterBanking && !player.getClickDelay().elapsed(20)))
            return;
        if (player.getInventory().getFreeSlots() < 2) {
            player.getPacketSender().sendMessage("You need at least 2 free inventory space to loot this.");
            return;
        }
        player.getInventory().delete(jar);
        player.getInventory().add(11260, 1);

        int[] lowSupplies = GameSettings.LOWSUPPLYDROPS;
        int[] highSupplies = GameSettings.HIGHSUPPLYDROPS;
        int clueChance = 0;

        int loot = 0;
        int amount = 0;

        switch (jar.getId()) {
            case 11238:
                loot = lowSupplies[RandomUtility.inclusiveRandom(lowSupplies.length - 1)];
                amount = RandomUtility.inclusiveRandom(5, 10);

                clueChance = 300;

                if (RandomUtility.inclusiveRandom(clueChance) == 1)
                    player.getInventory().add(213648, 1);
                break;
            case 11240:
                loot = lowSupplies[RandomUtility.inclusiveRandom(lowSupplies.length - 1)];
                amount = RandomUtility.inclusiveRandom(10, 15);
                clueChance = 250;

                if (RandomUtility.inclusiveRandom(clueChance) == 1)
                    player.getInventory().add(213648, 1);
                break;
            case 11242:
                loot = lowSupplies[RandomUtility.inclusiveRandom(lowSupplies.length - 1)];
                amount = RandomUtility.inclusiveRandom(15, 20);
                clueChance = 200;

                if (RandomUtility.inclusiveRandom(clueChance) == 1)
                    player.getInventory().add(213648, 1);
                break;
            case 11244:
                loot = lowSupplies[RandomUtility.inclusiveRandom(lowSupplies.length - 1)];
                amount = RandomUtility.inclusiveRandom(25, 30);

                clueChance = 300;

                if (RandomUtility.inclusiveRandom(clueChance) == 1)
                    player.getInventory().add(213649, 1);
                break;
            case 11246:
                loot = lowSupplies[RandomUtility.inclusiveRandom(lowSupplies.length - 1)];
                amount = RandomUtility.inclusiveRandom(30, 35);
                clueChance = 250;

                if (RandomUtility.inclusiveRandom(clueChance) == 1)
                    player.getInventory().add(213649, 1);
                break;
            case 11248:
                loot = lowSupplies[RandomUtility.inclusiveRandom(lowSupplies.length - 1)];
                amount = RandomUtility.inclusiveRandom(35, 40);
                clueChance = 200;

                if (RandomUtility.inclusiveRandom(clueChance) == 1)
                    player.getInventory().add(213649, 1);
                break;
            case 11250:
                loot = lowSupplies[RandomUtility.inclusiveRandom(lowSupplies.length - 1)];
                amount = RandomUtility.inclusiveRandom(40, 45);
                clueChance = 150;

                if (RandomUtility.inclusiveRandom(clueChance) == 1)
                    player.getInventory().add(213649, 1);
                break;
            case 11252:
                loot = lowSupplies[RandomUtility.inclusiveRandom(lowSupplies.length - 1)];
                amount = RandomUtility.inclusiveRandom(45, 50);
                clueChance = 300;

                if (RandomUtility.inclusiveRandom(clueChance) == 1)
                    player.getInventory().add(213650, 1);
                break;
            case 11254:
                loot = highSupplies[RandomUtility.inclusiveRandom(highSupplies.length - 1)];
                amount = RandomUtility.inclusiveRandom(4, 8);
                clueChance = 250;

                if (RandomUtility.inclusiveRandom(clueChance) == 1)
                    player.getInventory().add(213650, 1);
                break;
            case 11256:

                int randLoot = RandomUtility.inclusiveRandom(3);

                if (randLoot == 1) {
                    loot = 11232;
                    amount = 25;
                } else if (randLoot == 2) {
                    loot = 11237;
                    amount = 25;
                } else {
                    loot = highSupplies[RandomUtility.inclusiveRandom(highSupplies.length - 1)];
                    amount = RandomUtility.inclusiveRandom(8, 12);
                }
                clueChance = 200;

                if (RandomUtility.inclusiveRandom(clueChance) == 1)
                    player.getInventory().add(213650, 1);
                break;
            case 15517:
                loot = highSupplies[RandomUtility.inclusiveRandom(highSupplies.length - 1)];
                amount = RandomUtility.inclusiveRandom(12, 16);
                clueChance = 1000;

                if (RandomUtility.inclusiveRandom(clueChance) < 5)
                    player.getInventory().add(213651, 1);

                if (RandomUtility.inclusiveRandom(clueChance) == 5)
                    player.getInventory().add(15503, 1);

                if (RandomUtility.inclusiveRandom(clueChance) == 6)
                    player.getInventory().add(15505, 1);

                if (RandomUtility.inclusiveRandom(clueChance) == 7)
                    player.getInventory().add(15507, 1);

                if (RandomUtility.inclusiveRandom(clueChance) == 8)
                    player.getInventory().add(15509, 1);

                if (RandomUtility.inclusiveRandom(clueChance) == 9)
                    player.getInventory().add(15511, 1);

                break;
        }

        int[] lootData = {loot, amount};
        if (player.looterBanking && player.getGameMode() != GameMode.ULTIMATE_IRONMAN) {
            String jarName = jar.getDefinition().getName();
            player.getInventory().add(lootData[0], lootData[1]);
            Item loots = new Item(lootData[0]);
            player.getInventory().switchItem(
                    player.getBank(Bank.getTabForItem(player, lootData[0])),
                    new Item(lootData[0], lootData[1]),
                    player.getInventory().getSlot(lootData[0]),
                    false,
                    true
            );
            player.sendMessage("Your " + jarName + " contained " + lootData[1] + " " + loots.getDefinition().getName());
        } else {
            player.getInventory().add(lootData[0], lootData[1]);
        }
        player.getClickDelay().reset();
    }

}
