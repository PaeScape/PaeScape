package com.arlania.model.input.impl;

import com.arlania.model.input.EnterAmount;
import com.arlania.world.content.skill.impl.summoning.PouchMaking;
import com.arlania.world.entity.impl.player.Player;

public class EnterAmountToInfuse extends EnterAmount {

    @Override
    public void handleAmount(Player player, int amount) {
        if (player.getInterfaceId() != 63471) {
            player.getPacketSender().sendInterfaceRemoval();
            return;
        }
        PouchMaking.infusePouches(player, amount);
    }

}
