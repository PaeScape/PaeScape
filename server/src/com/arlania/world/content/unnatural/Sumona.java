package com.arlania.world.content.unnatural;

public class Sumona extends UnnaturalMenu {

    public enum SumonaTasks {

        assign_1(new String[]{"Dag", "Kings"}, 37),
        assign_2(new String[]{"KBD", ""}, 38),
        assign_3(new String[]{"Kalphite", "Queen"}, 39),
        assign_4(new String[]{"Graardor", ""}, 40),
        assign_5(new String[]{"Kreeara", ""}, 41),
        assign_6(new String[]{"Kril", ""}, 42),
        assign_7(new String[]{"Zilyana", ""}, 43),
        assign_8(new String[]{"Giant", "Mole"}, 44),
        assign_9(new String[]{"", ""}, 45),
        assign_10(new String[]{"", ""}, 46),
        assign_11(new String[]{"", ""}, 47),
        assign_12(new String[]{"", ""}, 48);


        private final String[] assignName;
        private final int slayerTaskId;

        SumonaTasks(final String[] assignName, final int slayerTaskId) {
            this.assignName = assignName;
            this.slayerTaskId = slayerTaskId;
        }

        public String[] getassignName() {
            return assignName;
        }

        public int getSlayerTaskId() {
            return slayerTaskId;
        }
    }

}
