package com.arlania.world.content.marketplace;

import com.arlania.DiscordBot;
import com.arlania.GameServer;
import com.arlania.GameSettings;
import com.arlania.model.GroundItem;
import com.arlania.model.Item;
import com.arlania.model.Locations.Location;
import com.arlania.model.definitions.ItemDefinition;
import com.arlania.util.Misc;
import com.arlania.world.entity.impl.GroundItemManager;
import com.arlania.world.entity.impl.player.Player;
import org.javacord.api.entity.message.MessageBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeathsCoffer {

    public static final HashMap<Integer, String> COFFER_DEFAULTS = new HashMap<Integer, String>() {{
        put(8839, "UNTRADEABLES,0,500000");
        put(8840, "UNTRADEABLES,0,500000");
        put(8842, "UNTRADEABLES,0,500000");
        put(19785, "UNTRADEABLES,0,1000000");
        put(19786, "UNTRADEABLES,0,1000000");
        put(11663, "UNTRADEABLES,0,500000");
        put(11664, "UNTRADEABLES,0,500000");
        put(11665, "UNTRADEABLES,0,500000");
        put(8850, "UNTRADEABLES,0,250000");
        put(20072, "UNTRADEABLES,0,500000");
        put(10551, "UNTRADEABLES,0,500000");
        put(212791, "UNTRADEABLES,0,500000");
        put(21034, "UNTRADEABLES,0,500000");
        put(13263, "UNTRADEABLES,0,500000");
        put(18337, "UNTRADEABLES,0,500000");
        put(6500, "UNTRADEABLES,0,500000");
        put(10506, "UNTRADEABLES,0,500000");
        put(4033, "UNTRADEABLES,0,500000");
        put(4034, "UNTRADEABLES,0,500000");
        put(4035, "UNTRADEABLES,0,500000");
        put(4036, "UNTRADEABLES,0,500000");
        put(4037, "UNTRADEABLES,0,500000");
        put(213329, "UNTRADEABLES,0,500000");
        put(213392, "UNTRADEABLES,0,500000");
        put(600, "UNTRADEABLES,0,500000");
        put(730, "UNTRADEABLES,0,500000");
        put(13659, "UNTRADEABLES,0,500000");
        put(13661, "UNTRADEABLES,0,500000");
        put(221140, "UNTRADEABLES,0,500000");
        put(221177, "UNTRADEABLES,0,500000");
        put(221183, "UNTRADEABLES,0,500000");
        put(6570, "UNTRADEABLES,0,500000");
        put(221295, "UNTRADEABLES,0,500000");
        put(221285, "UNTRADEABLES,0,500000");
        put(221791, "UNTRADEABLES,0,500000");
        put(221793, "UNTRADEABLES,0,500000");
        put(221795, "UNTRADEABLES,0,500000");
        put(221776, "UNTRADEABLES,0,500000");
        put(221780, "UNTRADEABLES,0,500000");
        put(221784, "UNTRADEABLES,0,500000");
        put(219647, "UNTRADEABLES,0,500000");
        put(221264, "UNTRADEABLES,0,500000");
        put(224370, "UNTRADEABLES,0,500000");
        put(219643, "UNTRADEABLES,0,500000");
        put(219639, "UNTRADEABLES,0,500000");
        put(221888, "UNTRADEABLES,0,500000");
        put(11137, "UNTRADEABLES,0,50000");
        put(18782, "UNTRADEABLES,0,250000");
        put(221898, "UNTRADEABLES,0,500000");
        put(219722, "UNTRADEABLES,0,500000");
        put(15492, "UNTRADEABLES,0,500000");
        put(13920, "PVP,0,1000000");
        put(13908, "PVP,0,1000000");
        put(13914, "PVP,0,1000000");
        put(13926, "PVP,0,1000000");
        put(13911, "PVP,0,1000000");
        put(13917, "PVP,0,1000000");
        put(13923, "PVP,0,1000000");
        put(13929, "PVP,0,1000000");
        put(13938, "PVP,0,1000000");
        put(13932, "PVP,0,1000000");
        put(13935, "PVP,0,1000000");
        put(13941, "PVP,0,1000000");
        put(13950, "PVP,0,1000000");
        put(13944, "PVP,0,1000000");
        put(13947, "PVP,0,1000000");
        put(213258, "SKILLING,0,100000");
        put(213259, "SKILLING,0,100000");
        put(213260, "SKILLING,0,100000");
        put(213261, "SKILLING,0,100000");
        put(210941, "SKILLING,0,100000");
        put(210939, "SKILLING,0,100000");
        put(210940, "SKILLING,0,100000");
        put(210933, "SKILLING,0,100000");
        put(213646, "SKILLING,0,100000");
        put(213642, "SKILLING,0,100000");
        put(213640, "SKILLING,0,100000");
        put(213644, "SKILLING,0,100000");
        put(5554, "SKILLING,0,100000");
        put(5553, "SKILLING,0,100000");
        put(5555, "SKILLING,0,100000");
        put(5556, "SKILLING,0,100000");
        put(5557, "SKILLING,0,100000");
        put(20046, "SKILLING,0,100000");
        put(10069, "SKILLING,0,100000");
        put(7409, "SKILLING,0,100000");
        put(775, "SKILLING,0,100000");
        put(776, "SKILLING,0,100000");
        put(18338, "SKILLING,0,100000");
        put(18339, "SKILLING,0,100000");
        put(19675, "SKILLING,0,100000");
        put(1580, "SKILLING,0,100000");
        put(2949, "SKILLING,0,100000");
        put(2950, "SKILLING,0,100000");
        put(2951, "SKILLING,0,100000");
        put(2946, "SKILLING,0,100000");
        put(3702, "SKILLING,0,100000");
        put(10075, "SKILLING,0,100000");
        put(11157, "PETS,0,500000");
        put(6550, "PETS,0,500000");
        put(8740, "PETS,0,500000");
        put(212335, "PETS,0,500000");
        put(11971, "PETS,0,500000");
        put(11972, "PETS,0,500000");
        put(11978, "PETS,0,500000");
        put(11979, "PETS,0,500000");
        put(11981, "PETS,0,500000");
        put(11982, "PETS,0,500000");
        put(11983, "PETS,0,500000");
        put(11984, "PETS,0,500000");
        put(11985, "PETS,0,500000");
        put(11986, "PETS,0,500000");
        put(11987, "PETS,0,500000");
        put(11988, "PETS,0,500000");
        put(11989, "PETS,0,500000");
        put(11990, "PETS,0,500000");
        put(11991, "PETS,0,500000");
        put(11992, "PETS,0,500000");
        put(11993, "PETS,0,500000");
        put(11994, "PETS,0,500000");
        put(11995, "PETS,0,500000");
        put(11996, "PETS,0,500000");
        put(11997, "PETS,0,500000");
        put(12001, "PETS,0,500000");
        put(12002, "PETS,0,500000");
        put(12003, "PETS,0,500000");
        put(12004, "PETS,0,500000");
        put(12005, "PETS,0,500000");
        put(12006, "PETS,0,500000");
        put(14916, "PETS,0,500000");
        put(20079, "PETS,0,500000");
        put(20080, "PETS,0,500000");
        put(20081, "PETS,0,500000");
        put(20082, "PETS,0,500000");
        put(20083, "PETS,0,500000");
        put(20085, "PETS,0,500000");
        put(20088, "PETS,0,500000");
        put(20089, "PETS,0,500000");
        put(20090, "PETS,0,500000");
        put(20103, "PETS,0,500000");
        put(20104, "PETS,0,500000");
        put(14914, "PETS,0,500000");
        put(221992, "PETS,0,500000");
        put(20086, "PETS,0,500000");
        put(221509, "PETS2,0,500000");
        put(220659, "PETS2,0,500000");
        put(220661, "PETS2,0,500000");
        put(220663, "PETS2,0,500000");
        put(220665, "PETS2,0,500000");
        put(220693, "PETS2,0,500000");
        put(221187, "PETS2,0,500000");
        put(213322, "PETS2,0,500000");
        put(211320, "PETS2,0,500000");
        put(220851, "PETS2,0,500000");
        put(212399, "PETS2,0,500000");
        put(212646, "PETS2,0,500000");
        put(222473, "PETS2,0,500000");
        put(224491, "PETS2,0,500000");
        put(219730, "PETS2,0,500000");
        put(207582, "PETS2,0,500000");
    }};

    public static final int
            INTERFACE_ID = 56090,
            TITLE = INTERFACE_ID + 2,
            ITEMS_INTERFACE = INTERFACE_ID + 27,
            INVENTORY_ID = ITEMS_INTERFACE + 1,
            INVENTORY_ITEMS = INVENTORY_ID + 1;

    enum UNIQUE_TYPES {
        UNTRADEABLES, PVP, SKILLING, PETS, PETS2

    }

    public static void openInter(Player player) {
        player.getPacketSender().sendInterfaceRemoval().sendClientRightClickRemoval();
        player.getPacketSender().sendInterfaceSet(INTERFACE_ID, INVENTORY_ID);
        clearItems(player);
        sendItems(player, UNIQUE_TYPES.UNTRADEABLES);
    }

    private static void sendItems(Player player, UNIQUE_TYPES unique_types) {
        player.getCofferData();
        //TITLE
        player.getPacketSender().sendFrame126(TITLE, "Death's Coffers @whi@(" + Misc.formatText(unique_types.name().replace("_", " ").toLowerCase()) + ")");

        List<Item> items = new ArrayList<Item>();
        player.cofferData.forEach((itemId, data) -> {
            String[] coffer_data = data.split(",");
            String types = coffer_data[0];
            String amount = coffer_data[1];
            if (unique_types.name().equalsIgnoreCase(types)) {
                items.add(new Item(itemId, Integer.parseInt(amount)));
//					player.sm("types="+types+" amount="+amount);
            }
        });
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            ItemDefinition defs = ItemDefinition.forId(item.getId());
            if (defs.getName().equals("None")) {//check if the item is non existent
                GameServer.getLogger().warning("This item definition is null: " + item.getId());
                return;
            }
            player.getPacketSender().sendItemOnInterface(ITEMS_INTERFACE, item.getId(), i, item.getAmount());
        }
        player.getPacketSender().sendItemContainer(player.getInventory(), INVENTORY_ITEMS);
//		player.sm("items.size()="+items.size());
    }

    private static void clearItems(Player player) {
        for (int i = 0; i < 198; i++) {
            player.getPA().sendItemOnInterface(ITEMS_INTERFACE, -1, 0);
        }
    }


    static String title = "[Death] ";

    public static void handleStoreItemOptions(Player player, int id, int slot, int option) {
        if (option == 1) {//VALUE
            player.cofferData.forEach((itemId, data) -> {
                if (itemId == id) {
                    String amount = data.split(",")[1];
                    String price = data.split(",")[2];
                    int real_amount = Integer.parseInt(amount);
                    int real_price = Integer.parseInt(price);

                    player.sm(title + " Buy price for (" + ItemDefinition.forId(id).getName() + "): " + Misc.format(real_price));
                }
            });
        }
        if (option == 2 || option == 3 || option == 4 || option == 5) {//BUY
            String coffer_data = player.cofferData.get(id);
            if (coffer_data == null) {
                player.sm(title + "This item is no longer available.");
                return;
            }
            String type = coffer_data.split(",")[0];
            String amount = coffer_data.split(",")[1];
            String price = coffer_data.split(",")[2];
            int real_amount = Integer.parseInt(amount);
            int real_price = Integer.parseInt(price);

            boolean pet = false;
            int pets = GameSettings.PETS.length - 1;

            for (int i = 0; i < pets; i++) {
                if (id == GameSettings.PETS[i]) {
                    pet = true;
                    break;
                }
            }

            if (real_amount < 1) {
                clearItems(player);
                for (UNIQUE_TYPES types : UNIQUE_TYPES.values()) {
                    if (types.name().equals(type)) {
                        sendItems(player, types);
                        break;
                    }
                }
                player.sm(title + "No more item available for sale.");
                return;
            }
            ItemDefinition defs = ItemDefinition.forId(id);
            if (defs == null) {
                player.sm(title + "This item definition is null, You can't buy this.");
                return;
            }
            if (!defs.isStackable() && player.getInventory().getFreeSlots() <= 0) {
                player.sm(title + "No more inventory space available.");
                return;
            }
            if (defs.isStackable() && player.getInventory().getFreeSlots() <= 0) {
                player.sm(title + "No more inventory space available.");
                return;
            }


            int buyAmount = (option == 2 ? 1 : option == 3 ? 10 : option == 4 ? 100 : option == 5 ? 1000 : 0);
            if (real_amount < buyAmount) {
                buyAmount = real_amount;
            }

            if ((buyAmount > player.getInventory().getFreeSlots()) && !defs.isStackable())
                buyAmount = player.getInventory().getFreeSlots();

            real_price *= buyAmount;
            //player.sm("real_price=" + real_price + " buyAmount=" + buyAmount);
            if (player.getMoneyInPouch() >= (long) real_price) {
                player.setMoneyInPouch(player.getMoneyInPouch() - (long) real_price);
                player.getPacketSender().sendString(8135, "" + player.getMoneyInPouch());
            } else if (player.getInventory().getAmount(995) >= real_price) {
                player.getInventory().delete(995, real_price, true);
            } else {
                player.sm(title + "You can't afford this item.");
                return;
            }
            if (!pet) {
                real_amount -= buyAmount;
                player.cofferData.put(id, type + "," + real_amount + "," + price);
            }
            Item item = new Item(id, buyAmount);
            player.getInventory().add(id, buyAmount);
            player.sm(title + "You succesfully bought x" + item.getAmount() + " " + item.getName() + " for " + real_price);

            String discordLog = "[" + Misc.getCurrentServerTime() + "] Username: " + player.getUsername() + " bought " + item.getAmount() + " " + item.getName() + " for " + real_price + " from Death's Coffers.";

            if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                new MessageBuilder().append(discordLog).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.DISC_DEATHSCOFFERS_CH).get());

            clearItems(player);
            for (UNIQUE_TYPES types : UNIQUE_TYPES.values()) {
                if (types.name().equals(type)) {
                    sendItems(player, types);
                    break;
                }
            }
        }
    }

    public static void handleInvItemOptions(Player player, int id, int slot, int option) {
//		player.sm("inv option: "+option);
        if (option == 1) {//VALUE
            player.cofferData.forEach((itemId, data) -> {
                if (itemId == id) {
                    String amount = data.split(",")[1];
                    String price = data.split(",")[2];
                    int real_amount = Integer.parseInt(amount);
                    int real_price = Integer.parseInt(price);

                    player.sm(title + " Sell price for (" + ItemDefinition.forId(id).getName() + "): " + Misc.format(((int) (real_price * 0.75))));
                }
            });
        }
        if (option == 2 || option == 3 || option == 4 || option == 5) {
            int actualId = id;

            if (ItemDefinition.forId(actualId).getName().toLowerCase().contains("pink") && actualId > 21000) {
                actualId = 21034;
            }

            String coffer_data = player.cofferData.get(actualId);

            if (coffer_data == null)
                return;

            if (!Item.tradeable(actualId)) {
                if (player.getLocation() != Location.DEATHS_COFFERS) {
                    //player.getInventory().switchItem(player.getBank(Bank.getTabForItem(player, actualId)), new Item(actualId, 1), player.getInventory().getSlot(actualId), false, true);
                    player.sm("Your @or2@" + ItemDefinition.forId(actualId).getName() + " @bla@ was sent to Death's Coffers.");
                } /*else {
                    player.sm("You can't sell untradeable items to Death's Coffers!");
                    return;
                }*/
            }

            String type = coffer_data.split(",")[0];
            String amount = coffer_data.split(",")[1];
            String price = coffer_data.split(",")[2];
            int real_amount = Integer.parseInt(amount);

            int sellAmount = (option == 2 ? 1 : option == 3 ? 5 : option == 4 ? 10 : option == 5 ? Integer.MAX_VALUE : 0);
            if (sellAmount > player.getInventory().getAmount(actualId))
                sellAmount = player.getInventory().getAmount(actualId);
            real_amount += sellAmount;

            player.cofferData.put(actualId, type + "," + real_amount + "," + price);

            player.getInventory().delete(actualId, sellAmount);
            player.getInventory().refreshItems();

            clearItems(player);
            for (UNIQUE_TYPES types : UNIQUE_TYPES.values()) {
                if (types.name().equals(type)) {
                    sendItems(player, types);
                    break;
                }
            }
        }

    }

    public static void handleDeathItems(Player player, int id, int newAmount) {
        int actualId = id;

        if (ItemDefinition.forId(actualId).getName().toLowerCase().contains("pink") && actualId > 21000 && player.getLocation() == Location.WILDERNESS) {
            actualId = 21034;
        }

        String coffer_data = player.cofferData.get(actualId);

        if (coffer_data == null) {
            GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(actualId, newAmount), player.getPosition(), player.getUsername(), player.getHostAddress(), false, 1000, true, 500));
            return;
        }

        if (!Item.tradeable(actualId)) {
            if (player.getLocation() != Location.DEATHS_COFFERS) {
                //player.getInventory().switchItem(player.getBank(Bank.getTabForItem(player, actualId)), new Item(actualId, 1), player.getInventory().getSlot(actualId), false, true);
                player.sm("Your @or2@" + ItemDefinition.forId(actualId).getName() + " @bla@ was sent to Death's Coffers.");
            } else {
                player.sm("You can't sell untradeable items to Death's Coffers!");
                return;
            }
        }

        String discordLog = "[" + Misc.getCurrentServerTime() + "] Username: " + player.getUsername() + " sent " + newAmount + " " + ItemDefinition.forId(id).getName() + " to Death's Coffers.";

        if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
            new MessageBuilder().append(discordLog).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.DISC_DEATHSCOFFERS_CH).get());


        String type = coffer_data.split(",")[0];
        String amount = coffer_data.split(",")[1];
        String price = coffer_data.split(",")[2];
        int real_amount = Integer.parseInt(amount);
        int real_price = Integer.parseInt(price);
        real_price = (int) (real_price * 0.5); //price player receives after cofferplace fee
        real_amount += newAmount;

        player.cofferData.put(actualId, type + "," + real_amount + "," + price);

        player.getInventory().delete(actualId, real_amount);
        player.getInventory().add(995, real_price);
    }


    public static void storePet(Player player, int id) {
        String coffer_data = player.cofferData.get(id);
        if (coffer_data == null) {
            return;
        }

        String type = coffer_data.split(",")[0];
        String amount = coffer_data.split(",")[1];
        String price = coffer_data.split(",")[2];
        int real_amount = Integer.parseInt(amount);
        int real_price = Integer.parseInt(price);

        if (real_amount > 0)
            return;

        player.sm("Your @or2@" + ItemDefinition.forId(id).getName() + " @bla@ was automatically stored at Death's Coffers.");
        player.cofferData.put(id, type + "," + 1 + "," + price);
    }

    public static boolean handleButtons(Player player, int id) {
        switch (id) {
            case 56096://shop1
                clearItems(player);
                sendItems(player, UNIQUE_TYPES.UNTRADEABLES);
                return true;
            case 56100://shop2
                clearItems(player);
                sendItems(player, UNIQUE_TYPES.PVP);
                return true;
            case 56104://shop3
                clearItems(player);
                sendItems(player, UNIQUE_TYPES.SKILLING);
                return true;
            case 56108://shop4
                clearItems(player);
                sendItems(player, UNIQUE_TYPES.PETS);
                return true;
            case 56112://shop5
                clearItems(player);
                sendItems(player, UNIQUE_TYPES.PETS2);
                return true;
            case 56093:
                player.getPA().closeAllWindows();
                return true;
        }
        return false;
    }

    public static void ensureConsistency(Map<Integer, String> cofferData) {
        for (Map.Entry<Integer, String> entry : COFFER_DEFAULTS.entrySet())
            cofferData.putIfAbsent(entry.getKey(), entry.getValue());
    }

    public static void petFix(Player player, int id) {

        String coffer_data = player.cofferData.get(id);
        if (coffer_data == null) {
            return;
        }

        player.sm("Your @or2@" + ItemDefinition.forId(id).getName() + " @bla@ was moved to Death's Coffers!");

        String type = coffer_data.split(",")[0];
        String amount = coffer_data.split(",")[1];
        String price = coffer_data.split(",")[2];

        player.cofferData.put(id, type + "," + 1 + "," + price);
    }


}
