package com.example.archek.newstest.newsactivity;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PageKeyedDataSource;
import android.arch.paging.PagedList;

import com.example.archek.newstest.models.NewsResponse;

public class NewsViewModel extends ViewModel {

    LiveData<PagedList<NewsResponse>> newsPagedList;
    LiveData<PageKeyedDataSource<Integer, NewsResponse>> liveDataSource;

    public NewsViewModel() {

        NewsDataSourceFactory newsDataSourceFactory = new NewsDataSourceFactory();
        liveDataSource = newsDataSourceFactory.getNewsLiveDataSource();

        /*setings paged list*/
        PagedList.Config config =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(10)
                        .build();

        newsPagedList = (new LivePagedListBuilder(newsDataSourceFactory, config)).build();

    }
}
