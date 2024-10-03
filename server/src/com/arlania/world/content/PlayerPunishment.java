/**
 * @@@@ Replace the whole PlayerPunishments.java with the following @@@@
 **/

package com.arlania.world.content;

import com.arlania.GameServer;
import com.arlania.GameSettings;
import com.arlania.model.Position;
import com.arlania.util.Misc;
import com.arlania.world.entity.impl.player.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;


public class PlayerPunishment {

    private static final String BAN_DIRECTORY = System.getProperty("user.home") + File.separator + "Saves" + File.separator + "Punishment" + File.separator;
    private static final String MUTE_DIRECTORY = System.getProperty("user.home") + File.separator + "Saves" + File.separator + "Punishment" + File.separator;
    private static final String STARTER_DIRECTORY = System.getProperty("user.home") + File.separator + "Saves" + File.separator + "Punishment" + File.separator;

    public static ArrayList<String> IPSBanned = new ArrayList<String>();
    public static ArrayList<String> IPSMuted = new ArrayList<String>();
    public static ArrayList<String> AccountsBanned = new ArrayList<String>();
    public static ArrayList<String> AccountsMuted = new ArrayList<String>();
    public static ArrayList<String> starterRecieved1 = new ArrayList<String>();
    public static ArrayList<String> starterRecieved2 = new ArrayList<String>();
    public static ArrayList<String> IPStarters1 = new ArrayList<String>();
    public static ArrayList<String> IPStarters2 = new ArrayList<String>();
    public static ArrayList<String> Referrals1 = new ArrayList<String>();
    public static ArrayList<String> Referrals2 = new ArrayList<String>();
    //public static ArrayList<String> Starters1 = new ArrayList<String>();
    //public static ArrayList<String> Starters2 = new ArrayList<String>();


    public static void init() {
        initializeList(BAN_DIRECTORY, "IPBans", IPSBanned);
        initializeList(BAN_DIRECTORY, "Bans", AccountsBanned);
        initializeList(MUTE_DIRECTORY, "IPMutes", IPSMuted);
        initializeList(STARTER_DIRECTORY, "IPStarters1", IPStarters1);
        initializeList(STARTER_DIRECTORY, "IPStarters2", IPStarters2);
        initializeList(STARTER_DIRECTORY, "Referrals1", Referrals1);
        initializeList(STARTER_DIRECTORY, "Referrals2", Referrals2);
        initializeList(MUTE_DIRECTORY, "Mutes", AccountsMuted);
    }

