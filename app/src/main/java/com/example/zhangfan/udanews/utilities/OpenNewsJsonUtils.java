package com.example.zhangfan.udanews.utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Harold on 2017/8/30.
 */

public class OpenNewsJsonUtils {

    public static ArrayList<News> getNewsListFromJson(String newsJsonStr)
            throws JSONException {

        final String RESPONSE = "response";
        final String RESULTS = "results";
        final String NEWS_TITLE = "webTitle";
        final String SECTION_NAME = "sectionName";
        final String NEWS_URL = "webUrl";

        //news list receive max 10 items
        ArrayList<News> newsArrayListList = new ArrayList<>(10);

        JSONObject rootJson = new JSONObject(newsJsonStr);

        JSONObject response = rootJson.getJSONObject(RESPONSE);

        // get all news
        JSONArray newsArrayJson = response.getJSONArray(RESULTS);

        if (newsArrayJson != null && newsArrayJson.length() > 0) {
            News news = null;
            String title = "";
            String section = "";
            String url = "";
            for (int i = 0; i < newsArrayJson.length(); i++) {
                JSONObject newsJson = newsArrayJson.getJSONObject(i);
                title = newsJson.getString(NEWS_TITLE);
                section = newsJson.getString(SECTION_NAME);
                url = newsJson.getString(NEWS_URL);

                news = new News(title, url, section);
                newsArrayListList.add(news);
            }
        }

        return newsArrayListList;
    }
}
