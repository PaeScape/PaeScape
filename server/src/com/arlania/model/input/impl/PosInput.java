package com.arlania.model.input.impl;

import com.arlania.model.input.Input;
import com.arlania.world.entity.impl.player.Player;

public class PosInput extends Input {

    @Override
    public void handleSyntax(Player player, String syntax) {
        player.getPacketSender().sendClientRightClickRemoval();
        player.getTradingPostManager().updateFilter(syntax);

    }
}
