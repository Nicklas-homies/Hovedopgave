package com.homies.hovedopgave.history;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.homies.hovedopgave.R;
import com.homies.hovedopgave.Repos.ProgramRepo;
import com.homies.hovedopgave.models.History;
import com.homies.hovedopgave.programs.ProgramDescriptionActivity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/* Written by **Jacob Ravn** jaco8748 */
public class HistoryRecyclerViewAdapter extends RecyclerView.Adapter<HistoryRecyclerViewAdapter.ViewHolder> {
    ArrayList<History> histories;

    public HistoryRecyclerViewAdapter(ArrayList<History> histories) {
        this.histories = histories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_history, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        History history = histories.get(position);

        holder.programName.setText(history.getProgramName());
        holder.dateCompleted.setText(holder.itemView.getContext().getString(R.string.completed_on) + " " + history.getCompletedDate().toString());

        holder.layout.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), ProgramDescriptionActivity.class);
            intent.putExtra("programId", history.getProgramId());
            view.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return histories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView dateCompleted;
        private TextView programName;
        private ConstraintLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dateCompleted = itemView.findViewById(R.id.history_item_date);
            programName = itemView.findViewById(R.id.history_item_program_name);
            layout = itemView.findViewById(R.id.history_item_layout);

        }
    }
}