    public static void initializeList(String directory, String file, ArrayList<String> list) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(directory + file + ".txt"));
            String data = null;
            while ((data = in.readLine()) != null) {
                list.add(data);
            }
            in.close();
        } catch (IOException e) {
            GameServer.getLogger().log(Level.SEVERE, "ruh roh", e);
        }
    }

    public static void addBannedIP(String IP) {
        if (!IPSBanned.contains(IP)) {
            addToFile(BAN_DIRECTORY + "IPBans.txt", IP);
            IPSBanned.add(IP);
        }
    }

    public static void addMutedIP(String IP) {
        if (!IPSMuted.contains(IP)) {
            addToFile(MUTE_DIRECTORY + "IPMutes.txt", IP);
            IPSMuted.add(IP);
        }
    }

    public static void appendStarters1(String IP) {
        if (!starterRecieved1.contains(IP)) {
            addToFile(STARTER_DIRECTORY + "Starters1.txt", IP);
            starterRecieved1.add(IP);
        }
    }

    public static void appendStarters2(String IP) {
        if (!starterRecieved2.contains(IP)) {
            addToFile(STARTER_DIRECTORY + "Starters2.txt", IP);
            starterRecieved2.add(IP);
        }
    }

    public static void addIpToStarterList1(String IP) {
        if (!IPStarters1.contains(IP)) {
            addToFile(STARTER_DIRECTORY + "IPStarters1.txt", IP);
            IPStarters1.add(IP);
        }
    }

    public static void addIpToStarterList2(String IP) {
        if (!IPStarters2.contains(IP)) {
            addToFile(STARTER_DIRECTORY + "IPStarters2.txt", IP);
            IPStarters2.add(IP);
        }
    }

    public static void addIpToReferralList1(String IP) {
        if (!Referrals1.contains(IP)) {
            addToFile(STARTER_DIRECTORY + "Referrals1.txt", IP);
            Referrals1.add(IP);
        }
    }

    public static void addIpToReferralList2(String IP) {
        if (!Referrals2.contains(IP)) {
            addToFile(STARTER_DIRECTORY + "Referrals2.txt", IP);
            Referrals2.add(IP);
        }
    }

    public static void ban(String p) {
        p = Misc.formatPlayerName(p.toLowerCase());
        if (!AccountsBanned.contains(p)) {
            addToFile(BAN_DIRECTORY + "Bans.txt", p);
            AccountsBanned.add(p);
        }
    }

    public static void mute(String p) {
        p = Misc.formatPlayerName(p.toLowerCase());
        if (!AccountsMuted.contains(p)) {
            addToFile(MUTE_DIRECTORY + "Mutes.txt", p);
            AccountsMuted.add(p);
        }
    }

    public static boolean banned(String player) {
        player = Misc.formatPlayerName(player.toLowerCase());
        return AccountsBanned.contains(player);
    }

    public static boolean muted(String player) {
        player = Misc.formatPlayerName(player.toLowerCase());
        return AccountsMuted.contains(player);
    }

    public static boolean IPBanned(String IP) {
        return IPSBanned.contains(IP);
    }

    public static boolean IPMuted(String IP) {
        return IPSMuted.contains(IP);
    }

    public static boolean hasRecieved1stStarter(String IP) {
        return starterRecieved1.contains(IP);
    }

    public static boolean hasRecieved2ndStarter(String IP) {
        return starterRecieved2.contains(IP);
    }

    public static boolean hasRecieved1stReferral(String IP) {
        return Referrals1.contains(IP);
    }

    public static boolean hasRecieved2ndReferral(String IP) {
        return Referrals2.contains(IP);
    }

    public static void addIpToStarter1(String IP) {
        starterRecieved1.add(IP);
        addIpToStarterList1(IP);
    }

    public static void addIpToStarter2(String IP) {
        starterRecieved2.add(IP);
        addIpToStarterList2(IP);
    }

    public static void unban(String player) {
        player = Misc.formatPlayerName(player.toLowerCase());
        deleteFromFile(BAN_DIRECTORY + "Bans.txt", player);
        AccountsBanned.remove(player);
    }

    public static void unmute(String player) {
        player = Misc.formatPlayerName(player.toLowerCase());
        deleteFromFile(MUTE_DIRECTORY + "Mutes.txt", player);
        AccountsMuted.remove(player);
    }

    public static void reloadIPBans() {
        IPSBanned.clear();
        initializeList(BAN_DIRECTORY, "IPBans", IPSBanned);
    }

    public static void reloadBans() {
        AccountsBanned.clear();
        initializeList(BAN_DIRECTORY, "Bans", AccountsBanned);
    }

    public static void reloadIPMutes() {
        IPSMuted.clear();
        initializeList(MUTE_DIRECTORY, "IPMutes", IPSMuted);
    }

    public static void deleteFromFile(String file, String name) {
        GameServer.getLoader().getEngine().submit(() -> {
            try {
                BufferedReader r = new BufferedReader(new FileReader(file));
                ArrayList<String> contents = new ArrayList<String>();
                while (true) {
                    String line = r.readLine();
                    if (line == null) {
                        break;
                    } else {
                        line = line.trim();
                    }
                    if (!line.equalsIgnoreCase(name)) {
                        contents.add(line);
                    }
                }
                r.close();
                BufferedWriter w = new BufferedWriter(new FileWriter(file));
                for (String line : contents) {
                    w.write(line, 0, line.length());
                    w.newLine();
                }
                w.flush();
                w.close();
            } catch (Exception e) {
                GameServer.getLogger().log(Level.SEVERE, "ruh roh", e);
            }
        });
    }

    public static void addToFile(String file, String data) {
        GameServer.getLoader().getEngine().submit(() -> {
            try {
                BufferedWriter out = new BufferedWriter(new FileWriter(file, true));
                try {
                    out.newLine();
                    out.write(data);
                } finally {
                    out.close();
                }
            } catch (IOException e) {
                GameServer.getLogger().log(Level.SEVERE, "ruh roh", e);
            }
        });
    }

    public static class Jail {

        private static final Player[] JAILED_PLAYERS = new Player[10];

        public static boolean jailPlayer(Player p) {
            int emptyCell = findSlot();
            if (emptyCell == -1) {
                return false;
            }
            Position pos = null;
            switch (emptyCell) {
                case 0:
                    pos = new Position(2606, 3105);
                    break;
                case 1:
                    pos = new Position(2606, 3105);
                    break;
                case 2:
                    pos = new Position(2606, 3105);
                    break;
                case 3:
                    pos = new Position(2606, 3105);
                    break;
                case 4:
                    pos = new Position(2606, 3105);
                    break;
                case 5:
                    pos = new Position(2606, 3105);
                    break;
                case 6:
                    pos = new Position(2606, 3105);
                    break;
                case 7:
                    pos = new Position(2606, 3105);
                    break;
                case 8:
                    pos = new Position(2606, 3105);
                    break;
                case 9:
                    pos = new Position(2606, 3105);
                    break;
            }
            p.moveTo(pos);
            JAILED_PLAYERS[emptyCell] = p;
            return true;
        }

        public static void unjail(Player plr) {
            int index = getIndex(plr);
            if (index >= 0) {
                JAILED_PLAYERS[index] = null;
            }
            plr.moveTo(GameSettings.DEFAULT_POSITION.copy());
        }

        public static boolean isJailed(Player plr) {
            return getIndex(plr) >= 0;
        }

        public static int getIndex(Player plr) {
            for (int i = 0; i < JAILED_PLAYERS.length; i++) {
                Player p = JAILED_PLAYERS[i];
                if (p == null)
                    continue;
                if (p == plr) {
                    return i;
                }
            }
            return -1;
        }

        public static int findSlot() {
            for (int i = 0; i < JAILED_PLAYERS.length; i++) {
                if (JAILED_PLAYERS[i] == null) {
                    return i;
                }
            }
            return -1;
        }

    }

}
