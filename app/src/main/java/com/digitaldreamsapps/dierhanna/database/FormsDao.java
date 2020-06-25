package com.digitaldreamsapps.dierhanna.database;


import androidx.room.Dao;
import androidx.room.Query;
import com.digitaldreamsapps.dierhanna.models.Form;
import java.util.List;
import io.reactivex.Flowable;

@Dao
public abstract class FormsDao extends BaseDao<Form>{
    @Query("SELECT * FROM form")
    public abstract Flowable<List<Form>> getAll();

    @Query("SELECT * FROM form")
    public abstract List<Form> getAllItems();




}
