package com.arlania.model.input.impl;

import com.arlania.model.GameMode;
import com.arlania.model.Item;
import com.arlania.model.container.impl.Bank;
import com.arlania.model.input.EnterAmount;
import com.arlania.world.entity.impl.player.Player;

public class EnterAmountToBank extends EnterAmount {

    public EnterAmountToBank(int item, int slot) {
        super(item, slot);
    }

    @Override
    public void handleAmount(Player player, int amount) {
        if (!player.isBanking())
            return;
        int item = player.getInventory().getItems()[getSlot()].getId();
        if (item != getItem())
            return;
        int invAmount = player.getInventory().getAmount(item);
        if (amount > invAmount)
            amount = invAmount;
        if (amount <= 0)
            return;

        if (player.getGameMode() == GameMode.ULTIMATE_IRONMAN) {
            player.getInventory().switchItem(player.getUimBank(), new Item(item, amount), getSlot(), false, true);
        } else {
            player.getInventory().switchItem(player.getBank(Bank.getTabForItem(player, item)), new Item(item, amount), getSlot(), false, true);
        }
    }
}
