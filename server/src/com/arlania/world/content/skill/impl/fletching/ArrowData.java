package com.arlania.world.content.skill.impl.fletching;

public enum ArrowData {

    HEADLESS(52, 314, 53, 15, 1),
    BRONZE(53, 39, 882, 25, 1),
    IRON(53, 40, 884, 50, 15),
    STEEL(53, 41, 886, 75, 30),
    MITHRIL(53, 42, 888, 100, 45),
    ADAMANT(53, 43, 890, 150, 60),
    RUNE(53, 44, 892, 200, 75),
    DRAGON(53, 11237, 11212, 300, 90);

    public int item1, item2, outcome, xp, levelReq;

    ArrowData(int item1, int item2, int outcome, int xp, int levelReq) {
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

    public static ArrowData forArrow(int id) {
        for (ArrowData ar : ArrowData.values()) {
            if (ar.getItem2() == id) {
                return ar;
            }
        }
        return null;
    }
}
