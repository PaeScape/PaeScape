package com.arlania.world.content;

import com.arlania.DiscordBot;
import com.arlania.GameServer;
import com.arlania.GameSettings;
import com.arlania.model.Item;
import com.arlania.model.Locations;
import com.arlania.model.StaffRights;
import com.arlania.model.definitions.ItemDefinition;
import com.arlania.util.Misc;
import com.arlania.world.World;
import com.arlania.world.entity.impl.player.Player;
import org.javacord.api.entity.message.MessageBuilder;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;

/**
 * @author: @Gabbe
 * Warning:
 * This crap is so messy and ugly. Will redo it once I get some time over.
 * Should be dupe-free.
 */

public class Trading {

    private final Player player;

    public Trading(Player p) {
        this.player = p;
    }

    public void requestTrade(Player player2) {
        if (player == null || player2 == null || player.getConstitution() <= 0 || player2.getConstitution() <= 0 || player.isTeleporting() || player2.isTeleporting())
            return;

        if (!player.canTrade(player2)) {
            player.getPacketSender().sendMessage("Only normal game mode accounts can trade.");
            return;
        }
        if (player.getBankPinAttributes().hasBankPin() && !player.getBankPinAttributes().hasEnteredBankPin()) {
            BankPin.init(player, false);
            return;
        }
        if (System.currentTimeMillis() - lastTradeSent < 5000 && !inTrade()) {
            player.getPacketSender().sendMessage("You're sending trade requests too frequently. Please slow down.");
            return;
        }
        if (inTrade()) {
            declineTrade(true);
            return;
        }
        if (player.isShopping() || player.isBanking()) {
            player.getPacketSender().sendInterfaceRemoval();
            return;
        }
        if (player.busy()) {
            return;
        }
        if (player2.busy() || player2.getInterfaceId() > 0 || player2.getTrading().inTrade() || player2.isBanking() || player2.isShopping()/* || player2.getDueling().inDuelScreen || FightPit.inFightPits(player2)*/) {
            player.getPacketSender().sendMessage("The other player is currently busy.");
            return;
        }
        if (player.getInterfaceId() > 0 || inTrade() || player.isBanking() || player.isShopping()/* || player.getDueling().inDuelScreen || FightPit.inFightPits(player)*/) {
            player.getPacketSender().sendMessage("You are currently unable to trade another player.");
            if (player.getInterfaceId() > 0)
                player.getPacketSender().sendMessage("Please close all open interfaces before requesting to open another one.");
            return;
        }
        tradeWith = player2.getIndex();
        if (getTradeWith() == player.getIndex())
            return;
        if (!Locations.goodDistance(player.getPosition().getX(), player.getPosition().getY(), player2.getPosition().getX(), player2.getPosition().getY(), 2)) {
            player.getPacketSender().sendMessage("Please get closer to request a trade.");
            return;
        }
        if (!inTrade() && player2.getTrading().tradeRequested() && player2.getTrading().getTradeWith() == player.getIndex()) {
            openTrade();
            player2.getTrading().openTrade();
        } else if (!inTrade()) {
            setTradeRequested(true);
            player.getPacketSender().sendMessage("You've sent a trade request to " + player2.getUsername() + ".");
            player2.getPacketSender().sendMessage(player.getUsername() + ":tradereq:");
        }
        lastTradeSent = System.currentTimeMillis();
    }

    public void openTrade() {
        player.getPacketSender().sendClientRightClickRemoval();
        Player player2 = World.getPlayers().get(getTradeWith());
        if (player == null || player2 == null || getTradeWith() == player.getIndex() || player.isBanking())
            return;
        setTrading(true);
        setTradeRequested(false);
        setCanOffer(true);
        setTradeStatus(1);
        player.getPacketSender().sendInterfaceItems(3415, offeredItems);
        player2.getPacketSender().sendInterfaceItems(3415, player2.getTrading().offeredItems);
        sendText(player2);
        player.getInventory().refreshItems();
        player.getPacketSender().sendInterfaceItems(3415, offeredItems);
        player.getPacketSender().sendInterfaceItems(3416, player2.getTrading().offeredItems);
        player.getMovementQueue().reset();
        inTradeWith = player2.getIndex();
    }

