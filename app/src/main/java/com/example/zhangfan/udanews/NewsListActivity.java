package com.example.zhangfan.udanews;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.zhangfan.udanews.utilities.NetworkUtils;
import com.example.zhangfan.udanews.utilities.News;
import com.example.zhangfan.udanews.utilities.OpenNewsJsonUtils;

import java.net.URL;
import java.util.ArrayList;

public class NewsListActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<ArrayList<News>>,
        NewsAdapter.NewsAdapterOnClickHandler {

    private EditText mSearchEditText;
    private RecyclerView mNewsRecyclerView;
    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;

    private NewsAdapter mNewsAdapter;
    private static final int NEWS_LOADER_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        mSearchEditText = (EditText) findViewById(R.id.search_bar);
        mNewsRecyclerView = (RecyclerView) findViewById(R.id.news_list);
        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mNewsRecyclerView.setLayoutManager(linearLayoutManager);
        mNewsRecyclerView.setHasFixedSize(true);
        mNewsAdapter = new NewsAdapter(this);
        mNewsRecyclerView.setAdapter(mNewsAdapter);

        String searchText = mSearchEditText.getText().toString().trim();
        getSupportLoaderManager().initLoader(NEWS_LOADER_ID, null, this);
    }

    public void searchNews(View view) {
        mNewsAdapter.setNewsData(null);
        getSupportLoaderManager().restartLoader(NEWS_LOADER_ID, null, this);
    }

    @Override
    public Loader<ArrayList<News>> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<ArrayList<News>>(this) {

            ArrayList<News> mNewsListData = null;

            @Override
            protected void onStartLoading() {
                if (mNewsListData != null) {
                    deliverResult(mNewsListData);
                } else {
                    mLoadingIndicator.setVisibility(View.VISIBLE);
                    forceLoad();
                }
            }

            @Override
            public ArrayList<News> loadInBackground() {
                String queryParam = mSearchEditText.getText().toString().trim();
                URL newsURL = NetworkUtils.buildUrl(queryParam);

                try {
                    String jsonResult = NetworkUtils.getResponseFromHttpUrl(newsURL);
                    ArrayList<News> newsArrayList = OpenNewsJsonUtils.getNewsListFromJson(jsonResult);
                    return newsArrayList;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            public void deliverResult(ArrayList<News> data) {
                mNewsListData = data;
                super.deliverResult(data);
            }

        };
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<News>> loader, ArrayList<News> data) {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        mNewsAdapter.setNewsData(data);
        if (data != null && data.size() > 0) {
            showBookDataView();
        } else {
            showErrorMessage();
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<News>> loader) {

    }

    private void showBookDataView() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mNewsRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mNewsRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(String webUrl) {
        Uri uri = Uri.parse(webUrl);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
