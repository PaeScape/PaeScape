package com.arlania.world.content.interfaces;

import com.arlania.GameSettings;
import com.arlania.world.entity.impl.player.Player;

import java.text.NumberFormat;

public class GambleInterface {

    public static final String LINE_START = "   > ";

    public static void refreshPanel(Player player) {

        NumberFormat myFormat = NumberFormat.getInstance();
        myFormat.setGroupingUsed(true);


        int counter = 39159;
        player.getPacketSender().sendString(counter++, "@gre@   Progressive: @gre@" + myFormat.format(GameSettings.progressiveJackpot)); //

        player.getPacketSender().sendString(counter++, "@gre@   Coin Pouch: @gre@" + myFormat.format(player.getMoneyInPouch())); //

        player.getPacketSender().sendString(counter++, "@or3@-@whi@ Higher/Lower");

        if (player.betHL > 0)
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet: @gre@" + myFormat.format(player.betHL));
        else
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet: @red@" + player.betHL);


        player.getPacketSender().sendString(counter++, "");

        player.getPacketSender().sendString(counter++, "@or3@-@whi@ Blackjack");

        if (player.betBlackjack > 0)
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet: @gre@" + myFormat.format(player.betBlackjack));
        else
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet: @red@" + player.betBlackjack);


        player.getPacketSender().sendString(counter++, "");

        player.getPacketSender().sendString(counter++, "@or3@-@whi@ Roulette");

        if (player.betRouletteBlack == 0)
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet Black: " + "@red@0");
        else
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet Black: " + "@gre@" + myFormat.format(player.betRouletteBlack));

        if (player.betRouletteRed == 0)
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet Red: " + "@red@0");
        else
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet Red: " + "@gre@" + myFormat.format(player.betRouletteRed));


        if (player.betRoulette0 == 0)
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet 0: " + "@red@0");
        else
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet 0: " + "@gre@" + myFormat.format(player.betRoulette0));

        if (player.betRoulette00 == 0)
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet 00: " + "@red@0");
        else
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet 00: " + "@gre@" + myFormat.format(player.betRoulette00));

        if (player.betRoulette1 == 0)
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet 1: " + "@red@0");
        else
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet 1: " + "@gre@" + myFormat.format(player.betRoulette1));

        if (player.betRoulette2 == 0)
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet 2: " + "@red@0");
        else
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet 2: " + "@gre@" + myFormat.format(player.betRoulette2));

        if (player.betRoulette3 == 0)
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet 3: " + "@red@0");
        else
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet 3: " + "@gre@" + myFormat.format(player.betRoulette3));

        if (player.betRoulette4 == 0)
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet 4: " + "@red@0");
        else
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet 4: " + "@gre@" + myFormat.format(player.betRoulette4));

        if (player.betRoulette5 == 0)
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet 5: " + "@red@0");
        else
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet 5: " + "@gre@" + myFormat.format(player.betRoulette5));

        if (player.betRoulette6 == 0)
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet 6: " + "@red@0");
        else
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet 6: " + "@gre@" + myFormat.format(player.betRoulette6));

        if (player.betRoulette7 == 0)
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet 7: " + "@red@0");
        else
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet 7: " + "@gre@" + myFormat.format(player.betRoulette7));

        if (player.betRoulette8 == 0)
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet 8: " + "@red@0");
        else
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet 8: " + "@gre@" + myFormat.format(player.betRoulette8));

        if (player.betRoulette9 == 0)
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet 9: " + "@red@0");
        else
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet 9: " + "@gre@" + myFormat.format(player.betRoulette9));

        if (player.betRoulette10 == 0)
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet 10: " + "@red@0");
        else
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet 10: " + "@gre@" + myFormat.format(player.betRoulette10));

        if (player.betRoulette11 == 0)
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet 11: " + "@red@0");
        else
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet 11: " + "@gre@" + myFormat.format(player.betRoulette11));

        if (player.betRoulette12 == 0)
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet 12: " + "@red@0");
        else
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet 12: " + "@gre@" + myFormat.format(player.betRoulette12));

        if (player.betRoulette13 == 0)
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet 13: " + "@red@0");
        else
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet 13: " + "@gre@" + myFormat.format(player.betRoulette13));

        if (player.betRoulette14 == 0)
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet 14: " + "@red@0");
        else
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet 14: " + "@gre@" + myFormat.format(player.betRoulette14));

        if (player.betRoulette15 == 0)
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet 15: " + "@red@0");
        else
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet 15: " + "@gre@" + myFormat.format(player.betRoulette15));

        if (player.betRoulette16 == 0)
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet 16: " + "@red@0");
        else
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet 16: " + "@gre@" + myFormat.format(player.betRoulette16));

        if (player.betRoulette17 == 0)
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet 17: " + "@red@0");
        else
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet 17: " + "@gre@" + myFormat.format(player.betRoulette17));

        if (player.betRoulette18 == 0)
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet 18: " + "@red@0");
        else
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet 18: " + "@gre@" + myFormat.format(player.betRoulette18));

        if (player.betRoulette19 == 0)
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet 19: " + "@red@0");
        else
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet 19: " + "@gre@" + myFormat.format(player.betRoulette19));

        if (player.betRoulette20 == 0)
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet 20: " + "@red@0");
        else
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet 20: " + "@gre@" + myFormat.format(player.betRoulette20));

        if (player.betRoulette21 == 0)
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet 21: " + "@red@0");
        else
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet 21: " + "@gre@" + myFormat.format(player.betRoulette21));

        if (player.betRoulette22 == 0)
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet 22: " + "@red@0");
        else
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet 22: " + "@gre@" + myFormat.format(player.betRoulette22));

        if (player.betRoulette23 == 0)
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet 23: " + "@red@0");
        else
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet 23: " + "@gre@" + myFormat.format(player.betRoulette23));

        if (player.betRoulette24 == 0)
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet 24: " + "@red@0");
        else
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet 24: " + "@gre@" + myFormat.format(player.betRoulette24));

        if (player.betRoulette25 == 0)
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet 25: " + "@red@0");
        else
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet 25: " + "@gre@" + myFormat.format(player.betRoulette25));

        if (player.betRoulette26 == 0)
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet 26: " + "@red@0");
        else
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet 26: " + "@gre@" + myFormat.format(player.betRoulette26));

        if (player.betRoulette27 == 0)
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet 27: " + "@red@0");
        else
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet 27: " + "@gre@" + myFormat.format(player.betRoulette27));

        if (player.betRoulette28 == 0)
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet 28: " + "@red@0");
        else
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet 28: " + "@gre@" + myFormat.format(player.betRoulette28));

        if (player.betRoulette29 == 0)
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet 29: " + "@red@0");
        else
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet 29: " + "@gre@" + myFormat.format(player.betRoulette29));

        if (player.betRoulette30 == 0)
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet 30: " + "@red@0");
        else
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet 30: " + "@gre@" + myFormat.format(player.betRoulette30));

        if (player.betRoulette31 == 0)
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet 31: " + "@red@0");
        else
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet 31: " + "@gre@" + myFormat.format(player.betRoulette31));

        if (player.betRoulette32 == 0)
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet 32: " + "@red@0");
        else
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet 32: " + "@gre@" + myFormat.format(player.betRoulette32));

        if (player.betRoulette33 == 0)
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet 33: " + "@red@0");
        else
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet 33: " + "@gre@" + myFormat.format(player.betRoulette33));

        if (player.betRoulette34 == 0)
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet 34: " + "@red@0");
        else
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet 34: " + "@gre@" + myFormat.format(player.betRoulette34));

        if (player.betRoulette35 == 0)
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet 35: " + "@red@0");
        else
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet 35: " + "@gre@" + myFormat.format(player.betRoulette35));

        if (player.betRoulette36 == 0)
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet 36: " + "@red@0");
        else
            player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bet 36: " + "@gre@" + myFormat.format(player.betRoulette36));


    }

