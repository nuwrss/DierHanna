package com.digitaldreamsapps.dierhanna.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Query;


import com.digitaldreamsapps.dierhanna.models.News;
import java.util.List;
import io.reactivex.Flowable;

@Dao
public abstract class NewsDao  extends BaseDao<News>{
    @Query("SELECT * FROM News")
    public abstract Flowable<List<News>> getAll();

    @Query("SELECT * FROM News")
    public abstract List<News> getAllItems();

}
