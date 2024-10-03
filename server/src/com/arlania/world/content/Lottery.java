package com.arlania.world.content;

import com.arlania.GameServer;
import com.arlania.model.StaffRights;
import com.arlania.util.Misc;
import com.arlania.util.RandomUtility;
import com.arlania.world.World;
import com.arlania.world.content.dialogue.Dialogue;
import com.arlania.world.content.dialogue.DialogueExpression;
import com.arlania.world.content.dialogue.DialogueManager;
import com.arlania.world.content.dialogue.DialogueType;
import com.arlania.world.entity.impl.player.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * Handles the Lottery.
 *
 * @author Gabriel Hannason
 */
public class Lottery {

    /**
     * The list holding all users who have entered the lottery.
     */
    private static final List<String> CONTESTERS = new ArrayList<>();

    /*
     * The location to the Lottery file where users are saved.
     */
    private static final File CONTESTERS_FILE_LOCATION = new File("./data/saves/lottery/lottery.txt");

    /*
     * The location to the Lottery file where the winners are saved.
     */
    private static final File LAST_WINNER_FILE_LOCATION = new File("./data/saves/lottery/lotterywin.txt");

    /*
     * Can players enter the lottery right now?
     */
    private static final boolean LOTTERY_ENABLED = true;

    /*
     * The amount of coins required to enter the lottery.
     */
    private static final int PRICE_TO_ENTER = 1000000;

    /*
     * Get's the amount of gold people have put in the pot.
     */
    public static final int getPot() {
        if (CONTESTERS.size() == 0) {
            return 0;
        }
        return (CONTESTERS.size() * (PRICE_TO_ENTER - 250000));
    }


    public static void scratchOff(Player player) {


        if (player.getInventory().getAmount(995) < 1100000000) {
            player.getInventory().delete(619, 1);

            int winner = RandomUtility.inclusiveRandom(0, 10000);

            if (winner == 1) {
                player.getInventory().add(995, 1000000000);
                World.sendMessage("drops", "@gre@[LOTTERY]@red@ " + player.getUsername() + " @gre@ won 1 BILLION Coins from a scratch-off!");
            } else if ((winner >= 2) && (winner <= 6)) {
                player.getInventory().add(995, 250000000);
                World.sendMessage("drops", "@gre@[LOTTERY]@red@ " + player.getUsername() + " @gre@ won 250 MILLION Coins from a scratch-off!");
            } else if ((winner >= 7) && (winner <= 16)) {
                player.getInventory().add(995, 100000000);
                World.sendMessage("drops", "@gre@[LOTTERY]@red@ " + player.getUsername() + " @gre@ won 100 MILLION Coins from a scratch-off!");
            } else if ((winner >= 17) && (winner <= 41))
                player.getInventory().add(995, 50000000);
            else if ((winner >= 42) && (winner <= 81))
                player.getInventory().add(995, 25000000);
            else if ((winner >= 82) && (winner <= 181))
                player.getInventory().add(995, 10000000);
            else if ((winner >= 182) && (winner <= 381))
                player.getInventory().add(995, 5000000);
        } else
            player.getPacketSender().sendMessage("@red@You must have less coins in your inventory to scratch off tickets.");

    }


    public static void scratchAll(Player player) {

        int amount = player.getInventory().getAmount(619);

        for (int i = 0; i < amount; i++) {


            if (player.getInventory().getAmount(995) < 1100000000) {
                player.getInventory().delete(619, 1);

                int winner = RandomUtility.inclusiveRandom(0, 10000);

                if (winner == 1) {
                    player.getInventory().add(995, 1000000000);
                    World.sendMessage("drops", "@gre@[LOTTERY]@red@ " + player.getUsername() + " @gre@ won 1 BILLION Coins from a scratch-off!");
                } else if ((winner >= 2) && (winner <= 6)) {
                    player.getInventory().add(995, 250000000);
                    World.sendMessage("drops", "@gre@[LOTTERY]@red@ " + player.getUsername() + " @gre@ won 250 MILLION Coins from a scratch-off!");
                } else if ((winner >= 7) && (winner <= 16)) {
                    player.getInventory().add(995, 100000000);
                    World.sendMessage("drops", "@gre@[LOTTERY]@red@ " + player.getUsername() + " @gre@ won 100 MILLION Coins from a scratch-off!");
                } else if ((winner >= 17) && (winner <= 41))
                    player.getInventory().add(995, 50000000);
                else if ((winner >= 42) && (winner <= 81))
                    player.getInventory().add(995, 25000000);
                else if ((winner >= 82) && (winner <= 181))
                    player.getInventory().add(995, 10000000);
                else if ((winner >= 182) && (winner <= 381))
                    player.getInventory().add(995, 5000000);
            } else {
                player.getPacketSender().sendMessage("@red@You must have less coins in your inventory to scratch off tickets.");
                return;
            }
        }
    }


