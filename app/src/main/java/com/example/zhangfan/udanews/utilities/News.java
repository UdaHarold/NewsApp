package com.example.zhangfan.udanews.utilities;

/**
 * Created by Harold on 2017/8/29.
 */

public class News {

    private String webTitle;
    private String webUrl;
    private String sectionName;

    public News(String title, String url, String section) {
        webTitle = title;
        webUrl = url;
        sectionName = section;
    }

    public String getWebTitle() {
        return webTitle;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public String getSectionName() {
        return sectionName;
    }

}
