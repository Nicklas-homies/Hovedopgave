package com.homies.hovedopgave.exercises;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.homies.hovedopgave.ExerciseRepo;
import com.homies.hovedopgave.R;
import com.homies.hovedopgave.models.Exercise;

import java.util.ArrayList;

public class NewExerciseActivity extends AppCompatActivity {
    EditText exerciseName;
    EditText exerciseMuscleGroup;
    EditText exerciseTools;
    EditText exerciseDescription;
    EditText exerciseTime;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_exercise);
        exerciseName = findViewById(R.id.new_exercise_name);
        exerciseMuscleGroup = findViewById(R.id.new_exercise_muscle_group);
        exerciseTools = findViewById(R.id.new_exercise_tools);
        exerciseDescription = findViewById(R.id.new_exercise_description);
        exerciseTime = findViewById(R.id.new_exercise_time);
    }

    public void addExerciseClick(View view) {
        String muscleGroup = exerciseMuscleGroup.getText().toString();
        ArrayList<String> muscleGroups = new ArrayList<String>();
        muscleGroups.add(muscleGroup);

        String tool = exerciseTools.getText().toString();
        ArrayList tools = new ArrayList<String>();
        tools.add(tool);

        Exercise exercise = new Exercise(exerciseName.getText().toString(), muscleGroups, tools,
                exerciseDescription.getText().toString(), Integer.valueOf(exerciseTime.getText().toString()));
        ExerciseRepo.r().addExercise(exercise);
        System.out.println("added: " + exercise.toString());
        System.out.println("I think");
        finish();
    }
}