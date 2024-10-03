package com.arlania.world.content.interfaces;

import com.arlania.world.content.dialogue.DialogueManager;
import com.arlania.world.entity.impl.player.Player;

/**
 * This packet listener manages a button that the player has clicked upon.
 *
 * @author Gabriel Hannason
 */

public class LooterOptions {

    public static void showLooterInterface(Player player) {

        String color = "@red@";

        player.getPacketSender().sendInterface(8714); //interface

        player.getPacketSender().sendString(8812, "Close Window"); //Button header
        player.getPacketSender().sendString(8716, ""); //title header
        player.getPacketSender().sendString(8718, ""); //1st column header
        player.getPacketSender().sendString(8719, "Summary"); //2nd column header

        player.getPacketSender().sendString(8846, ((player.looterSettings[0] == 0) ? "@red@" : (player.looterSettings[0] == 1) ? "@yel@" : "@gre@") + "Leather"); //1st Button
        player.getPacketSender().sendString(8823, ((player.looterSettings[1] == 0) ? "@red@" : (player.looterSettings[1] == 1) ? "@yel@" : "@gre@") + "Bars"); //2nd Button
        player.getPacketSender().sendString(8824, ((player.looterSettings[2] == 0) ? "@red@" : (player.looterSettings[2] == 1) ? "@yel@" : "@gre@") + "Herbs"); //3rd Button
        player.getPacketSender().sendString(8827, ((player.looterSettings[3] == 0) ? "@red@" : (player.looterSettings[3] == 1) ? "@yel@" : "@gre@") + "Logs"); //4th Button
        player.getPacketSender().sendString(8837, ((player.looterSettings[4] == 0) ? "@red@" : (player.looterSettings[4] == 1) ? "@yel@" : "@gre@") + "Ores"); //5th Button
        player.getPacketSender().sendString(8840, ((player.looterSettings[5] == 0) ? "@red@" : (player.looterSettings[5] == 1) ? "@yel@" : "@gre@") + "Gems"); //6th Button
        player.getPacketSender().sendString(8843, ((player.looterSettings[6] == 0) ? "@red@" : (player.looterSettings[6] == 1) ? "@yel@" : "@gre@") + "Fish"); //7th Button
        player.getPacketSender().sendString(8859, ((player.looterSettings[7] == 0) ? "@red@" : (player.looterSettings[7] == 1) ? "@yel@" : "@gre@") + "Alchables"); //8th Button
        player.getPacketSender().sendString(8862, ((player.looterSettings[8] == 0) ? "@red@" : (player.looterSettings[8] == 1) ? "@yel@" : "@gre@") + "Ammo"); //9th Button
        player.getPacketSender().sendString(8865, ((player.looterSettings[9] == 0) ? "@red@" : (player.looterSettings[9] == 1) ? "@yel@" : "@gre@") + "Charms"); //10th Button
        player.getPacketSender().sendString(15303, ((player.looterSettings[10] == 0) ? "@red@" : (player.looterSettings[10] == 1) ? "@yel@" : "@gre@") + "Bones"); //11th Button
        player.getPacketSender().sendString(15306, ((player.looterSettings[11] == 0) ? "@red@" : (player.looterSettings[11] == 1) ? "@yel@" : "@gre@") + "Keys"); //12th Button
        player.getPacketSender().sendString(15309, "Menu"); //13th Button


        int string = 8760;
        int cost = 8720;


        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "Green = Picked up"); //
        player.getPacketSender().sendString(string++, "Yellow = Alched"); //
        player.getPacketSender().sendString(string++, "Red = Dropped"); //

        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, ""); //

        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, ""); //

        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "Supply type must be Red for"); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "skilling tools to work."); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "Bonecrusher, Charming Imp, etc."); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, ""); //


    }

    public static void handleButton(Player player, int button) {

        switch (button) {

            case 8846:
                if (player.looterSettings[0] < 2) {
                    player.looterSettings[0]++;
                } else {
                    player.looterSettings[0] = 0;
                }
                showLooterInterface(player);
                break;

            case 8823:
                if (player.looterSettings[1] < 2) {
                    player.looterSettings[1]++;
                } else {
                    player.looterSettings[1] = 0;
                }
                showLooterInterface(player);
                break;

            case 8824:
                if (player.looterSettings[2] < 2) {
                    player.looterSettings[2]++;
                } else {
                    player.looterSettings[2] = 0;
                }
                showLooterInterface(player);
                break;

            case 8827:
                if (player.looterSettings[3] < 2) {
                    player.looterSettings[3]++;
                } else {
                    player.looterSettings[3] = 0;
                }
                showLooterInterface(player);
                break;

            case 8837:
                if (player.looterSettings[4] < 2) {
                    player.looterSettings[4]++;
                } else {
                    player.looterSettings[4] = 0;
                }
                showLooterInterface(player);
                break;

            case 8840:
                if (player.looterSettings[5] < 2) {
                    player.looterSettings[5]++;
                } else {
                    player.looterSettings[5] = 0;
                }
                showLooterInterface(player);
                break;

            case 8843:
                if (player.looterSettings[6] < 2) {
                    player.looterSettings[6]++;
                } else {
                    player.looterSettings[6] = 0;
                }
                showLooterInterface(player);
                break;

            case 8859:
                if (player.looterSettings[7] < 2) {
                    player.looterSettings[7]++;
                } else {
                    player.looterSettings[7] = 0;
                }
                showLooterInterface(player);
                break;

            case 8862:
                if (player.looterSettings[8] < 2) {
                    player.looterSettings[8]++;
                } else {
                    player.looterSettings[8] = 0;
                }
                showLooterInterface(player);
                break;

            case 8865:
                if (player.looterSettings[9] < 2) {
                    player.looterSettings[9]++;
                } else {
                    player.looterSettings[9] = 0;
                }
                showLooterInterface(player);
                break;

            case 15303:
                if (player.looterSettings[10] < 2) {
                    player.looterSettings[10]++;
                } else {
                    player.looterSettings[10] = 0;
                }
                showLooterInterface(player);
                break;

            case 15306:
                if (player.looterSettings[11] < 2) {
                    player.looterSettings[11]++;
                } else {
                    player.looterSettings[11] = 0;
                }
                showLooterInterface(player);
                break;

            case 15309:
                player.activeInterface = "perkmenu";
                PerkMenu.showPerkInterface(player);
                break;

        }

    }

}
