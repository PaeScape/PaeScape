package com.arlania.world.content.interfaces;

import com.arlania.world.entity.impl.player.Player;

/**
 * This packet listener manages a button that the player has clicked upon.
 *
 * @author Gabriel Hannason
 */

public class EquipmentUpgrades1 {

    public static void showInterface(Player player) {

        player.getPacketSender().sendInterface(8119); //interface
        player.getPacketSender().sendString(8129, "Close");


        player.getPacketSender().sendString(8121, "@red@Welcome to the Eternal Flame!");
        player.getPacketSender().sendString(8122, "@blu@You can use any item you see");
        player.getPacketSender().sendString(8123, "@blu@listed in #unique-tables on Discord");
        player.getPacketSender().sendString(8124, "@blu@on the Eternal Flame to start upgrading!");
        player.getPacketSender().sendString(8125, "@blu@");
        player.getPacketSender().sendString(8126, "@blu@Open your Collection Log and click");
        player.getPacketSender().sendString(8127, "@blu@the upgrades tab to view your progress!");
        player.getPacketSender().sendString(8128, "@blu@");

    }

}
