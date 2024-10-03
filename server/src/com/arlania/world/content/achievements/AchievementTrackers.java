package com.arlania.world.content.achievements;

import com.arlania.DiscordBot;
import com.arlania.GameSettings;
import com.arlania.model.GameMode;
import com.arlania.world.content.PlayerLogs;
import com.arlania.world.content.seasonal.SeasonalHandler;
import com.arlania.world.entity.impl.player.Player;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.javacord.api.entity.message.MessageBuilder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AchievementTrackers {

    private final Player player;
    private final Map<Integer, AchievementProgress> idToProgress = new HashMap<>();

    public AchievementTrackers(Player player) {
        this.player = player;
    }

    public void progress(AchievementData achievement, double amount) {
        AchievementProgress progress;
        if (idToProgress.containsKey(achievement.id)) {
            progress = idToProgress.get(achievement.id);
        } else {
            progress = new AchievementProgress(0, false);
            idToProgress.put(achievement.id, progress);
        }
        double oldProgress = progress.amountDone;
        double newProgress = progress.addAmount(amount, achievement.progressAmount);
        if (oldProgress != newProgress && newProgress >= achievement.progressAmount) {

            int points = 1;
            player.setAchievementPoints(player.getAchievementPoints() + points);

            player.sendMessage("<img=10> <col=339900>You have completed the achievement " + achievement + ".");
            player.sendMessage("<img=10> <col=339900>You now have " + player.getAchievementPoints() + " Achievement Points.");


            if (player.getGameMode() == GameMode.SEASONAL_IRONMAN) {

                if (achievement.type == AchievementType.SKILL)
                    points = 2;
                if (achievement.type == AchievementType.BOSS)
                    points = 2;
                if (achievement.type == AchievementType.PVM)
                    points = 5;
                if (achievement.type == AchievementType.MISC)
                    points = 5;

                SeasonalHandler.addPoints(player, points);

                String time = player.ticksToMinutes(player.getTotalPlayTime() + player.getRecordedLogin().elapsed());
                String message = player.getUsername() + " completed " + achievement + " and has " + player.seasonPoints + " Points! (" + time + ")";
                String discordMessage = GameSettings.SUCCESSKID + " " + message;

                String filename = "Seasonal_Log_" + player.seasonMonth + "_" + player.seasonYear;
                PlayerLogs.log(filename, message);

                if (GameSettings.DISCORD)//just add this anywhere and edit the message/or just copy the announcement
                    new MessageBuilder().append(discordMessage).send(DiscordBot.getInstance().getAPI().getTextChannelById(GameSettings.DISC_SEASONAL_POINTS_CH).get());
            }
        }
    }

    public JsonArray jsonSave() {
        JsonArray jsonArray = new JsonArray();
        for (Map.Entry<Integer, AchievementProgress> progress : idToProgress.entrySet()) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("id", progress.getKey());
            jsonObject.addProperty("amount", progress.getValue().amountDone);
            jsonObject.addProperty("collected", progress.getValue().rewardCollected);
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    public void load(JsonElement jsonElement) {
        JsonArray jsonArray = jsonElement.getAsJsonArray();
        for (JsonElement element : jsonArray) {
            if (!element.isJsonObject())
                continue;

            JsonObject object = (JsonObject) element;
            int id = object.get("id").getAsInt();
            int amount = object.get("amount").getAsInt();
            boolean collected = object.get("collected").getAsBoolean();
            idToProgress.put(id, new AchievementProgress(amount, collected));
        }
    }


    public void resetReward() {
        player.achievementCheck = true;

        for (Map.Entry<Integer, AchievementProgress> progress : idToProgress.entrySet()) {

            int[] masterRewards = {177, 165, 159, 153, 133, 124,};
            int[] legendaryRewards = {127, 121, 112,};
            int[] hardRewards = {118, 115, 109, 106, 103, 100, 97, 94, 91, 88};

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("id", progress.getKey());
            jsonObject.addProperty("amount", progress.getValue().amountDone);

            for (int i = 0; i < masterRewards.length; i++) {
                if (masterRewards[i] == progress.getKey() && progress.getValue().rewardCollected)
                    player.getInventory().add(4035, 1);
            }

            for (int i = 0; i < legendaryRewards.length; i++) {
                if (legendaryRewards[i] == progress.getKey() && progress.getValue().rewardCollected)
                    player.getInventory().add(4034, 1);
            }

            for (int i = 0; i < hardRewards.length; i++) {
                if (hardRewards[i] == progress.getKey() && progress.getValue().rewardCollected)
                    player.getInventory().add(4033, 1);
            }

        }
    }

    double getProgressFor(AchievementData selected) {
        if (idToProgress.containsKey(selected.id))
            return idToProgress.get(selected.id).amountDone;
        return 0;
    }

    public boolean hasCompletedAll() {
        return Arrays.stream(AchievementData.values()).map(achievementData -> idToProgress.get(achievementData.id))
                .allMatch(achievementProgress -> achievementProgress != null && achievementProgress.rewardCollected);
    }

    boolean hasCollected(AchievementData selected) {
        if (!idToProgress.containsKey(selected.id))
            return false;
        return idToProgress.get(selected.id).rewardCollected;
    }

    void setCollected(AchievementData selected) {
        if (!idToProgress.containsKey(selected.id)) {
            System.err.println("No Progress but collected achievement reward!");
            return;
        }
        idToProgress.get(selected.id).rewardCollected = true;
    }

    class AchievementProgress {
        private double amountDone;
        private boolean rewardCollected;

        AchievementProgress(double amountDone, boolean rewardCollected) {
            this.amountDone = amountDone;
            this.rewardCollected = rewardCollected;
        }

        double addAmount(double amount, double maxAmount) {
            amountDone = (int) Math.min(amountDone + amount, maxAmount);
            return amountDone;
        }
    }
}
