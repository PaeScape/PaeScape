package com.arlania.world.content.skill.impl.smithing;

import com.arlania.model.Item;
import com.arlania.model.Skill;
import com.arlania.util.RandomUtility;
import com.arlania.world.entity.impl.player.Player;

public class SmithingData {


    public static final int[] SMELT_BARS = {2349, 2351, 2355, 2353, 2357, 2359, 2361, 2363};
    public static final int[] SMITH_BARS = {2349, 2351, 2355, 2353, 2359, 2361, 2363};
    public static final int[] SMELT_FRAME = {2405, 2406, 2407, 2409, 2410, 2411, 2412, 2413};

    //BarId, Ore1, Ore2, Levelreq, XP
    public static final int[][] SmeltData = {
            {2349, 438, 436, 1, 10}, // Bronze bar
            {2351, 440, 440, 15, 15}, // Iron bar
            {2355, 442, 442, 20, 25}, // Silver bar
            {2353, 440, 453, 30, 40}, // Steel bar
            {2357, 444, 444, 40, 50}, // Gold bar
            {2359, 447, 453, 50, 75}, // Mithril bar
            {2361, 449, 453, 70, 100}, // Adamantite bar
            {2363, 451, 453, 85, 150} // Runite bar
    };

    /*
     * Gets the ores needed and stores them in to the array so we can use them.
     */
    public static int[] getOres(int bar) {
        int[] ores = new int[2];
        for (int i = 0; i < SmeltData.length; i++) {
            if (SmeltData[i][0] == bar) {
                int ore1 = SmeltData[i][1];
                int ore2 = SmeltData[i][2];
                ores[0] = ore1;
                ores[1] = ore2;
            }
        }
        return ores;
    }

    /*
     * Gets the Noted ores needed and stores them in to the array so we can use them.
     */

    public static int[] getNotedOres(int bar) {
        int[] ores = new int[2];
        for (int i = 0; i < SmeltData.length; i++) {
            if (SmeltData[i][0] == bar) {
                int ore1 = SmeltData[i][1];
                int ore2 = SmeltData[i][2];
                ores[0] = ore1 + 1;
                ores[1] = ore2 + 1;
            }
        }
        return ores;
    }

    /*
     * Checks if a player has ores required for a certain barId
     */
    public static boolean hasOres(Player player, int barId) {
        player.setOres(getOres(barId)); //Insert ores ids to the array

        if(player.checkAchievementAbilities(player, "processor")) {
            if(player.getInventory().contains(player.getOres()[0] + 1) && player.getInventory().contains(player.getOres()[1] + 1)) {
                player.setOres(getNotedOres(barId));
            }
        }


        if (player.getOres()[0] > 0 && player.getOres()[1] < 0) {
            return player.getInventory().getAmount(player.getOres()[0]) > 0;
        } else if (player.getOres()[1] > 0 && player.getOres()[1] != 453 && player.getOres()[0] > 0) {
            return player.getInventory().getAmount(player.getOres()[1]) > 0 && player.getInventory().getAmount(player.getOres()[0]) > 0;
        } else if (player.getInventory().contains(18339) && player.getOres()[1] == 453 && player.getOres()[0] > 0) {
            return player.getCoalBag() >= getCoalAmount(barId) && player.getInventory().getAmount(player.getOres()[0]) > 0;
        } else if (player.getOres()[1] > 0 && player.getOres()[1] == 453 && player.getOres()[0] > 0) {
            return player.getInventory().getAmount(player.getOres()[1]) >= getCoalAmount(barId) && player.getInventory().getAmount(player.getOres()[0]) > 0;
        }
        return false;

    }


    /*
     * Checks if a player has required stats to smelt certain barId
     */
    public static boolean canSmelt(Player player, int barId) {
        if (getLevelReq(barId) > player.getSkillManager().getCurrentLevel(Skill.SMITHING)) {
            player.getPacketSender().sendMessage("You need a Smithing level of at least " + getLevelReq(barId) + " to make this bar.");
            return false;
        }
        if (!hasOres(player, barId)) {
            player.getPacketSender().sendMessage("You do not have the required ores to make this bar.");
            String requirement = null;

            if (player.getOres()[0] > 0 && player.getOres()[1] > 0 && player.getOres()[1] != 453) {
                requirement = "To make " + anOrA(barId) + " " + new Item(barId).getDefinition().getName() + ", you need some " + new Item(player.getOres()[0]).getDefinition().getName().replace(" ore", "") + " and " + new Item(player.getOres()[1]).getDefinition().getName() + ".";
            } else if (player.getOres()[0] > 0 && player.getOres()[1] == -1) {
                requirement = "To make " + anOrA(barId) + " " + new Item(barId).getDefinition().getName() + ", you need some " + new Item(player.getOres()[0]).getDefinition().getName() + ".";
            } else if (player.getOres()[0] > 0 && player.getOres()[1] == 453) { //The bar uses custom coal amount
                requirement = "To make " + anOrA(barId) + " " + new Item(barId).getDefinition().getName() + ", you need some " + new Item(player.getOres()[0]).getDefinition().getName().replace(" ore", "") + " and " + getCoalAmount(barId) + " " + new Item(player.getOres()[1]).getDefinition().getName() + " ores.";
            }

            if (requirement != null)
                player.getPacketSender().sendMessage(requirement);

            return false;
        }
        return true;
    }

    /*
     * Gets the correct 'message'
     */
    public static String anOrA(int barId) {
        if (barId == 2351 || barId == 2361) // Iron and Adamantite bars
            return "an";
        return "a";
    }

    /*
     * Gets Smithing level required for a certain barId
     */
    public static int getLevelReq(int barId) {
        for (int i = 0; i < SmeltData.length; i++) {
            if (SmeltData[i][0] == barId) {
                return SmeltData[i][3];
            }
        }
        return 1;
    }

