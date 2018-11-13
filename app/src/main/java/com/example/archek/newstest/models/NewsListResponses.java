package com.example.archek.newstest.models;

import java.util.List;

public class NewsListResponses {
    public Integer code;
    public boolean has_more;

    public Integer getCode() {
        return code;
    }

    public List<NewsResponse> getList() {
        return list;
    }
    public List<NewsResponse> list;

}
