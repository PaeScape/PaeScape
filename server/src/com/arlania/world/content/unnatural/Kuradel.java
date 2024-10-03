package com.arlania.world.content.unnatural;

public class Kuradel extends UnnaturalMenu {

    public enum KuradelTasks {

        assign_1(new String[]{"Waterfiend", ""}, 25),
        assign_2(new String[]{"Steel", "Dragon"}, 26),
        assign_3(new String[]{"Mithril", "Dragon"}, 27),
        assign_4(new String[]{"Kurask", ""}, 28),
        assign_5(new String[]{"Gargoyle", ""}, 29),
        assign_6(new String[]{"Aquanite", ""}, 30),
        assign_7(new String[]{"Jungle", "Strykewyrm"}, 31),
        assign_8(new String[]{"Desert", "Strykewyrm"}, 32),
        assign_9(new String[]{"Ice", "Strykewyrm"}, 33),
        assign_10(new String[]{"Nechryael", ""}, 34),
        assign_11(new String[]{"Abyssal", "Demon"}, 35),
        assign_12(new String[]{"Dark Beast", ""}, 36);


        private final String[] assignName;
        private final int slayerTaskId;

        KuradelTasks(final String[] assignName, final int slayerTaskId) {
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
