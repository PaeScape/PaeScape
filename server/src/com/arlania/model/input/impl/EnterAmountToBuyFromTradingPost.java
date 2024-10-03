package com.arlania.model.input.impl;

import com.arlania.model.Item;
import com.arlania.model.container.impl.Shop;
import com.arlania.model.input.EnterAmount;
import com.arlania.world.content.pos.TradingPost;
import com.arlania.world.entity.impl.player.Player;

public class EnterAmountToBuyFromTradingPost extends EnterAmount {

    public EnterAmountToBuyFromTradingPost(int item, int slot) {
        super(item, slot);
    }

    @Override
    public void handleAmount(Player player, int amount) {
        if (player.isTradingPostOpen() && getItem() > 0 && getSlot() >= 0) {
            TradingPost shop = player.getTradingPostManager().getCurrentlyViewing();
            if (shop != null) {
                if (getSlot() >= shop.getItems().length || shop.getItems()[getSlot()].getId() != getItem())
                    return;
                player.getTradingPostManager().handleBuy(getSlot(), getItem(), amount);
            } else
                player.getPacketSender().sendInterfaceRemoval();
        } else
            player.getPacketSender().sendInterfaceRemoval();

    }

}
