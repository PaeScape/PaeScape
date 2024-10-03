package com.arlania.world.content.interfaces;

import com.arlania.world.entity.impl.player.Player;

import java.text.NumberFormat;

/**
 * This packet listener manages a button that the player has clicked upon.
 *
 * @author Gabriel Hannason
 */

public class DifficultySettings {

    public static void showInterface(Player player) {


        NumberFormat myFormat = NumberFormat.getInstance();
        myFormat.setGroupingUsed(true);

        player.getPacketSender().sendInterface(8714); //interface

        player.getPacketSender().sendString(8812, "Close Window"); //Button header
        player.getPacketSender().sendString(8716, "Difficulty Settings"); //title header
        player.getPacketSender().sendString(8718, ""); //1st column header
        player.getPacketSender().sendString(8719, ""); //2nd column header

        String color = (player.difficulty == 0) ? "@gre@" : "@red@";
        player.getPacketSender().sendString(8846, color + "Easy"); //1st Button
        color = (player.difficulty == 1) ? "@gre@" : "@red@";
        player.getPacketSender().sendString(8823, color + "Medium"); //2nd Button
        color = (player.difficulty == 2) ? "@gre@" : "@red@";
        player.getPacketSender().sendString(8824, color + "Hard"); //3rd Button
        color = (player.difficulty == 3) ? "@gre@" : "@red@";
        player.getPacketSender().sendString(8827, color + "Expert"); //4th Button
        color = (player.difficulty == 4) ? "@gre@" : "@red@";
        player.getPacketSender().sendString(8837, color + "Misery I"); //5th Button
        color = (player.difficulty == 5) ? "@gre@" : "@red@";
        player.getPacketSender().sendString(8840, color + "Misery II"); //6th Button
        color = (player.difficulty == 6) ? "@gre@" : "@red@";
        player.getPacketSender().sendString(8843, color + "Misery III"); //7th Button
        color = (player.difficulty == 7) ? "@gre@" : "@red@";
        player.getPacketSender().sendString(8859, color + "Misery IV"); //8th Button
        color = (player.difficulty == 8) ? "@gre@" : "@red@";
        player.getPacketSender().sendString(8862, color + "Misery V"); //9th Button
        color = (player.difficulty == 9) ? "@gre@" : "@red@";
        player.getPacketSender().sendString(8865, color + "Misery VI"); //10th Button
        color = (player.difficulty == 10) ? "@gre@" : "@red@";
        player.getPacketSender().sendString(15303, color + "Misery VII"); //11th Button
        player.getPacketSender().sendString(15306, color); //12th Button
        player.getPacketSender().sendString(15309, color); //13th Button


        int string = 8760;
        int reward = 8720;

        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, ""); //

        player.getPacketSender().sendString(reward++, ""); //
        player.getPacketSender().sendString(reward++, ""); //
        player.getPacketSender().sendString(reward++, ""); //
        player.getPacketSender().sendString(reward++, ""); //
        player.getPacketSender().sendString(reward++, ""); //
        player.getPacketSender().sendString(reward++, ""); //
        player.getPacketSender().sendString(reward++, ""); //
        player.getPacketSender().sendString(reward++, ""); //
        player.getPacketSender().sendString(reward++, ""); //
        player.getPacketSender().sendString(reward++, ""); //
        player.getPacketSender().sendString(reward++, ""); //
        player.getPacketSender().sendString(reward++, ""); //
        player.getPacketSender().sendString(reward++, ""); //
        player.getPacketSender().sendString(reward++, ""); //
        player.getPacketSender().sendString(reward++, ""); //
        player.getPacketSender().sendString(reward++, ""); //
        player.getPacketSender().sendString(reward++, ""); //
        player.getPacketSender().sendString(reward++, ""); //
        player.getPacketSender().sendString(reward++, ""); //
        player.getPacketSender().sendString(reward++, ""); //
        player.getPacketSender().sendString(reward++, ""); //
        player.getPacketSender().sendString(reward++, ""); //
        player.getPacketSender().sendString(reward++, ""); //
        player.getPacketSender().sendString(reward++, ""); //
        player.getPacketSender().sendString(reward++, ""); //
        player.getPacketSender().sendString(reward++, ""); //
        player.getPacketSender().sendString(reward++, ""); //
        player.getPacketSender().sendString(reward++, ""); //
        player.getPacketSender().sendString(reward++, ""); //
        player.getPacketSender().sendString(reward++, ""); //
        player.getPacketSender().sendString(reward++, ""); //
        player.getPacketSender().sendString(reward++, ""); //


        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, ""); //


    }


    public static void handleButton(Player player, int button) {

        switch (button) {

            case 8846:
                player.difficulty = 0;
                player.getPacketSender().sendMessage("Your difficulty has been updated to Easy!");
                showInterface(player);
                break;

            case 8823:
                player.difficulty = 1;
                player.getPacketSender().sendMessage("Your difficulty has been updated to Medium!");
                showInterface(player);
                break;

            case 8824:
                player.difficulty = 2;
                player.getPacketSender().sendMessage("Your difficulty has been updated to Hard!");
                showInterface(player);
                break;

            case 8827:
                player.difficulty = 3;
                player.getPacketSender().sendMessage("Your difficulty has been updated to Expert!");
                showInterface(player);
                break;

            case 8837:
                player.difficulty = 4;
                player.getPacketSender().sendMessage("Your difficulty has been updated to Misery I!");
                showInterface(player);
                break;

            case 8840:
                player.difficulty = 5;
                player.getPacketSender().sendMessage("Your difficulty has been updated to Misery II!");
                showInterface(player);
                break;

            case 8843:
                player.difficulty = 6;
                player.getPacketSender().sendMessage("Your difficulty has been updated to Misery III!");
                showInterface(player);
                break;

            case 8859:
                player.difficulty = 7;
                player.getPacketSender().sendMessage("Your difficulty has been updated to Misery IV!");
                showInterface(player);
                break;

            case 8862:
                player.difficulty = 8;
                player.getPacketSender().sendMessage("Your difficulty has been updated to Misery V!");
                showInterface(player);
                break;

            case 8865:
                player.difficulty = 9;
                player.getPacketSender().sendMessage("Your difficulty has been updated to Misery VI!");
                showInterface(player);
                break;

            case 15303:
                player.difficulty = 10;
                player.getPacketSender().sendMessage("Your difficulty has been updated to Misery VII!");
                showInterface(player);
                break;

            case 15306:

                break;

            case 15309:

                break;


        }
    }


}

