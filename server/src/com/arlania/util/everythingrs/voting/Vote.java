package com.arlania.util.everythingrs.voting;

import com.google.gson.Gson;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Vote {
    public static ExecutorService service = Executors.newFixedThreadPool(1);
    public String total_votes;
    public int vote_points;
    public int votes_month;
    public String username;
    public int give_amount;
    public int reward_id;
    public String reward_name;
    public int reward_amount;
    public String message;

    public Vote() {
    }

    public static Vote[] reward(String secret, String playerName, String id, String amount) throws Exception {
        String response = validate(secret, playerName, id, amount);
        Gson gson = new Gson();
        Vote[] vote = gson.fromJson(response, Vote[].class);
        return vote;
    }

    public static String validate(String secret, String playerName, String id, String amount) throws Exception {
        Map<String, Object> params = new LinkedHashMap();
        params.put("secret", secret);
        params.put("player", playerName);
        params.put("reward", id);
        params.put("amount", amount);
        return Post.sendPostData(params, "api/vote/validate");
    }

    public static String validate(String secret, String playerName, int id) throws Exception {
        return HTTP.connection("https://everythingrs.com/api/vote/process/" + playerName + "/" + secret + "/" + id);
    }

    public static String claimAuth(String secret, String username, String auth) throws Exception {
        Map<String, Object> params = new LinkedHashMap();
        params.put("username", username);
        params.put("auth", auth);
        params.put("secret", secret);
        String response = Post.sendPostData(params, "api/vote/claim-auth");
        System.out.println("response: " + response);
        Auth[] authResponse = (new Gson()).fromJson(response, Auth[].class);
        return authResponse[0].message;
    }
}
