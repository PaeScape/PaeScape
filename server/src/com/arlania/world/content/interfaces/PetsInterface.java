package com.arlania.world.content.interfaces;

import com.arlania.world.content.dialogue.DialogueManager;
import com.arlania.world.entity.impl.player.Player;

/**
 * This packet listener manages a button that the player has clicked upon.
 *
 * @author Gabriel Hannason
 */

public class PetsInterface {

    public static void showPerkInterface(Player player) {

        player.getPacketSender().sendInterface(8714); //interface

        player.getPacketSender().sendString(8812, "Close Window"); //Button header
        player.getPacketSender().sendString(8716, "Donator Pet Boosts"); //title header
        player.getPacketSender().sendString(8718, ""); //1st column header
        player.getPacketSender().sendString(8719, "Description"); //2nd column header

        player.getPacketSender().sendString(8846, (player.activeDonatorPets[0] == 1 ? "@gre@Genie" : (player.donatorPets[0] == 1 ? "@yel@Genie" : "@red@Genie"))); //1st Button
        player.getPacketSender().sendString(8823, (player.activeDonatorPets[1] == 1 ? "@gre@WoM" : (player.donatorPets[1] == 1 ? "@yel@WoM" : "@red@WoM"))); //2nd Button
        player.getPacketSender().sendString(8824, (player.activeDonatorPets[2] == 1 ? "@gre@Lazy Cat" : (player.donatorPets[2] == 1 ? "@yel@Lazy Cat" : "@red@Lazy Cat"))); //3rd Button
        player.getPacketSender().sendString(8827, (player.activeDonatorPets[3] == 1 ? "@gre@Duradead" : (player.donatorPets[3] == 1 ? "@yel@Duradead" : "@red@Duradead"))); //4th Button
        player.getPacketSender().sendString(8837, (player.activeDonatorPets[4] == 1 ? "@gre@Hatius" : (player.donatorPets[4] == 1 ? "@yel@Hatius" : "@red@Hatius"))); //5th Button
        player.getPacketSender().sendString(8840, (player.activeDonatorPets[5] == 1 ? "@gre@Hatius" : (player.donatorPets[5] == 1 ? "@yel@Tryout" : "@red@Tryout"))); //6th Button
        player.getPacketSender().sendString(8843, ""); //7th Button
        player.getPacketSender().sendString(8859, ""); //8th Button
        player.getPacketSender().sendString(8862, ""); //9th Button
        player.getPacketSender().sendString(8865, ""); //10th Button
        player.getPacketSender().sendString(15303, ""); //11th Button
        player.getPacketSender().sendString(15306, ""); //12th Button
        player.getPacketSender().sendString(15309, "Menu"); //13th Button


        int string = 8760;
        int cost = 8720;

        int abilities = 0;

        for(int i = 0; i < player.holidayPets.length; i++) {
            if(player.holidayPets[i] == 1) {
                abilities++;
            }
        }
        abilities++;

        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "@red@You can use Donator Pets on Pat (npc)"); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "@red@to unlock their abilities on Holiday Pets!"); //

        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, ""); ////

        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, "@red@Holiday pets you summon are able to use:"); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(string++, abilities +"@red@ Abilities"); //


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

        int abilitiesAvailable = 0;
        int abilitiesUsed = 0;

        for(int i = 0; i < player.holidayPets.length; i++) {
            if(player.holidayPets[i] == 1) {
                abilitiesAvailable++;
            }
        }
        abilitiesAvailable++;

        for(int i = 0; i < player.activeDonatorPets.length; i++) {
            if(player.activeDonatorPets[i] == 1) {
                abilitiesUsed++;
            }
        }

        switch (button) {

            case 8846:
                if(player.donatorPets[0] == 0) {
                    player.getPacketSender().sendMessage("You need to show Pat (npc) this pet to unlock it's abilities.");
                } else if(player.activeDonatorPets[0] == 1) {
                    player.activeDonatorPets[0] = 0;
                } else if (abilitiesUsed < abilitiesAvailable && player.activeDonatorPets[0] == 0) {
                    player.activeDonatorPets[0] = 1;
                } else if (player.activeDonatorPets[0] == 0) {
                    player.getPacketSender().sendMessage("You're only able to use " + abilitiesAvailable + " donator pet abilities.");
                }
                player.activeInterface = "pets";
                PetsInterface.showPerkInterface(player);
                break;

            case 8823:
                if(player.donatorPets[1] == 0) {
                    player.getPacketSender().sendMessage("You need to show Pat (npc) this pet to unlock it's abilities.");
                } else if(player.activeDonatorPets[1] == 1) {
                    player.activeDonatorPets[1] = 0;
                } else if (abilitiesUsed < abilitiesAvailable && player.activeDonatorPets[1] == 0) {
                    player.activeDonatorPets[1] = 1;
                } else if (player.activeDonatorPets[1] == 0) {
                    player.getPacketSender().sendMessage("You're only able to use " + abilitiesAvailable + " donator pet abilities.");
                }
                player.activeInterface = "pets";
                PetsInterface.showPerkInterface(player);
                break;

            case 8824:
                if(player.donatorPets[2] == 0) {
                    player.getPacketSender().sendMessage("You need to show Pat (npc) this pet to unlock it's abilities.");
                } else if(player.activeDonatorPets[2] == 1) {
                    player.activeDonatorPets[2] = 0;
                } else if (abilitiesUsed < abilitiesAvailable && player.activeDonatorPets[2] == 0) {
                    player.activeDonatorPets[2] = 1;
                } else if (player.activeDonatorPets[2] == 0) {
                    player.getPacketSender().sendMessage("You're only able to use " + abilitiesAvailable + " donator pet abilities.");
                }
                player.activeInterface = "pets";
                PetsInterface.showPerkInterface(player);
                break;

            case 8827:
                if(player.donatorPets[3] == 0) {
                    player.getPacketSender().sendMessage("You need to show Pat (npc) this pet to unlock it's abilities.");
                } else if(player.activeDonatorPets[3] == 1) {
                    player.activeDonatorPets[3] = 0;
                } else if (abilitiesUsed < abilitiesAvailable && player.activeDonatorPets[3] == 0) {
                    player.activeDonatorPets[3] = 1;
                } else if (player.activeDonatorPets[3] == 0) {
                    player.getPacketSender().sendMessage("You're only able to use " + abilitiesAvailable + " donator pet abilities.");
                }
                player.activeInterface = "pets";
                PetsInterface.showPerkInterface(player);
                break;

            case 8837:
                if(player.donatorPets[4] == 0) {
                    player.getPacketSender().sendMessage("You need to show Pat (npc) this pet to unlock it's abilities.");
                } else if(player.activeDonatorPets[4] == 1) {
                    player.activeDonatorPets[4] = 0;
                } else if (abilitiesUsed < abilitiesAvailable && player.activeDonatorPets[4] == 0) {
                    player.activeDonatorPets[4] = 1;
                } else if (player.activeDonatorPets[4] == 0) {
                    player.getPacketSender().sendMessage("You're only able to use " + abilitiesAvailable + " donator pet abilities.");
                }
                player.activeInterface = "pets";
                PetsInterface.showPerkInterface(player);
                break;

            case 8840:
                if(player.donatorPets[5] == 0) {
                    player.getPacketSender().sendMessage("You need to show Pat (npc) this pet to unlock it's abilities.");
                } else if(player.activeDonatorPets[5] == 1) {
                    player.activeDonatorPets[5] = 0;
                } else if (abilitiesUsed < abilitiesAvailable && player.activeDonatorPets[5] == 0) {
                    player.activeDonatorPets[5] = 1;
                } else if (player.activeDonatorPets[5] == 0) {
                    player.getPacketSender().sendMessage("You're only able to use " + abilitiesAvailable + " donator pet abilities.");
                }
                player.activeInterface = "pets";
                PetsInterface.showPerkInterface(player);
                break;

            /*case 8843:
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
