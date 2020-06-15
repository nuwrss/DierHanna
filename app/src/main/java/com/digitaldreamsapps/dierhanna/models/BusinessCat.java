package com.digitaldreamsapps.dierhanna.models;

import com.digitaldreamsapps.dierhanna.util.Config;
import com.digitaldreamsapps.dierhanna.util.Language;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

public class BusinessCat {
    private String nameAr;
    private String nameHe;
    private String nameEn;
    private String icon;

    public ArrayList<Place> getPlaces() {
        return places;
    }

    private ArrayList<Place>places=new ArrayList<>();

    public void addPlace(Place place){
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
