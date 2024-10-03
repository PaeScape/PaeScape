package com.arlania.world.content;

import com.arlania.GameSettings;
import com.arlania.model.GameMode;
import com.arlania.model.Item;
import com.arlania.model.container.impl.Bank;
import com.arlania.model.definitions.ItemDefinition;
import com.arlania.util.RandomUtility;
import com.arlania.world.World;
import com.arlania.world.content.achievements.AchievementData;
import com.arlania.world.content.clog.CollectionLog;
import com.arlania.world.content.clog.CollectionLog.CustomCollection;
import com.arlania.world.entity.impl.player.Player;

public class ClueScrolls {

    public static int CluesCompleted;
    public static String currentHint;

    public static int easyClue = 19833 + GameSettings.OSRS_ITEMS_OFFSET;
    public static int mediumClue = 12021 + GameSettings.OSRS_ITEMS_OFFSET;
    public static int hardClue = 12542 + GameSettings.OSRS_ITEMS_OFFSET;
    public static int eliteClue = 19782 + GameSettings.OSRS_ITEMS_OFFSET;
    public static int masterClue = 19835 + GameSettings.OSRS_ITEMS_OFFSET;


    public static final int[] ACTIVE_CLUES = {easyClue, mediumClue, hardClue, eliteClue, masterClue};
    // digging locations or show reward on reading clue??
    // to-do change name of clue scrolls in item def


    public static final int[] GENERIC_UNIQUES = { //god books, beanie, beret
            220164, 220205, 220208, 220211, 220214, 220217, 220220, 220223, 220226, 220229, 220232, 220235,
            212596, 212245, 212247, 212249, 212251, 223246, 223249,
            219991, 219994, 219997,
            10362, 10364, 10366,
            2633, 2635, 2637, 2639, 2641, 2643, 2645, 2647, 2649, 2651, 2577, 2579,
            3827, 3828, 3829, 3830, 3831, 3832, 3833, 3834, 3835, 3836, 3837, 3838,
            19600, 19601, 19602, 19603, 19604, 19605, 19606, 19607, 19608, 19609, 19610, 19611
    };

    public static final int[] EASY_UNIQUES = { //vestment robes, trimmed bronze, trimmed iron
            212193, 212195, 212197, 212199, 212201, 212203, 212205, 212207, 212209, 212211, 212213, 212215, 212217, 212219, 212221,
            212223, 212225, 212227, 212229, 212231, 212233, 212235, 212237, 212239, 212241, 212243,
            220166, 220169, 220172, 220175, 220178, 220181, 220184, 220187, 220190, 220193, 220196, 220199, 220202,
            220240, 220243, 223354,
            2583, 2585, 2587, 2589, 2591, 2593, 2595, 2597,
            19167, 19169, 19171, 19173, 19175, 19177, 19179, 19182, 19185, 19188, 19190, 19192, 19194, 19196, 19198, 19200, 19203, 19206, 19209, 19211, 19213,
            19215, 19217, 19219, 19221, 19224, 19227, 19230, 19232, 19234, 19236, 19238, 19240, 19242, 19245, 19248, 19251, 19253, 19255, 19257, 19259, 19261,
            19263, 19266, 19269, 19323, 19325, 19327, 19329, 19331,
            19362, 19364, 19366, 19368, 19370, 19372, 19374, 19376, 19378, 19380, 19382, 19384, 19386, 19388, 19390, 19392, 19394, 19396,
            3472, 3473
            //23303, 23306, 23309, 23312, 23315, 23318, 23321, 23324, 23327, 23354, 23360, 23363, 23381, 23384, 23413,

    };

    public static final int[] MEDIUM_UNIQUES = {
            223407, 223410, 220266, 220269, 220272, 212598, 223413,
            212277, 212279, 212281, 212283, 212285, 212287, 212289, 212291, 212293, 212295, 212297, 212299, 212301, 212303, 212305, 212307, 212309, 212311,
            212313, 212315, 212317, 212319,
            212321, 212323, 212325, 212339, 212341, 212343, 212345, 212347, 212349, 212361,
            2599, 2601, 2603, 2605, 2607, 2609, 2611, 2613,
            10392, 10394, 10396, 10398, 10400, 10402, 10404, 10406, 10408, 10410, 10412, 10414, 10416, 10418, 10420, 10422, 10424, 10426, 10428, 10430, 10432, 10434,
            10436, 10438, 10440, 10442, 10444, 10446, 10448, 10450, 10452, 10454, 10456, 10458, 10460, 10462, 10464, 10466, 10468, 10470, 10472, 10474,
            7319, 7321, 7323, 7325, 7327, 9470,
            19272, 19275, 19278, 19281, 19284, 19287, 19290, 19293, 19296, 19299, 19302, 19305, 3474, 3475

    };

