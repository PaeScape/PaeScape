package com.arlania.world.content.combat.magic;

import com.arlania.model.Item;
import com.arlania.model.definitions.WeaponInterfaces.WeaponInterface;
import com.arlania.world.entity.impl.player.Player;

/**
 * A set of constants representing the staves that can be used in place of
 * runes.
 *
 * @author lare96
 */
public enum PlayerMagicTomes {

    FROST(new int[]{18346}, new int[]{555}),
    FIRE(new int[]{20580}, new int[]{554});

    /**
     * The staves that can be used in place of runes.
     */
    private final int[] tomes;

    /**
     * The runes that the staves can be used for.
     */
    private final int[] runes;

    /**
     * Create a new {@link PlayerMagicTomes}.
     *
     * @param itemIds the staves that can be used in place of runes.
     * @param runeIds the runes that the staves can be used for.
     */
    PlayerMagicTomes(int[] itemIds, int[] runeIds) {
        this.tomes = itemIds;
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
            for (PlayerMagicTomes m : values()) {
                if (player.getEquipment().containsAny(m.tomes)) {
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
        if (player.getEquipment().contains(18346) ||
                player.getEquipment().contains(20580)) {
            for (PlayerMagicTomes m : values()) {
                if (player.getEquipment().containsAny(m.tomes)) {
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

