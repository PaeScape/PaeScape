package com.arlania.world.content.minigames.impl.kingdom;

import com.arlania.DiscordBot;
import com.arlania.GameServer;
import com.arlania.GameSettings;
import com.arlania.model.GameMode;
import com.arlania.model.container.impl.MailBox;
import com.arlania.util.Misc;
import com.arlania.world.World;
import com.arlania.world.content.dialogue.DialogueManager;
import com.arlania.world.content.marketplace.Marketplace;
import com.arlania.world.entity.impl.player.Player;
import com.google.gson.*;
import lombok.Data;
import org.javacord.api.entity.message.MessageBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Optional;
import java.util.logging.Level;

public class NobilitySystem {

    private static final double DAILY_TAX_RATE = 0.01;
    private static long nextTax = Long.MAX_VALUE;

    private static final ArrayList<Contribution> NOBLES = new ArrayList<>(25);
    private static final ArrayList<Contribution> NOBLES_IM = new ArrayList<>(25);
    private static final ArrayList<Contribution> NOBLES_HCIM = new ArrayList<>(25);
    private static final ArrayList<Contribution> NOBLES_UIM = new ArrayList<>(25);


    private static final File NOBILITY_SAVE_FILE = Paths.get(GameServer.getSaveDirectory(), "nobility_system", "nobility.json").toFile();
    /*

    Top 5

    15% chance to roll additional drop
    15% chance for additional raid key
    15% bonus XP
    15% max hit increase (non-pvp)


    Next 10

    10% chance to roll additional drop
    10% chance for additional raid key
    10% bonus XP
    10% max hit increase (non-pvp)


    Next 10

    5% chance to roll additional drop
    5% chance for additional raid key
    5% bonus XP
    5% max hit increase (non-pvp)


    Notes

    Noble players will bypass the cooldown timer of the Well of Events
    1% of each player's total Nobility wealth will be removed every 24 hours
    When a player would be removed from the Top 25 list, their current donation is sent to their Mailbox

    Players donate coins to the Kingdom, maybe use Miscellania for this.

    Rankings above based on how much wealth the player has donated into the Kingdom.
    Wealth can't be removed from the Kingdom, unless the player is no longer in the top 25

    At Server Time 00:00, all donations are decreased 1%.

    Use Miscellenia's Castle and area

    */


    public static void init() {
        nextTax = LocalDate.now(ZoneId.systemDefault()).toEpochDay() + 1;
        load();
    }

    public static void sequence() {
        LocalDate today = LocalDate.now(ZoneId.systemDefault());
        if (today.toEpochDay() >= nextTax) {
            long collectedTaxes = 0;
            for (Contribution contribution : NOBLES) {
                collectedTaxes += (long) (contribution.getContribution() * DAILY_TAX_RATE);
                contribution.setContribution((long) (contribution.getContribution() * (1 - DAILY_TAX_RATE)));
            }
            for (Contribution contribution : NOBLES_IM) {
                collectedTaxes += (long) (contribution.getContribution() * DAILY_TAX_RATE);
                contribution.setContribution((long) (contribution.getContribution() * (1 - DAILY_TAX_RATE)));
            }
            for (Contribution contribution : NOBLES_HCIM) {
                collectedTaxes += (long) (contribution.getContribution() * DAILY_TAX_RATE);
                contribution.setContribution((long) (contribution.getContribution() * (1 - DAILY_TAX_RATE)));
            }
            for (Contribution contribution : NOBLES_UIM) {
                collectedTaxes += (long) (contribution.getContribution() * DAILY_TAX_RATE);
                contribution.setContribution((long) (contribution.getContribution() * (1 - DAILY_TAX_RATE)));
            }

            Marketplace.removeItemsWithCollectedTaxes(collectedTaxes, World.taxes);

            nextTax++;
        }
    }

