package com.arlania.world.content.seasonal.Interface;

import com.arlania.model.Position;
import com.arlania.world.content.teleport.TeleportData;

public class Minigames extends Seasonal {

    public enum MinigamesTeleports {

        assign_1(new String[]{"Barrows", ""}, 1, TeleportData.BARROWS.getPosition()),
        assign_2(new String[]{"Blast", "Furnace"}, 2, TeleportData.BLAST_FURNACE.getPosition()),
        assign_3(new String[]{"Blast", "Mine"}, 3, TeleportData.BLAST_MINE.getPosition()),
        assign_4(new String[]{"Fight", "Caves"}, 4, TeleportData.FIGHT_CAVES.getPosition()),
        assign_5(new String[]{"Motherlode", "Mine"}, 5, TeleportData.MOTHERLODE_MINE.getPosition()),
        assign_6(new String[]{"Wintertodt", ""}, 6, TeleportData.WINTERTODT.getPosition()),
        assign_7(new String[]{"", ""}, 10, null),
        assign_8(new String[]{"", ""}, 10, null),
        assign_9(new String[]{"", ""}, 10, null),
        assign_10(new String[]{"", ""}, 10, null),
        assign_11(new String[]{"", ""}, 11, null),
        assign_12(new String[]{"", ""}, 12, null);


        private final String[] assignName;
        private final int seasonalUnlockId;
        private final Position position;

        MinigamesTeleports(final String[] assignName, final int seasonalUnlockId, final Position position) {
            this.assignName = assignName;
            this.seasonalUnlockId = seasonalUnlockId;
            this.position = position;
        }

        public String[] getassignName() {
            return assignName;
        }

        public int getSeasonalUnlockId() {
            return seasonalUnlockId;
        }

        public Position getPosition() {
            return position;
        }
    }

}
