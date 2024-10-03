package com.arlania.world.content;

import com.arlania.model.GameMode;
import com.arlania.util.Misc;
import com.arlania.util.Stopwatch;
import com.arlania.world.World;
import com.arlania.world.entity.impl.player.Player;

import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.CopyOnWriteArrayList;

public class PlayersOnlineInterface {

    private static final Stopwatch lastResort = new Stopwatch();
    private final static CopyOnWriteArrayList<Player> PLAYERS_ONLINE_LIST = new CopyOnWriteArrayList<Player>();

    public static void add(Player player) {
        PLAYERS_ONLINE_LIST.add(player);
    }

    public static void remove(Player player) {
        PLAYERS_ONLINE_LIST.remove(player);
    }

    private static void resort() {
        if (!lastResort.elapsed(1000)) {
            return;
        }
        lastResort.reset();
        Collections.sort(PLAYERS_ONLINE_LIST, new Comparator<Player>() {
            @Override
            public int compare(Player arg0, Player arg1) {
                int value1 = getValue(arg0);
                int value2 = getValue(arg1);
                if (value1 == value2) {
                    return 0;
                } else if (value1 > value2) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });
    }

    private static void clearInterface(Player player) {
        for (int i = 57042; i < 57141; i++) {
            player.getPacketSender().sendString(i, "");
        }
        int i = 57008;
        player.getPacketSender().sendString(i++, "Name:");
        player.getPacketSender().sendString(i++, "Rank:");
        player.getPacketSender().sendString(i++, "Time Played:");
        player.getPacketSender().sendString(i++, "Claimed:");
        player.getPacketSender().sendString(i++, "Game Mode:");
        player.getPacketSender().sendString(i++, "Combat Level:");
        player.getPacketSender().sendString(i++, "Total Level:");
        //player.getPacketSender().sendString(i++, "Slayer Points:");
        //player.getPacketSender().sendString(i++, "Opened Presents:");
        //player.getPacketSender().sendString(i++, "Opened 3rd Age:");
        //player.getPacketSender().sendString(i++, "Opened Holiday items:");
        //player.getPacketSender().sendString(i++, "Dung Points:");
        player.getPacketSender().sendString(i++, "Pk Points:");
        player.getPacketSender().sendString(i++, "Wild Streak:");
        player.getPacketSender().sendString(i++, "Wild Kills:");
        player.getPacketSender().sendString(i++, "Wil Deaths:");
        player.getPacketSender().sendString(i++, "Arena Victories:");
        player.getPacketSender().sendString(i++, "Arena Losses:");
    }

    private static void sendInterfaceData(Player player) {
        int child = 57042;
        int fakeCount = (int) (World.getPlayers().size() * 1.3);
        for (int i = 0; i < fakeCount; i++) {
            if (i >= PLAYERS_ONLINE_LIST.size()) {
                player.getPacketSender().sendString(child, "   N/A");
                child++;
                continue;
            }
            Player p = PLAYERS_ONLINE_LIST.get(i);
            if (p == null)
                continue;
            int rankId = p.getStaffRights().ordinal();
            if (rankId == 0) {
                if (p.getGameMode() == GameMode.IRONMAN) {
                    rankId = 33;
                } else if (p.getGameMode() == GameMode.ULTIMATE_IRONMAN) {
                    rankId = 32;
                } else if (p.getGameMode() == GameMode.HARDCORE_IRONMAN) {
                    rankId = 31;
                }
            }
            player.getPacketSender().sendString(child, (rankId > 0 ? "<img=" + rankId + ">" : "  ") + p.getUsername());
            child++;
        }
    }

    public static void showInterface(Player player) {
        resort();
        clearInterface(player);
        sendInterfaceData(player);
        player.getPacketSender().sendString(57003, "Players:  @gre@" + (int) (World.getPlayers().size() * 1.3)).sendInterface(57000);
    }

    private static void updateInterface(Player player, int index) {
        if (index >= PLAYERS_ONLINE_LIST.size() || PLAYERS_ONLINE_LIST.get(index) == null) {
            showInterface(player);
            player.getPacketSender().sendMessage("That player is currently unavailable.");
            return;
        }
        Player player2 = PLAYERS_ONLINE_LIST.get(index);
        player.setPlayerViewingIndex(index);
        player.getPacketSender().sendString(57008, "Name: @whi@" + player2.getUsername()).sendString(57009, "Rank: @whi@" + Misc.formatText(player2.getStaffRights().name().toLowerCase())).
                sendString(57010, "Time Played: @whi@" + Misc.getHoursPlayed((player2.getTotalPlayTime() + player2.getRecordedLogin().elapsed()))).sendString(57011, "Claimed: @whi@$" + player2.getPointsHandler().getAmountDonated()).
                sendString(57012, "Game Mode: @whi@" + Misc.formatText(player2.getGameMode().name().toLowerCase())).sendString(57013, "Combat Level: @whi@" + player2.getSkillManager().getCombatLevel()).
                sendString(57014, "Total Level: @whi@ " + player2.getSkillManager().getTotalLevel()).sendString(57015, "Slayer Points: @whi@" + player2.getPointsHandler().getSlayerPoints()).
                sendString(57016, "Total Boss Kills: @whi@" + player2.getPointsHandler().gettotalbosskills()).sendString(57017, "Opened Presents: @whi@" + player2.getPointsHandler().getopenedpresents()).
                sendString(57016, "Opened 3rd Age: @whi@" + player2.getPointsHandler().getopened3rdage()).sendString(57017, "Opened Holiday items: @whi@" + player2.getPointsHandler().getopenedholiday()).
                sendString(57018, "Dung Tokens: @whi@" + player2.getPointsHandler().getDungeoneeringTokens()).sendString(57019, "Pk Points: @whi@" + player2.getPointsHandler().getPkPoints()).
                sendString(57020, "Wild Streak: @whi@" + player2.getPlayerKillingAttributes().getPlayerKillStreak()).sendString(57021, "Player Kills: @whi@" + player2.getPlayerKillingAttributes().getPlayerKills()).
                sendString(57022, "Wild Deaths: @whi@" + player2.getPlayerKillingAttributes().getPlayerDeaths()).sendString(57023, "Arena Victories: @whi@" + player2.getDueling().arenaStats[0]).
                sendString(57024, "Arena Losses: @whi@" + player2.getDueling().arenaStats[1]);
    }

    public static boolean handleButton(Player player, int button) {
        if (button >= 57042 && button <= 57129) {
            int index = (button - 57042);
            updateInterface(player, index);
            return true;
        }
        if (button == 57025 || button == 57028) {
            Player p = player.getPlayerViewingIndex() < PLAYERS_ONLINE_LIST.size() ? PLAYERS_ONLINE_LIST.get(player.getPlayerViewingIndex()) : null;
            if (p == null) {
                player.getPacketSender().sendMessage("Please select an active player.");
                return true;
            }
            player.getPacketSender().sendString(button == 57025 ? 57025 : 57028, p.getUsername());
            return true;
        }
        return false;
    }

    private static int getValue(Player p) {
        int value = 0;
        switch (p.getStaffRights()) {
            case PLAYER:
                value = 0;
                break;
            case ADMINISTRATOR:
                value = 11;
                break;
		/*case DONATOR:
			value = 3;
			break;*/
            case DEVELOPER:
                value = 13;
                break;
		/*case UBER_DONATOR:
			value = 7;
			break;*/
		/*case EXTREME_DONATOR:
			value = 5;
			break;*/
            case MODERATOR:
                value = 10;
                break;
            case OWNER:
                value = 12;
                break;
		/*case LEGENDARY_DONATOR:
			value = 6;
			break;*/
		/*case SUPER_DONATOR:
			value = 4;
			break;*/
		/*case SUPPORT:
			value = 9;
			break;*/
		/*case YOUTUBER:
			value = 8;
			break;*/
        }


        if (value == 0) {
            if (p.getGameMode() == GameMode.IRONMAN) {
                value = 1;
            } else if (p.getGameMode() == GameMode.ULTIMATE_IRONMAN) {
                value = 2;
            } else if (p.getGameMode() == GameMode.HARDCORE_IRONMAN) {
                value = 3;
            }
        }
        return value;
    }
}
