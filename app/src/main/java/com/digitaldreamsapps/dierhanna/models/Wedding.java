package com.digitaldreamsapps.dierhanna.models;

import androidx.room.Entity;
@Entity(primaryKeys = {"title"})
public class Wedding extends BaseItem{


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    private String date;
}
