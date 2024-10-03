package com.arlania.world.content.interfaces;

import com.arlania.world.content.dialogue.DialogueManager;
import com.arlania.world.entity.impl.player.Player;

/**
 * This packet listener manages a button that the player has clicked upon.
 *
 * @author Gabriel Hannason
 */

public class AbilitiesInterface {

    public static void showPerkInterface(Player player) {

        String prefix = "@red@";

        player.getPacketSender().sendInterface(8714); //interface

        player.getPacketSender().sendString(8812, "Close Window"); //Button header
        player.getPacketSender().sendString(8716, "Achievement Abilities"); //title header
        player.getPacketSender().sendString(8718, ""); //1st column header
        player.getPacketSender().sendString(8719, ""); //2nd column header

        player.getPacketSender().sendString(8846, (player.achievementAbilities[0] > 0 ? "@gre@" : "@red@") + "Banker"); //1st Button
        player.getPacketSender().sendString(8823, (player.achievementAbilities[1] > 0 ? "@gre@" : "@red@") + "Processor"); //2nd Button
        player.getPacketSender().sendString(8824, (player.achievementAbilities[2] > 0 ? "@gre@" : "@red@") + "Gatherer"); //3rd Button
        player.getPacketSender().sendString(8827, (player.achievementAbilities[3] > 0 ? "@gre@" : "@red@") + "Sweeten"); //4th Button
        player.getPacketSender().sendString(8837, (player.achievementAbilities[4] > 0 ? "@gre@" : "@red@") + "Entertainer"); //5th Button
        player.getPacketSender().sendString(8840, (player.achievementAbilities[5] > 0 ? "@gre@" : "@red@") + "Combatant"); //6th Button
        player.getPacketSender().sendString(8843, (player.achievementAbilities[6] > 0 ? "@gre@" : "@red@") + "Cryptic"); //7th Button
        player.getPacketSender().sendString(8859, (player.achievementAbilities[7] > 0 ? "@gre@" : "@red@") + "Gambler"); //8th Button
        player.getPacketSender().sendString(8862, (player.achievementAbilities[8] > 0 ? "@gre@" : "@red@") + "Prosperous"); //9th Button
        player.getPacketSender().sendString(8865, (player.achievementAbilities[9] > 0 ? "@gre@" : "@red@") + "Bountiful"); //10th Button
        player.getPacketSender().sendString(15303, ""); //11th Button
        player.getPacketSender().sendString(15306, ""); //12th Button
        player.getPacketSender().sendString(15309, "Menu"); //13th Button



        int string = 8760;
        int cost = 8720;

        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "@blu@All Abilities cost 25 Achievement Points."); //

        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "Unlocks available: " + player.getAchievementPoints() / 25); //

        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "Points until next unlock: " + (25 - (player.getAchievementPoints() % 25))); //

        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, ""); //

        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "@blu@Banker"); //
        player.getPacketSender().sendString(string++, "@bla@-Magic Paper is no longer consumed"); //

        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, ""); //

        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "@blu@Processor"); //
        player.getPacketSender().sendString(string++, "@bla@-You can now process noted items"); //

        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, ""); //

        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "@blu@Gatherer"); //
        player.getPacketSender().sendString(string++, "@bla@-Skilling/PvM actions gather noted items"); //

        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, ""); //

        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "@blu@Sweeten"); //
        player.getPacketSender().sendString(string++, "@bla@-Bosses - 1:1000 Rare Candy"); //

        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, ""); //

        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "@blu@Entertainer"); //
        player.getPacketSender().sendString(string++, "@bla@-Bosses - 1:5000 Event Pass"); //

        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, ""); //

        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "@blu@Combatant"); //
        player.getPacketSender().sendString(string++, "@bla@-Bosses - 1:5000 Battle Pass"); //

        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, ""); //

        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "@blu@Cryptic"); //
        player.getPacketSender().sendString(string++, "@bla@-Raids - 1:2500 Mystery Pass"); //
        player.getPacketSender().sendString(string++, "@bla@-CoX, ToB, GWD, SHR"); //

        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, ""); //

        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "@blu@Gambler"); //
        player.getPacketSender().sendString(string++, "@bla@-Raids - 1:1000 Bingo Card"); //
        player.getPacketSender().sendString(string++, "@bla@-CoX, ToB, GWD, SHR"); //

        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, ""); //

        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "@blu@-Prosperous"); //
        player.getPacketSender().sendString(string++, "@bla@-5% Drop rate boost"); //

        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, ""); //

        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "@blu@-Bountiful"); //
        player.getPacketSender().sendString(string++, "@bla@-25% XP Boost"); //


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
                DialogueManager.start(player, 313);
                player.setDialogueActionId(313);
                break;

            case 8823:
                DialogueManager.start(player, 314);
                player.setDialogueActionId(314);
                break;

            case 8824:
                DialogueManager.start(player, 315);
                player.setDialogueActionId(315);
                break;

            case 8827:
                DialogueManager.start(player, 316);
                player.setDialogueActionId(316);
                break;

            case 8837:
                DialogueManager.start(player, 317);
                player.setDialogueActionId(317);
                break;

            case 8840:
                DialogueManager.start(player, 318);
                player.setDialogueActionId(318);
                break;

            case 8843:
                DialogueManager.start(player, 319);
                player.setDialogueActionId(319);
                break;

            case 8859:
                DialogueManager.start(player, 320);
                player.setDialogueActionId(320);
                break;

            case 8862:
                DialogueManager.start(player, 321);
                player.setDialogueActionId(321);
                break;

            case 8865:
                DialogueManager.start(player, 322);
                player.setDialogueActionId(322);
                break;

            case 15303:

                break;

            case 15306:

                break;

            case 15309:
                player.activeInterface = "perkmenu";
                PerkMenu.showPerkInterface(player);
                break;


        }
    }

}