    /*
     * The user who won the Lottery last
     */
    private static String LAST_WINNER = "Zezima";

    public static String getLastWinner() {
        return LAST_WINNER;
    }

    /*
     * Has the last week's winner been rewarded?
     */
    private static boolean LAST_WINNER_REWARDED = true;

    /**
     * Gets a random winner for the lottery.
     *
     * @return A random user who has won the lottery.
     */
    public static String getRandomWinner() {
        String winner = null;
        int listSize = CONTESTERS.size();
        if (listSize >= 4)
            winner = CONTESTERS.get(RandomUtility.inclusiveRandom(listSize - 1));
        return winner;
    }

    /**
     * Handles a player who wishes to enter the lottery.
     *
     * @param p The player who wants to enter the lottery.
     */
    public static void enterLottery(Player p) {
        if (!LOTTERY_ENABLED) {
            p.getPacketSender().sendInterfaceRemoval().sendMessage("The lottery is currently not active. Try again soon!");
            return;
        }
        if (CONTESTERS.contains(p.getUsername())) {
            DialogueManager.start(p, 17);
            return;
        }
        boolean usePouch = p.getMoneyInPouch() >= PRICE_TO_ENTER;
        if (p.getInventory().getAmount(995) < PRICE_TO_ENTER && !usePouch || p.getStaffRights() == StaffRights.ADMINISTRATOR || p.getStaffRights() == StaffRights.OWNER) {
            p.getPacketSender().sendInterfaceRemoval().sendMessage("").sendMessage("You do not have enough money in your inventory to enter this week's lottery.").sendMessage("The lottery for this week costs " + Misc.insertCommasToNumber("" + PRICE_TO_ENTER + "") + " coins to enter.");
            return;
        }
        if (usePouch) {
            p.setMoneyInPouch(p.getMoneyInPouch() - PRICE_TO_ENTER);
            p.getPacketSender().sendString(8135, "" + p.getMoneyInPouch());
        } else
            p.getInventory().delete(995, PRICE_TO_ENTER);
        addToLottery(p.getUsername());
        p.getPacketSender().sendMessage("You have entered the lottery!").sendMessage("A winner is announced every Friday.");
        DialogueManager.start(p, 18);
    }

