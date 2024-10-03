package com.arlania.world.content.interfaces;

import com.arlania.world.entity.impl.player.Player;

/**
 * This packet listener manages a button that the player has clicked upon.
 *
 * @author Gabriel Hannason
 */

public class CrystalArmorMakerInfo {

    public static void showInterface(Player player) {

        player.getPacketSender().sendInterface(8119); //interface
        player.getPacketSender().sendString(8129, "Close");


        player.getPacketSender().sendString(8121, "@gre@Welcome to the Crystal Armor Maker!");
        player.getPacketSender().sendString(8122, "@blu@Start by using a Crystal Armor Seed on Crystal Shards");
        player.getPacketSender().sendString(8123, "@blu@");
        player.getPacketSender().sendString(8124, "@blu@Crystal Helm = 1 Crystal Armor Seed + 500 Crystal Shards");
        player.getPacketSender().sendString(8125, "@blu@Crystal Body = 3 Crystal Armor Seeds + 1500 Crystal Shards");
        player.getPacketSender().sendString(8126, "@blu@Crystal Legs = 2 Crystal Armor Seeds + 1000 Crystal Shards");
        player.getPacketSender().sendString(8127, "@blu@");
        player.getPacketSender().sendString(8128, "@blu@Good luck! Have fun!");

    }

}
