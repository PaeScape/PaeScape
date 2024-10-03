package com.arlania.world.content.minigames;

import com.arlania.world.entity.impl.player.Player;

/**
 * Handles the MinigameManager.java
 *
 * @author www.rune-server.com/members/auguryps
 * @author Levi Patton
 */
public class MinigameManager {

    /**
     * Represents the allowed type of belongings
     */
    public enum AllowedType {
        /**
         * No inventory items allowed but rest are
         */
        NO_INVENTORY,
        /**
         * No equipment items allowed but rest are
         */
        NO_EQUIPMENT,
        /**
         * No equipment or inventory items but familiars allowed
         */
        NO_ITEMS,
        /**
         * Items allowed but no familiar
         */
        NO_FAMILIAR,
        /**
         * Nothing is allowed
         */
        NOTHING,
    }

    /**
     * Checks the entry
     *
     * @param player the player
     * @return enters
     */
    public static boolean checkEntry(Player player) {
        /**
         * The minigame
         */
        Minigame mini = player.getMinigame();
        /**
         * No minigame
         */
        if (mini == null) {
            return false;
        }
        /**
         * No inventory
         */
        player.getPA().closeAllWindows();
        if (mini.getAllowedType().equals(AllowedType.NO_ITEMS)
                || mini.getAllowedType().equals(AllowedType.NO_INVENTORY)
                || mini.getAllowedType().equals(AllowedType.NOTHING)) {
            for (int i = 0; i < player.getInventory().getItems().length; i++) {
                if (player.getInventory().getItems()[i] != null) {
                    player.sendMessage("You cannot bring any items in!");
                    return false;
                }
            }
        }
        /**
         * No equipment
         */
        if (mini.getAllowedType().equals(AllowedType.NO_ITEMS)
                || mini.getAllowedType().equals(AllowedType.NO_EQUIPMENT)
                || mini.getAllowedType().equals(AllowedType.NOTHING)) {
            for (int i = 0; i < player.getEquipment().getItems().length; i++) {
                if (player.getEquipment().getItems()[i] == null) {
                    player.sendMessage("You cannot bring any equipment!");
                    return false;
                }
            }
        }
        return true;
    }
}