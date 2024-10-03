package com.arlania.model.container.impl;

import com.arlania.DiscordBot;
import com.arlania.GameSettings;
import com.arlania.engine.task.TaskManager;
import com.arlania.engine.task.impl.ShopRestockTask;
import com.arlania.model.GameMode;
import com.arlania.model.Item;
import com.arlania.model.Skill;
import com.arlania.model.StaffRights;
import com.arlania.model.container.ItemContainer;
import com.arlania.model.container.StackType;
import com.arlania.model.definitions.ItemDefinition;
import com.arlania.model.input.impl.EnterAmountToBuyFromShop;
import com.arlania.model.input.impl.EnterAmountToSellToShop;
import com.arlania.util.JsonLoader;
import com.arlania.util.Misc;
import com.arlania.world.World;
import com.arlania.world.content.InformationPanel;
import com.arlania.world.content.PlayerLogs;
import com.arlania.world.content.minigames.impl.RecipeForDisaster;
import com.arlania.world.entity.impl.player.Player;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.javacord.api.entity.message.MessageBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * Messy but perfect Shop System
 *
 * @author Gabriel Hannason
 */

public class Shop extends ItemContainer {

    /*
     * The shop constructor
     */
    public Shop(Player player, int id, String name, Item currency, Item[] stockItems) {
        super(player);
        if (stockItems.length > 42)
            throw new ArrayIndexOutOfBoundsException(
                    "Stock cannot have more than 40 items; check shop[" + id + "]: stockLength: " + stockItems.length);
        this.id = id;
        this.name = name.length() > 0 ? name : "General Store";
        this.currency = currency;
        this.originalStock = new Item[stockItems.length];
        for (int i = 0; i < stockItems.length; i++) {
            Item item = new Item(stockItems[i].getId(), stockItems[i].getAmount());
            add(item, false);
            this.originalStock[i] = item;
        }
    }

    private final int id;

    private String name;

    private Item currency;

    private final Item[] originalStock;

