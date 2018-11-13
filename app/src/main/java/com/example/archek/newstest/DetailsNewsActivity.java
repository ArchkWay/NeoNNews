package com.example.archek.newstest;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.text.HtmlCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.archek.newstest.models.DetailNews;
import com.example.archek.newstest.models.NewsListResponses;
import com.example.archek.newstest.models.NewsResponse;
import com.example.archek.newstest.net.RetrofitClient;
import com.example.archek.newstest.newsactivity.NewsActivity;
import com.example.archek.newstest.newsactivity.NewsDataSource;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsNewsActivity extends AppCompatActivity {
    private static final String EXTRA_ID = "EXTRA_ID";
    private static final String EXTRA_TITLE = "EXTRA_TITLE";
    private static final String EXTRA_SHORT_DESC = "EXTRA_SHORT_DESC";
    public static Intent makeIntent(NewsActivity context, NewsResponse story) {
        return new Intent(context, DetailsNewsActivity.class )
                .putExtra( DetailsNewsActivity.EXTRA_ID, story.getId().toString())
                .putExtra( DetailsNewsActivity.EXTRA_TITLE, story.getTitle())
                .putExtra( DetailsNewsActivity.EXTRA_SHORT_DESC, story.getShortDescription());
    }
    Toolbar toolbar;
    TextView tvDetTitle;
    TextView tvDetDate;
    TextView tvShortDescription;
    TextView tvDetFullDesc;
    String reversedDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        toolbar = findViewById(R.id.toolbarDet);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tvDetTitle = findViewById(R.id.tvDetTitle);
        tvDetDate = findViewById(R.id.tvDetDate);
        tvShortDescription = findViewById(R.id.tvDetShortDesc);
        tvDetFullDesc = findViewById(R.id.tvDetFullDesc);
        Intent intent = getIntent();
        String detailID = intent.getStringExtra(EXTRA_ID);
        String detailTitle = intent.getStringExtra(EXTRA_TITLE);
        String detailShortDes = intent.getStringExtra(EXTRA_SHORT_DESC);
        int id = Integer.parseInt(detailID);
        tvDetTitle.setText(detailTitle);
        tvShortDescription.setText(detailShortDes);
        RetrofitClient.getInsance()
                .getService()
                .getNewDetails(id)
                .enqueue(new Callback<DetailNews>() {
                    @Override
                    public void onResponse(Call<DetailNews> call, Response<DetailNews> response) {
                        if(response.body() != null){
                            DetailNews detailNews = response.body();
                            adaptedDate(detailNews.story.getDate().toString());
                            tvDetDate.setText(reversedDate);
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                                tvDetFullDesc.setText(Html.fromHtml(detailNews.story.getFullDescription(),Html.FROM_HTML_MODE_LEGACY));
                            } else {
                                tvDetFullDesc.setText(Html.fromHtml(detailNews.story.getFullDescription()));
                            }
                            tvDetFullDesc.setMovementMethod(LinkMovementMethod.getInstance());
                        }
                    }
                    @Override
                    public void onFailure(Call<DetailNews> call, Throwable t) {

                    }
                });
    }
    public void adaptedDate(String inputDate) {
        reversedDate = inputDate.substring(0,10) + ", " + inputDate.substring(11,19);

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
