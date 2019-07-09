package com.example.dailynews.model.network;

import androidx.lifecycle.LiveData;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ListNewsService {
    @GET("sources")
    LiveData<ApiResponse<ListNewsResponse>> getNewsArticles(@Query("apiKey") String apiKey);
}
