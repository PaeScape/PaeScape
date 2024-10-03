package com.arlania.world.content.interfaces;

import com.arlania.world.content.dialogue.DialogueManager;
import com.arlania.world.entity.impl.player.Player;

/**
 * This packet listener manages a button that the player has clicked upon.
 *
 * @author Gabriel Hannason
 */

public class WildernessPerkInterface {

    public static void showPerkInterface(Player player) {

        player.getPacketSender().sendInterface(8714); //interface

        player.getPacketSender().sendString(8812, "Close Window"); //Button header
        player.getPacketSender().sendString(8716, "Perks"); //title header
        player.getPacketSender().sendString(8718, "Cost"); //1st column header
        player.getPacketSender().sendString(8719, "Description"); //2nd column header

        player.getPacketSender().sendString(8846, "Accelerate"); //1st Button
        player.getPacketSender().sendString(8823, "Looter"); //2nd Button
        player.getPacketSender().sendString(8824, "Specialist"); //3rd Button
        player.getPacketSender().sendString(8827, "Enraged"); //4th Button
        player.getPacketSender().sendString(8837, "Vesta"); //5th Button
        player.getPacketSender().sendString(8840, "Statius"); //6th Button
        player.getPacketSender().sendString(8843, "Morrigan"); //7th Button
        player.getPacketSender().sendString(8859, "Zuriel"); //8th Button
        player.getPacketSender().sendString(8862, "Savior"); //9th Button
        player.getPacketSender().sendString(8865, "Alchemize"); //10th Button
        player.getPacketSender().sendString(15303, "Tainted"); //11th Button
        player.getPacketSender().sendString(15306, "Botanist"); //12th Button
        player.getPacketSender().sendString(15309, "Menu"); //13th Button

        if (player.wildAccelerate == 3)
            player.getPacketSender().sendString(8846, "@gre@Accelerate");
        else if (player.wildAccelerate == 2)
            player.getPacketSender().sendString(8846, "@yel@Accelerate");
        else if (player.wildAccelerate == 1)
            player.getPacketSender().sendString(8846, "@or1@Accelerate");
        else
            player.getPacketSender().sendString(8846, "@red@Accelerate");

        if (player.wildLooter == 3)
            player.getPacketSender().sendString(8823, "@gre@Looter");
        else if (player.wildLooter == 2)
            player.getPacketSender().sendString(8823, "@yel@Looter");
        else if (player.wildLooter == 1)
            player.getPacketSender().sendString(8823, "@or1@Looter");
        else
            player.getPacketSender().sendString(8823, "@red@Looter");

        if (player.wildSpecialist == 3)
            player.getPacketSender().sendString(8824, "@gre@Specialist");
        else if (player.wildSpecialist == 2)
            player.getPacketSender().sendString(8824, "@yel@Specialist");
        else if (player.wildSpecialist == 1)
            player.getPacketSender().sendString(8824, "@or1@Specialist");
        else
            player.getPacketSender().sendString(8824, "@red@Specialist");

        if (player.wildEnraged == 3)
            player.getPacketSender().sendString(8827, "@gre@Enraged");
        else if (player.wildEnraged == 2)
            player.getPacketSender().sendString(8827, "@yel@Enraged");
        else if (player.wildEnraged == 1)
            player.getPacketSender().sendString(8827, "@or1@Enraged");
        else
            player.getPacketSender().sendString(8827, "@red@Enraged");

        if (player.wildVesta == 3)
            player.getPacketSender().sendString(8837, "@gre@Vesta");
        else if (player.wildVesta == 2)
            player.getPacketSender().sendString(8837, "@yel@Vesta");
        else if (player.wildVesta == 1)
            player.getPacketSender().sendString(8837, "@or1@Vesta");
        else
            player.getPacketSender().sendString(8837, "@red@Vesta");

        if (player.wildStatius == 3)
            player.getPacketSender().sendString(8840, "@gre@Statius");
        else if (player.wildStatius == 2)
            player.getPacketSender().sendString(8840, "@yel@Statius");
        else if (player.wildStatius == 1)
            player.getPacketSender().sendString(8840, "@or1@Statius");
        else
            player.getPacketSender().sendString(8840, "@red@Statius");

        if (player.wildMorrigan == 3)
            player.getPacketSender().sendString(8843, "@gre@Morrigan");
        else if (player.wildMorrigan == 2)
            player.getPacketSender().sendString(8843, "@yel@Morrigan");
        else if (player.wildMorrigan == 1)
            player.getPacketSender().sendString(8843, "@or1@Morrigan");
        else
            player.getPacketSender().sendString(8843, "@red@Morrigan");

        if (player.wildZuriel == 3)
            player.getPacketSender().sendString(8859, "@gre@Zuriel");
        else if (player.wildZuriel == 2)
            player.getPacketSender().sendString(8859, "@yel@Zuriel");
        else if (player.wildZuriel == 1)
            player.getPacketSender().sendString(8859, "@or1@Zuriel");
        else
            player.getPacketSender().sendString(8859, "@red@Zuriel");

        if (player.wildSavior == 3)
            player.getPacketSender().sendString(8862, "@gre@Savior");
        else if (player.wildSavior == 2)
            player.getPacketSender().sendString(8862, "@yel@Savior");
        else if (player.wildSavior == 1)
            player.getPacketSender().sendString(8862, "@or1@Savior");
        else
            player.getPacketSender().sendString(8862, "@red@Savior");

        if (player.wildAlchemize == 3)
            player.getPacketSender().sendString(8865, "@gre@Alchemize");
        else if (player.wildAlchemize == 2)
            player.getPacketSender().sendString(8865, "@yel@Alchemize");
        else if (player.wildAlchemize == 1)
            player.getPacketSender().sendString(8865, "@or1@Alchemize");
        else
            player.getPacketSender().sendString(8865, "@red@Alchemize");

        if (player.wildTainted == 1)
            player.getPacketSender().sendString(15303, "@gre@Tainted");
        else
            player.getPacketSender().sendString(15303, "@red@Tainted");

        if (player.wildBotanist == 1)
            player.getPacketSender().sendString(15306, "@gre@Botanist");
        else
            player.getPacketSender().sendString(15306, "@red@Botanist");



        int string = 8760;
        int cost = 8720;


        player.getPacketSender().sendString(cost++, "@or1@1000"); //
        player.getPacketSender().sendString(cost++, "@yel@2500"); //
        player.getPacketSender().sendString(cost++, "@gre@5000"); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "Accelerate (2/4/6)"); //
        player.getPacketSender().sendString(string++, "X Additional resources when skilling"); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, ""); //

        player.getPacketSender().sendString(cost++, "@or1@1000"); //
        player.getPacketSender().sendString(cost++, "@yel@2500"); //
        player.getPacketSender().sendString(cost++, "@gre@5000"); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "Looter (5/10/15)"); //
        player.getPacketSender().sendString(string++, "X Percentage increase for Uniques"); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, ""); //

        player.getPacketSender().sendString(cost++, "@or1@1000"); //
        player.getPacketSender().sendString(cost++, "@yel@2500"); //
        player.getPacketSender().sendString(cost++, "@gre@5000"); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "Specialist (5/10/15)"); //
        player.getPacketSender().sendString(string++, "X Percentage decrease in special attack drain"); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, ""); //

        player.getPacketSender().sendString(cost++, "@gre@5000"); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "Enraged"); //
        player.getPacketSender().sendString(string++, "Kill 100 bosses in the wild to increase speed"); //
        player.getPacketSender().sendString(string++, ""); //

        player.getPacketSender().sendString(cost++, "@or1@1000"); //
        player.getPacketSender().sendString(cost++, "@yel@2500"); //
        player.getPacketSender().sendString(cost++, "@gre@5000"); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "Vesta"); //
        player.getPacketSender().sendString(string++, "Vesta items increase your max hit"); //
        player.getPacketSender().sendString(string++, "by 10 percent per level."); //
        player.getPacketSender().sendString(string++, ""); //

        player.getPacketSender().sendString(cost++, "@or1@1000"); //
        player.getPacketSender().sendString(cost++, "@yel@2500"); //
        player.getPacketSender().sendString(cost++, "@gre@5000"); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "Statius"); //
        player.getPacketSender().sendString(string++, "Statius items increase your max hit"); //
        player.getPacketSender().sendString(string++, "by 10 percent per level."); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, ""); //

        player.getPacketSender().sendString(cost++, "@or1@1000"); //
        player.getPacketSender().sendString(cost++, "@yel@2500"); //
        player.getPacketSender().sendString(cost++, "@gre@5000"); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "Morrigan items increase your range max"); //
        player.getPacketSender().sendString(string++, "by 10 percent per level."); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, ""); //

        player.getPacketSender().sendString(cost++, "@or1@1000"); //
        player.getPacketSender().sendString(cost++, "@yel@2500"); //
        player.getPacketSender().sendString(cost++, "@gre@5000"); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "Zuriel"); //
        player.getPacketSender().sendString(string++, "Zuriel items increase your magic max hit"); //
        player.getPacketSender().sendString(string++, "by 10 percent per level."); //
        player.getPacketSender().sendString(string++, ""); //

        player.getPacketSender().sendString(cost++, "@or1@1000"); //
        player.getPacketSender().sendString(cost++, "@yel@2500"); //
        player.getPacketSender().sendString(cost++, "@gre@5000"); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "Savior (1/2/3)"); //
        player.getPacketSender().sendString(string++, "Each kill restores X HP and Prayer points"); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, ""); //

        player.getPacketSender().sendString(cost++, "@or1@1000"); //
        player.getPacketSender().sendString(cost++, "@yel@2500"); //
        player.getPacketSender().sendString(cost++, "@gre@5000"); //
        player.getPacketSender().sendString(string++, "Alchemize (75/100/125)"); //
        player.getPacketSender().sendString(string++, "Each resource drop from NPCs converts to"); //
        player.getPacketSender().sendString(string++, "coins equal to X% of that items value"); //


    }

    public static void handleButton(Player player, int button) {

        switch (button) {

            case 8846:
                DialogueManager.start(player, 215);
                player.setDialogueActionId(215);
                break;

            case 8823:
                DialogueManager.start(player, 216);
                player.setDialogueActionId(216);
                break;

            case 8824:
                DialogueManager.start(player, 217);
                player.setDialogueActionId(217);
                break;

            case 8827:
                DialogueManager.start(player, 218);
                player.setDialogueActionId(218);
                break;

            case 8837:
                DialogueManager.start(player, 219);
                player.setDialogueActionId(219);
                break;

            case 8840:
                DialogueManager.start(player, 220);
                player.setDialogueActionId(220);
                break;

            case 8843:
                DialogueManager.start(player, 221);
                player.setDialogueActionId(221);
                break;

            case 8859:
                DialogueManager.start(player, 222);
                player.setDialogueActionId(222);
                break;

            case 8862:
                DialogueManager.start(player, 223);
                player.setDialogueActionId(223);
                break;

            case 8865:
                DialogueManager.start(player, 224);
                player.setDialogueActionId(224);
                break;

            case 15303:
                DialogueManager.start(player, 232);
                player.setDialogueActionId(232);
                break;

            case 15306:
                DialogueManager.start(player, 233);
                player.setDialogueActionId(233);
                break;

            case 15309:
                player.activeInterface = "perkmenu";
                PerkMenu.showPerkInterface(player);
                break;

        }

    }

}
