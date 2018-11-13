package com.example.archek.newstest.net;

import com.example.archek.newstest.BuildConfig;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

final public class RetrofitClient {

    private static final String BASE_URL = "http://testtask.sebbia.com/v1/";
    private static RetrofitClient mInstance;
    private Retrofit retrofit;

    private RetrofitClient(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized RetrofitClient getInsance(){
        if(mInstance == null){
            mInstance = new RetrofitClient();
        }
        return mInstance;
    }


    public NewsService getService(){
        return retrofit.create(NewsService.class);
    }

}
