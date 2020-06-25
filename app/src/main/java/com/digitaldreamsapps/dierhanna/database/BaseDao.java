package com.digitaldreamsapps.dierhanna.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;
import java.util.List;
import io.reactivex.Flowable;

@Dao
public abstract class BaseDao <T> {

    public abstract Flowable<List<T>> getAll();
    public abstract List<T> getAllItems();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertAll(List<T> tList);

    @Delete
    public abstract void delete(T t);

    @Delete
    public abstract void deleteAll(T... ts);



    @Update
    abstract void update(List<T> tList);




}
