package com.example.dailynews.util;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AppExecutors {

    @Inject
    public AppExecutors() {
    }

    private final Executor mDiskIO = Executors.newSingleThreadExecutor();

    private final Executor mMainThreadExecutor = new MainThreadExecutor();


    public Executor diskIO(){
        return mDiskIO;
    }

    public Executor mainThread(){
        return mMainThreadExecutor;
    }

    private static class MainThreadExecutor implements Executor{

        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }
    }
}