    /*
     * Handles the Smithing interfaces and their buttons
     */
    public static boolean handleButtons(Player player, int id) {
        switch (id) {
            /*
             * Bronze Smelting
             */
            case 3987:
                Smelting.smeltBar(player, 2349, 28);
                player.getPacketSender().sendInterfaceRemoval();
                return true;
            case 3986:
                Smelting.smeltBar(player, 2349, 5);
                player.getPacketSender().sendInterfaceRemoval();
                return true;
            case 2807:
                Smelting.smeltBar(player, 2349, 10);
                player.getPacketSender().sendInterfaceRemoval();
                return true;

            /*
             * Iron Smelting
             */
            case 3991:
                Smelting.smeltBar(player, 2351, 28);
                player.getPacketSender().sendInterfaceRemoval();
                return true;
            case 3990:
                Smelting.smeltBar(player, 2351, 5);
                player.getPacketSender().sendInterfaceRemoval();
                return true;
            case 3989:
                Smelting.smeltBar(player, 2351, 10);
                player.getPacketSender().sendInterfaceRemoval();
                return true;

            /*
             * Silver Smelting
             */
            case 3995:
                Smelting.smeltBar(player, 2355, 28);
                player.getPacketSender().sendInterfaceRemoval();
                return true;
            case 3994:
                Smelting.smeltBar(player, 2355, 5);
                player.getPacketSender().sendInterfaceRemoval();
                return true;
            case 3993:
                Smelting.smeltBar(player, 2355, 10);
                player.getPacketSender().sendInterfaceRemoval();
                return true;

            /*
             * Steel Smelting
             */
            case 3999:
                Smelting.smeltBar(player, 2353, 28);
                player.getPacketSender().sendInterfaceRemoval();
                return true;
            case 3998:
                Smelting.smeltBar(player, 2353, 5);
                player.getPacketSender().sendInterfaceRemoval();
                return true;
            case 3997:
                Smelting.smeltBar(player, 2353, 10);
                player.getPacketSender().sendInterfaceRemoval();
                return true;

            /*
             * Gold Smelting
             */
            case 4003:
                Smelting.smeltBar(player, 2357, 28);
                player.getPacketSender().sendInterfaceRemoval();
                return true;
            case 4002:
                Smelting.smeltBar(player, 2357, 5);
                player.getPacketSender().sendInterfaceRemoval();
                return true;
            case 4001:
                Smelting.smeltBar(player, 2357, 10);
                player.getPacketSender().sendInterfaceRemoval();
                return true;

            /*
             * Mithril Smelting
             */
            case 7441:
                Smelting.smeltBar(player, 2359, 28);
                player.getPacketSender().sendInterfaceRemoval();
                return true;
            case 7440:
                Smelting.smeltBar(player, 2359, 5);
                player.getPacketSender().sendInterfaceRemoval();
                return true;
            case 6397:
                Smelting.smeltBar(player, 2359, 10);
                player.getPacketSender().sendInterfaceRemoval();
                return true;

            /*
             * Adamant Smelting
             */
            case 7446:
                Smelting.smeltBar(player, 2361, 28);
                player.getPacketSender().sendInterfaceRemoval();
                return true;
            case 7444:
                Smelting.smeltBar(player, 2361, 5);
                player.getPacketSender().sendInterfaceRemoval();
                return true;
            case 7443:
                Smelting.smeltBar(player, 2361, 10);
                player.getPacketSender().sendInterfaceRemoval();
                return true;

            /*
             * Runite Smelting
             */
            case 7450:
                Smelting.smeltBar(player, 2363, 28);
                player.getPacketSender().sendInterfaceRemoval();
                return true;
            case 7449:
                Smelting.smeltBar(player, 2363, 5);
                player.getPacketSender().sendInterfaceRemoval();
                return true;
            case 7448:
                Smelting.smeltBar(player, 2363, 10);
                player.getPacketSender().sendInterfaceRemoval();
                return true;


            /*
             * Handle X
             */
            case 2415:
            case 3988:
            case 3992:
            case 3996:
            case 4000:
            case 4158:
            case 7442:
            case 7447:
                int bar = id == 2415 ? 2349 : id == 3988 ? 2351 : id == 3992 ? 2355 : id == 3996 ? 2353 : id == 4000 ? 2357 : id == 4158 ? 2359 : id == 7442 ? 2361 : id == 7447 ? 2363 : -1;
                if (bar > 0) {

                    Smelting.smeltBar(player, bar, 28);
                    //player.setInputHandling(new EnterAmountOfBarsToSmelt(bar));
                    //player.getPacketSender().sendEnterAmountPrompt("How many "+ItemDefinition.forId(bar).getName()+"s would you like to smelt?");
                }
                return true;
        }
        return false;
    }


    public static void handleSpecialBar(int barId, Player player, String barAction) {
        if (barAction.equalsIgnoreCase("message")) {

            return;
        }
        if (barAction.equalsIgnoreCase("delete")) {

        }
    }

    public static int getCoalAmount(int barId) {
        if (barId == 2359)
            return 4;
        if (barId == 2361)
            return 6;
        if (barId == 2363)
            return 8;
        return 2;

    }

