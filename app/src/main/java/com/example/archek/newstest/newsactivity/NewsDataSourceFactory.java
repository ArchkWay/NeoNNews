package com.example.archek.newstest.newsactivity;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;
import android.arch.paging.PageKeyedDataSource;

import com.example.archek.newstest.models.NewsResponse;

public class NewsDataSourceFactory extends DataSource.Factory {

    private MutableLiveData<PageKeyedDataSource<Integer, NewsResponse>>
            newsLiveDataSource = new MutableLiveData<>();


    @Override
    public DataSource create() {
        NewsDataSource newsDataSource = new NewsDataSource();
        newsLiveDataSource.postValue(newsDataSource);
        return newsDataSource;
    }

    public MutableLiveData<PageKeyedDataSource<Integer, NewsResponse>> getNewsLiveDataSource() {
        return newsLiveDataSource;
    }
}
