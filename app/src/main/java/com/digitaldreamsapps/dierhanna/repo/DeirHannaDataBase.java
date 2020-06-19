package com.digitaldreamsapps.dierhanna.repo;

import android.content.Context;

import androidx.room.Room;

import com.digitaldreamsapps.dierhanna.database.BaseDao;
import com.digitaldreamsapps.dierhanna.database.DaoRepository;

import java.util.List;

import io.reactivex.Flowable;

public class DeirHannaDataBase {
    private Context mCtx;
    private static DeirHannaDataBase mInstance;
    private DaoRepository daoRepository;
    private DeirHannaDataBase(Context mCtx) {
        this.mCtx = mCtx;


        daoRepository = Room.databaseBuilder(mCtx, DaoRepository.class, "DeirHannaDataBase").allowMainThreadQueries().build();
    }
    public static synchronized DeirHannaDataBase getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new DeirHannaDataBase(mCtx);
        }
        return mInstance;
    }


    public  <T> void insertAll(List<T> t) {

        if (t==null || t.isEmpty()) return;
        BaseDao baseDao = daoRepository.getDao(t.get(0));

            baseDao.insertAll(t);

    }
    public <T> Flowable getAll(T t) {

        BaseDao baseDao = daoRepository.getDao(t);
        if (baseDao == null) return null;

            return baseDao.getAll();



    }

    public <T> boolean isAvailableDao(T t) {
        if (daoRepository.getDao(t) != null) return   true;
        return false;

    }
}
