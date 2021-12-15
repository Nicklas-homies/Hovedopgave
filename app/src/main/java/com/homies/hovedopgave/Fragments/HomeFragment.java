package com.homies.hovedopgave.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.homies.hovedopgave.R;
import com.homies.hovedopgave.Repos.ExerciseRepo;
import com.homies.hovedopgave.Repos.ProgramRepo;
import com.homies.hovedopgave.Updatable;
import com.homies.hovedopgave.UserRepo;
import com.homies.hovedopgave.interfaces.ExerciseUpdate;
import com.homies.hovedopgave.interfaces.UserUpdate;
import com.homies.hovedopgave.models.Exercise;
import com.homies.hovedopgave.models.Program;
import com.homies.hovedopgave.models.User;
import com.homies.hovedopgave.utils.ProgramAdapter;
import com.homies.hovedopgave.utils.ProgramHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//Creator: Jonathan
public class HomeFragment extends Fragment implements Updatable, ExerciseUpdate, UserUpdate {
    List<Program> programs = new ArrayList<>();
    ArrayList<Program> data = new ArrayList<>();
    List<Program> programsExerciseStringFormat = new ArrayList();
    ArrayList<Exercise> exercises = new ArrayList<>();
    ProgramAdapter programAdapter = new ProgramAdapter(data, true);

    List<User> userList = new ArrayList<>();

    RecyclerView rvActivePrograms;
    ConstraintLayout noProgramLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        noProgramLayout = view.findViewById(R.id.no_active_layout);

        setProgramAdapter(view);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProgramRepo.pr().setupNoListener(this, programs);
        UserRepo.r().setupNoListener(this, userList);
        UserRepo.r().getActiveProgramList(this, UserRepo.r().getLogicalUid());
    }

    private void setProgramAdapter(View view){
        rvActivePrograms = view.findViewById(R.id.rv_active_programs);
        rvActivePrograms.setHasFixedSize(true);
        rvActivePrograms.setLayoutManager(new LinearLayoutManager(view.getContext()));
        rvActivePrograms.setItemAnimator(new DefaultItemAnimator());

        rvActivePrograms.setAdapter(programAdapter);
    }

    @Override
    public void update(Object o) {
        try {
            programsExerciseStringFormat = (ArrayList<Program>) o;
        }catch (ClassCastException e){
            e.printStackTrace();
        }
        ExerciseRepo.r().tempStartListener(this);
    }

    @Override
    public void exerciseUpdate(Object o) {
        exercises = (ArrayList<Exercise>) o;
        HashMap<String, Exercise> exerciseMap = ProgramHelper.convertExerciseListToMap(exercises);

        programs = ProgramHelper.generateRealProgramList(programsExerciseStringFormat, exerciseMap);
        data.clear();
        data.addAll(programs);
        programAdapter.notifyDataSetChanged();

        if (data.size() == 0){
            noProgramLayout.setVisibility(View.VISIBLE);
            rvActivePrograms.setVisibility(View.GONE);
        }else{
            noProgramLayout.setVisibility(View.GONE);
            rvActivePrograms.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void activeProgramUpdate(Object o) {
        if (UserRepo.r().getLogicalUid() != null){
            noProgramLayout.setVisibility(View.GONE);
            rvActivePrograms.setVisibility(View.VISIBLE);
            ProgramRepo.pr().getProgramsByStringList((ArrayList<String>) o);
        }else {
            noProgramLayout.setVisibility(View.VISIBLE);
            rvActivePrograms.setVisibility(View.GONE);
        }
    }
}
