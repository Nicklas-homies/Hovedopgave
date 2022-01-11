package com.homies.hovedopgave.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.homies.hovedopgave.R;
import com.homies.hovedopgave.Repos.ExerciseRepo;
import com.homies.hovedopgave.Repos.ProgramRepo;
import com.homies.hovedopgave.Updatable;
import com.homies.hovedopgave.Repos.UserRepo;
import com.homies.hovedopgave.interfaces.ExerciseUpdate;
import com.homies.hovedopgave.models.Exercise;
import com.homies.hovedopgave.models.Program;
import com.homies.hovedopgave.programs.NewProgramActivity;
import com.homies.hovedopgave.utils.ProgramAdapter;
import com.homies.hovedopgave.utils.ProgramHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//Creator: Jonathan
public class ProgramsFragment extends Fragment implements Updatable, ExerciseUpdate {

    List<Program> programs = new ArrayList<>();
    ArrayList<Program> data = new ArrayList<>();
    public List<Program> programsExerciseStringFormat = new ArrayList();
    ArrayList<Exercise> exercises = new ArrayList<>();
    ProgramAdapter programAdapter = new ProgramAdapter(data, false);

    RecyclerView rvPrograms;
    Button newProgramButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_programs, container, false);

        newProgramButton = view.findViewById(R.id.new_program_button);
        newProgramButton.setOnClickListener(v -> {
            if (UserRepo.r().getLogicalUid() != null) {
                startActivity(new Intent(v.getContext(), NewProgramActivity.class));
            }else{
                Toast.makeText(view.getContext(), view.getContext().getString(R.string.logged_in_program), Toast.LENGTH_LONG).show();
            }
        });

        setProgramAdapter(view);

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ProgramRepo.pr().setup(this, programs);
    }

    @Override
    public void onResume() {
        super.onResume();
        ProgramRepo.pr().setup(this, programs);
    }

    private void setProgramAdapter(View view){
        rvPrograms = view.findViewById(R.id.rv_programs);
        rvPrograms.setHasFixedSize(true);
        rvPrograms.setLayoutManager(new LinearLayoutManager(view.getContext()));
        rvPrograms.setItemAnimator(new DefaultItemAnimator());

        rvPrograms.setAdapter(programAdapter);
    }

    @Override
    public void update(Object o) {
        programsExerciseStringFormat = (ArrayList<Program>) o;
        ExerciseRepo.r().tempStartListener(this);
    }

    @Override
    public void exerciseUpdate(Object o){
        exercises = (ArrayList<Exercise>) o;
        HashMap<String, Exercise> exerciseMap = ProgramHelper.convertExerciseListToMap(exercises);

        programs = ProgramHelper.generateRealProgramList(programsExerciseStringFormat, exerciseMap);
        data.clear();
        data.addAll(programs);
        programAdapter.notifyDataSetChanged();
    }
}