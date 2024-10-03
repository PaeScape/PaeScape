package com.arlania.world.content.skill.impl.herblore;

public enum UnfinishedPotions {

    GUAM_POTION(91, 249, 1, 199),
    MARRENTILL_POTION(93, 251, 5, 201),
    TARROMIN_POTION(95, 253, 12, 203),
    HARRALANDER_POTION(97, 255, 22, 205),
    RANARR_POTION(99, 257, 30, 207),
    TOADFLAX_POTION(3002, 2998, 34, 3049),
    SPIRIT_WEED_POTION(12181, 12172, 40, 12174),
    IRIT_POTION(101, 259, 45, 209),
    AVANTOE_POTION(103, 261, 50, 211),
    KWUARM_POTION(105, 263, 55,213),
    SNAPDRAGON_POTION(3004, 3000, 63, 3051),
    CADANTINE_POTION(107, 265, 66, 215),
    LANTADYME(2483, 2481, 69, 2485),
    DWARF_WEED_POTION(109, 267, 72, 217),
    TORSTOL_POTION(111, 269, 78, 219),
    WERGALI_POTION(14856, 14854, 85, 14836),

    NOTED_GUAM_POTION(92, 250, 1, 200),
    NOTED_MARRENTILL_POTION(94, 252, 5, 202),
    NOTED_TARROMIN_POTION(96, 254, 12, 204),
    NOTED_HARRALANDER_POTION(98, 256, 22, 206),
    NOTED_RANARR_POTION(100, 258, 30, 208),
    NOTED_TOADFLAX_POTION(3003, 2999, 34, 3050),
    NOTED_SPIRIT_WEED_POTION(12182, 12173, 40, 12175),
    NOTED_IRIT_POTION(102, 260, 45, 210),
    NOTED_AVANTOE_POTION(104, 262, 50, 212),
    NOTED_KWUARM_POTION(106, 264, 55, 214),
    NOTED_SNAPDRAGON_POTION(3005, 3001, 63, 3052),
    NOTED_CADANTINE_POTION(108, 266, 66, 216),
    NOTED_LANTADYME(2484, 2482, 69, 2486),
    NOTED_DWARF_WEED_POTION(110, 268, 72, 218),
    NOTED_TORSTOL_POTION(112, 270, 78, 220),
    NOTED_WERGALI_POTION(14857, 14855, 85, 14837);


    private final int unfinishedPotion;
    private final int herbNeeded;
    private final int levelReq;
    private final int grimyHerbNeeded;

    UnfinishedPotions(int unfinishedPotion, int herbNeeded, int levelReq, int grimyHerbNeeded) {
        this.unfinishedPotion = unfinishedPotion;
        this.herbNeeded = herbNeeded;
        this.levelReq = levelReq;
        this.grimyHerbNeeded = grimyHerbNeeded;
    }

    public int getUnfPotion() {
        return unfinishedPotion;
    }

    public int getHerbNeeded() {
        return herbNeeded;
    }

    public int getGrimyHerbNeeded() {
        return grimyHerbNeeded;
    }

    public int getLevelReq() {
        return levelReq;
    }

    public static UnfinishedPotions forId(int herbId) {
        for (UnfinishedPotions unf : UnfinishedPotions.values()) {
            if (unf.getHerbNeeded() == herbId) {
                return unf;
            }
        }
        for (UnfinishedPotions unf : UnfinishedPotions.values()) {
            if (unf.getGrimyHerbNeeded() == herbId) {
                return unf;
            }
        }
        return null;
    }

}