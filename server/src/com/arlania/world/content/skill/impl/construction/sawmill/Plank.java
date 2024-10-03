package com.arlania.world.content.skill.impl.construction.sawmill;

public enum Plank {
    NORMAL(1511, 960, 100),
    OAK(1521, 8778, 250),
    TEAK(6333, 8780, 500),
    MAHOGANY(6332, 8782, 1500);

    private int logId;
    private int plankId;
    private int cost;

    Plank(int logId, int plankId, int cost) {
        this.setLogId(logId);
        this.setPlankId(plankId);
        this.setCost(cost);
    }

    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public int getPlankId() {
        return plankId;
    }

    public void setPlankId(int plankId) {
        this.plankId = plankId;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public static Plank forId(int id) {
        for (Plank p : values()) {
            if (p.getLogId() == id || p.getPlankId() == id)
                return p;
        }
        return null;
    }
}
