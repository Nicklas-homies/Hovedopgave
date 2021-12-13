package com.homies.hovedopgave.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.homies.hovedopgave.R;
import com.homies.hovedopgave.Repos.ProgramRepo;
import com.homies.hovedopgave.Updatable;
import com.homies.hovedopgave.history.SessionActivity;
import com.homies.hovedopgave.models.Exercise;
import com.homies.hovedopgave.models.Program;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements Updatable {
    List<Program> programs = new ArrayList<Program>();
    public static Program activeProgram;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ProgramRepo.pr().setup(this, programs);
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        view.findViewById(R.id.fragment_home_placeholder_btn).setOnClickListener(v -> {
            startActivity(new Intent(getActivity().getApplicationContext(), SessionActivity.class));
        });
        return view;
    }

    @Override
    public void update(Object o) {
        programs.clear();
        programs.addAll((List<Program>) o);
        activeProgram = programs.get(1);
    }
}