    public static final int[] HARD_UNIQUES = {
            220110, 220113, 220116, 220119, 220122, 220125, 220128, 220131, 220134, 220137, 220140,
            212327, 212329, 212331, 212333,
            220020, 220023, 220026, 220029, 220032,
            10368, 10370, 10372, 10374, 10376, 10378, 10380, 10382, 10384, 10386, 10388, 10390, //guth, zam, sar d'hides
            212490, 212492, 212494, 212496, 212498, 212500, 212502, 212504, 212506, 212508, 212510, 212512, //arma, anc, band d'hides
            223188, 223191, 223194, 223197, 223200, 223203,
            2615, 2617, 2619, 2621, 2623, 2625, 2627, 2629, 2581,
            2653, 2655, 2657, 2659, 2661, 2663, 2665, 2667, 2669, 2671, 2673, 2675,
            19398, 19401, 19404, 19407, 19410, 19413, 19416, 19419, 19422, 19425, 19428, 19431, 19434, 19437, 19440,
            3476, 3477, 3478, 3479, 3480, 3485
            //, 19443,19445, 19447, 19449, 19451, 19453, 19455, 19457, 19459, 19461, 19463, 19465

    };

    public static final int[] ELITE_UNIQUES = {
            220035, 220038, 220041, 220044, 220047, 220050, 220053, 220056, 220059, 220062, 220065, 220068, 220071, 220074,
            220077, 220080, 220083, 220086, 220089, 220092, 220095, 220098, 220101,
            220104, 220107, 220143, 220146, 220149, 220152, 220155, 220158, 220161,
            212363, 212365, 212367, 212369, 212371, 223357, 220005, 223348, 220017,
            212381, 212383, 212385, 212387, 212389, 212391, 223351, 222246,
            3481, 3483, 3486, 3488


    };

    public static final int[] MASTER_UNIQUES = { //3rd age
            220011, 220014, 223336, 223339, 223342, 223345, 212600, 223242,
            10330, 10332, 10334, 10336, 10338, 10340, 10342, 10344, 10346, 10348, 10350, 10352,
            212426, 212424, 212422, 212437, 19308, 19311, 19314, 19317, 19320

    };


