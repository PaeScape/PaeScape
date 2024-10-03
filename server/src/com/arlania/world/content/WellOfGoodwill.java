package com.arlania.world.content;

import com.arlania.GameServer;
import com.arlania.GameSettings;
import com.arlania.util.Misc;
import com.arlania.world.World;
import com.arlania.world.content.dialogue.Dialogue;
import com.arlania.world.content.dialogue.DialogueExpression;
import com.arlania.world.content.dialogue.DialogueManager;
import com.arlania.world.content.dialogue.DialogueType;
import com.arlania.world.entity.impl.player.Player;

import java.io.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;

public class WellOfGoodwill {

    private static final int AMOUNT_NEEDED = 250000000; //250m
    private static final int LEAST_DONATE_AMOUNT_ACCEPTED = 1000000; //1m
    private static final int BONUSES_DURATION = 120; //2 hours in minutes

    private static final CopyOnWriteArrayList<Player> DONATORS = new CopyOnWriteArrayList<Player>();
    private static WellState STATE = WellState.EMPTY;
    private static long START_TIMER = 0;
    private static int MONEY_IN_WELL = 0;
    private static final String dirName = System.getProperty("user.home") + File.separator + "Saves" + File.separator + "Data" + File.separator;
    private static final String saveFileName = "well_of_goodwill.txt";

    public static void init() {
        try {
            BufferedReader in = new BufferedReader(new FileReader(dirName + saveFileName));
            if (in != null) {
                String line = in.readLine();
                if (line != null) {
                    long startTimer = Long.parseLong(line);
                    if (startTimer > 0) {
                        STATE = WellState.FULL;
                        START_TIMER = startTimer;
                        MONEY_IN_WELL = AMOUNT_NEEDED;
                    }
                }
            }
            in.close();
        } catch (Exception e) {
            GameServer.getLogger().log(Level.SEVERE, "ruh roh", e);
        }
    }

    public static void save() {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(dirName + saveFileName));
            out.write("" + START_TIMER);
            out.close();
        } catch (IOException e) {
            GameServer.getLogger().log(Level.SEVERE, "ruh roh", e);
        }
    }

    public static void lookDownWell(Player player) {
        if (checkFull(player)) {
            return;
        }
        DialogueManager.start(player, new Dialogue() {

            @Override
            public DialogueType type() {
                return DialogueType.NPC_STATEMENT;
            }

            @Override
            public DialogueExpression animation() {
                return DialogueExpression.NORMAL;
            }

            @Override
            public String[] dialogue() {
                return new String[]{"It looks like the well could hold another " + Misc.insertCommasToNumber("" + getMissingAmount()) + " coins."};
            }

            @Override
            public int npcId() {
                return 802;
            }

            @Override
            public Dialogue nextDialogue() {
                return DialogueManager.getDialogues().get(75);
            }

        });
    }

    public static boolean checkFull(Player player) {
        if (STATE == WellState.FULL) {
            DialogueManager.start(player, new Dialogue() {

                @Override
                public DialogueType type() {
                    return DialogueType.NPC_STATEMENT;
                }

                @Override
                public DialogueExpression animation() {
                    return DialogueExpression.NORMAL;
                }

                @Override
                public String[] dialogue() {
                    return new String[]{"The well is already full of coins and Saradomin", "has granted players with bonus experience for their", "generosity! There are currently " + getMinutesRemaining() + " minutes", "of bonus experience left."};
                }

                @Override
                public int npcId() {
                    return 802;
                }

                @Override
                public Dialogue nextDialogue() {
                    return DialogueManager.getDialogues().get(75);
                }

            });
            return true;
        }
        return false;
    }

    public static void donate(Player player, int amount) {
        if (checkFull(player)) {
            return;
        }
        if (amount < LEAST_DONATE_AMOUNT_ACCEPTED) {
            DialogueManager.sendStatement(player, "You must donate at least 1 million coins.");
            return;
        }
        if (amount > getMissingAmount()) {
            amount = getMissingAmount();
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
            player.getInventory().delete(995, amount);
        }
        if (!DONATORS.contains(player)) {
            DONATORS.add(player);
        }
        MONEY_IN_WELL += amount;
        if (amount > 25000000) {
            World.sendMessage("events", "@red@[EXP WELL]@bla@" + player.getUsername() + " has donated " + Misc.insertCommasToNumber("" + amount) + " coins to the Well of Goodwill!");
        }
        DialogueManager.sendStatement(player, "Thank you for your donation.");
        if (getMissingAmount() <= 0) {
            STATE = WellState.FULL;
            START_TIMER = System.currentTimeMillis();

            World.sendMessage("events", "@red@[EXP WELL]@bla@It is now granting everyone 2 hours of 30 percent bonus experience.");
            GameSettings.wellGoodwill = true;
        }
    }

    public static void updateState() {
        if (STATE == WellState.FULL) {
            if (getMinutesRemaining() <= 0) {
                World.sendMessage("events", "<img=10> <col=6666FF>The Well of Goodwill is no longer granting bonus experience.");
                GameSettings.wellGoodwill = false;
                setDefaults();
            }
        }
    }

    public static void setDefaults() {
        DONATORS.clear();
        STATE = WellState.EMPTY;
        START_TIMER = 0;
        MONEY_IN_WELL = 0;
    }

    public static int getMissingAmount() {
        return (AMOUNT_NEEDED - MONEY_IN_WELL);
    }

    public static int getMinutesRemaining() {

        return (BONUSES_DURATION - Misc.getMinutesPassed(System.currentTimeMillis() - START_TIMER));
    }

    public static boolean isActive() {
        updateState();
        return STATE == WellState.FULL;
    }

    public static boolean bonusLoyaltyPoints(Player player) {
        updateState();
        return STATE == WellState.FULL && DONATORS.contains(player);
    }

    public enum WellState {
        EMPTY,
        FULL
    }
}
