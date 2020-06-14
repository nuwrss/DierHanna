package com.digitaldreamsapps.dierhanna.models;

import java.util.ArrayList;

public class Phones {
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
