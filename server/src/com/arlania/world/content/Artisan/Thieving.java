package com.arlania.world.content.Artisan;

public class Thieving extends ArtisanMenu {

    public enum ThievingTasks {

        assign_1(new String[]{"Man", ""}, 28),
        assign_2(new String[]{"Master Farmer", ""}, 29),
        assign_3(new String[]{"Ardy Knight", ""}, 30),
        assign_4(new String[]{"Hero", ""}, 31),
        assign_5(new String[]{"Tzhaar", ""}, 32),
        assign_6(new String[]{"Vyrewatch", ""}, 33),
        assign_7(new String[]{"", ""}, -1),
        assign_8(new String[]{"", ""}, -1),
        assign_9(new String[]{"", ""}, -1),
        assign_10(new String[]{"", ""}, -1),
        assign_11(new String[]{"", ""}, -1),
        assign_12(new String[]{"", ""}, -1);


        private final String[] assignName;
        private final int skillerTaskId;

        ThievingTasks(final String[] assignName, final int skillerTaskId) {
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
