package com.homies.hovedopgave;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Notification;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import com.homies.hovedopgave.utils.LanguageHelper;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LanguageHelper.languageHelper().setup(this);
        LanguageHelper.languageHelper().loadLocale();
        setContentView(R.layout.activity_settings);

    }

    public void backClicked(View view){
        finish();
    }

//    public void languageClicked(View v){
//        startActivity(new Intent(getApplicationContext(), LanguageActivity.class));
//    }

    public void languageClicked(View v){
        final String[] listItems = {"english", "danish"};
        AlertDialog.Builder myBuilder = new AlertDialog.Builder(SettingsActivity.this);
        myBuilder.setTitle("Choose language").setSingleChoiceItems(listItems, -1, (dialog, which) -> {
            if (which == 0){
                LanguageHelper.languageHelper().saveLocale("en");
                recreate();
            }else if (which == 1) {
                LanguageHelper.languageHelper().saveLocale("da");
                recreate();
            }
            dialog.dismiss();
        });
        AlertDialog myDialog = myBuilder.create();
        myDialog.show();
    }

//    public void saveLocale(String languageToLoad){
//        Locale locale = new Locale(languageToLoad);
//        Locale.setDefault(locale);
//        Configuration config = new Configuration();
//        config.locale = locale;
//        getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());
//
//        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
//        editor.putString("My_Lang", languageToLoad);
//        editor.apply();
//
////        recreate();
//    }
//
//    public void loadLocale(){
//        SharedPreferences preferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
//        String lang = preferences.getString("My_Lang", "");
//        saveLocale(lang);
//        ActionBar myActionBar = getSupportActionBar();
//        myActionBar.setTitle(getResources().getString(R.string.app_name));
//    }


//    @Override
//    protected void onStop() {
//        super.onStop();
//        System.out.println("Stop called");
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        System.out.println("Destroy called");
//    }
}