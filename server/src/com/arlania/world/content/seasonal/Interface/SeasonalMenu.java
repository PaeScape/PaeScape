package com.arlania.world.content.seasonal.Interface;

public class SeasonalMenu extends Seasonal {

    public enum SeasonalOptions {

        assign_1(new String[]{"Teleports", "Unlocked: "}, 1),
        assign_2(new String[]{"PaePoints:", ""}, 2),
        assign_3(new String[]{"Crystal Keys", ""}, 3),
        assign_4(new String[]{"Event", "Pass"}, 4),
        assign_5(new String[]{"Ring of", "Fortune"}, 5),
        assign_6(new String[]{"Mystery", "Box"}, 6),
        assign_7(new String[]{"Elite", "Mbox"}, 7),
        assign_8(new String[]{"Supreme", "Mbox"}, 8),
        assign_9(new String[]{"Donator", "Tokens"}, 9),
        assign_10(new String[]{"", ""}, 10),
        assign_11(new String[]{"", ""}, 11),
        assign_12(new String[]{"", ""}, 12);


        private final String[] assignName;
        private final int seasonalUnlockId;

        SeasonalOptions(final String[] assignName, final int seasonalUnlockId) {
            this.assignName = assignName;
            this.seasonalUnlockId = seasonalUnlockId;
        }

        public String[] getassignName() {
            return assignName;
        }

        public int getSeasonalUnlockId() {
            return seasonalUnlockId;
        }
    }

}
