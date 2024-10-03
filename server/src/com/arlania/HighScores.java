package com.arlania;

import com.arlania.model.GameMode;
import com.arlania.model.StaffRights;
import com.arlania.world.entity.impl.player.Player;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

// todo: in PlayerDeathTask.execute: HighScores.toggleDeath(player) in if(player.getGameMode() == GameMode.HARDCORE_IRONMAN)
// todo: ::deiron
// todo: in PlayerHandler.handleLogout: HighScores.sendToHighScores(player); before player.save()
// todo: in ShutdownHook.run: HighScores.finish()
// todo: HighScores.toggleHidden() on command? on ban? idk
// todo: HighScores.delete()
public class HighScores {
    private static final ExecutorService highScoresExecutor = Executors.newSingleThreadExecutor(new ThreadFactoryBuilder().setNameFormat("HighScoresThread").build());

    private static final String API_HOST = "";

    public static void finish() throws InterruptedException {
        highScoresExecutor.shutdown();
        if (!highScoresExecutor.awaitTermination(30, TimeUnit.SECONDS))
            throw new IllegalStateException("HighScores Service was unable to terminate properly.");
    }

    public static void sendToHighScores(Player player) {
        if (true) {
            return;
        }

        Gson builder = new GsonBuilder().create();
        JsonObject object = new JsonObject();
        object.addProperty("username", player.getUsername().trim());
        object.addProperty("staff-rights", player.getStaffRights().name());
        object.addProperty("game-mode", player.getGameMode().name());
        object.addProperty("prestige", player.prestige);
        object.add("killcounts", builder.toJsonTree(player.getCollectionLog().getKillsTracker()));
        object.add("skills", builder.toJsonTree(player.getSkillManager().getSkills()));

        JsonObject statsObject = new JsonObject();
        statsObject.addProperty("total_xp", player.totalXP);
        statsObject.addProperty("total_boss_kills", player.getPointsHandler().gettotalbosskills());
        statsObject.addProperty("crystal_chests", player.CrystalChests);
        statsObject.addProperty("rare_candies", player.getRareCandy());
        statsObject.addProperty("slayer_tasks", player.totalSlayerTasks);
        statsObject.addProperty("skiller_tasks", player.totalSkillerTasks);
        statsObject.addProperty("wilderness_kills", player.getPlayerKillingAttributes().getPlayerKills());
        statsObject.addProperty("wilderness_deaths", player.getPlayerKillingAttributes().getPlayerDeaths());
        statsObject.addProperty("bonus_prestiges", player.extraPrestiges);
        statsObject.addProperty("completed_clogs", player.completedLogs);
        statsObject.addProperty("achievement_points", player.getAchievementPoints());
        object.add("statistics", statsObject);

        apiPost(object, "/player");
    }

    public static void toggleDeath(Player player) {
        JsonObject object = new JsonObject();

        object.addProperty("username", player.getUsername().trim());
        object.addProperty("game-mode", player.getGameMode().name());

        apiPost(object, "/death");
    }

    public static void toggleHidden(Player player) {
        JsonObject object = new JsonObject();

        object.addProperty("username", player.getUsername().trim());
        object.addProperty("game-mode", player.getGameMode().name());

        apiPost(object, "/hide");
    }

    public static void delete(Player player) {
        if (true)
            return;
        highScoresExecutor.execute(() -> {
            String gameMode = player.getGameMode().name(); // upper case underscore
            String username = player.getUsername().trim(); // lower case underscore
            try {
                Gson builder = new GsonBuilder().create();
                CloseableHttpClient httpClient = HttpClients.createDefault();
                HttpDelete httpDelete = new HttpDelete(String.format("%s/player/%s/%s", API_HOST, gameMode, username));
                CloseableHttpResponse response = httpClient.execute(httpDelete);

                int statusCode = response.getStatusLine().getStatusCode();
                String responseContentType = response.getEntity().getContentType().getValue();
                if (responseContentType.contains("application/json")) {
                    JsonReader reader = new JsonReader(new BufferedReader(new InputStreamReader(response.getEntity().getContent())));
                    reader.setLenient(true);
                    JsonObject fromAPI = builder.fromJson(reader, JsonObject.class);

                    if (statusCode != 200 && fromAPI.has("error") && fromAPI.get("error").getAsBoolean()) {
                        GameServer.getLogger().warning(String.format("[HighScores] error deleting %s %s (%s)", gameMode, username, fromAPI.get("message").getAsString()));
                    } else {
                        GameServer.getLogger().info(String.format("[HighScores] successful delete %s %s (%s)", gameMode, username, fromAPI));
                    }
                } else {
                    GameServer.getLogger().warning(String.format("[HighScores] error deleting %s %s (response from api was not json)\n%s", gameMode, username, EntityUtils.toString(response.getEntity())));
                }
                response.close();
                httpClient.close();
            } catch (IOException e) {
                GameServer.getLogger().log(Level.SEVERE, "Something happened while deleting " + player.getUsername() + "'s HighScores", e);
            }
        });
    }

    private static void apiPost(JsonObject object, String endPoint) {
        if (true)
            return;
        highScoresExecutor.execute(() -> {
            try {
                String gameMode = object.get("username").getAsString();
                String username = object.get("game-mode").getAsString();

                Gson builder = new GsonBuilder().create();
                CloseableHttpClient httpClient = HttpClients.createDefault();
                HttpPost httpPost = new HttpPost(API_HOST + endPoint);
                HttpEntity stringEntity = new StringEntity(builder.toJson(object), ContentType.APPLICATION_JSON);
                httpPost.setEntity(stringEntity);
                CloseableHttpResponse response = httpClient.execute(httpPost);

                int statusCode = response.getStatusLine().getStatusCode();
                String responseContentType = response.getEntity().getContentType().getValue();

                if (responseContentType.contains("application/json")) {
                    JsonReader reader = new JsonReader(new BufferedReader(new InputStreamReader(response.getEntity().getContent())));
                    reader.setLenient(true);
                    JsonObject fromAPI = builder.fromJson(reader, JsonObject.class);

                    if (statusCode != 200 && fromAPI.has("error") && fromAPI.get("error").getAsBoolean()) {
                        GameServer.getLogger().warning(String.format("[HighScores] error updating %s %s (%s)", gameMode, username, fromAPI.get("message").getAsString()));
                    } else {
                        GameServer.getLogger().info(String.format("[HighScores] successful %s %s %s (%s)", endPoint, gameMode, username, fromAPI));
                    }
                } else {
                    GameServer.getLogger().warning(String.format("[HighScores] error %s %s %s (response from api was not json)\n%s", endPoint, gameMode, username, EntityUtils.toString(response.getEntity())));
                }
                response.close();
                httpClient.close();
            } catch (IOException e) {
                GameServer.getLogger().log(Level.SEVERE, "Something happened while posting to the HighScores", e);
            }
        });
    }
}

