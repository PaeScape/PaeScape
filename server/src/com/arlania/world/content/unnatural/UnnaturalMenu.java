package com.arlania.world.content.unnatural;

import com.arlania.world.content.unnatural.Duradel.DuradelTasks;
import com.arlania.world.content.unnatural.EvilDave.EvilDaveTasks;
import com.arlania.world.content.unnatural.Kuradel.KuradelTasks;
import com.arlania.world.content.unnatural.Nieve.NieveTasks;
import com.arlania.world.content.unnatural.Sumona.SumonaTasks;
import com.arlania.world.content.unnatural.Vannaka.VannakaTasks;
import com.arlania.world.content.skill.impl.slayer.SlayerMaster;
import com.arlania.world.entity.impl.player.Player;

public class UnnaturalMenu {

    private static final int[][] LINE_IDS = {{60662, 60663}, {60664, 60665}, {60666, 60667}, {60668, 60669}, {60670, 60671}, {60672, 60673}, {60674, 60675}, {60676, 60677}, {60678, 60679}, {18374, 60701}, {60702, 60703}, {60704, 60705}};
    private static final int[] BUTTON_IDS = {60622, 60625, 60628, 60631, 60634, 60637, 60640, 60643, 60646, 60691, 60694, 60697
    };
    private static final int[] TAB_IDS = {60602, 60605, 60608, 60611, 60614, 60617};
    private static final int[] INTERFACE_IDS = {60600, 60700, 60800, 60900, 61000, 61100};

    /**
     * Method that handles the destination assigning.
     *
     * @param client The player.assigning to the destination.    //sec
     * @param button Button id being clicked to get the destination.
     */
    public static void assign(Player player, int button) {
        for (int i = 0; i < BUTTON_IDS.length; i++) {
            if (button == BUTTON_IDS[i]) {
                player.destination = i;
            }
        }
        if (player.lastClickedTab == 1)
            assignVannaka(player, player.destination);
        else if (player.lastClickedTab == 2)
            assignDuradel(player, player.destination);
        else if (player.lastClickedTab == 3)
            assignKuradel(player, player.destination);
        else if (player.lastClickedTab == 4)
            assignSumona(player, player.destination);
        else if (player.lastClickedTab == 5)
            assignNieve(player, player.destination);
        else if (player.lastClickedTab == 6)
            assignEvilDave(player, player.destination);

    }


    public static void assignVannaka(Player player, int taskId) {
        for (final Vannaka.VannakaTasks v : Vannaka.VannakaTasks.values()) {
            if (taskId == v.ordinal()) {

                if (v.getSlayerTaskId() == -1) {
                    player.getPacketSender().sendMessage("@red@You can't select this task right now.");
                    return;
                }

                player.getSlayer().setSlayerMaster(SlayerMaster.VANNAKA);
                player.getSlayer().assignUnnaturalTask(v.getSlayerTaskId());
            }
        }
    }

    public static void assignDuradel(Player player, int taskId) {
        for (final Duradel.DuradelTasks d : Duradel.DuradelTasks.values()) {
            if (taskId == d.ordinal()) {

                if (d.getSlayerTaskId() == -1) {
                    player.getPacketSender().sendMessage("@red@You can't select this task right now.");
                    return;
                }

                player.getSlayer().setSlayerMaster(SlayerMaster.DURADEL);
                player.getSlayer().assignUnnaturalTask(d.getSlayerTaskId());
            }
        }
    }

    public static void assignKuradel(Player player, int taskId) {
        for (final Kuradel.KuradelTasks k : Kuradel.KuradelTasks.values()) {
            if (taskId == k.ordinal()) {

                if (k.getSlayerTaskId() == -1) {
                    player.getPacketSender().sendMessage("@red@You can't select this task right now.");
                    return;
                }

                player.getSlayer().setSlayerMaster(SlayerMaster.KURADEL);
                player.getSlayer().assignUnnaturalTask(k.getSlayerTaskId());
            }
        }
    }

    public static void assignSumona(Player player, int taskId) {
        for (final Sumona.SumonaTasks s : Sumona.SumonaTasks.values()) {
            if (taskId == s.ordinal()) {

                if (s.getSlayerTaskId() == -1) {
                    player.getPacketSender().sendMessage("@red@You can't select this task right now.");
                    return;
                }

                player.getSlayer().setSlayerMaster(SlayerMaster.SUMONA);
                player.getSlayer().assignUnnaturalTask(s.getSlayerTaskId());
            }
        }
    }

