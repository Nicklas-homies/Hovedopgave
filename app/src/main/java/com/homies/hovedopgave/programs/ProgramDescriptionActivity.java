package com.homies.hovedopgave.programs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.homies.hovedopgave.R;
import com.homies.hovedopgave.Repos.ExerciseRepo;
import com.homies.hovedopgave.Repos.ProgramRepo;
import com.homies.hovedopgave.Updatable;
import com.homies.hovedopgave.exercises.ExerciseRecyclerAdapter;
import com.homies.hovedopgave.models.Exercise;
import com.homies.hovedopgave.models.Program;

import java.util.ArrayList;

//Creator: Jonathan
public class ProgramDescriptionActivity extends AppCompatActivity implements Updatable {

    private String programId;
    private ArrayList<Exercise> data = new ArrayList<>();

    private TextView programDescTitle, muscleList, toolList, timeField;
    private RecyclerView recyclerView;

    FragmentManager manager = getSupportFragmentManager();
    ExerciseRecyclerAdapter adapter = new ExerciseRecyclerAdapter(manager, data, false);

    private Program mProgram;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_description);
        programId = getIntent().getStringExtra("programId");

        setAdapter();

        ProgramRepo.pr().getProgramById(programId, this);
    }

    public void prepareView(Program program1){
        programDescTitle = findViewById(R.id.program_desc_title);
        muscleList = findViewById(R.id.muscle_list);
        toolList = findViewById(R.id.tool_list);
        timeField = findViewById(R.id.time_field);

        Program program = program1;
        programDescTitle.setText(program.getProgramName());
        muscleList.setText(program.getMuscleGroup().toString());
        toolList.setText(program.getToolsList().toString());
        timeField.setText(Integer.toString(program.getTime()));
    }

    private void setAdapter() {
        recyclerView = findViewById(R.id.new_exercise_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void update(Object o) {
        if (o != null && o instanceof Program){
            mProgram = (Program) o;
            ExerciseRepo.r().getExercisesById(getIntent().getStringArrayListExtra("exerciseList"), this);
        }
    }

    public void exerciseUpdate(ArrayList<Exercise> exList) {
        mProgram = new Program(mProgram.getId(), mProgram.getProgramName(), exList);
        data.clear();
        data.addAll(mProgram.getExerciseList());
        adapter.notifyDataSetChanged();
        prepareView(mProgram);
    }
}