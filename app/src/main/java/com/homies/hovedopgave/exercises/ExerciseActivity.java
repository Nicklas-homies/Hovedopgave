package com.homies.hovedopgave.exercises;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.homies.hovedopgave.ExerciseRepo;
import com.homies.hovedopgave.R;
import com.homies.hovedopgave.Updatable;
import com.homies.hovedopgave.models.Exercise;
import com.homies.hovedopgave.utils.LanguageHelper;

import java.util.ArrayList;
/* Written by **Jacob Ravn** jaco8748 */
public class ExerciseActivity extends AppCompatActivity implements Updatable {
    ArrayList<Exercise> exercises = new ArrayList<>();
    ArrayList<Exercise> data = new ArrayList<>();
    private RecyclerView recyclerView;
    FragmentManager manager = getSupportFragmentManager();
    ExerciseRecyclerAdapter adapter = new ExerciseRecyclerAdapter(manager, data);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LanguageHelper.languageHelper().setup(this);
        LanguageHelper.languageHelper().loadLocale();
        setContentView(R.layout.activity_exercise);
        ExerciseRepo.r().setup(this, exercises);

        setAdapter();
    }


    private void setAdapter() {
        recyclerView = findViewById(R.id.exercise_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void update(Object o) {
        System.out.println("we updated: " + this.exercises);
        data.clear();
        data.addAll(exercises);
        System.out.println("after: " + data.toString());
        adapter.notifyDataSetChanged();
    }

    public void openNewExerciseDialog(View view) {
        startActivity(new Intent(getApplicationContext(), NewExerciseActivity.class));
    }
}