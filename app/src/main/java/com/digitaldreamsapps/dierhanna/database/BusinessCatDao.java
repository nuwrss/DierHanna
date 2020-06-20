package com.digitaldreamsapps.dierhanna.database;


import androidx.room.Dao;
import androidx.room.Query;

import com.digitaldreamsapps.dierhanna.models.BusinessCat;


import java.util.List;

import io.reactivex.Flowable;

@Dao
public abstract class BusinessCatDao extends BaseDao<BusinessCat>{
    @Query("SELECT * FROM BusinessCat")
    public abstract Flowable<List<BusinessCat>> getAll();

    @Query("SELECT * FROM BusinessCat")
    public abstract List<BusinessCat> getAllItems();




}
