package com.example.dailynews.model.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.dailynews.model.NewsArticle;

@Database(entities = {NewsArticle.class}, version = 1)
public abstract class NewsDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "notes_db";

    public abstract NewsDao getNewsDao();
}
