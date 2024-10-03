package com.arlania.world.content.skill.impl.slayer;

/**
 * @author Gabriel Hannason
 */

public enum SlayerMobs {

    CRAWLING_HAND(5, new int[]{1648, 1649, 1650, 1651, 1652, 1653, 1654, 1655, 1656, 1657, 21388}, "Crawling Hands can be found in the Slayer Tower."),
    CAVE_CRAWLER(10, new int[]{1600, 21389}, "Cave Crawlers can be found in the Slayer Dungeon."),
    BANSHEE(15, new int[]{1612, 21390}, "Banshees can be found in the Slayer Tower."),
    COCKATRICE(25, new int[]{1621, 21393}, "Cockatrices can be found in the Slayer Dungeon."),
    PYREFIEND(30, new int[]{6216, 21394}, "Pyrefiends can be found in the Slayer Dungeon."),
    BASILISK(40, new int[]{1616, 23287}, "Basilisks can be found in the Slayer Dungeon."),
    INFERNAL_MAGE(45, new int[]{1643, 21396}, "Infernal Mages can be found in the Slayer Tower."),
    BLOODVELD(50, new int[]{161, 21397}, "Bloodvelds can be found in the Slayer Tower."),
    TUROTH(55, new int[]{1626, 24397}, "Turoths can be found in the Slayer Dungeon."),
    CAVE_HORROR(58, new int[]{15047}, "Cave Horrors can be found in the Slayer Dungeon."),
    ABERRANT_SPECTRE(60, new int[]{1604, 21402}, "Aberrant Spectres can be found in Slayer Tower."),
    KURASK(70, new int[]{1608, 14410, 21405}, "Kurasks can be found in the Slayer Dungeon."),
    SKELETAL_WYVERN(72, new int[]{3071}, "Skeletal Wyverns can be found in Asgarnia Ice Dungeon."),
    JUNGLE_STRYKEWYRM(73, new int[]{9467}, "Strykewyrms can be found in the Strykewyrm Cavern"),
    GARGOYLE(75, new int[]{1610, 21407}, "Gargoyles can be found in Slayer Tower."),
    DESERT_STRYKEWYRM(77, new int[]{9465}, "Strykewyrms can be found in the Strykewyrm Cavern."),
    AQUANITE(78, new int[]{9172}, "Aquanites can be found in the Waterbirth Dungeon."),
    NECHRYAEL(80, new int[]{1613, 21411}, "Nechryaels can be found in Slayer Tower."),
    SPIRITUAL_MAGE(83, new int[]{6221, 6231, 6257, 6278}, "Spiritual Mages can be found in Godwars Dungeon"),
    ABYSSAL_DEMON(85, new int[]{1615, 21410}, "Abyssal Demons can be found in Slayer Tower."),
    ABYSSAL_SIRE(85, new int[]{5886}, "Abyssal Sire can be found using the Boss teleport."),
    DARK_BEAST(90, new int[]{2783, 21409}, "Dark Beasts can be found in the Slayer Dungeon."),
    CERBERUS(91, new int[]{1999}, "Cerberus can be found using the Boss teleport."),
    THERMO(93, new int[]{499}, "Thermo can be found using the Boss teleport."),
    ICE_STRYKEWYRM(93, new int[]{9463}, "Strykewyrms can be found in the Strykewyrm Cavern.");


    SlayerMobs(int levelReq, int[] npcId, String npcLocation) {
        this.levelReq = levelReq;
        this.npcId = npcId;
        this.npcLocation = npcLocation;
    }

    private final int levelReq;
    private final int[] npcId;
    private final String npcLocation;

    public int getTaskID() {
        return this.levelReq;
    }

    public int[] getNpcId() {
        return this.npcId;
    }

    public String getNpcLocation() {
        return this.npcLocation;
    }

    public static SlayerMobs forId(int id) {
        for (SlayerMobs tasks : SlayerMobs.values()) {
            if (tasks.ordinal() == id) {
                return tasks;
            }
        }
        return null;
    }

    public static int slayerReqForId(int id) {
        for (SlayerMobs mobs : SlayerMobs.values()) {
            for (int i = 0; i < mobs.npcId.length; i++) {
                if (mobs.npcId[i] == id) {
                    return mobs.levelReq;
                }
            }
        }

        return -1;
    }

    public static int[] slayerUniques = {4153, 15488, 15490, 20580, 6916, 6918, 6920, 6922, 6924, 13290, 4158, 8921, 20564, 222002, 4153, 6746, 220517, 220520, 220595, 6914, 6889, 11732, 4151, 11235, 15486, 18346, 15126};

}
