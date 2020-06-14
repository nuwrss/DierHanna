package com.digitaldreamsapps.dierhanna.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.digitaldreamsapps.dierhanna.models.News;
import com.digitaldreamsapps.dierhanna.repo.FirebaseDatabaseRepository;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class MainViewModel extends ViewModel {


    private static final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("MainNews");
    private final FirebaseDatabaseRepository liveData = new FirebaseDatabaseRepository(mDatabase);

    @NonNull
    public LiveData<DataSnapshot> getdataSnapshotLiveData(){
        return liveData;
    }
}
