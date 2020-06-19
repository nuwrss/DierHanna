package com.digitaldreamsapps.dierhanna.database;



import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;

import androidx.room.OnConflictStrategy;
import androidx.room.Update;


import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface BaseDao <T> {

    Flowable<List<T>> getAll();
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<T> tList);

    @Delete
    void delete(T t);

    @Update
    void update(List<T> tList);


}
