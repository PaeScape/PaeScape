package com.arlania.world.content.pos;

import com.arlania.DiscordBot;
import com.arlania.GameServer;
import com.arlania.GameSettings;
import com.arlania.model.GameMode;
import com.arlania.model.Item;
import com.arlania.model.container.ItemContainer;
import com.arlania.model.container.StackType;
import com.arlania.model.container.impl.MailBox;
import com.arlania.model.definitions.ItemDefinition;
import com.arlania.util.Misc;
import com.arlania.world.World;
import com.arlania.world.entity.impl.player.Player;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.javacord.api.entity.message.MessageBuilder;

import java.util.HashMap;
import java.util.Objects;
import java.util.logging.Level;

/**
 * A class representing a single player owned shop. In this
 * we hold and manage all the items that are added or sold
 * using an instance of this class. A single instance of this
 * class shows a single player owned shop in the manager class.
 *
 * @author Voidstar
 */
public class TradingPost extends ItemContainer {

    /**
     * The total capacity of items a shop can contain.
     */
    public static final int SHOP_CAPACITY = 32;

    private boolean muted;
    private final HashMap<Integer, Long> itemPrices = new HashMap<>(SHOP_CAPACITY);
    private String ownerUsername;
    private Player owner;

    public TradingPost(JsonObject jo) {
        super(null);
        if (jo.has("items")) {
            jo.getAsJsonArray("items").forEach(je -> {
                JsonObject itemObject = je.getAsJsonObject();
                int id = itemObject.get("id").getAsInt();
                int amount = itemObject.get("amount").getAsInt();
                int price = itemObject.get("price").getAsInt();
                add(id, amount);
                setPrice(id, price);
            });
        }
        if (jo.has("owner")) {
            setOwnerName(jo.get("owner").getAsString());
        }
        if (jo.has("muted")) {
            setMuted(jo.get("muted").getAsBoolean());
        }
    }

    public TradingPost(Player owner) {
        super(null);
        setOwner(owner);
    }

    public void open(Player player) {
        if (player.getGameMode() == GameMode.SEASONAL_IRONMAN || player.getGameMode() == GameMode.ULTIMATE_IRONMAN || player.getGameMode() == GameMode.HARDCORE_IRONMAN || player.getGameMode() == GameMode.IRONMAN) {
            player.getPacketSender().sendMessage("Ironman players are not allowed to trade.");
            player.getPacketSender().sendInterfaceRemoval();
            return;
        }
        player.getSkillManager().stopSkilling();
        player.getPacketSender().sendClientRightClickRemoval();
        player.activeInterface = "playershops";
        player.setTradingPostOpen(true);

        player.getTradingPostManager().setCurrentlyViewing(this);
        refresh(player);
    }

    public void close(Player player) {
        player.getTradingPostManager().setCurrentlyViewing(null);
        player.setTradingPostOpen(false);
    }

    public boolean isViewing(Player player) {
        return player.getTradingPostManager().getCurrentlyViewing() == this;
    }

    @Override
    public synchronized TradingPost switchItem(ItemContainer to, Item item, int slot, boolean sort, boolean refresh) {
        if (to.getPlayer().getGameMode() != GameMode.NORMAL) {
            to.getPlayer().getPacketSender().sendMessage("Ironman players are not allowed to trade.");
            to.getPlayer().getPacketSender().sendInterfaceRemoval();
            return this;
        }
        setPlayer(to.getPlayer());
        final Player player = getPlayer();
        if (player == null)
            return this;
        if (!isViewing(player) || player.isBanking()) {
            player.getPacketSender().sendInterfaceRemoval();
            return this;
        }

        if (getItems()[slot].getId() != item.getId())
            return this;

        if (!contains(item.getId()))
            return this;

        if (getItems()[slot].getAmount() <= 0) {
            return this;
        }

        if (item.getAmount() > getItems()[slot].getAmount())
            item.setAmount(getItems()[slot].getAmount());

        int amountBuying = item.getAmount();

        if (amountBuying <= 0)
            return this;


        long pricePerItem = player == owner ? 0 : getPrice(item);

        if (pricePerItem < 0) {
            return this;
        }

        int freeSlots;
        if (item.getDefinition().isStackable()) {
            if (player.getInventory().contains(item.getId())) {
                freeSlots = Integer.MAX_VALUE - player.getInventory().getAmount(item.getId());
            } else if (player.getInventory().getFreeSlots() >= 1) {
                freeSlots = Integer.MAX_VALUE;
            } else {
                freeSlots = 0;
            }
        } else {
            freeSlots = player.getInventory().getFreeSlots();
        }

        if (freeSlots == 0) {
            player.sendMessage("You do not have space to store " + (amountBuying > 1 ? "those items" : "that item"));
            return this;
        }

        long coinsInMoneyPouch = player.getMoneyInPouch();

        if (coinsInMoneyPouch >= pricePerItem) {
            int itemsAbleToBuy = Math.min(amountBuying, freeSlots);
            if (player != owner) {
                itemsAbleToBuy = Math.min((int) (coinsInMoneyPouch / pricePerItem), itemsAbleToBuy);
                long coinsToSpend = itemsAbleToBuy * pricePerItem;
                player.setMoneyInPouch(player.getMoneyInPouch() - coinsToSpend);
                player.getPacketSender().sendString(8135, "" + player.getMoneyInPouch());
                MailBox.addCoinsToMoneyPouch(ownerUsername, coinsToSpend);
                String message = itemsAbleToBuy + " " + item.getDefinition().getName() + (itemsAbleToBuy > 1 ? "s" : "") + " for " + Misc.insertCommasToNumber(String.valueOf(coinsToSpend)) + ".";
                player.sendMessage("You purchase " + message);
                if (GameSettings.DISCORD && !player.getTradingPostManager().getMyShop().isMuted() && !this.isMuted())//just add this anywhere and edit the message/or just copy the announcement
                    new MessageBuilder().append("**[Trading Post]** " + player.getUsername() + " purchased " + message).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.MARKETPLACE_CH).get());
            } else {
                player.sendMessage("You withdraw " + itemsAbleToBuy + " " + item.getDefinition().getName() + (itemsAbleToBuy > 1 ? "s." : "."));
            }

