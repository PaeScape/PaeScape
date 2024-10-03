package com.arlania.world.content;

import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.Animation;
import com.arlania.util.RandomUtility;
import com.arlania.world.World;
import com.arlania.world.entity.impl.player.Player;


public class KeysEvent {

    //Item ids that will be dropped
    public static int pvmKey = 1543;

    //useless, just needed to write down object id
    public static int chest = 6420;

    public static int[] rareLoots = {15606, 15608, 15610, 15612, 15614, 15616, 15618, 15620, 15622, 7671, 7673, 10840, 989, 989, 989, 989, 19335, 6739, 15259, 11720, 11722, 11724, 11726, 18782, 18782, 18782, 18782,};

    public static int[] ultraLoots = {9470, 4084, 4084, 9921, 9922, 9923, 9924, 9925, 15352, 5607, 4565, 10330, 10332, 10334, 10336, 10338, 10340, 10342, 10344, 10346, 10348, 10350, 10352};

    public static int[] amazingLoots = {1038, 1040, 1042, 1044, 1046, 1048, 1050, 1051, 10735, 14052, 14049, 14051, 14046, 14045, 14048, 14047,};

    public static int[] commonLoots = {4151, 6585, 11732, 536, 2577, 2581, 6328, 6916, 6918, 6920, 6922, 6924, 4724, 4726, 4728, 4730, 4732, 4734, 4736, 4738, 4753, 4755, 4757, 4759, 4747, 4749, 4745, 4751, 10025, 6570};
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
		/*if(!p.getClickDelay().elapsed(1000)) 
			return;*/
        if (player.getInventory().contains(1543)) {

            TaskManager.submit(new Task(2, player, false) {
                @Override
                public void execute() {
                    player.performAnimation(new Animation(6387));
                    player.getPacketSender().sendMessage("Opening Chest...");
                    player.getInventory().delete(1543, 1);
                    giveReward(player);
                    this.stop();
                }
            });
        } else {

            player.getPacketSender().sendMessage("You require a Red Key to open this chest!");
        }

    }

    public static void giveReward(Player player) {
        if (RandomUtility.inclusiveRandom(20) == 5) { //Rare Item
            int rareDrops = getRandomItem(rareLoots);
            player.getInventory().add(rareDrops, 1);
            World.sendMessage("drops", "@or3@[Red Key Chest]@bla@ " + player.getUsername() + " has recieved a Rare from the chest!");
        } else if (RandomUtility.inclusiveRandom(225) == 147) {//Ultra Rare items
            World.sendMessage("drops", "@or3@[Red Key Chest]@bla@ " + player.getUsername() + " has recieved an Ultra Rare from the chest!");
            int ultraDrops = getRandomItem(ultraLoots);
            player.getInventory().add(ultraDrops, 1);
        } else if (RandomUtility.inclusiveRandom(400) == 388) {//Amazing items
            World.sendMessage("drops", "@or3@[Red Key Chest]@bla@ " + player.getUsername() + " has recieved a Legendary Reward from the chest!!");
            int amazingDrops = getRandomItem(amazingLoots);
            player.getInventory().add(amazingDrops, 1);
        } else {//Common items
            int commonDrops = getRandomItem(commonLoots);
            player.getInventory().add(commonDrops, 1);
        }

    }

}