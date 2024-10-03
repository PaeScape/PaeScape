package com.arlania.util;

/**
 * This file manages all math formulas that arent already handled in
 * java.
 *
 * @author levi patton
 */

public class MathUtils {

    /**
     * Gets a random number within the {@code range}.
     *
     * @param range The max number possible.
     * @return A random number within the {@code range}.
     */
    public static int random(int range) {
        return (int) (Math.random() * (range + 1));
    }

    /**
     * Gets a random number that is higher than {@code startingRange} and less than {@code endRange}.
     *
     * @param startingRange The lowest-possible generated random number.
     * @param endRange      The highest-possible generated random number.
     * @return A value between the two specified integers.
     */
    public static int random(int startingRange, int endRange) {
        int random = (int) (Math.random() * (endRange + 1));
        while (random < startingRange)
            random = (int) (Math.random() * (endRange + 1));
        return random;
    }

    /**
     * Checks if a number is even (can be divided by 2).
     *
     * @param number The number to check.
     * @return number % 2 != 0.
     */
    public static boolean even(int number) {
        return (number % 2 != 0);
    }

    /**
     * Checks if a number is odd (cannot be divided by 2).
     *
     * @param number The number to check.
     * @return !even({@code number}).
     */
    public static boolean odd(int number) {
        return !even(number);
    }
}