    public void declineTrade(boolean tellOther) {
        Player player2 = getTradeWith() >= 0 && !(getTradeWith() > World.getPlayers().capacity()) ? World.getPlayers().get(getTradeWith()) : null;
        for (Item item : offeredItems) {
            if (item.getAmount() < 1)
                continue;
            player.getInventory().add(item);
        }
        offeredItems.clear();
        if (tellOther && getTradeWith() > -1) {
            if (player2 == null)
                return;
            player2.getTrading().declineTrade(false);
            player2.getPacketSender().sendMessage("Other player declined the trade.");
        }
        resetTrade();
    }

    public void sendText(Player player2) {
        if (player2 == null)
            return;
        player2.getPacketSender().sendString(3451, Misc.formatPlayerName(player.getUsername()));
        player2.getPacketSender().sendString(3417, "Trading with: " + Misc.formatPlayerName(player.getUsername()));
        player.getPacketSender().sendString(3451, Misc.formatPlayerName(player2.getUsername()));
        player.getPacketSender().sendString(3417, "Trading with: " + Misc.formatPlayerName(player2.getUsername()));
        player.getPacketSender().sendString(3431, "");
        player.getPacketSender().sendString(3535, "Are you sure you want to make this trade?");
        player.getPacketSender().sendInterfaceSet(3323, 3321);
        player.getPacketSender().sendItemContainer(player.getInventory(), 3322);
    }

    public void tradeItem(int itemId, int amount, int slot) {
        if (slot < 0)
            return;
        if (!getCanOffer())
            return;
        Player player2 = World.getPlayers().get(getTradeWith());
        if (player2 == null || player == null)
            return;
		
	/*	if(player.getNewPlayerDelay() > 0 && player.getStaffRights().ordinal() == 0) {
			player.getPacketSender().sendMessage("You must wait another "+player.getNewPlayerDelay() / 60+" minutes before being able to trade items.");
			return;
		}*/
        if (player.getStaffRights() != StaffRights.DEVELOPER && player2.getStaffRights() != StaffRights.DEVELOPER && !(itemId == 1419 && player.getStaffRights().isStaff())) {
            if (!new Item(itemId).tradeable()) {
                player.getPacketSender().sendMessage("This item is currently untradeable and cannot be traded.");
                return;
            }
        }
        if (player.getStaffRights() != StaffRights.DEVELOPER && player2.getStaffRights() != StaffRights.DEVELOPER && !(itemId == 1419 && player.getStaffRights().isStaff())) {

            for (int i = 0; i < GameSettings.PETS.length; i++) {
                if (itemId == GameSettings.PETS[i]) {
                    player.getPacketSender().sendMessage("This item is currently untradeable and cannot be traded.");
                    return;
                }
            }
        }
        falseTradeConfirm();
        player.getPacketSender().sendClientRightClickRemoval();
        if (!inTrade() || !canOffer) {
            declineTrade(true);
            return;
        }
        if (!player.getInventory().contains(itemId))
            return;
        if (slot >= player.getInventory().capacity() || player.getInventory().getItems()[slot].getId() != itemId || player.getInventory().getItems()[slot].getAmount() <= 0)
            return;
        Item itemToTrade = player.getInventory().getItems()[slot];
        if (itemToTrade.getId() != itemId)
            return;
        if (player.getInventory().getAmount(itemId) < amount) {
            amount = player.getInventory().getAmount(itemId);
            if (amount == 0 || player.getInventory().getAmount(itemId) < amount) {
                return;
            }
        }
        if (!itemToTrade.getDefinition().isStackable()) {
            for (int a = 0; a < amount && a < 28; a++) {
                if (player.getInventory().getAmount(itemId) >= 1) {
                    offeredItems.add(new Item(itemId, 1));
                    player.getInventory().delete(itemId, 1);
                }
            }
        } else if (itemToTrade.getDefinition().isStackable()) {
            boolean itemInTrade = false;
            for (Item item : offeredItems) {
                if (item.getId() == itemId) {
                    itemInTrade = true;
                    item.setAmount(item.getAmount() + amount);
                    player.getInventory().delete(itemId, amount);
                    break;
                }
            }
            if (!itemInTrade) {
                offeredItems.add(new Item(itemId, amount));
                player.getInventory().delete(itemId, amount);
            }
        }
        player.getInventory().refreshItems();
        player.getPacketSender().sendInterfaceItems(3416, player2.getTrading().offeredItems);
        player.getPacketSender().sendInterfaceItems(3415, offeredItems);
        player.getPacketSender().sendString(3431, "");
        acceptedTrade = false;
        tradeConfirmed = false;
        tradeConfirmed2 = false;
        player2.getPacketSender().sendInterfaceItems(3416, offeredItems);
        player2.getPacketSender().sendString(3431, "");
        player2.getTrading().acceptedTrade = false;
        player2.getTrading().tradeConfirmed = false;
        player2.getTrading().tradeConfirmed2 = false;
        sendText(player2);
    }

