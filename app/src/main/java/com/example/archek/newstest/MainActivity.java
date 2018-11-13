package com.example.archek.newstest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.archek.newstest.models.ObjectListResponses;
import com.example.archek.newstest.net.NewsService;
import com.example.archek.newstest.net.RetrofitClient;
import com.example.archek.newstest.newsactivity.NewsActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity  {
    /*instal views*/
    private TextView tvMuchNews;
    private TextView tvOneNews;
    private TextView tvNoNews;
    private int amountNews;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*initiate the vews*/
        tvMuchNews = findViewById(R.id.tvMuchNews);
        tvOneNews = findViewById(R.id.tvOneNews);
        tvNoNews = findViewById(R.id.tvNoNews);

        /*Launch retrofit for downdload a data*/
        RetrofitClient.getInsance()
                .getService()
                .getCategories()
                .enqueue(new Callback <ObjectListResponses>() {
            @Override
            public void onResponse(Call <ObjectListResponses> call, Response <ObjectListResponses> response) {
                ObjectListResponses objectListResponse = response.body();
                tvMuchNews.setText(objectListResponse.getList().get(0).getName());
                tvOneNews.setText(objectListResponse.getList().get(1).getName());
                tvNoNews.setText(objectListResponse.getList().get(2).getName());
            }

            @Override
            public void onFailure(Call <ObjectListResponses> call, Throwable t) {
                if (call.isCanceled()) {
                    Toast.makeText(MainActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
                }
            }
        });
        /*setup switch for listener on right views*/
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = v.getId();
                switch (id) {
                    case R.id.tvMuchNews:
                        amountNews = 0;
                        onCategoryClick(amountNews);
                        break;
                    case R.id.tvOneNews:
                        amountNews = 1;
                        onCategoryClick(amountNews);
                        break;
                    case R.id.tvNoNews:
                        amountNews = 2;
                        onCategoryClick(amountNews);
                        break;
                    default:
                        break;
                }
            }
        };
        tvMuchNews.setOnClickListener(listener);
        tvOneNews.setOnClickListener(listener);
        tvNoNews.setOnClickListener(listener);
    }

    /*prepared way to NewsActivity*/
    public void onCategoryClick(int amountNews){
        String amountString = Integer.toString(amountNews);
        Intent intent = NewsActivity.makeIntent( this, amountString);
        startActivity(intent);
    }
}