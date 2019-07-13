package com.example.dailynews.di;

import android.app.Application;

import androidx.room.Room;

import com.example.dailynews.model.Repository;
import com.example.dailynews.model.local.NewsDao;
import com.example.dailynews.model.local.NewsDatabase;
import com.example.dailynews.model.network.ListNewsService;
import com.example.dailynews.util.AppExecutors;
import com.example.dailynews.util.Constants;
import com.example.dailynews.util.LiveDataCallAdapterFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.dailynews.model.local.NewsDatabase.DATABASE_NAME;

@Module
public class AppModule {
    @Singleton
    @Provides
    static Retrofit provideRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    static ListNewsService provideListNewsService(Retrofit retrofit) {
        return retrofit.create(ListNewsService.class);
    }

    @Singleton
    @Provides
    static NewsDao provideNewsDao(NewsDatabase newsDatabase) {
        return newsDatabase.getNewsDao();
    }

    @Singleton
    @Provides
    static NewsDatabase provideNewsDatabase(Application application) {
        return Room.databaseBuilder(application, NewsDatabase.class, DATABASE_NAME).build();
    }

    @Singleton
    @Provides
    static AppExecutors provideAppExecutors() {
        return new AppExecutors();
    }

    @Singleton
    @Provides
    static Repository provideRepository(NewsDao newsDao, AppExecutors appExecutors, ListNewsService service) {
        return new Repository(newsDao, appExecutors, service);
    }
}
