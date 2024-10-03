package com.arlania.world.content.pos;

import com.arlania.DiscordBot;
import com.arlania.GameServer;
import com.arlania.GameSettings;
import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.GameMode;
import com.arlania.model.Item;
import com.arlania.model.definitions.ItemDefinition;
import com.arlania.model.input.Input;
import com.arlania.util.Misc;
import com.arlania.world.World;
import com.arlania.world.content.dialogue.DialogueManager;
import com.arlania.world.entity.impl.player.Player;
import com.google.gson.*;
import org.javacord.api.entity.message.MessageBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Level;

/**
 * A management class for all player owned shops and information related to a
 * player owned shop on player instance basis.
 *
 * @author Voidstar
 */
public class TradingPostManager {

    /**
     * A collection of all player owned shops ever created.
     */
    public static final List<TradingPost> SHOPS = new ArrayList<>();

    /**
     * The directory for the player owned shops
     */

    public static final File POS_SAVE_FILE = Paths.get(GameServer.getSaveDirectory(), "tradingpost", "shops.json").toFile();

    /**
     * A reference to the player instance.
     */
    private final Player player;

    /**
     * The current player owned shop being visited by the player.
     */
    private TradingPost currentlyViewing;

    /**
     * The player owned shop owned by the player relative to the current
     * {@link TradingPostManager} instance.
     */
    private TradingPost myShop;

    /**
     * A collection of the shops filtered for this player's instance.
     */
    private List<TradingPost> filtered = new ArrayList<>();

    /**
     * The string we are using to filter through all of the player owned shops.
     */
    private String filterString = "";

    /**
     * Construct a new {@code PlayerOwnedShopManager} {@code Object}.
     *
     * @param player The reference to the player owning this instance.
     */
    public TradingPostManager(Player player) {
        this.player = player;
    }

    /**
     * Open the player owned shop management interface.
     */
    public void open() {
        if (player.getGameMode() != GameMode.NORMAL) {
            player.getPacketSender().sendMessage("Ironman players are not allowed to trade.");
            player.getPacketSender().sendInterfaceRemoval();
            return;
        }
        player.getPacketSender().sendString(TradingPost.SHOP_SEARCH_INTERFACE_TITLE_ID, "Trading Post");

        int i = 0;
        filtered.clear();

        for (TradingPost shop : SHOPS) {
            if (shop != null && shop.size() > 0) {
                if (i < 100)
                    player.getPacketSender().sendString(32623 + (i++), shop.getOwnerName());
                filtered.add(shop);
            }
        }

        for (; i < 100; i++) {
            player.getPacketSender().sendString(32623 + i, "");
        }

        player.getPacketSender().resetItemsOnInterface(TradingPost.SHOP_SEARCH_ITEM_CHILD_INTERFACE_ID, TradingPost.SHOP_CAPACITY);
        player.getPacketSender().sendInterface(TradingPost.SHOP_SEARCH_INTERFACE_ID);

    }

    public static void options(Player player) {
        DialogueManager.start(player, 100);
        player.setDialogueActionId(100);
    }

    /**
     * Open the interface to edit your own shop.
     */
    public void openEditor() {
        if (player.getGameMode() == GameMode.SEASONAL_IRONMAN || player.getGameMode() == GameMode.ULTIMATE_IRONMAN || player.getGameMode() == GameMode.HARDCORE_IRONMAN || player.getGameMode() == GameMode.IRONMAN) {
            player.getPacketSender().sendMessage("Ironman players are not allowed to trade.");
            player.getPacketSender().sendInterfaceRemoval();
            return;
        }

        myShop.open(player);
    }

