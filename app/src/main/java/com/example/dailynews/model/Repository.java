package com.example.dailynews.model;

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

    private final NewsDao newsDao;
    private AppExecutors appExecutors;
    private Retrofit retrofit;

    @Inject
    public Repository(NewsDao newsDao) {
        this.newsDao = newsDao;
    }

    public LiveData<Resource<List<NewsArticle>>> getNewsArticles() {
        return new NetworkBoundResource<List<NewsArticle>, ListNewsResponse>(appExecutors) {

            @Override
            protected void saveCallResult(@NonNull ListNewsResponse item) {
                if(item.getArticles() != null) {
                    newsDao.insertRecipes((NewsArticle[]) item.getArticles().toArray());
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
                return retrofit.create(ListNewsService.class).getNewsArticles(Constants.API_KEY);
            }
        }.getAsLiveData();
    }

}
