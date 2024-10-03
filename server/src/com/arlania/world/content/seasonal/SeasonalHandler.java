package com.arlania.world.content.seasonal;

import com.arlania.DiscordBot;
import com.arlania.GameLoader;
import com.arlania.GameServer;
import com.arlania.GameSettings;
import com.arlania.model.GameMode;
import com.arlania.model.StaffRights;
import com.arlania.model.Subscriptions;
import com.arlania.net.login.LoginResponses;
import com.arlania.util.Misc;
import com.arlania.world.entity.impl.player.Player;
import com.arlania.world.entity.impl.player.PlayerLoading;
import com.arlania.world.entity.impl.player.PlayerSaving;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.javacord.api.entity.message.MessageBuilder;

import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.logging.Level;

public class SeasonalHandler {

    public static void addPoints(Player player, int points) {
        if (player.getGameMode() != GameMode.SEASONAL_IRONMAN && player.getStaffRights() != StaffRights.DEVELOPER) {
            return;
        }

        player.seasonPoints += points;
        player.getPacketSender().sendMessage("<img=10> <col=339900>You now have " + player.seasonPoints + " Seasonal Points.");

        checkRewards(player);
    }

    //TODO - Rewards

    public static void checkRewards(Player player) {

        if (player.seasonalTier == 0 && player.seasonPoints >= 25) {
            player.seasonalPerks[0] = 1;
            player.seasonalTier++;
            player.sendMessage("@red@You can unlock a Tier 1 Seasonal Perk!");
        }
        if (player.seasonalTier == 1 && player.seasonPoints >= 50) {
            player.seasonalPP += 500;
            player.seasonalTier++;
            player.sendMessage("@red@You can claim 500 PaePoints in the Seasonal Interface!");
        }
        if (player.seasonalTier == 2 && player.seasonPoints >= 75) {
            player.seasonalTeleportUnlocks++;
            player.seasonalTier++;
            player.sendMessage("@red@You can now unlock 1 Teleport in the Seasonal Interface!");
        }
        if (player.seasonalTier == 3 && player.seasonPoints >= 100) {
            player.seasonalPerks[1] = 1;
            player.seasonalTier++;
            player.sendMessage("@red@You can unlock a Tier 2 Seasonal Perk!");
        }
        if (player.seasonalTier == 4 && player.seasonPoints >= 125) {
            player.seasonalTeleportUnlocks++;
            player.sendMessage("@red@You can now unlock 1 Teleport in the Seasonal Interface!");
            player.seasonalTier++;
        }
        if (player.seasonalTier == 5 && player.seasonPoints >= 150) {
            player.seasonalMBox += 3;
            player.seasonalTier++;
            player.sendMessage("@red@You can claim 3 Mystery Boxes in the Seasonal Interface!");
        }
        if (player.seasonalTier == 6 && player.seasonPoints >= 175) {
            player.seasonalTeleportUnlocks++;
            player.sendMessage("@red@You can now unlock 1 Teleport in the Seasonal Interface!");
            player.seasonalTier++;
        }
        if (player.seasonalTier == 7 && player.seasonPoints >= 200) {
            player.seasonalPerks[2] = 1;
            player.seasonalTier++;
            player.sendMessage("@red@You can unlock a Tier 3 Seasonal Perk!");
        }
        if (player.seasonalTier == 8 && player.seasonPoints >= 225) {
            player.seasonalPP += 1000;
            player.seasonalTier++;
            player.sendMessage("@red@You can claim 1,000 PaePoints in the Seasonal Interface!");
        }
        if (player.seasonalTier == 9 && player.seasonPoints >= 250) {
            player.seasonalEMBox++;
            player.seasonalTier++;
            player.sendMessage("@red@You can claim an Elite Mystery Box in the Seasonal Interface!");
        }
        if (player.seasonalTier == 10 && player.seasonPoints >= 275) {
            player.seasonalTeleportUnlocks++;
            player.seasonalTier++;
            player.sendMessage("@red@You can now unlock 1 Teleport in the Seasonal Interface!");
        }
        if (player.seasonalTier == 11 && player.seasonPoints >= 300) {
            player.seasonalPerks[3] = 1;
            player.seasonalTier++;
            player.sendMessage("@red@You can unlock a Tier 4 Seasonal Perk!");
        }
        if (player.seasonalTier == 12 && player.seasonPoints >= 325) {
            player.seasonalTeleportUnlocks++;
            player.seasonalTier++;
            player.sendMessage("@red@You can now unlock 1 Teleport in the Seasonal Interface!");
        }
        if (player.seasonalTier == 13 && player.seasonPoints >= 350) {
            player.seasonalEventPass++;
            player.seasonalTier++;
            player.sendMessage("@red@You can claim an Event Pass in the Seasonal Interface!");
        }
        if (player.seasonalTier == 14 && player.seasonPoints >= 375) {
            player.seasonalTeleportUnlocks++;
            player.seasonalTier++;
            player.sendMessage("@red@You can now unlock 1 Teleport in the Seasonal Interface!");
        }
        if (player.seasonalTier == 15 && player.seasonPoints >= 400) {
            player.seasonalPerks[4] = 1;
            player.seasonalTier++;
            player.sendMessage("@red@You can unlock a Tier 5 Seasonal Perk!");
        }
        if (player.seasonalTier == 16 && player.seasonPoints >= 425) {
            player.seasonalPP += 5000;
            player.seasonalTier++;
            player.sendMessage("@red@You can claim 5,000 PaePoints in the Seasonal Interface!");
        }
        if (player.seasonalTier == 17 && player.seasonPoints >= 450) {
            player.seasonalRing++;
            player.seasonalTier++;
            player.sendMessage("@red@You can claim an Ring of Fortune in the Seasonal Interface!");
        }
        if (player.seasonalTier == 18 && player.seasonPoints >= 475) {
            player.seasonalTeleportUnlocks++;
            player.seasonalTier++;
            player.sendMessage("@red@You can now unlock 1 Teleport in the Seasonal Interface!");
        }
        if (player.seasonalTier == 19 && player.seasonPoints >= 500) {
            player.seasonalDonationTokens += 10;
            player.seasonalTier++;
            player.sendMessage("@red@You can now claim 10 Donation Store Tokens in the Seasonal Interface!");
        }
        if (player.seasonalTier == 20 && player.seasonPoints >= 525) {
            player.seasonalTeleportUnlocks++;
            player.seasonalTier++;
            player.sendMessage("@red@You can now unlock 1 Teleport in the Seasonal Interface!");
        }
        if (player.seasonalTier == 21 && player.seasonPoints >= 550) {
            player.seasonalEventPass++;
            player.seasonalTier++;
            player.sendMessage("@red@You can claim an Event Pass in the Seasonal Interface!");
        }
        if (player.seasonalTier == 22 && player.seasonPoints >= 575) {
            player.seasonalTeleportUnlocks++;
            player.seasonalTier++;
            player.sendMessage("@red@You can now unlock 1 Teleport in the Seasonal Interface!");
        }
        if (player.seasonalTier == 23 && player.seasonPoints >= 600) {
            player.seasonalPerks[5] = 1;
            player.seasonalTier++;
            player.sendMessage("@red@You can unlock a Tier 6 Seasonal Perk!");
        }
        if (player.seasonalTier == 24 && player.seasonPoints >= 625) {
            player.seasonalPP += 10000;
            player.seasonalTier++;
            player.sendMessage("@red@You can claim 10,000 PaePoints in the Seasonal Interface!");
        }
        if (player.seasonalTier == 25 && player.seasonPoints >= 650) {
            player.seasonalDonationTokens += 20;
            player.seasonalTier++;
            player.sendMessage("@red@You can now claim 20 Donation Store Tokens in the Seasonal Interface!");
        }
        if (player.seasonalTier == 26 && player.seasonPoints >= 675) {
            player.seasonalTeleportUnlocks++;
            player.seasonalTier++;
            player.sendMessage("@red@You can now unlock 1 Teleport in the Seasonal Interface!");
        }
        if (player.seasonalTier == 27 && player.seasonPoints >= 700) {
            player.seasonalSBox++;
            player.seasonalTier++;
            player.sendMessage("@red@You can claim a Supreme Mystery Box in the Seasonal Interface!");
        }
        if (player.seasonalTier == 28 && player.seasonPoints >= 725) {
            player.seasonalTeleportUnlocks++;
            player.seasonalTier++;
            player.sendMessage("@red@You can now unlock 1 Teleport in the Seasonal Interface!");
        }
        if (player.seasonalTier == 29 && player.seasonPoints >= 750) {
            player.seasonalDonationTokens += 50;
            player.seasonalTier++;
            player.sendMessage("@red@You can now claim 50 Donation Store Tokens in the Seasonal Interface!");
        }
        if (player.seasonalTier == 30 && player.seasonPoints >= 775) {
            player.seasonalTeleportUnlocks++;
            player.seasonalTier++;
            player.sendMessage("@red@You can now unlock 1 Teleport in the Seasonal Interface!");
        }
        if (player.seasonalTier == 31 && player.seasonPoints >= 800) {
            player.seasonalPerks[6] = 1;
            player.seasonalTier++;
            player.sendMessage("@red@You can unlock a Tier 7 Seasonal Perk!");
        }
        if (player.seasonalTier == 32 && player.seasonPoints >= 900) {
            player.seasonalDonationTokens += 100;
            player.seasonalTier++;
            player.sendMessage("@red@You can now claim 100 Donation Store Tokens in the Seasonal Interface!");
        }
        if (player.seasonalTier == 33 && player.seasonPoints >= 1000) {
            player.seasonalPerks[7] = 1;
            player.seasonalTier++;
            player.sendMessage("@red@You can unlock a Tier 8 Seasonal Perk!");
        }

        StaffRights rights = null;

        if (player.seasonPoints >= 800 && player.getStaffRights().getStaffRank() < 6)
            rights = StaffRights.MASTER_DONATOR;
        else if (player.seasonPoints >= 600 && player.getStaffRights().getStaffRank() < 5)
            rights = StaffRights.UBER_DONATOR;
        else if (player.seasonPoints >= 400 && player.getStaffRights().getStaffRank() < 4)
            rights = StaffRights.LEGENDARY_DONATOR;
        else if (player.seasonPoints >= 300 && player.getStaffRights().getStaffRank() < 3)
            rights = StaffRights.EXTREME_DONATOR;
        else if (player.seasonPoints >= 200 && player.getStaffRights().getStaffRank() < 2)
            rights = StaffRights.SUPER_DONATOR;
        else if (player.seasonPoints >= 100 && player.getStaffRights().getStaffRank() < 1)
            rights = StaffRights.DONATOR;

        if(player.seasonPoints >= 750)
            player.setSubscription(Subscriptions.DRAGONSTONE);
        else if(player.seasonPoints >= 600)
            player.setSubscription(Subscriptions.DIAMOND);
        else if(player.seasonPoints >= 450)
            player.setSubscription(Subscriptions.RUBY);
        else if(player.seasonPoints >= 300)
            player.setSubscription(Subscriptions.EMERALD);
        else if(player.seasonPoints >= 150)
            player.setSubscription(Subscriptions.SAPPHIRE);

        LocalDate date = LocalDate.of(GameLoader.getYear(), GameLoader.getMonth(), GameLoader.getDayOfTheMonth()); // The date you want to convert
        long epochDays = date.toEpochDay();
        int epochInteger = (int) epochDays;

        player.subscriptionStartDate = epochInteger;
        player.subscriptionEndDate = epochInteger + 30;

        if (rights != null && rights != player.getStaffRights()) {
            player.getPacketSender().sendMessage("You've become a " + Misc.formatText(rights.toString().toLowerCase()) + "! Congratulations!");
            player.setStaffRights(rights);
            player.getPacketSender().sendRights();
        }
    }

