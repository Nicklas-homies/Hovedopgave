package com.homies.hovedopgave;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.homies.hovedopgave.Fragments.ExerciseFragment;
import com.homies.hovedopgave.Fragments.HistoryFragment;
import com.homies.hovedopgave.Fragments.HomeFragment;
import com.homies.hovedopgave.Fragments.ProgramsFragment;
import com.homies.hovedopgave.Fragments.ProfileFragment;
import com.homies.hovedopgave.utils.LanguageHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Updatable {

    private boolean isLoggedOut = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LanguageHelper.languageHelper().setup(this);
        LanguageHelper.languageHelper().loadLocale();
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();

        if (isLoggedOut) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new ProfileFragment()).commit();
        }

    }

    public void onClickLogout (View view) {
        FirebaseAuth.getInstance().signOut();
        UserRepo.r().setEmail(null);
        recreate();
        isLoggedOut = true;
    }

    @Override
    public void update(Object o) {
        System.out.println("We updated: ");
    }


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

            case R.id.nav_profile:
                selectedFragment = new ProfileFragment();
                break;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                selectedFragment).commit();

        return true;
    };
}