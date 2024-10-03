package com.arlania.model.input.impl;

import com.arlania.model.input.EnterAmount;
import com.arlania.world.entity.impl.player.Player;

public class EnterAmountToRemoveFromTrade extends EnterAmount {

    public EnterAmountToRemoveFromTrade(int item) {
        super(item);
    }

    @Override
    public void handleAmount(Player player, int amount) {
        if (player.getTrading().inTrade() && getItem() > 0)
            player.getTrading().removeTradedItem(getItem(), amount);
        else
            player.getPacketSender().sendInterfaceRemoval();
    }


}
