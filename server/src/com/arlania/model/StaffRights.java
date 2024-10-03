package com.arlania.model;

import com.arlania.util.Misc;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;


/**
 * Represents a player's privilege rights.
 *
 * @author Gabriel Hannason
 */

public enum StaffRights {

    /*
     * A regular member of the server.
     */
    PLAYER(-1, null, 0),

    /*
     * A member who has donated to the server.
     */

    DONATOR(60, "<shad=FF7F00>", 1),
    SUPER_DONATOR(45, "<col=787878>", 2),
    EXTREME_DONATOR(30, "<col=D9D919>", 3),
    LEGENDARY_DONATOR(15, "<shad=697998>", 4),
    UBER_DONATOR(0, "<col=0EBFE9>", 5),
    MASTER_DONATOR(0, "@blu@", 6),

    /*
     * A moderator who has more privilege than other regular members and donators.
     */
    MODERATOR(-1, "<col=20B2AA>", 7),

    /*
     * The second-highest-privileged member of the server.
     */
    ADMINISTRATOR(-1, "<col=FFFF64>", 8),

    /*
     * The highest-privileged member of the server
     */
    OWNER(-1, "<col=B40404>", 9),

    /*
     * The Developer of the server, has same rights as the owner.
     */
    DEVELOPER(-1, "<col=fa0505>", 9);




    /*
     * A member who has the ability to help people better.
     */
    //SUPPORT(-1, "@blu@", 6),

//    /*
//     * A member who has been with the server for a long time.
//     */
//    YOUTUBER(30, "<col=CD661D>", 1),
//
//    SUPPORT(60, "<shad=FF7F00>", 1);


    StaffRights(int yellDelaySeconds, String yellHexColorPrefix, int staffRank) {
        this.yellDelay = yellDelaySeconds;
        this.yellHexColorPrefix = yellHexColorPrefix;
        this.staffRank = staffRank;
    }

    private static final ImmutableSet<StaffRights> STAFF = Sets.immutableEnumSet(MODERATOR, ADMINISTRATOR, OWNER, DEVELOPER);
    private static final ImmutableSet<StaffRights> DONATORS = Sets.immutableEnumSet(DONATOR, SUPER_DONATOR, EXTREME_DONATOR, LEGENDARY_DONATOR, UBER_DONATOR, MASTER_DONATOR);

    /*
     * The yell delay for the rank
     * The amount of seconds the player with the specified rank must wait before sending another yell message.
     */
    private final int yellDelay;
    private final String yellHexColorPrefix;
    private final int staffRank;

    public int getYellDelay() {
        return yellDelay;
    }

    public int getStaffRank() {
        return staffRank;
    }

    /*
     * The player's yell message prefix.
     * Color and shadowing.
     */

    public String getYellPrefix() {
        return yellHexColorPrefix;
    }

    /**
     * The amount of loyalty points the rank gain per 4 seconds
     */
    public boolean isStaff() {
        return STAFF.contains(this);
    }

    public boolean isDonator() {
        return DONATORS.contains(this);
    }

    /**
     * Gets the rank for a certain id.
     *
     * @param id The id (ordinal()) of the rank.
     * @return rights.
     */
    public static StaffRights forId(int id) {
        for (StaffRights rights : StaffRights.values()) {
            if (rights.staffRank == id) {
                return rights;
            }
        }
        return null;
    }

    public static StaffRights forStaffRank(int rank) {
        for (StaffRights rights : StaffRights.values()) {
            if (rights.ordinal() == rank) {
                return rights;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return Misc.ucFirst(name().replaceAll("_", " "));
    }
}
