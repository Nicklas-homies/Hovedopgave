package com.homies.hovedopgave.history;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.homies.hovedopgave.Fragments.HomeFragment;
import com.homies.hovedopgave.R;
import com.homies.hovedopgave.Repos.ExerciseRepo;
import com.homies.hovedopgave.Updatable;
import com.homies.hovedopgave.models.Exercise;
import com.homies.hovedopgave.models.Program;

import java.util.ArrayList;

public class SessionActivity extends AppCompatActivity implements Updatable {
    Program program;
    ArrayList<Exercise> exercises = new ArrayList<>();
    ArrayList<Exercise> data = new ArrayList<>();
    SessionRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // omega hack løsning, den rigtige løsning ville være at gøre Program parcelable og sende via intent med .putExtra("key", program)
        program = HomeFragment.activeProgram;
        ExerciseRepo.r().setup(this);
        ExerciseRepo.r().getExercisesById((ArrayList<String>) program.getExerciseListString());
        setContentView(R.layout.activity_session);
        ((TextView) findViewById(R.id.history_program_name)).setText(program.getProgramName());

        setAdapter();
    }

    public void setAdapter() {
        adapter = new SessionRecyclerViewAdapter(getSupportFragmentManager(), data);
        RecyclerView recyclerView = findViewById(R.id.session_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void update(Object o) {
        System.out.println(o.toString());
        data.clear();
        data.addAll((ArrayList<Exercise>) o);
        adapter.notifyDataSetChanged();
    }

    public void finishClicked(View view) {
        System.out.println("hertl");
        finish();
    }
}