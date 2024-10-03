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
public class EquipmentUpgrades {

    public static final int lowUpgrade = 4037;
    public static final int mediumUpgrade = 4036;
    public static final int highUpgrade = 4033;
    public static final int legendaryUpgrade = 4034;
    public static final int masterUpgrade = 4035;
    public static final int customUpgrade = 4041;


    public static void sacrificeItem(Player player, int item, boolean token, int type) {

        final int itemToSacrifice = item;
        item = checkSpecialCases(item);
        int itemToUpgradeTier = player.getCollectionLog().getItemUpgradeTier(item);

        int nextTier = itemToUpgradeTier + 1;
        int pointsNeeded = 0;

        if(type == 7) {
            pointsNeeded = (nextTier * 500);
        }
        else {
            pointsNeeded = (nextTier * 200 * type);
        }

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

        if (player.getPaePoints() < pointsNeeded) {
            player.getPacketSender().sendMessage("@red@You need " + pointsNeeded + " PaePoints to upgrade this item to the next tier!");
            return;
        }

        player.setAttribute("item-to-sacrifice", itemToSacrifice);
        player.setAttribute("item-to-upgrade", item);
        player.setAttribute("item-to-upgrade-tier", itemToUpgradeTier);

        DialogueManager.start(player, 200);
        if (!token) {
            player.setDialogueActionId(200);
        } else {
            player.setDialogueActionId(201);
        }
    }


