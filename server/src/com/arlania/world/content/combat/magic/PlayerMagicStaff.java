package com.arlania.world.content.combat.magic;

import com.arlania.model.Item;
import com.arlania.model.definitions.WeaponInterfaces.WeaponInterface;
import com.arlania.world.content.skill.impl.runecrafting.RunecraftingData;
import com.arlania.world.entity.impl.player.Player;

/**
 * A set of constants representing the staves that can be used in place of
 * runes.
 *
 * @author lare96
 */




public enum PlayerMagicStaff {



    AIR(new int[]{1381, 1397, 1405}, new int[]{556}),
    WATER(new int[]{1383, 1395, 1403, 18902, 211907, 4454}, new int[]{555}),
    EARTH(new int[]{1385, 1399, 1407}, new int[]{557}),
    FIRE(new int[]{1387, 1393, 1401}, new int[]{554}),
    MUD(new int[]{6562, 6563}, new int[]{555, 557}),
    NIGHTMARE(new int[]{20568, 20569, 20570, 20571}, new int[]{560, 566}),
    SANGUINESTI(new int[]{21005}, new int[]{565, 566}),
    LAVA(new int[]{3053, 3054}, new int[]{554, 557}),
    TANGLE_GUM(new int[]{16977, 16153}, new int[]{556, 558}),
    SEEPING_ELM(new int[]{16979, 16154}, new int[]{556, 558, 557}),
    BLOOD_SPINDLE(new int[]{16981, 16155}, new int[]{556, 558, 554}),
    UTUKU(new int[]{16983, 16156}, new int[]{556, 562, 557}),
    SPINEBEAM(new int[]{16985, 16157}, new int[]{556, 562, 554}),
    BOVISTRANGLER(new int[]{16987, 16158}, new int[]{556, 560, 557}),
    THIGAT(new int[]{16989, 16159}, new int[]{556, 560, 554}),
    CORPSETHORN(new int[]{16991, 16160}, new int[]{556, 565, 557}),
    ENTGALLOW(new int[]{16993, 16161}, new int[]{554, 560, 565, 556}),
    GRAVE_CREEPER(new int[]{16995, 16162}, new int[]{556, 560, 565, 566}),
    CELESTIAL(new int[]{17017, 16173}, new int[]{555, 560, 565});

    //private int airRune = RunecraftingData.RuneData.AIR_RUNE.getRuneID(); //556
    //private int waterRune = RunecraftingData.RuneData.WATER_RUNE.getRuneID(); //555
    //private int earthRune = RunecraftingData.RuneData.EARTH_RUNE.getRuneID(); //557
    //private int fireRune = RunecraftingData.RuneData.FIRE_RUNE.getRuneID(); //554
    //private int mindRune = RunecraftingData.RuneData.MIND_RUNE.getRuneID(); //558
    //private int chaosRune = RunecraftingData.RuneData.CHAOS_RUNE.getRuneID(); //562
    //private int deathRune = RunecraftingData.RuneData.DEATH_RUNE.getRuneID(); //560
    //private int bloodRune = RunecraftingData.RuneData.BLOOD_RUNE.getRuneID(); //565

    //strike
    //bolt
    //blast
    //wave
    //surge

    /**
     * The staves that can be used in place of runes.
     */
    private final int[] staves;

    /**
     * The runes that the staves can be used for.
     */
    private final int[] runes;

    /**
     * Create a new {@link PlayerMagicStaff}.
     *
     * @param itemIds the staves that can be used in place of runes.
     * @param runeIds the runes that the staves can be used for.
     */
    PlayerMagicStaff(int[] itemIds, int[] runeIds) {
        this.staves = itemIds;
        this.runes = runeIds;
    }

    /**
     * Suppress items in the argued array if any of the items match the runes
     * that are represented by the staff the argued player is wielding.
     *
     * @param player        the player to suppress runes for.
     * @param runesRequired the runes to suppress.
     * @return the new array of items with suppressed runes removed.
     */
    public static Item[] suppressRunes(Player player, Item[] runesRequired) {
        if (player.getWeapon() == WeaponInterface.STAFF) {
            for (PlayerMagicStaff m : values()) {
                if (player.getEquipment().containsAny(m.staves)) {
                    for (int id : m.runes) {
                        for (int i = 0; i < runesRequired.length; i++) {
                            if (runesRequired[i] != null && runesRequired[i].getId() == id) {
                                runesRequired[i] = null;
                            }
                        }
                    }
                }
            }
            return runesRequired;
        }
        if (player.getEquipment().contains(20568) ||
                player.getEquipment().contains(20569) ||
                player.getEquipment().contains(20570) ||
                player.getEquipment().contains(20571)) {
            for (PlayerMagicStaff m : values()) {
                if (player.getEquipment().containsAny(m.staves)) {
                    for (int id : m.runes) {
                        for (int i = 0; i < runesRequired.length; i++) {
                            if (runesRequired[i] != null && runesRequired[i].getId() == id) {
                                runesRequired[i] = null;
                            }
                        }
                    }
                }
            }
            return runesRequired;
        }
        return runesRequired;
    }
}

