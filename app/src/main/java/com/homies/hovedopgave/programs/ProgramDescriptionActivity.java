package com.homies.hovedopgave.programs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.homies.hovedopgave.R;
import com.homies.hovedopgave.Repos.ProgramRepo;
import com.homies.hovedopgave.Updatable;
import com.homies.hovedopgave.models.Program;

import java.util.ArrayList;
import java.util.List;

public class ProgramDescriptionActivity extends AppCompatActivity implements Updatable {

    private String programId;
    private ArrayList<String> exerciseListString;
    private TextView programDescTitle;
    private TextView exerciseList;

    private ArrayList<Program> programList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_description);
        programId = getIntent().getStringExtra("programId");
        exerciseListString = getIntent().getStringArrayListExtra("exerciseList");

        ProgramRepo.pr().getProgramById(programId, this);

        programDescTitle = findViewById(R.id.program_desc_title);
        exerciseList = findViewById(R.id.exercise_list_desc);

        programDescTitle.setText(programId);
        exerciseList.setText(exerciseListString.toString());
    }

    @Override
    public void update(Object o) {
        if (o != null){
            programList.clear();
            programList.add((Program) o);
            System.out.println(programList.toString());
            prepareView(programList);
        }
    }

    public void prepareView(ArrayList<Program> programs){
        Program program = programs.get(0);
        programDescTitle.setText(program.getProgramName());
        exerciseList.setText(program.getExerciseListString().toString());
    }
}