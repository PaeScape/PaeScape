package com.arlania.world.content.skill.impl.fletching;

public enum BoltData {

    BRONZE(314, 9375, 877, 25, 1),
    IRON(314, 9377, 9140, 50, 15),
    STEEL(314, 9378, 9141, 75, 30),
    MITHRIL(314, 9379, 9142, 100, 45),
    ADAMANT(314, 9380, 9143, 150, 60),
    RUNE(314, 9381, 9144, 200, 75);

    public int item1, item2, outcome, xp, levelReq;

    BoltData(int item1, int item2, int outcome, int xp, int levelReq) {
        this.item1 = item1;
        this.item2 = item2;
        this.outcome = outcome;
        this.xp = xp;
        this.levelReq = levelReq;
    }

    public int getItem1() {
        return item1;
    }

    public int getItem2() {
        return item2;
    }

    public int getOutcome() {
        return outcome;
    }

    public int getXp() {
        return xp;
    }

    public int getLevelReq() {
        return levelReq;
    }

    public static BoltData forBolt(int id) {
        for (BoltData ar : BoltData.values()) {
            if (ar.getItem2() == id) {
                return ar;
            }
        }
        return null;
    }
}
