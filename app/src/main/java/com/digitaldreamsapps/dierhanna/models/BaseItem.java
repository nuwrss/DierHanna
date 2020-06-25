package com.digitaldreamsapps.dierhanna.models;

import androidx.annotation.NonNull;

public abstract class BaseItem {
    @NonNull
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
