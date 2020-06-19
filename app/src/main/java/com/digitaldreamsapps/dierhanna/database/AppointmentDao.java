package com.digitaldreamsapps.dierhanna.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import com.digitaldreamsapps.dierhanna.models.Appointment;


import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface AppointmentDao extends BaseDao<Appointment> {
    @Query("SELECT * FROM Appointment")
    Flowable<List<Appointment>> getAll();


}
