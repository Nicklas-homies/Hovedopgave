package com.homies.hovedopgave.utils;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;


import android.content.SharedPreferences;
import android.content.res.Configuration;

import java.util.Locale;

public class LanguageHelper{

    private static LanguageHelper langHelp = new LanguageHelper();
    private Activity parent;

    public static LanguageHelper languageHelper(){
        return langHelp;
    }

    public void setup(Activity activity){
        parent = activity;
    }

    public void saveLocale(String languageToLoad){
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        parent.getBaseContext().getResources().updateConfiguration(config,parent.getBaseContext().getResources().getDisplayMetrics());

        SharedPreferences.Editor editor = parent.getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_Lang", languageToLoad);
        editor.apply();

//        recreate();
    }

    public void loadLocale(){
        SharedPreferences preferences = parent.getSharedPreferences("Settings", MODE_PRIVATE);
        String lang = preferences.getString("My_Lang", "");
        saveLocale(lang);
    }
}
