package ru.ssau.pigeonmail;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Locale;

import ru.ssau.pigeonmail.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.signUpBtn.setOnClickListener(v -> {
            if(binding.emailEt.getText().toString().isEmpty()
                    ||
                    binding.passwordEt.getText().toString().isEmpty()
                    ||
                    binding.nameEt.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(),"Fields cannot be empty",Toast.LENGTH_SHORT).show();
            }else{
                String email = binding.emailEt.getText().toString();
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(binding.emailEt.getText().toString(),binding.passwordEt.getText().toString())
                        .addOnCompleteListener(task -> {
                            if(task.isSuccessful()){
                                HashMap<String,String> userInfo = new HashMap<>();
                                userInfo.put("email",binding.emailEt.getText().toString());
                                userInfo.put("userName",binding.nameEt.getText().toString());
                                userInfo.put("profileImage","");
                                userInfo.put("chats","");
                                userInfo.put("language", Locale.getDefault().getLanguage());
                                FirebaseDatabase.getInstance("https://pigeonmail-b4695-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(userInfo);
                                startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                            }else Toast.makeText(getApplicationContext(),task.getException().toString(),Toast.LENGTH_SHORT).show();
                        });

            }
        });

    }
}
