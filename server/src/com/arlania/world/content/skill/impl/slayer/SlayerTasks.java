package com.arlania.world.content.skill.impl.slayer;

import com.arlania.model.Position;
import com.arlania.util.Misc;
import com.arlania.util.RandomUtility;
import com.arlania.world.entity.impl.player.Player;

/**
 * @author Gabriel Hannason
 */

public enum SlayerTasks {

    NO_TASK(0, null, new int[]{-1}, null, -1, null, 1),

    /**
     * Vannaka
     */

    //starts at 1
    ROCK_CRAB(1, SlayerMaster.VANNAKA, new int[]{1265}, "Rock Crabs can be found in North Relekka.", 150, new Position(2709, 3715, 0), 1),
    GIANT_BAT(2, SlayerMaster.VANNAKA, new int[]{78}, "Giant Bats can be found in Taverly Dungeon.", 150, new Position(2907, 9833), 1),
    CHAOS_DRUID(3, SlayerMaster.VANNAKA, new int[]{181}, "Chaos Druids can be found in Edgeville Dungeon.", 150, new Position(3109, 9931, 0), 1),
    HOBGOBLIN(4, SlayerMaster.VANNAKA, new int[]{2686}, "Hobgoblins can be found in Edgeville Dungeon.", 150, new Position(3123, 9876, 0), 1),
    HILL_GIANT(5, SlayerMaster.VANNAKA, new int[]{117}, "Hill Giants can be found in Edgeville Dungeon.", 150, new Position(3120, 9844, 0), 1),
    DEADLY_RED_SPIDER(6, SlayerMaster.VANNAKA, new int[]{63}, "Deadly Red Spiders can be found in Edgeville Dungeon.", 150, new Position(3083, 9940, 0), 1),
    BABY_BLUE_DRAGON(7, SlayerMaster.VANNAKA, new int[]{52, 55}, "Baby Blue Dragons can be found in Taverly Dungeon.", 150, new Position(2891, 9772, 0), 1),
    CRAWLING_HAND(8, SlayerMaster.VANNAKA, new int[]{1648, 1649, 1650, 1651, 1652, 1653, 1654, 1655, 1656, 1657, 21388}, "Crawling Hands can be found in the Slayer Tower.", 15, new Position(3124, 9986), 5),
    CAVE_CRAWLER(9, SlayerMaster.VANNAKA, new int[]{1600, 21389}, "Cave Crawlers can be found in the Slayer Dungeon.", 150, new Position(3124, 9986), 10),
    BANSHEE(10, SlayerMaster.VANNAKA, new int[]{1612, 21390}, "Banshees can be found in the Slayer Tower.", 200, new Position(3124, 9986), 15),
    COCKATRICE(11, SlayerMaster.VANNAKA, new int[]{1621, 21393}, "Cockatrices can be found in the Slayer Dungeon.", 200, new Position(3124, 9986), 25),
    PYREFIEND(12, SlayerMaster.VANNAKA, new int[]{6216, 21394}, "Pyrefiends can be found in the Slayer Dungeon.", 200, new Position(3124, 9986), 30),


    /**
     * Duradel
     */

