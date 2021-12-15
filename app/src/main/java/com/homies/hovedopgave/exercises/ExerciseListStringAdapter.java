package com.homies.hovedopgave.exercises;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.homies.hovedopgave.R;

import java.util.ArrayList;

/* Written by **Jacob Ravn** jaco8748 */
public class ExerciseListStringAdapter extends RecyclerView.Adapter<ExerciseListStringAdapter.ViewHolder> {
    ArrayList<String> itemsToList = new ArrayList<>();

    public ExerciseListStringAdapter(ArrayList<String> itemsToList) {
        this.itemsToList = itemsToList;
    }

    @NonNull
    @Override
    public ExerciseListStringAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_string, parent, false);
        return new ViewHolder(itemView);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView string_item;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            string_item = itemView.findViewById(R.id.string_item);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseListStringAdapter.ViewHolder holder, int position) {
        holder.string_item.setText(itemsToList.get(position));
        holder.string_item.setOnClickListener(v -> {
            itemsToList.remove(position);
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return itemsToList.size();
    }
}
