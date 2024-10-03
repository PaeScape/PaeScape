package com.arlania.model;

import com.arlania.util.Misc;


/**
 * This enum contains data used as constants for skill configurations
 * such as experience rates, string id's for interface updating.
 *
 * @author Gabriel Hannason
 */
public enum Skill {

    ATTACK(6247, 25, 0),
    DEFENCE(6253, 25, 6),
    STRENGTH(6206, 25, 3),
    CONSTITUTION(6216, 25, 1),
    RANGED(4443, 25, 9),
    PRAYER(6242, 25, 12),
    MAGIC(6211, 25, 15),
    COOKING(6226, 25, 11),
    WOODCUTTING(4272, 25, 17),
    FLETCHING(6231, 25, 16),
    FISHING(6258, 25, 8),
    FIREMAKING(4282, 25, 14),
    CRAFTING(6263, 25, 13),
    SMITHING(6221, 25, 5),
    MINING(4416, 25, 2),
    HERBLORE(6237, 25, 7),
    AGILITY(4277, 25, 4),
    THIEVING(4261, 25, 10),
    SLAYER(12122, 25, 19),
    FARMING(5267, 25, 20),
    RUNECRAFTING(4267, 25, 18),
    SKILLER(7267, 25, 21),
    HUNTER(8267, 25, 22),
    SUMMONING(9267, 25, 23),
    DUNGEONEERING(10267, 25, 24);

    Skill(int chatboxInterface, int prestigePoints, int prestigeId) {
        this.chatboxInterface = chatboxInterface;
        this.prestigePoints = prestigePoints;
        this.prestigeId = prestigeId;
    }

    /**
     * The skill's chatbox interface
     * The interface which will be sent
     * on levelup.
     */
    private final int chatboxInterface;

    /**
     * The amount of points
     * the player will receive
     * for prestiging the skill.
     */
    private final int prestigePoints;

    /**
     * The button id for prestiging
     * this skill.
     */
    private final int prestigeId;

    /**
     * Gets the Skill's chatbox interface.
     *
     * @return The interface which will be sent on levelup.
     */
    public int getChatboxInterface() {
        return chatboxInterface;
    }

    /**
     * Get's the amount of points the player
     * will receive for prestiging the skill.
     *
     * @return The prestige points reward.
     */
    public int getPrestigePoints() {
        return prestigePoints;
    }


    /**
     * Gets the Skill's name.
     *
     * @return The skill's name in a lower case format.
     */
    public String getName() {
        return toString().toLowerCase();
    }

    /**
     * Gets the Skill's name.
     *
     * @return The skill's name in a formatted way.
     */
    public String getFormatName() {
        return Misc.formatText(getName());
    }

    /**
     * Gets the Skill value which ordinal() matches {@code id}.
     *
     * @param id The index of the skill to fetch Skill instance for.
     * @return The Skill instance.
     */
    public static Skill forId(int id) {
        for (Skill skill : Skill.values()) {
            if (skill.ordinal() == id) {
                return skill;
            }
        }
        return null;
    }

    /**
     * Gets the Skill value which prestigeId matches {@code id}.
     *
     * @param id The skill with matching prestigeId to fetch.
     * @return The Skill instance.
     */
    public static Skill forPrestigeId(int id) {
        for (Skill skill : Skill.values()) {
            if (skill.prestigeId == id) {
                return skill;
            }
        }
        return null;
    }

    /**
     * Gets the Skill value which name matches {@code name}.
     *
     * @param string The name of the skill to fetch Skill instance for.
     * @return The Skill instance.
     */
    public static Skill forName(String name) {
        for (Skill skill : Skill.values()) {
            if (skill.toString().equalsIgnoreCase(name)) {
                return skill;
            }
        }
        return null;
    }

    /**
     * Custom skill multipliers
     *
     * @return multiplier.
     */
    public int getExperienceMultiplier() {

        return 5;
    }
}