    /**
     * Handle a button on the management interface for this player.
     *
     * @param buttonId The button component id.
     */
    public void handleButton(int buttonId) {
        buttonId -= 32623;

        boolean f = !filtered.isEmpty();

        if (buttonId >= (f ? filtered : SHOPS).size()) {
            return;
        }

        TradingPost shop = (f ? filtered : SHOPS).get(buttonId);

        if (shop != null) {
            setCurrentlyViewing(shop);
            currentlyViewing.open(player);
        } else {
            player.getPacketSender().resetItemsOnInterface(TradingPost.SHOP_SEARCH_ITEM_CHILD_INTERFACE_ID, TradingPost.SHOP_CAPACITY);
        }
    }

    /**
     * Handle the action to buy an item from a player owned shop for the player
     * this management instance is relative to.
     *
     * @param slot   The item slot.
     * @param id     The item id.
     * @param amount The amount he/she would like to buy of this item.
     */
    public void handleBuy(int slot, int slotId, int amount) {
        if (player.getGameMode() != GameMode.NORMAL) {
            player.getPacketSender().sendMessage("Ironman players are not allowed to trade.");
            player.getPacketSender().sendInterfaceRemoval();
            return;
        }
        if (currentlyViewing == null) {
            return;
        }

        Item item = currentlyViewing.getItem(slot).copy().setAmount(amount);

        if (item == null) {
            return;
        }

        if (amount == -1) {
            ItemDefinition definition = item.getDefinition();
            if (definition != null) {
                String formatPrice = Misc.sendCashToString(currentlyViewing.getPrice(item));
                player.sendMessage("@red@" + definition.getName() + "@bla@ costs " + formatPrice
                        + " coins each in @red@" + currentlyViewing.getOwnerName() + "@bla@'s shop.");
            }
            return;
        }

        if (currentlyViewing == myShop) {
            player.sendMessage("You cannot buy items from your own shop.");
            return;
        }

        if (cantTrade(item)) return;

        TaskManager.submit(new Task() {
            @Override
            protected void execute() {
                currentlyViewing.switchItem(player.getInventory(), item, slot, true, true);
                stop();
            }
        });
    }

    private boolean cantTrade(Item item) {
        if (!item.tradeable()) {
            player.sendMessage("You can't trade this item.");
            return true;
        }

        for (int i : GameSettings.UNTRADEABLE_ITEMS) {
            if (i == item.getId()) {
                player.sendMessage("You can't trade this item.");
                return true;
            }
        }
        return false;
    }

    /**
     * Handle the withdraw action for this player's own shop.
     *
     * @param slot   The item slot.
     * @param id     The item id.
     * @param amount The amount the player would like to withdraw.
     */
    public void handleWithdraw(int slot, int id, int amount) {
        if (currentlyViewing != myShop) {
            return;
        }

        Item item = currentlyViewing.getItem(slot).copy().setAmount(amount);
        ItemDefinition definition = item.getDefinition();
        long price = currentlyViewing.getPrice(item);

        if (amount == -1) {
            if (definition != null) {
                String formatPrice = Misc.insertCommasToNumber(String.valueOf(price));
                player.sendMessage("@red@" + definition.getName() + "@bla@ is set to cost " + formatPrice + " coins in your shop.");
            }
            return;
        }
        TaskManager.submit(new Task() {
            @Override
            protected void execute() {
                currentlyViewing.switchItem(player.getInventory(), item, slot, true, true);
                stop();
            }
        });
    }

    public void handleStore(int slot, int id, int amount) {
        handleStore(slot, id, amount, -1);
    }

