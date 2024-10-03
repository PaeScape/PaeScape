package com.arlania.world.content.interfaces;

import com.arlania.world.entity.impl.player.Player;

/**
 * This packet listener manages a button that the player has clicked upon.
 *
 * @author Gabriel Hannason
 */

public class HeroesStatue {

    public static void showInterface(Player player) {

        player.getPacketSender().sendInterface(8119); //interface
        player.getPacketSender().sendString(8129, "Close");


        player.getPacketSender().sendString(8121, "@red@Statue of Heroes");
        player.getPacketSender().sendString(8122, "@blu@");
        player.getPacketSender().sendString(8123, "This statue is here in honor of the legends");
        player.getPacketSender().sendString(8124, "that once roamed PaeScape!");
        player.getPacketSender().sendString(8125, "@blu@");
        player.getPacketSender().sendString(8126, "@blu@");
        player.getPacketSender().sendString(8127, "@blu@");
        player.getPacketSender().sendString(8128, "@gre@");

    }

}
