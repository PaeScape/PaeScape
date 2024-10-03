package com.arlania.net.security;

import com.arlania.GameLoader;
import com.arlania.GameServer;
import com.arlania.GameSettings;
import com.arlania.model.GameMode;
import com.arlania.net.login.LoginDetailsMessage;
import com.arlania.net.login.LoginResponses;
import com.arlania.world.content.PlayerPunishment;
import com.arlania.world.entity.impl.player.Player;

import java.io.*;
import java.util.*;
import java.util.logging.Level;

/**
 * A lot of connection-related stuff.
 * Really messy.
 */

public class ConnectionHandler {

    public static void init() {
        loadHostBlacklist();
        loadBannedComputers();
        loadStarters();
    }

    public static int getResponse(Player player, LoginDetailsMessage msg) {

        String host = msg.getHost();
        String hardwareID = msg.getHardwareID();

        if (PlayerPunishment.banned(player.getUsername())) {
            return LoginResponses.LOGIN_DISABLED_ACCOUNT;
        }

        if(player.getGameMode() == GameMode.SEASONAL_IRONMAN) {
            if (player.seasonMonth != GameLoader.getMonth() || player.seasonYear != GameLoader.getYear()) {
                return LoginResponses.EXPIRED_SEASONAL_ACCOUNT;
            }
        }

        if (isBlocked(host)) {
            return LoginResponses.LOGIN_REJECT_SESSION;
        }
        if (isBlockedHardwareID(hardwareID)) {
            return LoginResponses.LOGIN_DISABLED_COMPUTER;
        }
        if (PlayerPunishment.IPBanned(host)) {
            return LoginResponses.LOGIN_DISABLED_IP;
        }

        if (!isLocal(host)) {
            if (CONNECTIONS.get(host) != null) {
                if (CONNECTIONS.get(host) >= GameSettings.CONNECTION_AMOUNT) {
                    //System.out.println("Connection limit reached : "+player.getUsername()+". Host: "+host);
                    return LoginResponses.LOGIN_CONNECTION_LIMIT;//LoginResponses.LOGIN_SUCCESSFUL;
                }
            }
        }
        return LoginResponses.LOGIN_SUCCESSFUL;
    }

    /**
     * BLACKLISTED CONNECTIONS SUCH AS PROXIES
     **/
    private static final String BLACKLIST_DIR = System.getProperty("user.home") + File.separator + "Saves" + File.separator + "Punishment" + File.separator + "blockedhosts.txt";
    private static final List<String> BLACKLISTED_HOSTNAMES = new ArrayList<String>();

    /**
     * BLACKLISTED HARDWARE NUMBERS
     **/
    private static final String BLACKLISTED_HARDWARE_IDENTIFIERS_DIR = System.getProperty("user.home") + File.separator + "Saves" + File.separator + "Punishment" + File.separator + "blockedhardwares.txt";
    private static final List<String> BLACKLISTED_HARDWARE_IDENTIFIERS = new ArrayList<>();

    /**
     * The concurrent map of registered connections.
     */
    private static final Map<String, Integer> CONNECTIONS = Collections.synchronizedMap(new HashMap<String, Integer>());

    /**
     * SAVED STARTERS
     **/
    private static final String STARTER_FILE = "./data/saves/starters.txt";

    /**
     * The concurrent map of registered connections.
     */
    private static final Map<String, Integer> STARTERS = Collections.synchronizedMap(new HashMap<String, Integer>());

    private static void loadHostBlacklist() {
        String word = null;
        try {
            BufferedReader in = new BufferedReader(
                    new FileReader(BLACKLIST_DIR));
            while ((word = in.readLine()) != null)
                BLACKLISTED_HOSTNAMES.add(word.toLowerCase());
            in.close();
            in = null;
        } catch (final Exception e) {
            GameServer.getLogger().log(Level.SEVERE, "Could not load blacklisted hosts.", e);
        }
    }

    public static boolean isBlocked(String host) {
        return BLACKLISTED_HOSTNAMES.contains(host.toLowerCase());
    }

	
	/*private static void loadBannedComputers() {
		String line = null;
		try {
			BufferedReader in = new BufferedReader(
					new FileReader(BLACKLISTED_HARDWARE_IDENTIFIERS_DIR));
			while ((line = in.readLine()) != null) {
				if(line.contains("="))
					BLACKLISTED_HARDWARE_IDENTIFIERS.add(line.substring(line.indexOf("=")+1));
			}
			in.close();
			in = null;
		} catch (final Exception e) {
			System.out.println("Could not load blacklisted hadware numbers.");
		}
	}*/

