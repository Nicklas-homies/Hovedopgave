package com.homies.hovedopgave.exercises;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.homies.hovedopgave.R;
import com.homies.hovedopgave.models.Exercise;

/* Written by **Jacob Ravn** jaco8748 */
public class ExerciseDialogDetails extends DialogFragment {
    private TextView nameTxt;
    private TextView muscle;
    private TextView tools;
    private TextView timeMin;
    private TextView description;

    private Exercise exercise;

    public ExerciseDialogDetails(Exercise exercise) {
        this.exercise = exercise;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view =  inflater.inflate(R.layout.dialog_exercise_details, null);

        builder.setView(view)
            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });

        nameTxt = (TextView) view.findViewById(R.id.dialog_exercise_details_name);
        muscle = (TextView) view.findViewById(R.id.dialog_exercise_details_muscle);
        tools = (TextView) view.findViewById(R.id.dialog_exercise_details_tools);
        timeMin = (TextView) view.findViewById(R.id.dialog_exercise_details_time);
        description = (TextView) view.findViewById(R.id.dialog_exercise_details_description);

        nameTxt.setText(exercise.getExerciseName());
        muscle.setText(getResources().getString(R.string.exercise_muscle_group)+ ": " + exercise.getMuscleGroup().toString());
        tools.setText(getResources().getString(R.string.exercise_tools) + ": " + exercise.getTools().toString());
        timeMin.setText(getResources().getString(R.string.exercise_time) + ": " + String.valueOf(exercise.getTime()));
        description.setText(getResources().getString(R.string.exercise_description) + ": " + exercise.getDescription());
        return builder.create();
    }
}
