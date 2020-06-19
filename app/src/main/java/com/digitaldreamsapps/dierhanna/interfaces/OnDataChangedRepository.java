package com.digitaldreamsapps.dierhanna.interfaces;



public interface OnDataChangedRepository <T> {

    void onDataChangedDataBase(T t);
    void onNoDataReceived();
}