    public Item[] getOriginalStock() {
        return this.originalStock;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    public Shop setName(String name) {
        this.name = name;
        return this;
    }

    public Item getCurrency() {
        return currency;
    }

    public Shop setCurrency(Item currency) {
        this.currency = currency;
        return this;
    }

    private boolean restockingItems;

    public boolean isRestockingItems() {
        return restockingItems;
    }

    public void setRestockingItems(boolean restockingItems) {
        this.restockingItems = restockingItems;
    }

    /**
     * Opens a shop for a player
     *
     * @param player The player to open the shop for
     * @return The shop instance
     */
    public Shop open(Player player) {
        setPlayer(player);
        getPlayer().getPacketSender().sendInterfaceRemoval().sendClientRightClickRemoval();
        getPlayer().setShop(ShopManager.getShops().get(id)).setInterfaceId(INTERFACE_ID).setShopping(true);
        refreshItems();
        if (Misc.getMinutesPlayed(getPlayer()) <= 190)
            getPlayer().getPacketSender()
                    .sendMessage("Note: When selling an item to a store, it loses 50% of its value.");
        return this;
    }


    /**
     * Refreshes a shop for every player who's viewing it
     */
    public void publicRefresh() {
        Shop publicShop = ShopManager.getShops().get(id);
        if (publicShop == null)
            return;
        publicShop.setItems(getItems());
        for (Player player : World.getPlayers()) {
            if (player == null)
                continue;
            if (player.getShop() != null && player.getShop().id == id && player.isShopping())
                player.getShop().setItems(publicShop.getItems());
        }
    }

    /**
     * Checks a value of an item in a shop
     *
     * @param player      The player who's checking the item's value
     * @param slot        The shop item's slot (in the shop!)
     * @param sellingItem Is the player selling the item?
     */
    public void checkValue(Player player, int slot, boolean sellingItem) {
        this.setPlayer(player);
        Item shopItem = new Item(getItems()[slot].getId());
        if (!player.isShopping()) {
            player.getPacketSender().sendInterfaceRemoval();
            return;
        }
        Item item = sellingItem ? player.getInventory().getItems()[slot] : getItems()[slot];
        if (item.getId() == 995)
            return;
        if (sellingItem) {
            if (!shopBuysItem(id, item)) {
                player.getPacketSender().sendMessage("You cannot sell this item to this store.");
                return;
            }
            if ((!player.canTransferWealth())) {
                player.getPacketSender().sendMessage("Your game mode can't sell to stores.");
                return;
            }
        }
        int finalValue = 0;
        String finalString = sellingItem ? ItemDefinition.forId(item.getId()).getName() + ": shop will buy for "
                : ItemDefinition.forId(shopItem.getId()).getName() + " currently costs ";
        if (getCurrency().getId() != -1) {
            finalValue = ItemDefinition.forId(item.getId()).getValue();

            String s = currency.getDefinition().getName().toLowerCase().endsWith("s")
                    ? currency.getDefinition().getName().toLowerCase()
                    : currency.getDefinition().getName().toLowerCase() + "s";
            /** CUSTOM CURRENCY, CUSTOM SHOP VALUES **/
            if (id == TOKKUL_EXCHANGE_STORE || id == ENERGY_FRAGMENT_STORE || id == STARDUST_STORE || id == AGILITY_TICKET_STORE ||
                    id == GRAVEYARD_STORE || id == HOLY_WATER_STORE || id == PEST_CONTROL_REWARDS || id == SPECIALTY_STORE ||
                    id == VOTE_STORE || id == CRYSTAL_ARMOR_STORE || id == CRYSTAL_WEAPON_STORE || id == WILDY_POINTS_SHOP) {
                Object[] obj = ShopManager.getCustomShopData(id, item.getId());
                if (obj == null)
                    return;
                finalValue = (int) obj[0];
                s = (String) obj[1];
            }
            if (sellingItem) {
                if (finalValue != 1) {
                    finalValue = (int) (finalValue * 0.50);
                }
            }
            finalString += finalValue + " " + s + shopPriceEx(finalValue) + ".";
        } else {
            Object[] obj = ShopManager.getCustomShopData(id, item.getId());
            if (obj == null)
                return;
            finalValue = (int) obj[0];
            if (sellingItem) {
                if (finalValue != 1) {

                    finalValue = (int) (finalValue * 0.5);
                }
            }
            finalString += finalValue + " " + obj[1] + ".";
        }
        if (player != null && finalValue > 0) {
            player.getPacketSender().sendMessage(finalString);
        }
    }

    public void sellItem(Player player, int slot, int amountToSell) {
        this.setPlayer(player);
        if (!player.isShopping() || player.isBanking()) {
            player.getPacketSender().sendInterfaceRemoval();
            return;
        }

        if (!player.isShopping() || player.isBanking()) {
            player.getPacketSender().sendInterfaceRemoval();
            return;
        }

        if (!player.canTransferWealth()) {
            player.getPacketSender().sendMessage("Your game mode cannot sell to stores.");
            return;
        }

        if (id != GENERAL_STORE) {
            player.getPacketSender().sendMessage("@red@You can only sell items to the General Store!");
            return;
        }
        Item itemToSell = player.getInventory().getItems()[slot];
        if (!itemToSell.sellable()) {
            player.getPacketSender().sendMessage("This item cannot be sold.");
            return;
        }
        if (!shopBuysItem(id, itemToSell)) {
            player.getPacketSender().sendMessage("You cannot sell this item to this store.");
            return;
        }
        if (!player.getInventory().contains(itemToSell.getId()) || itemToSell.getId() == 995)
            return;
        if (this.full(itemToSell.getId()))
            return;
        if (player.getInventory().getAmount(itemToSell.getId()) < amountToSell)
            amountToSell = player.getInventory().getAmount(itemToSell.getId());
        if (amountToSell == 0)
            return;
        /*
         * if(amountToSell > 300) { String s =
         * ItemDefinition.forId(itemToSell.getId()).getName().endsWith("s") ?
         * ItemDefinition.forId(itemToSell.getId()).getName() :
         * ItemDefinition.forId(itemToSell.getId()).getName() + "s";
         * player.getPacketSender().sendMessage("You can only sell 300 "+s+
         * " at a time."); return; }
         */
        int itemId = itemToSell.getId();
        boolean customShop = this.getCurrency().getId() == -1;
        boolean inventorySpace = customShop;
        if (!customShop) {
            if (!itemToSell.getDefinition().isStackable()) {
                if (!player.getInventory().contains(this.getCurrency().getId()))
                    inventorySpace = true;
            }
            if (player.getInventory().getFreeSlots() <= 0
                    && player.getInventory().getAmount(this.getCurrency().getId()) > 0)
                inventorySpace = true;
            if (player.getInventory().getFreeSlots() > 0
                    || player.getInventory().getAmount(this.getCurrency().getId()) > 0)
                inventorySpace = true;
        }
        int itemValue = 0;
        if (getCurrency().getId() > 0) {
            itemValue = ItemDefinition.forId(itemToSell.getId()).getValue();
        } else {
            Object[] obj = ShopManager.getCustomShopData(id, itemToSell.getId());
            if (obj == null)
                return;
            itemValue = (int) obj[0];
        }
        if (itemValue <= 0)
            return;

        itemValue = (int) (itemValue * 0.5);

        if (itemValue <= 0) {
            itemValue = 1;
        }
        for (int i = amountToSell; i > 0; i--) {
            itemToSell = new Item(itemId);
            if (this.full(itemToSell.getId()) || !player.getInventory().contains(itemToSell.getId())
                    || !player.isShopping())
                break;
            if (!itemToSell.getDefinition().isStackable()) {
                if (inventorySpace) {
                    if(player.canTransferWealth()) {
                        super.switchItem(player.getInventory(), this, itemToSell.getId(), amountToSell);
                    }
                    else {
                        player.getInventory().delete(itemToSell.getId(), amountToSell);
                    }
                    if (!customShop) {
                        player.getInventory().add(new Item(getCurrency().getId(), itemValue), false);
                    } else if (id == UNTRADEABLE_STORE) {
                        player.setPaePoints(player.getPaePoints() + itemValue);
                        // Return points here
                    } else if (id == PRESTIGE_STORE) {
                        player.setPaePoints(player.getPaePoints() + itemValue);
                        // Return points here
                    } else if (id == SKILLING_STORE) {
                        player.setPaePoints(player.getPaePoints() + itemValue);
                        // Return points here
                    }
                } else {
                    player.getPacketSender().sendMessage("Please free some inventory space before doing that.");
                    break;
                }
            } else {
                if (inventorySpace) {
                    if(player.canTransferWealth()) {
                        super.switchItem(player.getInventory(), this, itemToSell.getId(), amountToSell);
                    }
                    else {
                        player.getInventory().delete(itemToSell.getId(), amountToSell);
                    }

                    if (!customShop) {
                        player.getInventory().add(new Item(getCurrency().getId(), itemValue * amountToSell), false);
                    } else if (id == UNTRADEABLE_STORE) {
                        player.setPaePoints(player.getPaePoints() + itemValue);
                        // Return points here
                    } else if (id == PRESTIGE_STORE) {
                        player.setPaePoints(player.getPaePoints() + itemValue);
                        // Return points here
                    } else if (id == SKILLING_STORE) {
                        player.setPaePoints(player.getPaePoints() + itemValue);
                        // Return points here
                    }
                    break;
                } else {
                    player.getPacketSender().sendMessage("Please free some inventory space before doing that.");
                    break;
                }
            }
            amountToSell--;
        }
        if (customShop) {
            player.getPointsHandler().refreshPanel();
        }
        player.getInventory().refreshItems();
        InformationPanel.refreshPanel(player);
        fireRestockTask();
        refreshItems();
        publicRefresh();
    }

    /**
     * Buying an item from a shop
     */
    @Override
    public Shop switchItem(ItemContainer to, Item item, int slot, boolean sort, boolean refresh) {
        final Player player = getPlayer();
        if (player == null)
            return this;
        if (!player.isShopping() || player.isBanking()) {
            player.getPacketSender().sendInterfaceRemoval();
            return this;
        }
        if (this.id == GENERAL_STORE) {
            if (player.getGameMode() != GameMode.NORMAL) {
                player.getPacketSender()
                        .sendMessage("Your game mode is not allowed to buy items from the general store.");
                return this;
            }
        }
        if (!shopSellsItem(item))
            return this;

        if (getItems()[slot].getAmount() <= 1 && id != GENERAL_STORE) {

            player.getPacketSender()
                    .sendMessage("The shop can't be 1 items and needs to regenerate some items first..");

        }

        if (item.getAmount() > getItems()[slot].getAmount())
            item.setAmount(getItems()[slot].getAmount());
        int amountBuying = item.getAmount();
        if (id == 21) { //farming cheapfix
            if (getItems()[slot].getAmount() - amountBuying <= 1) {
                amountBuying = getItems()[slot].getAmount() - 1;
                while (getItems()[slot].getAmount() - amountBuying <= 1) {
                    if (getItems()[slot].getAmount() - amountBuying == 1) break;
                    amountBuying--;
                }
            }
        }
        if (getItems()[slot].getAmount() < amountBuying) {
            amountBuying = getItems()[slot].getAmount() - 101;
        }
        if (amountBuying == 0)
            return this;

        if (id == 92)
            amountBuying = 1;

        if (amountBuying > 25000) {
            player.getPacketSender().sendMessage(
                    "You can only buy 25000 " + ItemDefinition.forId(item.getId()).getName() + "s at a time.");
            return this;
        }
        boolean customShop = getCurrency().getId() == -1;
        boolean usePouch = false;
        int playerCurrencyAmount = 0;
        int value = ItemDefinition.forId(item.getId()).getValue();
        String currencyName = "";
        if (getCurrency().getId() != -1) {
            playerCurrencyAmount = player.getInventory().getAmount(currency.getId());
            currencyName = ItemDefinition.forId(currency.getId()).getName().toLowerCase();
            if (currency.getId() == 995) {
                if (player.getMoneyInPouch() >= value) {
                    playerCurrencyAmount = player.getMoneyInPouchAsInt();
                    if (!(player.getInventory().getFreeSlots() == 0
                            && player.getInventory().getAmount(currency.getId()) == value)) {
                        usePouch = true;
                    }
                }
            } else {
                /** CUSTOM CURRENCY, CUSTOM SHOP VALUES **/
                if (id == TOKKUL_EXCHANGE_STORE || id == ENERGY_FRAGMENT_STORE || id == STARDUST_STORE || id == AGILITY_TICKET_STORE
                        || id == GRAVEYARD_STORE || id == HOLY_WATER_STORE || id == PEST_CONTROL_REWARDS ||
                        id == CRYSTAL_ARMOR_STORE || id == CRYSTAL_WEAPON_STORE) {
                    value = (int) ShopManager.getCustomShopData(id, item.getId())[0];
                }
            }
        } else {
            Object[] obj = ShopManager.getCustomShopData(id, item.getId());
            if (obj == null)
                return this;
            value = (int) obj[0];
            currencyName = (String) obj[1];
            if (id == WILDY_POINTS_SHOP) {
                playerCurrencyAmount = player.WildyPoints;
            } //else if (id == VOTING_REWARDS_STORE) {
            //playerCurrencyAmount = player.getPointsHandler().getVotingPoints();}
            else if (id == DUNGEONEERING_STORE) {
                playerCurrencyAmount = player.getRaidPoints();
            } else if (id == DUNGEONEERING_STORE2) {
                playerCurrencyAmount = player.getRaidPoints();
            } else if (id == UNTRADEABLE_STORE) {
                playerCurrencyAmount = player.getPaePoints();
            } else if (id == PRESTIGE_STORE) {
                playerCurrencyAmount = player.getPaePoints();
            } else if (id == SPECIALTY_STORE) {
                playerCurrencyAmount = player.getPaePoints();
            } else if (id == IRONMAN_SPECIALTY_STORE) {
                playerCurrencyAmount = player.getPaePoints();
            } else if (id == SKILLING_STORE) {
                playerCurrencyAmount = player.getPaePoints();
            } else if (id == RAID_POINT_STORE) {
                playerCurrencyAmount = player.getRaidPoints();
            } else if (id == SLAYER_STORE) {
                playerCurrencyAmount = player.getPointsHandler().getSlayerPoints();
            } else if (id == VOTE_STORE) {
                playerCurrencyAmount = player.getVotePoints();
            } else if (id == PEST_CONTROL_REWARDS) {
                playerCurrencyAmount = player.getPointsHandler().getPCPoints();
            } else if (id == LOYALTY_REWARDS) {
                playerCurrencyAmount = player.getPointsHandler().getLoyaltyPoints();
            } else if (id == SEASONAL_SHOP) {
                playerCurrencyAmount = player.seasonalCurrency;
            }
        }
        if (value <= 0) {
            return this;
        }
        if (!hasInventorySpace(player, item, getCurrency().getId(), value)) {
            player.getPacketSender().sendMessage("You do not have any free inventory slots.");
            return this;
        }

        if(!player.canTransferWealth() && player.Shoplifter) {
            if(currency.getId() == 995 || id == UNTRADEABLE_STORE || id == IRONMAN_SPECIALTY_STORE || currency.getId() == 6529) {
                value = 0;
            }
        }

        if ((playerCurrencyAmount <= 0 && !player.Shoplifter) || playerCurrencyAmount < value) {
            player.getPacketSender()
                    .sendMessage("You do not have enough "
                            + ((currencyName.endsWith("s") ? (currencyName) : (currencyName + "s")))
                            + " to purchase this item.");
            return this;
        }
        if (id == SKILLCAPE_STORE_1 || id == SKILLCAPE_STORE_2 || id == SKILLCAPE_STORE_3) {
            for (int i = 0; i < item.getDefinition().getRequirement().length; i++) {
                int req = item.getDefinition().getRequirement()[i];
                if ((i == 3 || i == 5) && req == 99)
                    req *= 10;
                if (req > player.getSkillManager().getMaxLevel(i)) {
                    player.getPacketSender().sendMessage("You need to have at least level 99 in "
                            + Misc.formatText(Skill.forId(i).toString().toLowerCase()) + " to buy this item.");
                    return this;
                }
            }
        } else if (id == GAMBLING_STORE) {
            if (item.getId() == 15084 || item.getId() == 299) {
                if (player.getStaffRights() == StaffRights.PLAYER) {
                    player.getPacketSender().sendMessage("You need to be a member to use these items.");
                    return this;
                }
            }
        }

        for (int i = amountBuying; i > 0; i--) {
            if (!shopSellsItem(item)) {
                break;
            }
            if (getItems()[slot].getAmount() < amountBuying) {
                amountBuying = getItems()[slot].getAmount() - 101;

            }

            if (getItems()[slot].getAmount() <= 1 && id != GENERAL_STORE) {

                player.getPacketSender()
                        .sendMessage("The shop can't be below 1 items and needs to regenerate some items first...");
                break;
            }
            if (!item.getDefinition().isStackable()) {
                if (playerCurrencyAmount >= value && hasInventorySpace(player, item, getCurrency().getId(), value)) {

                    if (!customShop) {
                        if (usePouch) {
                            player.setMoneyInPouch((player.getMoneyInPouch() - value));
                        } else {
                            player.getInventory().delete(currency.getId(), value, false);
                        }
                    } else {
                        if (id == WILDY_POINTS_SHOP) {
                            player.WildyPoints -= value;
                        } //else if (id == VOTING_REWARDS_STORE) {
                        //player.getPointsHandler().setVotingPoints(-value, true);}
                        else if (id == DUNGEONEERING_STORE) {
                            player.setRaidPoints(player.getRaidPoints() - value);
                        } else if (id == DUNGEONEERING_STORE2) {
                            player.setRaidPoints(player.getRaidPoints() - value);
                        } else if (id == UNTRADEABLE_STORE) {
                            player.setPaePoints(player.getPaePoints() - value);
                        } else if (id == PRESTIGE_STORE) {
                            player.setPaePoints(player.getPaePoints() - value);
                        } else if (id == SPECIALTY_STORE) {
                            player.setPaePoints(player.getPaePoints() - value);
                        } else if (id == IRONMAN_SPECIALTY_STORE) {
                            player.setPaePoints(player.getPaePoints() - value);
                        } else if (id == SKILLING_STORE) {
                            player.setPaePoints(player.getPaePoints() - value);
                        } else if (id == RAID_POINT_STORE) {
                            player.setRaidPoints(player.getRaidPoints() - value);
                        } else if (id == SLAYER_STORE) {
                            player.getPointsHandler().setSlayerPoints(-value, true);
                        } else if (id == VOTE_STORE) {
                            player.setVotePoints(player.getVotePoints() - value);
                        } else if (id == LOYALTY_REWARDS) {
                            player.getPointsHandler().setLoyaltyPoints(-value, true);
                        } else if (id == SEASONAL_SHOP) {
                            player.seasonalCurrency -= value;
                        }
                        player.getPointsHandler().refreshPanel();
                    }

                    super.switchItem(to, new Item(item.getId(), 1), slot, false, false);

                    if (id == DONATOR_STORE) {

                        if(player.getGameMode() != GameMode.SEASONAL_IRONMAN) {
                            String donationLog = "[" + Misc.getCurrentServerTime() + "] Username: " + player.getUsername() + " " + player.getGameMode().toString() + " bought " + item.getName() + " for " + value + " donator store tokens.";
                            PlayerLogs.log("Donations", donationLog);

                            if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                                new MessageBuilder().append(donationLog).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.DONATOR_STORE_LOGS).get());
                        }
                    }

                    playerCurrencyAmount -= value;
                } else {
                    break;
                }
            } else {
                if (playerCurrencyAmount >= value && hasInventorySpace(player, item, getCurrency().getId(), value)) {

                    int canBeBought = 0;

                    if(!player.canTransferWealth() && player.Shoplifter) {
                        if(currency.getId() == 995 || id == UNTRADEABLE_STORE || id == IRONMAN_SPECIALTY_STORE || currency.getId() == 6529) {
                            canBeBought = 2000000000;
                        } else {
                            canBeBought = playerCurrencyAmount / (value);
                        }
                    }
                    else {
                        canBeBought = playerCurrencyAmount / (value);
                    }
                    if (canBeBought >= amountBuying) {
                        canBeBought = amountBuying;
                    }
                    if (canBeBought == 0)
                        break;

                    if (!customShop) {
                        if (usePouch) {
                            player.setMoneyInPouch((player.getMoneyInPouch() - ((long) value * canBeBought)));
                        } else {
                            player.getInventory().delete(currency.getId(), value * canBeBought, false);
                        }
                    } else {
                        if (id == WILDY_POINTS_SHOP) {
                            player.WildyPoints -= (value * canBeBought);
                        } //else if (id == VOTING_REWARDS_STORE) {
                        //player.getPointsHandler().setVotingPoints(-value * canBeBought, true);}
                        else if (id == DUNGEONEERING_STORE2) {
                            player.setRaidPoints(player.getRaidPoints() - (value * canBeBought));
                        } else if (id == DUNGEONEERING_STORE2) {
                            player.setRaidPoints(player.getRaidPoints() - (value * canBeBought));
                        } else if (id == UNTRADEABLE_STORE) {
                            player.setPaePoints(player.getPaePoints() - (value * canBeBought));
                        } else if (id == PRESTIGE_STORE) {
                            player.setPaePoints(player.getPaePoints() - (value * canBeBought));
                        } else if (id == SPECIALTY_STORE) {
                            player.setPaePoints(player.getPaePoints() - (value * canBeBought));
                        } else if (id == IRONMAN_SPECIALTY_STORE) {
                            player.setPaePoints(player.getPaePoints() - (value * canBeBought));
                        } else if (id == SKILLING_STORE) {
                            player.setPaePoints(player.getPaePoints() - (value * canBeBought));
                        } else if (id == RAID_POINT_STORE) {
                            player.setRaidPoints(player.getRaidPoints() - (value * canBeBought));
                        } else if (id == SLAYER_STORE) {
                            player.getPointsHandler().setSlayerPoints(-value * canBeBought, true);
                        } else if (id == VOTE_STORE) {
                            player.setVotePoints(player.getVotePoints() - (value * canBeBought));
                        } else if (id == LOYALTY_REWARDS) {
                            player.getPointsHandler().setLoyaltyPoints(-value * canBeBought, true);
                        } else if (id == SEASONAL_SHOP) {
                            player.seasonalCurrency = player.seasonalCurrency - (value * canBeBought);
                        }
                    }
                    super.switchItem(to, new Item(item.getId(), canBeBought), slot, false, false);

                    if (id == DONATOR_STORE) {
                        int total = value * canBeBought;

                        if(player.canTransferWealth()) {
                            String donationLog = "[" + Misc.getCurrentServerTime() + "] Username: " + player.getUsername() + " " + player.getGameMode() + " bought " + canBeBought + " " + item.getName() + " for " + total + " donator store tokens.";
                            PlayerLogs.log("Donations", donationLog);

                            if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                                new MessageBuilder().append(donationLog).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.DONATOR_STORE_LOGS).get());
                        }
                    }

                    playerCurrencyAmount -= value;
                    break;
                } else {
                    break;
                }
            }
            amountBuying--;
        }
        if (!customShop) {
            if (usePouch) {
                player.getPacketSender().sendString(8135, "" + player.getMoneyInPouch()); // Update
                // the
                // money
                // pouch
            }
        } else {
            player.getPointsHandler().refreshPanel();
        }
        player.getInventory().refreshItems();
        fireRestockTask();
        refreshItems();
        publicRefresh();
        return this;
    }

    /**
     * Checks if a player has enough inventory space to buy an item
     *
     * @param item The item which the player is buying
     * @return true or false if the player has enough space to buy the item
     */
    public static boolean hasInventorySpace(Player player, Item item, int currency, int pricePerItem) {
        if (player.getInventory().getFreeSlots() >= 1) {
            return true;
        }
        if (item.getDefinition().isStackable()) {
            if (player.getInventory().contains(item.getId())) {
                return true;
            }
        }
        if (currency != -1) {
            return player.getInventory().getFreeSlots() == 0
                    && player.getInventory().getAmount(currency) == pricePerItem;
        }
        return false;
    }

    @Override
    public Shop add(Item item, boolean refresh) {
        super.add(item, false);
        if (id != RECIPE_FOR_DISASTER_STORE)
            publicRefresh();
        return this;
    }

    @Override
    public int capacity() {
        return 42;
    }

    @Override
    public StackType stackType() {
        return StackType.STACKS;
    }

    @Override
    public Shop refreshItems() {
        if (id == RECIPE_FOR_DISASTER_STORE) {
            RecipeForDisaster.openRFDShop(getPlayer());
            return this;
        }
        for (Player player : World.getPlayers()) {
            if (player == null || !player.isShopping() || player.getShop() == null || player.getShop().id != id)
                continue;
            player.getPacketSender().sendItemContainer(player.getInventory(), INVENTORY_INTERFACE_ID);
            player.getPacketSender().sendItemContainer(ShopManager.getShops().get(id), ITEM_CHILD_ID);
            player.getPacketSender().sendString(NAME_INTERFACE_CHILD_ID, name);
            if (player.getInputHandling() == null || !(player.getInputHandling() instanceof EnterAmountToSellToShop
                    || player.getInputHandling() instanceof EnterAmountToBuyFromShop))
                player.getPacketSender().sendInterfaceSet(INTERFACE_ID, INVENTORY_INTERFACE_ID - 1);
        }
        return this;
    }

    @Override
    public Shop full() {
        getPlayer().getPacketSender().sendMessage("The shop is currently full. Please come back later.");
        return this;
    }

    public String shopPriceEx(int shopPrice) {
        String ShopAdd = "";
        if (shopPrice >= 1000 && shopPrice < 1000000) {
            ShopAdd = " (" + (shopPrice / 1000) + "K)";
        } else if (shopPrice >= 1000000) {
            ShopAdd = " (" + (shopPrice / 1000000) + " million)";
        }
        return ShopAdd;
    }

    private boolean shopSellsItem(Item item) {
        return contains(item.getId());
    }

    public void fireRestockTask() {
        if (isRestockingItems() || fullyRestocked())
            return;
        setRestockingItems(true);
        TaskManager.submit(new ShopRestockTask(this));
    }

    public void restockShop() {
        for (int shopItemIndex = 0; shopItemIndex < getOriginalStock().length; shopItemIndex++) {
            int currentStockAmount = getItems()[shopItemIndex].getAmount();
            add(getItems()[shopItemIndex].getId(), getOriginalStock()[shopItemIndex].getAmount() - currentStockAmount);
//			publicRefresh();
            refreshItems();
        }

    }

    public boolean fullyRestocked() {
        if (id == GENERAL_STORE) {
            return getValidItems().size() == 0;
        } else if (id == RECIPE_FOR_DISASTER_STORE) {
            return true;
        }
        if (getOriginalStock() != null) {
            for (int shopItemIndex = 0; shopItemIndex < getOriginalStock().length; shopItemIndex++) {
                if (getItems()[shopItemIndex].getAmount() != getOriginalStock()[shopItemIndex].getAmount())
                    return false;
            }
        }
        return true;
    }

    public static boolean shopBuysItem(int shopId, Item item) {
        if (shopId == GENERAL_STORE) {
            for (int i = 0; i < GameSettings.UNTRADEABLE_ITEMS.length; i++) {
                if (item.getId() == GameSettings.UNTRADEABLE_ITEMS[i])
                    return false;
            }

            for (int j = 0; j < GameSettings.PETS.length; j++) {
                if (item.getId() == GameSettings.PETS[j])
                    return false;
            }

            return true;
        }
        if (shopId == DUNGEONEERING_STORE || shopId == DUNGEONEERING_STORE2 || shopId == UNTRADEABLE_STORE
                || shopId == WILDY_POINTS_SHOP
                || shopId == RECIPE_FOR_DISASTER_STORE || shopId == HOLY_WATER_STORE
                || shopId == ENERGY_FRAGMENT_STORE || shopId == AGILITY_TICKET_STORE || shopId == GRAVEYARD_STORE
                || shopId == STARDUST_STORE || shopId == SLAYER_STORE
                || shopId == RAID_POINT_STORE || shopId == LOYALTY_REWARDS)
            return false;
        Shop shop = ShopManager.getShops().get(shopId);
        if (shop != null && shop.getOriginalStock() != null) {
            for (Item it : shop.getOriginalStock()) {
                if (it != null && it.getId() == item.getId())
                    return true;
            }
        }
        return false;
    }

    public static class ShopManager {

        private static final Map<Integer, Shop> shops = new HashMap<Integer, Shop>();

        public static Map<Integer, Shop> getShops() {
            return shops;
        }

        public static JsonLoader parseShops() {
            return new JsonLoader() {
                @Override
                public void load(JsonObject reader, Gson builder) {
                    int id = reader.get("id").getAsInt();
                    String name = reader.get("name").getAsString();
                    Item[] items = builder.fromJson(reader.get("items").getAsJsonArray(), Item[].class);
                    Item currency = new Item(reader.get("currency").getAsInt());
                    shops.put(id, new Shop(null, id, name, currency, items));
                }

                @Override
                public String filePath() {
                    return "./data/def/json/world_shops.json";
                }
            };
        }

        public static Object[] getCustomShopData(int shop, int item) {
            if (shop == WILDY_POINTS_SHOP) {
                switch (item) {
                    case 221880:
                    case 221326:
                        return new Object[]{2, "Wildy Points"};
                    case 213195:
                        return new Object[]{250, "Wildy Points"};
                    case 13920:
                    case 13938:
                    case 13950:
                        return new Object[]{1000, "Wildy Points"};
                    case 13923:
                    case 13926:
                    case 13929:
                    case 13941:
                        return new Object[]{2000, "Wildy Points"};
                    case 13908:
                    case 13914:
                    case 13911:
                    case 13917:
                    case 13932:
                    case 13935:
                    case 13944:
                    case 13947:
                        return new Object[]{1500, "Wildy Points"};

                }
            } else if (shop == STARDUST_STORE) {
                switch (item) {
                    case 18831:
                        return new Object[]{5, "stardust"};

                    case 6924:
                    case 6916:
                    case 6918:
                        return new Object[]{100, "stardust"};

                    case 6585:
                    case 2581:
                    case 2577:
                        return new Object[]{200, "stardust"};

                    case 15259:
                    case 13661:
                        return new Object[]{350, "stardust"};
                    case 2417:
                    case 2415:
                    case 2416:
                        return new Object[]{500, "stardust"};

                    case 11704:
                    case 11706:
                    case 11708:

                        return new Object[]{650, "stardust"};
                    case 11710:
                    case 11712:
                    case 11714:
                        return new Object[]{120, "stardust"};
                    case 15606:
                    case 15608:
                    case 15610:
                    case 15612:
                    case 15614:
                    case 15616:
                    case 15618:
                    case 15620:
                    case 15622:
                        return new Object[]{500, "stardust"};
                }
            } else if (shop == ENERGY_FRAGMENT_STORE) {
                switch (item) {
                    case 13632:
                    case 13633:
                    case 13634:
                    case 13635:
                    case 13636:
                    case 13637:
                    case 13638:
                    case 13639:
                    case 13640:
                        return new Object[]{500, "energy fragments"};
                    case 13641:
                        return new Object[]{750, "energy fragments"};
                    case 13642:
                        return new Object[]{1500, "energy fragments"};
                }
                return new Object[]{250, "energy fragments"};
            } else if (shop == HOLY_WATER_STORE) {
                switch (item) {
                    case 18337:
                        return new Object[]{350, "Holy Waters"};
                    case 20010:
                    case 20011:
                    case 20012:
                    case 20009:
                    case 20020:
                    case 10551:
                        return new Object[]{500, "Holy Waters"};
                    case 11720:
                    case 11718:
                    case 11722:
                    case 11724:
                    case 11726:
                    case 13239:
                    case 12708:
                    case 13235:
                        return new Object[]{650, "Holy Waters"};
                    case 9921:
                    case 9922:
                    case 9923:
                    case 9924:
                    case 9925:
                        return new Object[]{3750, "Holy Waters"};
                    case 4084:
                    case 10735:
                        return new Object[]{7500, "Holy Waters"};
                    case 19045:
                        return new Object[]{15000, "Holy Waters"};
                    case 18931:
                        return new Object[]{50000, "Holy Waters"};
                    case 962:
                        return new Object[]{250000, "Holy Waters"};
                }
                return new Object[]{10000, "Holy Waters"};
            } else if (shop == LOYALTY_REWARDS) {
                switch (item) {
                    case 4020:
                    case 4021:
                    case 4022:
                    case 4023:
                    case 4024:
                    case 4025:
                    case 4027:
                    case 4029:
                    case 4030:
                    case 4031:
                    case 6199:
                    case 2677:
                        return new Object[]{25000, "Loyalty points"};
                    case 4028:
                    case 4032:
                        return new Object[]{50000, "Loyalty points"};
                    case 15501:
                        return new Object[]{125000, "Loyalty points"};
                }
                return new Object[]{250, "energy fragments"};
            } else if (shop == UNTRADEABLE_STORE) {
                switch (item) {
                    case 13663:
                    case 8842:
                    case 8850:
                        return new Object[]{50, "PaePoints"};
                    case 11665:
                    case 11664:
                    case 11663:
                    case 10506:
                    case 7409:
                    case 775:
                    case 776:
                    case 18338:
                    case 18339:
                    case 10075:
                    case 2946:
                    case 2949:
                    case 2950:
                    case 2951:
                    case 7509:
                    case 3702:
                    case 20072:
                        return new Object[]{100, "PaePoints"};
                    case 8839:
                    case 8840:
                        return new Object[]{150, "PaePoints"};
                    case 19780:
                    case 10551:
                    case 13263:
                    case 1580:
                        return new Object[]{250, "PaePoints"};
                    case 611:
                    case 18337:
                    case 6500:
                    case 19675:
                        return new Object[]{300, "PaePoints"};
                    case 19999:
                    case 19994:
                    case 20027:
                    case 20003:
                    case 20007:
                    case 20015:
                    case 20023:
                    case 786:
                    case 4428:
                    case 20039:
                    case 19785:
                    case 19786:
                    case 212791:
                        return new Object[]{500, "PaePoints"};
                    case 20008:
                        return new Object[]{750, "PaePoints"};
                    case 19998:
                    case 20035:
                        return new Object[]{1000, "PaePoints"};
                    case 21034:
                        return new Object[]{5000, "PaePoints"};


                }
            } else if (shop == PRESTIGE_STORE) {
                switch (item) {

                    case 600:
                    case 730:
                    case 221177:
                    case 221183:
                    case 13659:
                        return new Object[]{5000, "PaePoints"};
                    case 213392:
                    case 221140:
                        return new Object[]{10000, "PaePoints"};


                }
            } else if ((shop == SPECIALTY_STORE) || (shop == IRONMAN_SPECIALTY_STORE)) {
                switch (item) {

                    case 6686:
                    case 3025:
                    case 12031:
                        return new Object[]{1, "PaePoints"};
                    case 15308:
                    case 15300:
                    case 15312:
                    case 15316:
                    case 15320:
                    case 15324:
                    case 12093:
                    case 12790:
                        return new Object[]{5, "PaePoints"};
                    case 15328:
                        return new Object[]{10, "PaePoints"};
                    case 4604:
                    case 707:
                    case 15332:
                        return new Object[]{25, "PaePoints"};
                    case 985:
                    case 987:
                        return new Object[]{50, "PaePoints"};
                    case 989:
                        return new Object[]{100, "PaePoints"};
                    case 4562:
                        return new Object[]{250, "PaePoints"};
                    case 20027:
                        return new Object[]{500, "PaePoints"};
                    case 20008:
                        return new Object[]{750, "PaePoints"};
                    //case 601:
                    //case 6199:
                    //return new Object[] {2500, "PaePoints" };
                    //case 15501:
                    //return new Object[] {25000, "PaePoints" };
                    case 2957:
                        return new Object[]{2500, "PaePoints"};
                    //case 602:
                    //return new Object[] {12500, "PaePoints" };
                    //case 603:
                    //return new Object[] {50000, "PaePoints" };
                }
            } else if (shop == SKILLING_STORE) {
                switch (item) {
                    case 213258:
                    case 213259:
                    case 213260:
                    case 213261:
                    case 210941:
                    case 210939:
                    case 210940:
                    case 210933:
                    case 213646:
                    case 213642:
                    case 213640:
                    case 213644:
                    case 212013:
                    case 212014:
                    case 212015:
                    case 212016:
                    case 221345:
                    case 219988:
                    case 220708:
                    case 220704:
                    case 220706:
                    case 220710:
                    case 220712:
                    case 5553:
                    case 5554:
                    case 5555:
                    case 5556:
                    case 5557:
                        return new Object[]{50, "PaePoints"};
                    //case 15332:
                    //case 989:
                    //return new Object[] {100, "PaePoints" };
                    case 20046:
                    case 10069:
                        return new Object[]{100, "PaePoints"};
                }
            } else if (shop == VOTE_STORE) {
                switch (item) {

                    case 989:
                        return new Object[]{1, "Vote Points"};
                    case 19864:
                    case 8851:
                        return new Object[]{5, "Vote Points"};
                    case 601:
                    case 6199:
                        return new Object[]{25, "Vote Points"};

                    case 1767:
                    case 1773:
                    case 2696:
                    case 206955:
                    case 1763:
                    case 2697:
                    case 1765:
                        return new Object[]{10, "Vote Points"};


                    case 2677: //250 Paepoints
                        return new Object[]{25, "Vote Points"};
                    case 2678: //500 Paepoints
                    case 15356: //$5 bond
                        return new Object[]{50, "Vote Points"};
                    case 2679: //1000 Paepoints
                    case 9941: //Tryout
                        return new Object[]{100, "Vote Points"};
                    case 2680: //2500 Paepoints
                    case 15501: //Elite Mystery Box
                    case 224373: //Pet Frog
                        return new Object[]{250, "Vote Points"};
                }
            } else if (shop == DONATOR_STORE) {
                switch (item) {
                    case 4020:
                    case 4021:
                    case 4022:
                    case 4023:
                    case 4024:
                    case 4025:
                    case 4027:
                    case 2957:
                    case 6199:
                    case 225316:
                        return new Object[]{5, "Donation Store Coins"};
                    case 4028:
                    case 4029:
                    case 4030:
                    case 4031:
                    case 4032:
                    case 7020:
                    case 7021:
                    case 7022:
                    case 7023:
                    case 7024:
                    case 7025:
                    case 7026:
                    case 7027:
                    case 7029:
                    case 7030:
                    case 7031:
                    case 7033:
                    case 7034:
                    case 911: //Sapphire Subscription
                        return new Object[]{10, "Donation Store Coins"};
                    case 6769://Event Pass
                    case 7035:
                    case 7028:
                    case 7040:
                    case 7042:
                    case 7049:
                    case 15501:
                    case 6854://Equipment Upgrade Box
                    case 6550://Pet Lazy Cat
                    case 8740://Pet Duradead
                        return new Object[]{15, "Donation Store Coins"};
                    case 2683://Starter pack
                    case 7032:
                    case 7041:
                    case 7043:
                    case 7045:
                    case 7046:
                    case 7047:
                    case 7048:
                    case 6758://Battle Pass
                    case 912: //Emerald Subscription
                    case 212355: //Pet Hatius
                        return new Object[]{20, "Donation Store Coins"};
                    case 962://Christmas Cracker
                    case 11157://Genie Lamp
                    case 212399://Wise Old Man
                    case 603: //Supreme mystery box
                    case 704:
                    case 6749: //Mystery Pass
                    case 2795: //BINGO card
                        return new Object[]{25, "Donation Store Coins"};
                    case 913: //Ruby Subscription
                        return new Object[]{30, "Donation Store Coins"};
                    case 914: //Diamond Subscription
                        return new Object[]{40, "Donation Store Coins"};
                    case 915: //Dragonstone Subscription
                    case 2684: //Elite pack
                        return new Object[]{50, "Donation Store Coins"};

                    case 2677: //500 Paepoints
                        return new Object[]{10, "Donation Store Coins"};
                    case 2678: //1500 Paepoints
                        return new Object[]{20, "Donation Store Coins"};
                    case 2681: //5000 Paepoints
                        return new Object[]{50, "Donation Store Coins"};


                }
                return new Object[]{100, "Donation Points"};
            } else if (shop == SEASONAL_SHOP) {
                switch (item) {
                    case 4020:
                    case 4021:
                    case 4022:
                    case 4023:
                    case 4024:
                    case 4025:
                    case 4027:
                    case 2957:
                    case 6199:
                    case 225316:
                        return new Object[]{50, "Seasonal Currency"};
                    case 4028:
                    case 4029:
                    case 4030:
                    case 4031:
                    case 4032:
                    case 7020:
                    case 7021:
                    case 7022:
                    case 7023:
                    case 7024:
                    case 7025:
                    case 7026:
                    case 7027:
                    case 7029:
                    case 7030:
                    case 7031:
                    case 7033:
                    case 7034:
                    case 911: //Sapphire Subscription
                        return new Object[]{100, "Seasonal Currency"};
                    case 6769://Event Pass
                    case 7035:
                    case 7028:
                    case 7040:
                    case 7042:
                    case 7049:
                    case 15501:
                    case 6854://Equipment Upgrade Box
                        return new Object[]{150, "Seasonal Currency"};
                    case 2683://Starter pack
                    case 7032:
                    case 7041:
                    case 7043:
                    case 7045:
                    case 7046:
                    case 7047:
                    case 7048:
                    case 6758://Battle Pass
                    case 912: //Emerald Subscription
                        return new Object[]{200, "Seasonal Currency"};
                    case 6749: //Mystery Pass
                    case 2795: //BINGO card
                    case 225001: //trailblazer hood t3
                    case 225004: //trailblazer body t3
                    case 225007: //trailblazer legs t3
                        return new Object[]{250, "Seasonal Currency"};
                    case 913: //Ruby Subscription
                        return new Object[]{300, "Seasonal Currency"};
                    case 914: //Diamond Subscription
                        return new Object[]{400, "Seasonal Currency"};
                    case 915: //Dragonstone Subscription
                    case 2684: //Elite pack
                        return new Object[]{500, "Seasonal Currency"};

                    case 2677: //500 Paepoints
                        return new Object[]{100, "Seasonal Currency"};
                    case 2678: //1500 Paepoints
                        return new Object[]{200, "Seasonal Currency"};
                    case 2681: //5000 Paepoints
                        return new Object[]{500, "Seasonal Currency"};


                }
                return new Object[]{100, "Seasonal Currency"};
            } else if (shop == AGILITY_TICKET_STORE) {
                switch (item) {
                    case 14936:
                    case 14938:
                        return new Object[]{60, "agility tickets"};
                }
            } else if (shop == GRAVEYARD_STORE) {
                switch (item) {
                    case 10551:
                    case 10555:
                        return new Object[]{250, "zombie fragments"};
                    case 6500:
                    case 18337:
                        return new Object[]{400, "zombie fragments"};

                }
            } else if (shop == RARE_CANDY_STORE) {
                switch (item) {
                    case 4020:
                    case 4021:
                    case 4022:
                    case 4023:
                    case 4024:
                    case 4025:
                    case 4027:
                        return new Object[]{50, "Rare Candy"};
                    case 4028:
                    case 4029:
                    case 4030:
                    case 4031:
                    case 4032:
                        return new Object[]{100, "Rare Candy"};
                }
            } else if (shop == TOKKUL_EXCHANGE_STORE) {
                switch (item) {
                    case 6522: //obsidian ring
                        return new Object[]{500, "tokkul"};
                    case 6525: //obsidian knife
                    case 6526: //obsidian staff
                        return new Object[]{25000, "tokkul"};
                    case 6527: //obsidian mace
                    case 6523: //obsidian sword
                        return new Object[]{75000, "tokkul"};
                    case 6528: //obsidian maul
                    case 6568: //obsidian cape
                    case 6524: //obsidian shield
                        return new Object[]{125000, "tokkul"};
                    case 221298: //obsidian helmet
                    case 221301: //obsidian platebody
                    case 221304: //obsidian platelegs
                        return new Object[]{500000, "tokkul"};
                    case 6571: //uncut onyx
                    case 6572:
                        return new Object[]{1000000, "tokkul"};
                }
            } else if (shop == DUNGEONEERING_STORE) {
                switch (item) {
                    case 16477:
                        return new Object[]{100, "Raid Points"};
                    case 17171:
                    case 16755:
                    case 16931:
                    case 16359:
                    case 16837:
                    case 16293:
                    case 16381:
                    case 17317:
                    case 17061:
                    case 17215:
                        return new Object[]{75000, "Raid Points"};
                    case 16865:
                    case 17017:
                    case 16711:
                    case 16403:
                    case 16315:
                    case 16955:
                    case 17143:
                    case 17339:
                    case 16337:
                    case 16887:
                        return new Object[]{100000, "Raid Points"};
                    case 17237:
                    case 16909:
                    case 15773:
                    case 16733:
                    case 17361:
                    case 16425:
                    case 16689:
                    case 16667:
                    case 17039:
                    case 17193:
                    case 17259:
                        return new Object[]{150000, "Raid Points"};
                }

            } else if (shop == DUNGEONEERING_STORE2) {
                switch (item) {
                    case 16283:
                    case 16349:
                    case 16452:
                    case 16797:
                        return new Object[]{1500, "Raid Points"};
                    case 16393:
                    case 16701:
                    case 16945:
                    case 17103:
                        return new Object[]{3000, "Raid Points"};
                    case 15763:
                    case 16415:
                    case 16657:
                    case 16679:
                    case 16723:
                    case 16899:
                    case 17029:
                    case 17351:
                        return new Object[]{4500, "Raid Points"};
                    case 17249:
                        return new Object[]{7500, "Raid Points"};
                }

            } else if (shop == SLAYER_STORE) {
                switch (item) {
                    case 11665:
                    case 11664:
                    case 11663:
                    case 8839:
                    case 8840:
                    case 8842:
                        return new Object[]{75, "Slayer points"};
                    case 10506:
                        return new Object[]{100, "Slayer points"};
                    case 607:
                    case 608:
                        return new Object[]{250, "Slayer points"};
                    case 786:
                        return new Object[]{750, "Slayer points"};
                    case 10551:
                    case 6570:
                        return new Object[]{300, "Slayer points"};
                    case 19111:
                    case 21004:
                        return new Object[]{1500, "Slayer points"};
                    case 13263:
                        return new Object[]{400, "Slayer points"};
                    case 19982:
                        return new Object[]{500, "Slayer points"};
                    case 2683:
                        return new Object[]{100, "Slayer points"};
                    case 2684:
                        return new Object[]{500, "Slayer points"};
                    case 2685:
                        return new Object[]{1000, "Slayer points"};
                }
            } else if (shop == PEST_CONTROL_REWARDS) {
                switch (item) {
                    case 8839:
                    case 8840:
                    case 8841:
                    case 8842:
                        return new Object[]{200, "PC points"};
                    case 11663:
                    case 11664:
                    case 11665:
                    case 19711:
                        return new Object[]{250, "PC points"};
                    case 19785:
                    case 19786:
                    case 19780:
                        return new Object[]{500, "PC points"};
                }
            } else if (shop == RAID_POINT_STORE) {
                switch (item) {
                    case 10195:
                        return new Object[]{5000, "Raid Points"};
                    case 18344:
                    case 608:
                    case 786:
                    case 18335:
                        return new Object[]{100000, "Raid Points"};
                    case 18349:
                    case 18351:
                    case 18353:
                    case 18355:
                    case 18357:
                        return new Object[]{200000, "Raid Points"};
                    case 2721:
                        return new Object[]{1000000, "Raid Points"};
                    case 2724:
                        return new Object[]{2000000, "Raid Points"};
                }
            } else if (shop == CRYSTAL_ARMOR_STORE) {
                switch (item) {
                    case 223971: //Crystal Helm
                        return new Object[]{2, "Crystal equipment seeds"};
                    case 223975: //Crystal Body
                        return new Object[]{5, "Crystal equipment seeds"};
                    case 223979: //Crystal Legs
                        return new Object[]{3, "Crystal equipment seeds"};
                    case 18359: //Crystal Kiteshield
                        return new Object[]{4, "Crystal equipment seeds"};
                    case 18361: //Farseer Kiteshield
                        return new Object[]{4, "Crystal equipment seeds"};
                    case 18363: //Eagle-eye Kiteshield
                        return new Object[]{4, "Crystal equipment seeds"};
                }
            } else if (shop == CRYSTAL_WEAPON_STORE) {
                switch (item) {
                    case 18349: //Crystal Rapier
                        return new Object[]{4, "Crystal equipment seeds"};
                    case 18351: //Crystal Longsword
                        return new Object[]{4, "Crystal equipment seeds"};
                    case 18353: //Crystal Maul
                        return new Object[]{4, "Crystal equipment seeds"};
                    case 18355: //Crystal Staff
                        return new Object[]{4, "Crystal equipment seeds"};
                    case 18357: //Crystal Crossbow
                        return new Object[]{4, "Crystal equipment seeds"};
                }
            }
            return null;
        }
    }

    /**
     * The shop interface id.
     */
    public static final int INTERFACE_ID = 3824;

    /**
     * The starting interface child id of items.
     */
    public static final int ITEM_CHILD_ID = 3900;

    /**
     * The interface child id of the shop's name.
     */
    public static final int NAME_INTERFACE_CHILD_ID = 3901;

    /**
     * The inventory interface id, used to set the items right click values to
     * 'sell'.
     */
    public static final int INVENTORY_INTERFACE_ID = 3823;

    /*
     * Declared shops
     */

    private static final int SKILLCAPE_STORE_1 = 8;
    private static final int SKILLCAPE_STORE_2 = 9;
    private static final int SKILLCAPE_STORE_3 = 10;
    public static final int GENERAL_STORE = 12;
    public static final int SPECIALTY_STORE = 19;
    private static final int WILDY_POINTS_SHOP = 26;
    public static final int DONATOR_STORE = 27;
    private static final int ENERGY_FRAGMENT_STORE = 33;
    public static final int RECIPE_FOR_DISASTER_STORE = 36;
    private static final int AGILITY_TICKET_STORE = 39;
    private static final int GAMBLING_STORE = 41;
    private static final int GRAVEYARD_STORE = 42;
    private static final int TOKKUL_EXCHANGE_STORE = 43;
    private static final int DUNGEONEERING_STORE = 44;
    private static final int DUNGEONEERING_STORE2 = 45;
    private static final int SLAYER_STORE = 47;
    private static final int HOLY_WATER_STORE = 51;
    private static final int SPECIALTY_COIN_STORE = 52;
    private static final int IRONMAN_SPECIALTY_STORE = 53;
    private static final int IRONMAN_SPECIALTY_COIN_STORE = 54;
    public static final int STARDUST_STORE = 55;
    public static final int PEST_CONTROL_REWARDS = 69;
    public static final int RAID_POINT_STORE = 70;
    public static final int VOTE_STORE = 71;
    private static final int SKILLING_STORE = 72;
    private static final int LOYALTY_REWARDS = 73;
    public static final int PET_STORAGE = 74;
    public static final int LIMITED_RARES = 75;
    public static final int UNTRADEABLE_STORE = 92;
    public static final int RARE_CANDY_STORE = 93;
    public static final int PRESTIGE_STORE = 94;
    public static final int CRYSTAL_ARMOR_STORE = 95;
    public static final int CRYSTAL_WEAPON_STORE = 96;
    public static final int SEASONAL_SHOP = 98;
}