    public static void showBronzeInterface(Player player) {
        String fiveb = GetForBars(2349, 5, player);
        String threeb = GetForBars(2349, 3, player);
        String twob = GetForBars(2349, 2, player);
        String oneb = GetForBars(2349, 1, player);

        int[] t1 = {1125, 1126, 1109, 1127, -1,
                1124, 1129, 1110, 1113, 1130,
                1116, 1118, 1111, -1, -1,
                1089, 1095, 1112, 1115, -1,
                1090, 8428, 11459, 13357, -1};

        int[] t2 = {1094, 1091, 1098, 1102, -1,
                1085, 1093, 1099, 1103, 1108,
                1087, 1083, 1100, -1, -1,
                1086, 1092, 1101, 1105, -1,
                1088, 8429, 11461, 13358, -1};

        String[] t3 = {"Dagger", "Scimitar", "Longsword", "Battleaxe", "empty",
                "Mace", "Warhammer", "Sq shield", "Kite shield", "2h",
                "Arrowtips", "Dart tips", "Chainbody", "empty", "empty",
                "Full helm", "Platebody", "Platelegs", "Plateskirt", "empty",
                "Med helm", "Claws", "Pickaxe", "Axe", "empty"};

        String[] t4 = {oneb, twob, twob, threeb, "",
                oneb, threeb, twob, threeb, threeb,
                oneb, oneb, threeb, "", "",
                twob, fiveb, threeb, threeb, "",
                oneb, twob, oneb, oneb, ""};

        String[] t5 = {"1 Bar", "2 Bars", "2 Bars", "3 Bars", "",
                "1 Bar", "3 Bars", "2 Bars", "3 Bars", "3 Bars",
                "1 Bar", "1 Bar", "3 Bars", "", "",
                "2 Bars", "5 Bars", "3 Bars", "3 Bars", "",
                "1 Bar", "2 Bars", "1 Bars", "1 Bar", ""};

        //item ids
        int[] t6 = {1205, 1321, 1291, 1375, -1,
                1422, 1337, 1173, 1189, 1307,
                39, 819, 1103, -1, -1,
                1155, 1117, 1075, 1087, -1,
                1139, 3095, 1265, 1351, -1};

        //level reqs
        int[] t7 = {1, 10, 9, 11, -1,
                5, 11, 10, 11, 12,
                3, 3, 13, -1, -1,
                8, 14, 13, 13, -1,
                6, 14, 9, 4, -1};

        int[] t8 = {0, 0, 0, 0, 0,
                1, 1, 1, 1, 1,
                2, 2, 2, 2, 2,
                3, 3, 3, 3, 3,
                4, 4, 4, 4, 4};

        int[] t9 = {1119, 1120, 1121, 1122, 1123,
                1119, 1120, 1121, 1122, 1123,
                1119, 1120, 1121, 1122, 1123,
                1119, 1120, 1121, 1122, 1123,
                1119, 1120, 1121, 1122, 1123};

        //qty made
        int[] t10 = {1, 1, 1, 1, -1,
                1, 1, 1, 1, 1,
                30, 20, 1, -1, -1,
                1, 1, 1, 1, -1,
                1, 1, 1, 1, -1};

        for (int i = 0; i < 25; i++) {
            player.getPacketSender().sendString(t1[i], t4[i] + t5[i] + t4[i]);
            player.getPacketSender().sendString(t2[i], GetForlvl(t7[i], player) + t3[i] + GetForlvl(t7[i], player));
            player.getPacketSender().sendSmithingData(t6[i], t8[i], t9[i], t10[i]);
        }

        player.getPacketSender().sendInterface(994);
    }

    public static void makeIronInterface(Player player) {
        String fiveb = GetForBars(2349, 5, player);
        String threeb = GetForBars(2349, 3, player);
        String twob = GetForBars(2349, 2, player);
        String oneb = GetForBars(2349, 1, player);

        int[] t1 = {1125, 1126, 1109, 1127, -1,
                1124, 1129, 1110, 1113, 1130,
                1116, 1118, 1111, -1, -1,
                1089, 1095, 1112, 1115, -1,
                1090, 8428, 11459, 13357, -1};

        int[] t2 = {1094, 1091, 1098, 1102, -1,
                1085, 1093, 1099, 1103, 1108,
                1087, 1083, 1100, -1, -1,
                1086, 1092, 1101, 1105, -1,
                1088, 8429, 11461, 13358, -1};

        String[] t3 = {"Dagger", "Scimitar", "Longsword", "Battleaxe", "empty",
                "Mace", "Warhammer", "Sq shield", "Kite shield", "2h",
                "Arrowtips", "Dart tips", "Chainbody", "empty", "empty",
                "Full helm", "Platebody", "Platelegs", "Plateskirt", "empty",
                "Med helm", "Claws", "Pickaxe", "Axe", "empty"};

        String[] t4 = {oneb, twob, twob, threeb, "",
                oneb, threeb, twob, threeb, threeb,
                oneb, oneb, threeb, "", "",
                twob, fiveb, threeb, threeb, "",
                oneb, twob, oneb, oneb, ""};

        String[] t5 = {"1 Bar", "2 Bars", "2 Bars", "3 Bars", "",
                "1 Bar", "3 Bars", "2 Bars", "3 Bars", "3 Bars",
                "1 Bar", "1 Bar", "3 Bars", "", "",
                "2 Bars", "5 Bars", "3 Bars", "3 Bars", "",
                "1 Bar", "2 Bars", "1 Bars", "1 Bar", ""};

        //item ids
        int[] t6 = {1203, 1323, 1293, 1363, -1,
                1420, 1335, 1175, 1191, 1309,
                40, 820, 1101, -1, -1,
                1153, 1115, 1067, 1081, -1,
                1137, 3096, 1267, 1349, -1};

        //level reqs
        int[] t7 = {16, 24, 23, 25, -1,
                19, 25, 24, 25, 26,
                17, 17, 27, -1, -1,
                22, 28, 27, 27, -1,
                20, 28, 23, 18, -1};

        int[] t8 = {0, 0, 0, 0, 0,
                1, 1, 1, 1, 1,
                2, 2, 2, 2, 2,
                3, 3, 3, 3, 3,
                4, 4, 4, 4, 4};

        int[] t9 = {1119, 1120, 1121, 1122, 1123,
                1119, 1120, 1121, 1122, 1123,
                1119, 1120, 1121, 1122, 1123,
                1119, 1120, 1121, 1122, 1123,
                1119, 1120, 1121, 1122, 1123};

        //qty made
        int[] t10 = {1, 1, 1, 1, -1,
                1, 1, 1, 1, 1,
                30, 20, 1, -1, -1,
                1, 1, 1, 1, -1,
                1, 1, 1, 1, -1};

        for (int i = 0; i < 25; i++) {
            player.getPacketSender().sendString(t1[i], t4[i] + t5[i] + t4[i]);
            player.getPacketSender().sendString(t2[i], GetForlvl(t7[i], player) + t3[i] + GetForlvl(t7[i], player));
            player.getPacketSender().sendSmithingData(t6[i], t8[i], t9[i], t10[i]);
        }

        player.getPacketSender().sendInterface(994);
    }

