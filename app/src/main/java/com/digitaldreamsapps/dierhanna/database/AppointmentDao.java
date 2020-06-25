package com.digitaldreamsapps.dierhanna.database;


import androidx.room.Dao;
import androidx.room.Query;
import com.digitaldreamsapps.dierhanna.models.Appointment;


import java.util.List;

import io.reactivex.Flowable;

@Dao
public abstract class AppointmentDao extends BaseDao<Appointment> {
    @Query("SELECT * FROM Appointment")
    public abstract Flowable<List<Appointment>> getAll();

    @Query("SELECT * FROM Appointment")
    public abstract List<Appointment> getAllItems();


}
