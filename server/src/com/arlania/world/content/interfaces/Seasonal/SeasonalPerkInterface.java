package com.arlania.world.content.interfaces.Seasonal;

import com.arlania.world.content.dialogue.DialogueManager;
import com.arlania.world.content.interfaces.PerkMenu;
import com.arlania.world.entity.impl.player.Player;

/**
 * This packet listener manages a button that the player has clicked upon.
 *
 * @author Gabriel Hannason
 */

public class SeasonalPerkInterface {

    public static void showPerkInterface(Player player) {

        String tier1prefix = "";
        String tier2prefix = "";
        String tier3prefix = "";
        String tier4prefix = "";
        String tier5prefix = "";
        String tier6prefix = "";
        String tier7prefix = "";
        String tier8prefix = "";

        String tier1perk = "";
        String tier2perk = "";
        String tier3perk = "";
        String tier4perk = "";
        String tier5perk = "";
        String tier6perk = "";
        String tier7perk = "";
        String tier8perk = "";



        if(player.Harvester) {
            tier1prefix = "@gre@";
            tier1perk = "Harvester";
        } else if(player.Producer) {
            tier1prefix = "@gre@";
            tier1perk = "Producer";
        } else if(player.seasonalPerks[0] == 1) {
            tier1prefix = "@yel@";
            tier1perk = "Tier 1";
        } else {
            tier1prefix = "@red@";
            tier1perk = "Tier 1";
        }

        if(player.Contender) {
            tier2prefix = "@gre@";
            tier2perk = "Contender";
        } else if(player.Strategist) {
            tier2prefix = "@gre@";
            tier2perk = "Strategist";
        } else if(player.seasonalPerks[1] == 1) {
            tier2prefix = "@yel@";
            tier2perk = "Tier 2";
        } else {
            tier2prefix = "@red@";
            tier2perk = "Tier 2";
        }

        if(player.Gilded) {
            tier3prefix = "@gre@";
            tier3perk = "Gilded";
        } else if(player.Shoplifter) {
            tier3prefix = "@gre@";
            tier3perk = "Shoplifter";
        } else if(player.seasonalPerks[2] == 1) {
            tier3prefix = "@yel@";
            tier3perk = "Tier 3";
        } else {
            tier3prefix = "@red@";
            tier3perk = "Tier 3";
        }

        if(player.Impulsive) {
            tier4prefix = "@gre@";
            tier4perk = "Impulisve";
        } else if(player.Rapid) {
            tier4prefix = "@gre@";
            tier4perk = "Rapid";
        } else if(player.seasonalPerks[3] == 1) {
            tier4prefix = "@yel@";
            tier4perk = "Tier 4";
        } else {
            tier4prefix = "@red@";
            tier4perk = "Tier 4";
        }

        if(player.Bloodthirsty) {
            tier5prefix = "@gre@";
            tier5perk = "Bloodthirsty";
        } else if(player.Infernal) {
            tier5prefix = "@gre@";
            tier5perk = "Infernal";
        } else if(player.seasonalPerks[4] == 1) {
            tier5prefix = "@yel@";
            tier5perk = "Tier 5";
        } else {
            tier5prefix = "@red@";
            tier5perk = "Tier 5";
        }

        if(player.Summoner) {
            tier6prefix = "@gre@";
            tier6perk = "Summoner";
        } else if(player.Ruinous) {
            tier6prefix = "@gre@";
            tier6perk = "Ruinous";
        } else if(player.seasonalPerks[5] == 1) {
            tier6prefix = "@yel@";
            tier6perk = "Tier 6";
        } else {
            tier6prefix = "@red@";
            tier6perk = "Tier 6";
        }

        if(player.Gladiator) {
            tier7prefix = "@gre@";
            tier7perk = "Gladiator";
        } else if(player.Warlord) {
            tier7prefix = "@gre@";
            tier7perk = "Warlord";
        } else if(player.seasonalPerks[6] == 1) {
            tier7prefix = "@yel@";
            tier7perk = "Tier 7";
        } else {
            tier7prefix = "@red@";
            tier7perk = "Tier 7";
        }

        if(player.Deathless) {
            tier8prefix = "@gre@";
            tier8perk = "Deathless";
        } else if(player.Executioner) {
            tier8prefix = "@gre@";
            tier8perk = "Executioner";
        } else if(player.seasonalPerks[7] == 1) {
            tier8prefix = "@yel@";
            tier8perk = "Tier 8";
        } else {
            tier8prefix = "@red@";
            tier8perk = "Tier 8";
        }

        player.getPacketSender().sendInterface(8714); //interface

        player.getPacketSender().sendString(8812, "Close Window"); //Button header
        player.getPacketSender().sendString(8716, "Perks"); //title header
        player.getPacketSender().sendString(8718, "Cost"); //1st column header
        player.getPacketSender().sendString(8719, "Description"); //2nd column header

        player.getPacketSender().sendString(8846, tier1prefix + tier1perk); //1st Button
        player.getPacketSender().sendString(8823, tier2prefix + tier2perk); //2nd Button
        player.getPacketSender().sendString(8824, tier3prefix + tier3perk); //3rd Button
        player.getPacketSender().sendString(8827, tier4prefix + tier4perk); //4th Button
        player.getPacketSender().sendString(8837, tier5prefix + tier5perk); //5th Button
        player.getPacketSender().sendString(8840, tier6prefix + tier6perk); //6th Button
        player.getPacketSender().sendString(8843, tier7prefix + tier7perk); //7th Button
        player.getPacketSender().sendString(8859, tier8prefix + tier8perk); //8th Button
        player.getPacketSender().sendString(8862, ""); //9th Button
        player.getPacketSender().sendString(8865, ""); //10th Button
        player.getPacketSender().sendString(15303, ""); //11th Button
        player.getPacketSender().sendString(15306, ""); //12th Button
        player.getPacketSender().sendString(15309, "Menu"); //13th Button


        int string = 8760;
        int cost = 8720;

        //public boolean Harvester = false;
        //public boolean Producer = false;
        //public boolean Contender = false;
        //public boolean Strategist = false;
        //public boolean Gilded = false;
        //public boolean Shoplifter = false;
        //public boolean Impulsive = false;
        //public boolean Rapid = false;
        //public boolean Bloodthirsty = false;
        //public boolean Infernal = false;
        //public boolean Summoner = false;
        //public boolean Ruinous = false;
        //public boolean Gladiator = false;
        //public boolean Warlord = false;
        //public boolean Deathless = false;
        //public boolean Executioner = false;

        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "Tier 1 - 25 Seasonal Points"); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "Harvester/Producer"); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "Tier 2 - 100 Seasonal Points"); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "Contender/Strategist"); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "Tier 3 - 200 Seasonal Points"); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "Gilded/Shoplifter"); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "Tier 4 - 300 Seasonal Points"); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "Impulsive/Rapid"); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "Tier 5 - 400 Seasonal Points"); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "Bloodthirsty/Infernal"); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "Tier 6 - 600 Seasonal Points"); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "Summoner/Ruinous"); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "Tier 7 - 800 Seasonal Points"); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "Gladiator/Warlord"); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "Tier 8 - 1000 Seasonal Points"); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "Deathless/Executioner"); //
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
                DialogueManager.start(player, 1051);
                player.setDialogueActionId(1051);
                break;

            case 8823:
                DialogueManager.start(player, 1052);
                player.setDialogueActionId(1052);
                break;

            case 8824:
                DialogueManager.start(player, 1053);
                player.setDialogueActionId(1053);
                break;

            case 8827:
                DialogueManager.start(player, 1054);
                player.setDialogueActionId(1054);
                break;

            case 8837:
                DialogueManager.start(player, 1055);
                player.setDialogueActionId(1055);
                break;

            case 8840:
                DialogueManager.start(player, 1056);
                player.setDialogueActionId(1056);
                break;

            case 8843:
                DialogueManager.start(player, 1057);
                player.setDialogueActionId(1057);
                break;

            case 8859:
                DialogueManager.start(player, 1058);
                player.setDialogueActionId(1058);
                break;

            case 8862:
                //DialogueManager.start(player, 1057);
                //player.setDialogueActionId(1057);
                break;

            case 8865:
                //DialogueManager.start(player, 1058);
                //player.setDialogueActionId(1058);
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
