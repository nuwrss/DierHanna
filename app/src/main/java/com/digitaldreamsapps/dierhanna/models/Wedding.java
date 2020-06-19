package com.digitaldreamsapps.dierhanna.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(primaryKeys = {"title"})
public class Wedding {
    @NonNull
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    private String date;
}
