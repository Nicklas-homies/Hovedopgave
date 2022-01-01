package com.homies.hovedopgave.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.homies.hovedopgave.Login.LoginActivity;
import com.homies.hovedopgave.R;

import com.homies.hovedopgave.Repos.UserRepo;
import com.homies.hovedopgave.friends.FriendActivity;
import com.homies.hovedopgave.utils.LanguageHelper;

public class ProfileFragment extends Fragment {

    TextView userLoggedIn;
    View view;
    BottomNavigationView bottomNav;
    ConstraintLayout constrainLayoutLogoutRemove;
    Button loginButton;

    public ProfileFragment(BottomNavigationView bottomNav) {
        this.bottomNav = bottomNav;
    }

    public ProfileFragment() {
    }

    public ProfileFragment(int contentLayoutId, BottomNavigationView bottomNav) {
        super(contentLayoutId);
        this.bottomNav = bottomNav;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LanguageHelper.languageHelper().setup(getActivity());
        LanguageHelper.languageHelper().loadLocale();
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        view.findViewById(R.id.danishButton).setOnClickListener(view1 -> languageClicked(view1));
        view.findViewById(R.id.loginLogoutButton).setOnClickListener(view1 -> LoginClicked(view1));
        view.findViewById(R.id.friendsButton).setOnClickListener(view1 -> friendsClicked(view1));
        return view;
    }

    @Override
    public void onResume() {
        userLoggedIn = (TextView) view.findViewById(R.id.userLoggedIn);
        userLoggedIn.setText(UserRepo.r().getEmail(getContext()));
        super.onResume();
        constrainLayoutLogoutRemove = (ConstraintLayout) view.findViewById(R.id.constrainLayoutLogoutRemove);
        loginButton = (Button) view.findViewById(R.id.loginLogoutButton);
        if (UserRepo.r().getLogicalUid() != null) {
            constrainLayoutLogoutRemove.setVisibility(View.VISIBLE);
            loginButton.setVisibility(View.GONE);
        } else {
            loginButton.setVisibility(View.VISIBLE);
            constrainLayoutLogoutRemove.setVisibility(View.GONE);
        }
    }


    public void LoginClicked (View v) {
        startActivity(new Intent(getActivity().getApplicationContext(), LoginActivity.class));
    }

    public void friendsClicked (View v){
        if (UserRepo.r().getLogicalUid() != null) {
            startActivity(new Intent(getActivity().getApplicationContext(), FriendActivity.class));
        }else {
            Toast.makeText(getContext(), getString(R.string.friend_not_logged_in).toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void languageClicked(View v){
        final String[] listItems = {"english", "danish"};
        AlertDialog.Builder myBuilder = new AlertDialog.Builder(getActivity());
        myBuilder.setTitle("Choose language").setSingleChoiceItems(listItems, -1, (dialog, which) -> {
            if (which == 0){
                LanguageHelper.languageHelper().saveLocale("en");
                bottomNav.getMenu().findItem(R.id.nav_home).setChecked(true);
                getActivity().recreate();
            }else if (which == 1) {
                LanguageHelper.languageHelper().saveLocale("da");
                bottomNav.getMenu().findItem(R.id.nav_home).setChecked(true);
                getActivity().recreate();
            }
            dialog.dismiss();
        });
        AlertDialog myDialog = myBuilder.create();
        myDialog.show();
    }
}
