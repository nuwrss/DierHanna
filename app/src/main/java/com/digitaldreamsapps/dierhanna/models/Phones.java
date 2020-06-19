package com.digitaldreamsapps.dierhanna.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import java.util.ArrayList;
@Entity(primaryKeys = {"title"})
public class Phones {
    @NonNull
    private String title ;
    private String email ;
    private ArrayList<String> phones ;

    public String getTitle() {
        return title;
    }

    public ArrayList<String> getPhones() {
        return phones;
    }

    public void setPhones(ArrayList<String> phones) {
        this.phones = phones;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
