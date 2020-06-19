package com.digitaldreamsapps.dierhanna.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;


import androidx.room.Query;

import com.digitaldreamsapps.dierhanna.models.Form;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface FormsDao extends BaseDao<Form>{
    @Query("SELECT * FROM form")
    Flowable<List<Form>> getAll();

}