    public void removeTradedItem(int itemId, int amount) {
        if (!getCanOffer())
            return;
        Player player2 = World.getPlayers().get(getTradeWith());
        if (player2 == null)
            return;
        if (!inTrade() || !canOffer) {
            declineTrade(false);
            return;
        }
        falseTradeConfirm();
        ItemDefinition def = ItemDefinition.forId(itemId);
        if (!def.isStackable()) {
            if (amount > 28)
                amount = 28;
            for (int a = 0; a < amount; a++) {
                for (Item item : offeredItems) {
                    if (item.getId() == itemId) {
                        if (!item.getDefinition().isStackable()) {
                            offeredItems.remove(item);
                            player.getInventory().add(itemId, 1);
                        }
                        break;
                    }
                }
            }
        } else
            for (Item item : offeredItems) {
                if (item.getId() == itemId) {
                    if (item.getDefinition().isStackable()) {
                        if (item.getAmount() > amount) {
                            item.setAmount(item.getAmount() - amount);
                            player.getInventory().add(itemId, amount);
                        } else {
                            amount = item.getAmount();
                            offeredItems.remove(item);
                            player.getInventory().add(itemId, amount);
                        }
                    }
                    break;
                }
            }
        falseTradeConfirm();
        player.getInventory().refreshItems();
        player.getPacketSender().sendInterfaceItems(3416, player2.getTrading().offeredItems);
        player.getPacketSender().sendInterfaceItems(3415, offeredItems);
        player2.getPacketSender().sendInterfaceItems(3416, offeredItems);
        sendText(player2);
        player.getPacketSender().sendString(3431, "");
        player2.getPacketSender().sendString(3431, "");
        player.getPacketSender().sendClientRightClickRemoval();
    }

