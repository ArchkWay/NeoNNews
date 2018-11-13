package com.example.archek.newstest.models;

public class Story {

    public Integer id;
    public String title;
    public String date;
    public String shortDescription;
    public String fullDescription;

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

    public String getFullDescription() {
        return fullDescription;
    }
}