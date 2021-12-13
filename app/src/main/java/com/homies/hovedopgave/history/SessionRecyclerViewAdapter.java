package com.homies.hovedopgave.history;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.homies.hovedopgave.R;
import com.homies.hovedopgave.exercises.ExerciseDialogDetails;
import com.homies.hovedopgave.models.Exercise;

import java.util.ArrayList;

public class SessionRecyclerViewAdapter extends RecyclerView.Adapter<SessionRecyclerViewAdapter.ViewHolder> {
    FragmentManager manager;
    ArrayList<Exercise> exercises;

    public SessionRecyclerViewAdapter(FragmentManager manager, ArrayList<Exercise> exercises) {
        this.manager = manager;
        this.exercises = exercises;
    }

    @Override
    public void onBindViewHolder(@NonNull SessionRecyclerViewAdapter.ViewHolder holder, int position) {
        Exercise exercise = exercises.get(position);

        holder.exerciseName.setText(exercise.getExerciseName());
        holder.muscleGroup.setText(exercise.getMuscleGroup().toString());

        holder.details.setOnClickListener(v -> {
            ExerciseDialogDetails dialogDetails = new ExerciseDialogDetails(exercise);
            dialogDetails.show(manager, "Info");
        });

        holder.finishButton.setOnClickListener(view -> {
            if (holder.flipColor) {
                holder.finishButton.setBackgroundResource(R.color.set_active_background);
            }
            else {
                holder.finishButton.setBackgroundResource(R.color.not_finished_yet);
            }
            holder.flipColor = !holder.flipColor;
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView exerciseName;
        private TextView muscleGroup;
        private Button finishButton;
        private Button details;
        private boolean flipColor;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            exerciseName = itemView.findViewById(R.id.history_item_exercise_name);
            muscleGroup = itemView.findViewById(R.id.history_item_muscle_group);
            finishButton = itemView.findViewById(R.id.history_done_exercise_btn);
            details = itemView.findViewById(R.id.history_exercise_details_btn);
            flipColor = true;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_session_exercise, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }
}
