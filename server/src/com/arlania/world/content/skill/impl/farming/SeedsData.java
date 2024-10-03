package com.arlania.world.content.skill.impl.farming;

public enum SeedsData {
    GUAM(5291, 199,25, 1),
    MARRENTILL(5292, 201, 30, 5),
    TARROMIN(5293, 203, 35, 11),
    HARRALANDER(5294, 205, 40, 20),
    RANARR(5295, 207, 45, 25),
    TOADFLAX(5296, 3049, 50, 30),
    SPIRITWEED(12176, 12174, 55, 35),
    IRIT(5297, 209, 60, 40),
    AVANTOE(5298, 211, 70, 48),
    KWUARM(5299, 213, 75, 54),
    SNAPDRAGON(5300, 3051, 80, 59),
    CADANTINE(5301, 215, 85, 65),
    LANTADYME(5302, 2485, 90, 67),
    DWARFWEED(5303, 217, 95, 70),
    TORSTOL(5304, 219, 100, 75),
    WERGALI(14870, 14836, 120, 85);

    SeedsData(int seedId, int grimyId, int plantXP, int levelReq) {
        this.seedId = seedId;
        this.grimyId = grimyId;
        this.plantXP = plantXP;
        this.levelReq = levelReq;
    }

    private final int seedId;
    private final int grimyId;
    private final int plantXP;
    private final int levelReq;

    public int getSeedID() {
        return this.seedId;
    }
    public int getGrimyID() {
        return this.grimyId;
    }

    public int getPlantingXP() {
        return this.plantXP;
    }

    public int getLevelReq() {
        return this.levelReq;
    }


    public static SeedsData forId(int seed) {
        for (SeedsData farmingData : SeedsData.values()) {
            if (farmingData.getSeedID() == seed) {
                return farmingData;
            }
        }
        return null;
    }

}
