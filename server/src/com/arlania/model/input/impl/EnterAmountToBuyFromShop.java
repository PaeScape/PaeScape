package com.arlania.model.input.impl;

import com.arlania.model.Item;
import com.arlania.model.container.impl.Shop;
import com.arlania.model.input.EnterAmount;
import com.arlania.world.entity.impl.player.Player;

public class EnterAmountToBuyFromShop extends EnterAmount {

    public EnterAmountToBuyFromShop(int item, int slot) {
        super(item, slot);
    }

    @Override
    public void handleAmount(Player player, int amount) {
        if (player.isShopping() && getItem() > 0 && getSlot() >= 0) {
            Shop shop = player.getShop();
            if (shop != null) {
                if (getSlot() >= shop.getItems().length || shop.getItems()[getSlot()].getId() != getItem())
                    return;

                player.getShop().setPlayer(player).forSlot(getSlot()).copy().setAmount(amount).copy();
                shop.switchItem(player.getInventory(), new Item(getItem(), amount), getSlot(), false, true);
            } else
                player.getPacketSender().sendInterfaceRemoval();
        } else
            player.getPacketSender().sendInterfaceRemoval();

    }

}
