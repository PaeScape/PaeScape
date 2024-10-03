package com.arlania.util;

/**
 * Handles utility for strings/text.
 *
 * @author Andys1814.
 * @author Ruse for some of the utility methods.
 */
public class TextUtility {

    /**
     * A private constructor, to discourage external instantiation.
     */
    private TextUtility() {

    }

    /**
     * Removes all the spaces from the specified string.
     *
     * @param s
     * @return
     */
    public static String removeSpaces(String string) {
        return string.replace(" ", "");
    }

    /**
     * Replaces all the underscores in the specified string with spaces.
     *
     * @param string
     * @return
     */
    public static String replaceUnderscores(String string) {
        return string.replaceAll("_", " ");
    }

}