    public static void assignNieve(Player player, int taskId) {
        for (final Nieve.NieveTasks r : Nieve.NieveTasks.values()) {
            if (taskId == r.ordinal()) {

                if (r.getSlayerTaskId() == -1) {
                    player.getPacketSender().sendMessage("@red@You can't select this task right now.");
                    return;
                }

                player.getSlayer().setSlayerMaster(SlayerMaster.NIEVE);
                player.getSlayer().assignUnnaturalTask(r.getSlayerTaskId());
            }
        }
    }

    public static void assignEvilDave(Player player, int taskId) {
        for (final EvilDave.EvilDaveTasks e : EvilDave.EvilDaveTasks.values()) {
            if (taskId == e.ordinal()) {

                if (e.getSlayerTaskId() == -1) {
                    player.getPacketSender().sendMessage("@red@You can't select this task right now.");
                    return;
                }

                player.getSlayer().setSlayerMaster(SlayerMaster.DAVE);
                player.getSlayer().assignUnnaturalTask(e.getSlayerTaskId());
            }
        }
    }


    public static void openTab(Player player, int button) {

        if (player.activeMenu == "slayer") {
            player.getPacketSender().sendString(60656, "Vannaka");
            player.getPacketSender().sendString(60657, "Duradel");
            player.getPacketSender().sendString(60658, "Kuradel");
            player.getPacketSender().sendString(60659, "Sumona");
            player.getPacketSender().sendString(60660, "Nieve");
            player.getPacketSender().sendString(60661, "Evil Dave");

            for (int i = 0; i < TAB_IDS.length; i++) {
                if (button == TAB_IDS[i]) {
                    player.lastClickedTab = i + 1;
                    player.getPacketSender().sendInterface(INTERFACE_IDS[i]);
                }
            }
            switch (player.lastClickedTab) {

                case 1:
                    for (final VannakaTasks s : Vannaka.VannakaTasks.values()) {
                        player.getPacketSender().sendTeleString(s.getassignName()[0], LINE_IDS[s.ordinal()][0]);
                        player.getPacketSender().sendTeleString(s.getassignName()[1], LINE_IDS[s.ordinal()][1]);
                    }
                    break;
                case 2:
                    for (final DuradelTasks d : Duradel.DuradelTasks.values()) {
                        player.getPacketSender().sendTeleString(d.getassignName()[0], LINE_IDS[d.ordinal()][0]);
                        player.getPacketSender().sendTeleString(d.getassignName()[1], LINE_IDS[d.ordinal()][1]);
                    }
                    break;
                case 3:
                    for (final KuradelTasks k : Kuradel.KuradelTasks.values()) {
                        player.getPacketSender().sendTeleString(k.getassignName()[0], LINE_IDS[k.ordinal()][0]);
                        player.getPacketSender().sendTeleString(k.getassignName()[1], LINE_IDS[k.ordinal()][1]);
                    }
                    break;
                case 4:
                    for (final SumonaTasks s : Sumona.SumonaTasks.values()) {
                        player.getPacketSender().sendTeleString(s.getassignName()[0], LINE_IDS[s.ordinal()][0]);
                        player.getPacketSender().sendTeleString(s.getassignName()[1], LINE_IDS[s.ordinal()][1]);
                    }
                    break;
                case 5:
                    for (final NieveTasks r : Nieve.NieveTasks.values()) {
                        player.getPacketSender().sendTeleString(r.getassignName()[0], LINE_IDS[r.ordinal()][0]);
                        player.getPacketSender().sendTeleString(r.getassignName()[1], LINE_IDS[r.ordinal()][1]);
                    }
                    break;
                case 6:
                    for (final EvilDaveTasks e : EvilDave.EvilDaveTasks.values()) {
                        player.getPacketSender().sendTeleString(e.getassignName()[0], LINE_IDS[e.ordinal()][0]);
                        player.getPacketSender().sendTeleString(e.getassignName()[1], LINE_IDS[e.ordinal()][1]);
                    }
                    break;
            }
        }
    }


}
