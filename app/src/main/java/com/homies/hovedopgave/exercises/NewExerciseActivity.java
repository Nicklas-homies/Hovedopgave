package com.homies.hovedopgave.exercises;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.homies.hovedopgave.ExerciseRepo;
import com.homies.hovedopgave.R;
import com.homies.hovedopgave.models.Exercise;

import java.util.ArrayList;

public class NewExerciseActivity extends AppCompatActivity {
    EditText exerciseName;
    TextView exerciseMuscleGroup;
    TextView exerciseTools;
    EditText exerciseDescription;
    EditText exerciseTime;

    ArrayList<String> muscleGroupList = new ArrayList<>();
    ArrayList<String> toolList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_exercise);
        exerciseName = findViewById(R.id.new_exercise_name);
        exerciseMuscleGroup = findViewById(R.id.added_muscle_group);
        exerciseTools = findViewById(R.id.added_tools);
        exerciseDescription = findViewById(R.id.new_exercise_description);
        exerciseTime = findViewById(R.id.new_exercise_time);
    }

    public void addExerciseClick(View view) {
        Exercise exercise = new Exercise(exerciseName.getText().toString(), muscleGroupList, toolList,
                exerciseDescription.getText().toString(), Integer.valueOf(exerciseTime.getText().toString()));
        ExerciseRepo.r().addExercise(exercise);
        System.out.println("added: " + exercise.toString());
        System.out.println("I think");
        finish();
    }

    public void addMuscleGroupClick(View view) {
        EditText whereToGet = findViewById(R.id.new_exercise_muscle_group);
        String muscleToAdd = whereToGet.getText().toString();
        whereToGet.getText().clear();
        TextView placeToAddMuscle = findViewById(R.id.added_muscle_group);
        placeToAddMuscle.append(" " + muscleToAdd);
        this.muscleGroupList.add(muscleToAdd);
    }

    public void addToolsClick(View view) {
        EditText whereToGet = findViewById(R.id.new_exercise_tools);
        String toolToAdd = whereToGet.getText().toString();
        whereToGet.getText().clear();
        TextView placeToAddMuscle = findViewById(R.id.added_tools);
        placeToAddMuscle.append(" " + toolToAdd);
        this.toolList.add(toolToAdd);
    }
}