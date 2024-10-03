package com.arlania.world.content;

import com.arlania.GameServer;
import com.arlania.model.Item;
import com.arlania.model.definitions.ItemDefinition;
import com.arlania.util.RandomUtility;
import com.arlania.world.entity.impl.player.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;

public class PlayerDropLog {

    /**
     * The server settings
     */
    public static final String DIRECTORY = "./data/saves/drop_logs/";

    /**
     * Represents the item drop log
     */
    private final ArrayList<DropLogItem> dropLog = new ArrayList<DropLogItem>();

    /**
     * Send drop log
     *
     * @param player the player
     * @param other  the other
     */
    public static void sendDropLog(Player player, Player other) {
        if (other == null) {
            return;
        }
        player.dropLogPlayer = other;
        /**
         * Clears the interface
         */
        for (int i = 26141; i < 26141 + 100; i++) {
            player.getPA().sendFrame126(i, "");
        }
        int line = 0;
        if (player.dropLogOrder) {
            player.getPA().sendFrame126(26113, "Oldest to Newest");
            for (int i = 0; i < other.getPlayerDropLog().getDropLog().size(); i++) {
                DropLogItem item = other.getPlayerDropLog().getDropLog().get(i);
                if (item == null) {
                    continue;
                }
                String drop = "@or1@" + item.getNpcName() + " @bla@- @or2@"
                        + ItemDefinition.forId(item.getItem().getId()).getName() + " x" + item.getItem().getAmount()
                        + " @bla@- @or3@" + item.getDate() + " - " + item.getTime();

                line++;
                player.getPA().sendFrame126(26141 + line, drop);
            }
        } else {
            player.getPA().sendFrame126(26113, "Newest to Oldest");
            for (int i = other.getPlayerDropLog().getDropLog().size(); i > 0; i--) {
                DropLogItem item = other.getPlayerDropLog().getDropLog().get(i - 1);
                if (item == null) {
                    continue;
                }
                String drop = "@or1@" + item.getNpcName() + " @bla@- @or2@"
                        + ItemDefinition.forId(item.getItem().getId()).getName() + " x" + item.getItem().getAmount()
                        + " @bla@- @or3@" + item.getDate() + " - " + item.getTime();

                line++;
                player.getPA().sendFrame126(26141 + line, drop);
            }

        }
        if (line == 0) {
            line += 5;
            player.getPA().sendFrame126(26141 + line,
                    "                                                                   @whi@No record!");
        }
        if (other.getUsername().equalsIgnoreCase(player.getUsername())) {
            player.getPA().sendFrame126(26110, "Personal Drop Log");
        } else {
            player.getPA().sendFrame126(26110, "Drop Log: " + other.getUsername());
        }
        player.getPA().sendInterface(26139);
    }

    /**
     * Adds to the drop log
     *
     * @param player the playerF
     * @param item   the item
     */
    @SuppressWarnings("deprecation")
    public static void addDrop(Player player, Item item, String npcName) {
        /**
         * In minigame
         */
        if (player.getMinigame() != null) {
            return;
        }
        /**
         * The date
         */
        Date date = new Date();
        /**
         * The item
         */
        String day = date.getDate() + "/" + (date.getMonth() + 1) + "/" + (date.getYear() - 100);
        DropLogItem dropItem = new DropLogItem(npcName, item, day,
                date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds());
        player.getPlayerDropLog().getDropLog().add(dropItem);
        player.sendMessage("<col=12372>That drop was added to your drop log. Type ::droplog to view your drop log.");
        if (RandomUtility.inclusiveRandom(4) == 1) {
            player.sendMessage(
                    "<col=12372>You can also view other online players drop log by typing ::droplog username");
        }
    }

    /**
     * Loads the settings
     */
    public static void init(Player player) {
        File f = new File(DIRECTORY + player.getUsername());
        if (!f.exists()) {
            System.out.println(DIRECTORY + player.getUsername() + " doesn't exist");
            return;
        }
        try {
            DataInputStream input = new DataInputStream(new FileInputStream(DIRECTORY + player.getUsername()));

            int size = input.readInt();

            for (int i = 0; i < size; i++) {
                String npcName = input.readUTF();
                Item item = new Item(input.readInt(), input.readInt());
                String date = input.readUTF();
                String time = input.readUTF();
                DropLogItem dropLogItem = new DropLogItem(npcName, item, date, time);
                player.getPlayerDropLog().getDropLog().add(dropLogItem);
            }
            System.out.println("Loaded " + player.getPlayerDropLog().getDropLog().size() + " drop log items");
            input.close();
        } catch (FileNotFoundException e) {
            GameServer.getLogger().log(Level.SEVERE, "ruh roh", e);
        } catch (IOException e) {
            GameServer.getLogger().log(Level.SEVERE, "ruh roh", e);
        }
    }

    /**
     * Saves the settings
     */
    public static void save(Player player) {
        try {
            DataOutputStream output = new DataOutputStream(new FileOutputStream(DIRECTORY + player.getUsername()));

            output.writeInt(player.getPlayerDropLog().getDropLog().size());

            for (int i = 0; i < player.getPlayerDropLog().getDropLog().size(); i++) {
                DropLogItem item = player.getPlayerDropLog().getDropLog().get(i);
                if (item == null) {
                    continue;
                }
                output.writeUTF(item.getNpcName());
                output.writeInt(item.getItem().getId());
                output.writeInt(item.getItem().getAmount());
                output.writeUTF(item.getDate());
                output.writeUTF(item.getTime());
            }

            output.flush();
            output.close();
        } catch (FileNotFoundException e) {
            GameServer.getLogger().log(Level.SEVERE, "ruh roh", e);
        } catch (IOException e) {
            GameServer.getLogger().log(Level.SEVERE, "ruh roh", e);
        }
    }

    /**
     * Gets the dropLog
     *
     * @return the dropLog
     */
    public ArrayList<DropLogItem> getDropLog() {
        return dropLog;
    }

    /**
     * Represents a single drop log item
     */
    public static class DropLogItem {

        /**
         * The npc killed
         */
        private String npcName;

        /**
         * The item
         */
        private Item item;

        /**
         * The drop date
         */
        private String date;

        /**
         * The drop time
         */
        private String time;

        /**
         * Represents a drop log item
         *
         * @param item the item
         * @param date the date
         * @param time the time
         */
        public DropLogItem(String npcName, Item item, String date, String time) {
            this.setNpcName(npcName);
            this.setItem(item);
            this.setDate(date);
            this.setTime(time);
        }

        /**
         * Gets the npcName
         *
         * @return the npcName
         */
        public String getNpcName() {
            return npcName;
        }

        /**
         * Sets the npcName
         *
         * @param npcName the npcName
         */
        public void setNpcName(String npcName) {
            this.npcName = npcName;
        }

        /**
         * Gets the item
         *
         * @return the item
         */
        public Item getItem() {
            return item;
        }

        /**
         * Sets the item
         *
         * @param item the item
         */
        public void setItem(Item item) {
            this.item = item;
        }

        /**
         * Gets the date
         *
         * @return the date
         */
        public String getDate() {
            return date;
        }

        /**
         * Sets the date
         *
         * @param date the date
         */
        public void setDate(String date) {
            this.date = date;
        }

        /**
         * Gets the time
         *
         * @return the time
         */
        public String getTime() {
            return time;
        }

        /**
         * Sets the time
         *
         * @param time the time
         */
        public void setTime(String time) {
            this.time = time;
        }
    }
}