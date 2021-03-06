package com.digitaldreamsapps.dierhanna.repo;

import android.content.Context;
import androidx.room.Room;
import com.digitaldreamsapps.dierhanna.database.BaseDao;
import com.digitaldreamsapps.dierhanna.database.DaoRepository;
import java.util.List;
import io.reactivex.Flowable;

public class DeirHannaDataBase {

    private static DeirHannaDataBase mInstance;
    private DaoRepository daoRepository;

    private DeirHannaDataBase(Context mCtx) {



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
        BaseDao baseDao = daoRepository.getDao(t.get(0).getClass().getSimpleName());


            List<T> list = baseDao.getAllItems();
            for (T to : list){
                baseDao.delete(to);
            }




            baseDao.insertAll(t);

    }
    public  Flowable getAll(String className) {

        BaseDao baseDao = daoRepository.getDao(className);
        if (baseDao == null) return null;

            return baseDao.getAll();



    }

    public  boolean isAvailableDao(String className) {
        if (daoRepository.getDao(className) != null) return   true;
        return false;

    }


}
