package com.arlania.world.content.skill.impl.herblore;

public enum FinishedPotions {

    MISSING_INGRIDIENTS(-1, -1, -1, -1, -1),
    ATTACK_POTION(121, 91, 221, 1, 25),
    ANTIPOISON(175, 93, 235, 5, 37),
    STRENGTH_POTION(115, 95, 225, 12, 50),
    RESTORE_POTION(127, 97, 223, 22, 62),
    ENERGY_POTION(3010, 97, 1975, 26, 67),
    DEFENCE_POTION(133, 99, 239, 30, 75),
    AGILITY_POTION(3034, 3002, 2152, 34, 80),
    COMBAT_POTION(9741, 97, 9736, 36, 84),
    PRAYER_POTION(139, 99, 231, 38, 87),
    SUMMONING_POTION(12142, 12181, 12109, 40, 91),
    CRAFTING_POTION(14840, 14856, 5004, 42, 95),
    SUPER_ATTACK(145, 101, 221, 45, 100),
    VIAL_OF_STENCH(18661, 101, 1871, 46, 0),
    SUPER_ANTIPOISON(181, 101, 235, 48, 106),
    SUPER_ENERGY(3018, 103, 2970, 52, 117),
    HUNTER_POTION(10000, 103, 10111, 53, 120),
    SUPER_STRENGTH(157, 105, 225, 55, 125),
    FLETCHING_POTION(14848, 103, 11525, 58, 130),
    WEAPON_POISON(187, 105, 241, 60, 137),
    SUPER_RESTORE(3026, 3004, 223, 63, 142),
    SUPER_DEFENCE(163, 107, 239, 66, 150),
    ANTIPOISON_PLUS(5945, 3002, 6049, 68, 155),
    ANTIFIRE(2454, 2483, 241, 69, 157),
    RANGING_POTION(169, 109, 245, 72, 162),
    MAGIC_POTION(3042, 2483, 3138, 76, 172),
    ZAMORAK_BREW(189, 111, 247, 78, 175),
    SARADOMIN_BREW(6687, 3002, 6693, 81, 180),
    RECOVER_SPECIAL(15301, 3018, 5972, 84, 185),
    SUPER_ANTIFIRE(15305, 2454, 4621, 85, 187),
    EXTREME_ATTACK(15309, 145, 261, 84, 185),
    EXTREME_STRENGTH(15313, 157, 267, 86, 187),
    EXTREME_DEFENCE(15317, 163, 2481, 88, 190),
    EXTREME_MAGIC(15321, 3042, 9594, 90, 192),
    EXTREME_RANGED(15325, 169, 12539, 92, 195),
    SUPER_PRAYER(15329, 139, 18834, 94, 197),
    SARADOMIN_BREW_PLUS(14846, 14854, 6685, 98, 200),
    OVERLOAD_PLUS(2438, 14854, 15332, 99, 205),

    NOTED_ATTACK_POTION(122, 92, 221, 1, 25),
    NOTED_ANTIPOISON(176, 94, 235, 5, 37),
    NOTED_STRENGTH_POTION(116, 96, 225, 12, 50),
    NOTED_RESTORE_POTION(128, 98, 223, 22, 62),
    NOTED_ENERGY_POTION(3011, 98, 1975, 26, 67),
    NOTED_DEFENCE_POTION(134, 100, 239, 30, 75),
    NOTED_AGILITY_POTION(3035, 3003, 2152, 34, 80),
    NOTED_COMBAT_POTION(9742, 98, 9736, 36, 84),
    NOTED_PRAYER_POTION(140, 100, 231, 38, 87),
    NOTED_SUMMONING_POTION(12143, 12182, 12109, 40, 91),
    NOTED_CRAFTING_POTION(14841, 14857, 5004, 42, 95),
    NOTED_SUPER_ATTACK(146, 102, 221, 45, 100),
    NOTED_SUPER_ANTIPOISON(182, 102, 235, 48, 106),
    NOTED_SUPER_ENERGY(3019, 104, 2970, 52, 117),
    NOTED_HUNTER_POTION(10001, 104, 10111, 53, 120),
    NOTED_SUPER_STRENGTH(158, 106, 225, 55, 125),
    NOTED_FLETCHING_POTION(14849, 104, 11525, 58, 130),
    NOTED_WEAPON_POISON(188, 106, 241, 60, 137),
    NOTED_SUPER_RESTORE(3027, 3005, 223, 63, 142),
    NOTED_SUPER_DEFENCE(164, 108, 239, 66, 150),
    NOTED_ANTIPOISON_PLUS(5946, 3003, 6049, 68, 155),
    NOTED_ANTIFIRE(2455, 2484, 241, 69, 157),
    NOTED_RANGING_POTION(170, 110, 245, 72, 162),
    NOTED_MAGIC_POTION(3043, 2484, 3138, 76, 172),
    NOTED_ZAMORAK_BREW(190, 112, 247, 78, 175),
    NOTED_SARADOMIN_BREW(6688, 3003, 6693, 81, 180);

    private final int finishedPotion;
    private final int unfinishedPotion;
    private final int itemNeeded;
    private final int levelReq;
    private final int expGained;

    FinishedPotions(int finishedPotion, int unfinishedPotion, int itemNeeded, int levelReq, int expGained) {
        this.finishedPotion = finishedPotion;
        this.unfinishedPotion = unfinishedPotion;
        this.itemNeeded = itemNeeded;
        this.levelReq = levelReq;
        this.expGained = expGained;
    }

    public int getFinishedPotion() {
        return finishedPotion;
    }

    public int getUnfinishedPotion() {
        return unfinishedPotion;
    }

    public int getItemNeeded() {
        return itemNeeded;
    }

    public int getLevelReq() {
        return levelReq;
    }

    public int getExpGained() {
        return expGained;
    }

    public static FinishedPotions forId(int id, int id2) {
        boolean hasOnePart = false;
        for (FinishedPotions pot : FinishedPotions.values()) {
            if ((pot.getUnfinishedPotion() == id || pot.getUnfinishedPotion() == id2)) {
                hasOnePart = true;
            }
            if ((pot.getItemNeeded() == id || pot.getItemNeeded() == id2) && (pot.getUnfinishedPotion() == id || pot.getUnfinishedPotion() == id2)) {
                return pot;
            }
        }
        return hasOnePart ? MISSING_INGRIDIENTS : null;
    }
}