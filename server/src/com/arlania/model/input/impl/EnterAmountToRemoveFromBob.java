package com.arlania.model.input.impl;

import com.arlania.model.Item;
import com.arlania.model.container.impl.BeastOfBurden;
import com.arlania.model.input.EnterAmount;
import com.arlania.world.entity.impl.player.Player;

public class EnterAmountToRemoveFromBob extends EnterAmount {

    public EnterAmountToRemoveFromBob(int item, int slot) {
        super(item, slot);
    }

    @Override
    public void handleAmount(Player player, int amount) {
        if (player.getSummoning().getBeastOfBurden() == null || player.getInterfaceId() != BeastOfBurden.INTERFACE_ID)
            return;
        if (!player.getSummoning().getBeastOfBurden().contains(getItem()))
            return;
        int invAmount = player.getSummoning().getBeastOfBurden().getAmount(getItem());
        if (amount > invAmount)
            amount = invAmount;
        if (amount <= 0)
            return;
        player.getSummoning().getBeastOfBurden().switchItem(player.getInventory(), new Item(getItem(), amount), player.getSummoning().getBeastOfBurden().getSlot(getItem()), false, true);
    }
}
