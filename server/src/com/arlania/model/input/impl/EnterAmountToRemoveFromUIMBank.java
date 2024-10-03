package com.arlania.model.input.impl;

import com.arlania.model.GameMode;
import com.arlania.model.Item;
import com.arlania.model.container.impl.Bank;
import com.arlania.model.input.EnterAmount;
import com.arlania.world.entity.impl.player.Player;

public class EnterAmountToRemoveFromUIMBank extends EnterAmount {


    public EnterAmountToRemoveFromUIMBank(int item, int slot) {
        super(item, slot);
    }

    @Override
    public void handleAmount(Player player, int amount) {
        if (!player.isBanking() || player.getGameMode() != GameMode.ULTIMATE_IRONMAN)
            return;
        int item = player.getUimBank().getItems()[getSlot()].getId();
        if (item != getItem())
            return;
        if (!player.getUimBank().contains(item))
            return;
        int invAmount = player.getUimBank().getAmount(item);
        if (amount > invAmount)
            amount = invAmount;
        if (amount <= 0)
            return;
        player.getUimBank().setPlayer(player).switchItem(player.getInventory(), new Item(item, amount), player.getUimBank().getSlot(item), false, true);
    }
}