    //starts at 13
    MOSS_GIANT(13, SlayerMaster.DURADEL, new int[]{112}, "Moss Giants can be found in Brimhaven Dungeon.", 275, new Position(2676, 9549), 1),
    FIRE_GIANT(14, SlayerMaster.DURADEL, new int[]{110}, "Fire Giants can be found in Brimhaven Dungeon.", 300, new Position(2664, 9480), 1),
    GREEN_DRAGON(15, SlayerMaster.DURADEL, new int[]{941, 5362}, "Green Dragons can be found in western Wilderness.", 400, new Position(2977, 3615), 1),
    BLUE_DRAGON(16, SlayerMaster.DURADEL, new int[]{55}, "Blue Dragons can be found in Taverly Dungeon.", 450, new Position(2892, 9799), 1),
    HELLHOUND(17, SlayerMaster.DURADEL, new int[]{49, 1999}, "Hellhounds can be found in Taverly Dungeon.", 500, new Position(2870, 9848), 1),
    BLACK_DEMON(18, SlayerMaster.DURADEL, new int[]{84}, "Black Demons can be found in Edgeville Dungeon.", 600, new Position(3089, 9967), 1),
    AVIANSIE(19, SlayerMaster.DURADEL, new int[]{6246, 6222, 6223, 6225, 6227}, "Aviansies can be found in the Godwars Dungeon.", 500, new Position(2868, 5268, 2), 1),
    BASILISK(20, SlayerMaster.DURADEL, new int[]{1616, 23287}, "Basilisks can be found in the Slayer Dungeon.", 500, new Position(3445, 3579, 1), 40),
    INFERNAL_MAGE(21, SlayerMaster.DURADEL, new int[]{1643, 21396}, "Infernal Mages can be found in the Slayer Tower.", 500, new Position(3445, 3579, 1), 45),
    BLOODVELD(22, SlayerMaster.DURADEL, new int[]{1618, 21397}, "Bloodvelds can be found in the Slayer Tower.", 500, new Position(3418, 3570, 1), 50),
    TUROTH(23, SlayerMaster.DURADEL, new int[]{1626, 24397}, "Turoths can be found in the Slayer Dungeon.", 550, new Position(3418, 3570, 1), 55),
    ABERRANT_SPECTRE(24, SlayerMaster.DURADEL, new int[]{1604, 21402}, "Aberrant Spectres can be found in Slayer Tower.", 600, new Position(3432, 3553, 1), 60),


    /**
     * Kuradel
     */

    //starts at 25
    WATERFIEND(25, SlayerMaster.KURADEL, new int[]{5361}, "Waterfiends can be found in the Ancient Cavern.", 600, new Position(1737, 5353), 1),
    STEEL_DRAGON(26, SlayerMaster.KURADEL, new int[]{1592}, "Steel dragons can be found in Brimhaven Dungeon.", 700, new Position(2710, 9441), 1),
    MITHRIL_DRAGON(27, SlayerMaster.KURADEL, new int[]{5363}, "Mithril Dragons can be found in the Ancient Cavern.", 750, new Position(1761, 5329, 1), 1),
    KURASK(28, SlayerMaster.KURADEL, new int[]{1608, 14410, 21405}, "Kurasks can be found in the Slayer Dungeon.", 750, new Position(3438, 3534, 2), 70),
    GARGOYLE(29, SlayerMaster.KURADEL, new int[]{1610, 21407}, "Gargoyles can be found in Slayer Tower.", 750, new Position(3438, 3534, 2), 75),
    AQUANITE(30, SlayerMaster.KURADEL, new int[]{9172}, "Aquanites can be found in the Waterbirth Dungeon.", 750, new Position(3448, 3564, 2), 90),
    JUNGLE_STRYKEWYRM(31, SlayerMaster.KURADEL, new int[]{9467}, "Strykewyrms can be found in the Strykewyrm Cavern", 800, new Position(2731, 5095), 78),
    DESERT_STRYKEWYRM(32, SlayerMaster.KURADEL, new int[]{9465}, "Strykewyrms can be found in the Strykewyrm Cavern.", 800, new Position(2731, 5095), 78),
    ICE_STRYKEWYRM(33, SlayerMaster.KURADEL, new int[]{9463}, "Strykewyrms can be found in the Strykewyrm Cavern.", 800, new Position(2731, 5095), 93),
    NECHRYAEL(34, SlayerMaster.KURADEL, new int[]{1613, 21411}, "Nechryaels can be found in Slayer Tower.", 800, new Position(3448, 3564, 2), 80),
    ABYSSAL_DEMON(35, SlayerMaster.KURADEL, new int[]{1615, 21410}, "Abyssal Demons can be found in Slayer Tower.", 1000, new Position(3448, 3564, 2), 85),
    DARK_BEAST(36, SlayerMaster.KURADEL, new int[]{2783, 21409}, "Dark Beasts can be found in the Slayer Dungeon.", 1000, new Position(3448, 3564, 2), 90),


    /**
     * Sumona
     */

