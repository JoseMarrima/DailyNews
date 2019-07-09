package com.example.dailynews.di.listnews;

import com.example.dailynews.listnews.ListNewsAdapter;

import dagger.Module;
import dagger.Provides;

@Module
public class ListNewsModule {
    @ListNewsScope
    @Provides
    static ListNewsAdapter provideAdapter() {
        return new ListNewsAdapter();
    }

}
