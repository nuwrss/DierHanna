package com.digitaldreamsapps.dierhanna.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import java.io.Serializable;
@Entity(primaryKeys = {"title"})
public class News implements Serializable {
    @NonNull
    private String title;
    private String article;
    private String image;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    private String date;

}
