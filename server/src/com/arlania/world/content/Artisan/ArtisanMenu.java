package com.arlania.world.content.Artisan;

import com.arlania.model.Skill;
import com.arlania.world.content.Artisan.Dungeoneering.DungeoneeringTasks;
import com.arlania.world.content.Artisan.Fishing.FishingTasks;
import com.arlania.world.content.Artisan.Hunter.HunterTasks;
import com.arlania.world.content.Artisan.Mining.MiningTasks;
import com.arlania.world.content.Artisan.Thieving.ThievingTasks;
import com.arlania.world.content.Artisan.Woodcutting.WoodcuttingTasks;
import com.arlania.world.entity.impl.player.Player;

public class ArtisanMenu {

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
            assignWoodcutting(player, player.destination);
        else if (player.lastClickedTab == 2)
            assignMining(player, player.destination);
        else if (player.lastClickedTab == 3)
            assignFishing(player, player.destination);
        else if (player.lastClickedTab == 4)
            assignThieving(player, player.destination);
        else if (player.lastClickedTab == 5)
            assignDungeoneering(player, player.destination);
        else if (player.lastClickedTab == 6)
            assignHunter(player, player.destination);

    }


    public static void assignWoodcutting(Player player, int taskId) {
        for (final Woodcutting.WoodcuttingTasks v : Woodcutting.WoodcuttingTasks.values()) {
            if (taskId == v.ordinal()) {

                if (v.getskillerTaskId() == -1) {
                    player.getPacketSender().sendMessage("@red@You can't select this task right now.");
                    return;
                }

                player.setSkillerSkill(Skill.WOODCUTTING);
                player.getSkiller().assignArtisanTask(player, v.getskillerTaskId());
            }
        }
    }

    public static void assignMining(Player player, int taskId) {
        for (final Mining.MiningTasks d : Mining.MiningTasks.values()) {
            if (taskId == d.ordinal()) {

                if (d.getskillerTaskId() == -1) {
                    player.getPacketSender().sendMessage("@red@You can't select this task right now.");
                    return;
                }

                player.setSkillerSkill(Skill.MINING);
                player.getSkiller().assignArtisanTask(player, d.getskillerTaskId());
            }
        }
    }

    public static void assignFishing(Player player, int taskId) {
        for (final Fishing.FishingTasks k : Fishing.FishingTasks.values()) {
            if (taskId == k.ordinal()) {

                if (k.getskillerTaskId() == -1) {
                    player.getPacketSender().sendMessage("@red@You can't select this task right now.");
                    return;
                }

                player.setSkillerSkill(Skill.FISHING);
                player.getSkiller().assignArtisanTask(player, k.getskillerTaskId());
            }
        }
    }

    public static void assignThieving(Player player, int taskId) {
        for (final Thieving.ThievingTasks s : Thieving.ThievingTasks.values()) {
            if (taskId == s.ordinal()) {

                if (s.getskillerTaskId() == -1) {
                    player.getPacketSender().sendMessage("@red@You can't select this task right now.");
                    return;
                }

                player.setSkillerSkill(Skill.THIEVING);
                player.getSkiller().assignArtisanTask(player, s.getskillerTaskId());
            }
        }
    }

    public static void assignDungeoneering(Player player, int taskId) {
        for (final Dungeoneering.DungeoneeringTasks e : Dungeoneering.DungeoneeringTasks.values()) {
            if (taskId == e.ordinal()) {

                if (e.getskillerTaskId() == -1) {
                    player.getPacketSender().sendMessage("@red@You can't select this task right now.");
                    return;
                }

                player.setSkillerSkill(Skill.DUNGEONEERING);
                player.getSkiller().assignArtisanTask(player, e.getskillerTaskId());
            }
        }
    }

    public static void assignHunter(Player player, int taskId) {
        for (final Hunter.HunterTasks r : Hunter.HunterTasks.values()) {
            if (taskId == r.ordinal()) {

                if (r.getskillerTaskId() == -1) {
                    player.getPacketSender().sendMessage("@red@You can't select this task right now.");
                    return;
                }

                player.setSkillerSkill(Skill.HUNTER);
                player.getSkiller().assignArtisanTask(player, r.getskillerTaskId());
            }
        }
    }


    public static void openTab(Player player, int button) {

        if (player.activeMenu == "skiller") {
            player.getPacketSender().sendString(60656, "Woodcutting");
            player.getPacketSender().sendString(60657, "Mining");
            player.getPacketSender().sendString(60658, "Fishing");
            player.getPacketSender().sendString(60659, "Thieving");
            player.getPacketSender().sendString(60660, "Dungeoneering");
            player.getPacketSender().sendString(60661, "Hunter");

            for (int i = 0; i < TAB_IDS.length; i++) {
                if (button == TAB_IDS[i]) {
                    player.lastClickedTab = i + 1;
                    player.getPacketSender().sendInterface(INTERFACE_IDS[i]);
                }
            }
            switch (player.lastClickedTab) {

                case 1:
                    for (final WoodcuttingTasks s : Woodcutting.WoodcuttingTasks.values()) {
                        player.getPacketSender().sendTeleString(s.getassignName()[0], LINE_IDS[s.ordinal()][0]);
                        player.getPacketSender().sendTeleString(s.getassignName()[1], LINE_IDS[s.ordinal()][1]);
                    }
                    break;
                case 2:
                    for (final MiningTasks d : Mining.MiningTasks.values()) {
                        player.getPacketSender().sendTeleString(d.getassignName()[0], LINE_IDS[d.ordinal()][0]);
                        player.getPacketSender().sendTeleString(d.getassignName()[1], LINE_IDS[d.ordinal()][1]);
                    }
                    break;
                case 3:
                    for (final FishingTasks k : Fishing.FishingTasks.values()) {
                        player.getPacketSender().sendTeleString(k.getassignName()[0], LINE_IDS[k.ordinal()][0]);
                        player.getPacketSender().sendTeleString(k.getassignName()[1], LINE_IDS[k.ordinal()][1]);
                    }
                    break;
                case 4:
                    for (final ThievingTasks s : Thieving.ThievingTasks.values()) {
                        player.getPacketSender().sendTeleString(s.getassignName()[0], LINE_IDS[s.ordinal()][0]);
                        player.getPacketSender().sendTeleString(s.getassignName()[1], LINE_IDS[s.ordinal()][1]);
                    }
                    break;
                case 5:
                    for (final DungeoneeringTasks e : Dungeoneering.DungeoneeringTasks.values()) {
                        player.getPacketSender().sendTeleString(e.getassignName()[0], LINE_IDS[e.ordinal()][0]);
                        player.getPacketSender().sendTeleString(e.getassignName()[1], LINE_IDS[e.ordinal()][1]);
                    }
                    break;
                case 6:
                    for (final HunterTasks r : Hunter.HunterTasks.values()) {
                        player.getPacketSender().sendTeleString(r.getassignName()[0], LINE_IDS[r.ordinal()][0]);
                        player.getPacketSender().sendTeleString(r.getassignName()[1], LINE_IDS[r.ordinal()][1]);
                    }
                    break;
            }
        }
    }


}
