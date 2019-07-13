package com.example.dailynews.model.network;

import androidx.lifecycle.LiveData;

import io.reactivex.Flowable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ListNewsService {
    @GET("top-headlines")
    LiveData<ApiResponse<ListNewsResponse>> getNewsArticles(@Query("sources") String source, @Query("apiKey") String apiKey);

    @GET("top-headlines")
    Call<ListNewsResponse> test(@Query("sources") String source, @Query("apiKey") String apiKey);
}
