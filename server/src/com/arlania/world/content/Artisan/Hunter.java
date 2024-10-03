package com.arlania.world.content.Artisan;

public class Hunter extends ArtisanMenu {

    public enum HunterTasks {

        assign_1(new String[]{"Kyatt", ""}, 52),
        assign_2(new String[]{"Graahk", ""}, 53),
        assign_3(new String[]{"Larupia", ""}, 54),
        assign_4(new String[]{"Herbibore", ""}, 55),
        assign_5(new String[]{"Baby", "Impling"}, 41),
        assign_6(new String[]{"Young", "Impling"}, 42),
        assign_7(new String[]{"Gourmet", "Impling"}, 43),
        assign_8(new String[]{"Essence", "Impling"}, 45),
        assign_9(new String[]{"Electic", "Impling"}, 46),
        assign_10(new String[]{"Ninja", "Impling"}, 49),
        assign_11(new String[]{"Dragon", "Impling"}, 50),
        assign_12(new String[]{"Kingly", "Impling"}, 51);


        private final String[] assignName;
        private final int skillerTaskId;

        HunterTasks(final String[] assignName, final int skillerTaskId) {
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
