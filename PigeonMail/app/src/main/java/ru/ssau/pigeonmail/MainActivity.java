package ru.ssau.pigeonmail;

import android.content.Intent;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.Map;

import ru.ssau.pigeonmail.bottomNav.chats.ChatFragment;
import ru.ssau.pigeonmail.bottomNav.new_chat.NewChatFragment;
import ru.ssau.pigeonmail.bottomNav.profile.ProfileFragment;
import ru.ssau.pigeonmail.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        binding.bottomView.setSelectedItemId(R.id.chats);
        setContentView(binding.getRoot());
        if(FirebaseAuth.getInstance().getCurrentUser() == null){
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
        Map<Integer, Fragment> fragmentMap = new HashMap<>();
        fragmentMap.put(R.id.chats, new ChatFragment());
        fragmentMap.put(R.id.new_chat, new NewChatFragment());
        fragmentMap.put(R.id.profile, new ProfileFragment());
        getSupportFragmentManager().beginTransaction().replace(binding.fragmentContainer.getId(),new ChatFragment()).commit();
        binding.bottomView.setOnItemSelectedListener(item -> {
            Fragment fragment = fragmentMap.get(item.getItemId());
            getSupportFragmentManager().beginTransaction().replace(binding.fragmentContainer.getId(),fragment).commit();
            return true;
        });
    }
}