package com.arlania.world.content.unnatural;

public class Nieve extends UnnaturalMenu {

    public enum NieveTasks {

        assign_1(new String[]{"Corporeal", "Beast"}, 49),
        assign_2(new String[]{"Abyssal", "Sire"}, 50),
        assign_3(new String[]{"Cerberus", ""}, 51),
        assign_4(new String[]{"Thermo", ""}, 52),
        assign_5(new String[]{"Zulrah", ""}, 53),
        assign_6(new String[]{"Jad", ""}, 54),
        assign_7(new String[]{"Nightmare", ""}, 55),
        assign_8(new String[]{"Glacio", ""}, 56),
        assign_9(new String[]{"", ""}, -1),
        assign_10(new String[]{"", ""}, -1),
        assign_11(new String[]{"", ""}, -1),
        assign_12(new String[]{"", ""}, -1);


        private final String[] assignName;
        private final int slayerTaskId;

        NieveTasks(final String[] assignName, final int slayerTaskId) {
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
