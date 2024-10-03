package com.arlania.util.everythingrs.backup;

import java.io.File;

class Pair implements Comparable {
    public long time;
    public File file;

    public Pair(File file) {
        this.file = file;
        this.time = file.lastModified();
    }

    public int compareTo(Object o) {
        long u = ((Pair) o).time;
        return this.time < u ? -1 : (this.time == u ? 0 : 1);
    }
}
