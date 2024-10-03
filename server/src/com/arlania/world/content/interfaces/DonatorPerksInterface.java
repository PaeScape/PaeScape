package com.arlania.world.content.interfaces;

import com.arlania.world.content.PlayerPanel;
import com.arlania.world.entity.impl.player.Player;

/**
 * This packet listener manages a button that the player has clicked upon.
 *
 * @author Gabriel Hannason
 */

public class DonatorPerksInterface {

    public static void showPerkInterface(Player player) {

        player.getPacketSender().sendInterface(8714); //interface

        player.getPacketSender().sendString(8812, "Close Window"); //Button header
        player.getPacketSender().sendString(8716, "Achievement Abilities"); //title header
        player.getPacketSender().sendString(8718, ""); //1st column header
        player.getPacketSender().sendString(8719, "Description"); //2nd column header

        player.getPacketSender().sendString(8846, (player.autosupply ? "@gre@" : "@red@") + "Supplies"); //1st Button
        player.getPacketSender().sendString(8823, (player.autobone ? "@gre@" : "@red@") + "Bones"); //2nd Button
        player.getPacketSender().sendString(8824, (player.autokey ? "@gre@" : "@red@") + "Keys"); //3rd Button
        player.getPacketSender().sendString(8827, (player.looterBanking ? "@gre@" : "@red@") + "Looter"); //4th Button
        player.getPacketSender().sendString(8837, (player.achievementAbilities[4] > 0 ? "@gre@" : "@red@") + ""); //5th Button
        player.getPacketSender().sendString(8840, (player.achievementAbilities[5] > 0 ? "@gre@" : "@red@") + ""); //6th Button
        player.getPacketSender().sendString(8843, (player.achievementAbilities[6] > 0 ? "@gre@" : "@red@") + ""); //7th Button
        player.getPacketSender().sendString(8859, (player.achievementAbilities[7] > 0 ? "@gre@" : "@red@") + ""); //8th Button
        player.getPacketSender().sendString(8862, (player.achievementAbilities[8] > 0 ? "@gre@" : "@red@") + ""); //9th Button
        player.getPacketSender().sendString(8865, (player.achievementAbilities[9] > 0 ? "@gre@" : "@red@") + ""); //10th Button
        player.getPacketSender().sendString(15303, ""); //11th Button
        player.getPacketSender().sendString(15306, ""); //12th Button
        player.getPacketSender().sendString(15309, "Menu"); //13th Button


        int string = 8760;
        int cost = 8720;

        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "@blu@All Abilities cost 25 Achievement Points."); //

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
        player.getPacketSender().sendString(string++, "@bla@-Skilling actions gather noted items"); //

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
                if (player.getStaffRights().getStaffRank() > 2) {
                    player.autosupply = !player.autosupply;
                    player.sendMessage("Your auto supply pickup is now: " + (player.autosupply ? "@gre@Active" : "@red@Inactive"));
                    PlayerPanel.refreshPanel(player);
                } else {
                    player.autosupply = false;
                    player.sendMessage("@red@You need to be an Extreme Donator to activate this!");
                }
                DonatorPerksInterface.showPerkInterface(player);
                break;

            case 8823:
                if (player.getStaffRights().getStaffRank() > 2) {
                    player.autobone = !player.autobone;
                    player.sendMessage("Your auto bone pickup is now: " + (player.autobone ? "@gre@Active" : "@red@Inactive"));
                    PlayerPanel.refreshPanel(player);
                } else {
                    player.autobone = false;
                    player.sendMessage("@red@You need to be an Extreme Donator to activate this!");
                }
                DonatorPerksInterface.showPerkInterface(player);
                break;

            case 8824:
                if (player.getStaffRights().getStaffRank() > 2) {
                    player.autokey = !player.autokey;
                    player.sendMessage("Your auto key pickup is now: " + (player.autokey ? "@gre@Active" : "@red@Inactive"));
                    PlayerPanel.refreshPanel(player);
                } else {
                    player.autokey = false;
                    player.sendMessage("@red@You need to be an Extreme Donator to activate this!");
                }
                DonatorPerksInterface.showPerkInterface(player);
                break;

            case 8827:
                if (player.getStaffRights().getStaffRank() > 2) {
                    player.looterBanking = !player.looterBanking;
                    player.sendMessage("Your Looter Banking is now: " + (player.looterBanking ? "@gre@On" : "@red@Off"));
                    PlayerPanel.refreshPanel(player);
                } else {
                    player.getPacketSender().sendMessage("@red@You must be an Extreme Donator to use this feature!");
                }
                DonatorPerksInterface.showPerkInterface(player);
                break;

            case 8837:
                break;

            case 8840:
                break;

            case 8843:
                break;

            case 8859:
                break;

            case 8862:
                break;

            case 8865:
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
