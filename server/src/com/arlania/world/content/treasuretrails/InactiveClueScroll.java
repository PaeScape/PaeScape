package com.arlania.world.content.treasuretrails;

import com.arlania.model.Item;
import com.arlania.util.RandomUtility;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.Player;

import java.util.ArrayList;


/**
 * Created by IntelliJ IDEA. User: levi Date: 11/14/16 Time: 17:17
 */
public class InactiveClueScroll {

    /*
     * level 3 scroll : I expect you to die! find clue : You've found another
     * clue!- You've been given a casket! - You found a casket! 6960 clue reward
     */

    /* the main clue scroll hint interface */

    public static final int CLUE_SCROLL_INTERFACE = 6965;

    public static final int CASKET_LV1 = 2724;
    public static final int CASKET_LV2 = 2726;
    public static final int CASKET_LV3 = 2728;

    public static final int REWARD_CASKET_LV1 = 2717;
    public static final int REWARD_CASKET_LV2 = 2714;
    public static final int REWARD_CASKET_LV3 = 2715;

    public static final int CLUE_ITEM = 2701;

    /* the puzzle class constants */

    public static final int PUZZLE_INTERFACE = 6976;

    public static final int PUZZLE_INTERFACE_CONTAINER = 6980;

    public static final int PUZZLE_INTERFACE_DEFAULT_CONTAINER = 6985;

    public static final int CASTLE_PUZZLE = 2800;
    public static final int TREE_PUZZLE = 3565;
    public static final int OGRE_PUZZLE = 3571;

    public static final int PUZZLE_LENGTH = 25;

    public static final int[] firstPuzzle = {2749, 2750, 2751, 2752, 2753,
            2754, 2755, 2756, 2757, 2758, 2759, 2760, 2761, 2762, 2763, 2764,
            2765, 2766, 2767, 2768, 2769, 2770, 2771, 2772, -1};

    public static final int[] secondPuzzle = {3619, 3620, 3621, 3622, 3623,
            3624, 3625, 3626, 3627, 3628, 3629, 3630, 3631, 3632, 3633, 3634,
            3635, 3636, 3637, 3638, 3639, 3640, 3641, 3642, -1};

    public static final int[] thirdPuzzle = {3643, 3644, 3645, 3646, 3647,
            3648, 3649, 3650, 3651, 3652, 3653, 3654, 3655, 3656, 3657, 3658,
            3659, 3660, 3661, 3662, 3663, 3664, 3665, 3666, -1};

    public static String[] levelOneClueNpc = {"Man", "Woman", "Goblin",
            "Mugger", "Barbarian", "Farmer", "Al-Kharid", "Thug", "Rock Crabs",
            "Rogue", "Thief", "H.A.M", "Banshees", "Cave Slime", "Afflicted",
            "Borrakar", "Freidar", "Freygerd", "Inga", "Jennella", "Lensa",
            "Lanzig"};

    public static String[] levelTwoClueNpc = {"Guard", "Tribesman",
            "Bandit Camp Humans", "Cockatrice", "Abyssal Leech", "Pyrefiend",
            "Harpie Bug Swarm", "Black Guard", "Rellekka Warriors",
            "Market Guard", "Jogre", "Ice Warrior", "Abyssal Guardian",
            "Paladin", "Vampire", "Dagannoth", "Giant Skeleton",
            "Abyssal Walker", "Dagannoth", "Wallasalki", "Mummy",
            "Giant Rock Crab"};

    public static String[] levelThreeClueNpc = {"Greater Demon",
            "Elf Warrior", "Tyras Guard", "Hellhound", "Dragon", "Dagannoth",
            "Turoth", "Jellie", "Aberrant Specter", "Gargoyle", "Nechryael",
            "Abyssal Demon"};

    // todo torn page make into mage books + firelighters + junk items to reward

    public static int[] mainJunk = {554, 555, 556, 557, 558, 559, 560, 561,
            562, 563, 564, 565, 566, 374, 380, 362, 1379, 1381, 1383, 1385,
            1387, 1065, 1099, 1135, 1097, 1169, 841, 843, 845, 847, 849};
    public static int[] junkItem1 = {1367, 1217, 1179, 1151, 1107, 1077, 1269,
            1089, 1125, 1165, 1195, 1283, 1297, 1313, 1327, 1341, 1367, 1426,
            334, 330, 851, 853, 855, 857, 859, 4821, 1765};
    public static int[] junkItem2 = {1430, 1371, 1345, 1331, 1317, 1301, 1287,
            1271, 1211, 1199, 1073, 1161, 1183, 1091, 1111, 1123, 1145, 1199,
            1681, 4823};
    public static int[] junkItem3 = {1432, 1373, 1347, 1333, 1319, 1303, 1289,
            1275, 1213, 1079, 1093, 1113, 1127, 1147, 1163, 1185, 1201, 4824,
            386, 2491, 2497, 2503};

