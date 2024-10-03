package com.arlania.world.content.interfaces;

import com.arlania.world.content.dialogue.DialogueManager;
import com.arlania.world.entity.impl.player.Player;

/**
 * This packet listener manages a button that the player has clicked upon.
 *
 * @author Gabriel Hannason
 */

public class PerkInterface {

    public static void showPerkInterface(Player player) {

        String berserkerLevel = "";
        String bullseyeLevel = "";
        String propheticLevel = "";
        String vampirismLevel = "";
        String survivalistLevel = "";
        String accelerateLevel = "";
        String luckyLevel = "";
        String prodigyLevel = "";
        String unnaturalLevel = "";
        String artisanLevel = "";

        player.activeInterface = "perks";
        player.getPacketSender().sendInterface(8714); //interface

        player.getPacketSender().sendString(8812, "Close Window"); //Button header
        player.getPacketSender().sendString(8716, "Perks"); //title header
        player.getPacketSender().sendString(8718, "Cost"); //1st column header
        player.getPacketSender().sendString(8719, "Description"); //2nd column header

        player.getPacketSender().sendString(8846, "Berserker"); //1st Button
        player.getPacketSender().sendString(8823, "Bullseye"); //2nd Button
        player.getPacketSender().sendString(8824, "Prophetic"); //3rd Button
        player.getPacketSender().sendString(8827, "Vampirism"); //4th Button
        player.getPacketSender().sendString(8837, "Survivalist"); //5th Button
        player.getPacketSender().sendString(8840, "Accelerate"); //6th Button
        player.getPacketSender().sendString(8843, "Lucky"); //7th Button
        player.getPacketSender().sendString(8859, "Prodigy"); //8th Button
        player.getPacketSender().sendString(8862, "Unnatural"); //9th Button
        player.getPacketSender().sendString(8865, "Artisan"); //10th Button
        player.getPacketSender().sendString(15303, ""); //11th Button
        player.getPacketSender().sendString(15306, ""); //12th Button
        player.getPacketSender().sendString(15309, "Menu"); //13th Button


        int string = 8760;
        int cost = 8720;


        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "Perk Level 1 costs 500 PaePoints."); //
        player.getPacketSender().sendString(string++, "Perk Level 2 costs 2500 PaePoints."); //
        player.getPacketSender().sendString(string++, "Perk Level 3 costs 5000 PaePoints."); //
        player.getPacketSender().sendString(string++, "Perk Level 4 costs 10000 PaePoints."); //

        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, ""); //

        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "Level 2, 3, and 4 also require a Perk Upgrade."); //

        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, ""); //


        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "Beserker: " + player.Berserker); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "Bullseye: " + player.Bullseye); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "Prophetic: " + player.Prophetic); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "Vampirism: " + player.Vampirism); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "Survivalist: " + player.Survivalist); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "Accelerate: " + player.Accelerate); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "Lucky: " + player.Lucky); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "Prodigy: " + player.Prodigy); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "Unnatural: " + player.Unnatural); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "Artisan: " + player.Artisan); //

        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, ""); //
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
                DialogueManager.start(player, 166);
                player.setDialogueActionId(79);
                break;

            case 8823:
                DialogueManager.start(player, 167);
                player.setDialogueActionId(80);
                break;

            case 8824:
                DialogueManager.start(player, 168);
                player.setDialogueActionId(81);
                break;

            case 8827:
                DialogueManager.start(player, 169);
                player.setDialogueActionId(82);
                break;

            case 8837:
                DialogueManager.start(player, 170);
                player.setDialogueActionId(83);
                break;

            case 8840:
                DialogueManager.start(player, 171);
                player.setDialogueActionId(84);
                break;

            case 8843:
                DialogueManager.start(player, 172);
                player.setDialogueActionId(85);
                break;

            case 8859:
                DialogueManager.start(player, 173);
                player.setDialogueActionId(86);
                break;

            case 8862:
                DialogueManager.start(player, 174);
                player.setDialogueActionId(87);
                break;

            case 8865:
                DialogueManager.start(player, 175);
                player.setDialogueActionId(88);
                break;

            case 15303:
                //DialogueManager.start(player, 176);
                //player.setDialogueActionId(89);
                break;

            case 15306:
                //DialogueManager.start(player, 177);
                //player.setDialogueActionId(90);
                break;

            case 15309:
                player.activeInterface = "perkmenu";
                PerkMenu.showPerkInterface(player);
                break;

        }

    }

}
