package com.homies.hovedopgave;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Locale;

public class LanguageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
        setContentView(R.layout.activity_language);
    }

    public void backClicked(View view){
        finish();
    }

    public void langClicked(View v){
        Button button = (Button) v;
        String lang = button.getText().toString().toLowerCase().substring(0, 2);
        String languageToLoad;
        switch (lang){
            case "da":
//                languageToLoad = "da";
                saveLocale("da");
//                recreate();
                break;
            default:
//                languageToLoad = "en";
                saveLocale("en");
//                recreate();
        }



//        Intent intent = new Intent(getApplicationContext(), this.getClass());
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//        recreate();
    }

    public void saveLocale(String languageToLoad){
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());

        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_Lang", languageToLoad);
        editor.apply();

        recreate();
    }

    public void loadLocale(){
        SharedPreferences preferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String lang = preferences.getString("My_Lang", "");
        saveLocale(lang);
    }
}