package com.arlania.world.content.marketplace;

import com.arlania.DiscordBot;
import com.arlania.GameServer;
import com.arlania.GameSettings;
import com.arlania.model.Animation;
import com.arlania.model.GameMode;
import com.arlania.model.Item;
import com.arlania.model.definitions.ItemDefinition;
import com.arlania.net.packet.ByteOrder;
import com.arlania.net.packet.Packet;
import com.arlania.net.packet.PacketBuilder;
import com.arlania.util.Misc;
import com.arlania.world.World;
import com.arlania.world.entity.impl.player.Player;
import com.google.common.base.Preconditions;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import lombok.Data;
import org.javacord.api.entity.message.MessageBuilder;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class Marketplace {

    public static final int MAX_ITEM_SLOTS = 77; // actually 363, but that's really huge and we don't need to send the client that much data

    public static final int
            INTERFACE_ID = 56000,
            TITLE_ID = INTERFACE_ID + 2,
            INVENTORY_ID = 56028,
            INVENTORY_ITEMS = 56029,
            ITEMS_INTERFACE = 56027;
    private static final String MESSAGE_TITLE = "[Marketplace] ";
    private static final File SAVE_FILE = new File(System.getProperty("user.home") + File.separator + "Saves" + File.separator + "Data" + File.separator + "market_data.json");

    public enum ItemType {
        LOW_UNIQUES, MEDIUM_UNIQUES, HIGH_UNIQUES, LEGENDARY_UNIQUES, MASTER_UNIQUES
    }

    private static Map<ItemType, List<MarketplaceEntry>> marketData;

    public static void init() {
        load();
        if (marketData == null) {
            // TODO: populate map with blanks
        }
    }

    public static void load() {
        if (!SAVE_FILE.exists()) {
            GameServer.getLogger().severe("market_data.json file did not exist");
            return;
        }
        try {
            JsonObject jsonUpdates = JsonParser.parseReader(new FileReader(SAVE_FILE)).getAsJsonObject();
            Type listType = new TypeToken<HashMap<ItemType, List<MarketplaceEntry>>>() {
            }.getType();

            marketData = new Gson().fromJson(jsonUpdates, listType);
        } catch (Exception e) {
            GameServer.getLogger().log(Level.SEVERE, "No marketdata found!", e);
        }
    }

    public static void save() {
        if (!SAVE_FILE.exists()) {
            Preconditions.checkState(SAVE_FILE.getParentFile().mkdirs());
        }
        try (FileWriter writer = new FileWriter(SAVE_FILE)) {
            Gson builder = new GsonBuilder().setPrettyPrinting().create();
            writer.write(builder.toJson(marketData));
        } catch (Exception e) {
            GameServer.getLogger().log(Level.SEVERE, "Error saving marketdata!", e);
        }
    }

    public static void open(Player player) {
        if (player.getGameMode() != GameMode.NORMAL) {
            player.getPacketSender().sendMessage("Ironman players are not allowed to trade.");
            player.getPacketSender().sendInterfaceRemoval();
            return;
        }

        player.getPacketSender().sendInterfaceRemoval().sendClientRightClickRemoval();
        sendItems(player, player.getAttribute("marketplace_tab", ItemType.LOW_UNIQUES));
        player.getPacketSender().sendInterfaceSet(INTERFACE_ID, INVENTORY_ID);
    }

    private static void sendItems(Player player, ItemType itemType) {
        //TITLE
        player.getPacketSender().sendFrame126(TITLE_ID, "PaeScape MarketPlace @whi@(" + Misc.formatText(itemType.name().replace("_", " ").toLowerCase()) + ")");

        // hacky "sendItemContainer" impl
        List<MarketplaceEntry> entries = marketData.get(itemType);
        PacketBuilder out = new PacketBuilder(53, Packet.PacketType.SHORT);
        out.putShort(ITEMS_INTERFACE);
        out.putShort(MAX_ITEM_SLOTS);
        for (MarketplaceEntry entry : entries) {
            ItemDefinition defs = ItemDefinition.forId(entry.getId());
            if (defs.getName().equals("None")) {//check if the item is non existent
                System.out.println("This item definition is null: " + entry.getId());
                continue;
            }
            if (entry.getAmount() > 254) {
                out.put((byte) 255);
                out.putInt(entry.getAmount(), ByteOrder.INVERSE_MIDDLE);
            } else {
                out.put(entry.getAmount());
            }
            out.putInt(entry.getId() + 1);


        }
        for (int slot = entries.size(); slot < MAX_ITEM_SLOTS; slot++) {
            out.put(0);
            out.putInt(0);
        }
        player.getSession().queueMessage(out);

        // send player's inventory
        player.getPacketSender().sendItemContainer(player.getInventory(), INVENTORY_ITEMS);
    }

    public static void handleStoreItemOptions(Player player, int id, int slot, int option) {
        ItemType itemType = player.getAttribute("marketplace_tab", ItemType.LOW_UNIQUES);
        Optional<MarketplaceEntry> entryOpt = marketData.get(itemType).stream()
                .filter((MarketplaceEntry e) -> e.getId() == id).findFirst();

        if (entryOpt.isPresent()) {
            MarketplaceEntry entry = entryOpt.get();
            long cost = entry.getBuyPrice();
            if (option == 1) {//VALUE
                sendMarketplaceMessage(player, " Buy price for (" + ItemDefinition.forId(id).getName() + "): " + Misc.format(cost));
            } else if (option == 2 || option == 3 || option == 4) {//BUY
                if (entry.getAmount() < 1) {
                    sendItems(player, itemType);
                    sendMarketplaceMessage(player, "No more item available for sale.");
                    return;
                }

                ItemDefinition defs = ItemDefinition.forId(id);
                if (defs == null) {
                    sendMarketplaceMessage(player, "This item definition is null, You can't buy this.");
                    return;
                }
                if (!defs.isStackable() && player.getInventory().getFreeSlots() <= 0) {
                    sendMarketplaceMessage(player, "No more inventory space available.");
                    return;
                }
                if (defs.isStackable() && player.getInventory().getAmount(id) <= 0) {
                    sendMarketplaceMessage(player, "No more inventory space available.");
                    return;
                }
                int buyAmount = (option == 2 ? 1 : option == 3 ? 5 : option == 4 ? 10 : 0);
                if (entry.getAmount() < buyAmount) {
                    buyAmount = entry.getAmount();
                }

                if (buyAmount > player.getInventory().getFreeSlots()) {
                    buyAmount = player.getInventory().getFreeSlots();
                }

                cost *= buyAmount;

                if (player.getMoneyInPouch() >= cost) {
                    player.setMoneyInPouch(player.getMoneyInPouch() - cost);
                    player.getPacketSender().sendString(8135, "" + player.getMoneyInPouch());
                } else if (player.getInventory().getAmount(995) >= cost && cost <= Integer.MAX_VALUE) {
                    player.getInventory().delete(995, (int) cost, true);
                } else {
                    sendMarketplaceMessage(player, "You can't afford this item.");
                    return;
                }

                entry.setAmount(entry.getAmount() - buyAmount);

                Item item = new Item(id, buyAmount);
                player.getInventory().add(id, buyAmount);
                sendMarketplaceMessage(player, "You succesfully bought x" + item.getAmount() + " " + item.getName() + " for " + cost);

                String commandLog = "[" + Misc.getCurrentServerTime() + "] Username: " + player.getUsername() + " bought " + item.getAmount() + " " + item.getName() + " for " + cost;
                if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                    new MessageBuilder().append(commandLog).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.DISC_MARKETPLACE_CH).get());

                sendItems(player, itemType);
            }
        } else {
            if (option == 2 || option == 3 || option == 4) {//BUY
                sendMarketplaceMessage(player, "This item is no longer available.");
            }
        }

    }

    public static void handleInvItemOptions(Player player, int id, int slot, int option) {
        MarketplaceEntry entry = null;
        ItemType itemType = player.getAttribute("marketplace_tab", ItemType.LOW_UNIQUES);
        for (ItemType it : ItemType.values()) {
            Optional<MarketplaceEntry> entryOpt = marketData.get(it).stream()
                    .filter((MarketplaceEntry e) -> e.getId() == id).findFirst();
            if (entryOpt.isPresent()) {
                entry = entryOpt.get();
                itemType = it;
                break;
            }
        }

        if (entry == null) {
            sendMarketplaceMessage(player, "This item can't be sold here...");
            return;
        }

        if (itemType != player.getAttribute("marketplace_tab", ItemType.LOW_UNIQUES)) {
            player.setAttribute("marketplace_tab", itemType);
            sendItems(player, itemType);
        }


        long cost = entry.getSellPrice();
        long tax = entry.getTax();

        if (option == 1) {//VALUE
            sendMarketplaceMessage(player, "Sell price for (" + ItemDefinition.forId(id).getName() + "): " + Misc.format(cost));
        } else if (option == 2 || option == 3 || option == 4 || option == 5) {//SELL

            int sellAmount = (option == 2 ? 1 : option == 3 ? 5 : option == 4 ? 10 : option == 5 ? Integer.MAX_VALUE : 0);
            if (sellAmount > player.getInventory().getAmount(id))
                sellAmount = player.getInventory().getAmount(id);
            cost *= sellAmount;
            tax *= sellAmount;

            player.getInventory().delete(id, sellAmount);
            if (cost + player.getInventory().getAmount(995) > Integer.MAX_VALUE) {
                player.setMoneyInPouch(player.getMoneyInPouch() + cost);
                player.getPacketSender().sendString(8135, "" + player.getMoneyInPouch());
                World.taxes += tax;
            } else {
                int coinAmt = (int) (cost);
                player.getInventory().add(995, coinAmt);
            }

            entry.setAmount(entry.getAmount() + sellAmount);

            sendMarketplaceMessage(player, "You succesfully sold x" + sellAmount + " " + ItemDefinition.forId(id).getName() + " for " + Misc.format(cost));

            String commandLog = "[" + Misc.getCurrentServerTime() + "] Username: " + player.getUsername() + " sold " + sellAmount + " " + ItemDefinition.forId(id).getName() + " for " + cost;
            if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                new MessageBuilder().append(commandLog).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.DISC_MARKETPLACE_CH).get());

            sendItems(player, itemType);
        }

    }

    public static boolean handleButtons(Player player, int id) {
        switch (id) {
            case 56006://shop1
                player.setAttribute("marketplace_tab", ItemType.LOW_UNIQUES);
                sendItems(player, ItemType.LOW_UNIQUES);
                return true;
            case 56010://shop2
                player.setAttribute("marketplace_tab", ItemType.MEDIUM_UNIQUES);
                sendItems(player, ItemType.MEDIUM_UNIQUES);
                return true;
            case 56014://shop3
                player.setAttribute("marketplace_tab", ItemType.HIGH_UNIQUES);
                sendItems(player, ItemType.HIGH_UNIQUES);
                return true;
            case 56018://shop4
                player.setAttribute("marketplace_tab", ItemType.LEGENDARY_UNIQUES);
                sendItems(player, ItemType.LEGENDARY_UNIQUES);
                return true;
            case 56022://shop5
                player.setAttribute("marketplace_tab", ItemType.MASTER_UNIQUES);
                sendItems(player, ItemType.MASTER_UNIQUES);
                return true;
            case 56003:
                player.getPA().closeAllWindows();
                return true;
        }
        return false;
    }


    public static long getCandyCredit(Player player, int id, int amount) {
        Optional<MarketplaceEntry> entryOpt = marketData.values().stream().flatMap(Collection::stream).filter(me -> me.getId() == id).findFirst();
        if (!entryOpt.isPresent()) {
            sendMarketplaceMessage(player, "This item can't be sold here...");
            return -1;
        }
        MarketplaceEntry entry = entryOpt.get();
        long cost = entry.getBuyPrice();

        sendMarketplaceMessage(player, "Trade-in price: " + cost);
        amount = Math.min(amount, player.getInventory().getAmount(id));
        cost *= amount;

        long candyCredit = cost / 1000;

        if (candyCredit < 1) {
            sendMarketplaceMessage(player, "@red@This item won't trade for at least one CandyCredit.");
            return -1;
        }

        player.performAnimation(new Animation(827));
        player.getInventory().delete(id, amount);

        sendMarketplaceMessage(player, "You succesfully sold " + ItemDefinition.forId(id).getName() + " for " + Misc.format(candyCredit) + " Candy Credits!");

        return candyCredit;
    }

    public static void removeItemsWithCollectedTaxes(long collectedTaxes, long taxes) {
        StringBuilder discordMessage = new StringBuilder("Marketplace Items Yeeted:\n```");
        discordMessage.append("Nobility Yeet: ").append(collectedTaxes).append('\n').append('\n');
        discordMessage.append("Tax Yeet: ").append(taxes).append('\n').append('\n');

        long totalYeet = collectedTaxes + taxes;

        for (ItemType itemType : ItemType.values()) {
            List<MarketplaceEntry> entries = marketData.get(itemType).stream()
                    .sorted(Comparator.comparing(MarketplaceEntry::getAmount, Comparator.reverseOrder()))
                    .collect(Collectors.toList());

            for (MarketplaceEntry me : entries) {
                if (me.getAmount() <= 50)
                    break;

                int itemsAbleToYeet = (int) Math.min(Math.min(me.getAmount() - 50, totalYeet / me.getSellPrice()), 20L);
                if (itemsAbleToYeet <= 0)
                    continue;

                discordMessage.append(itemsAbleToYeet).append("x ")
                        .append(ItemDefinition.forId(me.getId()).getName())
                        .append(" (").append(itemsAbleToYeet * me.getSellPrice()).append(")").append("\n");

                me.setAmount(me.getAmount() - itemsAbleToYeet);
                totalYeet -= (long) itemsAbleToYeet * me.getSellPrice();
                if (totalYeet <= 0)
                    break;
            }
            if (totalYeet <= 0)
                break;
        }
        discordMessage.append("```");

        if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
            new MessageBuilder().append(discordMessage.toString()).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.MARKETPLACE_YEET_LOG).get());

        World.taxes = 0;
    }

    public static void sendMarketplaceMessage(Player player, String message) {
        player.sendMessage(MESSAGE_TITLE + message);
    }

    public static int getCurrentPrice(int id, ItemType itemType) {
        int cost = 0;

        Optional<MarketplaceEntry> entryOpt = marketData.get(itemType).stream()
                .filter((MarketplaceEntry e) -> e.getId() == id).findFirst();

        if (entryOpt.isPresent()) {
            MarketplaceEntry entry = entryOpt.get();
            cost = entry.getBuyPrice();
        }

        return cost;
    }

    @Data
    public static class MarketplaceEntry {
        private int id;
        private int amount;
        private int basePrice;

        public long getSellPrice() {
            return (long) (basePrice * Math.pow(.99, amount) * 0.75);
        }
        public long getTax() {
            return (long) (basePrice * Math.pow(.99, amount) * 0.25);
        }

        public int getBuyPrice() {
            return (int) (basePrice * Math.pow(.99, amount));
        }
    }

    /*
     * ONLY USED TO CREATE JSON DATA TO START
     */
