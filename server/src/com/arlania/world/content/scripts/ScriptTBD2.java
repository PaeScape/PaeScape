package com.arlania.world.content.scripts;

public class ScriptTBD2 extends ScriptMenu {
    public enum TBD2 {
        SCRIPT_1(new String[]{"Edgeville", "Dungeon"}, new int[]{3097, 9870, 0}),
        SCRIPT_2(new String[]{"Brimhaven", "Dungeon"}, new int[]{2713, 9564, 0}),
        SCRIPT_3(new String[]{"Taverley", "Dungeon"}, new int[]{2884, 9797, 0}),
        SCRIPT_4(new String[]{"Strykewyrm", "Cavern"}, new int[]{2731, 5095, 0}),
        SCRIPT_5(new String[]{"Ancient", "Cavern"}, new int[]{1745, 5325, 0}),
        SCRIPT_6(new String[]{"Metal", "Dragons"}, new int[]{2711, 9464, 0}),
        SCRIPT_7(new String[]{"Ape Atoll", "Dungeon"}, new int[]{2804, 9146, 0}),
        SCRIPT_8(new String[]{"Slayer", "Tower"}, new int[]{3429, 3538, 0}),
        SCRIPT_9(new String[]{"Fremmenik", "Slayer Dung"}, new int[]{2805, 10001, 0}),
        SCRIPT_10(new String[]{" ", " "}, new int[]{2804, 9146, 10}),
        SCRIPT_11(new String[]{" ", " "}, new int[]{3429, 3538, 10}),
        SCRIPT_12(new String[]{" ", " "}, new int[]{3429, 3538, 10});

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
        TBD2(final String[] scriptName, final int[] scriptCoordinates) {
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
