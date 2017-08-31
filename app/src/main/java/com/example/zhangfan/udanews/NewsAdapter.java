package com.example.zhangfan.udanews;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zhangfan.udanews.utilities.News;

import java.util.ArrayList;

/**
 * Created by Harold on 2017/8/30.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    public NewsAdapter(NewsAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    private ArrayList<News> mNewsData;

    private final NewsAdapterOnClickHandler mClickHandler;

    public interface NewsAdapterOnClickHandler {
        void onClick(String webUrl);
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.news_item, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        News news = mNewsData.get(position);
        holder.mSectionTextView.setText(news.getSectionName());
        holder.mTitleTextView.setText(news.getWebTitle());
    }

    @Override
    public int getItemCount() {
        if (mNewsData != null) return mNewsData.size();
        return 0;
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final TextView mSectionTextView;
        final TextView mTitleTextView;

        public NewsViewHolder(View view) {
            super(view);
            mSectionTextView = (TextView) view.findViewById(R.id.news_section);
            mTitleTextView = (TextView) view.findViewById(R.id.news_title);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            News news = mNewsData.get(position);
            mClickHandler.onClick(news.getWebUrl());
        }
    }

    public void setNewsData(ArrayList<News> data) {
        mNewsData = data;
        notifyDataSetChanged();
    }
}
