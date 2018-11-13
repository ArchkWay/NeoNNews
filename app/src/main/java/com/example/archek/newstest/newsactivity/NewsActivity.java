package com.example.archek.newstest.newsactivity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.archek.newstest.DetailsNewsActivity;
import com.example.archek.newstest.MainActivity;
import com.example.archek.newstest.R;
import com.example.archek.newstest.models.NewsResponse;

import retrofit2.Callback;

public class NewsActivity extends MainActivity implements NewsAdapter.Callback  {

    /*get ID for understand which amount news each will have downloaded*/

    public static final String EXTRA_AMOUNT = "TYPE_AMOUNT";
    public static Intent makeIntent(Context context, String amountNewsSt) {
        return new Intent(context, NewsActivity.class)
                .putExtra( NewsActivity.EXTRA_AMOUNT, amountNewsSt);
    }

    Toolbar toolbar;
    Button btnNext;
    Button btnPrev;
    TextView tvPageNum;

    /*take page number from service class*/
    String temp = String.valueOf(NewsDataSource.getPAGE()+1);

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newslist);
        btnNext = findViewById(R.id.btnNext);
        btnPrev = findViewById(R.id.btnPrev);
        tvPageNum = findViewById(R.id.tvPagenum);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /*set the number of page*/
        tvPageNum.setText(temp);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.setStatusBarColor(getResources().getColor(R.color.colorAccent));
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*get ID of amount news*/
        Intent intent = getIntent();
        String amountNewsSt = intent.getStringExtra(EXTRA_AMOUNT);
        int id = Integer.parseInt(amountNewsSt);
        /*send it to the service class connected with api*/
        NewsDataSource.setID(id);

        RecyclerView rvNews = findViewById(R.id.rvNews);
        final RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        rvNews.setLayoutManager(lm);
        rvNews.setHasFixedSize(true);

        /*get supports ViewModel, Adapter all for paged list*/
        NewsViewModel newsViewModel = ViewModelProviders.of(this).get(NewsViewModel.class);
        final NewsAdapter adapter = new NewsAdapter(this, this);

        newsViewModel.newsPagedList.observe(this, new Observer<PagedList<NewsResponse>>() {
            @Override
            public void onChanged(@Nullable PagedList<NewsResponse> news) {
                adapter.submitList(news);
            }
        });
        rvNews.setAdapter(adapter);

        /*set buttons for changing pages*/
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
                NewsDataSource.setPAGE(NewsDataSource.getPAGE()+1);
                tvPageNum.setText(temp);
            }
        });
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
                if(NewsDataSource.getPAGE() != 0){
                    NewsDataSource.setPAGE(NewsDataSource.getPAGE()-1);
                }
                tvPageNum.setText(temp);
            }
        });
    }

    /*back arrow batton set page - 0*/
    @Override
    public boolean onSupportNavigateUp() {
        NewsDataSource.setPAGE(0);
        onBackPressed();
        return true;
    }

    /*prepared to display next - details news activity*/
    @Override
    public void onStoryClick(NewsResponse story) {
        Intent intent = DetailsNewsActivity.makeIntent(this, story);
        startActivity( intent );
    }
}