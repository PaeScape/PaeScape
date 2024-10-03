package com.arlania.world.content.interfaces;

import com.arlania.world.content.dialogue.DialogueManager;
import com.arlania.world.entity.impl.player.Player;

/**
 * This packet listener manages a button that the player has clicked upon.
 *
 * @author Gabriel Hannason
 */

public class MasteryPerkInterface {

    public static void showPerkInterface(Player player) {

        player.getPacketSender().sendInterface(8714); //interface

        player.getPacketSender().sendString(8812, "Close Window"); //Button header
        player.getPacketSender().sendString(8716, "Mastery Perks"); //title header
        player.getPacketSender().sendString(8718, ""); //1st column header
        player.getPacketSender().sendString(8719, "Description"); //2nd column header

        player.getPacketSender().sendString(8846, "Detective"); //1st Button
        player.getPacketSender().sendString(8823, "Critical"); //2nd Button
        player.getPacketSender().sendString(8824, "Flurry"); //3rd Button
        player.getPacketSender().sendString(8827, "Consistent"); //4th Button
        player.getPacketSender().sendString(8837, "Dexterity"); //5th Button
        player.getPacketSender().sendString(8840, "Stability"); //6th Button
        player.getPacketSender().sendString(8843, "Absorb"); //7th Button
        player.getPacketSender().sendString(8859, "Efficiency"); //8th Button
        player.getPacketSender().sendString(8862, "Efficacy"); //9th Button
        player.getPacketSender().sendString(8865, "Devour"); //10th Button
        player.getPacketSender().sendString(15303, "Wealthy"); //11th Button
        player.getPacketSender().sendString(15306, "Reflect"); //12th Button
        player.getPacketSender().sendString(15309, "Menu"); //13th Button

        if (player.Detective == 1)
            player.getPacketSender().sendString(8846, "@gre@Detective");
        else
            player.getPacketSender().sendString(8846, "@red@Detective");

        if (player.Critical == 1)
            player.getPacketSender().sendString(8823, "@gre@Critical");
        else
            player.getPacketSender().sendString(8823, "@red@Critical");

        if (player.Flurry == 1)
            player.getPacketSender().sendString(8824, "@gre@Flurry");
        else
            player.getPacketSender().sendString(8824, "@red@Flurry");

        if (player.Consistent == 1)
            player.getPacketSender().sendString(8827, "@gre@Consistent");
        else
            player.getPacketSender().sendString(8827, "@red@Consistent");

        if (player.Dexterity == 1)
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


        int string = 8760;
        int cost = 8720;


        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "@blu@Detective"); //
        player.getPacketSender().sendString(string++, "@bla@-% Increased Clue drop rate from all sources"); //
        player.getPacketSender().sendString(string++, "@bla@-Boost % = Completed C-Logs / 4"); //


        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "@blu@Critical"); //
        player.getPacketSender().sendString(string++, "@bla@-% Increased Max Hit from Special Attacks"); //
        player.getPacketSender().sendString(string++, "@bla@-Boost % = Completed C-Logs / 2"); //

        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "@blu@Flurry"); //
        player.getPacketSender().sendString(string++, "@bla@-% Chance for an additional attack"); //
        player.getPacketSender().sendString(string++, "@bla@-Boost % = Completed C-Logs / 3"); //


        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "@blu@Consistent"); //
        player.getPacketSender().sendString(string++, "@bla@-% Increased Minimum Hit"); //
        player.getPacketSender().sendString(string++, "@bla@-Boost % = Completed C-Logs / 2"); //


        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "@blu@Dexterity"); //
        player.getPacketSender().sendString(string++, "@bla@-% Increased Accuracy"); //
        player.getPacketSender().sendString(string++, "@bla@-Boost % = Completed C-Logs / 2"); //


        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "@blu@Stability"); //
        player.getPacketSender().sendString(string++, "@bla@-% Increased Defence"); //
        player.getPacketSender().sendString(string++, "@bla@-Boost % = Completed C-Logs / 2"); //


        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "@blu@Absorb"); //
        player.getPacketSender().sendString(string++, "@bla@-% Decrease Max Hit against you"); //
        player.getPacketSender().sendString(string++, "@bla@-Boost % = Completed C-Logs / 3"); //


        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "@blu@Efficiency"); //
        player.getPacketSender().sendString(string++, "@bla@-% chance Food and potions are not consumed"); //
        player.getPacketSender().sendString(string++, "@bla@-Boost % = Completed C-Logs / 2"); //


        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "@blu@Efficacy"); //
        player.getPacketSender().sendString(string++, "@bla@-Potions have a % increased effect"); //
        player.getPacketSender().sendString(string++, "@bla@-Boost % = Completed C-Logs / 2"); //


        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "@blu@Devour"); //
        player.getPacketSender().sendString(string++, "@bla@-Food Heals % more. Faster animation "); //
        player.getPacketSender().sendString(string++, "@bla@-Boost % = Completed C-Logs / 2"); //


        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "@blu@Wealthy"); //
        player.getPacketSender().sendString(string++, "@bla@-% chance that coins are added to your pouch"); //
        player.getPacketSender().sendString(string++, "@bla@-equivalent to the NPC level"); //
        player.getPacketSender().sendString(string++, "@bla@-Boost % = Completed C-Logs / 2"); //


        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "@blu@Reflect"); //
        player.getPacketSender().sendString(string++, "@bla@-% chance that you deal damage to NPC"); //
        player.getPacketSender().sendString(string++, "@bla@-equivalent to the damage the NPC tries to hit on you"); //
        player.getPacketSender().sendString(string++, "@bla@-Boost % = Completed C-Logs / 4"); //


        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, ""); //


    }

    public static void handleButton(Player player, int button) {

        switch (button) {

            case 8846:
                DialogueManager.start(player, 201);
                player.setDialogueActionId(111);
                break;

            case 8823:
                DialogueManager.start(player, 202);
                player.setDialogueActionId(112);
                break;

            case 8824:
                DialogueManager.start(player, 203);
                player.setDialogueActionId(113);
                break;

            case 8827:
                DialogueManager.start(player, 204);
                player.setDialogueActionId(114);
                break;

            case 8837:
                DialogueManager.start(player, 205);
                player.setDialogueActionId(115);
                break;

            case 8840:
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

                break;

            case 15309:
                player.activeInterface = "perkmenu";
                PerkMenu.showPerkInterface(player);
                break;


        }
    }

}
