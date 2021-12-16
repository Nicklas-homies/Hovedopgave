package com.homies.hovedopgave.friends;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.homies.hovedopgave.R;
import com.homies.hovedopgave.Repos.ExerciseRepo;
import com.homies.hovedopgave.Repos.HistoryRepo;
import com.homies.hovedopgave.Repos.ProgramRepo;
import com.homies.hovedopgave.Updatable;
import com.homies.hovedopgave.UserRepo;
import com.homies.hovedopgave.history.HistoryRecyclerViewAdapter;
import com.homies.hovedopgave.interfaces.ExerciseUpdate;
import com.homies.hovedopgave.interfaces.UserUpdate;
import com.homies.hovedopgave.models.Exercise;
import com.homies.hovedopgave.models.History;
import com.homies.hovedopgave.models.Program;
import com.homies.hovedopgave.models.User;
import com.homies.hovedopgave.utils.ProgramAdapter;
import com.homies.hovedopgave.utils.ProgramHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//Creator: Alle
public class FriendHistoryActivity extends AppCompatActivity implements UserUpdate, Updatable, ExerciseUpdate {
    private String friendId, friendName;
    private Boolean isHistory = false;

    List<Program> programs = new ArrayList<>();
    ArrayList<Program> data = new ArrayList<>();
    List<Program> programsExerciseStringFormat = new ArrayList();
    ArrayList<Exercise> exercises = new ArrayList<>();

    ArrayList<History> userHistory = new ArrayList<>();
    ArrayList<History> historyData = new ArrayList<>();
    User user;
    HistoryRecyclerViewAdapter historyAdapter = new HistoryRecyclerViewAdapter(userHistory);

    private TextView friendNameText, statusText;
    private RecyclerView rvActivePrograms, rvHistory;
    private Button changeRecycler;

    ProgramAdapter programAdapter = new ProgramAdapter(data, true);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_history);
        friendId = getIntent().getStringExtra("friendId");
        friendName = getIntent().getStringExtra("friendName");

        friendNameText = findViewById(R.id.friend_name_text);
        statusText = findViewById(R.id.status_text);
        changeRecycler = findViewById(R.id.change_recycler_button);

        friendNameText.setText(friendName);

        setProgramAdapter();
        setHistoryAdapter();

        ProgramRepo.pr().setupNoListener(this, programs);
        UserRepo.r().getActiveProgramList(this, friendId);
        UserRepo.r().getUserById(friendId, this);
    }

    private void setProgramAdapter(){
        rvActivePrograms = findViewById(R.id.active_programs_recycler);
        rvActivePrograms.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        rvActivePrograms.setAdapter(programAdapter);
    }

    private void setHistoryAdapter(){
        rvHistory = findViewById(R.id.history_recycler);
        rvHistory.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        rvHistory.setAdapter(historyAdapter);
    }

    public void onChangeClicked(View view) {
        statusText.setVisibility(View.GONE);
        if (isHistory){
            if (data.size() == 0) {
                statusText.setText(getString(R.string.friend_no_active_programs));
                statusText.setVisibility(View.VISIBLE);
            }
            rvActivePrograms.setVisibility(View.VISIBLE);
            rvHistory.setVisibility(View.GONE);
            changeRecycler.setText(getString(R.string.history));
        }else{
            changeRecycler.setText(getString(R.string.active_programs));
            if (historyData.size() != 0) {
                rvHistory.setVisibility(View.VISIBLE);
                rvActivePrograms.setVisibility(View.GONE);
            }else{
                rvHistory.setVisibility(View.GONE);
                rvActivePrograms.setVisibility(View.GONE);
                statusText.setText(getString(R.string.friend_no_history));
                statusText.setVisibility(View.VISIBLE);
            }
        }
        isHistory = !isHistory;
    }

    @Override
    public void activeProgramUpdate(Object o) {
        ProgramRepo.pr().getProgramsByStringList((ArrayList<String>) o);
    }

    @Override
    public void update(Object o) {
        if (o instanceof ArrayList){
            programsExerciseStringFormat = (ArrayList<Program>) o;
            ExerciseRepo.r().tempStartListener(this);
        }else if (o instanceof User) {
            this.user = (User) o;
            if (this.user.getHistory() != null) {
                HistoryRepo.r().setup(this, historyData, (ArrayList<String>) user.getHistory());
            }
        }else if (o instanceof Integer) {
            if ((int) o == 1) {
                userHistory.clear();
                userHistory.addAll(historyData);
                historyAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void exerciseUpdate(Object o) {
        exercises = (ArrayList<Exercise>) o;
        HashMap<String, Exercise> exerciseMap = ProgramHelper.convertExerciseListToMap(exercises);

        programs = ProgramHelper.generateRealProgramList(programsExerciseStringFormat, exerciseMap);
        data.clear();
        data.addAll(programs);
        programAdapter.notifyDataSetChanged();

        if (data.size() == 0){
            rvActivePrograms.setVisibility(View.GONE);
            statusText.setVisibility(View.VISIBLE);
        }
    }
}