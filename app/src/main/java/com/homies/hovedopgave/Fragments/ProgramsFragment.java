package com.homies.hovedopgave.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.homies.hovedopgave.R;
import com.homies.hovedopgave.Repos.ExerciseRepo;
import com.homies.hovedopgave.Repos.ProgramRepo;
import com.homies.hovedopgave.Updatable;
import com.homies.hovedopgave.models.Exercise;
import com.homies.hovedopgave.models.Program;
import com.homies.hovedopgave.utils.ProgramAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class ProgramsFragment extends Fragment implements Updatable {

    List<Program> programs = new ArrayList<>();
    ArrayList<Program> data = new ArrayList<>();
    public List<Program> programsExerciseStringFormat = new ArrayList();
    ArrayList<Exercise> exercises = new ArrayList<>();
    ProgramAdapter programAdapter = new ProgramAdapter(data);
    RecyclerView rvPrograms;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_programs, container, false);

        setProgramAdapter(view);

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProgramRepo.pr().setup(this, programs);
        ProgramRepo.pr().startListener();
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

    public void exerciseUpdate(Object o){
        exercises = (ArrayList<Exercise>) o;
        HashMap<String, Exercise> exerciseMap = convertExerciseListToMap(exercises);

        programs = generateRealProgramList(programsExerciseStringFormat, exerciseMap);
        data.clear();
        data.addAll(programs);
        programAdapter.notifyDataSetChanged();
    }

    public ArrayList<Program> generateRealProgramList(List<Program> programList, Map<String, Exercise> exerciseMap){
        ArrayList<Program> toReturn = new ArrayList<>();
        List<Exercise> tempExerciseList;
        for (Program p : programList) {
            tempExerciseList = new ArrayList<>();
            for (String s : p.getExerciseListString()) {
                if (exerciseMap.containsKey(s)){
                    tempExerciseList.add(exerciseMap.get(s));
                }
            }
            toReturn.add(new Program(p.getId(), p.getProgramName(), tempExerciseList));
        }
        System.out.println("toReturn: " + toReturn.toString());
        return toReturn;
    }

    public HashMap<String, Exercise> convertExerciseListToMap(ArrayList<Exercise> list){
        HashMap<String, Exercise> toReturn = new HashMap();
        for (Exercise exercise : list) {
            toReturn.put(exercise.getExerciseName(), exercise);
        }
        return toReturn;
    }
}