    public void handleStore(int slot, int id, int amount, long price) {
        if (player.getGameMode() != GameMode.NORMAL) {
            player.getPacketSender().sendMessage("Ironman players are not allowed to trade.");
            player.getPacketSender().sendInterfaceRemoval();
            return;
        }
        if (player.getInventory().get(slot) == null) {
            return;
        }

        int itemId = player.getInventory().get(slot).getId();
        int itemAmount = player.getInventory().getAmount(itemId);
        Item item = new Item(itemId, itemAmount);

        if (itemId == id) {
            if (id == 995) {
                player.sendMessage("You cannot store money in your shop.");
                return;
            } else if (id == 18016) {
                player.sendMessage("You cannot store money in your shop.");
                return;
            }

            if (cantTrade(item)) return;

            if (amount >= itemAmount) {
                amount = itemAmount;
            }

            int currentAmount = myShop.getAmount(id);

            if (currentAmount == 0 && price == -1) {
                final int amount2 = amount;
                player.setInputHandling(new Input() {

                    @Override
                    public void handleAmount(Player player, int value) {
                        handleStore(slot, id, amount2, value);
                    }

                });
                player.getPacketSender().sendEnterAmountPrompt("Enter the price for this item:");
                return;
            }

            if (myShop.size() >= 32) {
                player.sendMessage("Your shop cannot contain any more items.");
                return;
            }

            if (currentAmount == Integer.MAX_VALUE) {
                player.sendMessage("You cannot store any more of this item in your shop.");
                return;
            }

            long total = ((long) currentAmount + (long) amount);

            if (total > Integer.MAX_VALUE) {
                amount = Integer.MAX_VALUE - currentAmount;
            }

            if (myShop.contains(id))
                price = myShop.getPrice(item);

            if (amount > 0) {
                player.getInventory().delete(id, amount, slot);
                myShop.add(id, amount, price);
                String message = " listed " + amount + " " + ItemDefinition.forId(id).getName() + (amount > 1 ? "s" : "") + " for " + Misc.insertCommasToNumber(String.valueOf(price)) + " each.";
                player.sendMessage("You" + message);
                if (GameSettings.DISCORD && !myShop.isMuted() && currentAmount == 0)//just add this anywhere and edit the message/or just copy the announcement
                    new MessageBuilder().append("**[Trading Post]** " + player.getUsername() + message).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.MARKETPLACE_CH).get());
            }
            myShop.refreshItems();
        }
    }

    public void setCustomPrice(int slot, int id, int price) {
        if (currentlyViewing != myShop) {
            return;
        }

        Item item = currentlyViewing.getItem(slot);

        if (item == null) {
            return;
        }

        ItemDefinition definition = item.getDefinition();

        if (price > 0) {
            currentlyViewing.setPrice(item, price);
            String formatPrice = Misc.sendCashToString(price);
            player.sendMessage("You have set @red@" + definition.getName().toLowerCase()
                    + "@bla@ to cost @red@" + formatPrice + "@bla@ coins in your shop.");
        }
    }

    public void hookShop() {
        Optional<TradingPost> shopOpt = SHOPS.stream().filter(Objects::nonNull).filter(shop -> player.getUsername().equalsIgnoreCase(shop.getOwnerName())).findFirst();
        if (shopOpt.isPresent()) {
            myShop = shopOpt.get();
            myShop.setOwner(player);
        } else {
            myShop = new TradingPost(player);
            SHOPS.add(myShop);
        }
    }

    public void unhookShop() {
        if (myShop == null) {
            hookShop();
        }

        if (myShop != null) {
            myShop.setOwner(null);
        }
    }

    public void updateFilter(String filterString) {
        filtered.clear();
        boolean emptyFilter = filterString.isEmpty();
        player.getPacketSender().sendString(32610, emptyFilter ? "Trading Post" : "Trading Post - Searching: " + (setFilterString(filterString)));
        int i = 0;

        for (TradingPost shop : SHOPS) {
            if (shop != null && shop.size() > 0 && (emptyFilter || shop.contains(filterString) || shop.getOwnerName().toLowerCase().contains(filterString.toLowerCase()))) {
                player.getPacketSender().sendString(32623 + (i++), shop.getOwnerName());
                filtered.add(shop);
            }
        }

        for (; i < 100; i++) {
            player.getPacketSender().sendString(32623 + i, "");
        }
    }

    public static void loadShops() {
        if (!POS_SAVE_FILE.getParentFile().exists()) {
            try {
                POS_SAVE_FILE.getParentFile().mkdirs();
            } catch (SecurityException e) {
                GameServer.getLogger().log(Level.SEVERE, "Unable to create directory for Trading Post data!", e);
            }
        }

        if (!POS_SAVE_FILE.exists()) {
            return;
        }

        // Now read the properties from the json parser.
        try (FileReader fileReader = new FileReader(POS_SAVE_FILE)) {
            JsonObject reader = (JsonObject) JsonParser.parseReader(fileReader);

            if (!reader.has("shops")) {
                GameServer.getLogger().severe(POS_SAVE_FILE.getAbsolutePath() + " does not contain \"shops\" attribute.");
                return;
            }

            JsonArray ja = reader.getAsJsonArray("shops");

            SHOPS.clear();

            ja.forEach((je -> SHOPS.add(new TradingPost(je.getAsJsonObject()))));

        } catch (Exception e) {
            GameServer.getLogger().log(Level.SEVERE, "ruh roh", e);
        }
    }

    public static void saveShops() {
        Collections.shuffle(SHOPS);
        POS_SAVE_FILE.getParentFile().setWritable(true);

        if (!POS_SAVE_FILE.getParentFile().exists()) {
            try {
                POS_SAVE_FILE.getParentFile().mkdirs();
            } catch (SecurityException e) {
                GameServer.getLogger().log(Level.SEVERE, "Unable to create directory for Trading Post data!", e);
            }
        }
        try (FileWriter writer = new FileWriter(POS_SAVE_FILE)) {
            Gson builder = new GsonBuilder().setPrettyPrinting().create();
            JsonArray ja = new JsonArray();
            for (TradingPost shop : SHOPS) {
                if (shop.size() > 0 || shop.isMuted()) {
                    ja.add(shop.save());
                }
            }
            JsonObject jo = new JsonObject();
            jo.add("shops", ja);
            writer.write(builder.toJson(jo));
        } catch (IOException e) {
            GameServer.getLogger().log(Level.SEVERE, "ruh roh", e);
        }
    }

    public static boolean mute(String playerName) {
        playerName = Misc.formatText(playerName);

        Optional<TradingPost> shopOpt = findByOwnerName(playerName);
        if (!shopOpt.isPresent()) {
            return false;
        }

        TradingPost shop = shopOpt.get();
        shop.setMuted(!shop.isMuted());

        Player player = World.getPlayerByName(playerName);
        if (player != null) {
            player.sendMessage("Your shop has been " + (player.getTradingPostManager().getMyShop().isMuted() ? "" : "un") + "muted.");
        }
        return true;
    }

    public static Optional<TradingPost> findByOwnerName(String ownerName) {
        final String finalOwnerName = Misc.formatText(ownerName);
        return SHOPS.stream().filter(Objects::nonNull).filter((tradingPost -> tradingPost.getOwnerName().equals(finalOwnerName))).findFirst();
    }


    public TradingPost getCurrentlyViewing() {
        return currentlyViewing;
    }

    public void setCurrentlyViewing(TradingPost currentlyViewing) {
        this.currentlyViewing = currentlyViewing;
    }

    public TradingPost getMyShop() {
        return myShop;
    }

    public void setMyShop(TradingPost myShop) {
        this.myShop = myShop;
    }

    public List<TradingPost> getFiltered() {
        return filtered;
    }

    public void setFiltered(List<TradingPost> filtered) {
        this.filtered = filtered;
    }

    public String getFilterString() {
        return filterString;
    }

    public String setFilterString(String filterString) {
        this.filterString = filterString;
        return filterString;
    }

    public void handleBuyX(int slot, int id) {
        if (getCurrentlyViewing().getItem(slot).getId() != id) return;

        int itemAmount = getCurrentlyViewing().getAmount(id);
        if (itemAmount <= 0) return;

        player.setInputHandling(new Input() {
            @Override
            public void handleAmount(Player player, int value) {
                player.getTradingPostManager().handleBuy(slot, id, value);
                getCurrentlyViewing().open(player);
            }
        });
        player.getPacketSender().sendEnterAmountPrompt("How many would you like to buy?");
    }
}
