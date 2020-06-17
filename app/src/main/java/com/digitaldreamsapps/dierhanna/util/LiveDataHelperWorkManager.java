package com.digitaldreamsapps.dierhanna.util;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

public class LiveDataHelperWorkManager {
    private LiveDataHelperWorkManager(){}
    private static LiveDataHelperWorkManager liveDataHelper;
    private MediatorLiveData<Integer> downloadPercent = new MediatorLiveData<>();

    synchronized public static LiveDataHelperWorkManager getInstance(){
        if(liveDataHelper == null)
            liveDataHelper = new LiveDataHelperWorkManager();
        return liveDataHelper;
    }

    public void updateDownloadPer(int percentage){
        downloadPercent.postValue(percentage);
    }

    public LiveData<Integer> observePercentage(){
        return downloadPercent;
    }
}
