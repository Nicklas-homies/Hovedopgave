package com.homies.hovedopgave.programs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.homies.hovedopgave.R;
import com.homies.hovedopgave.Repos.ExerciseRepo;
import com.homies.hovedopgave.Repos.ProgramRepo;
import com.homies.hovedopgave.Updatable;
import com.homies.hovedopgave.exercises.ExerciseRecyclerAdapter;
import com.homies.hovedopgave.interfaces.ExerciseUpdate;
import com.homies.hovedopgave.interfaces.NewProgramInterface;
import com.homies.hovedopgave.models.Exercise;
import com.homies.hovedopgave.models.Program;
import com.homies.hovedopgave.utils.NewExerciseRecyclerAdapter;

import java.util.ArrayList;


//Creator: Jonathan
public class EditProgramActivity extends AppCompatActivity implements Updatable, ExerciseUpdate, NewProgramInterface {
    FragmentManager manager = getSupportFragmentManager();

    private Program mProgram;

    private String programId;

    private ArrayList<Exercise> allData = new ArrayList();
    private ArrayList<String> currentData = new ArrayList<>();

    private ExerciseRecyclerAdapter allAdapter = new ExerciseRecyclerAdapter(manager, allData, true, this);
    private NewExerciseRecyclerAdapter currentAdapter = new NewExerciseRecyclerAdapter(currentData, this);

    private TextView editProgramName;
    private RecyclerView editExerciseRecycler, toAddEditExerciseRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_program);
        programId = getIntent().getStringExtra("programId");

        ProgramRepo.pr().getProgramById(programId, this);

        setAllAdapter();
        setCurrentAdapter();
    }

    private void setAllAdapter(){
        toAddEditExerciseRecycler = findViewById(R.id.to_add_edit_exercise_recycler_view);
        toAddEditExerciseRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        toAddEditExerciseRecycler.setItemAnimator(new DefaultItemAnimator());

        toAddEditExerciseRecycler.setAdapter(allAdapter);
    }

    private void setCurrentAdapter(){
        editExerciseRecycler = findViewById(R.id.edit_exercise_recycler_view);
        editExerciseRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        editExerciseRecycler.setItemAnimator(new DefaultItemAnimator());

        editExerciseRecycler.setAdapter(currentAdapter);
    }

    public void prepareView(){
        editProgramName = findViewById(R.id.edit_program_name);

        editProgramName.setText(mProgram.getProgramName());

        allData.clear();
        ArrayList<Exercise> tempList = new ArrayList<>();
        for (Exercise ex : ExerciseRepo.r().getExercises()) {
            if (!currentData.contains(ex.getExerciseName())){
                tempList.add(ex);
            }
        }
        allData.addAll(tempList);
        allAdapter.notifyDataSetChanged();
    }

    public void saveClicked(View view){
        mProgram.setProgramName(editProgramName.getText().toString());
        mProgram.setExerciseListString(currentData);

        if (!mProgram.getProgramName().equals("") && currentData.size() > 0) {
            ProgramRepo.pr().updateProgram(mProgram);
            Toast.makeText(getApplicationContext(), R.string.saved_changes, Toast.LENGTH_LONG).show();
            finish();
        } else {
            editProgramName.setError(getString(R.string.new_program_error));
            editProgramName.requestFocus();
        }
    }

    public void deleteClicked(View view){
        DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    ProgramRepo.pr().deleteProgram(mProgram);
                    Toast.makeText(getApplicationContext(), getString(R.string.deleted) + " " + mProgram.getProgramName(), Toast.LENGTH_LONG).show();
                    finish();
                    break;
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.delete)
                .setMessage(getString(R.string.are_you_sure) + " " + mProgram.getProgramName() + "?")
                .setPositiveButton(R.string.yes, dialogClickListener)
                .setNegativeButton(R.string.no, dialogClickListener)
                .show();
    }

    @Override
    public void update(Object o) {
        if (o != null && o instanceof Program){
            mProgram = (Program) o;

            currentData.clear();
            currentData.addAll(mProgram.getExerciseListString());
            currentAdapter.notifyDataSetChanged();

            ExerciseRepo.r().getExercisesById((ArrayList<String>) mProgram.getExerciseListString(), this);
        }
    }

    @Override
    public void exerciseUpdate(Object o) {
        mProgram = new Program(mProgram.getId(), mProgram.getProgramName(), (ArrayList<Exercise>) o);
        System.out.println(mProgram.toString());
        prepareView();
    }

    @Override
    public void listUpdated(Object o) {
        String tempString = (String) o;
        ArrayList<Exercise> tempList = new ArrayList<>();
        tempList.addAll(allData);

        for (Exercise ex : tempList) {
            if (ex.getExerciseName().equals(tempString)){
                tempList.remove(ex);
                currentData.add(tempString);
                break;
            }
        }

        allData.clear();
        allData.addAll(tempList);
        allAdapter.notifyDataSetChanged();
        currentAdapter.notifyDataSetChanged();
    }

    @Override
    public void removeEx(Object o) {
        String tempString = (String) o;

        Exercise tempEx = new Exercise();
        for (Exercise ex : ExerciseRepo.r().getExercises()) {
            if (ex.getExerciseName().equals(tempString)){
                tempEx = ex;
                break;
            }
        }

        for (String ex : currentData) {
            if (ex.equals(tempString)){
                allData.add(tempEx);
                currentData.remove(tempString);
                break;
            }
        }

        allAdapter.notifyDataSetChanged();
        currentAdapter.notifyDataSetChanged();
    }
}