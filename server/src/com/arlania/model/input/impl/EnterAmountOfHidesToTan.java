package com.arlania.model.input.impl;

import com.arlania.model.input.EnterAmount;
import com.arlania.world.content.skill.impl.crafting.Tanning;
import com.arlania.world.entity.impl.player.Player;

public class EnterAmountOfHidesToTan extends EnterAmount {

    private final int button;

    public EnterAmountOfHidesToTan(int button) {
        this.button = button;
    }

    @Override
    public void handleAmount(Player player, int amount) {
        Tanning.tanHide(player, button, amount);
    }

}
