package com.arlania.model.input.impl;

import com.arlania.model.input.EnterAmount;
import com.arlania.world.content.WellOfWealth;
import com.arlania.world.entity.impl.player.Player;

public class DonateWealth extends EnterAmount {

    @Override
    public void handleAmount(Player player, int amount) {
        WellOfWealth.donate(player, amount);
    }

}
