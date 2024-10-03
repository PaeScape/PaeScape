package com.arlania.model.input.impl;

import com.arlania.model.input.Input;
import com.arlania.world.content.clan.ClanChatManager;
import com.arlania.world.entity.impl.player.Player;

public class EnterClanChatToJoin extends Input {

    @Override
    public void handleSyntax(Player player, String syntax) {
        if (syntax.length() <= 1) {
            player.getPacketSender().sendMessage("Invalid syntax entered.");
            return;
        }
        ClanChatManager.join(player, syntax);
    }
}
