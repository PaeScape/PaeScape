package com.arlania.world.content.skill.impl.herblore.decanting;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;

/**
 * Represents a decantable potion and it's respective data.
 *
 * @author Andys1814.
 */
public enum NotedDecantablePotion {

    STRENGTH(114, 116, 118, 120),
    ATTACK(2429, 122, 124, 126),
    RESTORE(2431, 128, 130, 132),
    DEFENCE(2433, 134, 136, 138),
    PRAYER(2435, 140, 142, 144),
    FISHING(2439, 151, 153, 155),
    RANGING(2444, 170, 172, 174),
    ANTIFIRE(2453, 2455, 2457, 2459),
    ENERGY(3009, 3011, 3013, 3015),
    AGILITY(3033, 3035, 3037, 3039),
    MAGIC(3041, 3043, 3045, 3047),
    COMBAT(9740, 9742, 9744, 9746),
    SUMMONING(12141, 12143, 12145, 12147),
    SUPER_ATTACK(2437, 146, 148, 150),
    SUPER_STRENGTH(2441, 158, 160, 162),
    SUPER_DEFENCE(2443, 164, 166, 168),
    SUPER_ENERGY(3017, 3019, 3021, 3023),
    SUPER_RESTORE(3025, 3027, 3029, 3031),
    SARADOMIN_BREW(6686, 6688, 6690, 6692);

    private final int[] itemIds;

    NotedDecantablePotion(int... itemIds) {
        this.itemIds = itemIds;
    }

    public static NotedDecantablePotion forId(int itemId) {
        return Arrays.stream(NotedDecantablePotion.values()).filter(potion -> ArrayUtils.contains(potion.getIds(), itemId))
                .findAny().get();
    }

    public int doseForId(int itemId) {
        if (itemId == this.getIds()[0]) {
            return 4;
        }
        if (itemId == this.getIds()[1]) {
            return 3;
        }
        if (itemId == this.getIds()[2]) {
            return 2;
        }
        if (itemId == this.getIds()[3]) {
            return 1;
        }
        return -1;
    }

    public static void main(String[] args) {
        System.out.println(forId(113));
        FISHING.getIds();
    }

    public int[] getIds() {
        return itemIds;
    }

}