    public static void makeSteelInterface(Player player) {
        String fiveb = GetForBars(2349, 5, player);
        String threeb = GetForBars(2349, 3, player);
        String twob = GetForBars(2349, 2, player);
        String oneb = GetForBars(2349, 1, player);

        int[] t1 = {1125, 1126, 1109, 1127, -1,
                1124, 1129, 1110, 1113, 1130,
                1116, 1118, 1111, -1, -1,
                1089, 1095, 1112, 1115, -1,
                1090, 8428, 11459, 13357, -1};

        int[] t2 = {1094, 1091, 1098, 1102, -1,
                1085, 1093, 1099, 1103, 1108,
                1087, 1083, 1100, -1, -1,
                1086, 1092, 1101, 1105, -1,
                1088, 8429, 11461, 13358, -1};

        String[] t3 = {"Dagger", "Scimitar", "Longsword", "Battleaxe", "empty",
                "Mace", "Warhammer", "Sq shield", "Kite shield", "2h",
                "Arrowtips", "Dart tips", "Chainbody", "empty", "empty",
                "Full helm", "Platebody", "Platelegs", "Plateskirt", "empty",
                "Med helm", "Claws", "Pickaxe", "Axe", "empty"};

        String[] t4 = {oneb, twob, twob, threeb, "",
                oneb, threeb, twob, threeb, threeb,
                oneb, oneb, threeb, "", "",
                twob, fiveb, threeb, threeb, "",
                oneb, twob, oneb, oneb, ""};

        String[] t5 = {"1 Bar", "2 Bars", "2 Bars", "3 Bars", "",
                "1 Bar", "3 Bars", "2 Bars", "3 Bars", "3 Bars",
                "1 Bar", "1 Bar", "3 Bars", "", "",
                "2 Bars", "5 Bars", "3 Bars", "3 Bars", "",
                "1 Bar", "2 Bars", "1 Bars", "1 Bar", ""};

        //item ids
        int[] t6 = {1207, 1325, 1295, 1365, -1,
                1424, 1339, 1177, 1193, 1311,
                41, 821, 1105, -1, -1,
                1157, 1119, 1069, 1083, -1,
                1141, 3097, 1269, 1353, -1};

        //level reqs
        int[] t7 = {30, 38, 37, 39, -1,
                33, 39, 38, 39, 40,
                31, 31, 41, -1, -1,
                36, 42, 41, 41, -1,
                34, 42, 37, 32, -1};

        int[] t8 = {0, 0, 0, 0, 0,
                1, 1, 1, 1, 1,
                2, 2, 2, 2, 2,
                3, 3, 3, 3, 3,
                4, 4, 4, 4, 4};

        int[] t9 = {1119, 1120, 1121, 1122, 1123,
                1119, 1120, 1121, 1122, 1123,
                1119, 1120, 1121, 1122, 1123,
                1119, 1120, 1121, 1122, 1123,
                1119, 1120, 1121, 1122, 1123};

        //qty made
        int[] t10 = {1, 1, 1, 1, -1,
                1, 1, 1, 1, 1,
                30, 20, 1, -1, -1,
                1, 1, 1, 1, -1,
                1, 1, 1, 1, -1};

        for (int i = 0; i < 25; i++) {
            player.getPacketSender().sendString(t1[i], t4[i] + t5[i] + t4[i]);
            player.getPacketSender().sendString(t2[i], GetForlvl(t7[i], player) + t3[i] + GetForlvl(t7[i], player));
            player.getPacketSender().sendSmithingData(t6[i], t8[i], t9[i], t10[i]);
        }

        player.getPacketSender().sendInterface(994);
    }

    public static void makeMithInterface(Player player) {
        String fiveb = GetForBars(2349, 5, player);
        String threeb = GetForBars(2349, 3, player);
        String twob = GetForBars(2349, 2, player);
        String oneb = GetForBars(2349, 1, player);

        int[] t1 = {1125, 1126, 1109, 1127, -1,
                1124, 1129, 1110, 1113, 1130,
                1116, 1118, 1111, -1, -1,
                1089, 1095, 1112, 1115, -1,
                1090, 8428, 11459, 13357, -1};

        int[] t2 = {1094, 1091, 1098, 1102, -1,
                1085, 1093, 1099, 1103, 1108,
                1087, 1083, 1100, -1, -1,
                1086, 1092, 1101, 1105, -1,
                1088, 8429, 11461, 13358, -1};

        String[] t3 = {"Dagger", "Scimitar", "Longsword", "Battleaxe", "empty",
                "Mace", "Warhammer", "Sq shield", "Kite shield", "2h",
                "Arrowtips", "Dart tips", "Chainbody", "empty", "empty",
                "Full helm", "Platebody", "Platelegs", "Plateskirt", "empty",
                "Med helm", "Claws", "Pickaxe", "Axe", "empty"};

        String[] t4 = {oneb, twob, twob, threeb, "",
                oneb, threeb, twob, threeb, threeb,
                oneb, oneb, threeb, "", "",
                twob, fiveb, threeb, threeb, "",
                oneb, twob, oneb, oneb, ""};

        String[] t5 = {"1 Bar", "2 Bars", "2 Bars", "3 Bars", "",
                "1 Bar", "3 Bars", "2 Bars", "3 Bars", "3 Bars",
                "1 Bar", "1 Bar", "3 Bars", "", "",
                "2 Bars", "5 Bars", "3 Bars", "3 Bars", "",
                "1 Bar", "2 Bars", "1 Bars", "1 Bar", ""};

        //item ids
        int[] t6 = {1209, 1329, 1299, 1369, -1,
                1428, 1343, 1181, 1197, 1315,
                42, 822, 1109, -1, -1,
                1159, 1121, 1071, 1085, -1,
                1143, 3099, 1273, 1355, -1};

        //level reqs
        int[] t7 = {44, 52, 51, 53, -1,
                47, 53, 52, 53, 54,
                45, 45, 55, -1, -1,
                50, 56, 55, 55, -1,
                48, 56, 51, 46, -1};

        int[] t8 = {0, 0, 0, 0, 0,
                1, 1, 1, 1, 1,
                2, 2, 2, 2, 2,
                3, 3, 3, 3, 3,
                4, 4, 4, 4, 4};

        int[] t9 = {1119, 1120, 1121, 1122, 1123,
                1119, 1120, 1121, 1122, 1123,
                1119, 1120, 1121, 1122, 1123,
                1119, 1120, 1121, 1122, 1123,
                1119, 1120, 1121, 1122, 1123};

        //qty made
        int[] t10 = {1, 1, 1, 1, -1,
                1, 1, 1, 1, 1,
                30, 20, 1, -1, -1,
                1, 1, 1, 1, -1,
                1, 1, 1, 1, -1};

        for (int i = 0; i < 25; i++) {
            player.getPacketSender().sendString(t1[i], t4[i] + t5[i] + t4[i]);
            player.getPacketSender().sendString(t2[i], GetForlvl(t7[i], player) + t3[i] + GetForlvl(t7[i], player));
            player.getPacketSender().sendSmithingData(t6[i], t8[i], t9[i], t10[i]);
        }

        player.getPacketSender().sendInterface(994);
    }

