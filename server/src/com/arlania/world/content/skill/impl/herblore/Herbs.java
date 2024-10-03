package com.arlania.world.content.skill.impl.herblore;

public enum Herbs {

    GUAM(199, 249, 1, 3),
    MARRENTILL(201, 251, 5, 4),
    TARROMIN(203, 253, 11, 5),
    HARRALANDER(205, 255, 20, 6),
    RANARR(207, 257, 25, 7),
    TOADFLAX(3049, 2998, 30, 8),
    SPIRITWEED(12174, 12172, 35, 9),
    IRIT(209, 259, 40, 9),
    AVANTOE(211, 261, 48, 10),
    KWUARM(213, 263, 54, 11),
    SNAPDRAGON(3051, 3000, 59, 12),
    CADANTINE(215, 265, 65, 13),
    LANTADYME(2485, 2481, 67, 14),
    DWARFWEED(217, 267, 70, 15),
    TORSTOL(219, 269, 75, 16),
    WERGALI(14836, 14854, 85, 17);

    private final int grimyHerb, farmHerb, levelReq, farmingExp;

    Herbs(int grimyHerb, int farmHerb, int levelReq, int farmingExp) {
        this.grimyHerb = grimyHerb;
        this.farmHerb = farmHerb;
        this.levelReq = levelReq;
        this.farmingExp = farmingExp;
    }

    public int getGrimyHerb() {
        return grimyHerb;
    }

    public int getCleanHerb() {
        return farmHerb;
    }

    public int getLevelReq() {
        return levelReq;
    }

    public int getExp() {
        return farmingExp;
    }

    public static Herbs forId(int herbId) {
        for (Herbs herb : Herbs.values()) {
            if (herb.getGrimyHerb() == herbId) {
                return herb;
            }
        }
        return null;
    }

}