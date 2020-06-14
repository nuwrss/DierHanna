package com.digitaldreamsapps.dierhanna.models;

import com.digitaldreamsapps.dierhanna.util.Config;
import com.digitaldreamsapps.dierhanna.util.Language;

import java.util.ArrayList;

public abstract class Place {
    private String nameEn;
    private String nameAr;
    private String nameHe;
    private Double lat;
    private Double longt;
    private String pic;
    private  ArrayList<String> pics;
    private Boolean show_image_inside_dialog_window= false;

    public Boolean istShow_image_inside_marker_window() {
        if (show_image_inside_marker_window==null)return false;
        return show_image_inside_marker_window;
    }


    public void setShowimageinsidedialogwindow(Boolean show_image_inside_marker_window) {

        this.show_image_inside_dialog_window=show_image_inside_marker_window;
    }


    public Boolean isShowimageinsidedialogwindow() {
        if(show_image_inside_dialog_window==null)return false;
        return show_image_inside_dialog_window;
    }

    private Boolean show_image_inside_marker_window = false;

    public void setShowImageInMarkerWindow(Boolean show_image_inside_marker_window) {
        this.show_image_inside_marker_window=show_image_inside_marker_window;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public void setLongt(Double longt) {
        this.longt = longt;
    }


    public void setPhones(ArrayList<String> phones) {
        this.phones = phones;
    }


    public void setPict(String pict) {

        this.pic=pict;
    }


    public void setPictures(ArrayList<String> pics) {
        this.pics=pics;
    }

    private String marker;

    public void setMarker(String marker) {
        this.marker=marker;

    }


    public String getMarker() {
        return marker;
    }

    private String descriptionAr;
    private String descriptionHe;


    public void setNameAr(String nameAr) {

        this.nameAr = nameAr;
    }

    public void setNameHe(String nameHe) {
        this.nameHe = nameHe;
    }


    public void setNameEn(String nameAr) {
        this.nameEn = nameAr;
    }


    public void setDescriptionAr(String descriptionAr) {
        this.descriptionAr = descriptionAr;
    }

    public void setDescriptionHe(String descriptionHe) {
        this.descriptionHe = descriptionHe;
    }

    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
    }

    private String descriptionEn;
    private ArrayList<String> phones ;

    public ArrayList<String> getPhones() {
        return phones;
    }


    public ArrayList<String> getPictures() {
        return pics;
    }



    public String getName() {
        if (Config.language==Language.ARABIC) return nameAr;
        if (Config.language==Language.HEBREW) return nameHe;
        else{
            if (nameEn == null || nameEn.trim().equals("")) return nameAr;
            return nameEn;
        }
    }


    public Double getLat() {
        return lat;
    }


    public Double getLongt() {
        return longt;
    }


    public String getDescription() {
        if (Config.language==Language.ARABIC) return descriptionAr;
        if (Config.language==Language.HEBREW) return descriptionHe;
        else{
            if (descriptionEn == null || descriptionEn.trim().equals("")) return descriptionAr;
            return descriptionEn;
        }

    }


    public String getPic() {
        return pic;
    }
}
