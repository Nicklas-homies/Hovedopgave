package com.homies.hovedopgave;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.homies.hovedopgave.Fragments.ExerciseFragment;
import com.homies.hovedopgave.Fragments.HistoryFragment;
import com.homies.hovedopgave.Fragments.HomeFragment;
import com.homies.hovedopgave.Fragments.ProgramsFragment;
import com.homies.hovedopgave.Fragments.SettingsFragment;
import com.homies.hovedopgave.Repos.Repo;
import com.homies.hovedopgave.utils.LanguageHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Updatable {
    ArrayList<String> users = new ArrayList();
    //Button createUserButton;
    //EditText usernameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LanguageHelper.languageHelper().setup(this);
        LanguageHelper.languageHelper().loadLocale();
        setContentView(R.layout.activity_main);
        //createUserButton = findViewById(R.id.createUserButton);
        //usernameText = findViewById(R.id.addUsername);
        Repo.r().setup(this, users);
        //createUserButton.setOnClickListener(v ->{
        //    Repo.r().addUser(usernameText.getText().toString());
        //});

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();
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

    // Jonas - Navigation bar
    private BottomNavigationView.OnItemSelectedListener navListener = item -> {
        Fragment selectedFragment = null;

        switch (item.getItemId()) {
            case R.id.nav_home:
                selectedFragment = new HomeFragment();
                break;

            case R.id.nav_programs:
                selectedFragment = new ProgramsFragment();
                break;

            case R.id.nav_exercises:
                selectedFragment = new ExerciseFragment();
                break;

            case R.id.nav_history:
                selectedFragment = new HistoryFragment();
                break;

            case R.id.nav_settings:
                selectedFragment = new SettingsFragment();
                break;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                selectedFragment).commit();

        return true;
    };
}