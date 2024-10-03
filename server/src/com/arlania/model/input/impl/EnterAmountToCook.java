package com.arlania.model.input.impl;

import com.arlania.model.input.EnterAmount;
import com.arlania.world.content.skill.impl.cooking.Cooking;
import com.arlania.world.entity.impl.player.Player;

public class EnterAmountToCook extends EnterAmount {

    @Override
    public void handleAmount(Player player, int amount) {
        if (player.getSelectedSkillingItem() > 0)
            Cooking.cook(player, player.getSelectedSkillingItem(), amount);
    }

}
