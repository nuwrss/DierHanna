package com.digitaldreamsapps.dierhanna.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.digitaldreamsapps.dierhanna.database.DeirHannaDataBase;
import com.digitaldreamsapps.dierhanna.repo.FirebaseDatabaseRepository;
import com.digitaldreamsapps.dierhanna.util.ConnectionLiveData;
import com.digitaldreamsapps.dierhanna.util.ConnectionStatus;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class BaseViewModel <T> extends AndroidViewModel {

    private  DatabaseReference mDatabase ;
    private  FirebaseDatabaseRepository liveData;
    private ConnectionLiveData connectionLiveData;
    private MutableLiveData<ArrayList<T>> databaseLiveData = new MutableLiveData<ArrayList<T>>();


    public BaseViewModel(@NonNull Application application) {
        super(application);

    }

    public void setChildFirebase(String childFirebase){
        mDatabase = FirebaseDatabase.getInstance().getReference().child(childFirebase);
        liveData = new FirebaseDatabaseRepository(mDatabase);
        connectionLiveData = new ConnectionLiveData(getApplication());
    }

    @NonNull
    public LiveData<DataSnapshot> getdataSnapshotLiveData(){

        return liveData;
    }

    public LiveData<ConnectionStatus> getConnectionStatusLiveData(){
        return  connectionLiveData;
    }

    public  MutableLiveData<ArrayList<T>> getDataFromDatabase(T t){
        if (DeirHannaDataBase.getInstance(getApplication()).isAvailableDao(t)) {
            DeirHannaDataBase.getInstance(getApplication()).getAll(t).subscribeOn(Schedulers.io())
                    .subscribe(new Consumer() {

                        @Override
                        public void accept(Object o) throws Exception {
                            List<T> list = (List<T>) o;
                            databaseLiveData.postValue((ArrayList<T>) list);

                        }
                    });
        }
        return databaseLiveData;
    }
}
