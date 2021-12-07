package com.homies.hovedopgave.exercises;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.homies.hovedopgave.R;
import com.homies.hovedopgave.interfaces.NewProgramInterface;
import com.homies.hovedopgave.models.Exercise;

import java.util.ArrayList;

/* Written by **Jacob Ravn** jaco8748 */
public class ExerciseRecyclerAdapter extends RecyclerView.Adapter<ExerciseRecyclerAdapter.ViewHolder> {
    ArrayList<Exercise> exercises;
    FragmentManager manager;
    boolean isAdd;
    NewProgramInterface parent;

    public ExerciseRecyclerAdapter(FragmentManager manager, ArrayList<Exercise> exercises, boolean isAdd) {
        this.exercises = exercises;
        this.manager = manager;
        this.isAdd = isAdd;
    }

    public ExerciseRecyclerAdapter(FragmentManager manager, ArrayList<Exercise> exercises, boolean isAdd, NewProgramInterface parent){
        this.exercises = exercises;
        this.manager = manager;
        this.isAdd = isAdd;
        this.parent = parent;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTxt;
        private TextView muscle;
        private Button detailsBtn;
        private Button addBtn;

        public ViewHolder(@NonNull View view) {
            super(view);
            nameTxt = (TextView) view.findViewById(R.id.list_exercise_name);
            muscle = (TextView) view.findViewById(R.id.list_exercise_muscle_group);
            detailsBtn = (Button) view.findViewById(R.id.list_exercise_btn_details);
            addBtn = (Button) view.findViewById(R.id.list_exercise_add_button);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_exercise, parent, false);
        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Exercise exercise = exercises.get(position);
        holder.nameTxt.setText(exercise.getExerciseName());
        holder.muscle.setText(exercise.getMuscleGroup().toString());

        if (!isAdd) {
            holder.addBtn.setVisibility(View.GONE);
        }

        holder.detailsBtn.setOnClickListener(v -> {
            ExerciseDialogDetails dialogDetails = new ExerciseDialogDetails(exercise);
            dialogDetails.show(manager, "Info");
        });
        holder.addBtn.setOnClickListener(v -> {
            parent.listUpdated(exercise.getExerciseName());
        });
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

}