    public static void upgradeItem(Player player, boolean token) {
        int itemToSacrifice = player.getAttribute("item-to-sacrifice", -1);
        int itemId = player.getAttribute("item-to-upgrade", -1);
        int itemToUpgradeTier = player.getAttribute("item-to-upgrade-tier", -1);
        if (itemId == -1 || itemToUpgradeTier == -1 || itemToSacrifice == -1) {
            player.sendMessage("Ruh roh...");
            return;
        }

        int nextTier = itemToUpgradeTier + 1;
        int pointsNeeded = 10000000;

        int[] CustomUniques = GameSettings.CUSTOMUNIQUES;
        int[] MasterUniques = GameSettings.MASTERUNIQUES;
        int[] LegendaryUniques = GameSettings.LEGENDARYUNIQUES;
        int[] HighUniques = GameSettings.HIGHUNIQUES;
        int[] MediumUniques = GameSettings.MEDIUMUNIQUES;
        int[] LowUniques = GameSettings.LOWUNIQUES;
        int[] UpgradeableUntradeables = GameSettings.UPGRADEABLE_UNTRADEABLES;


        player.getPacketSender().sendInterfaceRemoval();
        //player.getPacketSender().sendMessage("itemId: " + itemId);


        Item item = new Item(itemId);

        if (!player.getInventory().contains(itemToSacrifice)) {
            player.getPacketSender().sendMessage("@red@Your inventory does not contain this item. Tell Pat if this happens.");
            return;
        }

        boolean upgradeable = false;

        if (!upgradeable)
            for (int lowUnique : LowUniques) {
                if (lowUnique == itemId) {
                    if (!token)
                        player.getInventory().delete(itemId, 1);
                    else {
                        if (player.getInventory().getAmount(4037) >= nextTier) {
                            player.getInventory().delete(4037, nextTier);
                            player.getPacketSender().sendMessage("You use " + nextTier + " tokens to upgrade your item.");
                        } else {
                            player.getPacketSender().sendMessage("You need " + nextTier + " tokens to upgrade your item.");
                            return;
                        }
                    }
                    upgradeable = true;
                    pointsNeeded = (nextTier * 200);
                    player.getCollectionLog().addKill(CustomCollection.LOW_UNIQUES.getId());
                    player.getCollectionLog().handleDrop(CustomCollection.LOW_UNIQUES.getId(), item.getId(), 1);
                    break;
                }
            }
        if (!upgradeable)
            for (int mediumUnique : MediumUniques) {
                if (mediumUnique == itemId) {
                    if (!token)
                        player.getInventory().delete(itemId, 1);
                    else {
                        if (player.getInventory().getAmount(4036) >= nextTier) {
                            player.getInventory().delete(4036, nextTier);
                            player.getPacketSender().sendMessage("You use " + nextTier + " tokens to upgrade your item.");
                        } else {
                            player.getPacketSender().sendMessage("You need " + nextTier + " tokens to upgrade your item.");
                            return;
                        }
                    }
                    upgradeable = true;
                    pointsNeeded = (nextTier * 400);
                    player.getCollectionLog().addKill(CustomCollection.MEDIUM_UNIQUES.getId());
                    player.getCollectionLog().handleDrop(CustomCollection.MEDIUM_UNIQUES.getId(), item.getId(), 1);
                    break;
                }
            }
        if (!upgradeable)
            for (int highUnique : HighUniques) {
                if (highUnique == itemId) {
                    if (!token)
                        player.getInventory().delete(itemId, 1);
                    else {
                        if (player.getInventory().getAmount(4033) >= nextTier) {
                            player.getInventory().delete(4033, nextTier);
                            player.getPacketSender().sendMessage("You use " + nextTier + " tokens to upgrade your item.");
                        } else {
                            player.getPacketSender().sendMessage("You need " + nextTier + " tokens to upgrade your item.");
                            return;
                        }
                    }
                    upgradeable = true;
                    pointsNeeded = (nextTier * 600);
                    player.getCollectionLog().addKill(CustomCollection.HIGH_UNIQUES.getId());
                    player.getCollectionLog().handleDrop(CustomCollection.HIGH_UNIQUES.getId(), item.getId(), 1);
                    break;
                }
            }
        if (!upgradeable)
            for (int legendaryUnique : LegendaryUniques) {
                if (legendaryUnique == itemId) {
                    if (!token)
                        player.getInventory().delete(itemId, 1);
                    else {
                        if (player.getInventory().getAmount(4034) >= nextTier) {
                            player.getInventory().delete(4034, nextTier);
                            player.getPacketSender().sendMessage("You use " + nextTier + " tokens to upgrade your item.");
                        } else {
                            player.getPacketSender().sendMessage("You need " + nextTier + " tokens to upgrade your item.");
                            return;
                        }
                    }
                    upgradeable = true;
                    pointsNeeded = (nextTier * 800);
                    player.getCollectionLog().addKill(CustomCollection.LEGENDARY_UNIQUES.getId());
                    player.getCollectionLog().handleDrop(CustomCollection.LEGENDARY_UNIQUES.getId(), item.getId(), 1);
                    break;
                }
            }
        if (!upgradeable)
            for (int masterUnique : MasterUniques) {
                if (masterUnique == itemId) {
                    if (!token)
                        player.getInventory().delete(itemId, 1);
                    else {
                        if (player.getInventory().getAmount(4035) >= nextTier) {
                            player.getInventory().delete(4035, nextTier);
                            player.getPacketSender().sendMessage("You use " + nextTier + " tokens to upgrade your item.");
                        } else {
                            player.getPacketSender().sendMessage("You need " + nextTier + " tokens to upgrade your item.");
                            return;
                        }
                    }
                    upgradeable = true;
                    pointsNeeded = (nextTier * 1000);
                    player.getCollectionLog().addKill(CustomCollection.MASTER_UNIQUES.getId());
                    player.getCollectionLog().handleDrop(CustomCollection.MASTER_UNIQUES.getId(), item.getId(), 1);
                    break;
                }
            }
        if (!upgradeable)
            for (int customUnique : CustomUniques) {
                if (customUnique == itemId) {
                    player.getCollectionLog().addKill(CustomCollection.CUSTOM_UNIQUES.getId());
                    player.getCollectionLog().handleDrop(CustomCollection.CUSTOM_UNIQUES.getId(), item.getId(), 1);
                    if (!token)
                        player.getInventory().delete(itemId, 1);
                    else
                        player.getInventory().delete(4041, 1);
                    upgradeable = true;
                    pointsNeeded = (nextTier * 1200);
                    break;
                }
            }
        if (!upgradeable)
            for (int upgradeableUntradeables : UpgradeableUntradeables) {
                if (upgradeableUntradeables == itemId) {
                    player.getCollectionLog().addKill(CustomCollection.UNTRADEABLES.getId());
                    player.getCollectionLog().handleDrop(CustomCollection.UNTRADEABLES.getId(), item.getId(), 1);
                    player.getInventory().delete(itemId, 1);
                    upgradeable = true;
                    pointsNeeded = (nextTier * 500);
                    break;
                }
            }

        if (!upgradeable) {
            player.getPacketSender().sendMessage("@red@That item can't be upgraded!");
            return;
        }

        Item upgradedItem = new Item(itemId);

        if (player.getPaePoints() < pointsNeeded && !token) {
            player.getPacketSender().sendMessage("You need " + pointsNeeded + " PaePoints to do that upgrade.");
            return;
        }

        if (!token)
            player.setPaePoints(player.getPaePoints() - pointsNeeded);


        player.getPacketSender().sendMessage("@red@You sacrifice an item to the Eternal Flame!");
        player.getPacketSender().sendMessage("@red@Your " + upgradedItem.getDefinition().getName() + " is now Tier: " + nextTier);


        player.getCollectionLog().loadUpgrades();
        player.setAttribute("item-to-upgrade", -1);
        player.setAttribute("item-to-upgrade-tier", -1);
    }

    public static int checkSpecialCases(int itemId) {

        switch (itemId) {

            case 21064:
                return 21063;
            case 21073:
                return 21070;
            case 21074:
                return 21071;
            case 21075:
                return 21072;
            case 21080:
            case 21082:
            case 21084:
                return 21078;


        }

        return itemId;
    }

}
