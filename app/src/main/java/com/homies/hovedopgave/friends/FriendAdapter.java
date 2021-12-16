package com.homies.hovedopgave.friends;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.homies.hovedopgave.R;
import com.homies.hovedopgave.models.User;

import java.util.List;

//Creator: Alle
public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.ViewHolder> {


    private List<User> friendList;

    public FriendAdapter(List<User> friendList) {
        this.friendList = friendList;
    }

    @NonNull
    @Override
    public FriendAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);

        return new FriendAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendAdapter.ViewHolder holder, int position) {
        User user = friendList.get(position);

        String email = user.getEmail();
        email = email.substring(0, email.indexOf("@"));

        holder.myButton.setText(email);
        String finalEmail = email;
        holder.myButton.setOnClickListener(v -> v.getContext().startActivity(new Intent(v.getContext(), FriendHistoryActivity.class).putExtra("friendId", user.getId()).putExtra("friendName", finalEmail)));
    }

    @Override
    public int getItemCount() {
        return friendList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.item_friend;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final Button myButton;

        public ViewHolder(@NonNull View exerciseItemView){
            super(exerciseItemView);

            myButton = exerciseItemView.findViewById(R.id.friend_button);
        }
    }
}

