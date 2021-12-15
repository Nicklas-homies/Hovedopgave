package com.homies.hovedopgave.Fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.homies.hovedopgave.R;
import com.homies.hovedopgave.Repos.HistoryRepo;
import com.homies.hovedopgave.Updatable;
import com.homies.hovedopgave.UserRepo;
import com.homies.hovedopgave.history.HistoryRecyclerViewAdapter;
import com.homies.hovedopgave.models.History;
import com.homies.hovedopgave.models.User;

import java.util.ArrayList;

/* Written by **Jacob Ravn** jaco8748 */
public class HistoryFragment extends Fragment implements Updatable {
    ArrayList<History> userHistory = new ArrayList<>();
    ArrayList<History> data = new ArrayList<>();
    User user;
    HistoryRecyclerViewAdapter adapter = new HistoryRecyclerViewAdapter(userHistory);
    RecyclerView recyclerView;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        System.out.println("hertil");
        System.out.println("hertil");
        System.out.println("hertil");
        System.out.println("hertil");
        System.out.println("hertil");
        System.out.println(FirebaseAuth.getInstance().getCurrentUser());
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            UserRepo.r().getUserById(FirebaseAuth.getInstance().getCurrentUser().getUid(), this);
            view.findViewById(R.id.fragment_history_not_login).setVisibility(View.GONE);
            setAdapter(view);
        }
        return view;
    }

    public void setAdapter(View view) {
        recyclerView = view.findViewById(R.id.fragment_history_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void update(Object o) {
        if (o instanceof User) {
            this.user = (User) o;
            HistoryRepo.r().setup(this, data, (ArrayList<String>) user.getHistory());
            return;
        }
        if (o instanceof Integer) {
            if ((int) o == 1) {
                userHistory.clear();
                userHistory.addAll(data);
                adapter.notifyDataSetChanged();
            }
        }
    }
}
