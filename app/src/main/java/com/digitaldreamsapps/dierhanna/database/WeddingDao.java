package com.digitaldreamsapps.dierhanna.database;

import androidx.room.Dao;
import androidx.room.Query;

import com.digitaldreamsapps.dierhanna.models.News;
import com.digitaldreamsapps.dierhanna.models.Wedding;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface WeddingDao extends BaseDao<Wedding>{
    @Query("SELECT * FROM Wedding")
    Flowable<List<Wedding>> getAll();

}
