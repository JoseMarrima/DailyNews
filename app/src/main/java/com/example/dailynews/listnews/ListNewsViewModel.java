package com.example.dailynews.listnews;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.dailynews.model.NewsArticle;
import com.example.dailynews.model.Repository;
import com.example.dailynews.util.Resource;

import java.util.List;

import javax.inject.Inject;

public class ListNewsViewModel extends ViewModel {

    private static final String TAG = "ListNewsViewModel";

    private Repository repository;
    private MediatorLiveData<Resource<List<NewsArticle>>> news = new MediatorLiveData<>();

    @Inject
    public ListNewsViewModel(Repository repository) {
        this.repository = repository;
        Log.i(TAG, "ListNewsViewModel: " + repository);
    }

    public LiveData<Resource<List<NewsArticle>>> getNewsArticles() {
        loadGetArticles();
        return news;
    }


    private void loadGetArticles() {
        if(repository.getNewsArticles() != null) {
            final LiveData<Resource<List<NewsArticle>>> repositorySource = repository.getNewsArticles();

            news.addSource(repositorySource, new Observer<Resource<List<NewsArticle>>>() {
                @Override
                public void onChanged(Resource<List<NewsArticle>> listResource) {
                    if(listResource != null) {
                        news.setValue(listResource);
                        if(listResource.status == Resource.Status.SUCCESS) {
                            news.removeSource(repositorySource);
                        }
                        else if(listResource.status == Resource.Status.ERROR) {
                            news.removeSource(repositorySource);
                        }
                    }
                    else {
                        news.removeSource(repositorySource);
                    }
                }
            });
        }
    }
}