    public static int[] levelOneRewards = {2583, 2585, 2587, 2589, 2591, 2593,
            2595, 2597, 3472, 3473, 2579, 2633, 2635, 2637, 2631, 7362, 7364,
            7366, 7368, 7386, 7388, 7390, 7392, 7394, 7396, 7329, 7330, 7331,
            7332, 7338, 7344, 7350, 7356, 3827, 3831, 3835, 3827, 3831, 3835,
            3827, 3831, 3835};

    public static int[] levelTwoRewards = {7329, 7330, 7331, 7319, 7321, 7323,
            7325, 7327, 7370, 7372, 7378, 7380, 2645, 2647, 2649, 2579, 2577,
            2599, 2601, 2603, 2605, 2607, 2609, 2611, 2613, 7334, 7340, 7346,
            7352, 7358, 3828, 3832, 3836, 3829, 3833, 3837, 3829, 3833, 3837,
            3829, 3833, 3837};

    public static int[] levelThreeRewards = {3480, 3481, 3483, 3486, 3488,
            2653, 2655, 2657, 2659, 2661, 2663, 2665, 2667, 2669, 2671, 2673,
            2675, 2581, 2651, 7398, 7399, 7400, 7329, 7330, 7331, 7374, 7376,
            7382, 7384, 2615, 2617, 2619, 2621, 2623, 2625, 2627, 2629, 7336,
            7342, 7348, 7354, 7360, 3830, 3834, 3838, 3830, 3834, 3838, 3830,
            3834, 3838, 2639, 2640, 2643};

    /* cleaning the actual clue interfaces(strings) */

    public static void cleanClueInterface(Player player) {
        for (int i = 6968; i <= 6975; i++) {
            player.getPacketSender().sendString(i, "");
        }
        if (player.getInventory().contains(3596)) { //Cheaphaxing the seers village clue interface
            player.getPacketSender().sendString(4268, "");
            player.getPacketSender().sendString(4269, "");
            player.getPacketSender().sendString(358, "");
        }
    }

    public static void clueReward(Player player, int clueLevel, String string,
                                  boolean isDialogue, String rewardString) {
        switch (clueLevel) {
            case 1:
                if (player.clue1Amount < 4 && RandomUtility.inclusiveRandom(3) == 0
                        || player.clue1Amount == 4) {
                    player.clue1Amount = 0;
                    if (isDialogue) {
                        itemReward(player, player.clueLevel);
                    } else {
                        itemReward(player, clueLevel);
                    }
                } else {
                    player.getPacketSender().sendMessage(string);
                    addNewClue(player, clueLevel);
                    player.clue1Amount++;
                }
                break;
            case 2:
                if (player.clue2Amount < 5 && RandomUtility.inclusiveRandom(4) == 0
                        || player.clue2Amount == 5) {
                    player.clue2Amount = 0;
                    if (isDialogue) {
                        itemReward(player, player.clueLevel);
                    } else {
                        itemReward(player, clueLevel);
                    }
                } else {
                    player.getPacketSender().sendMessage(string);
                    addNewClue(player, clueLevel);
                    player.clue2Amount++;
                }
                break;
            case 3:
                if (player.clue3Amount < 7 && RandomUtility.inclusiveRandom(6) == 0
                        || player.clue3Amount == 7) {
                    player.clue3Amount = 0;
                    if (isDialogue) {
                        itemReward(player, player.clueLevel);

                    } else {
                        itemReward(player, clueLevel);
                    }
                } else {
                    player.getPacketSender().sendMessage(string);
                    addNewClue(player, clueLevel);
                    player.clue3Amount++;
                }
                break;
        }
    }

    private static void addNewClue(Player player, int clueLevel) {
        player.getInventory().add(new Item(getRandomClue(clueLevel), 1));
    }

