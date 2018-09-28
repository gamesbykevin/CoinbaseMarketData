package com.gamesbykevin.coinbase;

import com.gamesbykevin.coinbase.product.Product;
import com.gamesbykevin.coinbase.util.GsonParser;

import static com.gamesbykevin.coinbase.util.Properties.*;
import static com.gamesbykevin.coinbase.util.S3Bucket.save;
import static com.gamesbykevin.coinbase.util.Util.getJson;

public class Main {

    //good / bad result
    public static final int CODE_SUCCESS = 200;

    /**
     * Entry point for lambda function
     * @return status code success (200) / failure (500)
     */
    public static Integer myHandler() throws Exception {

        //load our properties
        load();

        Product[] products = getProducts();

        for (Product product : products) {

            //we are only interested in USD products
            if (product.id != null && product.id.toUpperCase().contains("-USD")) {

                //check every candle available
                for (int index = 0; index < getCandles().length; index++) {

                    try {

                        //get the duration of the candle
                        String duration = getCandles()[index].split(DELIMITER_DATA)[0];
                        String durationDesc = getCandles()[index].split(DELIMITER_DATA)[1];

                        //format our endpoint
                        String endpoint = String.format(getEndpointHistory(), product.id, duration);

                        //make call to get json data
                        String json = getJson(endpoint, getUserAgent());

                        //display data
                        System.out.println(endpoint);
                        System.out.println(json);

                        //store json object in file and place in s3 bucket
                        save(json, product.id, durationDesc);

                    } catch (Exception e) {
                        e.printStackTrace();
                        throw e;
                    }

                    //sleep so we aren't blocked
                    Thread.sleep(getThreadDelay());
                }

                System.out.println(product.id);
            }
        }

        return CODE_SUCCESS;
    }

    public static void main(String[] args) {

        try {
            System.out.println("Code: " + myHandler());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Product[] getProducts() throws Exception {
        return GsonParser.get().fromJson(getJson(getEndpointProduct(), getUserAgent()), Product[].class);
    }
}