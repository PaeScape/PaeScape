package com.arlania.world.content.unnatural;

public class Vannaka extends UnnaturalMenu {

    public enum VannakaTasks {

        assign_1(new String[]{"Rock Crab", ""}, 1),
        assign_2(new String[]{"Giant Bat", ""}, 2),
        assign_3(new String[]{"Chaos Druid", ""}, 3),
        assign_4(new String[]{"Hobgoblin", ""}, 4),
        assign_5(new String[]{"Hill Giant", ""}, 5),
        assign_6(new String[]{"Red Spider", ""}, 6),
        assign_7(new String[]{"Baby Blue", "Dragon"}, 7),
        assign_8(new String[]{"Crawling", "Hand"}, 8),
        assign_9(new String[]{"Cave", "Crawler"}, 9),
        assign_10(new String[]{"Banshee", ""}, 10),
        assign_11(new String[]{"Cockatrice", ""}, 11),
        assign_12(new String[]{"Pyrefiend", ""}, 12);


        private final String[] assignName;
        private final int slayerTaskId;

        VannakaTasks(final String[] assignName, final int slayerTaskId) {
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
