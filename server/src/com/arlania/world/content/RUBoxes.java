package com.arlania.world.content;

import com.arlania.util.RandomUtility;
import com.arlania.world.entity.impl.player.Player;

public class RUBoxes {


    public static final int[] commonRewards = {14484, 9813, 15019, 15220, 15018, 15020, 15501, 18782, 18782, 20000, 20001, 20002, 13263, 18337, 4151, 6585, 2572, 6199, 14000, 14001, 14002, 14003, 14004, 14005, 14006, 14007, 10836, 10837, 10838, 10839, 18744, 18745, 18746};
    public static final int[] uncommonRewards = {18893, 19040, 19041, 19042, 19043, 19022, 11718, 11720, 11722, 11724, 11726, 20012, 20010, 20011, 20019, 20020, 20016, 20017, 20018, 20021, 20022, 15501, 4151, 6585, 2572, 6199, 14000, 14001, 14002, 14003, 14004, 14005, 14006, 14007, 10836, 10837, 10838, 10839, 18744, 18745, 18746};
    public static final int[] rareRewards = {9813, 6666, 14008, 14009, 14010, 14011, 14012, 14013, 14014, 14015, 14016, 13740, 13742, 13744, 13738, 15501, 7671, 7673, 19311, 19308, 19314, 4151, 6585, 2572, 6199, 9470, 14000, 14001, 14002, 14003, 14004, 14005, 14006, 14007, 10836, 10837, 10838, 10839};
    public static final int[] extremeRewards = {17291, 12601, 15501, 19317, 19320, 4450, 4452, 4448, 4452, 4453, 4454, 4151, 6585, 2572, 6199, 4151, 6585, 2572, 6199, 9470, 14000, 14001, 14002, 14003, 14004, 14005, 14006, 14007, 19317, 19320, 19308, 19314, 10836, 10837, 10838, 10839, 19747};
    public static final int[] legendaryRewards = {19022, 19016, 19017, 19018, 19024, 19025, 19026, 19027, 19037, 15501, 4151, 6585, 2572, 6199, 4151, 6585, 2572, 6199, 9470, 14000, 14001, 14002, 14003, 14004, 14005, 14006, 14007, 19317, 19320, 19308, 19314};

    public static void openCommonBox(Player player) {
        if (player.getInventory().getFreeSlots() < 3) {
            player.getPacketSender().sendMessage("You need at least 3 inventory spaces");
            return;
        }
        player.getInventory().delete(15369, 1);
        commonRReward(player);
    }

    public static void openUncommonBox(Player player) {
        if (player.getInventory().getFreeSlots() < 3) {
            player.getPacketSender().sendMessage("You need at least 3 inventory spaces");
            return;
        }
        player.getInventory().delete(15370, 1);
        uncommonRReward(player);
    }

    public static void openRareBox(Player player) {
        if (player.getInventory().getFreeSlots() < 3) {
            player.getPacketSender().sendMessage("You need at least 3 inventory spaces");
            return;
        }
        player.getInventory().delete(15371, 1);
        rareRReward(player);
    }

    public static void openExtremeBox(Player player) {
        if (player.getInventory().getFreeSlots() < 3) {
            player.getPacketSender().sendMessage("You need at least 3 inventory spaces");
            return;
        }
        player.getInventory().delete(15372, 1);
        extremeRReward(player);
    }

    public static void openLegendaryBox(Player player) {
        if (player.getInventory().getFreeSlots() < 3) {
            player.getPacketSender().sendMessage("You need at least 3 inventory spaces");
            return;
        }
        player.getInventory().delete(15373, 1);
        legendaryRReward(player);
    }


    public static void commonRReward(Player player) {
        if (RandomUtility.RANDOM.nextInt(4) == 1) {
            player.getPacketSender().sendMessage("Your box has disappeared! Sorry!");
        } else {
            player.getInventory().add(commonRewards[RandomUtility.inclusiveRandom(commonRewards.length - 1)], 1);
            player.getInventory().add(995, 1000000 + RandomUtility.RANDOM.nextInt(5000000));
        }
    }

    public static void uncommonRReward(Player player) {
        if (RandomUtility.RANDOM.nextInt(5) == 1) {
            player.getPacketSender().sendMessage("Your box has disappeared! Sorry!");
        } else {
            player.getInventory().add(uncommonRewards[RandomUtility.inclusiveRandom(uncommonRewards.length - 1)], 1);
            player.getInventory().add(995, 2500000 + RandomUtility.RANDOM.nextInt(7850000));
        }
    }

    public static void rareRReward(Player player) {
        if (RandomUtility.RANDOM.nextInt(6) == 1) {
            player.getPacketSender().sendMessage("Your box has disappeared! Sorry!");
        } else {
            player.getInventory().add(rareRewards[RandomUtility.inclusiveRandom(rareRewards.length - 1)], 1);
            player.getInventory().add(995, 5000000 + RandomUtility.RANDOM.nextInt(10000000));
        }
    }

    public static void extremeRReward(Player player) {
        if (RandomUtility.RANDOM.nextInt(8) == 1) {
            player.getPacketSender().sendMessage("Your box has disappeared! Sorry!");
        } else {
            player.getInventory().add(extremeRewards[RandomUtility.inclusiveRandom(extremeRewards.length - 1)], 1);
            player.getInventory().add(995, 12500000 + RandomUtility.RANDOM.nextInt(25000000));
        }
    }

    public static void legendaryRReward(Player player) {
        if (RandomUtility.RANDOM.nextInt(10) == 1) {
            player.getPacketSender().sendMessage("Your box has disappeared! Sorry!");
        } else {
            player.getInventory().add(legendaryRewards[RandomUtility.inclusiveRandom(legendaryRewards.length - 1)], 1);
            player.getInventory().add(995, 25000000 + RandomUtility.RANDOM.nextInt(27500000));
        }
    }


}
