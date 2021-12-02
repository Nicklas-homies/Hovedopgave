package com.homies.hovedopgave.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.homies.hovedopgave.ExerciseRepo;
import com.homies.hovedopgave.R;
import com.homies.hovedopgave.Updatable;
import com.homies.hovedopgave.exercises.ExerciseRecyclerAdapter;
import com.homies.hovedopgave.exercises.NewExerciseActivity;
import com.homies.hovedopgave.models.Exercise;
import com.homies.hovedopgave.utils.LanguageHelper;

import java.util.ArrayList;

/* Written by **Jacob Ravn** jaco8748 */
public class ExerciseFragment extends Fragment implements Updatable {
    ArrayList<Exercise> exercises = new ArrayList<>();
    ArrayList<Exercise> data = new ArrayList<>();
    private RecyclerView recyclerView;
    FragmentManager manager;
    ExerciseRecyclerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercise, container, false);
        setAdapter(view);

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LanguageHelper.languageHelper().setup(getActivity());
        LanguageHelper.languageHelper().loadLocale();
        ExerciseRepo.r().setup(this, exercises);
    }


    private void setAdapter(View view) {
        manager = getParentFragmentManager();
        adapter = new ExerciseRecyclerAdapter(manager, data);
        recyclerView = view.findViewById(R.id.exercise_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void update(Object o) {
        data.clear();
        data.addAll(exercises);
        adapter.notifyDataSetChanged();
    }

    public void openNewExerciseDialog(View view) {
        startActivity(new Intent(getActivity().getApplicationContext(), NewExerciseActivity.class));
    }
}
