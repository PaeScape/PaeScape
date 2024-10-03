package com.arlania.world.content.scripts;

public class CombatScripts extends ScriptMenu {

    public enum Combat {
        SCRIPT_1(new String[]{"Fight", "Caves"}, new int[]{2445, 5177, 0}),
        SCRIPT_2(new String[]{"Fight", "Pits"}, new int[]{2399, 5177, 0}),
        SCRIPT_3(new String[]{"Pest", "Control"}, new int[]{2662, 2650, 0}),
        SCRIPT_4(new String[]{"Duel", "Arena"}, new int[]{3364, 3267, 0}),
        SCRIPT_5(new String[]{"Warrior's", "Guild"}, new int[]{2855, 3543, 0}),
        SCRIPT_6(new String[]{"Recipe For", "Disaster"}, new int[]{3080, 3498, 0}),
        SCRIPT_7(new String[]{"Nomad's", "Requeim"}, new int[]{1891, 3177, 0}),
        SCRIPT_8(new String[]{"Zombie", "Slaughter"}, new int[]{3503, 3563, 0}),
        SCRIPT_9(new String[]{" ", " "}, new int[]{2933, 9848, 10}),
        SCRIPT_10(new String[]{" ", " "}, new int[]{3235, 3295, 10}),
        SCRIPT_11(new String[]{" ", " "}, new int[]{3235, 3295, 10}),
        SCRIPT_12(new String[]{" ", " "}, new int[]{2480, 5174, 10});


        /**
         * Initializing the script names.
         */
        private final String[] scriptName;
        /**
         * Initializing the script coordinates.
         */
        private final int[] scriptCoordinates;

        Combat(final String[] scriptName, final int[] scriptCoordinates) {
            this.scriptName = scriptName;
            this.scriptCoordinates = scriptCoordinates;
        }

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
