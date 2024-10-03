package com.arlania.world.content.achievements;

import com.arlania.model.Item;
import com.arlania.net.packet.PacketSender;
import com.arlania.world.entity.impl.player.Player;

import java.util.Arrays;

public class AchievementInterface {
    private static final int INTERFACE_ID = 23150;
    private static final int COLLECT_BUTTON = 23159;
    private static final int CLOSE_BUTTON = 23156;
    private static final int EASY_BUTTON = 23164;
    private static final int MED_BUTTON = 23165;
    private static final int HARD_BUTTON = 23166;
    private static final int DAILY_BUTTON = 23167;
    private static final int TAB_CONFIG_ID = 1086;
    private static final int SLOTS = 80;
    private static final int START_LIST_ID = 23181;
    private static final int NAME_ID = 23162;
    private static final int SUB_NAME_ID = 23163;
    private static final int DESCRIPTION_ID = 23172;
    private static final int POINTS_ID = 23173;
    private static final int ITEMS_CONTAINER_ID = 23174;
    private static final int PROGRESS_BAR_WIDTH = 274;
    private static final int PROGRESS_BAR_ID = 23179;
    private static final int PROGRESS_TEXT_ID = 23176;


    private final Player player;
    private AchievementType achievementType = AchievementType.SKILL;
    private AchievementData selected = Arrays.stream(AchievementData.values).filter(a -> a.type.equals(achievementType)).findFirst().get();

    private AchievementInterface(Player player) {
        this.player = player;
    }

    public static void open(Player player) {
        AchievementInterface achievementInterface = new AchievementInterface(player);
        player.setAchievementInterface(achievementInterface);
        achievementInterface.open();
    }

    private void open() {
        player.getPacketSender().sendInterface(INTERFACE_ID);
        refresh();
    }

    private void refresh() {
        PacketSender out = player.getPacketSender();
        out.sendConfig(TAB_CONFIG_ID, achievementType.ordinal());
        AchievementData[] achievements = AchievementData.getAchievementsOfType(achievementType);

        for (int i = 0; i < SLOTS; i++) {
            if (i < achievements.length) {
                AchievementData achievement = achievements[i];
                // out.sendString(START_LIST_ID + 1 + i * 2, completed ? "@gre@" + achievement.toString() : achievement.toString());

                boolean done = player.getAchievementTracker().getProgressFor(achievement) == achievements[i].progressAmount;

                boolean collected = player.getAchievementTracker().hasCollected(achievement);

                if (collected)
                    out.sendString(START_LIST_ID + 1 + i * 2, "@gre@" + achievement);

                else if (done)
                    out.sendString(START_LIST_ID + 1 + i * 2, "@yel@" + achievement);

                else
                    out.sendString(START_LIST_ID + 1 + i * 2, "@red@" + achievement);
            } else {
                out.sendString(START_LIST_ID + 1 + i * 2, "");
            }
        }

        out.sendString(NAME_ID, selected.toString());
        out.sendString(SUB_NAME_ID, selected.type.toString());
        out.sendString(DESCRIPTION_ID, selected.description);
        double done = player.getAchievementTracker().getProgressFor(selected);
        double total = selected.progressAmount;
        int percent = (int) (100 * done / total);
        int width = (int) (PROGRESS_BAR_WIDTH * done / total);
        out.sendString(PROGRESS_TEXT_ID, percent + "%" + " | " + done + "/" + total);
        out.sendInterfaceComponentMoval(width - PROGRESS_BAR_WIDTH, 0, PROGRESS_BAR_ID);
        out.sendItemsOnInterface(ITEMS_CONTAINER_ID, selected.itemRewards);
        out.sendString(POINTS_ID, getPointsString(selected.nonItemRewards));
    }

    private String getPointsString(NonItemReward[] nonItemRewards) {
        StringBuilder sb = new StringBuilder();
        for (NonItemReward reward : nonItemRewards) {
            sb.append(reward.rewardDescription()).append("\\n");
        }
        return sb.toString();
    }

    public boolean handleButton(int id) {
        //System.out.println("id=" + id);
        if (id == CLOSE_BUTTON) {
            player.getPacketSender().removeAllWindows();
            player.setAchievementInterface(null);
            return true;
        }
        if (id == COLLECT_BUTTON) {
            tryCollectReward();
            return true;
        }
        if (id == EASY_BUTTON) {
            achievementType = AchievementType.SKILL;
            refresh();
            return true;
        }
        if (id == MED_BUTTON) {
            achievementType = AchievementType.BOSS;
            refresh();
            return true;
        }
        if (id == HARD_BUTTON) {
            achievementType = AchievementType.PVM;
            refresh();
            return true;
        }
        if (id == DAILY_BUTTON) {
            achievementType = AchievementType.MISC;
            refresh();
            return true;
        }
        if (id >= START_LIST_ID && id <= START_LIST_ID + SLOTS * 2) {
            trySelectAchievement((id - START_LIST_ID) / 2);
            return true;
        }
        return false;
    }

    private void trySelectAchievement(int index) {
        AchievementData[] achievements = AchievementData.getAchievementsOfType(achievementType);
        if (index >= achievements.length)
            return;
        selected = achievements[index];
        refresh();
    }

    private void tryCollectReward() {
        double done = player.getAchievementTracker().getProgressFor(selected);
        if (done < selected.progressAmount) {
            player.sendMessage("You have not completed this achievement yet.");
            return;
        }
        if (player.getAchievementTracker().hasCollected(selected)) {
            player.sendMessage("You have already collected the reward for this achievement.");
            return;
        }
        player.getAchievementTracker().setCollected(selected);
        for (Item item : selected.itemRewards) {
            player.getInventory().add(item);
        }
        for (NonItemReward reward : selected.nonItemRewards) {
            reward.giveReward(player);
        }
        player.sendMessage("You now have " + player.getPaePoints() + " PaePoints!");
    }
}
