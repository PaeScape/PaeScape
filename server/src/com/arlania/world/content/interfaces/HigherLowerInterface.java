package com.arlania.world.content.interfaces;

import com.arlania.GameSettings;
import com.arlania.util.RandomUtility;
import com.arlania.world.entity.impl.player.Player;

import java.text.NumberFormat;

/**
 * This packet listener manages a button that the player has clicked upon.
 *
 * @author Gabriel Hannason
 */

public class HigherLowerInterface {

    public static void showInterface(Player player) {

        NumberFormat myFormat = NumberFormat.getInstance();
        myFormat.setGroupingUsed(true);

        player.getPacketSender().sendInterface(8714); //interface

        player.getPacketSender().sendString(8812, "Close Window"); //Button header
        player.getPacketSender().sendString(8716, "Higher / Lower"); //title header
        player.getPacketSender().sendString(8718, ""); //1st column header
        player.getPacketSender().sendString(8719, ""); //2nd column header

        player.getPacketSender().sendString(8846, "@gre@Higher"); //1st Button
        player.getPacketSender().sendString(8823, "@red@Lower"); //2nd Button
        player.getPacketSender().sendString(8824, ""); //3rd Button
        player.getPacketSender().sendString(8827, "@blu@Collect"); //4th Button
        player.getPacketSender().sendString(8837, ""); //5th Button
        player.getPacketSender().sendString(8840, ""); //6th Button
        player.getPacketSender().sendString(8843, ""); //7th Button
        player.getPacketSender().sendString(8859, ""); //8th Button
        player.getPacketSender().sendString(8862, ""); //9th Button
        player.getPacketSender().sendString(8865, ""); //10th Button
        player.getPacketSender().sendString(15303, ""); //11th Button
        player.getPacketSender().sendString(15306, ""); //12th Button
        player.getPacketSender().sendString(15309, ""); //13th Button

        int string = 8760;
        int cost = 8720;


        player.getPacketSender().sendString(string++, "The current number is: " + player.currentHL); //
        player.getPacketSender().sendString(string++, "The previous number was: " + player.previousHL); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, "Your current bet is: " + myFormat.format(player.betHL)); //
        player.getPacketSender().sendString(string++, "Prize pool: " + myFormat.format(player.prizeHL)); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, ""); //
        player.getPacketSender().sendString(string++, ""); //

        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //
        player.getPacketSender().sendString(cost++, ""); //


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


        NumberFormat myFormat = NumberFormat.getInstance();
        myFormat.setGroupingUsed(true);

        switch (button) {

            case 8846: //higher

                if (player.currentHL == 0)
                    player.currentHL = 7;

                if (player.betHL == 0) {
                    player.getPacketSender().sendMessage("You can place bets in the quest tab interface!");
                    return;
                } else if (player.betHL > player.getMoneyInPouch() && player.prizeHL == 0) {
                    player.getPacketSender().sendMessage("@red@You don't have enough in your money pouch to cover your Higher/Lower bets.");
                    return;
                } else if (player.prizeHL == 0) {
                    player.setMoneyInPouch(player.getMoneyInPouch() - player.betHL);
                    player.getPacketSender().sendMessage("@or2@You place a total bet of " + myFormat.format(player.betHL));
                    GameSettings.progressiveJackpot += player.betHL / 100;
                }


                player.previousHL = player.currentHL;

                player.currentHL = RandomUtility.inclusiveRandom(1, 13);

                if (player.currentHL > player.previousHL) {

                    if (player.prizeHL > 0)
                        player.prizeHL *= 2;
                    else
                        player.prizeHL = player.betHL;

                    player.getPacketSender().sendMessage("@gre@You were correct!");
                    player.getPacketSender().sendMessage("@blu@The current prize pool is: @gre@" + myFormat.format(player.prizeHL));
                    showInterface(player);

                } else {
                    player.getPacketSender().sendMessage("@red@You were incorrect!");
                    player.prizeHL = 0;
                    player.currentHL = 7;
                    showInterface(player);

                }
                break;

            case 8823: //lower

                if (player.currentHL == 0)
                    player.currentHL = 7;

                if (player.betHL == 0) {
                    player.getPacketSender().sendMessage("You can place bets in the quest tab interface!");
                    return;
                } else if (player.betHL > player.getMoneyInPouch() && player.prizeHL == 0) {
                    player.getPacketSender().sendMessage("@red@You don't have enough in your money pouch to cover your Higher/Lower bets.");
                    return;
                } else if (player.prizeHL == 0) {
                    player.setMoneyInPouch(player.getMoneyInPouch() - player.betHL);
                    player.getPacketSender().sendMessage("@or2@You place a total bet of " + myFormat.format(player.betHL));
                    GameSettings.progressiveJackpot += player.betHL / 100;
                }


                player.previousHL = player.currentHL;

                player.currentHL = RandomUtility.inclusiveRandom(1, 13);

                if (player.currentHL < player.previousHL) {

                    if (player.prizeHL > 0)
                        player.prizeHL *= 2;
                    else
                        player.prizeHL = player.betHL;

                    player.getPacketSender().sendMessage("@gre@You were correct!");
                    player.getPacketSender().sendMessage("@blu@The current prize pool is: @gre@" + myFormat.format(player.prizeHL));

                    showInterface(player);

                } else {
                    player.getPacketSender().sendMessage("@red@You were incorrect!");
                    player.prizeHL = 0;
                    player.currentHL = 7;
                    showInterface(player);

                }
                break;

            case 8824:

                break;

            case 8827: //collect

                player.setMoneyInPouch(player.getMoneyInPouch() + player.prizeHL);
                player.currentHL = 7;
                player.prizeHL = 0;
                showInterface(player);
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

                break;


        }
        player.getPacketSender().sendString(8135, "" + player.getMoneyInPouch());
    }
}
