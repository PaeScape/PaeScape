package com.arlania.world.content.seasonal.Interface;

import com.arlania.model.Position;
import com.arlania.world.content.teleport.TeleportData;

public class Raids extends Seasonal {

    public enum RaidsTeleports {

        assign_1(new String[]{"Chambers of Xeric", ""}, 1, TeleportData.CHAMBERS_OF_XERIC.getPosition()),
        assign_2(new String[]{"Raids Lobby", ""}, 2, TeleportData.RAIDS_LOBBY.getPosition()),
        assign_3(new String[]{"Stronghold Raids", ""}, 3, TeleportData.STRONGHOLD_RAIDS.getPosition()),
        assign_4(new String[]{"Theatre of Blood", ""}, 4, TeleportData.THEATRE_OF_BLOOD.getPosition()),
        assign_5(new String[]{"", ""}, 5, null),
        assign_6(new String[]{"", ""}, 6, null),
        assign_7(new String[]{"", ""}, 7, null),
        assign_8(new String[]{"", ""}, 8, null),
        assign_9(new String[]{"", ""}, 9, null),
        assign_10(new String[]{"", ""}, 10, null),
        assign_11(new String[]{"", ""}, 11, null),
        assign_12(new String[]{"", ""}, 12, null);


        private final String[] assignName;
        private final int seasonalUnlockId;
        private final Position position;

        RaidsTeleports(final String[] assignName, final int seasonalUnlockId, final Position position) {
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
