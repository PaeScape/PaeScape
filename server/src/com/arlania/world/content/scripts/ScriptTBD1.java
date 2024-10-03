package com.arlania.world.content.scripts;

/**
 * Script class for PKing.
 *
 * @author Tyler
 */

public class ScriptTBD1 extends ScriptMenu {

    public enum TBD1 {
        SCRIPT_1(new String[]{"Green", "Dragons"}, new int[]{3351, 3680, 0}),
        SCRIPT_2(new String[]{"Zombie", "Graveyard"}, new int[]{3166, 3682, 0}),
        SCRIPT_3(new String[]{"Greater", "Demons"}, new int[]{3288, 3886, 0}),
        SCRIPT_4(new String[]{"Wilderness", "Castle"}, new int[]{3005, 3631, 0}),
        SCRIPT_5(new String[]{"West", "Dragons"}, new int[]{2980, 3599, 0}),
        SCRIPT_6(new String[]{"East", "Dragons"}, new int[]{3339, 3667, 0}),
        SCRIPT_7(new String[]{"Chaos", "Altar"}, new int[]{3239, 3619, 0}),
        SCRIPT_8(new String[]{"Rune", "Rocks"}, new int[]{3061, 3886, 0}),
        SCRIPT_9(new String[]{"Mage Bank", "(Level 52)"}, new int[]{3099, 3958, 0}),
        SCRIPT_10(new String[]{"Rogues'", "Castle"}, new int[]{3286, 3922, 0}),
        SCRIPT_11(new String[]{"Ice", "Plateau"}, new int[]{2953, 3901, 0}),
        SCRIPT_12(new String[]{"Safe Pvp", "Arena"}, new int[]{2815, 5511, 0});

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
        TBD1(final String[] scriptName, final int[] scriptCoordinates) {
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
