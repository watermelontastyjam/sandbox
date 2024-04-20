package ru.ssau.pigeonmail.bottomNav.chats;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ru.ssau.pigeonmail.databinding.FragmentChatsBinding;


public class ChatFragment extends Fragment {
   private FragmentChatsBinding binding;

   @Nullable
   @Override
   public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      binding = FragmentChatsBinding.inflate(inflater, container, false);

      return binding.getRoot();
   }
}

