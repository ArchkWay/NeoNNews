package com.example.archek.newstest.newsactivity;

import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.archek.newstest.R;
import com.example.archek.newstest.models.NewsResponse;

public class NewsAdapter extends PagedListAdapter<NewsResponse, NewsAdapter.NewsViewHolder> {

    private Context ctx;
    private String reversedDate;
    private final Callback callback;

    protected NewsAdapter(Context ctx, Callback callback) {
        super(DIFF_CALLBACK);
        this.ctx = ctx;
        this.callback = callback;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.item, parent, false);
        final NewsViewHolder holder = new NewsViewHolder(view);
        view.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewsResponse story = getItem(holder.getAdapterPosition());
                callback.onStoryClick( story );
            }
        } );
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {

        NewsResponse story = getItem(position);
        if (story != null) {
            holder.tvTitleNews.setText(story.getTitle());
            holder.tvShortDescription.setText(story.getShortDescription());
            /*change date format*/
            adaptedDate(story.getDate());
            holder.tvDate.setText(reversedDate);

        } else {
            Toast.makeText(ctx, "News is null", Toast.LENGTH_LONG).show();
        }
    }

    /*comparing items before downloading*/
    private static DiffUtil.ItemCallback<NewsResponse> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<NewsResponse>() {
                @Override
                public boolean areItemsTheSame(NewsResponse oldNews, NewsResponse newNews) {
                    return oldNews.getId() == newNews.getId();
                }

                @Override
                public boolean areContentsTheSame(NewsResponse oldNews, NewsResponse newNews) {
                    return oldNews.equals(newNews)                            ;
                }
            };


    class NewsViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitleNews;
        private TextView tvShortDescription;
        private TextView tvDate;

        public NewsViewHolder(View view) {
            super(view);

            tvTitleNews = view.findViewById(R.id.tvTitleNews);
            tvShortDescription = view.findViewById(R.id.tvShortDescription);
            tvDate = view.findViewById(R.id.tvDate);
        }
    }

    public interface Callback{
        void onStoryClick(NewsResponse story);
    }

    public void adaptedDate(String inputDate) {
        reversedDate = inputDate.substring(0,10) + ", " + inputDate.substring(11,19);
    }
}
