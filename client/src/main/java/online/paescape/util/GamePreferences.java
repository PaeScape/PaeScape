package online.paescape.util;

import java.io.*;

public enum GamePreferences {
    PARTICLES(1);

    /**
     * The file containing the player's saved preferences.
     */
    private static final File SAVED_PREF = new File(Signlink.findcachedir() + ".preference.sim");

    /**
     * The current state of this preference. The number 0 indicates disabled and
     * 1 indicates enabled. However higher numbers can indicate revision.
     */
    private int state = 0;

    /**
     * The revisions the player can choose between for this preference.
     */
    private int[] revisions;

    GamePreferences(int state) {
        this.state = state;
    }

    GamePreferences(int... revisions) {
        this.revisions = revisions;
        this.state = revisions[0];
    }

    public static void load() throws IOException {
        if (SAVED_PREF.exists()) {
            try (FileReader fr = new FileReader(SAVED_PREF); BufferedReader br = new BufferedReader(fr)) {
                for (GamePreferences preference : GamePreferences.values()) {
                    preference.setState(br.read());
                }
            }
        }
    }

    /**
     * Toggles the preference. Do not toggle settings that are revision based.
     * Use {@link #cycle()} instead or set the state directly.
     */
    public void toggle() {
        if (state > 1) {
            new IllegalStateException("You can not toggle this setting.");
        }
        state = toggled() ? 0 : 1;
        save();
    }

    public void cycle() {
        for (int index = 0; index < revisions.length; index++) {
            if (index == revisions[index]) {
                if (index == revisions.length - 1) {
                    state = revisions[0];
                } else {
                    state = revisions[index + 1];
                }
                save();
                return;
            }
        }
    }

    private void save() {
        try {
            if (!SAVED_PREF.exists()) {
                SAVED_PREF.createNewFile();
            }
            try (FileWriter fw = new FileWriter(SAVED_PREF); BufferedWriter bw = new BufferedWriter(fw)) {
                for (GamePreferences preference : GamePreferences.values()) {
                    bw.write(preference.getState());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean toggled() {
        return state == 1;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
