package com.arlania.util.everythingrs.voting;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class Post {
    private static final boolean local = false;

    public Post() {
    }

    public static String sendPostData(Map<String, Object> params, String location) throws Exception {
        String target = "https://everythingrs.com/" + location;
        URL url = new URL(target);
        StringBuilder postData = new StringBuilder();
        Iterator var6 = params.entrySet().iterator();

        while (var6.hasNext()) {
            Entry<String, Object> param = (Entry) var6.next();
            if (postData.length() != 0) {
                postData.append('&');
            }

            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }

        byte[] postDataBytes = postData.toString().getBytes(StandardCharsets.UTF_8);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        setRequestProperties(conn, postDataBytes);
        conn.setDoOutput(true);
        conn.getOutputStream().write(postDataBytes);
        Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
        StringBuilder builder = new StringBuilder();

        int c;
        while ((c = in.read()) >= 0) {
            builder.append((char) c);
        }

        in.close();
        return builder.toString();
    }

    public static void setRequestProperties(HttpURLConnection conn, byte[] postDataBytes) throws Exception {
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Host", "everythingrs.com");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.2; rv:20.0) Gecko/20121202 Firefox/20.0");
        conn.setRequestProperty("Accept", "*/*");
        conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
    }
}