            super.switchItem(to, new Item(item.getId(), itemsAbleToBuy), slot, sort, false);

            if (!contains(item.getId())) {
                itemPrices.remove(item.getId());
            }
        }
        if (refresh) {
            player.getInventory().refreshItems();
            refreshItems();
        }
        setPlayer(null);
        return this;
    }

    public ItemContainer add(int id, int amount, long price) {
        ItemDefinition definition = ItemDefinition.forId(id);

        if (price == -1) {
            if (definition != null) {
                price = itemPrices.getOrDefault(id, (long) definition.getValue());
            } else if (itemPrices.containsKey(id)) {
                price = itemPrices.get(id);
            } else {
                GameServer.getLogger().log(Level.SEVERE, "Something went very wrong with the %s's Trading Post. item has no definition. no price listed. id:%d, price:%d", new String[]{getOwnerName(), String.valueOf(id), String.valueOf(price)});
            }
        }

        super.add(id, amount);
        itemPrices.put(id, price);
        return this;
    }

    public Item getItem(int slot) {
        return get(slot);
    }

    public boolean contains(String name) {
        for (Item item : getItems()) {
            if (item != null && item.getId() > 0 && item.getAmount() > 0) {
                if (item.getDefinition().getName().toLowerCase().contains(name.toLowerCase())) {
                    return true;
                }
            }
        }
        return false;
    }

    public void refresh(Player player) {
        int interfaceId;
        int itemChildInterfaceId;
        if (player.getTradingPostManager().getMyShop() == this) {
            interfaceId = SHOP_EDITOR_INTERFACE_ID;
            itemChildInterfaceId = SHOP_EDITOR_ITEM_CHILD_INTERFACE_ID;
        } else {
            interfaceId = SHOP_SEARCH_INTERFACE_ID;
            itemChildInterfaceId = SHOP_SEARCH_ITEM_CHILD_INTERFACE_ID;
            player.getPacketSender().sendString(SHOP_SEARCH_INTERFACE_TITLE_ID, getOwnerName() + "'s Shop");
            player.getTradingPostManager().updateFilter("");
        }

        player.getTradingPostManager().setCurrentlyViewing(this);
        player.getPacketSender().sendItemContainer(this, itemChildInterfaceId);
        if (player.getTradingPostManager().getMyShop() == this) {
            player.getPacketSender().sendInterfaceSet(interfaceId, INVENTORY_INTERFACE_ID);
            player.getPacketSender().sendItemContainer(player.getInventory(), INVENTORY_ITEM_CHILD_INTERFACE_ID);
        } else {
            player.getPacketSender().sendInterface(interfaceId);
        }

        player.setInterfaceId(interfaceId);
        player.getInventory().refreshItems();
    }

    public JsonObject save() {
        JsonArray ja = new JsonArray();
        for (Item item : getItems()) {
            if (item != null && item.getId() > 0 && item.getAmount() > 0) {
                JsonObject jo = new JsonObject();
                jo.addProperty("id", item.getId());
                jo.addProperty("amount", item.getAmount());
                jo.addProperty("price", getPrice(item));
                ja.add(jo);
            }
        }
        JsonObject jo = new JsonObject();
        jo.addProperty("muted", isMuted());
        jo.addProperty("owner", getOwnerName());
        if (!ja.isEmpty()) {
            jo.add("items", ja);
        }
        return jo;
    }

    public boolean isMuted() {
        return muted;
    }

    public void setMuted(boolean muted) {
        this.muted = muted;
    }

    @Override
    public int capacity() {
        return SHOP_CAPACITY;
    }

    @Override
    public StackType stackType() {
        return StackType.STACKS;
    }

    @Override
    public ItemContainer refreshItems() {
        World.getPlayers().stream().filter(Objects::nonNull).forEach(player -> {
            if (player.getTradingPostManager().getCurrentlyViewing() == this) {
                refresh(player);
            }
        });
        return this;
    }

    @Override
    public ItemContainer full() {
        return this;
    }

    public int size() {
        return itemPrices.size();
    }

    public long getPrice(Item item) {
        return itemPrices.getOrDefault(item.getId(), (long) -1);
    }

    public void setPrice(int id, long price) {
        if (contains(id)) {
            itemPrices.put(id, price);
        }
    }

    public void setPrice(Item item, long price) {
        if (contains(item.getId())) {
            itemPrices.put(item.getId(), price);
        }
    }

    public void setOwnerName(String username) {
        this.ownerUsername = username;
    }

    public String getOwnerName() {
        return ownerUsername;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
        if (owner != null)
            this.ownerUsername = owner.getUsername();
    }

    public static final int INVENTORY_INTERFACE_ID = 37053;

    public static final int INVENTORY_ITEM_CHILD_INTERFACE_ID = 37054;

    public static final int SHOP_EDITOR_INTERFACE_ID = 33600;

    public static final int SHOP_EDITOR_ITEM_CHILD_INTERFACE_ID = 33621;

    public static final int SHOP_SEARCH_INTERFACE_ID = 32600;

    public static final int SHOP_SEARCH_INTERFACE_TITLE_ID = 32610;

    public static final int SHOP_SEARCH_ITEM_CHILD_INTERFACE_ID = 32621;

}
