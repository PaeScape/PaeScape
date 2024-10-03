package com.arlania.world.content;

import com.google.common.base.Stopwatch;

import java.util.concurrent.TimeUnit;


/**
 * Handles functionality for the npc fight duration timer, utilizing guava's
 * Stopwatch clas.
 *
 * @author Andys1814.
 */
public class NPCFightDuration {

    /**
     * Represents the stopwatch that is utilized in this instance.
     */
    private static Stopwatch stopwatch = Stopwatch.createUnstarted();

    public static void start() {
        stopwatch = Stopwatch.createStarted();
    }

    public static void stop() {
        stopwatch.stop();
    }

    public static void main(String[] args) {
        start();
        for (int i = 0; i <= 5_000_000; i++) {
            System.out.println(i);
        }
        stop();
        System.out.println(stopwatch.elapsed(TimeUnit.SECONDS));
    }


}
