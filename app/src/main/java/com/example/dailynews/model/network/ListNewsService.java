package com.example.dailynews.requests;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ListNewsService {
    @GET("sources")
    Flowable<ApiResponse<ListNewsResponse>> getNewsArticles(@Query("apiKey") String apiKey);
}
