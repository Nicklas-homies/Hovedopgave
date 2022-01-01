package com.homies.hovedopgave.friends;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.homies.hovedopgave.R;
import com.homies.hovedopgave.Updatable;
import com.homies.hovedopgave.Repos.UserRepo;
import com.homies.hovedopgave.models.User;

import java.util.ArrayList;

//Creator: Alle
public class FriendActivity extends AppCompatActivity implements Updatable {

    ArrayList<User> friendList = new ArrayList<>();
    ArrayList<User> data = new ArrayList<>();

    private FriendAdapter adapter = new FriendAdapter(data);

    private RecyclerView friendRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);

        UserRepo.r().setup(this, friendList);

        setAdapter();
    }

    public void setAdapter() {
        friendRecycler = findViewById(R.id.friend_recycler);
        friendRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        friendRecycler.setAdapter(adapter);
    }

    @Override
    public void update(Object o) {
        data.clear();
        data.addAll(friendList);
        adapter.notifyDataSetChanged();
    }
}