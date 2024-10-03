package com.arlania.model.input.impl;

import com.arlania.model.input.EnterAmount;
import com.arlania.world.content.MagicalPumpkin;
import com.arlania.world.entity.impl.player.Player;

public class EnterAmountToCredit extends EnterAmount {

    public EnterAmountToCredit(int item) {
        super(item);
    }

    @Override
    public void handleAmount(Player player, int amount) {
        player.getPacketSender().sendInterfaceRemoval();
        if (getItem() <= 0) {
            return;
        }
        MagicalPumpkin.handleCredit(player, getItem(), amount);

    }

}
