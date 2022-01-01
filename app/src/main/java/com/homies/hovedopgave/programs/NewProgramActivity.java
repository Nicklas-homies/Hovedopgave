package com.homies.hovedopgave.programs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.homies.hovedopgave.R;
import com.homies.hovedopgave.Repos.ExerciseRepo;
import com.homies.hovedopgave.Repos.ProgramRepo;
import com.homies.hovedopgave.Updatable;
import com.homies.hovedopgave.exercises.ExerciseRecyclerAdapter;
import com.homies.hovedopgave.interfaces.NewProgramInterface;
import com.homies.hovedopgave.models.Exercise;
import com.homies.hovedopgave.models.Program;
import com.homies.hovedopgave.utils.NewExerciseRecyclerAdapter;

import java.util.ArrayList;

//Creator: Jonathan
public class NewProgramActivity extends AppCompatActivity implements Updatable, NewProgramInterface {
    FragmentManager manager = getSupportFragmentManager();

    ArrayList<Exercise> exercises = new ArrayList<>();
    ArrayList<Exercise> data = new ArrayList();

    ArrayList<String> newExercises = new ArrayList<>();
    ArrayList<String> newData = new ArrayList<>();

    private ExerciseRecyclerAdapter adapter = new ExerciseRecyclerAdapter(manager, data, true, this);
    private NewExerciseRecyclerAdapter newAdapter = new NewExerciseRecyclerAdapter(newData, this);;

    private RecyclerView recyclerView;
    private RecyclerView exerciseRecyclerView;
    private EditText programName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_program);
        programName = findViewById(R.id.new_program_name);

        ExerciseRepo.r().setup(this, exercises);

        setAdapter();
        setNewAdapter();
    }

    private void setAdapter() {
        recyclerView = findViewById(R.id.exercise_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(adapter);
    }

    private void setNewAdapter(){
        exerciseRecyclerView = findViewById(R.id.new_exercise_recycler_view);
        exerciseRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        exerciseRecyclerView.setItemAnimator(new DefaultItemAnimator());

        exerciseRecyclerView.setAdapter(newAdapter);
    }

    public void onSaveClicked(View v){
        String tempName = programName.getText().toString();
        if (!tempName.equals("") && newData.size() > 0) {
            ProgramRepo.pr().saveProgram(new Program(newData, tempName));
            finish();
        } else {
            programName.setError(getString(R.string.new_program_error));
            programName.requestFocus();
        }
    }

    @Override
    public void update(Object o) {
        data.clear();
        data.addAll(exercises);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void listUpdated(Object o) {
        String tempString = (String) o;
        newExercises.add(tempString);

        for (Exercise ex : data) {
            if (ex.getExerciseName().equals(tempString)){
                data.remove(ex);
                break;
            }
        }

        newData.clear();
        newData.addAll(newExercises);
        adapter.notifyDataSetChanged();
        newAdapter.notifyDataSetChanged();
    }

    @Override
    public void removeEx(Object o) {
        String tempString = (String) o;

        for (Exercise ex : exercises) {
            if (ex.getExerciseName().equals(tempString)){
                data.add(ex);
                newData.remove(tempString);
                newExercises.remove(tempString);
                break;
            }
        }

        adapter.notifyDataSetChanged();
        newAdapter.notifyDataSetChanged();
    }
}