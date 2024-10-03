package com.arlania.model.input.impl;

import com.arlania.model.input.EnterAmount;
import com.arlania.world.content.skill.impl.crafting.Flax;
import com.arlania.world.entity.impl.player.Player;

public class EnterAmountToSpin extends EnterAmount {

    @Override
    public void handleAmount(Player player, int amount) {
        Flax.spinFlax(player, amount);
    }

}
