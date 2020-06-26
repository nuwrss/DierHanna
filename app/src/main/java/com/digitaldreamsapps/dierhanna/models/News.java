package com.digitaldreamsapps.dierhanna.models;


import androidx.room.Entity;
import java.io.Serializable;
import java.util.ArrayList;

@Entity(primaryKeys = {"title"})
public class News extends BaseItem implements Serializable {

    private String article;
    private String image;

    private ArrayList<String> images;

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
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
