package ru.ssau.pigeonmail;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ru.ssau.pigeonmail.databinding.ActivityChatBinding;
import ru.ssau.pigeonmail.message.Message;
import ru.ssau.pigeonmail.message.MessagesAdapter;

public class ChatActivity extends AppCompatActivity {
    private ActivityChatBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        loadMessages();

        setContentView(binding.getRoot());

    }
    private void loadMessages(){
        String chatId = getIntent().getStringExtra("chatId");
        if(chatId == null) return;
        FirebaseDatabase.getInstance("https://pigeonmail-b4695-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("Chats")
                .child(chatId).child("messages")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(!snapshot.exists()) return;
                        List<Message> messages = new ArrayList<>();
                        for(DataSnapshot messageSnapshot: snapshot.getChildren()){
                            String messageId = messageSnapshot.getKey();
                            String ownerId = messageSnapshot.child("ownerId").getValue().toString();
                            String text = messageSnapshot.child("text").getValue().toString();
                            String date = messageSnapshot.child("date").getValue().toString();

                            messages.add(new Message(messageId,ownerId,text,date));
                        }
                        binding.messagesRv.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                        binding.messagesRv.setAdapter(new MessagesAdapter(messages));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}