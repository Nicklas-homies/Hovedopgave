package com.homies.hovedopgave.exercises;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.homies.hovedopgave.R;
import com.homies.hovedopgave.models.Exercise;

import java.util.ArrayList;

/* Written by **Jacob Ravn** jaco8748 */
public class ExerciseRecyclerAdapter extends RecyclerView.Adapter<ExerciseRecyclerAdapter.ViewHolder> {
    ArrayList<Exercise> exercises;
    FragmentManager context;

    public ExerciseRecyclerAdapter(FragmentManager context, ArrayList<Exercise> exercises) {
        this.exercises = exercises;
        this.context = context;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTxt;
        private TextView muscle;
        private TextView tools;
        private TextView timeMin;
        private TextView description;
        private Button detailsBtn;

        public ViewHolder(@NonNull View view) {
            super(view);
            nameTxt = (TextView) view.findViewById(R.id.list_exercise_name);
            muscle = (TextView) view.findViewById(R.id.list_exercise_muscle_group);
            detailsBtn = (Button) view.findViewById(R.id.list_exercise_btn_details);

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
        System.out.println(exercise + "Do I get here even?");
        holder.nameTxt.setText(exercise.getExerciseName());
        holder.muscle.setText(exercise.getMuscleGroup().toString());
//        holder.tools.setText(exercise.getTools().toString());
//        holder.timeMin.setText(String.valueOf(exercise.getTime()));
//        holder.description.setText(exercise.getDescription());
        holder.detailsBtn.setOnClickListener(v -> {
            ExerciseDialogDetails dialogDetails = new ExerciseDialogDetails(exercise);
            dialogDetails.show(context, "virk?");
        });
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

}
