package com.homies.hovedopgave.Fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.homies.hovedopgave.Repos.ExerciseRepo;
import com.homies.hovedopgave.R;
import com.homies.hovedopgave.Updatable;
import com.homies.hovedopgave.exercises.ExerciseRecyclerAdapter;
import com.homies.hovedopgave.exercises.NewExerciseActivity;
import com.homies.hovedopgave.models.Exercise;
import com.homies.hovedopgave.utils.LanguageHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/* Written by **Jacob Ravn** jaco8748 */
public class ExerciseFragment extends Fragment implements Updatable {
    ArrayList<Exercise> exercises = new ArrayList<>();
    ArrayList<Exercise> data = new ArrayList<>();
    private RecyclerView recyclerView;
    FragmentManager manager;
    ExerciseRecyclerAdapter adapter;
    List<Exercise> filterData = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercise, container, false);
        setAdapter(view);
        setChangeListenerSearch(view);
        setChangeListenerFilter(view);
        setChangeListenerMinTime(view);
        setChangeListenerMaxTime(view);

        view.findViewById(R.id.btn_new_exercise).setOnClickListener(view1 -> openNewExerciseDialog(view1));
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
        adapter = new ExerciseRecyclerAdapter(manager, data, false);
        recyclerView = view.findViewById(R.id.new_exercise_recycler_view);
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void filter(View view) {
        filterData.clear();
        filterData.addAll(exercises);
        // skaffer keywords her, da det er her vi har view.
        CharSequence searchKeyword = ((TextView) view.findViewById(R.id.edit_text_text_person_name)).getText();
        CharSequence filterKeyword = ((TextView) view.findViewById(R.id.edit_text_filter_muscle)).getText();
        String minTimeString = ((TextView) view.findViewById(R.id.min_time_filter)).getText().toString();
        String maxTimeString = ((TextView) view.findViewById(R.id.max_time_filter)).getText().toString();

        filterByName(searchKeyword);
        filterByMuscleOrTool(filterKeyword);
        filterByTime(minTimeString, maxTimeString);

        data.clear();
        data.addAll(filterData);
        adapter.notifyDataSetChanged();
    }

    private void filterByName(CharSequence searchWord) {

        this.filterData = filterData.stream().filter(exercise -> exercise.getExerciseName().toLowerCase().contains((searchWord).toString().toLowerCase()))
                .collect(Collectors.toList());
    }

    private void filterByMuscleOrTool(CharSequence searchWord) {
        this.filterData = filterData.stream().filter(exercise -> eachContainsLower((ArrayList<String>) exercise.getMuscleGroup(), searchWord.toString())
                        || eachContainsLower((ArrayList<String>) exercise.getTools(), searchWord.toString()))
                .collect(Collectors.toList());
    }

    private void filterByTime(String minTimeString, String maxTimeString) {
        int minTime;
        int maxTime;
        // handle empty fields
        if (minTimeString.equals("")) {
            minTime = -1;
        }
        else {
            minTime = Integer.valueOf(minTimeString);
        }
        if (maxTimeString.equals("")) {
            maxTime = Integer.MAX_VALUE;
        }
        else {
            maxTime = Integer.valueOf(maxTimeString);
        }
        this.filterData = filterData.stream().filter(exercise -> exercise.getTime() < maxTime && exercise.getTime() > minTime)
                .collect(Collectors.toList());
    }

    private boolean eachContainsLower(@NonNull ArrayList<String> list, String keyword) {
        for (String compare : list) {
            if (compare.toLowerCase().contains(keyword.toLowerCase())) {
                System.out.println(keyword);
                return true;
            }
        }
        return false;
    }

    public void openNewExerciseDialog(View view) {
        startActivity(new Intent(getActivity().getApplicationContext(), NewExerciseActivity.class));
    }

    private void setChangeListenerMaxTime(View view) {
        ((TextView) view.findViewById(R.id.max_time_filter)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filter(view);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void setChangeListenerMinTime(View view) {
        ((TextView) view.findViewById(R.id.min_time_filter)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filter(view);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void setChangeListenerFilter(View view) {
        ((TextView) view.findViewById(R.id.edit_text_filter_muscle)).addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filter(view);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void setChangeListenerSearch(View view) {
        ((TextView) view.findViewById(R.id.edit_text_text_person_name)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filter(view);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}