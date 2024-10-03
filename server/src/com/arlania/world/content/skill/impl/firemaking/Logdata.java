package com.arlania.world.content.skill.impl.firemaking;

import com.arlania.world.entity.impl.player.Player;


public class Logdata {

    public enum logData {

        LOG(1511, 1, 40, 60),
        ACHEY(2862, 1, 40, 60),
        OAK(1521, 15, 60, 60),
        WILLOW(1519, 30, 90, 60),
        TEAK(6333, 35, 105, 60),
        ARCTIC_PINE(10810, 42, 125, 60),
        MAPLE(1517, 45, 135, 60),
        MAHOGANY(6332, 50, 158, 60),
        EUCALYPTUS(12581, 58, 200, 60),
        YEW(1515, 60, 203, 60),
        MAGIC(1513, 75, 304, 60),
        REDWOOD(219669, 90, 400, 60),

        NOTED_LOG(1512, 1, 40, 60),
        NOTED_ACHEY(2863, 1, 40, 60),
        NOTED_OAK(1522, 15, 60, 60),
        NOTED_WILLOW(1520, 30, 90, 60),
        NOTED_TEAK(6334, 35, 105, 60),
        NOTED_ARCTIC_PINE(10811, 42, 125, 60),
        NOTED_MAPLE(1518, 45, 135, 60),
        NOTED_MAHOGANY(6333, 50, 158, 60),
        NOTED_EUCALYPTUS(12582, 58, 200, 60),
        NOTED_YEW(1516, 60, 203, 60),
        NOTED_MAGIC(1514, 75, 304, 60),
        NOTED_REDWOOD(219670, 90, 400, 60);

        private final int logId;
        private final int level;
        private final int burnTime;
        private final int xp;

        logData(int logId, int level, int xp, int burnTime) {
            this.logId = logId;
            this.level = level;
            this.xp = xp;
            this.burnTime = burnTime;
        }

        public int getLogId() {
            return logId;
        }

        public int getLevel() {
            return level;
        }

        public int getXp() {
            return xp;
        }

        public int getBurnTime() {
            return this.burnTime;
        }
    }

    public static logData getLogData(Player p, int log) {
        for (final Logdata.logData l : Logdata.logData.values()) {
            if (log == l.getLogId() || log == -1 && p.getInventory().contains(l.getLogId())) {
                return l;
            }
        }
        return null;
    }

}