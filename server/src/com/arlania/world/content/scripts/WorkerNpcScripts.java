package com.arlania.world.content.scripts;

public class WorkerNpcScripts extends ScriptMenu {

    public enum WorkerNpc {
        SCRIPT_1(new String[]{"100 Ores", "1m"}, new int[]{2679, 3714, 0}),
        SCRIPT_2(new String[]{"1,000 Ores", "2m"}, new int[]{3561, 9948, 0}),
        SCRIPT_3(new String[]{"10,000 Ores", "5m"}, new int[]{3169, 2982, 0}),
        SCRIPT_4(new String[]{"100 Logs", "1m"}, new int[]{2679, 3714, 0}),
        SCRIPT_5(new String[]{"1,000 Logs", "2m"}, new int[]{3561, 9948, 0}),
        SCRIPT_6(new String[]{"10,000 Logs", "5m"}, new int[]{3169, 2982, 0}),
        SCRIPT_7(new String[]{"100 Herbs", "1m"}, new int[]{2679, 3714, 0}),
        SCRIPT_8(new String[]{"1,000 Herbs", "2m"}, new int[]{3561, 9948, 0}),
        SCRIPT_9(new String[]{"10,000 Herbs", "5m"}, new int[]{3169, 2982, 0}),
        SCRIPT_10(new String[]{"100 Fish", "1m"}, new int[]{2679, 3714, 0}),
        SCRIPT_11(new String[]{"1,000 Fish", "2m"}, new int[]{3561, 9948, 0}),
        SCRIPT_12(new String[]{"10,000 Fish", "5m"}, new int[]{3169, 2982, 0});

        /**
         * Initializing the SCRIPT names.
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
        WorkerNpc(final String[] scriptName, final int[] scriptCoordinates) {
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

