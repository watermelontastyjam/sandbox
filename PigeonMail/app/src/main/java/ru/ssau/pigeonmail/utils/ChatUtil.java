package ru.ssau.pigeonmail.utils;

import android.provider.ContactsContract;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;

import ru.ssau.pigeonmail.users.User;

public class ChatUtil {
    public static void createChat(User user){
        String uid = Objects.requireNonNull(FirebaseAuth.getInstance().getUid());
        HashMap<String,String> chatInfo = new HashMap<>();
        chatInfo.put("user1", Objects.requireNonNull(FirebaseAuth.getInstance().getUid()));
        chatInfo.put("user2", user.getUid());
        String chatId = generateChatId(uid, user.getUid());
        FirebaseDatabase.getInstance("https://pigeonmail-b4695-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("Users")
                .child(uid).child("chats").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot chatsSnapshot:snapshot.getChildren()){
                            if(chatsSnapshot.getValue().equals(chatId))
                                return;
                        }
                        FirebaseDatabase.getInstance("https://pigeonmail-b4695-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("Chats").child(chatId)
                                .setValue(chatInfo);
                        addChatIdToUser(uid,chatId);
                        addChatIdToUser(user.getUid(), chatId);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }
    private static String generateChatId(String userId1,String userId2){
        String sumUser1User2 = userId1 + userId2;
        char[] charArray = sumUser1User2.toCharArray();
        Arrays.sort(charArray);
        return new String(charArray);
    }
    private static void addChatIdToUser(String uid,String chatId){

        FirebaseDatabase.getInstance().getReference().child("Users").child(uid)
                .child("chats").push().setValue(chatId);
    }
    private static String addIdToStr(String str,String chatId){
        if(str.isEmpty())
            str+=chatId;
        else
            str+=","+chatId;
        return str;
    }
    public static void deleteChat(String chatId){

        FirebaseDatabase.getInstance("https://pigeonmail-b4695-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("chats").
                addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String[] chatsIds = Objects.requireNonNull(snapshot.getValue()).toString().split(",");
                        String newChatsIds = null;

                        for(String chat:chatsIds){
                            if(!chat.equals(chatId))
                               newChatsIds = ChatUtil.addIdToStr(newChatsIds,chat);
                        }

                        FirebaseDatabase.getInstance("https://pigeonmail-b4695-default-rtdb.europe-west1.firebasedatabase.app/").
                                getReference().child("Users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("chats").setValue(chatId);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        FirebaseDatabase.getInstance("https://pigeonmail-b4695-default-rtdb.europe-west1.firebasedatabase.app/").getReference()
                .child("Chats").child(chatId).removeValue();
    }
}
