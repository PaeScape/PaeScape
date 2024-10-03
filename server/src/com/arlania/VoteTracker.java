package com.arlania;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.javacord.api.entity.channel.TextChannel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.logging.Level;

public class VoteTracker extends TimerTask {

    private static VoteTracker singleton;
    private final long channel = -1L;

    public static void initialize() {
        if (singleton == null) {
            singleton = new VoteTracker();
        }
    }

    public static VoteTracker getInstance() {
        return singleton;
    }

    public void run() {
        Optional<TextChannel> textChannel = DiscordBot.api.getTextChannelById(channel);
        if (textChannel.isPresent() && textChannel.get().canYouWrite()) {
            String votes = getVotes();
            if (votes != null) {
                textChannel.get().sendMessage(votes);
            }
        }
    }

    private VoteTracker() {
//        Timer timer = new Timer();
//        timer.schedule(this, 60 * 60 * 1000, 60 * 60 * 1000);
    }

    public static String getVotes() {
        String urlStr = "";
        JsonArray responseBody;

        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Host", "everythingrs.com");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.2; rv:20.0) Gecko/20121202 Firefox/20.0");
            conn.setRequestProperty("Accept", "*/*");
            conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Content-Type", "application/json");

            conn.setDoOutput(true);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = "{\"days\":\"30\",\"gameServerId\":1508}".getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            if (conn.getResponseCode() != 200) {
                GameServer.getLogger().log(Level.SEVERE, "EverythingRS vote page returned status code:", conn.getResponseCode());
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    GameServer.getLogger().log(Level.SEVERE, line);
                }
                return null;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            responseBody = (JsonArray) JsonParser.parseReader(reader);
            reader.close();
        } catch (IOException e) {
            GameServer.getLogger().log(Level.SEVERE, "VoteTracker Error.", e);
            return null;
        }

        if (responseBody == null) {
            GameServer.getLogger().log(Level.SEVERE, "VoteTracker response body null.");
            return null;
        }

        StringBuilder resultString = new StringBuilder();
        for (JsonElement jsonElement : responseBody) {
            JsonObject topVoter = jsonElement.getAsJsonObject();
            resultString.append(topVoter.get("username").getAsString()).append(": ").append(topVoter.get("votes").getAsString()).append("\n");
        }
        return resultString.toString();
    }
}
