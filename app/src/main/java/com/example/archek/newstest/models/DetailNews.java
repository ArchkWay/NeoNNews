package com.example.archek.newstest.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DetailNews {

    @SerializedName("code")
    @Expose
    public Integer code;
    @SerializedName("news")
    @Expose
    public Story story;
}

