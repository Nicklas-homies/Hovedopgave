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
import com.homies.hovedopgave.Repos.ProgramRepo;
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
        ProgramRepo.pr().getProgramById(getIntent().getStringExtra("programId"), this);
        super.onCreate(savedInstanceState);
        // omega hack løsning, den rigtige løsning ville være at gøre Program parcelable og sende via intent med .putExtra("key", program)
        ExerciseRepo.r().setup(this);
        setContentView(R.layout.activity_session);
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
        if (o instanceof Program) {
            this.program = (Program) o;
            ExerciseRepo.r().getExercisesById((ArrayList<String>) program.getExerciseListString());
            ((TextView) findViewById(R.id.history_program_name)).setText(program.getProgramName());

            return;
        }
        data.clear();
        data.addAll((ArrayList<Exercise>) o);
        adapter.notifyDataSetChanged();
    }

    public void finishClicked(View view) {
        finish();
    }
}