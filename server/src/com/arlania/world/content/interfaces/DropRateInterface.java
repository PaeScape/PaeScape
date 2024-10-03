package com.arlania.world.content.interfaces;

import com.arlania.world.content.dialogue.DialogueManager;
import com.arlania.world.entity.impl.player.Player;

/**
 * This packet listener manages a button that the player has clicked upon.
 *
 * @author Gabriel Hannason
 */

public class DropRateInterface {

    public static void showPerkInterface(Player player) {

        player.getPacketSender().sendInterface(8714); //interface

        player.getPacketSender().sendString(8812, "Close Window"); //Button header
        player.getPacketSender().sendString(8716, "Drop Rate Increases"); //title header
        player.getPacketSender().sendString(8718, ""); //1st column header
        player.getPacketSender().sendString(8719, "Description"); //2nd column header

        player.getPacketSender().sendString(8846, "COX"); //1st Button
        player.getPacketSender().sendString(8823, "TOB"); //2nd Button
        player.getPacketSender().sendString(8824, "Chaos"); //3rd Button
        player.getPacketSender().sendString(8827, "GWD"); //4th Button
        player.getPacketSender().sendString(8837, "SHR"); //5th Button
        player.getPacketSender().sendString(8840, ""); //6th Button
        player.getPacketSender().sendString(8843, ""); //7th Button
        player.getPacketSender().sendString(8859, ""); //8th Button
        player.getPacketSender().sendString(8862, ""); //9th Button
        player.getPacketSender().sendString(8865, ""); //10th Button
        player.getPacketSender().sendString(15303, ""); //11th Button
        player.getPacketSender().sendString(15306, ""); //12th Button
        player.getPacketSender().sendString(15309, "Menu"); //13th Button

        if (player.coxRaidBonus > 0)
            player.getPacketSender().sendString(8846, "@gre@COX");
        else
            player.getPacketSender().sendString(8846, "@red@COX");

        if (player.tobRaidBonus > 0)
            player.getPacketSender().sendString(8823, "@gre@TOB");
        else
            player.getPacketSender().sendString(8823, "@red@TOB");

        if (player.chaosRaidBonus > 0)
            player.getPacketSender().sendString(8824, "@gre@Chaos");
        else
            player.getPacketSender().sendString(8824, "@red@Chaos");

        if (player.gwdRaidBonus > 0)
            player.getPacketSender().sendString(8827, "@gre@GWD");
        else
            player.getPacketSender().sendString(8827, "@red@GWD");

        if (player.gwdRaidBonus > 0)
            player.getPacketSender().sendString(8837, "@gre@SHR");
        else
            player.getPacketSender().sendString(8837, "@red@SHR");

        /*if (player.Dexterity == 1)
            player.getPacketSender().sendString(8837, "@gre@Dexterity");
        else
            player.getPacketSender().sendString(8837, "@red@Dexterity");

        if (player.Stability == 1)
            player.getPacketSender().sendString(8840, "@gre@Stability");
        else
            player.getPacketSender().sendString(8840, "@red@Stability");

        if (player.Absorb == 1)
            player.getPacketSender().sendString(8843, "@gre@Absorb");
        else
            player.getPacketSender().sendString(8843, "@red@Absorb");


        if (player.Efficiency == 1)
            player.getPacketSender().sendString(8859, "@gre@Efficiency");
        else
            player.getPacketSender().sendString(8859, "@red@Efficiency");


        if (player.Efficacy == 1)
            player.getPacketSender().sendString(8862, "@gre@Efficacy");
        else
            player.getPacketSender().sendString(8862, "@red@Efficacy");


        if (player.Devour == 1)
            player.getPacketSender().sendString(8865, "@gre@Devour");
        else
            player.getPacketSender().sendString(8865, "@red@Devour");


        if (player.Wealthy == 1)
            player.getPacketSender().sendString(15303, "@gre@Wealthy");
        else
            player.getPacketSender().sendString(15303, "@red@Wealthy");


        if (player.Reflect == 1)
            player.getPacketSender().sendString(15306, "@gre@Reflect");
        else
            player.getPacketSender().sendString(15306, "@red@Reflect");
        */

        int string = 8760;
        int cost = 8720;


        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "@red@Each upgrade costs 500m coins."); //

        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, ""); //

        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "@blu@Chambers of Xeric Bonus RNG: " + player.coxRaidBonus); //

        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "@blu@Theatre of Blood Bonus RNG: " + player.tobRaidBonus); //

        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "@blu@Chaos Raid Bonus RNG: " + player.chaosRaidBonus); //

        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "@blu@GWD Raid Bonus RNG: " + player.gwdRaidBonus); //

        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "@blu@SHR Bonus RNG: " + player.shrRaidBonus); //


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
                DialogueManager.start(player, 225);
                player.setDialogueActionId(225);
                break;

            case 8823:
                DialogueManager.start(player, 226);
                player.setDialogueActionId(226);
                break;

            case 8824:
                DialogueManager.start(player, 227);
                player.setDialogueActionId(227);
                break;

            case 8827:
                DialogueManager.start(player, 228);
                player.setDialogueActionId(228);
                break;

            case 8837:
                DialogueManager.start(player, 231);
                player.setDialogueActionId(231);
                break;

            /*case 8840:
                DialogueManager.start(player, 206);
                player.setDialogueActionId(116);
                break;

            case 8843:
                DialogueManager.start(player, 207);
                player.setDialogueActionId(117);
                break;

            case 8859:
                DialogueManager.start(player, 208);
                player.setDialogueActionId(118);
                break;

            case 8862:
                DialogueManager.start(player, 209);
                player.setDialogueActionId(119);
                break;

            case 8865:
                DialogueManager.start(player, 210);
                player.setDialogueActionId(120);

                break;

            case 15303:
                DialogueManager.start(player, 211);
                player.setDialogueActionId(121);

                break;

            case 15306:
                DialogueManager.start(player, 212);
                player.setDialogueActionId(122);

                break;*/

            case 15309:
                player.activeInterface = "perkmenu";
                PerkMenu.showPerkInterface(player);
                break;


        }
    }

}
