package com.arlania.world.content.seasonal.Interface;

import com.arlania.model.Position;
import com.arlania.world.content.teleport.TeleportData;

public class Bosses extends Seasonal {

    public enum BossesTeleports {

        assign_1(new String[]{"Abyssal", "Sire"}, 1, TeleportData.ABYSSAL_SIRE.getPosition()),
        assign_2(new String[]{"Cerberus", ""}, 2, TeleportData.CERBERUS.getPosition()),
        assign_3(new String[]{"Corporeal", "Beast"}, 3, TeleportData.CORPOREAL_BEAST.getPosition()),
        assign_4(new String[]{"Dagannoth", "Kings"}, 4, TeleportData.DAGANNOTH_KINGS.getPosition()),
        assign_5(new String[]{"Giant", "Mole"}, 5, TeleportData.GIANT_MOLE.getPosition()),
        assign_6(new String[]{"Godwars", "Dungeon"}, 6, TeleportData.GODWARS_DUNGEON.getPosition()),
        assign_7(new String[]{"King Black", "Dragon"}, 7, TeleportData.KING_BLACK_DRAGON.getPosition()),
        assign_8(new String[]{"Thermo", "Smoke Devil"}, 8, TeleportData.THERMONUCLEAR.getPosition()),
        assign_9(new String[]{"Nightmare", ""}, 9, null),
        assign_10(new String[]{"Kalphite", "Queen"}, 10, null),
        assign_11(new String[]{"", ""}, 11, null),
        assign_12(new String[]{"", ""}, 12, null);


        private final String[] assignName;
        private final int seasonalUnlockId;
        private final Position position;

        BossesTeleports(final String[] assignName, final int seasonalUnlockId, final Position position) {
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
