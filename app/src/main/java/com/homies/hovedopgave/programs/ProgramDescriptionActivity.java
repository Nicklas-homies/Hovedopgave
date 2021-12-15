package com.homies.hovedopgave.programs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.homies.hovedopgave.R;
import com.homies.hovedopgave.Repos.ExerciseRepo;
import com.homies.hovedopgave.Repos.ProgramRepo;
import com.homies.hovedopgave.Updatable;
import com.homies.hovedopgave.UserRepo;
import com.homies.hovedopgave.exercises.ExerciseRecyclerAdapter;
import com.homies.hovedopgave.interfaces.ExerciseUpdate;
import com.homies.hovedopgave.models.Exercise;
import com.homies.hovedopgave.models.Program;
import com.homies.hovedopgave.models.User;

import java.util.ArrayList;

//Creator: Jonathan
public class ProgramDescriptionActivity extends AppCompatActivity implements Updatable, ExerciseUpdate {

    private String programId;
    private ArrayList<Exercise> data = new ArrayList<>();
    private ArrayList<User> notUsed = new ArrayList<>();
    private Boolean isEdit;

    private TextView programDescTitle, muscleList, toolList, timeField;
    private RecyclerView recyclerView;
    private Button editButton;

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
        UserRepo.r().setupNoListener(this, notUsed);

    }

    @Override
    protected void onResume() {
        super.onResume();
        ProgramRepo.pr().getProgramById(programId, this);
    }

    public void prepareView(Program program1){
        programDescTitle = findViewById(R.id.program_desc_title);
        muscleList = findViewById(R.id.muscle_list);
        toolList = findViewById(R.id.tool_list);
        timeField = findViewById(R.id.time_field);
        editButton = findViewById(R.id.edit_button);

        Program program = program1;
        programDescTitle.setText(program.getProgramName());
        muscleList.setText(program.getMuscleGroup().toString());
        toolList.setText(program.getToolsList().toString());
        timeField.setText(Integer.toString(program.getTime()));

        editButton.setOnClickListener(v -> {
                startActivity(new Intent(getApplicationContext(), EditProgramActivity.class)
                    .putExtra("programId", program1.getId())
                );
                finish();
        });
        UserRepo.r().checkProgramOwnerById(program1.getId());
        adapter.notifyDataSetChanged();
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
        }else if (o != null && o instanceof Boolean){
            isEdit = (Boolean) o;
            if (isEdit){
                editButton.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void exerciseUpdate(Object o) {
        mProgram = new Program(mProgram.getId(), mProgram.getProgramName(), (ArrayList<Exercise>) o);
        data.clear();
        data.addAll(mProgram.getExerciseList());
        adapter.notifyDataSetChanged();
        prepareView(mProgram);
    }
}