package com.arlania.world.content.unnatural;

public class EvilDave extends UnnaturalMenu {

    public enum EvilDaveTasks {

        assign_1(new String[]{"Frost", "Dragon"}, 63),
        assign_2(new String[]{"Callisto", ""}, 64),
        assign_3(new String[]{"Venenatis", ""}, 65),
        assign_4(new String[]{"Scorpia", ""}, 66),
        assign_5(new String[]{"Chaos", "Elemental"}, 67),
        assign_6(new String[]{"Wildywyrm", ""}, 68),
        assign_7(new String[]{"Vetion", ""}, 69),
        assign_8(new String[]{"Revenants", ""}, 61),
        assign_9(new String[]{"Tormented", "Demon"}, 62),
        assign_10(new String[]{"", ""}, -1),
        assign_11(new String[]{"", ""}, -1),
        assign_12(new String[]{"", ""}, -1);


        private final String[] assignName;
        private final int slayerTaskId;

        EvilDaveTasks(final String[] assignName, final int slayerTaskId) {
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