    public static void makeAddyInterface(Player player) {
        String fiveb = GetForBars(2349, 5, player);
        String threeb = GetForBars(2349, 3, player);
        String twob = GetForBars(2349, 2, player);
        String oneb = GetForBars(2349, 1, player);

        int[] t1 = {1125, 1126, 1109, 1127, -1,
                1124, 1129, 1110, 1113, 1130,
                1116, 1118, 1111, -1, -1,
                1089, 1095, 1112, 1115, -1,
                1090, 8428, 11459, 13357, -1};

        int[] t2 = {1094, 1091, 1098, 1102, -1,
                1085, 1093, 1099, 1103, 1108,
                1087, 1083, 1100, -1, -1,
                1086, 1092, 1101, 1105, -1,
                1088, 8429, 11461, 13358, -1};

        String[] t3 = {"Dagger", "Scimitar", "Longsword", "Battleaxe", "empty",
                "Mace", "Warhammer", "Sq shield", "Kite shield", "2h",
                "Arrowtips", "Dart tips", "Chainbody", "empty", "empty",
                "Full helm", "Platebody", "Platelegs", "Plateskirt", "empty",
                "Med helm", "Claws", "Pickaxe", "Axe", "empty"};

        String[] t4 = {oneb, twob, twob, threeb, "",
                oneb, threeb, twob, threeb, threeb,
                oneb, oneb, threeb, "", "",
                twob, fiveb, threeb, threeb, "",
                oneb, twob, oneb, oneb, ""};

        String[] t5 = {"1 Bar", "2 Bars", "2 Bars", "3 Bars", "",
                "1 Bar", "3 Bars", "2 Bars", "3 Bars", "3 Bars",
                "1 Bar", "1 Bar", "3 Bars", "", "",
                "2 Bars", "5 Bars", "3 Bars", "3 Bars", "",
                "1 Bar", "2 Bars", "1 Bars", "1 Bar", ""};

        //item ids
        int[] t6 = {1211, 1331, 1301, 1371, -1,
                1430, 1345, 1183, 1199, 1317,
                43, 823, 1111, -1, -1,
                1161, 1123, 1073, 1091, -1,
                1145, 3100, 1271, 1357, -1};

        //level reqs
        int[] t7 = {58, 66, 63, 67, -1,
                61, 67, 66, 67, 68,
                59, 59, 69, -1, -1,
                64, 70, 69, 69, -1,
                62, 70, 65, 60, -1};

        int[] t8 = {0, 0, 0, 0, 0,
                1, 1, 1, 1, 1,
                2, 2, 2, 2, 2,
                3, 3, 3, 3, 3,
                4, 4, 4, 4, 4};

        int[] t9 = {1119, 1120, 1121, 1122, 1123,
                1119, 1120, 1121, 1122, 1123,
                1119, 1120, 1121, 1122, 1123,
                1119, 1120, 1121, 1122, 1123,
                1119, 1120, 1121, 1122, 1123};

        //qty made
        int[] t10 = {1, 1, 1, 1, -1,
                1, 1, 1, 1, 1,
                30, 20, 1, -1, -1,
                1, 1, 1, 1, -1,
                1, 1, 1, 1, -1};

        for (int i = 0; i < 25; i++) {
            player.getPacketSender().sendString(t1[i], t4[i] + t5[i] + t4[i]);
            player.getPacketSender().sendString(t2[i], GetForlvl(t7[i], player) + t3[i] + GetForlvl(t7[i], player));
            player.getPacketSender().sendSmithingData(t6[i], t8[i], t9[i], t10[i]);
        }

        player.getPacketSender().sendInterface(994);
    }

    public static void makeRuneInterface(Player player) {
        String fiveb = GetForBars(2349, 5, player);
        String threeb = GetForBars(2349, 3, player);
        String twob = GetForBars(2349, 2, player);
        String oneb = GetForBars(2349, 1, player);

        int[] t1 = {1125, 1126, 1109, 1127, -1,
                1124, 1129, 1110, 1113, 1130,
                1116, 1118, 1111, -1, -1,
                1089, 1095, 1112, 1115, -1,
                1090, 8428, 11459, 13357, -1};

        int[] t2 = {1094, 1091, 1098, 1102, -1,
                1085, 1093, 1099, 1103, 1108,
                1087, 1083, 1100, -1, -1,
                1086, 1092, 1101, 1105, -1,
                1088, 8429, 11461, 13358, -1};

        String[] t3 = {"Dagger", "Scimitar", "Longsword", "Battleaxe", "empty",
                "Mace", "Warhammer", "Sq shield", "Kite shield", "2h",
                "Arrowtips", "Dart tips", "Chainbody", "empty", "empty",
                "Full helm", "Platebody", "Platelegs", "Plateskirt", "empty",
                "Med helm", "Claws", "Pickaxe", "Axe", "empty"};

        String[] t4 = {oneb, twob, twob, threeb, "",
                oneb, threeb, twob, threeb, threeb,
                oneb, oneb, threeb, "", "",
                twob, fiveb, threeb, threeb, "",
                oneb, twob, oneb, oneb, ""};

        String[] t5 = {"1 Bar", "2 Bars", "2 Bars", "3 Bars", "",
                "1 Bar", "3 Bars", "2 Bars", "3 Bars", "3 Bars",
                "1 Bar", "1 Bar", "3 Bars", "", "",
                "2 Bars", "5 Bars", "3 Bars", "3 Bars", "",
                "1 Bar", "2 Bars", "1 Bars", "1 Bar", ""};

        //item ids
        int[] t6 = {1213, 1333, 1303, 1373, -1,
                1432, 1347, 1185, 1201, 1319,
                44, 824, 1113, -1, -1,
                1163, 1127, 1079, 1093, -1,
                1147, 3101, 1275, 1359, -1};

        //level reqs
        int[] t7 = {72, 80, 77, 81, -1,
                75, 81, 80, 81, 82,
                73, 73, 83, -1, -1,
                78, 84, 83, 83, -1,
                76, 84, 79, 74, -1};

        int[] t8 = {0, 0, 0, 0, 0,
                1, 1, 1, 1, 1,
                2, 2, 2, 2, 2,
                3, 3, 3, 3, 3,
                4, 4, 4, 4, 4};

        int[] t9 = {1119, 1120, 1121, 1122, 1123,
                1119, 1120, 1121, 1122, 1123,
                1119, 1120, 1121, 1122, 1123,
                1119, 1120, 1121, 1122, 1123,
                1119, 1120, 1121, 1122, 1123};

        //qty made
        int[] t10 = {1, 1, 1, 1, -1,
                1, 1, 1, 1, 1,
                30, 20, 1, -1, -1,
                1, 1, 1, 1, -1,
                1, 1, 1, 1, -1};

        for (int i = 0; i < 25; i++) {
            player.getPacketSender().sendString(t1[i], t4[i] + t5[i] + t4[i]);
            player.getPacketSender().sendString(t2[i], GetForlvl(t7[i], player) + t3[i] + GetForlvl(t7[i], player));
            player.getPacketSender().sendSmithingData(t6[i], t8[i], t9[i], t10[i]);
        }

        player.getPacketSender().sendInterface(994);
    }

