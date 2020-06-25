package com.digitaldreamsapps.dierhanna.models;


import androidx.room.Entity;
import java.util.ArrayList;
@Entity(primaryKeys = {"title"})
public class Phones extends BaseItem {

    private String email ;
    private ArrayList<String> phones ;



    public ArrayList<String> getPhones() {
        return phones;
    }

    public void setPhones(ArrayList<String> phones) {
        this.phones = phones;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
