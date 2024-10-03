package com.arlania.model.input.impl;

import com.arlania.model.input.EnterAmount;
import com.arlania.world.content.TransferDonator;
import com.arlania.world.entity.impl.player.Player;

public class EnterAmountOfTokensToTransfer extends EnterAmount {

    @Override
    public void handleAmount(Player player, int amount) {
        if (amount > 0)
            TransferDonator.transferTokens(player, amount);
    }

}
