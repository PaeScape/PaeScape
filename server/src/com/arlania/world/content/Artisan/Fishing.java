package com.arlania.world.content.Artisan;

public class Fishing extends ArtisanMenu {

    public enum FishingTasks {

        assign_1(new String[]{"Shrimp", ""}, 18),
        assign_2(new String[]{"Trout", ""}, 19),
        assign_3(new String[]{"Salmon", ""}, 20),
        assign_4(new String[]{"Tuna", ""}, 21),
        assign_5(new String[]{"Lobster", ""}, 22),
        assign_6(new String[]{"Swordfish", ""}, 23),
        assign_7(new String[]{"Monkfish", ""}, 24),
        assign_8(new String[]{"Shark", ""}, 25),
        assign_9(new String[]{"Rocktail", ""}, 26),
        assign_10(new String[]{"", ""}, -1),
        assign_11(new String[]{"", ""}, -1),
        assign_12(new String[]{"", ""}, -1);


        private final String[] assignName;
        private final int skillerTaskId;

        FishingTasks(final String[] assignName, final int skillerTaskId) {
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
