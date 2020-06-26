package com.digitaldreamsapps.dierhanna.models;

import androidx.annotation.NonNull;

import java.io.Serializable;

public abstract class BaseItem implements Serializable {
    @NonNull
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
