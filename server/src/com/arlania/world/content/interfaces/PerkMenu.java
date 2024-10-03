package com.arlania.world.content.interfaces;

import com.arlania.model.GameMode;
import com.arlania.world.content.interfaces.Seasonal.SeasonalPerkInterface;
import com.arlania.world.content.seasonal.Interface.Seasonal;
import com.arlania.world.entity.impl.player.Player;

/**
 * This packet listener manages a button that the player has clicked upon.
 *
 * @author Gabriel Hannason
 */

public class PerkMenu {

    public static void showPerkInterface(Player player) {

        player.getPacketSender().sendInterface(8714); //interface

        player.getPacketSender().sendString(8812, "Close Window"); //Button header
        player.getPacketSender().sendString(8716, "Perk Menu"); //title header
        player.getPacketSender().sendString(8718, ""); //1st column header
        player.getPacketSender().sendString(8719, "Description"); //2nd column header

        player.getPacketSender().sendString(8846, "Basic"); //1st Button
        player.getPacketSender().sendString(8823, "Mastery"); //2nd Button
        player.getPacketSender().sendString(8824, "Wilderness"); //3rd Button
        player.getPacketSender().sendString(8827, "Abilities"); //4th Button
        player.getPacketSender().sendString(8837, ""); //5th Button
        player.getPacketSender().sendString(8840, ""); //6th Button
        player.getPacketSender().sendString(8843, ""); //7th Button
        player.getPacketSender().sendString(8859, ""); //8th Button
        player.getPacketSender().sendString(8862, ""); //9th Button
        player.getPacketSender().sendString(8865, "Pets"); //10th Button
        if(player.getGameMode() == GameMode.SEASONAL_IRONMAN) {
            player.getPacketSender().sendString(15303, "Seasonal"); //11th Button
        } else {
            player.getPacketSender().sendString(15303, ""); //11th
        }
        player.getPacketSender().sendString(15306, "Drop Rate"); //12th Button
        player.getPacketSender().sendString(15309, "Looter"); //13th Button


        int string = 8760;
        int cost = 8720;

        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "Basic"); //
        player.getPacketSender().sendString(string++, "-A set of perks everyone has access to."); //

        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, "Mastery"); //
        player.getPacketSender().sendString(string++, "-A set of perks unlocked after Prestige perks."); //

        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, "Wilderness"); //
        player.getPacketSender().sendString(string++, "-A set of perks specific for the Wilderness."); //

        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, "Drop Rate"); //
        player.getPacketSender().sendString(string++, "-Players have the ability to pay coins for a "); //
        player.getPacketSender().sendString(string++, "  Drop Rate boost at specified raids."); //

        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, "Donator"); //
        player.getPacketSender().sendString(string++, "-A set of perks unlocked through Donator Benefits"); //

        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, ""); //

        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, ""); //

        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, ""); //

        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, ""); //

        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, ""); //

        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, ""); //
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
                player.activeInterface = "perks";
                PerkInterface.showPerkInterface(player);
                break;

            case 8823:
                player.activeInterface = "masteryperks";
                MasteryPerkInterface.showPerkInterface(player);
                break;

            case 8824:
                player.activeInterface = "wildernessperks";
                WildernessPerkInterface.showPerkInterface(player);
                break;

            case 8827:
                player.activeInterface = "achievementabilities";
                AbilitiesInterface.showPerkInterface(player);
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
                player.activeInterface = "pets";
                PetsInterface.showPerkInterface(player);
                break;

            case 15303:
                player.activeInterface = "seasonalperks";
                SeasonalPerkInterface.showPerkInterface(player);
                break;

            case 15306:
                player.activeInterface = "droprate";
                DropRateInterface.showPerkInterface(player);
                break;

            case 15309:
                player.activeInterface = "looteroptions";
                LooterOptions.showLooterInterface(player);
                break;

        }

    }

}
