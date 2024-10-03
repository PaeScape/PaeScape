package com.arlania.world.content;

import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.*;
import com.arlania.util.RandomUtility;
import com.arlania.world.World;
import com.arlania.world.content.transportation.TeleportHandler;
import com.arlania.world.entity.impl.GroundItemManager;
import com.arlania.world.entity.impl.player.Player;

/*
 * Halloween event for 2016
 * @author Arlania www.Arlania.com
 *
 */

public class TrioBosses {

    //Npc ids that will drop keys
    public static int skeletonId = 1973;
    public static int zombieId = 75;
    public static int ghostId = 103;

    //Item ids that will be dropped
    public static int skeletonKey = 605;
    public static int ghostKey = 993;
    public static int zombieKey = 1590;

    //useless, just needed to write down obejct id
    public static int chest = 2079;

    //arrays that hold the rare and common chest loots
    public static int[] rareLoots = {10614};

    public static int[] commonLoots = {1959, 1959, 1959, 1959, 1959, 1959, 1959, 1959, 526, 526, 2947, 946, 2436, 2440, 1381, 1478,
            1704, 1540, 5297, 299, 1121, 3026, 15272, 18016, 373, 1283, 379, 1213, 1213, 2, 2, 3751, 3751, 4621, 2621, 215, 4675,
            4675, 2412, 2412, 4115, 4115, 4107, 4107, 4131, 4131, 11126, 11126, 1333, 1333, 1333, 9185, 9185, 892, 391, 391, 6685, 6685};

    /*
     * Start methods below
     */


    /*
     * Grabs a random item from aray
     */
    public static int getRandomItem(int[] array) {
        return array[RandomUtility.inclusiveRandom(array.length - 1)];
    }


    /*
     * Opening the chest with suspense, give reward
     */
    public static void openChest(Player player) {
        if (player.getInventory().contains(605) && player.getInventory().contains(993) && player.getInventory().contains(1590)) {

            TaskManager.submit(new Task(2, player, false) {
                @Override
                public void execute() {
                    player.performAnimation(new Animation(6387));
                    player.getPacketSender().sendMessage("Opening Chest...Happy Halloween!");
                    giveReward(player);
                    this.stop();
                }
            });
        } else {
            /*
             * Player does not have all the keys
             */
            player.getPacketSender().sendMessage("You need all the keys to open the chest!");
        }

    }

    /*
     * Eating Pumpkins action (fun useless thing)
     */
    public static void eatPumpkin(Player player) {
        player.getInventory().delete(1959, 1);
        player.performAnimation(new Animation(865));
        player.performGraphic(new Graphic(199, GraphicHeight.HIGH));
        player.forceChat("Happy Halloween everyone from " + player.getUsername());
    }

    /*
     * Handles teleporting into the event area
     */
    public static void teleIn(Player player) {
        TeleportHandler.teleportPlayer(player, new Position(2573, 9866), player.getSpellbook().getTeleportType());
        player.getPacketSender().sendMessage("Good luck at the @or3@Halloween Event!");
    }

    /*
     * Gives loot from chest
     */
    public static void giveReward(Player player) {
        World.sendMessage("drops", "@or3@[Halloween Event]@bla@ " + player.getUsername() + " has opened a chest!");
        if (RandomUtility.inclusiveRandom(20) == 5) {
            /*
             * Give a rare Loot
             */
            int rareDrops = getRandomItem(rareLoots);
            player.getInventory().add(rareDrops, 1);
            World.sendMessage("drops", "@or3@[Halloween Event]@bla@ " + player.getUsername() + " has recieved a rare holiday item!");

        } else {
            /*
             * Give Common Loot
             */
            int commonDrops = getRandomItem(commonLoots);
            player.getInventory().add(commonDrops, 1);
        }

    }

    /*
     * Handles the skeleton npc drops
     */
    public static void handleSkeleton(Player player, Position pos) {
        if (RandomUtility.inclusiveRandom(50) == 25) {
            GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(skeletonKey), pos, player.getUsername(), false, 150, true, 200));
            player.getPacketSender().sendMessage("@red@You have recieved a key!");
        }
        GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(526), pos, player.getUsername(), false, 150, true, 200));
    }

    /*
     * Handles the ghost npc drops
     */
    public static void handleGhost(Player player, Position pos) {
        if (RandomUtility.inclusiveRandom(50) == 25) {
            GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(ghostKey), pos, player.getUsername(), false, 150, true, 200));
            player.getPacketSender().sendMessage("@red@You have recieved a key!");
        }
        GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(526), pos, player.getUsername(), false, 150, true, 200));
    }

    /*
     * Handles the ghost npc drops
     */
    public static void handleZombie(Player player, Position pos) {
        if (RandomUtility.inclusiveRandom(50) == 25) {
            GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(zombieKey), pos, player.getUsername(), false, 150, true, 200));
            player.getPacketSender().sendMessage("@red@You have recieved a key!");
        }
        GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(526), pos, player.getUsername(), false, 150, true, 200));
    }

}
