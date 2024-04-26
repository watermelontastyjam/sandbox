package ru.ssau.pigeonmail.users;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import ru.ssau.pigeonmail.R;
import ru.ssau.pigeonmail.utils.ChatUtil;

public class UserAdapter extends RecyclerView.Adapter<UserViewHolder> {

    private ArrayList<User> users ;
    public UserAdapter(ArrayList<User> users){
        this.users = users;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.person_item_rv, parent,false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user =users.get(position);

        holder.username_tv.setText(users.get(position).getUsername());
        if(!users.get(position).getProfileImage().isEmpty()){
            Glide.with(holder.itemView.getContext()).load(users.get(position).getProfileImage()).into(holder.profileImage_iv);
        }
        holder.itemView.setOnClickListener(view -> {
            ChatUtil.createChat(user);
            Toast.makeText(holder.itemView.getContext(), "Successfully created chat",Toast.LENGTH_SHORT).show();
        });

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

}
