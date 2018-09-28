package com.gamesbykevin.coinbase.util;

import com.amazonaws.regions.Regions;

public class Properties {

    public static boolean DEBUG = false;

    private static String ENDPOINT_PRODUCT = "ProductEndpoint";
    private static String ENDPOINT_HISTORY = "HistoryEndpoint";
    private static String USER_AGENT = "UserAgent";
    private static String CANDLE_KEY = "Candles";
    private static String THREAD_SLEEP = "ThreadSleep";
    private static String S3_BUCKET_NAME = "S3BucketName";
    private static String CLIENT_REGION = "ClientRegion";

    private static long THREAD_DELAY = -1;
    private static String[] CANDLES;
    public static final String DELIMITER_CANDLE = ";";
    public static final String DELIMITER_DATA = ",";

    public static void load() {

        //if there is no system environment value we are debugging
        DEBUG = (System.getenv(ENDPOINT_PRODUCT) == null);

        System.out.println("DEBUG: " + DEBUG);

        if (DEBUG) {

            //ENDPOINT_PRODUCT = "https://api-public.sandbox.pro.coinbase.com/products";
            //ENDPOINT_HISTORY = "https://api-public.sandbox.pro.coinbase.com/products/%s/candles?granularity=%s";
            ENDPOINT_PRODUCT = "https://api.gdax.com/products";
            ENDPOINT_HISTORY = "https://api.gdax.com/products/%s/candles?granularity=%s";

            USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11";
            CANDLE_KEY = "60,one_minute;300,five_minutes;900,fifteen_minutes;3600,one_hour;21600,six_hours;86400,one_day";
            THREAD_SLEEP = "1000";
            S3_BUCKET_NAME = "coinbase-market-data";
            CLIENT_REGION = Regions.US_EAST_1.getName();

        } else {

            ENDPOINT_PRODUCT = System.getenv(ENDPOINT_PRODUCT);
            ENDPOINT_HISTORY = System.getenv(ENDPOINT_HISTORY);
            USER_AGENT = System.getenv(USER_AGENT);
            CANDLE_KEY = System.getenv(CANDLE_KEY);
            THREAD_SLEEP = System.getenv(THREAD_SLEEP);
            S3_BUCKET_NAME = System.getenv(S3_BUCKET_NAME);
            CLIENT_REGION = System.getenv(CLIENT_REGION);
        }

        //create our candles
        CANDLES = CANDLE_KEY.split(DELIMITER_CANDLE);
    }

    public static String getEndpointProduct() {
        return ENDPOINT_PRODUCT;
    }

    public static String getClientRegion() {
        return CLIENT_REGION;
    }

    public static String getBucketName() {
        return S3_BUCKET_NAME;
    }

    public static String getEndpointHistory() {
        return ENDPOINT_HISTORY;
    }

    public static String getUserAgent() {
        return USER_AGENT;
    }

    public static String[] getCandles() {
        return CANDLES;
    }

    public static long getThreadDelay() {

        if (THREAD_DELAY < 0)
            THREAD_DELAY = Long.parseLong(THREAD_SLEEP);

        return THREAD_DELAY;
    }
}