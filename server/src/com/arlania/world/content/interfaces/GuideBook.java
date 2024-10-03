package com.arlania.world.content.interfaces;

import com.arlania.world.entity.impl.player.Player;

/**
 * This packet listener manages a button that the player has clicked upon.
 *
 * @author Gabriel Hannason
 */

public class GuideBook {

    public static void showInterface(Player player) {

        player.getPacketSender().sendInterface(8119); //interface
        player.getPacketSender().sendString(8129, "Close");


        player.getPacketSender().sendString(8121, "@red@Welcome to PaeScape!");
        player.getPacketSender().sendString(8122, "@blu@Everything in the server revolves around PaePoints.");
        player.getPacketSender().sendString(8123, "@blu@You can earn PaePoints in various ways:");
        player.getPacketSender().sendString(8124, "@blu@PvM, PvP, Slayer, Achievements, Trivia, Donating, Voting.");
        player.getPacketSender().sendString(8125, "@blu@");
        player.getPacketSender().sendString(8126, "@blu@Please check out the quest tab.");
        player.getPacketSender().sendString(8127, "@blu@All options under \"Tools\" are clickable buttons.");
        player.getPacketSender().sendString(8128, "@blu@");

    }

}