    private static void loadBannedComputers() {
        String line = null;
        try {
            BufferedReader in = new BufferedReader(
                    new FileReader(BLACKLISTED_HARDWARE_IDENTIFIERS_DIR));
            while ((line = in.readLine()) != null) {
                if (line.contains("="))
                    BLACKLISTED_HARDWARE_IDENTIFIERS.add(line.substring(line.indexOf("=") + 1));
            }
            in.close();
            in = null;
        } catch (final Exception e) {
            GameServer.getLogger().log(Level.SEVERE, "Could not load blacklisted hardware numbers.", e);
        }
    }

    public static void banComputer(String playername, String hardwareID) {
        if (BLACKLISTED_HARDWARE_IDENTIFIERS.contains(hardwareID))
            return;
        BLACKLISTED_HARDWARE_IDENTIFIERS.add(hardwareID);
        GameServer.getLoader().getEngine().submit(() -> {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(BLACKLISTED_HARDWARE_IDENTIFIERS_DIR, true));
                writer.write(playername + "=" + hardwareID);
                writer.newLine();
                writer.close();
            } catch (IOException e) {
                GameServer.getLogger().log(Level.SEVERE, "Error banning " + playername + " with hwid " + hardwareID, e);
            }
        });
    }
	
	/*public static void banComputer(String playername, String mac) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(BLACKLISTED_HARDWARE_IDENTIFIERS_DIR, true));
			writer.write(""+playername+"="+mac);
			writer.newLine();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(!BLACKLISTED_HARDWARE_IDENTIFIERS.contains(mac))
			BLACKLISTED_HARDWARE_IDENTIFIERS.add(mac);
	}*/

    public static void reloadUUIDBans() {
        BLACKLISTED_HARDWARE_IDENTIFIERS.clear();
        loadBannedComputers();
    }

    public static boolean isBlockedHardwareID(String host) {
        return BLACKLISTED_HARDWARE_IDENTIFIERS.contains(host);
    }

    public static void loadStarters() {
        try {
            BufferedReader r = new BufferedReader(new FileReader(STARTER_FILE));
            while (true) {
                String line = r.readLine();
                if (line == null) {
                    break;
                } else {
                    line = line.trim();
                }
                addStarter(line, false);
            }
            r.close();
        } catch (IOException e) {
            GameServer.getLogger().log(Level.SEVERE, "Error loading starters", e);
        }
    }


    public static void add(String host) {
        if (!isLocal(host)) {
            if (CONNECTIONS.get(host) == null) {
                CONNECTIONS.put(host, 1);
            } else {
                int amt = CONNECTIONS.get(host) + 1;
                CONNECTIONS.put(host, amt);
            }
        }
    }

    public static void remove(String host) {
        if (!isLocal(host)) {
            if (CONNECTIONS.get(host) != null) {
                int amt = CONNECTIONS.get(host) - 1;
                if (amt == 0) {
                    CONNECTIONS.remove(host);
                } else {
                    CONNECTIONS.put(host, amt);
                }
            }
        }
    }

    public static int getStarters(String host) {
        if (host == null) {
            return GameSettings.MAX_STARTERS_PER_IP;
        }
        if (!ConnectionHandler.isLocal(host)) {
            if (STARTERS.get(host) != null) {
                return STARTERS.get(host);
            }
        }
        return 0;
    }

    public static void addStarter(String host, boolean write) {
        if (!ConnectionHandler.isLocal(host)) {
            if (STARTERS.get(host) == null) {
                STARTERS.put(host, 1);
            } else {
                int amt = STARTERS.get(host) + 1;
                STARTERS.put(host, amt);
            }
            if (write) {
                GameServer.getLoader().getEngine().submit(() -> {
                    try {
                        BufferedWriter writer = new BufferedWriter(new FileWriter(STARTER_FILE, true));
                        writer.write(host);
                        writer.newLine();
                        writer.close();
                    } catch (IOException e) {
                        GameServer.getLogger().log(Level.SEVERE, "Error adding starter for " + host, e);
                    }
                });
            }
        }
    }

    /**
     * Determines if the specified host is connecting locally.
     *
     * @param host the host to check if connecting locally.
     * @return {@code true} if the host is connecting locally, {@code false}
     * otherwise.
     */
    public static boolean isLocal(String host) {
        return host == null || host.equals("null") || host.equals("127.0.0.1") || host.equals("localhost");
    }
}
