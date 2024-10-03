package com.arlania.model.input.impl;

import com.arlania.model.input.EnterAmount;
import com.arlania.world.content.skill.impl.prayer.BonesOnAltar;
import com.arlania.world.entity.impl.player.Player;

public class EnterAmountOfBonesToSacrifice extends EnterAmount {

    @Override
    public void handleAmount(Player player, int amount) {
        BonesOnAltar.offerBones(player, -1 ,amount);
        //should never be called
    }

}
