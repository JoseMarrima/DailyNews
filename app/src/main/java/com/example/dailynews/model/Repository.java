package com.example.dailynews.model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.example.dailynews.model.local.NewsDao;
import com.example.dailynews.model.network.ApiResponse;
import com.example.dailynews.model.network.ListNewsResponse;
import com.example.dailynews.model.network.ListNewsService;
import com.example.dailynews.util.AppExecutors;
import com.example.dailynews.util.Constants;
import com.example.dailynews.util.NetworkBoundResource;
import com.example.dailynews.util.Resource;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Retrofit;

@Singleton
public class Repository {

    private static final String TAG = "Repository";

    private final NewsDao newsDao;
    private AppExecutors appExecutors;
    private ListNewsService listNewsService;

    public Repository(NewsDao newsDao, AppExecutors appExecutors, ListNewsService listNewsService) {
        this.newsDao = newsDao;
        this.appExecutors = appExecutors;
        this.listNewsService = listNewsService;
    }

    public LiveData<Resource<List<NewsArticle>>> getNewsArticles() {
        return new NetworkBoundResource<List<NewsArticle>, ListNewsResponse>(appExecutors) {

            @Override
            protected void saveCallResult(@NonNull ListNewsResponse item) {
                Log.i(TAG, "saveCallResult: SAVING THE CALL" + item);
                NewsArticle[] newsArray = new NewsArticle[item.getArticles().size()];
                if(item.getArticles() != null) {
                    newsDao.insertRecipes((NewsArticle[]) item.getArticles().toArray(newsArray));
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable List<NewsArticle> data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<NewsArticle>> loadFromDb() {
                return newsDao.getNewsArticles();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<ListNewsResponse>> createCall() {
                return listNewsService.getNewsArticles(Constants.SOURCE, Constants.API_KEY);
            }
        }.getAsLiveData();
    }

}
