package com.digitaldreamsapps.dierhanna.models;


import androidx.room.Entity;

@Entity(primaryKeys = {"title"})
public class Appointment extends BaseItem {

    private String link;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
