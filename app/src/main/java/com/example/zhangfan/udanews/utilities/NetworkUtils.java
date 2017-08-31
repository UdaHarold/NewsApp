package com.example.zhangfan.udanews.utilities;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Scanner;

/**
 * Created by Harold on 2017/8/29.
 */

public class NetworkUtils {

    private static final String NEWS_API_URL = "http://content.guardianapis.com/search";
    private static final String QUERY_PARAM = "q";
    private static final String API_KEY_PARAM = "api-key";
    private static final String API_KEY_VALUE = "test";

    public static URL buildUrl(String queryString) {
        Uri builtUri = Uri.parse(NEWS_API_URL).buildUpon()
                .appendQueryParameter(API_KEY_PARAM, API_KEY_VALUE)
                .appendQueryParameter(QUERY_PARAM, queryString)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