    public static void makeDragonInterface(Player player) {
        String fiveb = GetForBars(2349, 5, player);
        String threeb = GetForBars(2349, 3, player);
        String twob = GetForBars(2349, 2, player);
        String oneb = GetForBars(2349, 1, player);

        int[] t1 = {1125, 1126, 1109, 1127, -1,
                1124, 1129, 1110, 1113, 1130,
                1116, 1118, 1111, -1, -1,
                1089, 1095, 1112, 1115, -1,
                1090, 8428, 11459, 13357, -1};

        int[] t2 = {1094, 1091, 1098, 1102, -1,
                1085, 1093, 1099, 1103, 1108,
                1087, 1083, 1100, -1, -1,
                1086, 1092, 1101, 1105, -1,
                1088, 8429, 11461, 13358, -1};

        String[] t3 = {"Dagger", "Scimitar", "Longsword", "Battleaxe", "empty",
                "Mace", "Warhammer", "Sq shield", "Kite shield", "2h",
                "Arrowtips", "Dart tips", "Chainbody", "empty", "empty",
                "Full helm", "Platebody", "Platelegs", "Plateskirt", "empty",
                "Med helm", "Claws", "Pickaxe", "Axe", "empty"};

        String[] t4 = {oneb, twob, twob, threeb, "",
                oneb, threeb, twob, threeb, threeb,
                oneb, oneb, threeb, "", "",
                twob, fiveb, threeb, threeb, "",
                oneb, twob, oneb, oneb, ""};

        String[] t5 = {"1 Bar", "2 Bars", "2 Bars", "3 Bars", "",
                "1 Bar", "3 Bars", "2 Bars", "3 Bars", "3 Bars",
                "1 Bar", "1 Bar", "3 Bars", "", "",
                "2 Bars", "5 Bars", "3 Bars", "3 Bars", "",
                "1 Bar", "2 Bars", "1 Bars", "1 Bar", ""};

        //item ids
        int[] t6 = {1215, 4587, 1305, 1377, -1,
                1434, 20555, 1187, 11613, 7158,
                11237, 11232, 3140, -1, -1,
                11335, 14479, 4087, 4585, -1,
                1149, 14484, 15259, 6739, -1};

        //level reqs
        int[] t7 = {86, 94, 91, 95, -1,
                89, 95, 94, 95, 96,
                87, 87, 97, -1, -1,
                92, 98, 97, 97, -1,
                90, 98, 93, 88, -1};

        int[] t8 = {0, 0, 0, 0, 0,
                1, 1, 1, 1, 1,
                2, 2, 2, 2, 2,
                3, 3, 3, 3, 3,
                4, 4, 4, 4, 4};

        int[] t9 = {1119, 1120, 1121, 1122, 1123,
                1119, 1120, 1121, 1122, 1123,
                1119, 1120, 1121, 1122, 1123,
                1119, 1120, 1121, 1122, 1123,
                1119, 1120, 1121, 1122, 1123};

        //qty made
        int[] t10 = {1, 1, 1, 1, -1,
                1, 1, 1, 1, 1,
                30, 20, 1, -1, -1,
                1, 1, 1, 1, -1,
                1, 1, 1, 1, -1};

        for (int i = 0; i < 25; i++) {
            player.getPacketSender().sendString(t1[i], t4[i] + t5[i] + t4[i]);
            player.getPacketSender().sendString(t2[i], GetForlvl(t7[i], player) + t3[i] + GetForlvl(t7[i], player));
            player.getPacketSender().sendSmithingData(t6[i], t8[i], t9[i], t10[i]);
        }

        player.getPacketSender().sendInterface(994);
    }

    public static void sendString(Player player, String s, int i) {
        player.getPacketSender().sendString(i, s);
    }

    private static String GetForlvl(int i, Player player) {
        if (player.getSkillManager().getCurrentLevel(Skill.SMITHING) >= i)
            return "@whi@";

        return "@bla@";
    }

    private static String GetForBars(int i, int j, Player player) {
        if (player.getInventory().getAmount(i) >= j)
            return "@gre@";
        return "@red@";
    }

    public static int getItemAmount(Item item) {
        String name = item.getDefinition().getName().toLowerCase();
        if (name.contains("cannon")) {
            return 10;
        } else if (name.contains("knife")) {
            return 10;
        } else if (name.contains("arrow") || name.contains("nails")) {
            return 30;
        } else if (name.contains("dart tip") || name.contains("bolts")) {
            return 20;
        }
        return 1;
    }

    public static int getBarAmount(Item item) {
        String name = item.getDefinition().getName().toLowerCase();
        if (name.contains("scimitar") || name.contains("claws") || name.contains("longsword") || name.contains("sq shield") || name.contains("full helm")) {
            return 2;
        } else if (name.contains("2h sword") || name.contains("warhammer") || name.contains("battleaxe") || name.contains("chainbody") || name.contains("platelegs") || name.contains("plateskirt") || name.contains("kiteshield")) {
            return 3;
        } else if (name.contains("latebod")) {
            return 5;
        }
        return 1;
    }

