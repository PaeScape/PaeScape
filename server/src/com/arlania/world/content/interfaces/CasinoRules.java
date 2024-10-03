package com.arlania.world.content.interfaces;

import com.arlania.world.entity.impl.player.Player;

/**
 * This packet listener manages a button that the player has clicked upon.
 *
 * @author Gabriel Hannason
 */

public class CasinoRules {

    public static void showInterface(Player player) {

        player.getPacketSender().sendInterface(8119); //interface
        player.getPacketSender().sendString(8129, "Close");


        player.getPacketSender().sendString(8121, "@gre@Welcome to our Casino!");
        player.getPacketSender().sendString(8122, "@blu@You're able to play Roulette here!.");
        player.getPacketSender().sendString(8123, "@blu@Place bets using the interface in your quest tab!");
        player.getPacketSender().sendString(8124, "@blu@");
        player.getPacketSender().sendString(8125, "@blu@Betting @red@Red @blu@or @bla@Black @gre@pays @blu@2:1 on a winner!");
        player.getPacketSender().sendString(8126, "@blu@Betting a number @gre@pays @blu@36:1 on a winner!");
        player.getPacketSender().sendString(8127, "@blu@");
        player.getPacketSender().sendString(8128, "@blu@Good luck!");

    }

}
