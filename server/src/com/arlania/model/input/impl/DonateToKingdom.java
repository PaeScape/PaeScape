package com.arlania.model.input.impl;

import com.arlania.model.input.EnterAmount;
import com.arlania.world.content.minigames.impl.kingdom.NobilitySystem;
import com.arlania.world.entity.impl.player.Player;

public class DonateToKingdom extends EnterAmount {

    @Override
    public void handleAmount(Player player, int amount) {
        NobilitySystem.contribute(player, amount * 1_000_000L);
    }

}
