package com.arlania.world.content;

import com.arlania.world.entity.impl.player.Player;

public class PetAbilities {

    public static void checkDonatorPets(Player player, int itemId) {

        if (itemId == 11157) { //genie
            player.donatorPets[0] = 1;
        }

        if (itemId == 212399) { //wise old man
            player.donatorPets[1] = 1;
        }

        if (itemId == 6550) { //lazy cat
            player.donatorPets[2] = 1;
        }

        if (itemId == 8740) { //duradead
            player.donatorPets[3] = 1;
        }

        if (itemId == 212335) { //Hatius
            player.donatorPets[4] = 1;
        }

        if (itemId == 9941) { //Tryout
            player.donatorPets[5] = 1;
        }

        player.getPacketSender().sendMessage("Genie has " + (player.donatorPets[0] == 1 ? "@gre@been shown to Pet." : "@red@has not been show to Pat."));
        player.getPacketSender().sendMessage("Wise Old Man has " + (player.donatorPets[1] == 1 ? "@gre@been shown to Pet." : "@red@has not been show to Pat."));
        player.getPacketSender().sendMessage("Lazy Cat has " + (player.donatorPets[2] == 1 ? "@gre@been shown to Pet." : "@red@has not been show to Pat."));
        player.getPacketSender().sendMessage("Duradead has " + (player.donatorPets[3] == 1 ? "@gre@been shown to Pet." : "@red@has not been show to Pat."));
        player.getPacketSender().sendMessage("Hatius has " + (player.donatorPets[4] == 1 ? "@gre@been shown to Pet." : "@red@has not been show to Pat."));
        player.getPacketSender().sendMessage("Tryout has " + (player.donatorPets[5] == 1 ? "@gre@been shown to Pet." : "@red@has not been show to Pat."));

    }

    public static void checkHolidayPets(Player player, int itemId) {

        if (itemId == 2948) { //leprechaun
            player.holidayPets[0] = 1;
        }
        if (itemId == 7930) { //easter bunny
            player.holidayPets[1] = 1;
        }
        if (itemId == 964) { //death
            player.holidayPets[2] = 1;
        }
        if (itemId == 3619) { //santa
            player.holidayPets[3] = 1;
        }

        player.getPacketSender().sendMessage("Leprechaun has " + (player.holidayPets[0] == 1 ? "@gre@been shown to Pet." : "@red@has not been show to Pat."));
        player.getPacketSender().sendMessage("Easter Bunny has " + (player.holidayPets[1] == 1 ? "@gre@been shown to Pet." : "@red@has not been show to Pat."));
        player.getPacketSender().sendMessage("Death has " + (player.holidayPets[2] == 1 ? "@gre@been shown to Pet." : "@red@has not been show to Pat."));
        player.getPacketSender().sendMessage("Santa has " + (player.holidayPets[3] == 1 ? "@gre@been shown to Pet." : "@red@has not been show to Pat."));

    }

    public static boolean checkPetAbilities(Player player, String type) {

        type = type.toLowerCase();
        boolean hasHolidayPet = false;
        boolean hasPet = false;

        //Holiday Pets
        //Pet Leprechaun (St. Patrick's Day)
        //Pet Easter Bunny (Easter)
        //Pet Death (Halloween)
        //Pet Santa (Christmas)

        if (player.getInventory().contains(2948) || player.getInventory().contains(7930) || player.getInventory().contains(3619) || player.getInventory().contains(964)) {
            hasHolidayPet = true;
        }

        if (player.getSummoning().getFamiliar() != null) {
            if (player.getSummoning().getFamiliar().getSummonNpc().getId() == 3021 || player.getSummoning().getFamiliar().getSummonNpc().getId() == 1835 ||
                    player.getSummoning().getFamiliar().getSummonNpc().getId() == 2862 || player.getSummoning().getFamiliar().getSummonNpc().getId() == 14386) {
                hasHolidayPet = true;
            }
        }

        if (hasHolidayPet) {

            switch (type) {

                case "genie":
                    hasPet = player.activeDonatorPets[0] == 1;
                    break;
                case "wom":
                    hasPet = player.activeDonatorPets[1] == 1;
                    break;
                case "lazycat":
                    hasPet = player.activeDonatorPets[2] == 1;
                    break;
                case "duradead":
                    hasPet = player.activeDonatorPets[3] == 1;
                    break;
                case "hatius":
                    hasPet = player.activeDonatorPets[4] == 1;
                    break;
                case "tryout":
                    hasPet = player.activeDonatorPets[5] == 1;
                    break;
            }


        }

        if (!hasPet) {
            switch (type) {

                case "wom":
                    hasPet = player.getInventory().contains(212399) || (player.getSummoning().getFamiliar() != null && player.getSummoning().getFamiliar().getSummonNpc().getId() == 2253);
                    break;
                case "genie":
                    hasPet = player.getInventory().contains(11157) || (player.getSummoning().getFamiliar() != null && player.getSummoning().getFamiliar().getSummonNpc().getId() == 409);
                    break;
                case "lazycat":
                    hasPet = player.getInventory().contains(6550) || (player.getSummoning().getFamiliar() != null && player.getSummoning().getFamiliar().getSummonNpc().getId() == 2663);
                    break;
                case "duradead":
                    hasPet = player.getInventory().contains(8740) || (player.getSummoning().getFamiliar() != null && player.getSummoning().getFamiliar().getSummonNpc().getId() == 14405);
                    break;
                case "hatius":
                    hasPet = player.getInventory().contains(212335) || (player.getSummoning().getFamiliar() != null && player.getSummoning().getFamiliar().getSummonNpc().getId() == 19523);
                    break;
                case "tryout":
                    hasPet = player.getInventory().contains(9941) || (player.getSummoning().getFamiliar() != null && player.getSummoning().getFamiliar().getSummonNpc().getId() == 2082);
                    break;
            }
        }

        if (hasPet) {
            return true;
        }

        return false;
    }


}