    public static void addClueRewards(Player player, int scroll) {


        if (player.getInventory().getFreeSlots() < 6) {
            player.getPacketSender().sendMessage("You must have at least 6 free inventory spaces!");
            return;
        }

        player.getInventory().delete(scroll, 1);//Deletes Clue Scroll

        int[] loot = new int[5];

        if (scroll == easyClue) {

            player.getCollectionLog().addKill(CollectionLog.CustomCollection.EasyClues.getId());

            player.getAchievementTracker().progress(AchievementData.COMPLETE_25_EASY_CLUES, 1);

            if (player.getCollectionLog().getKills(CollectionLog.CustomCollection.EasyClues.getId()) >= 25)
                player.getAchievementTracker().progress(AchievementData.COMPLETE_25_EASY_CLUES, 25);

            for (int i = 0; i < 3; i++) {
                int random = 5;

                if (player.Lucky >= 1)
                    random = 4;

                int chance = RandomUtility.inclusiveRandom(random);

                if (chance == 1) {
                    int unique = RandomUtility.inclusiveRandom(10);


                    if ((unique != 1) && (unique != 2)) {
                        loot[i] = EASY_UNIQUES[RandomUtility.inclusiveRandom(EASY_UNIQUES.length - 1)];
                        player.getInventory().add(loot[i], 1);
                        player.getCollectionLog().handleDrop(CustomCollection.EasyClues.getId(), loot[i], 1);
                    } else {
                        loot[i] = GENERIC_UNIQUES[RandomUtility.inclusiveRandom(GENERIC_UNIQUES.length - 1)];
                        player.getInventory().add(loot[i], 1);
                        player.getCollectionLog().handleDrop(CustomCollection.GenericClues.getId(), loot[i], 1);
                    }
                } else {
                    loot[i] = addAlchable(player);

                    if (player.looterBanking && player.getGameMode() != GameMode.ULTIMATE_IRONMAN)
                        player.getInventory().switchItem(player.getBank(Bank.getTabForItem(player, loot[i])), new Item(loot[i], 1), player.getInventory().getSlot(loot[i]), false, true);

                }
            }

            int chance = 2000;

            if (player.Lucky >= 4)
                chance = 1800;
            else if (player.Lucky >= 2)
                chance = 1900;

            int master = RandomUtility.inclusiveRandom(chance);

            if (master == 1) {
                player.getInventory().add(masterClue, 1);
            }
        }

        if (scroll == mediumClue) {
            player.getCollectionLog().addKill(CollectionLog.CustomCollection.MediumClues.getId());

            player.getAchievementTracker().progress(AchievementData.COMPLETE_50_MEDIUM_CLUES, 1);

            if (player.getCollectionLog().getKills(CollectionLog.CustomCollection.MediumClues.getId()) >= 50)
                player.getAchievementTracker().progress(AchievementData.COMPLETE_50_MEDIUM_CLUES, 50);

            for (int i = 0; i < 4; i++) {
                int random = 5;

                if (player.Lucky >= 1)
                    random = 4;

                int chance = RandomUtility.inclusiveRandom(random);

                if (chance == 1) {
                    int unique = RandomUtility.inclusiveRandom(10);

                    if ((unique != 1) && (unique != 2)) {
                        loot[i] = MEDIUM_UNIQUES[RandomUtility.inclusiveRandom(MEDIUM_UNIQUES.length - 1)];
                        player.getInventory().add(loot[i], 1);
                        player.getCollectionLog().handleDrop(CustomCollection.MediumClues.getId(), loot[i], 1);
                    } else {
                        loot[i] = GENERIC_UNIQUES[RandomUtility.inclusiveRandom(GENERIC_UNIQUES.length - 1)];
                        player.getInventory().add(loot[i], 1);
                        player.getCollectionLog().handleDrop(CustomCollection.GenericClues.getId(), loot[i], 1);
                    }
                } else {
                    loot[i] = addAlchable(player);

                    //Looter Banking
                    if (player.looterBanking && player.getGameMode() != GameMode.ULTIMATE_IRONMAN)
                        player.getInventory().switchItem(player.getBank(Bank.getTabForItem(player, loot[i])), new Item(loot[i], 1), player.getInventory().getSlot(loot[i]), false, true);

                }
            }

            int chance = 500;

            if (player.Lucky >= 4)
                chance = 450;
            else if (player.Lucky >= 2)
                chance = 475;

            int master = RandomUtility.inclusiveRandom(chance);

            if (master == 1) {
                player.getInventory().add(masterClue, 1);
                String message = "@blu@[MASTER] " + player.getUsername() + " has just received a @red@MASTER CLUE SCROLL@blu@!";
                World.sendMessage("drops", message);
            }
        }

        if (scroll == hardClue) {
            player.getCollectionLog().addKill(CollectionLog.CustomCollection.HardClues.getId());

            player.getAchievementTracker().progress(AchievementData.COMPLETE_100_HARD_CLUES, 1);

            if (player.getCollectionLog().getKills(CollectionLog.CustomCollection.HardClues.getId()) >= 100)
                player.getAchievementTracker().progress(AchievementData.COMPLETE_100_HARD_CLUES, 100);

            for (int i = 0; i < 5; i++) {
                int random = 5;

                if (player.Lucky >= 1)
                    random = 4;

                int chance = RandomUtility.inclusiveRandom(random);

                if (chance == 1) {
                    int unique = RandomUtility.inclusiveRandom(10);

                    if ((unique != 1) && (unique != 2)) {
                        loot[i] = HARD_UNIQUES[RandomUtility.inclusiveRandom(HARD_UNIQUES.length - 1)];
                        player.getInventory().add(loot[i], 1);
                        player.getCollectionLog().handleDrop(CustomCollection.HardClues.getId(), loot[i], 1);
                    } else {
                        loot[i] = GENERIC_UNIQUES[RandomUtility.inclusiveRandom(GENERIC_UNIQUES.length - 1)];
                        player.getInventory().add(loot[i], 1);
                        player.getCollectionLog().handleDrop(CustomCollection.GenericClues.getId(), loot[i], 1);
                    }
                } else {
                    loot[i] = addAlchable(player);

                    //Looter Banking

                    if (player.looterBanking && player.getGameMode() != GameMode.ULTIMATE_IRONMAN)
                        player.getInventory().switchItem(player.getBank(Bank.getTabForItem(player, loot[i])), new Item(loot[i], 1), player.getInventory().getSlot(loot[i]), false, true);

                }

            }

            int chance = 200;

            if (player.Lucky >= 4)
                chance = 180;
            else if (player.Lucky >= 2)
                chance = 190;

            int master = RandomUtility.inclusiveRandom(chance);

            if (master == 1) {
                player.getInventory().add(masterClue, 1);
                String message = "@blu@[MASTER] " + player.getUsername() + " has just received a @red@MASTER CLUE SCROLL@blu@!";
                World.sendMessage("drops", message);
            }
        }

        if (scroll == eliteClue) {
            player.getCollectionLog().addKill(CollectionLog.CustomCollection.EliteClues.getId());

            player.getAchievementTracker().progress(AchievementData.COMPLETE_250_ELITE_CLUES, 1);

            if (player.getCollectionLog().getKills(CollectionLog.CustomCollection.EliteClues.getId()) >= 250)
                player.getAchievementTracker().progress(AchievementData.COMPLETE_250_ELITE_CLUES, 250);


            for (int i = 0; i < 5; i++) {
                int random = 5;

                if (player.Lucky >= 1)
                    random = 4;

                int chance = RandomUtility.inclusiveRandom(random);

                if (chance == 1) {
                    loot[i] = ELITE_UNIQUES[RandomUtility.inclusiveRandom(ELITE_UNIQUES.length - 1)];
                    player.getInventory().add(loot[i], 1);
                    player.getCollectionLog().handleDrop(CustomCollection.EliteClues.getId(), loot[i], 1);
                } else {
                    loot[i] = addAlchable(player);

                    //Looter Banking
                    if (player.looterBanking && player.getGameMode() != GameMode.ULTIMATE_IRONMAN)
                        player.getInventory().switchItem(player.getBank(Bank.getTabForItem(player, loot[i])), new Item(loot[i], 1), player.getInventory().getSlot(loot[i]), false, true);

                }
            }

            int chance = 100;

            if (player.Lucky >= 4)
                chance = 90;
            else if (player.Lucky >= 2)
                chance = 95;

            int master = RandomUtility.inclusiveRandom(chance);

            if (master == 1) {
                player.getInventory().add(masterClue, 1);
                String message = "@blu@[MASTER] " + player.getUsername() + " has just received a @red@MASTER CLUE SCROLL@blu@!";
                World.sendMessage("drops", message);
            }
        }
        if (scroll == masterClue) {
            player.getCollectionLog().addKill(CollectionLog.CustomCollection.MasterClues.getId());

            if (RandomUtility.inclusiveRandom(250) == 1) {
                player.getInventory().add(219730, 1);
                String message = "@blu@[PET] " + player.getUsername() + " has just received a @red@BLOODHOUND@blu@!";
                World.sendMessage("drops", message);
            }

            for (int i = 0; i < 5; i++) {
                int random = 5;

                if (player.Lucky >= 1)
                    random = 4;

                int chance = RandomUtility.inclusiveRandom(random);

                if (chance == 1) {
                    loot[i] = MASTER_UNIQUES[RandomUtility.inclusiveRandom(MASTER_UNIQUES.length - 1)];
                    player.getInventory().add(loot[i], 1);
                    Item lootId = new Item(loot[i]);
                    String itemName = lootId.getDefinition().getName();
                    player.getCollectionLog().handleDrop(CustomCollection.MasterClues.getId(), loot[i], 1);
                } else {
                    loot[i] = addAlchable(player);

                    //Looter Banking
                    if (player.looterBanking && player.getGameMode() != GameMode.ULTIMATE_IRONMAN)
                        player.getInventory().switchItem(player.getBank(Bank.getTabForItem(player, loot[i])), new Item(loot[i], 1), player.getInventory().getSlot(loot[i]), false, true);

                }
            }
        }

        //Clue Scroll reward interface
        player.getPacketSender().sendInterface(6960);





        if ((scroll == hardClue) || (scroll == eliteClue) || (scroll == masterClue)) {
            Item[] items = new Item[5];

            items[0] = new Item(loot[0]);
            items[1] = new Item(loot[1]);
            items[2] = new Item(loot[2]);
            items[3] = new Item(loot[3]);
            items[4] = new Item(loot[4]);

            player.getPacketSender().sendItemsOnInterface(6963, items);
        } else if (scroll == mediumClue) {
            Item[] items = new Item[4];

            items[0] = new Item(loot[0]);
            items[1] = new Item(loot[1]);
            items[2] = new Item(loot[2]);
            items[3] = new Item(loot[3]);

            player.getPacketSender().sendItemsOnInterface(6963, items);
        } else if (scroll == easyClue) {
            Item[] items = new Item[3];

            items[0] = new Item(loot[0]);
            items[1] = new Item(loot[1]);
            items[2] = new Item(loot[2]);

            player.getPacketSender().sendItemsOnInterface(6963, items);
        }


    }

    public static int addAlchable(Player player) {

        int loot = GameSettings.ALCHABLES[RandomUtility.inclusiveRandom(GameSettings.ALCHABLES.length - 1)];

        if(player.alchablecoins) {
            player.getInventory().add(995, (int) (ItemDefinition.forId(loot).getValue() * .6));
            return 995;
        }

        player.getInventory().add(loot, 1);
        return loot;
    }


    public static void setCluesCompleted(int CluesCompleted, boolean add) {
        if (add)
            CluesCompleted += CluesCompleted;
        else
            ClueScrolls.CluesCompleted = CluesCompleted;
    }

    public static void incrementCluesCompleted(double amount) {
        CluesCompleted += amount;
    }

    public static int getCluesCompleted() {
        return CluesCompleted;
    }

}
