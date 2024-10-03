package com.arlania.model.input.impl;

import com.arlania.model.input.EnterAmount;
import com.arlania.world.content.skill.impl.farming.SeedsOnPatch;
import com.arlania.world.entity.impl.player.Player;

public class EnterAmountOfSeedsToPlant extends EnterAmount {

    @Override
    public void handleAmount(Player player, int amount) {
        SeedsOnPatch.offerSeeds(player, amount);
    }

}