    //starts at 37
    DAGANNOTH_KINGS(37, SlayerMaster.SUMONA, new int[]{2881, 2882, 2883}, "The Dagannoth Kings can be found using the Boss teleport.", 2500, new Position(1908, 4367), 1),
    KING_BLACK_DRAGON(38, SlayerMaster.SUMONA, new int[]{50}, "The King Black Dragon can be found using the Wilderness teleport.", 3000, new Position(2273, 4680, 1), 1),
    KALPHITE_QUEEN(39, SlayerMaster.SUMONA, new int[]{1158, 1159, 1160}, "The Kalphite Queen can be found using the Boss teleport.", 3000, new Position(3476, 9502), 1),
    GENERAL_GRAARDOR(40, SlayerMaster.SUMONA, new int[]{6260}, "General Graardor can be found in the Godwars Dungeon.", 4000, new Position(2863, 5354), 1),
    KREEARRA(41, SlayerMaster.SUMONA, new int[]{6222}, "KreeArra can be found in the Godwars Dungeon.", 4000, new Position(3211, 5459), 1),
    KRIL_TSUTSUROTH(42, SlayerMaster.SUMONA, new int[]{6203}, "Kril Tsutsuorth can be found in the Godwars Dungeon.", 4000, new Position(3221, 5480), 1),
    COMMANDER_ZILYANA(43, SlayerMaster.SUMONA, new int[]{6247}, "Commander Zilyana can be found in the Godwars Dungeon.", 4000, new Position(3186, 5450), 1),
    GIANT_MOLE(44, SlayerMaster.SUMONA, new int[]{3340}, "The Giant Mole can be found in Faladdor", 2500, new Position(1761, 5186), 1),
    NULL2(45, null, new int[]{-1}, null, -1, null, 0),
    NULL3(46, null, new int[]{-1}, null, -1, null, 0),
    NULL4(47, null, new int[]{-1}, null, -1, null, 0),
    NULL5(48, null, new int[]{-1}, null, -1, null, 0),

    /**
     * Nieve
     */

    //starts at 49
    CORPOREAL_BEAST(49, SlayerMaster.NIEVE, new int[]{8133}, "The Corporeal Beast can be found using the Wilderness teleport.", 1200, new Position(2885, 4375), 1),
    ABYSSAL_SIRE(50, SlayerMaster.NIEVE, new int[]{5886}, "Abyssal Sire can be found using the Boss teleport.", 5000, new Position(3147, 5476), 92),
    CERBERUS(51, SlayerMaster.NIEVE, new int[]{1999}, "Cerberus can be found using the Boss teleport.", 5000, new Position(3147, 5476), 92),
    THERMO(52, SlayerMaster.NIEVE, new int[]{499}, "Thermo can be found using the Boss teleport.", 5000, new Position(3147, 5476), 92),
    ZULRAH(53, SlayerMaster.NIEVE, new int[]{2042, 2043, 2044}, "Zulrah can be found using the Boss teleport.", 5000, new Position(3147, 5476), 92),
    JAD(54, SlayerMaster.NIEVE, new int[]{2745}, "Jad can be found using the Tzhaar City teleport.", 6000, new Position(3147, 5476), 92),
    NIGHTMARE(55, SlayerMaster.NIEVE, new int[]{23425, 23426}, "Nightmare can be found using the Boss teleport.", 10000, new Position(3147, 5476), 92),
    GLACIO(56, SlayerMaster.NIEVE, new int[]{4540}, "Glacio can be found using the Boss teleport.", 10000, new Position(3147, 5476), 92),
    NULL6(57, null, new int[]{-1}, null, -1, null, 0),
    NULL7(58, null, new int[]{-1}, null, -1, null, 0),
    NULL8(59, null, new int[]{-1}, null, -1, null, 0),
    NULL9(60, null, new int[]{-1}, null, -1, null, 0),

    /**
     * Dave
     */