    public static void itemReward(Player player, int clueLevel) {
        ArrayList<Integer> array = new ArrayList<Integer>();
        int random = RandomUtility.inclusiveRandom(4) + 2;
        switch (clueLevel) {
            case 1:
                for (int i = 0; i < random; i++) {
                    int percent = RandomUtility.inclusiveRandom(100);
                    if (percent <= 7) {
                        array.add(levelOneRewards[RandomUtility
                                .inclusiveRandom(levelOneRewards.length - 1)]);
                    } else if (percent > 7 && percent <= 30 && !array.contains(995)) {
                        array.add(995);
                    } else {
                        array.add(RandomUtility.inclusiveRandom(2) == 1 ? junkItem1[RandomUtility
                                .inclusiveRandom(junkItem1.length - 1)] : mainJunk[RandomUtility
                                .inclusiveRandom(mainJunk.length - 1)]);
                    }
                }
                break;
            case 2:
                for (int i = 0; i < random; i++) {
                    int percent = RandomUtility.inclusiveRandom(100);
                    if (percent <= 7) {
                        array.add(levelTwoRewards[RandomUtility
                                .inclusiveRandom(levelTwoRewards.length - 1)]);
                    } else if (percent > 7 && percent <= 30 && !array.contains(995)) {
                        array.add(995);
                    } else {
                        array.add(RandomUtility.inclusiveRandom(2) == 1 ? junkItem2[RandomUtility
                                .inclusiveRandom(junkItem2.length - 1)] : mainJunk[RandomUtility
                                .inclusiveRandom(mainJunk.length - 1)]);
                    }

                }
                break;
            case 3:
                for (int i = 0; i < random; i++) {
                    int percent = RandomUtility.inclusiveRandom(100);
                    if (percent <= 7) {
                        array.add(levelThreeRewards[RandomUtility
                                .inclusiveRandom(levelThreeRewards.length - 1)]);
                    } else if (percent > 7 && percent <= 30 && !array.contains(995)) {
                        array.add(995);
                    } else {
                        array.add(RandomUtility.inclusiveRandom(2) == 1 ? junkItem3[RandomUtility
                                .inclusiveRandom(junkItem3.length - 1)] : mainJunk[RandomUtility
                                .inclusiveRandom(mainJunk.length - 1)]);
                    }
                }
                break;
        }

        int[] items = new int[random];
        int[] amounts = new int[random];
        Item[] item = new Item[random];
        for (int i = 0; i < random; i++) {
            items[i] = array.get(i);
            amounts[i] = 1;// new Item(items[i]).getDefinition().() ? items[i]
            // == 995 ? RandomUtility.inclusiveRandom(10000) : RandomUtility.inclusiveRandom(4) + 11
            // : 1;
            if (new Item(items[i]).getDefinition().getName().toLowerCase()
                    .contains("page")) {
                amounts[i] = 1;
            }
            item[i] = new Item(items[i], amounts[i]);
            player.getInventory().add(new Item(items[i], amounts[i]));
        }
        player.getPacketSender().sendInterfaceItems(6963, item);
        player.getPacketSender().sendInterface(6960);

        //Achievements.handleAchievement(player, Tasks.TASK75);
        //player.getPacketSender().sendMessage(
        //	"Well done, you've completed the Treasure Trail!");
    }

    public static void dropClue(Player player, NPC npc) {
		/*if(npc != null && npc.getPosition().getZ() == 0 && npc.getCombatAttributes().getSpawnedFor() == null) {
			int clue = RandomUtility.getRandom(25) <= 1 ? 1 : RandomUtility.getRandom(50) <= 1 ? 2 : RandomUtility.getRandom(100) <= 1 ? 3 : -1;
			if(clue >= 1 && !hasClue(player))
				GroundItemManager.add(new GroundItem(new Item(getRandomClue(clue)), npc.getPosition().copy(), player.getUsername(), false, 100, false, 60), true);
		}*/
    }

    public static int getRandomClue(int clueLevel) {
        ArrayList<Integer> array = new ArrayList<Integer>();
        switch (clueLevel) {
            case 1:
                array.add(MapScrolls.getRandomScroll(1));
                array.add(SearchScrolls.getRandomScroll(1));

                array.add(MapScrolls.getRandomScroll(1));
                array.add(SearchScrolls.getRandomScroll(1));

                array.add(DiggingScrolls.getRandomScroll(1));

                return array.get(RandomUtility.inclusiveRandom(array.size() - 1));

            case 2:
                array.add(MapScrolls.getRandomScroll(2));
                array.add(SearchScrolls.getRandomScroll(2));
                array.add(CoordinateScrolls.getRandomScroll(2));

                return array.get(RandomUtility.inclusiveRandom(array.size() - 1));

            case 3:
                array.add(MapScrolls.getRandomScroll(3));
                array.add(SearchScrolls.getRandomScroll(3));
                array.add(DiggingScrolls.getRandomScroll(3));
                array.add(CoordinateScrolls.getRandomScroll(3));

                return array.get(RandomUtility.inclusiveRandom(array.size() - 1));

        }

        return -1;
    }

    public static boolean handleCasket(Player player, int itemId) {
        switch (itemId) {
            case REWARD_CASKET_LV1:
                player.getInventory().delete(new Item(itemId, 1));
                itemReward(player, 1);
                return true;
            case REWARD_CASKET_LV2:
                player.getInventory().delete(new Item(itemId, 1));
                itemReward(player, 2);
                return true;
            case REWARD_CASKET_LV3:
                player.getInventory().delete(new Item(itemId, 1));
                itemReward(player, 3);
                return true;
            case CASKET_LV1:
                player.getInventory().delete(new Item(itemId, 1));
                clueReward(player, 1, "You've found another clue!", false,
                        "Here is your reward!");
                return true;
            case CASKET_LV2:
                player.getInventory().delete(new Item(itemId, 1));
                clueReward(player, 2, "You've found another clue!", false,
                        "Here is your reward!");
                return true;
            case CASKET_LV3:
                player.getInventory().delete(new Item(itemId, 1));
                clueReward(player, 3, "You've found another clue!", false,
                        "Here is your reward!");
                return true;
        }
        return false;
    }

    public static boolean hasClue(Player player) {
        boolean has = false;
        for (Item item : player.getInventory().getValidItems()) {
            if (item != null && item.getDefinition().getName().toLowerCase()
                    .contains("clue")) {
                has = true;
                break;
            }
        }
        for (Item item : player.getBank().array()) {
            if (item != null && item.getDefinition().getName().toLowerCase().contains("clue")) {
                has = true;
                break;
            }
        }
        return has;
    }

}
