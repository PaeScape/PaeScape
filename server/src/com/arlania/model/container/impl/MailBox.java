package com.arlania.model.container.impl;

import com.arlania.DiscordBot;
import com.arlania.GameServer;
import com.arlania.GameSettings;
import com.arlania.model.Item;
import com.arlania.model.container.ItemContainer;
import com.arlania.model.container.StackType;
import com.arlania.util.Misc;
import com.arlania.world.World;
import com.arlania.world.entity.impl.player.Player;
import com.arlania.world.entity.impl.player.PlayerLoading;
import com.arlania.world.entity.impl.player.PlayerSaving;
import org.javacord.api.entity.message.MessageBuilder;

import java.util.List;

/**
 * Messy but perfect Mail System
 *
 * @author Voidstar
 */

public class MailBox extends ItemContainer {

    /*
     * The mailbox constructor
     */
    public MailBox(Player player) {
        super(player);
    }

    public static void addCoinsToMoneyPouch(String playerName, long amount) {
        Player player = World.getPlayerByName(playerName);
        boolean offline = player == null;
        if (offline) {
            player = PlayerLoading.getOfflinePlayer(playerName);
            if (player == null) {
                return;
            }
        }

        player.setMoneyInPouch(player.getMoneyInPouch() + amount);
        if (!offline) {
            player.getPacketSender().sendString(8135, "" + player.getMoneyInPouch());
        } else {
            PlayerSaving.save(player);
        }
    }

    /**
     * Opens a shop for a player
     *
     * @return The mailbox instance
     */
    public MailBox open() {
        getPlayer().getSkillManager().stopSkilling();
        getPlayer().getPacketSender().sendClientRightClickRemoval();
        getPlayer().activeInterface = "mailbox";
        getPlayer().getPacketSender().sendInterfaceRemoval().sendClientRightClickRemoval();
        refreshItems();
        getPlayer().setInterfaceId(INTERFACE_ID).setMailBoxOpen(true).setInputHandling(null);
        checkRemoteItemSystems();
        return this;
    }

    public void withdrawAllItems() {
        List<Item> validItems = getValidItems();
        for (Item item : validItems) {
            int slot = getSlot(item.getId());
            int amount = getAmountForSlot(slot);
            withdrawX(slot, amount);
        }
    }

    public void withdrawX(int slot, int amount) {
        Item item = forSlot(slot).copy();
        if (amount > 0 && amount <= item.getAmount()) {
            item.setAmount(amount);
        }
        String discordLog = getPlayer().getUsername() + " has withdrawn " + item.getAmount() + " " + item.getDefinition().getName() + " from their Mailbox!";

        if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
            new MessageBuilder().append(discordLog).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.MAILBOX_LOGS_CH).get());

        switchItem(getPlayer().getInventory(), item, slot, true, true);
    }

    /**
     * Checks if a player has enough inventory space to withdraw an item
     *
     * @param item The item which the player is withdrawing
     * @return true or false if the player has enough space to buy the item
     */
    public boolean hasInventorySpace(Item item) {
        final Player player = getPlayer();
        if (player.getInventory().getFreeSlots() >= 1) {
            return true;
        }
        if (item.getDefinition().isStackable()) {
            return player.getInventory().contains(item.getId());
        }
        return false;
    }

    public MailBox addMail(int id, int amount) {
        addMail(new Item(id, amount));
        return this;
    }

    public MailBox addMail(Item item) {
        add(item, false);
        boolean plural = item.getAmount() > 1;
        getPlayer().sendMessage("@blu@[Mailbox] @red@" + item.getAmount() + " " + item.getName() + (plural ? " have " : " has ") + "been delivered to your mailbox.");
        if (getPlayer().isMailBoxOpen()) {
            refreshItems();
        }
        return this;
    }

    public static boolean addMail(String playerName, Item item) {
        playerName = Misc.formatText(playerName);
        boolean playerExists = PlayerSaving.playerExists(playerName);
        if (!playerExists)
            return false;
        if (item.getDefinition().getName() == null || item.getDefinition().getName().isEmpty())
            return false;

        Player player = World.getPlayerByName(playerName);
        boolean offline = player == null;
        if (offline) {
            player = PlayerLoading.getOfflinePlayer(playerName);
            if (player == null) {
                return false;
            }
        }

        boolean isCoins = false;
        if (item.getId() == 995) {
            player.setMoneyInPouch(player.getMoneyInPouch() + item.getAmount());
            isCoins = true;
        }


        int slot = player.getMailBox().getSlot(item.getId());
        if (slot >= 0) {
            Item[] items = player.getMailBox().getItems();
            items[slot].setId(item.getId());
            if (items[slot].getAmount() > 0 && item.getAmount() > 0 && item.getAmount() + items[slot].getAmount() < 0) {
                return false;
            }
        }


        if (!isCoins)
            player.getMailBox().add(item, false);
        if (offline) {
            PlayerSaving.save(player);
        }

        return true;
    }

    @Override
    public int capacity() {
        return 40;
    }

    @Override
    public StackType stackType() {
        return StackType.STACKS;
    }

    @Override
    public MailBox refreshItems() {
        final Player player = getPlayer();
        player.getPacketSender().sendInterface(INTERFACE_ID);
        player.getPacketSender().sendItemContainer(this, ITEM_CHILD_ID);
        player.getPacketSender().sendString(NAME_INTERFACE_CHILD_ID, "Mailbox");
        player.getInventory().refreshItems();
        return this;
    }

    @Override
    public ItemContainer full() {
        return this;
    }

    public void checkRemoteItemSystems() {
        if (GameServer.isDebug()) return;
        if (System.currentTimeMillis() - getPlayer().getAttribute("api_check_time", (long) 0) > 60_000) {
            // only allow players to check everythingrs/gpay every minute. let's not get blackballed
            getPlayer().claimVoteScrolls();
            getPlayer().claimItemsFromGPay();
            getPlayer().setAttribute("api_check_time", System.currentTimeMillis());
        }
    }

    private boolean mailboxHasItem(Item item) {
        return contains(item.getId());
    }

    /**
     * The shop interface id.
     */
    public static final int INTERFACE_ID = 22047;

    /**
     * The starting interface child id of items.
     */
    public static final int ITEM_CHILD_ID = INTERFACE_ID + 7;

    /**
     * The interface child id of the shop's name.
     */
    public static final int NAME_INTERFACE_CHILD_ID = 3901;

    public static final int WITHDRAW_ALL_ITEMS_BUTTON_ID = 22053;
}
