package com.arlania.model.input.impl;

import com.arlania.model.input.EnterAmount;
import com.arlania.world.content.skill.impl.crafting.JewelryMaking;
import com.arlania.world.entity.impl.player.Player;

public class EnterAmountToCraft extends EnterAmount {

    @Override
    public void handleAmount(Player player, int amount) {
        if (player.getSelectedSkillingItem() > 0)
            JewelryMaking.craftJewelry(player, amount, player.getSelectedSkillingItem());
    }

}
