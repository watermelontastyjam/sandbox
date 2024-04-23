package ru.ssau.pigeonmail.chats;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import ru.ssau.pigeonmail.R;

public class ChatAdapter extends RecyclerView.Adapter<ChatViewHolder> {
    private ArrayList<Chat> chats;

    public ChatAdapter(ArrayList<Chat> chats){
        this.chats = chats;
    }
    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.person_item_rv,parent,false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        holder.chat_name_tv.setText(chats.get(position).getChat_name());

        String userId;

        if(!chats.get(position).getUserId1().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
            userId = chats.get(position).getUserId1();
        }else userId = chats.get(position).getUserId2();

        FirebaseDatabase.getInstance("https://pigeonmail-b4695-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("Users").child(userId)
                .child("profileImage").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        try {
                            String profileImage = task.getResult().getValue().toString();

                            if(!profileImage.isEmpty())
                                Glide.with(holder.itemView.getContext()).load(profileImage).into(holder.chat_iv);
                        }catch (Exception e){
                            Toast.makeText(holder.itemView.getContext(),"Failed to get profile image link", Toast.LENGTH_SHORT);
                        }

                    }
                });
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }
}