package com.digitaldreamsapps.dierhanna.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.digitaldreamsapps.dierhanna.repo.FirebaseDatabaseRepository;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PhonesViewModel extends ViewModel {
    private static final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Important phones");
    private final FirebaseDatabaseRepository liveData = new FirebaseDatabaseRepository(mDatabase);

    @NonNull
    public LiveData<DataSnapshot> getdataSnapshotLiveData(){
        return liveData;
    }
}
