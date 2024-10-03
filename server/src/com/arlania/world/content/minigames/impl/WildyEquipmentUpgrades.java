package com.arlania.world.content.minigames.impl;

import com.arlania.GameSettings;
import com.arlania.model.GameMode;
import com.arlania.model.Item;
import com.arlania.world.content.clog.CollectionLog.CustomCollection;
import com.arlania.world.content.dialogue.DialogueManager;
import com.arlania.world.entity.impl.player.Player;

/**
 * @author Gabriel Hannason
 * Wrote this quickly!!
 * Handles the RFD quest
 */
public class WildyEquipmentUpgrades {

    public static final int wildUpgradeToken = 4039;


    public static void sacrificeItem(Player player, int item, boolean token) {
        int itemToUpgradeTier = player.getCollectionLog().getItemUpgradeTier(item);
        int nextTier = itemToUpgradeTier + 1;
        int pointsNeeded = 0;

        pointsNeeded = (nextTier * 250);


        if (itemToUpgradeTier == -1) {
            player.getPacketSender().sendMessage("@red@You can't upgrade that item!");
            return;
        }

        if (nextTier > 10) {
            player.getPacketSender().sendMessage("@red@You can only upgrade to Tier 10!");
            return;
        }

        if (player.prestige < nextTier && player.getGameMode() != GameMode.SEASONAL_IRONMAN) {
            player.getPacketSender().sendMessage("@red@You need to be at least " + nextTier + " Prestige to upgrade this item!");
            return;
        }

        if (player.WildyPoints < pointsNeeded) {
            player.getPacketSender().sendMessage("@red@You need " + pointsNeeded + " Wildy Points to upgrade this item to the next tier!");
            return;
        }

        player.setAttribute("item-to-upgrade", item);
        player.setAttribute("item-to-upgrade-tier", itemToUpgradeTier);

        DialogueManager.start(player, 1059);
        if (!token) {
            player.setDialogueActionId(1059);
        } else {
            player.setDialogueActionId(1060);
        }
    }


    public static void upgradeItem(Player player, boolean token) {
        int itemId = player.getAttribute("item-to-upgrade", -1);
        int itemToUpgradeTier = player.getAttribute("item-to-upgrade-tier", -1);
        if (itemId == -1 || itemToUpgradeTier == -1) {
            player.sendMessage("Ruh roh...");
            return;
        }

        int nextTier = itemToUpgradeTier + 1;
        int pointsNeeded = 10000000;

        int[] WildyUniques = GameSettings.WILDYUNIQUES;

        player.getPacketSender().sendInterfaceRemoval();
        //player.getPacketSender().sendMessage("itemId: " + itemId);

        Item item = new Item(itemId);

        if (!player.getInventory().contains(itemId)) {
            player.getPacketSender().sendMessage("@red@Your inventory does not contain this item. Tell Pat if this happens.");
            return;
        }

        boolean upgradeable = false;

        if (!upgradeable)
            for (int wildyUnique : WildyUniques) {
                if (wildyUnique == itemId) {
                    player.getCollectionLog().addKill(CustomCollection.WILDY_UNIQUES.getId());
                    player.getCollectionLog().handleDrop(CustomCollection.WILDY_UNIQUES.getId(), item.getId(), 1);
                    if (!token)
                        player.getInventory().delete(itemId, 1);
                    else
                        player.getInventory().delete(4039, 1);
                    upgradeable = true;
                    pointsNeeded = (nextTier * 250);
                    break;
                }
            }

        if (!upgradeable) {
            player.getPacketSender().sendMessage("@red@That item can't be upgraded!");
            return;
        }

        Item upgradedItem = new Item(itemId);

        if (player.WildyPoints < pointsNeeded && !token) {
            player.getPacketSender().sendMessage("You need " + pointsNeeded + " Wildy Points to do that upgrade.");
            return;
        }

        if (!token)
            player.WildyPoints -= pointsNeeded;


        player.getPacketSender().sendMessage("@red@You sacrifice an item to the Eternal Flame!");
        player.getPacketSender().sendMessage("@red@Your " + upgradedItem.getDefinition().getName() + " is now Tier: " + nextTier);


        player.getCollectionLog().loadUpgrades();
        player.setAttribute("item-to-upgrade", -1);
        player.setAttribute("item-to-upgrade-tier", -1);
    }


}
