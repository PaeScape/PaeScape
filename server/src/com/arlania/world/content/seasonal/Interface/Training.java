package com.arlania.world.content.seasonal.Interface;

import com.arlania.model.Position;
import com.arlania.world.content.teleport.TeleportData;

public class Training extends Seasonal {

    public enum TrainingTeleports {

        assign_1(new String[]{"Barbarian", "Agility"}, 1, TeleportData.BARBARIAN_AGILITY.getPosition()),
        assign_2(new String[]{"Crafting", "Guild"}, 2, TeleportData.CRAFTING_GUILD.getPosition()),
        assign_3(new String[]{"Essence", "Mine"}, 3, TeleportData.ESSENCE_MINE.getPosition()),
        assign_4(new String[]{"Farming", "Patch"}, 4, TeleportData.FARMING_PATCH.getPosition()),
        assign_5(new String[]{"Implings", ""}, 5, TeleportData.IMPLINGS.getPosition()),
        assign_6(new String[]{"Mining", "Guild"}, 6, TeleportData.MINING_GUILD.getPosition()),
        assign_7(new String[]{"Piscatoris", "Fishing"}, 7, TeleportData.PISCATORIS_FISHING.getPosition()),
        assign_8(new String[]{"Rimmington", "Mine"}, 8, TeleportData.RIMMINGTON_MINE.getPosition()),
        assign_9(new String[]{"Woodcutting", "Guild"}, 9, TeleportData.WOODCUTTING_GUILD.getPosition()),
        assign_10(new String[]{"", ""}, 10, null),
        assign_11(new String[]{"", ""}, 11, null),
        assign_12(new String[]{"", ""}, 12, null);


        private final String[] assignName;
        private final int seasonalUnlockId;
        private final Position position;

        TrainingTeleports(final String[] assignName, final int seasonalUnlockId, final Position position) {
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
