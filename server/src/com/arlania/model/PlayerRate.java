package com.arlania.model;

import com.arlania.util.Misc;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;


/**
 * Represents a player's privilege Rate.
 *
 * @author Gabriel Hannason
 */

public enum PlayerRate {


    //Rates
    FIVE(5),
    TEN(10),
    FIFTEEN(15),
    TWENTY(20),
    TWENTYFIVE(25),
    THIRTY(30);


    PlayerRate(double experienceGainModifier) {
        this.experienceGainModifier = experienceGainModifier;
    }

    private static final ImmutableSet<PlayerRate> RATES = Sets.immutableEnumSet(FIVE, TEN, FIFTEEN, TWENTY, TWENTYFIVE, THIRTY);

    /*
     * The yell delay for the Rate
     * The amount of seconds the player with the specified Rate must wait before sending another yell message.
     */
    private final double experienceGainModifier;


    public double getExperienceGainModifier() {
        return experienceGainModifier;
    }

    public boolean isRates() {
        return RATES.contains(this);
    }


    /**
     * Gets the Rate for a certain id.
     *
     * @param id The id (ordinal()) of the Rate.
     * @return Rate.
     */
    public static PlayerRate forId(int id) {
        for (PlayerRate Rate : PlayerRate.values()) {
            if (Rate.ordinal() == id) {
                return Rate;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return Misc.ucFirst(name().replaceAll("_", " "));
    }
}
