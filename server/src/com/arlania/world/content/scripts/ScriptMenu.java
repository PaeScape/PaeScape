package com.arlania.world.content.scripts;

import com.arlania.model.StaffRights;
import com.arlania.world.entity.impl.player.Player;

public class ScriptMenu {

    private static final int[][] LINE_IDS = {{60662, 60663}, {60664, 60665}, {60666, 60667}, {60668, 60669}, {60670, 60671}, {60672, 60673}, {60674, 60675}, {60676, 60677}, {60678, 60679}, {18374, 60701}, {60702, 60703}, {60704, 60705}};
    private static final int[] BUTTON_IDS = {-4914, -4911, -4908, -4905, -4902, -4899, -4896, -4893, -4890, -4845, -4842, -4839};
    private static final int[] TAB_IDS = {-4934, -4931, -4928, -4925, -4922, -4919};
    private static final int[] INTERFACE_IDS = {60600, 60700, 60800, 60900, 61000, 61100};


    public static void script(Player player, int button) {
        for (int i = 0; i < BUTTON_IDS.length; i++) {
            if (button == BUTTON_IDS[i]) {
                player.destination = i;
            }
        }
        if (player.lastClickedTab == 1)
            WorkerNpcScripts(player, player.destination);
        else if (player.lastClickedTab == 2)
            CombatScripts(player, player.destination);
        else if (player.lastClickedTab == 3)
            SkillingScripts1(player, player.destination);
        else if (player.lastClickedTab == 4)
            SkillingScripts2(player, player.destination);
        else if (player.lastClickedTab == 5)
            ScriptsTBD1(player, player.destination);
        else if (player.lastClickedTab == 6)
            ScriptsTBD2(player, player.destination);
    }

    /**
     * Training script method.
     *
     * @param client      The player.scripting to a training area.
     * @param destination The destination being scripted to.
     */
    public static void WorkerNpcScripts(Player player, int destination) {
        for (final WorkerNpcScripts.WorkerNpc t : WorkerNpcScripts.WorkerNpc.values()) {
            if (destination == t.ordinal()) {
                if (t.getCoordinates()[2] == 10) {
                    player.sendMessage("This script is currently unavailable.");
                    return;
                }

            }
        }
    }

    /**
     * Minigame script method.
     *
     * @param client      The player.scripting to a minigame area.
     * @param destination The destination being scripted to.
     */
    public static void CombatScripts(Player player, int destination) {
        for (final CombatScripts.Combat m : CombatScripts.Combat.values()) {
            if (destination == m.ordinal()) {
                if (m.getCoordinates()[2] == 10) {
                    player.sendMessage("This script is currently unavailable.");
                    return;
                }

            }
        }
    }

    /**
     * Bosses script method.
     *
     * @param client      The player.scripting to a bosses area.
     * @param destination The destination being scripted to.
     */
    public static void SkillingScripts1(Player player, int destination) {
        for (final SkillingScripts1.Skilling1 b : SkillingScripts1.Skilling1.values()) {
            if (destination == b.ordinal()) {
                if (b.getCoordinates()[0] == 3088) {
                    //player.sendMessage("This script is currently unavailable.");
                    return;

                }
            }
        }
    }

    /**
     * Player killing script method.
     *
     * @param client      The player.scripting to a player killing area.
     * @param destination The destination being scripted to.
     */
    public static void SkillingScripts2(Player player, int destination) {

        for (final SkillingScripts2.Skilling2 p : SkillingScripts2.Skilling2.values()) {
            if (destination == p.ordinal()) {
                if (p.getCoordinates()[2] == 10) {
                    player.sendMessage("This script is currently unavailable.");
                    return;
                }

            }
        }
    }


    /**
     * TBD1 script method.
     *
     * @param client      The player.scripting to a TBD1 area.
     * @param destination The destination being scripted to.
     */
    public static void ScriptsTBD1(Player player, int destination) {
        for (final ScriptTBD1.TBD1 s : ScriptTBD1.TBD1.values()) {
            if (destination == s.ordinal()) {
                if (s.getCoordinates()[2] == 10) {
                    player.sendMessage("This script is currently unavailable.");
                    return;
                }


            }
        }
    }

    /**
     * TBD2 script method.
     *
     * @param client      The player.scripting to a donator area.
     * @param destination The destination being scripted to.
     */
    public static void ScriptsTBD2(Player player, int destination) {

        for (final ScriptTBD2.TBD2 d : ScriptTBD2.TBD2.values()) {
            if (destination == d.ordinal()) {
                if (d.getCoordinates()[2] == 10) {
                    player.sendMessage("This script is currently unavailable.");
                    return;
                }


            }
        }
    }

    /**
     * Opening a tab in the scripts interface.
     *
     * @param client player.opening the tab.
     * @param button Tab id being opened.
     */
    public static void openTab(Player player, int button) {
        if (button == 236201 && player.getStaffRights() == StaffRights.PLAYER) {
            player.sendMessage("You need to be a donator to access this tab.");
            return;
        }
        for (int i = 0; i < TAB_IDS.length; i++) {
            if (button == TAB_IDS[i]) {
                player.lastClickedTab = i + 1;
                player.getPacketSender().sendInterface(INTERFACE_IDS[i]);
            }
        }
        switch (player.lastClickedTab) {
            case 1:
                for (WorkerNpcScripts.WorkerNpc t : WorkerNpcScripts.WorkerNpc.values()) {
                    player.getPacketSender().sendTeleString(t.getScriptName()[0], LINE_IDS[t.ordinal()][0]);
                    player.getPacketSender().sendTeleString(t.getScriptName()[1], LINE_IDS[t.ordinal()][1]);
                }
                break;
            case 2:
                for (final CombatScripts.Combat m : CombatScripts.Combat.values()) {
                    player.getPacketSender().sendTeleString(m.getScriptName()[0], LINE_IDS[m.ordinal()][0]);
                    player.getPacketSender().sendTeleString(m.getScriptName()[1], LINE_IDS[m.ordinal()][1]);
                }
                break;
            case 3:
                for (final SkillingScripts1.Skilling1 b : SkillingScripts1.Skilling1.values()) {
                    player.getPacketSender().sendTeleString(b.getScriptName()[0], LINE_IDS[b.ordinal()][0]);
                    player.getPacketSender().sendTeleString(b.getScriptName()[1], LINE_IDS[b.ordinal()][1]);
                }
                break;
            case 4:
                for (final SkillingScripts2.Skilling2 p : SkillingScripts2.Skilling2.values()) {
                    player.getPacketSender().sendTeleString(p.getScriptName()[0], LINE_IDS[p.ordinal()][0]);
                    player.getPacketSender().sendTeleString(p.getScriptName()[1], LINE_IDS[p.ordinal()][1]);
                }
                break;
            case 5:
                for (final ScriptTBD1.TBD1 s : ScriptTBD1.TBD1.values()) {
                    player.getPacketSender().sendTeleString(s.getScriptName()[0], LINE_IDS[s.ordinal()][0]);
                    player.getPacketSender().sendTeleString(s.getScriptName()[1], LINE_IDS[s.ordinal()][1]);
                }
                break;
            case 6:
                for (final ScriptTBD2.TBD2 d : ScriptTBD2.TBD2.values()) {
                    player.getPacketSender().sendTeleString(d.getScriptName()[0], LINE_IDS[d.ordinal()][0]);
                    player.getPacketSender().sendTeleString(d.getScriptName()[1], LINE_IDS[d.ordinal()][1]);
                }
                break;
        }
    }


}