    //SEASONAL TODO - Make sure players can't save over existing player files
    public static void newSeasonal(Player player, String playerName) {

        Player newAcc = new Player(null);
        newAcc.setUsername(Misc.formatPlayerName(playerName));

        if (PlayerLoading.getResultWithoutLogin(newAcc) != LoginResponses.NEW_ACCOUNT) {
            player.getPacketSender().sendMessage("You can't use that user name");
            return;
        }

        String seasonal = GameLoader.getMonth() + "" + GameLoader.getYear();
        player.lastSeasonal = seasonal;
        newAcc.setPasswordHash(player.getPasswordHash());
        newAcc.setPosition(GameSettings.DEFAULT_POSITION);
        GameMode.setSeasonal(newAcc);
        newAcc.seasonMonth = GameLoader.getMonth();
        newAcc.seasonYear = GameLoader.getYear();
        newAcc.setNewPlayer(false);
        newAcc.setPlayerLocked(false);
        newAcc.setexprate(5);
        newAcc.linkedMain = player.getUsername();

        player.getPacketSender().sendMessage("@red@Seasonal accounts will be removed after the season is over.");
        player.getPacketSender().sendMessage("@red@Seasonal accounts are not able to transfer wealth.");

        saveNewSeasonal(newAcc);
        PlayerSaving.save(player);

        String discordMessage = "[New Seasonal] **" + newAcc.getUsername() + "** was created for Season " + GameLoader.getMonth() + "/" + GameLoader.getYear();

        if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
            new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.NEW_PLAYERS).get());

    }

    public static void saveNewSeasonal(Player player) {
        if (player.newPlayer())
            return;
        // Create the path and file objects.
        Path path = Paths.get(GameServer.getSaveDirectory(), player.getUsername() + ".json");
        File file = path.toFile();
        file.getParentFile().setWritable(true);

        // Attempt to make the player save directory if it doesn't
        // exist.
        if (!file.getParentFile().exists()) {
            try {
                file.getParentFile().mkdirs();
            } catch (SecurityException e) {
                GameServer.getLogger().log(Level.SEVERE, "Unable to create directory for player data!", e);
            }
        }
        try (FileWriter writer = new FileWriter(file)) {

            Gson builder = new GsonBuilder().setPrettyPrinting().create();
            JsonObject object = new JsonObject();
            object.addProperty("username", player.getUsername().trim());
            object.addProperty("passwordHash", new String(player.getPasswordHash(), StandardCharsets.UTF_8));
            object.addProperty("game-mode", player.getGameMode().name());
            object.addProperty("seasonMonth", player.seasonMonth);
            object.addProperty("seasonYear", player.seasonYear);
            object.addProperty("exprate", player.getexprate());
            object.addProperty("linkedMain", player.linkedMain);
            object.add("position", builder.toJsonTree(player.getPosition()));

            writer.write(builder.toJson(object));
        } catch (Exception e) {
            // An error happened while saving.
            GameServer.getLogger().log(Level.WARNING, "An error has occured while saving a character file!", e);
        }
    }

    public static void resetSeasonal(Player player) {

        player.seasonalTier = 0;
        player.seasonalTeleportUnlocks = 0;
        player.seasonalPerks[0] = 0;
        player.seasonalPerks[1] = 0;
        player.seasonalPerks[2] = 0;
        player.seasonalPerks[3] = 0;
        player.seasonalPerks[4] = 0;
        player.seasonalPerks[5] = 0;
        player.seasonalPerks[6] = 0;
        player.seasonalPerks[7] = 0;
        for (int i = 0; i < player.seasonalTrainingTeleports.length; i++) {
            player.seasonalTrainingTeleports[i] = 0;
        }
        for (int i = 0; i < player.seasonalDungeonTeleports.length; i++) {
            player.seasonalDungeonTeleports[i] = 0;
        }
        for (int i = 0; i < player.seasonalBossTeleports.length; i++) {
            player.seasonalBossTeleports[i] = 0;
        }
        for (int i = 0; i < player.seasonalMinigameTeleports.length; i++) {
            player.seasonalMinigameTeleports[i] = 0;
        }
        for (int i = 0; i < player.seasonalRaidsTeleports.length; i++) {
            player.seasonalRaidsTeleports[i] = 0;
        }
        player.seasonalPP = 0;
        player.seasonalMBox = 0;
        player.seasonalEMBox = 0;
        player.seasonalCrystalKeys = 0;
        player.seasonalSBox = 0;
        player.seasonalEventPass = 0;
        player.seasonalRing = 0;
        player.seasonalDonationTokens = 0;
        player.Harvester = false;
        player.Producer = false;
        player.Contender = false;
        player.Strategist = false;
        player.Gilded = false;
        player.Shoplifter = false;
        player.Impulsive = false;
        player.Rapid = false;
        player.Bloodthirsty = false;
        player.Infernal = false;
        player.Summoner = false;
        player.Ruinous = false;
        player.Gladiator = false;
        player.Warlord = false;
        player.Deathless = false;
        player.Executioner = false;
    }

}
