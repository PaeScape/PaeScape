package com.arlania.world.content.skill.impl.fletching;

public enum DartData {

    BRONZE(314, 819, 806, 25, 1),
    IRON(314, 820, 807, 50, 15),
    STEEL(314, 821, 808, 75, 30),
    MITHRIL(314, 822, 809, 100, 45),
    ADAMANT(314, 823, 810, 150, 60),
    RUNE(314, 824, 811, 200, 75),
    DRAGON(314, 11232, 11230, 300, 90);

    public int item1, item2, outcome, xp, levelReq;

    DartData(int item1, int item2, int outcome, int xp, int levelReq) {
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

    public static DartData forDart(int id) {
        for (DartData da : DartData.values()) {
            if (da.getItem2() == id) {
                return da;
            }
        }
        return null;
    }
}
