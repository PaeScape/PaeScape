package com.arlania.world.content;


import com.arlania.DiscordBot;
import com.arlania.GameSettings;
import com.arlania.model.Animation;
import com.arlania.model.Graphic;
import com.arlania.model.GraphicHeight;
import com.arlania.model.Position;
import com.arlania.util.RandomUtility;
import com.arlania.world.content.interfaces.HigherLowerInterface;
import com.arlania.world.entity.impl.player.Player;
import org.javacord.api.entity.message.MessageBuilder;

import java.text.NumberFormat;

public class Casino {


    public static void poker(Player player) {
        player.getPacketSender().sendMessage("@gre@Poker coming soon!");
    }

    public static void blackjack(Player player) {
        player.getPacketSender().sendMessage("@gre@Poker coming soon!");
    }

    public static void higherLower(Player player) {

        player.performAnimation(new Animation(1351));


        HigherLowerInterface.showInterface(player);


    }


    public static void roulette(Player player) {

        player.performAnimation(new Animation(1351));

        NumberFormat myFormat = NumberFormat.getInstance();
        myFormat.setGroupingUsed(true);

        int totalBet = player.betRoulette0 + player.betRoulette00 + player.betRoulette1 + player.betRoulette2 + player.betRoulette3 + player.betRoulette4 +
                player.betRoulette5 + player.betRoulette6 + player.betRoulette7 + player.betRoulette8 + player.betRoulette9 + player.betRoulette10 +
                player.betRoulette11 + player.betRoulette12 + player.betRoulette13 + player.betRoulette14 + player.betRoulette15 + player.betRoulette16 +
                player.betRoulette17 + player.betRoulette18 + player.betRoulette19 + player.betRoulette20 + player.betRoulette21 + player.betRoulette22 +
                player.betRoulette23 + player.betRoulette24 + player.betRoulette25 + player.betRoulette26 + player.betRoulette27 + player.betRoulette28 +
                player.betRoulette29 + player.betRoulette30 + player.betRoulette31 + player.betRoulette32 + player.betRoulette33 + player.betRoulette34 +
                player.betRoulette35 + player.betRoulette36 + player.betRouletteRed + player.betRouletteBlack;

        if (totalBet == 0) {
            player.getPacketSender().sendMessage("You can place bets in the quest tab interface!");
            return;
        } else if (totalBet > player.getMoneyInPouch()) {
            player.getPacketSender().sendMessage("@red@You don't have enough in your money pouch to cover your Roulette bets.");
            return;
        } else {
            player.setMoneyInPouch(player.getMoneyInPouch() - totalBet);
            player.getPacketSender().sendMessage("@or2@You place a total bet of " + myFormat.format(totalBet));
            GameSettings.progressiveJackpot += totalBet / 100;

            String discordMessage = player.getUsername() + " has placed a bet of " + myFormat.format(totalBet);

            if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.GAMBLING_LOGS_CH).get());
        }

        Position spinningBall = player.getInteractingObject().getPosition();
        spinningBall.add(1, 1);
        player.getPacketSender().sendGlobalGraphic(new Graphic(1356, GraphicHeight.HIGH), spinningBall);

        int[] numbers = {100, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36};
        String inside = "";
        int[] colors = {0, 0, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 2, 1, 2, 1, 2, 1, 2, 1, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 2, 1, 2, 1, 2, 1, 2, 1};
        String outside = "";


        int randSpin = RandomUtility.inclusiveRandom(numbers.length - 1);
        int winnings = 0;


        if (randSpin == 0)
            inside = "00";
        else
            inside = String.valueOf(numbers[randSpin]);

        outside = String.valueOf(colors[randSpin]);


        switch (outside) {

            case "0":
                outside = "Green";
                break;
            case "1":
                outside = "Red";
                break;
            case "2":
                outside = "Black";
                break;
        }

        String color = outside;

        switch (color) {
            case "Green":
                color = "@gre@";
                break;
            case "Red":
                color = "@red@";
                break;
            case "Black":
                color = "@bla@";
                break;
        }


        player.getPacketSender().sendMessage("@blu@The winning number is: " + color + inside);


        switch (outside) {

            case "Green":
                break;
            case "Red":
                if (player.betRouletteRed > 0) {
                    winnings = player.betRouletteRed * 2;
                    player.setMoneyInPouch(player.getMoneyInPouch() + winnings);
                    player.getPacketSender().sendMessage("@gre@You've won " + myFormat.format(winnings) + " coins!");
                    player.forceChat(" just won " + myFormat.format(winnings) + " coins");

                    String discordMessage = player.getUsername() + " has won " + myFormat.format(winnings) + " coins";

                    if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                        new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.GAMBLING_LOGS_CH).get());
                }
                break;
            case "Black":
                if (player.betRouletteBlack > 0) {
                    winnings = player.betRouletteBlack * 2;
                    player.setMoneyInPouch(player.getMoneyInPouch() + winnings);
                    player.getPacketSender().sendMessage("@gre@You've won " + myFormat.format(winnings) + " coins!");
                    player.forceChat(" just won " + myFormat.format(winnings) + " coins");

                    String discordMessage = player.getUsername() + " has won " + myFormat.format(winnings) + " coins";

                    if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                        new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.GAMBLING_LOGS_CH).get());
                }
                break;

        }


        switch (inside) {

            case "00":
                if (player.betRoulette00 > 0) {
                    winnings = player.betRoulette00 * 36;
                    player.setMoneyInPouch(player.getMoneyInPouch() + winnings);
                    player.getPacketSender().sendMessage("@gre@You've won " + myFormat.format(winnings) + " coins!");
                    player.forceChat(" just won " + myFormat.format(winnings) + " coins");

                    String discordMessage = player.getUsername() + " has won " + myFormat.format(winnings) + " coins";

                    if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                        new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.GAMBLING_LOGS_CH).get());
                }

                break;
            case "0":
                if (player.betRoulette0 > 0) {
                    winnings = player.betRoulette0 * 36;
                    player.setMoneyInPouch(player.getMoneyInPouch() + winnings);
                    player.getPacketSender().sendMessage("@gre@You've won " + myFormat.format(winnings) + " coins!");
                    player.forceChat(" just won " + myFormat.format(winnings) + " coins");

                    String discordMessage = player.getUsername() + " has won " + myFormat.format(winnings) + " coins";

                    if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                        new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.GAMBLING_LOGS_CH).get());
                }
                break;
            case "1":
                if (player.betRoulette1 > 0) {
                    winnings = player.betRoulette1 * 36;
                    player.setMoneyInPouch(player.getMoneyInPouch() + winnings);
                    player.getPacketSender().sendMessage("@gre@You've won " + myFormat.format(winnings) + " coins!");
                    player.forceChat(" just won " + myFormat.format(winnings) + " coins");

                    String discordMessage = player.getUsername() + " has won " + myFormat.format(winnings) + " coins";

                    if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                        new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.GAMBLING_LOGS_CH).get());
                }
                break;
            case "2":
                if (player.betRoulette2 > 0) {
                    winnings = player.betRoulette2 * 36;
                    player.setMoneyInPouch(player.getMoneyInPouch() + winnings);
                    player.getPacketSender().sendMessage("@gre@You've won " + myFormat.format(winnings) + " coins!");
                    player.forceChat(" just won " + myFormat.format(winnings) + " coins");

                    String discordMessage = player.getUsername() + " has won " + myFormat.format(winnings) + " coins";

                    if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                        new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.GAMBLING_LOGS_CH).get());
                }
                break;
            case "3":
                if (player.betRoulette3 > 0) {
                    winnings = player.betRoulette3 * 36;
                    player.setMoneyInPouch(player.getMoneyInPouch() + winnings);
                    player.getPacketSender().sendMessage("@gre@You've won " + myFormat.format(winnings) + " coins!");
                    player.forceChat(" just won " + myFormat.format(winnings) + " coins");

                    String discordMessage = player.getUsername() + " has won " + myFormat.format(winnings) + " coins";

                    if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                        new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.GAMBLING_LOGS_CH).get());
                }
                break;
            case "4":
                if (player.betRoulette4 > 0) {
                    winnings = player.betRoulette4 * 36;
                    player.setMoneyInPouch(player.getMoneyInPouch() + winnings);
                    player.getPacketSender().sendMessage("@gre@You've won " + myFormat.format(winnings) + " coins!");
                    player.forceChat(" just won " + myFormat.format(winnings) + " coins");

                    String discordMessage = player.getUsername() + " has won " + myFormat.format(winnings) + " coins";

                    if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                        new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.GAMBLING_LOGS_CH).get());
                }
                break;
            case "5":
                if (player.betRoulette5 > 0) {
                    winnings = player.betRoulette5 * 36;
                    player.setMoneyInPouch(player.getMoneyInPouch() + winnings);
                    player.getPacketSender().sendMessage("@gre@You've won " + myFormat.format(winnings) + " coins!");
                    player.forceChat(" just won " + myFormat.format(winnings) + " coins");

                    String discordMessage = player.getUsername() + " has won " + myFormat.format(winnings) + " coins";

                    if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                        new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.GAMBLING_LOGS_CH).get());
                }
                break;
            case "6":
                if (player.betRoulette6 > 0) {
                    winnings = player.betRoulette6 * 36;
                    player.setMoneyInPouch(player.getMoneyInPouch() + winnings);
                    player.getPacketSender().sendMessage("@gre@You've won " + myFormat.format(winnings) + " coins!");
                    player.forceChat(" just won " + myFormat.format(winnings) + " coins");

                    String discordMessage = player.getUsername() + " has won " + myFormat.format(winnings) + " coins";

                    if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                        new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.GAMBLING_LOGS_CH).get());
                }
                break;
            case "7":
                if (player.betRoulette7 > 0) {
                    winnings = player.betRoulette7 * 36;
                    player.setMoneyInPouch(player.getMoneyInPouch() + winnings);
                    player.getPacketSender().sendMessage("@gre@You've won " + myFormat.format(winnings) + " coins!");
                    player.forceChat(" just won " + myFormat.format(winnings) + " coins");

                    String discordMessage = player.getUsername() + " has won " + myFormat.format(winnings) + " coins";

                    if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                        new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.GAMBLING_LOGS_CH).get());
                }
                break;
            case "8":
                if (player.betRoulette8 > 0) {
                    winnings = player.betRoulette8 * 36;
                    player.setMoneyInPouch(player.getMoneyInPouch() + winnings);
                    player.getPacketSender().sendMessage("@gre@You've won " + myFormat.format(winnings) + " coins!");
                    player.forceChat(" just won " + myFormat.format(winnings) + " coins");

                    String discordMessage = player.getUsername() + " has won " + myFormat.format(winnings) + " coins";

                    if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                        new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.GAMBLING_LOGS_CH).get());
                }
                break;
            case "9":
                if (player.betRoulette9 > 0) {
                    winnings = player.betRoulette9 * 36;
                    player.setMoneyInPouch(player.getMoneyInPouch() + winnings);
                    player.getPacketSender().sendMessage("@gre@You've won " + myFormat.format(winnings) + " coins!");
                    player.forceChat(" just won " + myFormat.format(winnings) + " coins");

                    String discordMessage = player.getUsername() + " has won " + myFormat.format(winnings) + " coins";

                    if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                        new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.GAMBLING_LOGS_CH).get());
                }
                break;
            case "10":
                if (player.betRoulette10 > 0) {
                    winnings = player.betRoulette10 * 36;
                    player.setMoneyInPouch(player.getMoneyInPouch() + winnings);
                    player.getPacketSender().sendMessage("@gre@You've won " + myFormat.format(winnings) + " coins!");
                    player.forceChat(" just won " + myFormat.format(winnings) + " coins");

                    String discordMessage = player.getUsername() + " has won " + myFormat.format(winnings) + " coins";

                    if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                        new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.GAMBLING_LOGS_CH).get());
                }
                break;
            case "11":
                if (player.betRoulette11 > 0) {
                    winnings = player.betRoulette11 * 36;
                    player.setMoneyInPouch(player.getMoneyInPouch() + winnings);
                    player.getPacketSender().sendMessage("@gre@You've won " + myFormat.format(winnings) + " coins!");
                    player.forceChat(" just won " + myFormat.format(winnings) + " coins");

                    String discordMessage = player.getUsername() + " has won " + myFormat.format(winnings) + " coins";

                    if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                        new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.GAMBLING_LOGS_CH).get());
                }
                break;
            case "12":
                if (player.betRoulette12 > 0) {
                    winnings = player.betRoulette12 * 36;
                    player.setMoneyInPouch(player.getMoneyInPouch() + winnings);
                    player.getPacketSender().sendMessage("@gre@You've won " + myFormat.format(winnings) + " coins!");
                    player.forceChat(" just won " + myFormat.format(winnings) + " coins");

                    String discordMessage = player.getUsername() + " has won " + myFormat.format(winnings) + " coins";

                    if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                        new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.GAMBLING_LOGS_CH).get());
                }
                break;
            case "13":
                if (player.betRoulette13 > 0) {
                    winnings = player.betRoulette13 * 36;
                    player.setMoneyInPouch(player.getMoneyInPouch() + winnings);
                    player.getPacketSender().sendMessage("@gre@You've won " + myFormat.format(winnings) + " coins!");
                    player.forceChat(" just won " + myFormat.format(winnings) + " coins");

                    String discordMessage = player.getUsername() + " has won " + myFormat.format(winnings) + " coins";

                    if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                        new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.GAMBLING_LOGS_CH).get());
                }
                break;
            case "14":
                if (player.betRoulette14 > 0) {
                    winnings = player.betRoulette14 * 36;
                    player.setMoneyInPouch(player.getMoneyInPouch() + winnings);
                    player.getPacketSender().sendMessage("@gre@You've won " + myFormat.format(winnings) + " coins!");
                    player.forceChat(" just won " + myFormat.format(winnings) + " coins");

                    String discordMessage = player.getUsername() + " has won " + myFormat.format(winnings) + " coins";

                    if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                        new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.GAMBLING_LOGS_CH).get());
                }
                break;
            case "15":
                if (player.betRoulette15 > 0) {
                    winnings = player.betRoulette15 * 36;
                    player.setMoneyInPouch(player.getMoneyInPouch() + winnings);
                    player.getPacketSender().sendMessage("@gre@You've won " + myFormat.format(winnings) + " coins!");
                    player.forceChat(" just won " + myFormat.format(winnings) + " coins");

                    String discordMessage = player.getUsername() + " has won " + myFormat.format(winnings) + " coins";

                    if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                        new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.GAMBLING_LOGS_CH).get());
                }
                break;
            case "16":
                if (player.betRoulette16 > 0) {
                    winnings = player.betRoulette16 * 36;
                    player.setMoneyInPouch(player.getMoneyInPouch() + winnings);
                    player.getPacketSender().sendMessage("@gre@You've won " + myFormat.format(winnings) + " coins!");
                    player.forceChat(" just won " + myFormat.format(winnings) + " coins");

                    String discordMessage = player.getUsername() + " has won " + myFormat.format(winnings) + " coins";

                    if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                        new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.GAMBLING_LOGS_CH).get());
                }
                break;
            case "17":
                if (player.betRoulette17 > 0) {
                    winnings = player.betRoulette17 * 36;
                    player.setMoneyInPouch(player.getMoneyInPouch() + winnings);
                    player.getPacketSender().sendMessage("@gre@You've won " + myFormat.format(winnings) + " coins!");
                    player.forceChat(" just won " + myFormat.format(winnings) + " coins");

                    String discordMessage = player.getUsername() + " has won " + myFormat.format(winnings) + " coins";

                    if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                        new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.GAMBLING_LOGS_CH).get());
                }
                break;
            case "18":
                if (player.betRoulette18 > 0) {
                    winnings = player.betRoulette18 * 36;
                    player.setMoneyInPouch(player.getMoneyInPouch() + winnings);
                    player.getPacketSender().sendMessage("@gre@You've won " + myFormat.format(winnings) + " coins!");
                    player.forceChat(" just won " + myFormat.format(winnings) + " coins");

                    String discordMessage = player.getUsername() + " has won " + myFormat.format(winnings) + " coins";

                    if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                        new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.GAMBLING_LOGS_CH).get());
                }
                break;
            case "19":
                if (player.betRoulette19 > 0) {
                    winnings = player.betRoulette19 * 36;
                    player.setMoneyInPouch(player.getMoneyInPouch() + winnings);
                    player.getPacketSender().sendMessage("@gre@You've won " + myFormat.format(winnings) + " coins!");
                    player.forceChat(" just won " + myFormat.format(winnings) + " coins");

                    String discordMessage = player.getUsername() + " has won " + myFormat.format(winnings) + " coins";

                    if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                        new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.GAMBLING_LOGS_CH).get());
                }
                break;
            case "20":
                if (player.betRoulette20 > 0) {
                    winnings = player.betRoulette20 * 36;
                    player.setMoneyInPouch(player.getMoneyInPouch() + winnings);
                    player.getPacketSender().sendMessage("@gre@You've won " + myFormat.format(winnings) + " coins!");
                    player.forceChat(" just won " + myFormat.format(winnings) + " coins");

                    String discordMessage = player.getUsername() + " has won " + myFormat.format(winnings) + " coins";

                    if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                        new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.GAMBLING_LOGS_CH).get());
                }
                break;
            case "21":
                if (player.betRoulette21 > 0) {
                    winnings = player.betRoulette21 * 36;
                    player.setMoneyInPouch(player.getMoneyInPouch() + winnings);
                    player.getPacketSender().sendMessage("@gre@You've won " + myFormat.format(winnings) + " coins!");
                    player.forceChat(" just won " + myFormat.format(winnings) + " coins");

                    String discordMessage = player.getUsername() + " has won " + myFormat.format(winnings) + " coins";

                    if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                        new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.GAMBLING_LOGS_CH).get());
                }
                break;
            case "22":
                if (player.betRoulette22 > 0) {
                    winnings = player.betRoulette22 * 36;
                    player.setMoneyInPouch(player.getMoneyInPouch() + winnings);
                    player.getPacketSender().sendMessage("@gre@You've won " + myFormat.format(winnings) + " coins!");
                    player.forceChat(" just won " + myFormat.format(winnings) + " coins");

                    String discordMessage = player.getUsername() + " has won " + myFormat.format(winnings) + " coins";

                    if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                        new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.GAMBLING_LOGS_CH).get());
                }
                break;
            case "23":
                if (player.betRoulette23 > 0) {
                    winnings = player.betRoulette23 * 36;
                    player.setMoneyInPouch(player.getMoneyInPouch() + winnings);
                    player.getPacketSender().sendMessage("@gre@You've won " + myFormat.format(winnings) + " coins!");
                    player.forceChat(" just won " + myFormat.format(winnings) + " coins");

                    String discordMessage = player.getUsername() + " has won " + myFormat.format(winnings) + " coins";

                    if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                        new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.GAMBLING_LOGS_CH).get());
                }
                break;
            case "24":
                if (player.betRoulette24 > 0) {
                    winnings = player.betRoulette24 * 36;
                    player.setMoneyInPouch(player.getMoneyInPouch() + winnings);
                    player.getPacketSender().sendMessage("@gre@You've won " + myFormat.format(winnings) + " coins!");
                    player.forceChat(" just won " + myFormat.format(winnings) + " coins");

                    String discordMessage = player.getUsername() + " has won " + myFormat.format(winnings) + " coins";

                    if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                        new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.GAMBLING_LOGS_CH).get());
                }
                break;
            case "25":
                if (player.betRoulette25 > 0) {
                    winnings = player.betRoulette25 * 36;
                    player.setMoneyInPouch(player.getMoneyInPouch() + winnings);
                    player.getPacketSender().sendMessage("@gre@You've won " + myFormat.format(winnings) + " coins!");
                    player.forceChat(" just won " + myFormat.format(winnings) + " coins");

                    String discordMessage = player.getUsername() + " has won " + myFormat.format(winnings) + " coins";

                    if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                        new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.GAMBLING_LOGS_CH).get());
                }
                break;
            case "26":
                if (player.betRoulette26 > 0) {
                    winnings = player.betRoulette26 * 36;
                    player.setMoneyInPouch(player.getMoneyInPouch() + winnings);
                    player.getPacketSender().sendMessage("@gre@You've won " + myFormat.format(winnings) + " coins!");
                    player.forceChat(" just won " + myFormat.format(winnings) + " coins");

                    String discordMessage = player.getUsername() + " has won " + myFormat.format(winnings) + " coins";

                    if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                        new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.GAMBLING_LOGS_CH).get());
                }
                break;
            case "27":
                if (player.betRoulette27 > 0) {
                    winnings = player.betRoulette27 * 36;
                    player.setMoneyInPouch(player.getMoneyInPouch() + winnings);
                    player.getPacketSender().sendMessage("@gre@You've won " + myFormat.format(winnings) + " coins!");
                    player.forceChat(" just won " + myFormat.format(winnings) + " coins");

                    String discordMessage = player.getUsername() + " has won " + myFormat.format(winnings) + " coins";

                    if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                        new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.GAMBLING_LOGS_CH).get());
                }
                break;
            case "28":
                if (player.betRoulette28 > 0) {
                    winnings = player.betRoulette28 * 36;
                    player.setMoneyInPouch(player.getMoneyInPouch() + winnings);
                    player.getPacketSender().sendMessage("@gre@You've won " + myFormat.format(winnings) + " coins!");
                    player.forceChat(" just won " + myFormat.format(winnings) + " coins");

                    String discordMessage = player.getUsername() + " has won " + myFormat.format(winnings) + " coins";

                    if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                        new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.GAMBLING_LOGS_CH).get());
                }
                break;
            case "29":
                if (player.betRoulette29 > 0) {
                    winnings = player.betRoulette29 * 36;
                    player.setMoneyInPouch(player.getMoneyInPouch() + winnings);
                    player.getPacketSender().sendMessage("@gre@You've won " + myFormat.format(winnings) + " coins!");
                    player.forceChat(" just won " + myFormat.format(winnings) + " coins");

                    String discordMessage = player.getUsername() + " has won " + myFormat.format(winnings) + " coins";

                    if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                        new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.GAMBLING_LOGS_CH).get());
                }
                break;
            case "30":
                if (player.betRoulette30 > 0) {
                    winnings = player.betRoulette30 * 36;
                    player.setMoneyInPouch(player.getMoneyInPouch() + winnings);
                    player.getPacketSender().sendMessage("@gre@You've won " + myFormat.format(winnings) + " coins!");
                    player.forceChat(" just won " + myFormat.format(winnings) + " coins");

                    String discordMessage = player.getUsername() + " has won " + myFormat.format(winnings) + " coins";

                    if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                        new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.GAMBLING_LOGS_CH).get());
                }
                break;
            case "31":
                if (player.betRoulette31 > 0) {
                    winnings = player.betRoulette31 * 36;
                    player.setMoneyInPouch(player.getMoneyInPouch() + winnings);
                    player.getPacketSender().sendMessage("@gre@You've won " + myFormat.format(winnings) + " coins!");
                    player.forceChat(" just won " + myFormat.format(winnings) + " coins");

                    String discordMessage = player.getUsername() + " has won " + myFormat.format(winnings) + " coins";

                    if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                        new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.GAMBLING_LOGS_CH).get());
                }
                break;
            case "32":
                if (player.betRoulette32 > 0) {
                    winnings = player.betRoulette32 * 36;
                    player.setMoneyInPouch(player.getMoneyInPouch() + winnings);
                    player.getPacketSender().sendMessage("@gre@You've won " + myFormat.format(winnings) + " coins!");
                    player.forceChat(" just won " + myFormat.format(winnings) + " coins");

                    String discordMessage = player.getUsername() + " has won " + myFormat.format(winnings) + " coins";

                    if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                        new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.GAMBLING_LOGS_CH).get());
                }
                break;
            case "33":
                if (player.betRoulette33 > 0) {
                    winnings = player.betRoulette33 * 36;
                    player.setMoneyInPouch(player.getMoneyInPouch() + winnings);
                    player.getPacketSender().sendMessage("@gre@You've won " + myFormat.format(winnings) + " coins!");
                    player.forceChat(" just won " + myFormat.format(winnings) + " coins");

                    String discordMessage = player.getUsername() + " has won " + myFormat.format(winnings) + " coins";

                    if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                        new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.GAMBLING_LOGS_CH).get());
                }
                break;
            case "34":
                if (player.betRoulette34 > 0) {
                    winnings = player.betRoulette34 * 36;
                    player.setMoneyInPouch(player.getMoneyInPouch() + winnings);
                    player.getPacketSender().sendMessage("@gre@You've won " + myFormat.format(winnings) + " coins!");
                    player.forceChat(" just won " + myFormat.format(winnings) + " coins");

                    String discordMessage = player.getUsername() + " has won " + myFormat.format(winnings) + " coins";

                    if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                        new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.GAMBLING_LOGS_CH).get());
                }
                break;
            case "35":
                if (player.betRoulette35 > 0) {
                    winnings = player.betRoulette35 * 36;
                    player.setMoneyInPouch(player.getMoneyInPouch() + winnings);
                    player.getPacketSender().sendMessage("@gre@You've won " + myFormat.format(winnings) + " coins!");
                    player.forceChat(" just won " + myFormat.format(winnings) + " coins");

                    String discordMessage = player.getUsername() + " has won " + myFormat.format(winnings) + " coins";

                    if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                        new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.GAMBLING_LOGS_CH).get());
                }
                break;
            case "36":
                if (player.betRoulette36 > 0) {
                    winnings = player.betRoulette36 * 36;
                    player.setMoneyInPouch(player.getMoneyInPouch() + winnings);
                    player.getPacketSender().sendMessage("@gre@You've won " + myFormat.format(winnings) + " coins!");
                    player.forceChat(" just won " + myFormat.format(winnings) + " coins");

                    String discordMessage = player.getUsername() + " has won " + myFormat.format(winnings) + " coins";

                    if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                        new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.GAMBLING_LOGS_CH).get());
                }
                break;


        }


        player.getPacketSender().sendString(8135, "" + player.getMoneyInPouch());

    }


}
