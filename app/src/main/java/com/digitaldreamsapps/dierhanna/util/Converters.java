package com.digitaldreamsapps.dierhanna.util;

import androidx.room.TypeConverter;
import com.digitaldreamsapps.dierhanna.models.Business;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Converters {
    private static Gson gson = new Gson();
    @TypeConverter
    public static String fromArrayList(ArrayList<String> strings) {
        return gson.toJson(strings);

    }

    @TypeConverter
    public static ArrayList<String> fromString(String s) {

        if (s == null) {
            return new ArrayList<>();
        }

        Type listType = new TypeToken<List<String>>() {}.getType();

        return gson.fromJson(s, listType);



    }


    @TypeConverter
    public static ArrayList<Business> stringToList(String data) {
        if (data == null) {
            return new ArrayList<>();
        }

        Type listType = new TypeToken<List<Business>>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String ListToString(ArrayList<Business> someObjects) {
        return gson.toJson(someObjects);
    }
}
