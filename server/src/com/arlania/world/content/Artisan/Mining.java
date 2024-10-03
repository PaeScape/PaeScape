package com.arlania.world.content.Artisan;

public class Mining extends ArtisanMenu {

    public enum MiningTasks {

        assign_1(new String[]{"Copper", ""}, 9),
        assign_2(new String[]{"Tin", ""}, 10),
        assign_3(new String[]{"Iron", ""}, 11),
        assign_4(new String[]{"Coal", ""}, 12),
        assign_5(new String[]{"Gold", ""}, 13),
        assign_6(new String[]{"Mithril", ""}, 14),
        assign_7(new String[]{"Adamantite", ""}, 15),
        assign_8(new String[]{"Runite", ""}, 16),
        assign_9(new String[]{"", ""}, -1),
        assign_10(new String[]{"", ""}, -1),
        assign_11(new String[]{"", ""}, -1),
        assign_12(new String[]{"", ""}, -1);


        private final String[] assignName;
        private final int skillerTaskId;

        MiningTasks(final String[] assignName, final int skillerTaskId) {
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
