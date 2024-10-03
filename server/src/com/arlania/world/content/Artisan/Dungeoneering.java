package com.arlania.world.content.Artisan;

public class Dungeoneering extends ArtisanMenu {

    public enum DungeoneeringTasks {

        assign_1(new String[]{"Barrows", "Minigame"}, 35),
        assign_2(new String[]{"Barrows", "Raid"}, 36),
        assign_3(new String[]{"GWD", "Raid"}, 37),
        assign_4(new String[]{"Chambers", "of Xeric"}, 38),
        assign_5(new String[]{"Theatre", "of Blood"}, 39),
        assign_6(new String[]{"", ""}, -1),
        assign_7(new String[]{"", ""}, -1),
        assign_8(new String[]{"", ""}, -1),
        assign_9(new String[]{"", ""}, -1),
        assign_10(new String[]{"", ""}, -1),
        assign_11(new String[]{"", ""}, -1),
        assign_12(new String[]{"", ""}, -1);


        private final String[] assignName;
        private final int skillerTaskId;

        DungeoneeringTasks(final String[] assignName, final int skillerTaskId) {
            this.assignName = assignName;
            this.skillerTaskId = skillerTaskId;
        }

        public String[] getassignName() {
            return assignName;
        }

        public int getskillerTaskId() {
            return skillerTaskId;
        }
    }

}
