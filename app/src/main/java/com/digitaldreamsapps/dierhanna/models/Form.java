package com.digitaldreamsapps.dierhanna.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(primaryKeys = {"title"})
public class Form {
    @NonNull
    private String title;
    private String link;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
