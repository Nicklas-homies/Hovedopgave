package com.homies.hovedopgave.utils;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.homies.hovedopgave.R;
import com.homies.hovedopgave.UserRepo;
import com.homies.hovedopgave.history.SessionActivity;
import com.homies.hovedopgave.models.Exercise;
import com.homies.hovedopgave.models.Program;
import com.homies.hovedopgave.programs.ProgramDescriptionActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Creator: Jonathan
public class ProgramAdapter extends RecyclerView.Adapter<ProgramAdapter.ViewHolder> {

    private List<Program> programList;
    private boolean isStart = false;
    private boolean isStop = false;
    Map<String, Boolean> programMap = new HashMap<>();

    public ProgramAdapter(List<Program> programList) {
        this.programList = programList;
    }

    public ProgramAdapter(List<Program> programList, Boolean isStart) {
        this.programList = programList;
        this.isStart = isStart;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Program program = programList.get(position);

        holder.getProgramTitle().setText(program.getProgramName());
        ArrayList<String> tempExercise = new ArrayList<>();
        for (Exercise ex : program.getExerciseList()) {
            tempExercise.add(ex.getExerciseName());
        }

        holder.getTimeField().setText(program.getTime() + " " + holder.getMinutes());
        holder.getExerciseList().setText(tempExercise.toString());
        holder.getMuscleGroupList().setText(program.getMuscleGroup().toString());

        holder.getProgramLayout().setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), ProgramDescriptionActivity.class);
            intent.putExtra("programId", program.getId());
            v.getContext().startActivity(intent);
        });

        holder.getSetActiveButton().setOnClickListener(v -> {
            if (!isStart) {
                if (UserRepo.r().getLogicalUid() != null) {
                    UserRepo.r().addToActiveProgram(program.getId());
                    Toast.makeText(v.getContext(), v.getContext().getString(R.string.added) + " " + program.getProgramName() + " " + v.getContext().getString(R.string.active_toast_text), Toast.LENGTH_LONG).show();
                } else {
                    System.out.println("UID was null at OnBindViewHolder : ProgramAdapter");
                }
            }else if (!programMap.containsKey(program.getId())){
                programMap.put(program.getId(), true);
            }

            if (isStart) {
                v.getContext().startActivity(new Intent(v.getContext().getApplicationContext(), SessionActivity.class).putExtra("programId", program.getId()));
            }
        });

        if (isStart){
            holder.getSetActiveButton().setText(holder.getStart());
        }
    }

    @Override
    public int getItemCount() {
        return programList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.item_program;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView programTitle;
        private final TextView timeField;
        private final TextView exerciseList;
        private final TextView muscleGroupList;
        private final Button setActiveButton;
        private final ConstraintLayout programLayout;

        private String minutes;
        private String start;

        public ViewHolder(@NonNull View programItemView){
            super(programItemView);

            programTitle = (TextView) programItemView.findViewById(R.id.programTitle);
            timeField = (TextView) programItemView.findViewById(R.id.timeField);
            exerciseList = (TextView) programItemView.findViewById(R.id.exercise_list);
            muscleGroupList = (TextView) programItemView.findViewById(R.id.muscle_group_list);
            setActiveButton = (Button) programItemView.findViewById(R.id.setActiveButton);
            programLayout = (ConstraintLayout) programItemView.findViewById(R.id.programLayout);

            minutes = programItemView.getResources().getString(R.string.minutes);
            start = programItemView.getResources().getString(R.string.start);
        }

        public TextView getProgramTitle() {
            return programTitle;
        }

        public TextView getTimeField() {
            return timeField;
        }

        public TextView getExerciseList() {
            return exerciseList;
        }

        public TextView getMuscleGroupList() {
            return muscleGroupList;
        }

        public ConstraintLayout getProgramLayout() {
            return programLayout;
        }

        public String getMinutes() {
            return minutes;
        }

        public String getStart() {
            return start;
        }

        public Button getSetActiveButton() {
            return setActiveButton;
        }
    }
}
