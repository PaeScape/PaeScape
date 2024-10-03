package com.arlania.model.input.impl;

import com.arlania.model.input.EnterAmount;
import com.arlania.world.content.skill.impl.fletching.Fletching;
import com.arlania.world.entity.impl.player.Player;

public class EnterAmountOfBowsToString extends EnterAmount {

    @Override
    public void handleAmount(Player player, int amount) {
        Fletching.stringBow(player, amount);
    }

}
