package com.arlania.util;

import com.arlania.model.Item;

import java.security.SecureRandom;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Handles all utility related to psuedo-random element generation.
 *
 * @author Andys1814
 * @author Ruse for some of the utility methods.
 */
public final class RandomUtility {

    /**
     * The singleton instance of {@link Random} utilized in this class.
     */
    public static final Random RANDOM = new Random(new SecureRandom().nextLong());

    private static final Stopwatch timer = new Stopwatch().reset();

    /**
     * A private constructor, to discourage external instantiation.
     */
    private RandomUtility() {

    }

    /**
     * Sequence called in world.java
     * Handles the main method
     * tumbles the random seed every so often
     */
    public static void sequence() {
        if (timer.elapsed(86400000)) { // 24 hours
            timer.reset();
            RANDOM.setSeed(new SecureRandom().nextLong());
        }
    }

    /**
     * Returns a pseudo-random {@code int} value between inclusive
     * <code>min</code> and exclusive <code>max</code>.
     *
     * <br>
     * <br>
     * This method is thread-safe. </br>
     *
     * @param min The minimum inclusive number.
     * @param max The maximum exclusive number.
     * @return The pseudo-random {@code int}.
     * @throws IllegalArgumentException If the specified range is less <tt>0</tt>
     */
    public static int exclusiveRandom(int min, int max) {
        if (max <= min) {
            max = min + 1;
        }
        return RANDOM.nextInt((max - min)) + min;
    }

    /**
     * Returns a pseudo-random {@code int} value between inclusive <tt>0</tt>
     * and exclusive <code>range</code>.
     *
     * <br>
     * <br>
     * This method is thread-safe. </br>
     *
     * @param range The exclusive range.
     * @return The pseudo-random {@code int}.
     * @throws IllegalArgumentException If the specified range is less <tt>0</tt>
     */
    public static int exclusiveRandom(int range) {
        return exclusiveRandom(0, range);
    }

    /**
     * Returns a pseudo-random {@code int} value between inclusive
     * <code>min</code> and inclusive <code>max</code>.
     *
     * @param min The minimum inclusive number.
     * @param max The maximum inclusive number.
     * @return The pseudo-random {@code int}.
     * @throws IllegalArgumentException If {@code max - min + 1} is less than <tt>0</tt>.
     * @see {@link #exclusiveRandom(int)}.
     */
    public static int inclusiveRandom(int min, int max) {
        if (max < min) {
            max = min + 1;
        }
        return exclusiveRandom((max - min) + 1) + min;
    }

    /**
     * Returns a pseudo-random {@code int} value between inclusive <tt>0</tt>
     * and inclusive <code>range</code>.
     *
     * @param range The maximum inclusive number.
     * @return The pseudo-random {@code int}.
     * @throws IllegalArgumentException If {@code max - min + 1} is less than <tt>0</tt>.
     * @see {@link #exclusiveRandom(int)}.
     */
    public static int inclusiveRandom(int range) {
        return inclusiveRandom(0, range);
    }

    /**
     * Picks a random element out of any array type.
     *
     * @param array the array to pick the element from.
     * @return the element chosen.
     */
    public static <T> T randomElement(T[] array) {
        return array[(int) (RANDOM.nextDouble() * array.length)];
    }

    /**
     * Picks a random element out of any list type.
     *
     * @param list the list to pick the element from.
     * @return the element chosen.
     */
    public static <T> T randomElement(List<T> list) {
        return list.get((int) (RANDOM.nextDouble() * list.size()));
    }

    /**
     * Pics a random element out of the specified {@link Set}.
     *
     * @param set The set to pick from.
     * @return the random element chosen.
     */
    public static <T> T randomElement(Set<T> set) {
        int index = RANDOM.nextInt(set.size());
        Iterator<T> iter = set.iterator();
        for (int i = 0; i < index; i++) {
            iter.next();
        }
        return iter.next();
    }

    /**
     * Chooses a random item out of the specified array range of items.
     *
     * @param items The items to chose from.
     * @return The random item that was found.
     */
    public static Item randomItem(Item[] item) {
        return item[inclusiveRandom(item.length)];

    }

}
