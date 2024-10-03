package com.arlania.world.content.interfaces;

import com.arlania.world.entity.impl.player.Player;

/**
 * This packet listener manages a button that the player has clicked upon.
 *
 * @author Gabriel Hannason
 */

public class Scratchoff {

    public static void showInterface(Player player) {

        player.getPacketSender().sendInterface(8119); //interface
        player.getPacketSender().sendString(8129, "Close");


        player.getPacketSender().sendString(8121, "@red@Odds of Winning");
        player.getPacketSender().sendString(8122, "@blu@1:50 - 5m coins.");
        player.getPacketSender().sendString(8123, "@blu@1:100 - 10m coins");
        player.getPacketSender().sendString(8124, "@blu@1:250 - 25m coins");
        player.getPacketSender().sendString(8125, "@blu@1:500 - 50m coins");
        player.getPacketSender().sendString(8126, "@blu@1:1,000 - 100m coins");
        player.getPacketSender().sendString(8127, "@blu@1:2,500 - 250m coins");
        player.getPacketSender().sendString(8128, "@blu@1:10,000 - 1b coins");

    }

}
