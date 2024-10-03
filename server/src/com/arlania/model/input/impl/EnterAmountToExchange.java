package com.arlania.model.input.impl;

import com.arlania.model.input.EnterAmount;
import com.arlania.world.content.ExchangeTable;
import com.arlania.world.entity.impl.player.Player;

public class EnterAmountToExchange extends EnterAmount {

    @Override
    public void handleAmount(Player player, int amount) {
        if (player.getSelectedSkillingItem() > 0) {
            ExchangeTable.handleExchange(player, amount);
            player.getPacketSender().sendInterfaceRemoval();
        }
    }

}
