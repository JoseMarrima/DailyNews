package com.example.dailynews.di;

import com.example.dailynews.di.listnews.ListNewsModule;
import com.example.dailynews.di.listnews.ListNewsScope;
import com.example.dailynews.di.listnews.ListNewsViewModelModule;
import com.example.dailynews.listnews.ListNewsActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {

    @ListNewsScope
    @ContributesAndroidInjector(modules = {ListNewsViewModelModule.class, ListNewsModule.class})
    abstract ListNewsActivity contributeListNewsArticlesActivity();
}
