package com.example.archek.newstest.newsactivity;

import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.archek.newstest.models.NewsListResponses;
import com.example.archek.newstest.models.NewsResponse;
import com.example.archek.newstest.net.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsDataSource extends PageKeyedDataSource<Integer, NewsResponse> {

    /*variabls for setings pages/ids*/
    private static final int FIRST_PAGE = 0;
    private static int ID;
    private static int PAGE;


    public static int getPAGE() {
        return PAGE;
    }
    public static void setPAGE(int PAGE) {
        NewsDataSource.PAGE = PAGE;
    }
    public static void setID(int ID) {
        NewsDataSource.ID = ID;
    }


    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params,
                            @NonNull final LoadInitialCallback<Integer, NewsResponse> callback) {
        RetrofitClient.getInsance()
                .getService()
                .getNews(ID, PAGE)
                .enqueue(new Callback<NewsListResponses>() {
                    @Override
                    public void onResponse(Call<NewsListResponses> call, Response<NewsListResponses> response) {
                        if(response.body() != null){
                            callback.onResult(response.body().list, null, FIRST_PAGE + 1);
                        }
                    }
                    @Override
                    public void onFailure(Call<NewsListResponses> call, Throwable t) {

                    }
                });

    }

    @Override
    public void loadBefore(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, NewsResponse> callback) {

        RetrofitClient.getInsance()
                .getService()
                .getNews(ID, PAGE + params.key)
                .enqueue(new Callback<NewsListResponses>() {
                    @Override
                    public void onResponse(Call<NewsListResponses> call,
                                           Response<NewsListResponses> response) {
                        if(response.body() != null){
                            Integer key = (params.key > 1) ? params.key - 1 : null;
                            callback.onResult(response.body().list, key);
                        }
                    }

                    @Override
                    public void onFailure(Call<NewsListResponses> call, Throwable t) {

                    }
                });

    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, NewsResponse> callback) {

        RetrofitClient.getInsance()
                .getService()
                .getNews(ID, PAGE + params.key)
                .enqueue(new Callback<NewsListResponses>() {
                    @Override
                    public void onResponse(Call<NewsListResponses> call, Response<NewsListResponses> response) {
                        if(response.body() != null){
                            Integer key = response.body().has_more ? params.key + 1 : null;
                            callback.onResult(response.body().list, key);
                        }
                    }

                    @Override
                    public void onFailure(Call<NewsListResponses> call, Throwable t) {

                    }
                });
    }
}
