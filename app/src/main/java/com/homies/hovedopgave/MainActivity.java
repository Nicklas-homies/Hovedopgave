package com.homies.hovedopgave;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.homies.hovedopgave.exercises.ExerciseActivity;
import com.homies.hovedopgave.utils.LanguageHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Updatable {
    ArrayList<String> users = new ArrayList();
    Button createUserButton;
    EditText usernameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LanguageHelper.languageHelper().setup(this);
        LanguageHelper.languageHelper().loadLocale();
        setContentView(R.layout.activity_main);
        createUserButton = findViewById(R.id.createUserButton);
        usernameText = findViewById(R.id.addUsername);
        Repo.r().setup(this, users);
        createUserButton.setOnClickListener(v ->{
            Repo.r().addUser(usernameText.getText().toString());
        });

    }

    public void goToExercise(View view) {
        startActivity(new Intent(getApplicationContext(), ExerciseActivity.class));
    }

    @Override
    public void update(Object o) {
        System.out.println("We updated: " + users);
    }

    public void settingsClicked(View view){
        startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
    }

//    public void langClicked(View v){
//        String languageToLoad  = "en";
//        System.out.println(Locale.getDefault().getLanguage());
//        if (Locale.getDefault().getLanguage().equals("en")){
//            languageToLoad = "da";
//        }
//        System.out.println("langToLoad: " + languageToLoad);
//        Locale locale = new Locale(languageToLoad);
//        Locale.setDefault(locale);
//        Configuration config = new Configuration();
//        config.locale = locale;
//        this.getResources().updateConfiguration(config,this.getResources().getDisplayMetrics());
//
//        Intent intent = new Intent(this, this.getClass());
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//    }
}