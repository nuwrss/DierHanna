package com.digitaldreamsapps.dierhanna.viewmodels;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.digitaldreamsapps.dierhanna.util.Config;
import com.digitaldreamsapps.dierhanna.util.Language;

import java.util.Locale;

import static com.digitaldreamsapps.dierhanna.util.Config.setLanguageConfig;

public  class BaseActivity extends AppCompatActivity {


    public void setLanguage(){
        SharedPreferences sharedPreferences
                = getSharedPreferences("MySharedPref",
                MODE_PRIVATE);

        Log.e("www",Locale.getDefault().getLanguage());
         String l = sharedPreferences.getString("language", "");
         if (l.equals(Locale.getDefault().getLanguage())){
             return;
         }


        saveLanguage(setLanguageConfig(l));
    }

    public void saveLanguage(String language){
        SharedPreferences sharedPreferences
                = getSharedPreferences("MySharedPref",
                MODE_PRIVATE);
        SharedPreferences.Editor myEdit
                = sharedPreferences.edit();
        myEdit.putString("language",language);
        myEdit.commit();
        setLanguageConfig(language);
        rec(language);


    }

    public void rec(String language){

        Locale locale;

        locale = new Locale(language);
        Configuration config = new Configuration(getResources().getConfiguration());
        Locale.setDefault(locale);
        config.setLocale(locale);

        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        recreate();

    }
}
