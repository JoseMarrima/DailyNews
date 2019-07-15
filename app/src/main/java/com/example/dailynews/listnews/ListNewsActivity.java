package com.example.dailynews.listnews;

import android.os.Bundle;
import android.util.Log;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dailynews.R;
import com.example.dailynews.di.ViewModelProviderFactory;
import com.example.dailynews.model.NewsArticle;
import com.example.dailynews.model.network.ListNewsResponse;
import com.example.dailynews.model.network.ListNewsService;
import com.example.dailynews.util.Constants;
import com.example.dailynews.util.Resource;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ListNewsActivity extends DaggerAppCompatActivity {

    private static final String TAG = "ListNewsActivity";

    @Inject
    public ViewModelProviderFactory mFactory;

    private ListNewsViewModel mViewModel;
    private RecyclerView mRecyclerView;

    @Inject
    public ListNewsAdapter mAdapter;

    @Inject
    public ListNewsService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_news_articles);

        mViewModel = ViewModelProviders.of(this, mFactory).get(ListNewsViewModel.class);

        Log.i(TAG, "onCreate: " + mViewModel.toString());
        initRecyclerView();
        Log.i(TAG, "onCreate: SERVICE" + service);
        Log.i(TAG, "onCreate: ADAPTER " + mAdapter);
        //test();
        observeListOfNews();
    }

    private void test() {
        Call<ListNewsResponse> call = service.test(Constants.SOURCE, Constants.API_KEY);
        Log.i(TAG, "test: " + call);
        call.enqueue(new Callback<ListNewsResponse>() {
            @Override
            public void onResponse(Call<ListNewsResponse> call, Response<ListNewsResponse> response) {
                Log.i(TAG, "onResponse: " + response.message());
                if(response.isSuccessful()) {
                    Log.i(TAG, "onResponse: IT WAS SUCCESSFUL" );
                    Log.i(TAG, "onResponse: " + response.body().getStatus());
                    Log.i(TAG, "onResponse: " + response.body().getTotalResults());
                    Log.i(TAG, "onResponse: " + response.body().getArticles().get(0).getTitle());
                    mAdapter.setNewsArticles(response.body().getArticles());
                }
            }

            @Override
            public void onFailure(Call<ListNewsResponse> call, Throwable t) {
                Log.i(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private void initRecyclerView() {
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }

    private void observeListOfNews() {
        Log.i(TAG, "observeListOfNews: " + mViewModel.getNewsArticles());
        if(mViewModel.getNewsArticles() != null) {
            mViewModel.getNewsArticles().observe(this, new Observer<Resource<List<NewsArticle>>>() {
                @Override
                public void onChanged(Resource<List<NewsArticle>> listResource) {
                    switch (listResource.status) {
                        case LOADING:{
                            break;
                        }
                        case SUCCESS:{
                            Log.i(TAG, "onChanged: " + listResource.data.get(0).getTitle());
                            mAdapter.setNewsArticles(listResource.data);
                            break;
                        }
                        case ERROR:{
                            break;
                        }
                    }
                }
            });
        }

    }
}
