package com.arlania.util.everythingrs.voting;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTP {
    public HTTP() {
    }

    public static String connection(String connectionURL) throws Exception {
        connectionURL = connectionURL.replace(" ", "%20");
        URL url = new URL(connectionURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Host", "everythingrs.com");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.2; rv:20.0) Gecko/20121202 Firefox/20.0");
        connection.setRequestProperty("Accept", "*/*");
        connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        connection.setRequestProperty("X-Requested-With", "XMLHttpRequest");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String inputLine;
        String html;
        for (html = ""; (inputLine = in.readLine()) != null; html = html + inputLine) {
        }

        in.close();
        return html;
    }
}