    public void acceptTrade(int stage) {
        if (!player.getClickDelay().elapsed(1000))
            return;
        if (getTradeWith() < 0) {
            declineTrade(false);
            return;
        }
        Player player2 = World.getPlayers().get(getTradeWith());
        if (player == null || player2 == null) {
            declineTrade(false);
            return;
        }
        if (!twoTraders(player, player2)) {
            player.getPacketSender().sendMessage("An error has occured. Please try re-trading the player.");
            return;
        }
        if (stage == 2) {
            if (!inTrade() || !player2.getTrading().inTrade() || !player2.getTrading().tradeConfirmed) {
                declineTrade(true);
                return;
            }
            if (!tradeConfirmed)
                return;
            acceptedTrade = true;
            tradeConfirmed2 = true;
            player2.getPacketSender().sendString(3535, "Other player has accepted.");
            player.getPacketSender().sendString(3535, "Waiting for other player...");
            if (inTrade() && player2.getTrading().tradeConfirmed2) {
                acceptedTrade = true;
                giveItems();
                player.getPacketSender().sendMessage("Trade accepted.");
                player2.getTrading().acceptedTrade = true;
                player2.getTrading().giveItems();
                player2.getPacketSender().sendMessage("Trade accepted.");
                resetTrade();
                player2.getTrading().resetTrade();
            }
        } else if (stage == 1) {
            player2.getTrading().goodTrade = true;
            player2.getPacketSender().sendString(3431, "Other player has accepted.");
            goodTrade = true;
            player.getPacketSender().sendString(3431, "Waiting for other player...");
            tradeConfirmed = true;
            if (inTrade() && player2.getTrading().tradeConfirmed && player2.getTrading().goodTrade && goodTrade) {
                confirmScreen();
                player2.getTrading().confirmScreen();
            }
        }
        player.getClickDelay().reset();
    }

    public void confirmScreen() {
        Player player2 = World.getPlayers().get(getTradeWith());
        if (player2 == null)
            return;
        setCanOffer(false);
        player.getInventory().refreshItems();
        String SendTrade = "Absolutely nothing!";
        String SendAmount;
        int Count = 0;
        for (Item item : offeredItems) {
            if (item.getAmount() >= 1000 && item.getAmount() < 1000000) {
                SendAmount = "@cya@" + (item.getAmount() / 1000) + "K @whi@(" + Misc.format(item.getAmount()) + ")";
            } else if (item.getAmount() >= 1000000) {
                SendAmount = "@gre@" + (item.getAmount() / 1000000) + " million @whi@(" + Misc.format(item.getAmount()) + ")";
            } else {
                SendAmount = Misc.format(item.getAmount());
            }
            if (Count == 0) {
                SendTrade = item.getDefinition().getName().replaceAll("_", " ");
            } else
                SendTrade = SendTrade + "\\n" + item.getDefinition().getName().replaceAll("_", " ");
            if (item.getDefinition().isStackable())
                SendTrade = SendTrade + " x " + SendAmount;
            Count++;
        }

        player.getPacketSender().sendString(3557, SendTrade);
        SendTrade = "Absolutely nothing!";
        SendAmount = "";
        Count = 0;
        for (Item item : player2.getTrading().offeredItems) {
            if (item.getAmount() >= 1000 && item.getAmount() < 1000000) {
                SendAmount = "@cya@" + (item.getAmount() / 1000) + "K @whi@(" + Misc.format(item.getAmount()) + ")";
            } else if (item.getAmount() >= 1000000) {
                SendAmount = "@gre@" + (item.getAmount() / 1000000) + " million @whi@(" + Misc.format(item.getAmount()) + ")";
            } else {
                SendAmount = Misc.format(item.getAmount());
            }
            if (Count == 0) {
                SendTrade = item.getDefinition().getName().replaceAll("_", " ");
            } else
                SendTrade = SendTrade + "\\n" + item.getDefinition().getName().replaceAll("_", " ");
            if (item.getDefinition().isStackable())
                SendTrade = SendTrade + " x " + SendAmount;
            Count++;
        }
        player.getPacketSender().sendString(3558, SendTrade);
        player.getPacketSender().sendInterfaceSet(3443, 3321);
        player.getPacketSender().sendItemContainer(player.getInventory(), 3322);
        /*
         * Remove all tabs!
         */
        //player.getPacketSender().sendInterfaceSet(3443, Inventory.INTERFACE_ID);
        //player.getPacketSender().sendItemContainer(player.getInventory(), Inventory.INTERFACE_ID);
    }

