package com.homies.hovedopgave;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.homies.hovedopgave.Fragments.ExerciseFragment;
import com.homies.hovedopgave.Fragments.HistoryFragment;
import com.homies.hovedopgave.Fragments.HomeFragment;
import com.homies.hovedopgave.Fragments.ProgramsFragment;
import com.homies.hovedopgave.Fragments.ProfileFragment;
import com.homies.hovedopgave.Repos.UserRepo;
import com.homies.hovedopgave.utils.LanguageHelper;

public class MainActivity extends AppCompatActivity implements Updatable {

    private boolean isLoggedOut = false;

    public BottomNavigationView bottomNav;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LanguageHelper.languageHelper().setup(this);
        LanguageHelper.languageHelper().loadLocale();
        setContentView(R.layout.activity_main);

        bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();

        if (isLoggedOut) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new ProfileFragment(bottomNav)).commit();
        }

    }

    public void onClickLogout (View view) {
        FirebaseAuth.getInstance().signOut();
        UserRepo.r().setEmail(null);
        UserRepo.r().setUid(null);
        recreate();
        isLoggedOut = true;
    }

    @Override
    public void update(Object o) {
        System.out.println("We updated: ");
    }

    // Jacobs fede redirect
    public void homeIconRedirect(View view) {
        Menu menu = bottomNav.getMenu();
        MenuItem menuItem = menu.findItem(R.id.nav_home);
        menuItem.setChecked(true);
        menu.performIdentifierAction(R.id.nav_home, 0);
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
                selectedFragment = new ProfileFragment(bottomNav);
                break;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                selectedFragment).commit();

        return true;
    };
}