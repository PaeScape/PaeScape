package com.arlania.model.input.impl;

import com.arlania.model.input.Input;
import com.arlania.world.content.transportation.FairyRings;
import com.arlania.world.entity.impl.player.Player;

public class EnterFairyCode extends Input {

    @Override
    public void handleSyntax(Player player, String syntax) {
        FairyRings.forFairyCode(player, syntax.toLowerCase());
    }
}
