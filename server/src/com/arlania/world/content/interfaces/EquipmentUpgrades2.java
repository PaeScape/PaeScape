package com.arlania.world.content.interfaces;

import com.arlania.world.entity.impl.player.Player;

/**
 * This packet listener manages a button that the player has clicked upon.
 *
 * @author Gabriel Hannason
 */

public class EquipmentUpgrades2 {

    public static void showInterface(Player player) {

        player.getPacketSender().sendInterface(8119); //interface
        player.getPacketSender().sendString(8129, "Close");


        player.getPacketSender().sendString(8121, "@blu@You must have prestiged at least");
        player.getPacketSender().sendString(8122, "@blu@the number of times you are upgrading");
        player.getPacketSender().sendString(8123, "@blu@each piece of equipment.");
        player.getPacketSender().sendString(8124, "@blu@");
        player.getPacketSender().sendString(8125, "@blu@Price per upgrade uses this formula:");
        player.getPacketSender().sendString(8126, "@blu@Equipment Level * 1000 PaePoints");
        player.getPacketSender().sendString(8127, "@blu@");
        player.getPacketSender().sendString(8128, "@blu@");

    }

}
