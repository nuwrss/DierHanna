package com.digitaldreamsapps.dierhanna.interfaces;

import com.google.firebase.database.DataSnapshot;

public interface OnDataChangedRepository <T> {
    void onDataChangedFirebase(DataSnapshot dataSnapshot);
    void onDataChangedDataBase(T t);
    void onNoDataReceived();
}
