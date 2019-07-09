package com.example.dailynews.di.listnews;

import androidx.lifecycle.ViewModel;

import com.example.dailynews.di.ViewModelKey;
import com.example.dailynews.listnews.ListNewsViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ListNewsViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ListNewsViewModel.class)
    public abstract ViewModel bindListNewsViewModel(ListNewsViewModel viewModel);
}