    //starts at 61
    REVENANT(61, SlayerMaster.DAVE, new int[]{6691, 6725, 6701, 6716, 6715}, "Revenants are located in the Revenant Arena.", 400, new Position(2602, 5713), 1),
    TORMENTED_DEMON(62, SlayerMaster.DAVE, new int[]{8349}, "Tormented Demons can be found using the Boss teleport.", 400, new Position(2602, 5713), 1),
    FROST_DRAGON(63, SlayerMaster.DAVE, new int[]{51, 22061}, "Frost Dragons can be found using the Wilderness teleport.", 3000, new Position(1908, 4367), 1),
    CALLISTO(64, SlayerMaster.DAVE, new int[]{2009}, "Callisto can be found using the Wilderness teleport.", 5000, new Position(1908, 4367), 1),
    VENENATIS(65, SlayerMaster.DAVE, new int[]{2000}, "Venenatis can be found using the Wilderness teleport.", 5000, new Position(1908, 4367), 1),
    SCORPIA(66, SlayerMaster.DAVE, new int[]{2001}, "Scorpia can be found using the Wilderness teleport.", 5000, new Position(2273, 4680, 1), 1),
    CHAOS_ELEMENTAL(67, SlayerMaster.DAVE, new int[]{3200}, "Chaos Elemental can be found using the Wilderness teleport.", 5000, new Position(2602, 5713), 1),
    WILDYWYRM(68, SlayerMaster.DAVE, new int[]{3334}, "Wildywyrm can be found using the Wilderness teleport.", 5000, new Position(2602, 5713), 1),
    VETION(69, SlayerMaster.DAVE, new int[]{2006}, "Vetion can be found using the Wilderness teleport.", 5000, new Position(3476, 9502), 1),
    NULL10(70, null, null, null, -1, null, 0),
    NULL11(71, null, null, null, -1, null, 0),
    NULL12(72, null, null, null, -1, null, 0);


    SlayerTasks(int taskID, SlayerMaster taskMaster, int[] npcId, String npcLocation, int XP, Position taskPosition, int reqLvl) {
        this.taskID = taskID;
        this.taskMaster = taskMaster;
        this.npcId = npcId;
        this.npcLocation = npcLocation;
        this.XP = XP;
        this.taskPosition = taskPosition;
        this.reqLvl = reqLvl;
    }

    private final int taskID;
    private final SlayerMaster taskMaster;
    private final int[] npcId;
    private final String npcLocation;
    private final int XP;
    private final Position taskPosition;
    private final int reqLvl;

    public int getTaskID() {
        return this.taskID;
    }

    public SlayerMaster getTaskMaster() {
        return this.taskMaster;
    }

    public int[] getNpcId() {
        return this.npcId;
    }

    public String getNpcLocation() {
        return this.npcLocation;
    }

    public int getXP() {
        return this.XP;
    }

    public Position getTaskPosition() {
        return this.taskPosition;
    }

    public int getreqLvl() {
        return this.reqLvl;
    }

    public static SlayerTasks forId(int id) {
        for (SlayerTasks tasks : SlayerTasks.values()) {
            if (tasks.ordinal() == id) {
                return tasks;
            }
        }
        return null;
    }

