package com.gamesbykevin.coinbase.util;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static com.gamesbykevin.coinbase.util.Properties.DEBUG;
import static com.gamesbykevin.coinbase.util.Properties.getBucketName;
import static com.gamesbykevin.coinbase.util.Properties.getClientRegion;

public class S3Bucket {

    public static final String SUFFIX = "/";

    private static AmazonS3 CLIENT;

    private static AmazonS3 getClient() {

        if (CLIENT == null) {

            if (DEBUG) {

                CLIENT = AmazonS3ClientBuilder.standard()
                        .withRegion(getClientRegion())
                        .withCredentials(new ProfileCredentialsProvider())
                        .build();
            } else {

                CLIENT = AmazonS3ClientBuilder.defaultClient();
            }

        }

        return CLIENT;
    }

    public static void save(String json, String productId, String durationDesc) throws Exception {

        String stringObjKeyName = productId + SUFFIX + durationDesc;
        String fileName = System.nanoTime() + ".json";

        //create the folder
        createFolder(stringObjKeyName);

        //upload a text string as a new object.
        getClient().putObject(getBucketName(), stringObjKeyName + SUFFIX + fileName, json);
    }

    private static void createFolder(String folderName) {

        //create meta-data for your folder and set content-length to 0
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(0);

        //create empty content
        InputStream emptyContent = new ByteArrayInputStream(new byte[0]);

        //create a PutObjectRequest passing the folder name suffixed by /
        PutObjectRequest putObjectRequest = new PutObjectRequest(getBucketName(), folderName + SUFFIX, emptyContent, metadata);

        // send request to S3 to create folder
        getClient().putObject(putObjectRequest);
    }
}