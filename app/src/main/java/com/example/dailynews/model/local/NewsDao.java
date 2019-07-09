package com.example.dailynews.model.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.dailynews.model.NewsArticle;

import java.util.List;

import static androidx.room.OnConflictStrategy.IGNORE;

@Dao
public interface NewsDao {
    @Insert(onConflict = IGNORE)
    long[] insertRecipes(NewsArticle... newsArticles);

    @Query("SELECT * FROM news_table")
    LiveData<List<NewsArticle>> getNewsArticles();
}
