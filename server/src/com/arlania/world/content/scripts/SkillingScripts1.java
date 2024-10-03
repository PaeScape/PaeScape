package com.arlania.world.content.scripts;

public class SkillingScripts1 extends ScriptMenu {

    public enum Skilling1 {

        SCRIPT_1(new String[]{"Iron", "(Drop)"}, new int[]{1, 0, 0}),
        SCRIPT_2(new String[]{"Coal", "(Bank)"}, new int[]{2, 0, 0}),
        SCRIPT_3(new String[]{"Mithril", "(Bank)"}, new int[]{3, 0, 0}),

        SCRIPT_4(new String[]{"Willow", "(Drop)"}, new int[]{4, 0, 0}),
        SCRIPT_5(new String[]{"Maple", "(Bank)"}, new int[]{5, 0, 0}),
        SCRIPT_6(new String[]{"Yew", "(Bank)"}, new int[]{6, 0, 0}),

        SCRIPT_7(new String[]{"Swordfish", "(Drop)"}, new int[]{7, 0, 0}),
        SCRIPT_8(new String[]{"Monkfish", "(Bank)"}, new int[]{8, 0, 0}),
        SCRIPT_9(new String[]{"Shark", "(Bank)"}, new int[]{9, 0, 0}),

        SCRIPT_10(new String[]{"TBD", ""}, new int[]{10, 0, 0}),
        SCRIPT_11(new String[]{"TBD", ""}, new int[]{11, 0, 0}),
        SCRIPT_12(new String[]{"TBD", ""}, new int[]{12, 0, 0});


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
        Skilling1(final String[] scriptName, final int[] scriptCoordinates) {
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
