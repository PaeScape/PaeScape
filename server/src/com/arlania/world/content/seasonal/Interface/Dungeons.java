package com.arlania.world.content.seasonal.Interface;

import com.arlania.model.Position;
import com.arlania.world.content.teleport.TeleportData;

public class Dungeons extends Seasonal {

    public enum DungeonTeleports {

        assign_1(new String[]{"Ancient", "Cavern"}, 1, TeleportData.ANCIENT_CAVERN.getPosition()),
        assign_2(new String[]{"Ape Atoll", "Dungeon"}, 2, TeleportData.APE_ATOLL.getPosition()),
        assign_3(new String[]{"Asgarnia", "Ice Dungeon"}, 3, TeleportData.ASGARNIA_ICE_CAVE.getPosition()),
        assign_4(new String[]{"Brimhaven", "Dungeon"}, 4, TeleportData.BRIMHAVEN_DUNGEON.getPosition()),
        assign_5(new String[]{"Edgeville", "Dungeon"}, 5, TeleportData.EDGEVILLE_DUNGEON.getPosition()),
        assign_6(new String[]{"Slayer", "Dungeon"}, 6, TeleportData.SLAYER_DUNGEON.getPosition()),
        assign_7(new String[]{"Slayer", "Tower"}, 7, TeleportData.SLAYER_TOWER.getPosition()),
        assign_8(new String[]{"Strykewyrm", "Cavern"}, 8, TeleportData.STRYKEWYRM_CAVERN.getPosition()),
        assign_9(new String[]{"Taverly", "Dungeon"}, 9, TeleportData.TAVERLY_DUNG.getPosition()),
        assign_10(new String[]{"Waterbirth", "Dungeon"}, 10, TeleportData.WATERBIRTH_DUNGEON.getPosition()),
        assign_11(new String[]{"", ""}, 11, null),
        assign_12(new String[]{"", ""}, 12, null);


        private final String[] assignName;
        private final int seasonalUnlockId;
        private final Position position;

        DungeonTeleports(final String[] assignName, final int seasonalUnlockId, final Position position) {
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
