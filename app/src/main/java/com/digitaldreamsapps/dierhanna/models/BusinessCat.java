package com.digitaldreamsapps.dierhanna.models;

import com.digitaldreamsapps.dierhanna.util.Config;
import com.digitaldreamsapps.dierhanna.util.Language;

import java.util.ArrayList;

public class BusinessCat {
    private String nameAr;
    private String nameHe;
    private String nameEn;

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

    private String icon;

}
