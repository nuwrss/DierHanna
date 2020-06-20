package com.digitaldreamsapps.dierhanna.models;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;

import com.digitaldreamsapps.dierhanna.util.Config;
import com.digitaldreamsapps.dierhanna.util.Language;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
@Entity(primaryKeys = {"nameAr"})
public class BusinessCat {
    @NonNull
    private String nameAr;
    private String nameHe;

    @NonNull
    public String getNameAr() {
        return nameAr;
    }

    public String getNameHe() {
        return nameHe;
    }

    public String getNameEn() {
        return nameEn;
    }

    private String nameEn;
    private String icon;

    public ArrayList<Business> getPlaces() {
        return places;
    }

    private ArrayList<Business>places=new ArrayList<>();

    public void setPlaces(ArrayList<Business> places) {
        this.places = places;
    }

    public void addPlace(Business place){
        places.add(place);
    }
    public void clearPlaces(){
        places.clear();
    }

    public String getName() {
        if (Config.language== Language.ARABIC) return nameAr;
        if (Config.language==Language.HEBREW) return nameHe;
        else{
            if (nameEn == null || nameEn.trim().equals("")) return nameAr;
            return nameEn;
        }
    }

    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
    }



    public void setNameHe(String nameHe) {
        this.nameHe = nameHe;
    }



    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setDetails(DataSnapshot details){
        setNameAr((String) details.child("nameAr").getValue());

        setNameHe((String) details.child("nameHe").getValue());
        setNameEn((String) details.child("nameEn").getValue());
        setIcon((String) details.child("icon").getValue());
    }

}
