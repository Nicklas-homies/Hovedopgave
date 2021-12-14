package com.homies.hovedopgave.Fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.homies.hovedopgave.R;
import com.homies.hovedopgave.Repos.HistoryRepo;
import com.homies.hovedopgave.Updatable;
import com.homies.hovedopgave.UserRepo;
import com.homies.hovedopgave.models.History;
import com.homies.hovedopgave.models.User;

import java.util.ArrayList;

public class HistoryFragment extends Fragment implements Updatable {
    ArrayList<History> userHistory = new ArrayList<>();
    ArrayList<History> data = new ArrayList<>();
    User currentUser;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        HistoryRepo.r().setup(this, data, (ArrayList<String>) currentUser.getHistory());
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void update(Object o) {

    }
}
