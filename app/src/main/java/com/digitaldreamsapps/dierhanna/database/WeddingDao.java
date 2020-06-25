package com.digitaldreamsapps.dierhanna.database;

import androidx.room.Dao;
import androidx.room.Query;
import com.digitaldreamsapps.dierhanna.models.Wedding;
import java.util.List;
import io.reactivex.Flowable;

@Dao
public abstract class WeddingDao extends BaseDao<Wedding>{
    @Query("SELECT * FROM Wedding")
    public abstract Flowable<List<Wedding>> getAll();
    @Query("SELECT * FROM Wedding")
    public abstract List<Wedding> getAllItems();



}
