package com.arlania.model.input.impl;

import com.arlania.model.input.EnterAmount;
import com.arlania.world.content.skill.impl.smithing.Smelting;
import com.arlania.world.content.skill.impl.smithing.SmithingData;
import com.arlania.world.entity.impl.player.Player;

public class EnterAmountOfBarsToSmelt extends EnterAmount {

    public EnterAmountOfBarsToSmelt(int bar) {
        this.bar = bar;
    }

    @Override
    public void handleAmount(Player player, int amount) {
        for (int barId : SmithingData.SMELT_BARS) {
            if (barId == bar) {
                Smelting.smeltBar(player, barId, amount);
                break;
            }
        }
    }

    private final int bar;

    public int getBar() {
        return bar;
    }

}
