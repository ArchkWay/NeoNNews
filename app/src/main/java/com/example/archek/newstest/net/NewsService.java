package com.example.archek.newstest.net;



import com.example.archek.newstest.models.DetailNews;
import com.example.archek.newstest.models.NewsListResponses;
import com.example.archek.newstest.models.ObjectListResponses;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface NewsService {
/*interface with the get-requests*/
    @GET("news/categories")
        Call<ObjectListResponses> getCategories();

    @GET("news/categories/{id}/news")
    Call<NewsListResponses> getNews(@Path("id") int id, @Query("page") int page);

    @GET("news/details")
    Call<DetailNews> getNewDetails(@Query("id") int id);

}

