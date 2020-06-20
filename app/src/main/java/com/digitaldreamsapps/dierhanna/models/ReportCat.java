package com.digitaldreamsapps.dierhanna.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import com.digitaldreamsapps.dierhanna.util.Config;
import com.digitaldreamsapps.dierhanna.util.Language;

import java.io.Serializable;

@Entity(primaryKeys = {"nameAr"})
public class ReportCat implements Serializable {
    @NonNull
    private String nameAr;
    private String nameHe;

    @NonNull
    public String getNameAr() {
        return nameAr;
    }

    public void setNameAr(@NonNull String nameAr) {
        this.nameAr = nameAr;
    }

    public String getNameHe() {
        return nameHe;
    }

    public void setNameHe(String nameHe) {
        this.nameHe = nameHe;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    private String nameEn;
    private String icon;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
    public String getName() {
        if (Config.language== Language.ARABIC) return nameAr;
        if (Config.language==Language.HEBREW) return nameHe;
        else{
            if (nameEn == null || nameEn.trim().equals("")) return nameAr;
            return nameEn;
        }
    }



}