    public static int getData(Item item, String type) {
        int xp = 1;
        int reqLvl = 1;
        @SuppressWarnings("unused")
        int amount = getItemAmount(item);
        switch (item.getId()) {
            case 2:
                xp = 370;
                break;
            case 1205: //Bronze dagger
                xp = 25;
                reqLvl = 1;
                break;

            case 39: //Bronze Arrowtips
                xp = 25;
                reqLvl = 5;
                break;

            case 819: //Bronze Dart Tip
                xp = 25;
                reqLvl = 4;
                break;

            case 1351: //Bronze Hatchet
                xp = 25;
                reqLvl = 1;
                break;

            case 1422: //Bronze Mace
                xp = 25;
                reqLvl = 2;
                break;

            case 1291: //Bronze Long Sword
                xp = 50;
                reqLvl = 6;
                break;

            case 1139: //Bronze Med Helm
                xp = 25;
                reqLvl = 3;

            case 1155: //Bronze Full Helm
                xp = 50;
                reqLvl = 7;
                break;

            case 1265: //Bronze Pickaxe
                xp = 25;
                reqLvl = 9;
                break;

            case 1321: //Bronze Scimitar
                xp = 50;
                reqLvl = 5;
                break;

            case 1173: //Bronze Sq Shield
                xp = 50;
                reqLvl = 8;
                break;

            case 1189: //Bronze Kiteshield
                xp = 75;
                reqLvl = 12;
                break;

            case 1375: //Bronze Battleaxe
                xp = 75;
                reqLvl = 10;
                break;

            case 1337: //Bronze Warhammer
                xp = 75;
                reqLvl = 9;
                break;

            case 1307: //Bronze 2h Sword
                xp = 75;
                reqLvl = 14;
                break;

            case 1103: //Bronze Chainbody
                xp = 75;
                reqLvl = 11;
                break;

            case 1075: //Bronze Platelegs
                xp = 75;
                reqLvl = 16;
                break;

            case 1087: //Bronze Plateskirt
                xp = 75;
                reqLvl = 16;
                break;

            case 1117: //Bronze Platebody
                xp = 125;
                reqLvl = 18;
                break;

            case 3095: //Bronze Claws
                xp = 50;
                reqLvl = 13;
                break;

            case 1203: //iron dagger
                xp = 50;
                reqLvl = 15;
                break;

            case 40: //iron arrowtip
                xp = 50;
                reqLvl = 20;
                break;

            case 820: //Iron Dart Tip
                xp = 50;
                reqLvl = 19;
                break;

            case 1349: //iron Hatchet
                xp = 50;
                reqLvl = 16;
                break;

            case 1420: //iron mace
                xp = 50;
                reqLvl = 17;
                break;

            case 1137: //iron med helm
                xp = 50;
                reqLvl = 18;
                break;

            case 1293: //iron Longsword
                xp = 100;
                reqLvl = 21;
                break;

            case 1153: //iron Full Helm
                xp = 100;
                reqLvl = 22;
                break;

            case 1267: //iron Pickaxe
                xp = 50;
                reqLvl = 23;
                break;

            case 1323: //iron Scimitar
                xp = 100;
                reqLvl = 20;
                break;

            case 1175: //iron Sq shield
                xp = 100;
                reqLvl = 23;
                break;

            case 1191: //iron Kiteshield
                xp = 150;
                reqLvl = 27;
                break;

            case 1363: //iron Battleaxe
                xp = 150;
                reqLvl = 25;
                break;

            case 1335: //iron Warhammer
                xp = 150;
                reqLvl = 24;
                break;

            case 1309: //iron 2h Sword
                xp = 150;
                reqLvl = 29;
                break;

            case 1067: //iron Platelegs
                xp = 150;
                reqLvl = 31;
                break;

            case 1081: //iron Plateskirt
                xp = 150;
                reqLvl = 31;
                break;

            case 1101: //iron chainbody
                xp = 150;
                reqLvl = 26;
                break;

            case 3096: //iron claws
                xp = 250;
                reqLvl = 28;
                break;

            case 1115: //iron platebody
                xp = 250;
                reqLvl = 33;
                break;

            case 1207: //steel Dagger
                xp = 100;
                reqLvl = 30;
                break;

            case 41: //steel Arrowtips
                xp = 100;
                reqLvl = 35;
                break;

            case 821: //Steel Dart Tip
                xp = 100;
                reqLvl = 34;
                break;

            case 1353: //steel Hatchet
                xp = 100;
                reqLvl = 31;
                break;

            case 1424: //steel Mace
                xp = 100;
                reqLvl = 32;
                break;

            case 1141: //steel Med helm
                xp = 100;
                reqLvl = 33;
                break;

            case 1295: //steel Longsword
                xp = 200;
                reqLvl = 36;
                break;

            case 1157: //steel full helm
                xp = 200;
                reqLvl = 37;
                break;

            case 1269: //steel Pickaxe
                xp = 100;
                reqLvl = 37;
                break;

            case 1325: //steel scimitar
                xp = 200;
                reqLvl = 35;
                break;

            case 1177: //steel sq shield
                xp = 200;
                reqLvl = 38;
                break;

            case 1193: //steel Kiteshield
                xp = 300;
                reqLvl = 42;
                break;

            case 1365: //steel Battleaxe
                xp = 300;
                reqLvl = 40;
                break;

            case 1339: //steel warhammer
                xp = 300;
                reqLvl = 39;
                break;

            case 1311: //steel 2h Sword
                xp = 300;
                reqLvl = 44;
                break;

            case 1069: //steel Platelegs
                xp = 300;
                reqLvl = 46;
                break;

            case 1083: //steel Plateskirt
                xp = 300;
                reqLvl = 46;
                break;

            case 1105: //steel chainbody
                xp = 300;
                reqLvl = 41;
                break;

            case 1119: //steel Platebody
                xp = 500;
                reqLvl = 48;
                break;

            case 3097: //steel claws
                xp = 200;
                reqLvl = 43;
                break;

            case 1209: //Mithril Dagger
                xp = 200;
                reqLvl = 50;
                break;

            case 42: //Mithril Arrowtips
                xp = 200;
                reqLvl = 55;
                break;

            case 822: //Mithril Dart Tip
                xp = 200;
                reqLvl = 54;
                break;

            case 1355: //Mithril Hatchet
                xp = 200;
                reqLvl = 51;
                break;

            case 1428: //Mithril Mace
                xp = 200;
                reqLvl = 52;
                break;

            case 1143: //Mithril Med helm
                xp = 200;
                reqLvl = 53;
                break;

            case 1299: //Mithril longsword
                xp = 400;
                reqLvl = 56;
                break;

            case 1159: //Mithril Full helm
                xp = 400;
                reqLvl = 57;
                break;

            case 1273: //Mithril Pickaxe
                xp = 200;
                reqLvl = 55;
                break;

            case 1329: //Mithril Scimitar
                xp = 400;
                reqLvl = 55;
                break;

            case 1181: //Mithril Sq shield
                xp = 400;
                reqLvl = 58;
                break;

            case 1197: //Mithril Kiteshield
                xp = 600;
                reqLvl = 62;
                break;

            case 1369: //Mithril Battleaxe
                xp = 600;
                reqLvl = 60;
                break;

            case 1343: //Mithril Warhammer
                xp = 600;
                reqLvl = 59;
                break;

            case 1315: //Mithril 2h sword
                xp = 600;
                reqLvl = 64;
                break;

            case 1071: //Mithril platelegs
                xp = 600;
                reqLvl = 66;
                break;

            case 1085: //Mithril plateskirt
                xp = 600;
                reqLvl = 66;
                break;

            case 1109: //Mithril chainbody
                xp = 600;
                reqLvl = 61;
                break;

            case 1121: //Mithril Platebody
                xp = 1000;
                reqLvl = 68;
                break;

            case 3099: //Mithril claws
                xp = 400;
                reqLvl = 63;
                break;

            case 1211: //Adamant Dagger
                xp = 250;
                reqLvl = 70;
                break;

            case 43: //Adamant Arrowtips
                xp = 250;
                reqLvl = 75;
                break;

            case 823: //Adamant Dart Tip
                xp = 250;
                reqLvl = 74;
                break;

            case 1357: //Adamant Hatchet
                xp = 250;
                reqLvl = 71;
                break;

            case 1430: //Adamant Mace
                xp = 250;
                reqLvl = 72;
                break;

            case 1145: //Adamant Med helm
                xp = 250;
                reqLvl = 73;
                break;

            case 1301: //Adamant  Longsword
                xp = 500;
                reqLvl = 76;
                break;

            case 1161: //Adamant Full Helm
                xp = 500;
                reqLvl = 77;
                break;

            case 1271: //Adamant Pickaxe
                xp = 250;
                reqLvl = 75;

            case 1331: //Adamant Scimitar
                xp = 500;
                reqLvl = 75;
                break;

            case 1183: //Adamant Sq shield
                xp = 500;
                reqLvl = 78;
                break;

            case 1199: //Adamant  Kiteshield
                xp = 750;
                reqLvl = 82;
                break;

            case 1371: //Adamant Battleaxe
                xp = 750;
                reqLvl = 80;
                break;

            case 1345: //Adamant Warhammer
                xp = 750;
                reqLvl = 79;
                break;

            case 1317: //Adamant 2h sword
                xp = 750;
                reqLvl = 84;
                break;

            case 1073: //Adamant Platelegs
                xp = 750;
                reqLvl = 86;
                break;

            case 1111: //Adamant chainbody
                xp = 750;
                reqLvl = 81;
                break;

            case 1091: //Adamant Plateskirt
                xp = 750;
                reqLvl = 86;
                break;

            case 1123: //Adamant platebody
                xp = 1250;
                reqLvl = 88;
                break;

            case 3100: //Adamant claws
                xp = 500;
                reqLvl = 83;
                break;

            case 1213: //Rune Dagger
                xp = 300;
                reqLvl = 85;
                break;

            case 44: //Rune Arrowtip
                xp = 300;
                reqLvl = 90;
                break;

            case 824: //Rune Dart Tip
                xp = 300;
                reqLvl = 89;
                break;

            case 1359: //Rune Axe
                xp = 300;
                reqLvl = 86;
                break;

            case 1432: //Rune Mace
                xp = 300;
                reqLvl = 87;
                break;

            case 1147: //Rune med helm
                xp = 300;
                reqLvl = 88;
                break;

            case 1303: //Rune Longsword
                xp = 600;
                reqLvl = 91;
                break;

            case 1163: //Rune Full helm
                xp = 600;
                reqLvl = 92;
                break;

            case 1275: //Rune Pickaxe
                xp = 300;
                reqLvl = 90;
                break;

            case 1333: //Rune Scimitar
                xp = 600;
                reqLvl = 90;
                break;

            case 1185: //Rune Sq shield
                xp = 600;
                reqLvl = 93;
                break;

            case 1201: //Rune Kiteshield
                xp = 900;
                reqLvl = 97;
                break;

            case 1373: //Rune Battleaxe
                xp = 900;
                reqLvl = 95;
                break;

            case 1347: //Rune Warhammer
                xp = 900;
                reqLvl = 94;
                break;

            case 1319: //Rune 2h sword
                xp = 900;
                reqLvl = 99;
                break;

            case 1079: //Rune Platelegs
                xp = 900;
                reqLvl = 99;
                break;

            case 1093: //Rune Plateskirt
                xp = 900;
                reqLvl = 99;
                break;

            case 1113: //Rune chainbody
                xp = 900;
                reqLvl = 96;
                break;

            case 1127: //Rune Platebody
                xp = 1500;
                reqLvl = 99;
                break;

            case 3101: //Rune claws
                xp = 600;
                reqLvl = 98;
                break;
        }

        return type.equalsIgnoreCase("xp") ? xp : reqLvl;
    }

    public static boolean ironOreSuccess(Player player) {
        return RandomUtility.inclusiveRandom((int) (1 + player.getSkillManager().getCurrentLevel(Skill.SMITHING) / 1.5)) > 5;
    }
}
