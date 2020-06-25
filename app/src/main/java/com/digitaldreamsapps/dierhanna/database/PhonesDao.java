package com.digitaldreamsapps.dierhanna.database;

import androidx.room.Dao;
import androidx.room.Query;
import com.digitaldreamsapps.dierhanna.models.Phones;
import java.util.List;
import io.reactivex.Flowable;

@Dao
public abstract class PhonesDao extends BaseDao<Phones>{
    @Query("SELECT * FROM Phones")
    public abstract Flowable<List<Phones>> getAll();
    @Query("SELECT * FROM Phones")
    public abstract List<Phones> getAllItems();

}
