package com.arlania.world.content.unnatural;

public class Duradel extends UnnaturalMenu {

    public enum DuradelTasks {

        assign_1(new String[]{"Moss Giant", ""}, 13),
        assign_2(new String[]{"Fire Giant", ""}, 14),
        assign_3(new String[]{"Green Dragon", ""}, 15),
        assign_4(new String[]{"Blue Dragon", ""}, 16),
        assign_5(new String[]{"Hellhound", ""}, 17),
        assign_6(new String[]{"Black Demon", ""}, 18),
        assign_7(new String[]{"Aviansie", ""}, 19),
        assign_8(new String[]{"Basilisk", ""}, 20),
        assign_9(new String[]{"Infernal Mage", ""}, 21),
        assign_10(new String[]{"Bloodveld", ""}, 22),
        assign_11(new String[]{"Turoth", ""}, 23),
        assign_12(new String[]{"Aberrant", "Spectre"}, 24);


        private final String[] assignName;
        private final int slayerTaskId;

        DuradelTasks(final String[] assignName, final int slayerTaskId) {
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
