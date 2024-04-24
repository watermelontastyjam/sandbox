package ru.ssau.pigeonmail.utils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

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
        FirebaseDatabase.getInstance("https://pigeonmail-b4695-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("Chats").child(chatId)
                .setValue(chatInfo);
        addChatIdToUser(uid,chatId);
        addChatIdToUser(user.getUid(), chatId);
    }
    private static String generateChatId(String userId1,String userId2){
        String sumUser1User2 = userId1 + userId1;
        char[] charArray = sumUser1User2.toCharArray();
        Arrays.sort(charArray);
        return new String(charArray);
    }
    private static void addChatIdToUser(String uid,String chatId){
        FirebaseDatabase.getInstance().getReference().child("Users").child(uid)
                .child("chats").get().addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        String chats = task.getResult().getValue().toString();
                        String chatsUpd = addIdToStr(chats,chatId);
                        FirebaseDatabase.getInstance().getReference().child("Users").child(uid)
                                .child("chats").setValue(chatsUpd);
                    }
                });
    }
    private static String addIdToStr(String str,String chatId){
        if(str.isEmpty())
            str+=chatId;
        else
            str+=","+chatId;
        return str;
    }
}