    public static void contribute(Player player, long amount) {

        if(player.getGameMode() == GameMode.SEASONAL_IRONMAN) {
            player.getPacketSender().sendMessage("Seasonal Ironman accounts can't contribute to Nobility.");
            return;
        }


        String replacedUser = "";

        ArrayList<Contribution> noblePlayers = NOBLES;

        if (player.getGameMode() == GameMode.IRONMAN)
            noblePlayers = NOBLES_IM;
        else if (player.getGameMode() == GameMode.HARDCORE_IRONMAN)
            noblePlayers = NOBLES_HCIM;
        else if (player.getGameMode() == GameMode.ULTIMATE_IRONMAN)
            noblePlayers = NOBLES_UIM;

        if (noblePlayers.size() == 25) {
            Contribution leastNoble = noblePlayers.get(noblePlayers.size() - 1);

            replacedUser = leastNoble.getUserName();

            if (leastNoble.getContribution() >= amount) {
                DialogueManager.sendStatement(player, "You must contribute more than " + Misc.currency(leastNoble.getContribution()) + " coins to unseat " + leastNoble.getUserName());

                return;
            }
        }

        boolean usePouch = player.getMoneyInPouch() >= amount;
        if (!usePouch && player.getInventory().getAmount(995) < amount) {
            DialogueManager.sendStatement(player, "You do not have that much money in your inventory or money pouch.");
            return;
        }
        if (usePouch) {
            player.setMoneyInPouch(player.getMoneyInPouch() - amount);
            player.getPacketSender().sendString(8135, "" + player.getMoneyInPouch());
        } else {
            player.getInventory().delete(995, (int) amount);
        }

        DialogueManager.sendStatement(player, "Thank you for your contribution to the Kingdom.");

        boolean replaced = updateUserData(player, amount);
        sortNobles(player.getGameMode());


        String discordLog = player.getUsername() + " contributed " + amount + " to the Kingdom.";
        if (replaced) {
            discordLog += "\n   and removed " + replacedUser + " from nobility.";
        }

        if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
            new MessageBuilder().append(discordLog).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.MISC_LOGS_CH).get());

    }

    private static boolean updateUserData(Player player, long newContributionValue) {
        String userName = player.getUsername();
        int currentRank = getRank(player);

        ArrayList<Contribution> noblePlayers = NOBLES;

        if (player.getGameMode() == GameMode.IRONMAN)
            noblePlayers = NOBLES_IM;
        else if (player.getGameMode() == GameMode.HARDCORE_IRONMAN)
            noblePlayers = NOBLES_HCIM;
        else if (player.getGameMode() == GameMode.ULTIMATE_IRONMAN)
            noblePlayers = NOBLES_UIM;

        // Check if the player is already noble
        if (currentRank > -1) {
            // If yes, update the existing value
            noblePlayers.get(currentRank).increaseContribution(newContributionValue);
        } else {
            // If no, add the new user name and value
            // Check if the user value is greater than the 25th highest value
            boolean replaced = noblePlayers.size() == 25;
            if (replaced) {
                Contribution leastNoble = noblePlayers.get(noblePlayers.size() - 1);

                // refund least noble
                MailBox.addCoinsToMoneyPouch(leastNoble.getUserName(), leastNoble.getContribution());
                noblePlayers.remove(leastNoble);
            }

            // add new noble
            noblePlayers.add(new Contribution(userName, newContributionValue));
            return replaced;
        }
        return false;
    }

    public static void removeContribution(Player player) {

        if(player == null)
            return;

        ArrayList<Contribution> noblePlayers = NOBLES;

        if (player.getGameMode() == GameMode.IRONMAN)
            noblePlayers = NOBLES_IM;
        else if (player.getGameMode() == GameMode.HARDCORE_IRONMAN)
            noblePlayers = NOBLES_HCIM;
        else if (player.getGameMode() == GameMode.ULTIMATE_IRONMAN)
            noblePlayers = NOBLES_UIM;

        Optional<Contribution> contribution = noblePlayers.stream().filter(c -> c.getUserName().equals(player.getUsername())).findFirst();

        if (contribution.isPresent()) {
            // refund removed player
            MailBox.addCoinsToMoneyPouch(player.getUsername(), contribution.get().getContribution());
            noblePlayers.remove(contribution.get());
            sortNobles(player.getGameMode());
        } else {
            // do we care?
        }
    }

    public static void openLineage(Player player, GameMode gameMode) {

        ArrayList<Contribution> noblePlayers = NOBLES;

        if (gameMode == GameMode.IRONMAN)
            noblePlayers = NOBLES_IM;
        else if (gameMode == GameMode.HARDCORE_IRONMAN)
            noblePlayers = NOBLES_HCIM;
        else if (gameMode == GameMode.ULTIMATE_IRONMAN)
            noblePlayers = NOBLES_UIM;

        int stringId = 6402;

        for (int i = 0; i < 50; stringId++, i++) {
            if (i == 10) {
                stringId = 8578;
            }

            String line = "";
            if (i < noblePlayers.size()) {
                Contribution noble = noblePlayers.get(i);
                if (noble != null) {
                    line = "@whi@Rank @or1@" + (i + 1) + "@whi@ - " + noble.getUserName().replaceAll("_", " ") + " - Contribution: @or1@" + Misc.insertCommasToNumber(noble.getContribution() + "");
                }
            } else
                line = "";

            player.getPacketSender().sendString(stringId, line);
        }

        player.getPacketSender().sendInterface(6308).sendString(6400, "Nobility System").sendString(6399, "").sendString(6401, "Close");
    }

    public static int getRank(Player player) {

        ArrayList<Contribution> noblePlayers = NOBLES;

        if (player.getGameMode() == GameMode.IRONMAN)
            noblePlayers = NOBLES_IM;
        else if (player.getGameMode() == GameMode.HARDCORE_IRONMAN)
            noblePlayers = NOBLES_HCIM;
        else if (player.getGameMode() == GameMode.ULTIMATE_IRONMAN)
            noblePlayers = NOBLES_UIM;

        for (int i = 0; i < noblePlayers.size(); i++) {
            if (player.getUsername().equals(noblePlayers.get(i).getUserName())) {
                return i;
            }
        }


        return -1;
    }

    public static double getNobilityBoost(Player player) {

        double boost = 0.00;

        if (getRank(player) >= 0 && getRank(player) <= 4)
            boost = .15;
        if (getRank(player) >= 5 && getRank(player) <= 14)
            boost = .10;
        if (getRank(player) >= 15 && getRank(player) <= 24)
            boost = .05;

        player.nobilityBoost = (int) (100 * boost);

        return boost;
    }

    private static void sortNobles(GameMode gameMode) {
        ArrayList<Contribution> noblePlayers = NOBLES;

        if (gameMode == GameMode.IRONMAN)
            noblePlayers = NOBLES_IM;
        else if (gameMode == GameMode.HARDCORE_IRONMAN)
            noblePlayers = NOBLES_HCIM;
        else if (gameMode == GameMode.ULTIMATE_IRONMAN)
            noblePlayers = NOBLES_UIM;

        noblePlayers.sort(Collections.reverseOrder(Comparator.comparing(Contribution::getContribution)));
    }

    public static void load() {

        //Normal
        if (!NOBILITY_SAVE_FILE.getParentFile().exists()) {
            try {
                NOBILITY_SAVE_FILE.getParentFile().mkdirs();
            } catch (SecurityException e) {
                GameServer.getLogger().log(Level.SEVERE, "Unable to create directory for POS data!", e);
            }
        }

        if (!NOBILITY_SAVE_FILE.exists()) {
            return;
        }

        try (FileReader fileReader = new FileReader(NOBILITY_SAVE_FILE)) {
            JsonObject reader = (JsonObject) JsonParser.parseReader(fileReader);

            if (reader.has("nobles_normal")) {
                reader.getAsJsonArray("nobles_normal").forEach(je -> {
                    JsonObject jo = je.getAsJsonObject();
                    NOBLES.add(new Contribution(jo.get("username").getAsString(), jo.get("contribution").getAsLong()));
                });
                sortNobles(GameMode.NORMAL);
            } else {
                GameServer.getLogger().severe(NOBILITY_SAVE_FILE.getAbsolutePath() + " does not contain \"nobles_normal\" attribute.");
            }

            if (reader.has("nobles_im")) {
                reader.getAsJsonArray("nobles_im").forEach(je -> {
                    JsonObject jo = je.getAsJsonObject();
                    NOBLES_IM.add(new Contribution(jo.get("username").getAsString(), jo.get("contribution").getAsLong()));
                });
                sortNobles(GameMode.IRONMAN);
            } else {
                GameServer.getLogger().severe(NOBILITY_SAVE_FILE.getAbsolutePath() + " does not contain \"nobles_normal\" attribute.");
            }

            if (reader.has("nobles_hcim")) {
                reader.getAsJsonArray("nobles_hcim").forEach(je -> {
                    JsonObject jo = je.getAsJsonObject();
                    NOBLES_HCIM.add(new Contribution(jo.get("username").getAsString(), jo.get("contribution").getAsLong()));
                });
                sortNobles(GameMode.HARDCORE_IRONMAN);
            } else {
                GameServer.getLogger().severe(NOBILITY_SAVE_FILE.getAbsolutePath() + " does not contain \"nobles_normal\" attribute.");
            }

            if (reader.has("nobles_uim")) {
                reader.getAsJsonArray("nobles_uim").forEach(je -> {
                    JsonObject jo = je.getAsJsonObject();
                    NOBLES_UIM.add(new Contribution(jo.get("username").getAsString(), jo.get("contribution").getAsLong()));
                });
                sortNobles(GameMode.ULTIMATE_IRONMAN);
            } else {
                GameServer.getLogger().severe(NOBILITY_SAVE_FILE.getAbsolutePath() + " does not contain \"nobles_normal\" attribute.");
            }

        } catch (Exception e) {
            GameServer.getLogger().log(Level.SEVERE, "ruh roh", e);
        }


    }

    public static void save() {
        NOBILITY_SAVE_FILE.getParentFile().setWritable(true);

        if (!NOBILITY_SAVE_FILE.getParentFile().exists()) {
            try {
                NOBILITY_SAVE_FILE.getParentFile().mkdirs();
            } catch (SecurityException e) {
                GameServer.getLogger().log(Level.SEVERE, "Unable to create directory for POS data!", e);
            }
        }

        try (FileWriter writer = new FileWriter(NOBILITY_SAVE_FILE)) {
            Gson builder = new GsonBuilder().setPrettyPrinting().create();
            JsonObject jo = new JsonObject();

            //Normal
            JsonArray normal = new JsonArray();
            for (Contribution contribution : NOBLES) {
                JsonObject contributionJson = new JsonObject();
                contributionJson.addProperty("username", contribution.getUserName());
                contributionJson.addProperty("contribution", contribution.getContribution());
                normal.add(contributionJson);
            }
            jo.add("nobles_normal", normal);

            //Ironman
            JsonArray ironman = new JsonArray();
            for (Contribution contribution : NOBLES_IM) {
                JsonObject contributionJson = new JsonObject();
                contributionJson.addProperty("username", contribution.getUserName());
                contributionJson.addProperty("contribution", contribution.getContribution());
                ironman.add(contributionJson);
            }
            jo.add("nobles_im", ironman);

            //Hardcore Ironman
            JsonArray hcim = new JsonArray();
            for (Contribution contribution : NOBLES_HCIM) {
                JsonObject contributionJson = new JsonObject();
                contributionJson.addProperty("username", contribution.getUserName());
                contributionJson.addProperty("contribution", contribution.getContribution());
                hcim.add(contributionJson);
            }
            jo.add("nobles_hcim", hcim);

            //Ultimate Ironman
            JsonArray uim = new JsonArray();
            for (Contribution contribution : NOBLES_UIM) {
                JsonObject contributionJson = new JsonObject();
                contributionJson.addProperty("username", contribution.getUserName());
                contributionJson.addProperty("contribution", contribution.getContribution());
                uim.add(contributionJson);
            }
            jo.add("nobles_uim", uim);

            writer.write(builder.toJson(jo));
        } catch (IOException e) {
            GameServer.getLogger().log(Level.SEVERE, "ruh roh", e);
        }
    }

@Data
static class Contribution {
    String userName;
    long contribution;

    public Contribution(String userName, long contribution) {
        this.userName = userName;
        this.contribution = contribution;
    }

    public void increaseContribution(long increase) {
        contribution += increase;
    }
}
}
