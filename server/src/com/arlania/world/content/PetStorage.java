package com.arlania.world.content;

import com.arlania.model.Item;
import com.arlania.model.container.impl.Shop;
import com.arlania.model.input.impl.EnterAmountToBuyFromShop;
import com.arlania.model.input.impl.EnterAmountToSellToShop;
import com.arlania.world.entity.impl.player.Player;

/**
 * @author Gabriel Hannason
 * Wrote this quickly!!
 * Handles the RFD quest
 */
public class PetStorage {

    public static void openPetShop(final Player player) {
        int[] stock = new int[40];
        int[] stockAmount = new int[40];

        stock[0] = player.petStorage1;
        stock[1] = player.petStorage2;
        stock[2] = player.petStorage3;
        stock[3] = player.petStorage4;
        stock[4] = player.petStorage5;
        stock[5] = player.petStorage6;
        stock[6] = player.petStorage7;
        stock[7] = player.petStorage8;
        stock[8] = player.petStorage9;
        stock[9] = player.petStorage10;
        stock[10] = player.petStorage11;
        stock[11] = player.petStorage12;
        stock[12] = player.petStorage13;
        stock[13] = player.petStorage14;
        stock[14] = player.petStorage15;
        stock[15] = player.petStorage16;
        stock[16] = player.petStorage17;
        stock[17] = player.petStorage18;
        stock[18] = player.petStorage19;
        stock[19] = player.petStorage20;
        stock[20] = player.petStorage21;
        stock[21] = player.petStorage22;
        stock[22] = player.petStorage23;
        stock[23] = player.petStorage24;
        stock[24] = player.petStorage25;
        stock[25] = player.petStorage26;
        stock[26] = player.petStorage27;
        stock[27] = player.petStorage28;
        stock[28] = player.petStorage29;
        stock[29] = player.petStorage30;
        stock[30] = player.petStorage31;
        stock[31] = player.petStorage32;
        stock[32] = player.petStorage33;
        stock[33] = player.petStorage34;
        stock[34] = player.petStorage35;
        stock[35] = player.petStorage36;
        stock[36] = player.petStorage37;
        stock[37] = player.petStorage38;
        stock[38] = player.petStorage39;
        stock[39] = player.petStorage40;

        for (int i = 0; i < stock.length; i++) {
            stockAmount[i] = 2;
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
        player.getPacketSender().sendString(Shop.NAME_INTERFACE_CHILD_ID, "Pet Storage");
        if (player.getInputHandling() == null || !(player.getInputHandling() instanceof EnterAmountToSellToShop || player.getInputHandling() instanceof EnterAmountToBuyFromShop))
            player.getPacketSender().sendInterfaceSet(Shop.INTERFACE_ID, Shop.INVENTORY_INTERFACE_ID - 1);
        player.setShop(shop).setInterfaceId(Shop.INTERFACE_ID).setShopping(true);
    }

    public static void savePets(final Player player, int petId) {

        if (player.petStorage1 == petId) {
            player.getPacketSender().sendMessage("@red@You've already stored this pet!");
            return;
        } else if (player.petStorage2 == petId) {
            player.getPacketSender().sendMessage("@red@You've already stored this pet!");
            return;
        } else if (player.petStorage3 == petId) {
            player.getPacketSender().sendMessage("@red@You've already stored this pet!");
            return;
        } else if (player.petStorage4 == petId) {
            player.getPacketSender().sendMessage("@red@You've already stored this pet!");
            return;
        } else if (player.petStorage5 == petId) {
            player.getPacketSender().sendMessage("@red@You've already stored this pet!");
            return;
        } else if (player.petStorage6 == petId) {
            player.getPacketSender().sendMessage("@red@You've already stored this pet!");
            return;
        } else if (player.petStorage7 == petId) {
            player.getPacketSender().sendMessage("@red@You've already stored this pet!");
            return;
        } else if (player.petStorage8 == petId) {
            player.getPacketSender().sendMessage("@red@You've already stored this pet!");
            return;
        } else if (player.petStorage9 == petId) {
            player.getPacketSender().sendMessage("@red@You've already stored this pet!");
            return;
        } else if (player.petStorage10 == petId) {
            player.getPacketSender().sendMessage("@red@You've already stored this pet!");
            return;
        } else if (player.petStorage11 == petId) {
            player.getPacketSender().sendMessage("@red@You've already stored this pet!");
            return;
        } else if (player.petStorage12 == petId) {
            player.getPacketSender().sendMessage("@red@You've already stored this pet!");
            return;
        } else if (player.petStorage13 == petId) {
            player.getPacketSender().sendMessage("@red@You've already stored this pet!");
            return;
        } else if (player.petStorage14 == petId) {
            player.getPacketSender().sendMessage("@red@You've already stored this pet!");
            return;
        } else if (player.petStorage15 == petId) {
            player.getPacketSender().sendMessage("@red@You've already stored this pet!");
            return;
        } else if (player.petStorage16 == petId) {
            player.getPacketSender().sendMessage("@red@You've already stored this pet!");
            return;
        } else if (player.petStorage17 == petId) {
            player.getPacketSender().sendMessage("@red@You've already stored this pet!");
            return;
        } else if (player.petStorage18 == petId) {
            player.getPacketSender().sendMessage("@red@You've already stored this pet!");
            return;
        } else if (player.petStorage19 == petId) {
            player.getPacketSender().sendMessage("@red@You've already stored this pet!");
            return;
        } else if (player.petStorage20 == petId) {
            player.getPacketSender().sendMessage("@red@You've already stored this pet!");
            return;
        } else if (player.petStorage21 == petId) {
            player.getPacketSender().sendMessage("@red@You've already stored this pet!");
            return;
        } else if (player.petStorage22 == petId) {
            player.getPacketSender().sendMessage("@red@You've already stored this pet!");
            return;
        } else if (player.petStorage23 == petId) {
            player.getPacketSender().sendMessage("@red@You've already stored this pet!");
            return;
        } else if (player.petStorage24 == petId) {
            player.getPacketSender().sendMessage("@red@You've already stored this pet!");
            return;
        } else if (player.petStorage25 == petId) {
            player.getPacketSender().sendMessage("@red@You've already stored this pet!");
            return;
        } else if (player.petStorage26 == petId) {
            player.getPacketSender().sendMessage("@red@You've already stored this pet!");
            return;
        } else if (player.petStorage27 == petId) {
            player.getPacketSender().sendMessage("@red@You've already stored this pet!");
            return;
        } else if (player.petStorage28 == petId) {
            player.getPacketSender().sendMessage("@red@You've already stored this pet!");
            return;
        } else if (player.petStorage29 == petId) {
            player.getPacketSender().sendMessage("@red@You've already stored this pet!");
            return;
        } else if (player.petStorage30 == petId) {
            player.getPacketSender().sendMessage("@red@You've already stored this pet!");
            return;
        } else if (player.petStorage31 == petId) {
            player.getPacketSender().sendMessage("@red@You've already stored this pet!");
            return;
        } else if (player.petStorage32 == petId) {
            player.getPacketSender().sendMessage("@red@You've already stored this pet!");
            return;
        } else if (player.petStorage33 == petId) {
            player.getPacketSender().sendMessage("@red@You've already stored this pet!");
            return;
        } else if (player.petStorage34 == petId) {
            player.getPacketSender().sendMessage("@red@You've already stored this pet!");
            return;
        } else if (player.petStorage35 == petId) {
            player.getPacketSender().sendMessage("@red@You've already stored this pet!");
            return;
        } else if (player.petStorage36 == petId) {
            player.getPacketSender().sendMessage("@red@You've already stored this pet!");
            return;
        } else if (player.petStorage37 == petId) {
            player.getPacketSender().sendMessage("@red@You've already stored this pet!");
            return;
        } else if (player.petStorage38 == petId) {
            player.getPacketSender().sendMessage("@red@You've already stored this pet!");
            return;
        } else if (player.petStorage39 == petId) {
            player.getPacketSender().sendMessage("@red@You've already stored this pet!");
            return;
        } else if (player.petStorage40 == petId) {
            player.getPacketSender().sendMessage("@red@You've already stored this pet!");
            return;
        }


        if (player.petCount == 0)
            player.petStorage1 = petId;
        else if (player.petCount == 1)
            player.petStorage2 = petId;
        else if (player.petCount == 2)
            player.petStorage3 = petId;
        else if (player.petCount == 3)
            player.petStorage4 = petId;
        else if (player.petCount == 4)
            player.petStorage5 = petId;
        else if (player.petCount == 5)
            player.petStorage6 = petId;
        else if (player.petCount == 6)
            player.petStorage7 = petId;
        else if (player.petCount == 7)
            player.petStorage8 = petId;
        else if (player.petCount == 8)
            player.petStorage9 = petId;
        else if (player.petCount == 9)
            player.petStorage10 = petId;
        if (player.petCount == 10)
            player.petStorage11 = petId;
        else if (player.petCount == 11)
            player.petStorage12 = petId;
        else if (player.petCount == 12)
            player.petStorage13 = petId;
        else if (player.petCount == 13)
            player.petStorage14 = petId;
        else if (player.petCount == 14)
            player.petStorage15 = petId;
        else if (player.petCount == 15)
            player.petStorage16 = petId;
        else if (player.petCount == 16)
            player.petStorage17 = petId;
        else if (player.petCount == 17)
            player.petStorage18 = petId;
        else if (player.petCount == 18)
            player.petStorage19 = petId;
        else if (player.petCount == 19)
            player.petStorage20 = petId;
        if (player.petCount == 20)
            player.petStorage21 = petId;
        else if (player.petCount == 21)
            player.petStorage22 = petId;
        else if (player.petCount == 22)
            player.petStorage23 = petId;
        else if (player.petCount == 23)
            player.petStorage24 = petId;
        else if (player.petCount == 24)
            player.petStorage25 = petId;
        else if (player.petCount == 25)
            player.petStorage26 = petId;
        else if (player.petCount == 26)
            player.petStorage27 = petId;
        else if (player.petCount == 27)
            player.petStorage28 = petId;
        else if (player.petCount == 28)
            player.petStorage29 = petId;
        else if (player.petCount == 29)
            player.petStorage30 = petId;
        if (player.petCount == 30)
            player.petStorage31 = petId;
        else if (player.petCount == 31)
            player.petStorage32 = petId;
        else if (player.petCount == 32)
            player.petStorage33 = petId;
        else if (player.petCount == 33)
            player.petStorage34 = petId;
        else if (player.petCount == 34)
            player.petStorage35 = petId;
        else if (player.petCount == 35)
            player.petStorage36 = petId;
        else if (player.petCount == 36)
            player.petStorage37 = petId;
        else if (player.petCount == 37)
            player.petStorage38 = petId;
        else if (player.petCount == 38)
            player.petStorage39 = petId;
        else if (player.petCount == 39)
            player.petStorage40 = petId;


        player.getInventory().delete(petId, 1);
        player.petCount++;

    }
}
