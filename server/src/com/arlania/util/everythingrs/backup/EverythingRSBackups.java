//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.arlania.util.everythingrs.backup;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class EverythingRSBackups {
    private static final ScheduledExecutorService service = Executors.newScheduledThreadPool(2);
    private String source;
    private String output;
    private boolean dropbox;
    private String auth;
    private int time = 0;
    private int maxBackups = 2147483647;

    public EverythingRSBackups() {
    }

    public void init() {
        long intialDelay = 0L;
        if (this.getTime() <= 0) {
            this.setTime(3600);
        }

        service.scheduleAtFixedRate(new Runnable() {
            public void run() {
                if (EverythingRSBackups.this.getSource() != null && EverythingRSBackups.this.getOutput() != null && EverythingRSBackups.this.getSource() != "" && EverythingRSBackups.this.getOutput() != "") {
                    String fileName = EverythingRSBackups.this.generateFileName();
                    ZipUtils.init(EverythingRSBackups.this.getSource(), EverythingRSBackups.this.getOutput(), fileName);
                    EverythingRSBackups.this.checkBackups();

                } else {
                    System.out.println("[EverythingRS] Please make sure to enter the backup folder name and the output directory");
                }
            }
        }, intialDelay, this.getTime(), TimeUnit.SECONDS);
    }

    public void checkBackups() {
        String fileSource = this.getOutput();
        File node = new File(fileSource);
        if (node.exists()) {
            if (node.isDirectory()) {
                File[] files = node.listFiles();
                Pair[] pairs = new Pair[files.length];

                int iterations;
                for (iterations = 0; iterations < files.length; ++iterations) {
                    pairs[iterations] = new Pair(files[iterations]);
                }

                Arrays.sort(pairs);
                iterations = 0;

                for (int i = files.length - 1; i >= 0; --i) {
                    files[i] = pairs[i].file;
                    ++iterations;
                    if (iterations > this.getMaxBackups() && files[i].getName().endsWith("[everythingrs].zip")) {
                        files[i].delete();
                    }
                }
            }

        }
    }

    private String generateFileName() {
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("MMM-dd-yyyy HH.mm.ss");
        return dateFormat.format(date) + " [everythingrs].zip";
    }

    public String getSource() {
        return this.source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getOutput() {
        return this.output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public void enableDropbox(String auth) {
        this.dropbox = true;
        this.auth = auth;
    }

    public boolean getDropbox() {
        return this.dropbox;
    }

    public String getAuth() {
        return this.auth;
    }

    public int getTime() {
        return this.time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getMaxBackups() {
        return this.maxBackups;
    }

    public void setMaxBackups(int max) {
        this.maxBackups = max;
    }
}
