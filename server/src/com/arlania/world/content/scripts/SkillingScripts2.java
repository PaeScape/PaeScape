package com.arlania.world.content.scripts;

public class SkillingScripts2 extends ScriptMenu {

    public enum Skilling2 {

        SCRIPT_1(new String[]{"TBD", ""}, new int[]{3056, 9555, 10}),
        SCRIPT_2(new String[]{"TBD", ""}, new int[]{3565, 3313, 0}),
        SCRIPT_3(new String[]{"TBD", " "}, new int[]{3056, 9555, 10}),
        SCRIPT_4(new String[]{"TBD", ""}, new int[]{3056, 9555, 10}),
        SCRIPT_5(new String[]{"TBD", ""}, new int[]{3291, 5452, 0}),
        SCRIPT_6(new String[]{"TBD", ""}, new int[]{3221, 5480, 0}),
        SCRIPT_7(new String[]{"TBD", ""}, new int[]{3056, 9555, 10}),
        SCRIPT_8(new String[]{"TBD", ""}, new int[]{3308, 5454, 0}),
        SCRIPT_9(new String[]{"TBD", " "}, new int[]{3056, 9555, 10}),
        SCRIPT_10(new String[]{"TBD", ""}, new int[]{3056, 9555, 10}),
        SCRIPT_11(new String[]{"TBD", " "}, new int[]{3056, 9555, 10}),
        SCRIPT_12(new String[]{"TBD", " "}, new int[]{3056, 9555, 10});

        /**
         * Initializing the script names.
         */
        private final String[] scriptName;
        /**
         * Initializing the script coordinates.
         */
        private final int[] scriptCoordinates;

        /**
         * Constructing the enumerator.
         *
         * @param scriptName        The name of the script.
         * @param scriptName2       The secondary name of the script.
         * @param scriptCoordinates The coordinates of the script.
         */
        Skilling2(final String[] scriptName, final int[] scriptCoordinates) {
            this.scriptName = scriptName;
            this.scriptCoordinates = scriptCoordinates;
        }

        /**
         * Setting the script name.
         *
         * @return
         */
        public String[] getScriptName() {
            return scriptName;
        }

        /**
         * Setting the script coordinates.
         *
         * @return
         */
        public int[] getCoordinates() {
            return scriptCoordinates;
        }
    }

}
