package com.digitaldreamsapps.dierhanna.interfaces;

import com.google.firebase.database.DataSnapshot;

public interface OnDataChangedFireBase {
    void onDataChanged(DataSnapshot dataSnapshot);
    void onNoDataReceived();
}
