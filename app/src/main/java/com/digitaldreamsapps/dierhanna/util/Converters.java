package com.digitaldreamsapps.dierhanna.util;

import androidx.room.TypeConverter;

import java.util.ArrayList;



public class Converters {
    @TypeConverter
    public static String fromArrayList(ArrayList<String> strings) {
        return  strings.toString();

    }

    @TypeConverter
    public static ArrayList<String> fromString(String s) {
        s=s.replace("[","");
        s=s.replaceAll("]","");
        String[]arrS =s.split(",");
        ArrayList<String>strings = new ArrayList<>();
        for (String ss : arrS){
            strings.add(ss);
        }
        return strings;
    }
}
