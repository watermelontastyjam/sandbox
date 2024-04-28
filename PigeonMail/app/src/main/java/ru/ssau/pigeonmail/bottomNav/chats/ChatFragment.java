package ru.ssau.pigeonmail.bottomNav.chats;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

import ru.ssau.pigeonmail.chats.Chat;
import ru.ssau.pigeonmail.chats.ChatAdapter;
import ru.ssau.pigeonmail.databinding.FragmentChatsBinding;


public class ChatFragment extends Fragment {
   private FragmentChatsBinding binding;

   @Nullable
   @Override
   public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      binding = FragmentChatsBinding.inflate(inflater, container, false);

      loadChats();

      return binding.getRoot();
   }
   private void loadChats(){
       ArrayList<Chat> chats = new ArrayList<Chat>();
       String uid = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
       FirebaseDatabase.getInstance("https://pigeonmail-b4695-default-rtdb.europe-west1.firebasedatabase.app/").getReference()
               .addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               for(DataSnapshot chatSnapshot:snapshot.child("Users").child(uid).child("chats").getChildren()){
                   String chatId = Objects.requireNonNull(chatSnapshot.getValue()).toString();
                   String userId1 = Objects.requireNonNull(snapshot.child("Chats").child(chatId).child("user1").getValue().toString());
                   String userId2 = Objects.requireNonNull(snapshot.child("Chats").child(chatId).child("user2").getValue().toString());

                   String chatUserId = (uid.equals(userId1) ? userId2 : userId1);

                   String chatName = snapshot.child("Users").child(chatUserId).child("userName").getValue().toString();

                   Chat chat = new Chat(chatId,chatName,userId1,userId2);
                   chats.add(chat);

               }

               binding.chatsRv.setLayoutManager(new LinearLayoutManager(getContext()));
               binding.chatsRv.setAdapter(new ChatAdapter(chats));
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {
               Toast.makeText(getContext(),"Failed to get user chats", Toast.LENGTH_SHORT).show();

           }
       });


   }
}

