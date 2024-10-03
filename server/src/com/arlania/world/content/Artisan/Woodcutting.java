package com.arlania.world.content.Artisan;

public class Woodcutting extends ArtisanMenu {

    public enum WoodcuttingTasks {

        assign_1(new String[]{"Normal", ""}, 1),
        assign_2(new String[]{"Oak", ""}, 2),
        assign_3(new String[]{"Willow", ""}, 3),
        assign_4(new String[]{"Maple", ""}, 4),
        assign_5(new String[]{"Yew", ""}, 5),
        assign_6(new String[]{"Magic", ""}, 6),
        assign_7(new String[]{"Redwood", ""}, 7),
        assign_8(new String[]{"", ""}, -1),
        assign_9(new String[]{"", ""}, -1),
        assign_10(new String[]{"", ""}, -1),
        assign_11(new String[]{"", ""}, -1),
        assign_12(new String[]{"", ""}, -1);


        private final String[] assignName;
        private final int skillerTaskId;

        WoodcuttingTasks(final String[] assignName, final int skillerTaskId) {
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