    /**
     * Adds a user to the lottery by writing their username to the file aswell as adding them to the list of users
     * who have entered already.
     *
     * @param user The username to add to the lists.
     */
    public static void addToLottery(String user) {
        CONTESTERS.add(user);
        GameServer.getLoader().getEngine().submit(() -> {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(CONTESTERS_FILE_LOCATION, true));
                writer.write(user);
                writer.newLine();
                writer.close();
            } catch (IOException e) {
                GameServer.getLogger().log(Level.SEVERE, "ruh roh", e);
            }
        });
    }

    /**
     * Reads the lottery list and adds every user from the .txt files to the lists.
     */
    public static void init() {
        try {
            BufferedReader r = new BufferedReader(new FileReader(CONTESTERS_FILE_LOCATION));
            while (true) {
                String line = r.readLine();
                if (line == null) {
                    break;
                } else {
                    line = line.trim();
                }
                if (line.length() > 0) {
                    if (!CONTESTERS.contains(line)) //user might have gotten on list twice somehow.. don't give them extra chance of winning
                        CONTESTERS.add(line);
                }
            }
            r.close();

            BufferedReader r2 = new BufferedReader(new FileReader(LAST_WINNER_FILE_LOCATION));
            while (true) {
                String line = r2.readLine();
                if (line == null) {
                    break;
                } else {
                    line = line.trim();
                }
                if (line.length() > 0) {
                    if (!line.contains("NOT REWARDED. NEEDS REWARD!"))
                        LAST_WINNER = line;
                    else
                        LAST_WINNER_REWARDED = false;
                }
            }
            r2.close();

        } catch (IOException e) {
            GameServer.getLogger().log(Level.SEVERE, "ruh roh", e);
        }
    }

    /**
     * Restarts the lottery and rewards this week's winner.
     */
    public static void restartLottery() {
        if (!LOTTERY_ENABLED)
            return;
        try {
            String winner = getRandomWinner();
            if (winner != null) {
                LAST_WINNER = winner;
                Player player = World.getPlayerByName(winner);
                BufferedWriter writer = new BufferedWriter(new FileWriter(LAST_WINNER_FILE_LOCATION));
                writer.write(winner);
                writer.newLine();
                if (player != null) {
                    rewardPlayer(player, true);
                } else {
                    LAST_WINNER_REWARDED = false;
                    writer.write("NOT REWARDED. NEEDS REWARD!");
                    System.out.println("Player " + winner + " won the lottery but wasn't online.");
                }
                CONTESTERS.clear();
                writer.close();
                writer = new BufferedWriter(new FileWriter(CONTESTERS_FILE_LOCATION));
                writer.write("");
                writer.close();
                World.sendMessage("drops", "<col=D9D919><shad=0>This week's lottery winner is " + winner + "! Congratulations!");
            } else
                World.sendMessage("drops", "<col=D9D919><shad=0>The lottery needs some more contesters before a winner can be selected.");
        } catch (Exception e) {
            GameServer.getLogger().log(Level.SEVERE, "ruh roh", e);
        }
    }

    /**
     * Rewards a player with items for winning the lottery.
     *
     * @param player The player to reward
     * @param ignore Should a check be ignored?
     * @throws IOException Throws exceptions
     */
    public static void rewardPlayer(Player player, boolean ignore) throws IOException {
        if ((!LAST_WINNER_REWARDED || ignore) && LAST_WINNER.equalsIgnoreCase(player.getUsername())) {
            LAST_WINNER_REWARDED = true;
            player.setMoneyInPouch(player.getMoneyInPouch() + getPot());
            player.getPacketSender().sendString(8135, "" + player.getMoneyInPouch());
            player.getPacketSender().sendMessage("You've won the lottery for this week! Congratulations!");
            player.getPacketSender().sendMessage("The reward has been added to your money pouch.");
            BufferedWriter writer = new BufferedWriter(new FileWriter(LAST_WINNER_FILE_LOCATION));
            writer.write(player.getUsername());
            writer.close();
            PlayerLogs.log(player.getUsername(), "Player got " + getPot() + " from winning the lottery!");
        }
    }

    /**
     * Handles the lottery for a player on login
     * Checks if a user won the lottery without being rewarded.
     *
     * @param p The player to handle login for.
     */
    public static void onLogin(Player p) {
        try {
            rewardPlayer(p, false);
        } catch (Exception e) {
            GameServer.getLogger().log(Level.SEVERE, "ruh roh", e);
        }
    }

    public static class Dialogues {

        public static Dialogue getCurrentPot(Player p) {
            return new Dialogue() {

                @Override
                public DialogueType type() {
                    return DialogueType.NPC_STATEMENT;
                }

                @Override
                public DialogueExpression animation() {
                    return DialogueExpression.NORMAL;
                }

                @Override
                public int npcId() {
                    return 4249;
                }

                @Override
                public String[] dialogue() {
                    return new String[]{"The pot is currently at:", Misc.insertCommasToNumber("" + Lottery.getPot()) + " coins."};
                }

                @Override
                public Dialogue nextDialogue() {
                    return DialogueManager.getDialogues().get(15);
                }
            };
        }

        public static Dialogue getLastWinner(Player p) {
            return new Dialogue() {

                @Override
                public DialogueType type() {
                    return DialogueType.NPC_STATEMENT;
                }

                @Override
                public DialogueExpression animation() {
                    return DialogueExpression.NORMAL;
                }

                @Override
                public int npcId() {
                    return 4249;
                }

                @Override
                public String[] dialogue() {
                    return new String[]{"Last week's winner was " + Lottery.getLastWinner() + "."};
                }

                @Override
                public Dialogue nextDialogue() {
                    return DialogueManager.getDialogues().get(15);
                }
            };
        }
    }
}