    public static int[] getNewTaskData(SlayerMaster master, Player player) {
        int slayerTaskId = 1, slayerTaskAmount = 20;
        int easyTasks = 12, mediumTasks = 12, hardTasks = 12, eliteTasks = 12, masterTasks = 12, wildTasks = 12;
        int actualEasyTasks = 12, actualMediumTasks = 12, actualHardTasks = 12;
        int actualEliteTasks = 7, actualMasterTasks = 8, actualWildTasks = 9;

        int max = 0;
        int min = 0;


        if (master == SlayerMaster.VANNAKA) {
            slayerTaskId = RandomUtility.inclusiveRandom(actualEasyTasks);
            if (slayerTaskId > easyTasks)
                slayerTaskId = easyTasks;

            max = 100;
            min = 70;

            if (player.slayerQty == "max")
                slayerTaskAmount = max;
            else if (player.slayerQty == "min")
                slayerTaskAmount = min;
            else
                slayerTaskAmount = RandomUtility.exclusiveRandom(min, max);

        } else if (master == SlayerMaster.DURADEL) {
            slayerTaskId = easyTasks + RandomUtility.inclusiveRandom(actualMediumTasks);
            max = 80;
            min = 60;

            if (player.slayerQty == "max")
                slayerTaskAmount = max;
            else if (player.slayerQty == "min")
                slayerTaskAmount = min;
            else
                slayerTaskAmount = RandomUtility.exclusiveRandom(min, max);
        } else if (master == SlayerMaster.KURADEL) {
            slayerTaskId = easyTasks + mediumTasks + RandomUtility.inclusiveRandom(actualHardTasks);
            max = 75;
            min = 55;

            if (player.slayerQty == "max")
                slayerTaskAmount = max;
            else if (player.slayerQty == "min")
                slayerTaskAmount = min;
            else
                slayerTaskAmount = RandomUtility.exclusiveRandom(min, max);
        } else if (master == SlayerMaster.SUMONA) {
            slayerTaskId = easyTasks + mediumTasks + hardTasks + RandomUtility.inclusiveRandom(actualEliteTasks);
            max = 20;
            min = 12;

            if (player.slayerQty == "max")
                slayerTaskAmount = max;
            else if (player.slayerQty == "min")
                slayerTaskAmount = min;
            else
                slayerTaskAmount = RandomUtility.exclusiveRandom(min, max);
        } else if (master == SlayerMaster.NIEVE) {
            slayerTaskId = easyTasks + mediumTasks + hardTasks + eliteTasks + RandomUtility.inclusiveRandom(actualMasterTasks);
            max = 15;
            min = 8;

            if (player.slayerQty == "max")
                slayerTaskAmount = max;
            else if (player.slayerQty == "min")
                slayerTaskAmount = min;
            else
                slayerTaskAmount = RandomUtility.exclusiveRandom(min, max);
        } else if (master == SlayerMaster.DAVE) {
            slayerTaskId = easyTasks + mediumTasks + hardTasks + eliteTasks + masterTasks + RandomUtility.inclusiveRandom(actualWildTasks);
            max = 12;
            min = 7;

            if (player.slayerQty == "max")
                slayerTaskAmount = max;
            else if (player.slayerQty == "min")
                slayerTaskAmount = min;
            else
                slayerTaskAmount = RandomUtility.exclusiveRandom(min, max);
        }


        return new int[]{slayerTaskId, slayerTaskAmount};
    }


    @Override
    public String toString() {
        return Misc.ucFirst(name().toLowerCase().replaceAll("_", " "));
    }


    public static int getUnnaturalTaskQuantity(Player player) {

        SlayerMaster master = player.getSlayer().getSlayerMaster();

        int slayerTaskAmount = 1;
        int max = 1;
        int min = 1;

        String slayerQty = player.slayerQty;

        if (master == SlayerMaster.VANNAKA) {

            max = 100;
            min = 70;

            if (slayerQty == "max")
                slayerTaskAmount = max;
            else if (slayerQty == "min")
                slayerTaskAmount = min;
            else
                slayerTaskAmount = RandomUtility.exclusiveRandom(min, max);

        } else if (master == SlayerMaster.DURADEL) {
            max = 80;
            min = 60;

            if (slayerQty == "max")
                slayerTaskAmount = max;
            else if (slayerQty == "min")
                slayerTaskAmount = min;
            else
                slayerTaskAmount = RandomUtility.exclusiveRandom(min, max);
        } else if (master == SlayerMaster.KURADEL) {
            max = 75;
            min = 55;

            if (slayerQty == "max")
                slayerTaskAmount = max;
            else if (slayerQty == "min")
                slayerTaskAmount = min;
            else
                slayerTaskAmount = RandomUtility.exclusiveRandom(min, max);
        } else if (master == SlayerMaster.SUMONA) {
            max = 20;
            min = 12;

            if (slayerQty == "max")
                slayerTaskAmount = max;
            else if (slayerQty == "min")
                slayerTaskAmount = min;
            else
                slayerTaskAmount = RandomUtility.exclusiveRandom(min, max);
        } else if (master == SlayerMaster.NIEVE) {
            max = 15;
            min = 8;

            if (slayerQty == "max")
                slayerTaskAmount = max;
            else if (slayerQty == "min")
                slayerTaskAmount = min;
            else
                slayerTaskAmount = RandomUtility.exclusiveRandom(min, max);
        } else if (master == SlayerMaster.DAVE) {
            max = 12;
            min = 7;

            if (slayerQty == "max")
                slayerTaskAmount = max;
            else if (slayerQty == "min")
                slayerTaskAmount = min;
            else
                slayerTaskAmount = RandomUtility.exclusiveRandom(min, max);
        }
        return slayerTaskAmount;

    }


}
