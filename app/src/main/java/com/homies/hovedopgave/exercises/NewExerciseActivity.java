package com.homies.hovedopgave.exercises;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.homies.hovedopgave.ExerciseRepo;
import com.homies.hovedopgave.R;
import com.homies.hovedopgave.models.Exercise;
import com.homies.hovedopgave.utils.EditTextEnterClicked;

import java.util.ArrayList;

/* Written by **Jacob Ravn** jaco8748 */
public class NewExerciseActivity extends AppCompatActivity {
    EditText exerciseName;
    TextView exerciseMuscleGroup;
    TextView exerciseTools;
    EditText exerciseDescription;
    EditText exerciseTime;

    EditText addExerciseMuscleGroup;
    EditText addExerciseTool;

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

        addExerciseTool = findViewById(R.id.new_exercise_tools);
        addExerciseMuscleGroup = findViewById(R.id.new_exercise_muscle_group);

        EditTextEnterClicked.setPressEnterOnEditTextNextEditText(exerciseName, addExerciseMuscleGroup);
        EditTextEnterClicked.setPressEnterOnEditTextNextEditText(exerciseDescription, exerciseTime);

        EditTextEnterClicked.setPressEnterOnEditTextBtnClick(addExerciseTool, (Button) findViewById(R.id.btn_add_tools));
        EditTextEnterClicked.setPressEnterOnEditTextBtnClick(addExerciseMuscleGroup, (Button) findViewById(R.id.btn_add_muscle_group));

        EditTextEnterClicked.setPressEnterOnEditTextBtnClick(exerciseTime, (Button) findViewById(R.id.btn_add_exercise));
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