    public void giveItems() {
        Player player2 = World.getPlayers().get(getTradeWith());
        if (player2 == null)
            return;
        if (!inTrade() || !player2.getTrading().inTrade())
            return;
        try {
            for (Item item : player2.getTrading().offeredItems) {
                player.getInventory().add(item);
            }

            //logs
            for (Item item : player.getTrading().offeredItems) {

                String log = " Gave " + item.getAmount() + " " + item.getName() + " [" + item.getId() + "] in a trade with " + player2.getUsername() + ".";

                PlayerLogs.log(player.getUsername(), log);

                if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                    new MessageBuilder().append(player.getUsername() + log).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.DISC_TRADELOGS_CH).get());

            }
            for (Item item : player2.getTrading().offeredItems) {

                String log = " Received " + item.getAmount() + " " + item.getName() + " [" + item.getId() + "] in a trade with " + player2.getUsername() + ".";

                PlayerLogs.log(player.getUsername(), log);

                if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                    new MessageBuilder().append(player.getUsername() + log).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.DISC_TRADELOGS_CH).get());

            }
        } catch (Exception e) {
            GameServer.getLogger().log(Level.SEVERE, "ruh roh", e);
        }
    }

    public void resetTrade() {
        inTradeWith = -1;
        offeredItems.clear();
        setCanOffer(true);
        setTrading(false);
        setTradeWith(-1);
        setTradeStatus(0);
        lastTradeSent = 0;
        acceptedTrade = false;
        tradeConfirmed = false;
        tradeConfirmed2 = false;
        tradeRequested = false;
        canOffer = true;
        goodTrade = false;
        player.getPacketSender().sendString(3535, "Are you sure you want to make this trade?");
        player.getPacketSender().sendInterfaceRemoval();
        player.getPacketSender().sendInterfaceRemoval();
    }


    private boolean falseTradeConfirm() {
        Player player2 = World.getPlayers().get(getTradeWith());
        return tradeConfirmed = player2.getTrading().tradeConfirmed = false;
    }

    public CopyOnWriteArrayList<Item> offeredItems = new CopyOnWriteArrayList<Item>();
    private boolean inTrade = false;
    private boolean tradeRequested = false;
    private int tradeWith = -1;
    private int tradeStatus;
    public long lastTradeSent, lastAction;
    private boolean canOffer = true;
    public boolean tradeConfirmed = false;
    public boolean tradeConfirmed2 = false;
    public boolean acceptedTrade = false;
    public boolean goodTrade = false;

    public void setTrading(boolean trading) {
        this.inTrade = trading;
    }

    public boolean inTrade() {
        return this.inTrade;
    }

    public void setTradeRequested(boolean tradeRequested) {
        this.tradeRequested = tradeRequested;
    }

    public boolean tradeRequested() {
        return this.tradeRequested;
    }

    public void setTradeWith(int tradeWith) {
        this.tradeWith = tradeWith;
    }

    public int getTradeWith() {
        return this.tradeWith;
    }

    public void setTradeStatus(int status) {
        this.tradeStatus = status;
    }

    public int getTradeStatus() {
        return this.tradeStatus;
    }

    public void setCanOffer(boolean canOffer) {
        this.canOffer = canOffer;
    }

    public boolean getCanOffer() {
        return canOffer && player.getInterfaceId() == 3323 && !player.isBanking() && !player.getPriceChecker().isOpen();
    }

    public int inTradeWith = -1;

    /**
     * Checks if two players are the only ones in a trade.
     *
     * @param p1 Player1 to check if he's 1/2 player in trade.
     * @param p2 Player2 to check if he's 2/2 player in trade.
     * @return true if only two people are in the trade.
     */
    public static boolean twoTraders(Player p1, Player p2) {
        int count = 0;
        for (Player player : World.getPlayers()) {
            if (player == null)
                continue;
            if (player.getTrading().inTradeWith == p1.getIndex() || player.getTrading().inTradeWith == p2.getIndex()) {
                count++;
            }
        }
        return count == 2;
    }

    /**
     * The trade interface id.
     */
    public static final int INTERFACE_ID = 3322;

    /**
     * The trade interface id for removing items.
     */
    public static final int INTERFACE_REMOVAL_ID = 3415;

}
