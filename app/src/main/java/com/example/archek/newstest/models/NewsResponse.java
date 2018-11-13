package com.example.archek.newstest.models;

import com.google.gson.annotations.SerializedName;

public class NewsResponse {
    public Integer id;
    public String title;
    public String date;
    @SerializedName("shortDescription")
    public String shortDescription;

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getShortDescription() {
        return shortDescription;
    }

}