//	enum UNIQUE_ITEMS{
//		//#		-	type	-	-	-	-itemId	- price-
//		ITEM1(UNIQUE_TYPES.MASTER_UNIQUES,21005,100000000),
//		ITEM2(UNIQUE_TYPES.MASTER_UNIQUES,21006,100000000),
//		
//		ITEM50(UNIQUE_TYPES.LEGENDARY_UNIQUES,14484,10000000),
//		ITEM51(UNIQUE_TYPES.LEGENDARY_UNIQUES,18888,10000000),
//		
//		ITEM100(UNIQUE_TYPES.HIGH_UNIQUES,11724,1000000),
//		ITEM101(UNIQUE_TYPES.HIGH_UNIQUES,11726,1000000),
//		
//		ITEM150(UNIQUE_TYPES.MEDIUM_UNIQUES,4453,100000),
//		ITEM151(UNIQUE_TYPES.MEDIUM_UNIQUES,11730,100000),
//		
//		ITEM200(UNIQUE_TYPES.LOW_UNIQUES,4708,10000),
//		ITEM201(UNIQUE_TYPES.LOW_UNIQUES,4710,10000),
//		
//		;
//		private UNIQUE_TYPES types;
//		private int itemId,price;
//		UNIQUE_ITEMS(UNIQUE_TYPES types, int itemId, int price) {
//			this.types = types;
//			this.itemId = itemId;
//			this.price = price;
//		}
//		public UNIQUE_TYPES getTypes() {
//			return types;
//		}
//		public void setTypes(UNIQUE_TYPES types) {
//			this.types = types;
//		}
//		public int getItemId() {
//			return itemId;
//		}
//		public void setItemId(int itemId) {
//			this.itemId = itemId;
//		}
//		public int getPrice() {
//			return price;
//		}
//		public void setPrice(int price) {
//			this.price = price;
//		}
//		
//	}
//	
//	public static void loadDefaultData() {
//		for(UNIQUE_ITEMS uItems : UNIQUE_ITEMS.values()){
//				marketData.put(uItems.itemId, new String(uItems.types.name()+",0,"+uItems.price));
//			}
//		saveToJSON();
//	}

//	public class MarketData {
//	public UNIQUE_TYPES types;
//	public Integer id,amount,price;
//	
//	MarketData(UNIQUE_TYPES types,Integer id, Integer amount, Integer price) {
//		this.types = types;
//		this.id = id;
//		this.amount = amount;
//		this.price = price;
//	}
//}
//

}