    public static void buttons(Player player, int button) {


        int[] pokerBets = {0, 10000, 100000, 1000000, 10000000};
        int[] blackjackBets = {0, 10000, 100000, 1000000, 10000000, 100000000};
        int[] rouletteBets = {0, 1000, 10000, 100000, 1000000, 10000000};
        int[] rouletteOutsideBets = {0, 100000, 1000000, 10000000, 100000000, 1000000000};
        int[] hlBets = {0, 100000, 1000000, 10000000, 100000000};

        switch (button) {


            case 39161:
                player.betHL = 0;
                break;

            case 39167:

                player.betRouletteRed = 0;
                player.betRouletteBlack = 0;
                player.betRoulette00 = 0;
                player.betRoulette0 = 0;
                player.betRoulette1 = 0;
                player.betRoulette2 = 0;
                player.betRoulette3 = 0;
                player.betRoulette4 = 0;
                player.betRoulette5 = 0;
                player.betRoulette6 = 0;
                player.betRoulette7 = 0;
                player.betRoulette8 = 0;
                player.betRoulette9 = 0;
                player.betRoulette10 = 0;
                player.betRoulette11 = 0;
                player.betRoulette12 = 0;
                player.betRoulette13 = 0;
                player.betRoulette14 = 0;
                player.betRoulette15 = 0;
                player.betRoulette16 = 0;
                player.betRoulette17 = 0;
                player.betRoulette18 = 0;
                player.betRoulette19 = 0;
                player.betRoulette20 = 0;
                player.betRoulette21 = 0;
                player.betRoulette22 = 0;
                player.betRoulette23 = 0;
                player.betRoulette24 = 0;
                player.betRoulette25 = 0;
                player.betRoulette26 = 0;
                player.betRoulette27 = 0;
                player.betRoulette28 = 0;
                player.betRoulette29 = 0;
                player.betRoulette30 = 0;
                player.betRoulette31 = 0;
                player.betRoulette32 = 0;
                player.betRoulette33 = 0;
                player.betRoulette34 = 0;
                player.betRoulette35 = 0;
                player.betRoulette36 = 0;

                player.getPacketSender().sendMessage("You've reset all of your bets!");

                break;


            case 39162: //HL bets

                if (player.prizeHL > 0) {
                    player.getPacketSender().sendMessage("@red@You can't change your bet in the middle of this game!");
                    break;
                }


                if (player.betHL == hlBets[0])
                    player.betHL = hlBets[1];
                else if (player.betHL == hlBets[1])
                    player.betHL = hlBets[2];
                else if (player.betHL == hlBets[2])
                    player.betHL = hlBets[3];
                else if (player.betHL == hlBets[3])
                    player.betHL = hlBets[4];
                else if (player.betHL == hlBets[4])
                    player.betHL = hlBets[0];
                else
                    player.betHL = 0;

                player.getPacketSender().sendMessage("You've changed your Higher/Lower Bet!");

                break;

            case 39165: //blackjack bets

                if (player.betBlackjack == blackjackBets[0])
                    player.betBlackjack = blackjackBets[1];
                else if (player.betBlackjack == blackjackBets[1])
                    player.betBlackjack = blackjackBets[2];
                else if (player.betBlackjack == blackjackBets[2])
                    player.betBlackjack = blackjackBets[3];
                else if (player.betBlackjack == blackjackBets[3])
                    player.betBlackjack = blackjackBets[4];
                else if (player.betBlackjack == blackjackBets[4])
                    player.betBlackjack = blackjackBets[5];
                else if (player.betBlackjack == blackjackBets[5])
                    player.betBlackjack = blackjackBets[0];
                else
                    player.betBlackjack = 0;

                player.getPacketSender().sendMessage("You've changed your Blackjack Bet!");
                break;


            case 39168: //roulette bets

                if (player.betRouletteBlack == rouletteOutsideBets[0])
                    player.betRouletteBlack = rouletteOutsideBets[1];
                else if (player.betRouletteBlack == rouletteOutsideBets[1])
                    player.betRouletteBlack = rouletteOutsideBets[2];
                else if (player.betRouletteBlack == rouletteOutsideBets[2])
                    player.betRouletteBlack = rouletteOutsideBets[3];
                else if (player.betRouletteBlack == rouletteOutsideBets[3])
                    player.betRouletteBlack = rouletteOutsideBets[4];
                else if (player.betRouletteBlack == rouletteOutsideBets[4])
                    player.betRouletteBlack = rouletteOutsideBets[5];
                else if (player.betRouletteBlack == rouletteOutsideBets[5])
                    player.betRouletteBlack = rouletteOutsideBets[0];
                else player.betRouletteBlack = 0;

                break;


            case 39169: //roulette bets

                if (player.betRouletteRed == rouletteOutsideBets[0])
                    player.betRouletteRed = rouletteOutsideBets[1];
                else if (player.betRouletteRed == rouletteOutsideBets[1])
                    player.betRouletteRed = rouletteOutsideBets[2];
                else if (player.betRouletteRed == rouletteOutsideBets[2])
                    player.betRouletteRed = rouletteOutsideBets[3];
                else if (player.betRouletteRed == rouletteOutsideBets[3])
                    player.betRouletteRed = rouletteOutsideBets[4];
                else if (player.betRouletteRed == rouletteOutsideBets[4])
                    player.betRouletteRed = rouletteOutsideBets[5];
                else if (player.betRouletteRed == rouletteOutsideBets[5])
                    player.betRouletteRed = rouletteOutsideBets[0];
                else player.betRouletteRed = 0;

                break;


            case 39170: //roulette bets

                if (player.betRoulette0 == rouletteBets[0])
                    player.betRoulette0 = rouletteBets[1];
                else if (player.betRoulette0 == rouletteBets[1])
                    player.betRoulette0 = rouletteBets[2];
                else if (player.betRoulette0 == rouletteBets[2])
                    player.betRoulette0 = rouletteBets[3];
                else if (player.betRoulette0 == rouletteBets[3])
                    player.betRoulette0 = rouletteBets[4];
                else if (player.betRoulette0 == rouletteBets[4])
                    player.betRoulette0 = rouletteBets[5];
                else if (player.betRoulette0 == rouletteBets[5])
                    player.betRoulette0 = rouletteBets[0];
                else player.betRoulette0 = 0;

                break;


            case 930: //roulette bets

                if (player.betRoulette00 == rouletteBets[0])
                    player.betRoulette00 = rouletteBets[1];
                else if (player.betRoulette00 == rouletteBets[1])
                    player.betRoulette00 = rouletteBets[2];
                else if (player.betRoulette00 == rouletteBets[2])
                    player.betRoulette00 = rouletteBets[3];
                else if (player.betRoulette00 == rouletteBets[3])
                    player.betRoulette00 = rouletteBets[4];
                else if (player.betRoulette00 == rouletteBets[4])
                    player.betRoulette00 = rouletteBets[5];
                else if (player.betRoulette00 == rouletteBets[5])
                    player.betRoulette00 = rouletteBets[0];
                else player.betRoulette00 = 0;

                break;


            case 941: //roulette bets
            case 942:

                if (player.betRoulette1 == rouletteBets[0])
                    player.betRoulette1 = rouletteBets[1];
                else if (player.betRoulette1 == rouletteBets[1])
                    player.betRoulette1 = rouletteBets[2];
                else if (player.betRoulette1 == rouletteBets[2])
                    player.betRoulette1 = rouletteBets[3];
                else if (player.betRoulette1 == rouletteBets[3])
                    player.betRoulette1 = rouletteBets[4];
                else if (player.betRoulette1 == rouletteBets[4])
                    player.betRoulette1 = rouletteBets[5];
                else if (player.betRoulette1 == rouletteBets[5])
                    player.betRoulette1 = rouletteBets[0];
                else player.betRoulette1 = 0;

                break;


            case 39173: //roulette bets

                if (player.betRoulette2 == rouletteBets[0])
                    player.betRoulette2 = rouletteBets[1];
                else if (player.betRoulette2 == rouletteBets[1])
                    player.betRoulette2 = rouletteBets[2];
                else if (player.betRoulette2 == rouletteBets[2])
                    player.betRoulette2 = rouletteBets[3];
                else if (player.betRoulette2 == rouletteBets[3])
                    player.betRoulette2 = rouletteBets[4];
                else if (player.betRoulette2 == rouletteBets[4])
                    player.betRoulette2 = rouletteBets[5];
                else if (player.betRoulette2 == rouletteBets[5])
                    player.betRoulette2 = rouletteBets[0];
                else player.betRoulette2 = 0;

                break;


            case 39174: //roulette bets

                if (player.betRoulette3 == rouletteBets[0])
                    player.betRoulette3 = rouletteBets[1];
                else if (player.betRoulette3 == rouletteBets[1])
                    player.betRoulette3 = rouletteBets[2];
                else if (player.betRoulette3 == rouletteBets[2])
                    player.betRoulette3 = rouletteBets[3];
                else if (player.betRoulette3 == rouletteBets[3])
                    player.betRoulette3 = rouletteBets[4];
                else if (player.betRoulette3 == rouletteBets[4])
                    player.betRoulette3 = rouletteBets[5];
                else if (player.betRoulette3 == rouletteBets[5])
                    player.betRoulette3 = rouletteBets[0];
                else player.betRoulette3 = 0;

                break;


            case 39175: //roulette bets

                if (player.betRoulette4 == rouletteBets[0])
                    player.betRoulette4 = rouletteBets[1];
                else if (player.betRoulette4 == rouletteBets[1])
                    player.betRoulette4 = rouletteBets[2];
                else if (player.betRoulette4 == rouletteBets[2])
                    player.betRoulette4 = rouletteBets[3];
                else if (player.betRoulette4 == rouletteBets[3])
                    player.betRoulette4 = rouletteBets[4];
                else if (player.betRoulette4 == rouletteBets[4])
                    player.betRoulette4 = rouletteBets[5];
                else if (player.betRoulette4 == rouletteBets[5])
                    player.betRoulette4 = rouletteBets[0];
                else player.betRoulette4 = 0;

                break;


            case 39176: //roulette bets

                if (player.betRoulette5 == rouletteBets[0])
                    player.betRoulette5 = rouletteBets[1];
                else if (player.betRoulette5 == rouletteBets[1])
                    player.betRoulette5 = rouletteBets[2];
                else if (player.betRoulette5 == rouletteBets[2])
                    player.betRoulette5 = rouletteBets[3];
                else if (player.betRoulette5 == rouletteBets[3])
                    player.betRoulette5 = rouletteBets[4];
                else if (player.betRoulette5 == rouletteBets[4])
                    player.betRoulette5 = rouletteBets[5];
                else if (player.betRoulette5 == rouletteBets[5])
                    player.betRoulette5 = rouletteBets[0];
                else player.betRoulette5 = 0;

                break;


            case 39177: //roulette bets

                if (player.betRoulette6 == rouletteBets[0])
                    player.betRoulette6 = rouletteBets[1];
                else if (player.betRoulette6 == rouletteBets[1])
                    player.betRoulette6 = rouletteBets[2];
                else if (player.betRoulette6 == rouletteBets[2])
                    player.betRoulette6 = rouletteBets[3];
                else if (player.betRoulette6 == rouletteBets[3])
                    player.betRoulette6 = rouletteBets[4];
                else if (player.betRoulette6 == rouletteBets[4])
                    player.betRoulette6 = rouletteBets[5];
                else if (player.betRoulette6 == rouletteBets[5])
                    player.betRoulette6 = rouletteBets[0];
                else player.betRoulette6 = 0;

                break;


            case 39178: //roulette bets

                if (player.betRoulette7 == rouletteBets[0])
                    player.betRoulette7 = rouletteBets[1];
                else if (player.betRoulette7 == rouletteBets[1])
                    player.betRoulette7 = rouletteBets[2];
                else if (player.betRoulette7 == rouletteBets[2])
                    player.betRoulette7 = rouletteBets[3];
                else if (player.betRoulette7 == rouletteBets[3])
                    player.betRoulette7 = rouletteBets[4];
                else if (player.betRoulette7 == rouletteBets[4])
                    player.betRoulette7 = rouletteBets[5];
                else if (player.betRoulette7 == rouletteBets[5])
                    player.betRoulette7 = rouletteBets[0];
                else player.betRoulette7 = 0;

                break;


            case 39179: //roulette bets

                if (player.betRoulette8 == rouletteBets[0])
                    player.betRoulette8 = rouletteBets[1];
                else if (player.betRoulette8 == rouletteBets[1])
                    player.betRoulette8 = rouletteBets[2];
                else if (player.betRoulette8 == rouletteBets[2])
                    player.betRoulette8 = rouletteBets[3];
                else if (player.betRoulette8 == rouletteBets[3])
                    player.betRoulette8 = rouletteBets[4];
                else if (player.betRoulette8 == rouletteBets[4])
                    player.betRoulette8 = rouletteBets[5];
                else if (player.betRoulette8 == rouletteBets[5])
                    player.betRoulette8 = rouletteBets[0];
                else player.betRoulette8 = 0;

                break;


            case 39180: //roulette bets

                if (player.betRoulette9 == rouletteBets[0])
                    player.betRoulette9 = rouletteBets[1];
                else if (player.betRoulette9 == rouletteBets[1])
                    player.betRoulette9 = rouletteBets[2];
                else if (player.betRoulette9 == rouletteBets[2])
                    player.betRoulette9 = rouletteBets[3];
                else if (player.betRoulette9 == rouletteBets[3])
                    player.betRoulette9 = rouletteBets[4];
                else if (player.betRoulette9 == rouletteBets[4])
                    player.betRoulette9 = rouletteBets[5];
                else if (player.betRoulette9 == rouletteBets[5])
                    player.betRoulette9 = rouletteBets[0];
                else player.betRoulette9 = 0;

                break;


            case 39181: //roulette bets

                if (player.betRoulette10 == rouletteBets[0])
                    player.betRoulette10 = rouletteBets[1];
                else if (player.betRoulette10 == rouletteBets[1])
                    player.betRoulette10 = rouletteBets[2];
                else if (player.betRoulette10 == rouletteBets[2])
                    player.betRoulette10 = rouletteBets[3];
                else if (player.betRoulette10 == rouletteBets[3])
                    player.betRoulette10 = rouletteBets[4];
                else if (player.betRoulette10 == rouletteBets[4])
                    player.betRoulette10 = rouletteBets[5];
                else if (player.betRoulette10 == rouletteBets[5])
                    player.betRoulette10 = rouletteBets[0];
                else player.betRoulette10 = 0;

                break;


            case 39182: //roulette bets

                if (player.betRoulette11 == rouletteBets[0])
                    player.betRoulette11 = rouletteBets[1];
                else if (player.betRoulette11 == rouletteBets[1])
                    player.betRoulette11 = rouletteBets[2];
                else if (player.betRoulette11 == rouletteBets[2])
                    player.betRoulette11 = rouletteBets[3];
                else if (player.betRoulette11 == rouletteBets[3])
                    player.betRoulette11 = rouletteBets[4];
                else if (player.betRoulette11 == rouletteBets[4])
                    player.betRoulette11 = rouletteBets[5];
                else if (player.betRoulette11 == rouletteBets[5])
                    player.betRoulette11 = rouletteBets[0];
                else player.betRoulette11 = 0;

                break;


            case 39183: //roulette bets

                if (player.betRoulette12 == rouletteBets[0])
                    player.betRoulette12 = rouletteBets[1];
                else if (player.betRoulette12 == rouletteBets[1])
                    player.betRoulette12 = rouletteBets[2];
                else if (player.betRoulette12 == rouletteBets[2])
                    player.betRoulette12 = rouletteBets[3];
                else if (player.betRoulette12 == rouletteBets[3])
                    player.betRoulette12 = rouletteBets[4];
                else if (player.betRoulette12 == rouletteBets[4])
                    player.betRoulette12 = rouletteBets[5];
                else if (player.betRoulette12 == rouletteBets[5])
                    player.betRoulette12 = rouletteBets[0];
                else player.betRoulette12 = 0;

                break;


            case 39184: //roulette bets

                if (player.betRoulette13 == rouletteBets[0])
                    player.betRoulette13 = rouletteBets[1];
                else if (player.betRoulette13 == rouletteBets[1])
                    player.betRoulette13 = rouletteBets[2];
                else if (player.betRoulette13 == rouletteBets[2])
                    player.betRoulette13 = rouletteBets[3];
                else if (player.betRoulette13 == rouletteBets[3])
                    player.betRoulette13 = rouletteBets[4];
                else if (player.betRoulette13 == rouletteBets[4])
                    player.betRoulette13 = rouletteBets[5];
                else if (player.betRoulette13 == rouletteBets[5])
                    player.betRoulette13 = rouletteBets[0];
                else player.betRoulette13 = 0;

                break;


            case 39185: //roulette bets

                if (player.betRoulette14 == rouletteBets[0])
                    player.betRoulette14 = rouletteBets[1];
                else if (player.betRoulette14 == rouletteBets[1])
                    player.betRoulette14 = rouletteBets[2];
                else if (player.betRoulette14 == rouletteBets[2])
                    player.betRoulette14 = rouletteBets[3];
                else if (player.betRoulette14 == rouletteBets[3])
                    player.betRoulette14 = rouletteBets[4];
                else if (player.betRoulette14 == rouletteBets[4])
                    player.betRoulette14 = rouletteBets[5];
                else if (player.betRoulette14 == rouletteBets[5])
                    player.betRoulette14 = rouletteBets[0];
                else player.betRoulette14 = 0;

                break;


            case 39186: //roulette bets

                if (player.betRoulette15 == rouletteBets[0])
                    player.betRoulette15 = rouletteBets[1];
                else if (player.betRoulette15 == rouletteBets[1])
                    player.betRoulette15 = rouletteBets[2];
                else if (player.betRoulette15 == rouletteBets[2])
                    player.betRoulette15 = rouletteBets[3];
                else if (player.betRoulette15 == rouletteBets[3])
                    player.betRoulette15 = rouletteBets[4];
                else if (player.betRoulette15 == rouletteBets[4])
                    player.betRoulette15 = rouletteBets[5];
                else if (player.betRoulette15 == rouletteBets[5])
                    player.betRoulette15 = rouletteBets[0];
                else player.betRoulette15 = 0;

                break;


            case 39187: //roulette bets

                if (player.betRoulette16 == rouletteBets[0])
                    player.betRoulette16 = rouletteBets[1];
                else if (player.betRoulette16 == rouletteBets[1])
                    player.betRoulette16 = rouletteBets[2];
                else if (player.betRoulette16 == rouletteBets[2])
                    player.betRoulette16 = rouletteBets[3];
                else if (player.betRoulette16 == rouletteBets[3])
                    player.betRoulette16 = rouletteBets[4];
                else if (player.betRoulette16 == rouletteBets[4])
                    player.betRoulette16 = rouletteBets[5];
                else if (player.betRoulette16 == rouletteBets[5])
                    player.betRoulette16 = rouletteBets[0];
                else player.betRoulette16 = 0;

                break;


            case 39188: //roulette bets

                if (player.betRoulette17 == rouletteBets[0])
                    player.betRoulette17 = rouletteBets[1];
                else if (player.betRoulette17 == rouletteBets[1])
                    player.betRoulette17 = rouletteBets[2];
                else if (player.betRoulette17 == rouletteBets[2])
                    player.betRoulette17 = rouletteBets[3];
                else if (player.betRoulette17 == rouletteBets[3])
                    player.betRoulette17 = rouletteBets[4];
                else if (player.betRoulette17 == rouletteBets[4])
                    player.betRoulette17 = rouletteBets[5];
                else if (player.betRoulette17 == rouletteBets[5])
                    player.betRoulette17 = rouletteBets[0];
                else player.betRoulette17 = 0;

                break;


            case 39189: //roulette bets

                if (player.betRoulette18 == rouletteBets[0])
                    player.betRoulette18 = rouletteBets[1];
                else if (player.betRoulette18 == rouletteBets[1])
                    player.betRoulette18 = rouletteBets[2];
                else if (player.betRoulette18 == rouletteBets[2])
                    player.betRoulette18 = rouletteBets[3];
                else if (player.betRoulette18 == rouletteBets[3])
                    player.betRoulette18 = rouletteBets[4];
                else if (player.betRoulette18 == rouletteBets[4])
                    player.betRoulette18 = rouletteBets[5];
                else if (player.betRoulette18 == rouletteBets[5])
                    player.betRoulette18 = rouletteBets[0];
                else player.betRoulette18 = 0;

                break;


            case 39190: //roulette bets

                if (player.betRoulette19 == rouletteBets[0])
                    player.betRoulette19 = rouletteBets[1];
                else if (player.betRoulette19 == rouletteBets[1])
                    player.betRoulette19 = rouletteBets[2];
                else if (player.betRoulette19 == rouletteBets[2])
                    player.betRoulette19 = rouletteBets[3];
                else if (player.betRoulette19 == rouletteBets[3])
                    player.betRoulette19 = rouletteBets[4];
                else if (player.betRoulette19 == rouletteBets[4])
                    player.betRoulette19 = rouletteBets[5];
                else if (player.betRoulette19 == rouletteBets[5])
                    player.betRoulette19 = rouletteBets[0];
                else player.betRoulette19 = 0;

                break;


            case 39191: //roulette bets

                if (player.betRoulette20 == rouletteBets[0])
                    player.betRoulette20 = rouletteBets[1];
                else if (player.betRoulette20 == rouletteBets[1])
                    player.betRoulette20 = rouletteBets[2];
                else if (player.betRoulette20 == rouletteBets[2])
                    player.betRoulette20 = rouletteBets[3];
                else if (player.betRoulette20 == rouletteBets[3])
                    player.betRoulette20 = rouletteBets[4];
                else if (player.betRoulette20 == rouletteBets[4])
                    player.betRoulette20 = rouletteBets[5];
                else if (player.betRoulette20 == rouletteBets[5])
                    player.betRoulette20 = rouletteBets[0];
                else player.betRoulette20 = 0;

                break;


            case 39192: //roulette bets

                if (player.betRoulette21 == rouletteBets[0])
                    player.betRoulette21 = rouletteBets[1];
                else if (player.betRoulette21 == rouletteBets[1])
                    player.betRoulette21 = rouletteBets[2];
                else if (player.betRoulette21 == rouletteBets[2])
                    player.betRoulette21 = rouletteBets[3];
                else if (player.betRoulette21 == rouletteBets[3])
                    player.betRoulette21 = rouletteBets[4];
                else if (player.betRoulette21 == rouletteBets[4])
                    player.betRoulette21 = rouletteBets[5];
                else if (player.betRoulette21 == rouletteBets[5])
                    player.betRoulette21 = rouletteBets[0];
                else player.betRoulette21 = 0;

                break;


            case 39193: //roulette bets

                if (player.betRoulette22 == rouletteBets[0])
                    player.betRoulette22 = rouletteBets[1];
                else if (player.betRoulette22 == rouletteBets[1])
                    player.betRoulette22 = rouletteBets[2];
                else if (player.betRoulette22 == rouletteBets[2])
                    player.betRoulette22 = rouletteBets[3];
                else if (player.betRoulette22 == rouletteBets[3])
                    player.betRoulette22 = rouletteBets[4];
                else if (player.betRoulette22 == rouletteBets[4])
                    player.betRoulette22 = rouletteBets[5];
                else if (player.betRoulette22 == rouletteBets[5])
                    player.betRoulette22 = rouletteBets[0];
                else player.betRoulette22 = 0;

                break;


            case 39194: //roulette bets

                if (player.betRoulette23 == rouletteBets[0])
                    player.betRoulette23 = rouletteBets[1];
                else if (player.betRoulette23 == rouletteBets[1])
                    player.betRoulette23 = rouletteBets[2];
                else if (player.betRoulette23 == rouletteBets[2])
                    player.betRoulette23 = rouletteBets[3];
                else if (player.betRoulette23 == rouletteBets[3])
                    player.betRoulette23 = rouletteBets[4];
                else if (player.betRoulette23 == rouletteBets[4])
                    player.betRoulette23 = rouletteBets[5];
                else if (player.betRoulette23 == rouletteBets[5])
                    player.betRoulette23 = rouletteBets[0];
                else player.betRoulette23 = 0;

                break;


            case 39195: //roulette bets

                if (player.betRoulette24 == rouletteBets[0])
                    player.betRoulette24 = rouletteBets[1];
                else if (player.betRoulette24 == rouletteBets[1])
                    player.betRoulette24 = rouletteBets[2];
                else if (player.betRoulette24 == rouletteBets[2])
                    player.betRoulette24 = rouletteBets[3];
                else if (player.betRoulette24 == rouletteBets[3])
                    player.betRoulette24 = rouletteBets[4];
                else if (player.betRoulette24 == rouletteBets[4])
                    player.betRoulette24 = rouletteBets[5];
                else if (player.betRoulette24 == rouletteBets[5])
                    player.betRoulette24 = rouletteBets[0];
                else player.betRoulette24 = 0;

                break;


            case 39196: //roulette bets

                if (player.betRoulette25 == rouletteBets[0])
                    player.betRoulette25 = rouletteBets[1];
                else if (player.betRoulette25 == rouletteBets[1])
                    player.betRoulette25 = rouletteBets[2];
                else if (player.betRoulette25 == rouletteBets[2])
                    player.betRoulette25 = rouletteBets[3];
                else if (player.betRoulette25 == rouletteBets[3])
                    player.betRoulette25 = rouletteBets[4];
                else if (player.betRoulette25 == rouletteBets[4])
                    player.betRoulette25 = rouletteBets[5];
                else if (player.betRoulette25 == rouletteBets[5])
                    player.betRoulette25 = rouletteBets[0];
                else player.betRoulette25 = 0;

                break;


            case 39197: //roulette bets

                if (player.betRoulette26 == rouletteBets[0])
                    player.betRoulette26 = rouletteBets[1];
                else if (player.betRoulette26 == rouletteBets[1])
                    player.betRoulette26 = rouletteBets[2];
                else if (player.betRoulette26 == rouletteBets[2])
                    player.betRoulette26 = rouletteBets[3];
                else if (player.betRoulette26 == rouletteBets[3])
                    player.betRoulette26 = rouletteBets[4];
                else if (player.betRoulette26 == rouletteBets[4])
                    player.betRoulette26 = rouletteBets[5];
                else if (player.betRoulette26 == rouletteBets[5])
                    player.betRoulette26 = rouletteBets[0];
                else player.betRoulette26 = 0;

                break;


            case 39198: //roulette bets

                if (player.betRoulette27 == rouletteBets[0])
                    player.betRoulette27 = rouletteBets[1];
                else if (player.betRoulette27 == rouletteBets[1])
                    player.betRoulette27 = rouletteBets[2];
                else if (player.betRoulette27 == rouletteBets[2])
                    player.betRoulette27 = rouletteBets[3];
                else if (player.betRoulette27 == rouletteBets[3])
                    player.betRoulette27 = rouletteBets[4];
                else if (player.betRoulette27 == rouletteBets[4])
                    player.betRoulette27 = rouletteBets[5];
                else if (player.betRoulette27 == rouletteBets[5])
                    player.betRoulette27 = rouletteBets[0];
                else player.betRoulette27 = 0;

                break;


            case 39199: //roulette bets

                if (player.betRoulette28 == rouletteBets[0])
                    player.betRoulette28 = rouletteBets[1];
                else if (player.betRoulette28 == rouletteBets[1])
                    player.betRoulette28 = rouletteBets[2];
                else if (player.betRoulette28 == rouletteBets[2])
                    player.betRoulette28 = rouletteBets[3];
                else if (player.betRoulette28 == rouletteBets[3])
                    player.betRoulette28 = rouletteBets[4];
                else if (player.betRoulette28 == rouletteBets[4])
                    player.betRoulette28 = rouletteBets[5];
                else if (player.betRoulette28 == rouletteBets[5])
                    player.betRoulette28 = rouletteBets[0];
                else player.betRoulette28 = 0;

                break;


            case 39200: //roulette bets

                if (player.betRoulette29 == rouletteBets[0])
                    player.betRoulette29 = rouletteBets[1];
                else if (player.betRoulette29 == rouletteBets[1])
                    player.betRoulette29 = rouletteBets[2];
                else if (player.betRoulette29 == rouletteBets[2])
                    player.betRoulette29 = rouletteBets[3];
                else if (player.betRoulette29 == rouletteBets[3])
                    player.betRoulette29 = rouletteBets[4];
                else if (player.betRoulette29 == rouletteBets[4])
                    player.betRoulette29 = rouletteBets[5];
                else if (player.betRoulette29 == rouletteBets[5])
                    player.betRoulette29 = rouletteBets[0];
                else player.betRoulette29 = 0;

                break;


            case 39201: //roulette bets

                if (player.betRoulette30 == rouletteBets[0])
                    player.betRoulette30 = rouletteBets[1];
                else if (player.betRoulette30 == rouletteBets[1])
                    player.betRoulette30 = rouletteBets[2];
                else if (player.betRoulette30 == rouletteBets[2])
                    player.betRoulette30 = rouletteBets[3];
                else if (player.betRoulette30 == rouletteBets[3])
                    player.betRoulette30 = rouletteBets[4];
                else if (player.betRoulette30 == rouletteBets[4])
                    player.betRoulette30 = rouletteBets[5];
                else if (player.betRoulette30 == rouletteBets[5])
                    player.betRoulette30 = rouletteBets[0];
                else player.betRoulette30 = 0;

                break;


            case 39202: //roulette bets

                if (player.betRoulette31 == rouletteBets[0])
                    player.betRoulette31 = rouletteBets[1];
                else if (player.betRoulette31 == rouletteBets[1])
                    player.betRoulette31 = rouletteBets[2];
                else if (player.betRoulette31 == rouletteBets[2])
                    player.betRoulette31 = rouletteBets[3];
                else if (player.betRoulette31 == rouletteBets[3])
                    player.betRoulette31 = rouletteBets[4];
                else if (player.betRoulette31 == rouletteBets[4])
                    player.betRoulette31 = rouletteBets[5];
                else if (player.betRoulette31 == rouletteBets[5])
                    player.betRoulette31 = rouletteBets[0];
                else player.betRoulette31 = 0;

                break;


            case 39203: //roulette bets

                if (player.betRoulette32 == rouletteBets[0])
                    player.betRoulette32 = rouletteBets[1];
                else if (player.betRoulette32 == rouletteBets[1])
                    player.betRoulette32 = rouletteBets[2];
                else if (player.betRoulette32 == rouletteBets[2])
                    player.betRoulette32 = rouletteBets[3];
                else if (player.betRoulette32 == rouletteBets[3])
                    player.betRoulette32 = rouletteBets[4];
                else if (player.betRoulette32 == rouletteBets[4])
                    player.betRoulette32 = rouletteBets[5];
                else if (player.betRoulette32 == rouletteBets[5])
                    player.betRoulette32 = rouletteBets[0];
                else player.betRoulette32 = 0;

                break;


            case 39204: //roulette bets

                if (player.betRoulette33 == rouletteBets[0])
                    player.betRoulette33 = rouletteBets[1];
                else if (player.betRoulette33 == rouletteBets[1])
                    player.betRoulette33 = rouletteBets[2];
                else if (player.betRoulette33 == rouletteBets[2])
                    player.betRoulette33 = rouletteBets[3];
                else if (player.betRoulette33 == rouletteBets[3])
                    player.betRoulette33 = rouletteBets[4];
                else if (player.betRoulette33 == rouletteBets[4])
                    player.betRoulette33 = rouletteBets[5];
                else if (player.betRoulette33 == rouletteBets[5])
                    player.betRoulette33 = rouletteBets[0];
                else player.betRoulette33 = 0;

                break;


            case 39205: //roulette bets

                if (player.betRoulette34 == rouletteBets[0])
                    player.betRoulette34 = rouletteBets[1];
                else if (player.betRoulette34 == rouletteBets[1])
                    player.betRoulette34 = rouletteBets[2];
                else if (player.betRoulette34 == rouletteBets[2])
                    player.betRoulette34 = rouletteBets[3];
                else if (player.betRoulette34 == rouletteBets[3])
                    player.betRoulette34 = rouletteBets[4];
                else if (player.betRoulette34 == rouletteBets[4])
                    player.betRoulette34 = rouletteBets[5];
                else if (player.betRoulette34 == rouletteBets[5])
                    player.betRoulette34 = rouletteBets[0];
                else player.betRoulette34 = 0;

                break;


            case 39206: //roulette bets

                if (player.betRoulette35 == rouletteBets[0])
                    player.betRoulette35 = rouletteBets[1];
                else if (player.betRoulette35 == rouletteBets[1])
                    player.betRoulette35 = rouletteBets[2];
                else if (player.betRoulette35 == rouletteBets[2])
                    player.betRoulette35 = rouletteBets[3];
                else if (player.betRoulette35 == rouletteBets[3])
                    player.betRoulette35 = rouletteBets[4];
                else if (player.betRoulette35 == rouletteBets[4])
                    player.betRoulette35 = rouletteBets[5];
                else if (player.betRoulette35 == rouletteBets[5])
                    player.betRoulette35 = rouletteBets[0];
                else player.betRoulette35 = 0;

                break;


            case 39207: //roulette bets

                if (player.betRoulette36 == rouletteBets[0])
                    player.betRoulette36 = rouletteBets[1];
                else if (player.betRoulette36 == rouletteBets[1])
                    player.betRoulette36 = rouletteBets[2];
                else if (player.betRoulette36 == rouletteBets[2])
                    player.betRoulette36 = rouletteBets[3];
                else if (player.betRoulette36 == rouletteBets[3])
                    player.betRoulette36 = rouletteBets[4];
                else if (player.betRoulette36 == rouletteBets[4])
                    player.betRoulette36 = rouletteBets[5];
                else if (player.betRoulette36 == rouletteBets[5])
                    player.betRoulette36 = rouletteBets[0];
                else player.betRoulette36 = 0;

                break;

        }


    }


}