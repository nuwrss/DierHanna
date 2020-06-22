package com.digitaldreamsapps.dierhanna.viewmodels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import com.digitaldreamsapps.dierhanna.repo.Repository;
import com.digitaldreamsapps.dierhanna.util.ConnectionStatus;

public class BaseViewModel <T> extends AndroidViewModel {


    private Repository repository ;

    public BaseViewModel(@NonNull Application application) {
        super(application);

    }

    public void setChildFirebase(String childFirebase, T t , LifecycleOwner lifecycleOwner){
        repository = new Repository(getApplication(),lifecycleOwner);

        repository.setFirebaseChild(childFirebase,t);

    }

    public LiveData getData(){
        return repository.getData();
    }

    public LiveData<ConnectionStatus> getConnectionStatusLiveData(){
        return  repository.getConnectionStatusLiveData();
    }


    @Override
    protected void onCleared() {
        repository.clear();
        super.onCleared();
    }
}
