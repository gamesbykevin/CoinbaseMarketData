package com.gamesbykevin.coinbase.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Util {

    public static String getJson(String endpoint, String useragent) throws Exception {

        String result = "";

        URL url = new URL(endpoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("User-Agent", useragent);

        if (connection.getResponseCode() != 200)
            throw new RuntimeException("Failed : HTTP error code : " + connection.getResponseCode());

        BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));

        String output;

        while ((output = br.readLine()) != null) {
            result += output;
        }

        connection.disconnect();
        connection = null;
        url = null;

        return result;
    }
}