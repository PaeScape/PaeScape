package com.arlania.model.input.impl;

import com.arlania.model.input.EnterAmount;
import com.arlania.world.content.grandexchange.GrandExchange;
import com.arlania.world.entity.impl.player.Player;

public class EnterGePricePerItem extends EnterAmount {

    @Override
    public void handleAmount(Player player, int amount) {
        GrandExchange.setPricePerItem(player, amount);
    }

}
