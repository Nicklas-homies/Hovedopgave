package com.homies.hovedopgave.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.homies.hovedopgave.R;
import com.homies.hovedopgave.interfaces.NewProgramInterface;

import java.util.List;

//Creator: Jonathan
public class NewExerciseRecyclerAdapter extends RecyclerView.Adapter<NewExerciseRecyclerAdapter.ViewHolder> {

    private List<String> exList;
    private NewProgramInterface parent;

    public NewExerciseRecyclerAdapter(List<String> exList, NewProgramInterface parent) {
        this.exList = exList;
        this.parent = parent;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String ex = exList.get(position);

        holder.myButton.setText(ex);

        holder.myButton.setOnClickListener(v -> parent.removeEx(ex));
    }

    @Override
    public int getItemCount() {
        return exList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.item_exercise_listview;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final Button myButton;

        public ViewHolder(@NonNull View exerciseItemView){
            super(exerciseItemView);

            myButton = exerciseItemView.findViewById(R.id.ex_button);
        }
    }
}
