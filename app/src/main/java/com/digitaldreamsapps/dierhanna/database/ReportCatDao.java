package com.digitaldreamsapps.dierhanna.database;


import androidx.room.Dao;
import androidx.room.Query;
import com.digitaldreamsapps.dierhanna.models.ReportCat;
import java.util.List;
import io.reactivex.Flowable;

@Dao
public abstract class ReportCatDao extends BaseDao<ReportCat>{
    @Query("SELECT * FROM ReportCat")
    public abstract Flowable<List<ReportCat>> getAll();

    @Query("SELECT * FROM BusinessCat")
    public abstract List<ReportCat> getAllItems();




}
