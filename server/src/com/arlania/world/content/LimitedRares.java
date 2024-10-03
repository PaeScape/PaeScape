package com.arlania.world.content;

import com.arlania.GameServer;
import com.arlania.GameSettings;
import com.arlania.model.Item;
import com.arlania.model.container.impl.Shop;
import com.arlania.model.input.impl.EnterAmountToBuyFromShop;
import com.arlania.model.input.impl.EnterAmountToSellToShop;
import com.arlania.world.entity.impl.player.Player;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;

/**
 * @author Gabriel Hannason
 * Wrote this quickly!!
 * Handles the RFD quest
 */
public class LimitedRares {

    public static void openRareShop(final Player player) {

        int[] stock = new int[40];
        int[] stockAmount = new int[40];

        //Create the path and file objects.
        Path path = Paths.get(System.getProperty("user.home") + File.separator + "Saves" + File.separator, "Limited Rares.json");
        File file = path.toFile();

        // Now read the properties from the json parser.
        try (FileReader fileReader = new FileReader(file)) {
            JsonParser fileParser = new JsonParser();
            Gson builder = new GsonBuilder().create();
            JsonObject reader = (JsonObject) fileParser.parse(fileReader);

            if (reader.has("rare0")) {
                GameSettings.rare0 = (reader.get("rare0").getAsInt());
                stock[0] = (reader.get("rare0").getAsInt());
            }
            if (reader.has("rare0quantity")) {
                GameSettings.rare0quantity = (reader.get("rare0quantity").getAsInt());
                stockAmount[0] = (reader.get("rare0quantity").getAsInt());
            }

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            GameServer.getLogger().log(Level.SEVERE, "ruh roh", e);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            GameServer.getLogger().log(Level.SEVERE, "ruh roh", e);
        }


        Item[] stockItems = new Item[stock.length];
        for (int i = 0; i < stock.length; i++)
            stockItems[i] = new Item(stock[i], stockAmount[i]);
        Shop shop = new Shop(player, Shop.PET_STORAGE, "Pet Storage", new Item(995), stockItems);
        stock = stockAmount = null;
        stockItems = null;
        shop.setPlayer(player);
        player.getPacketSender().sendItemContainer(player.getInventory(), Shop.INVENTORY_INTERFACE_ID);
        player.getPacketSender().sendItemContainer(shop, Shop.ITEM_CHILD_ID);
        player.getPacketSender().sendString(Shop.NAME_INTERFACE_CHILD_ID, "Limited Rares");
        if (player.getInputHandling() == null || !(player.getInputHandling() instanceof EnterAmountToSellToShop || player.getInputHandling() instanceof EnterAmountToBuyFromShop))
            player.getPacketSender().sendInterfaceSet(Shop.INTERFACE_ID, Shop.INVENTORY_INTERFACE_ID - 1);
        player.setShop(shop).setInterfaceId(Shop.INTERFACE_ID).setShopping(true);
    }


    //method for updating quantity


    public static void save(Player player, int bought, int itemid) {


        if (GameSettings.rare0 == itemid)
            GameSettings.rare0quantity -= bought;


        // Create the path and file objects.
        Path path = Paths.get(System.getProperty("user.home") + File.separator + "Saves" + File.separator, "Limited Rares.json");
        File file = path.toFile();
        file.getParentFile().setWritable(true);

        // Attempt to make the player save directory if it doesn't
        // exist.
        if (!file.getParentFile().exists()) {
            try {
                file.getParentFile().mkdirs();
            } catch (SecurityException e) {
                GameServer.getLogger().log(Level.SEVERE, "Unable to create directory for player data!", e);
            }
        }
        try (FileWriter writer = new FileWriter(file)) {

            Gson builder = new GsonBuilder().setPrettyPrinting().create();
            JsonObject object = new JsonObject();

            object.addProperty("rare0", GameSettings.rare0);
            object.addProperty("rare0quantity", GameSettings.rare0quantity);


            writer.write(builder.toJson(object));
            writer.close();
        } catch (Exception e) {
            // An error happened while saving.
            GameServer.getLogger().log(Level.WARNING, "An error has occured while saving the Limited Rares file!", e);
        }
    }


}
