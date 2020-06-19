package com.digitaldreamsapps.dierhanna.database;

import androidx.room.Dao;
import androidx.room.Query;
import com.digitaldreamsapps.dierhanna.models.News;
import java.util.List;
import io.reactivex.Flowable;

@Dao
public interface NewsDao  extends BaseDao<News>{
    @Query("SELECT * FROM News")
    Flowable<List<News>> getAll();

}
