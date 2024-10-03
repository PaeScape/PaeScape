package com.arlania.model.input.impl;

import com.arlania.model.input.Input;
import com.arlania.world.entity.impl.player.Player;

public class EnterItemDropSearch extends Input {

    @Override
    public void handleSyntax(Player player, String syntax) {
        if (player.getInterfaceId() == 24000) {
            player.getDropTableInterface().searchItem(syntax.toLowerCase());
        }
    }
}
