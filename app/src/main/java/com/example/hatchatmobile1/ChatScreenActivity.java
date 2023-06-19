package com.example.hatchatmobile1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.example.hatchatmobile1.ViewModals.ContactViewModel;
import com.example.hatchatmobile1.databinding.ActivityAddContactBinding;
import com.example.hatchatmobile1.databinding.ActivityChatScreenBinding;

public class ChatScreenActivity extends AppCompatActivity {

    private ActivityChatScreenBinding binding;
    private ContactViewModel viewModel;
    private String contactUsername;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the intent.
        Intent intent = getIntent();
        // Get the username of the contact.
        contactUsername = intent.getStringExtra("username");
        // Get the username of the current user.
        String mainUsername = intent.getStringExtra("mainUsername");
        // Set the layout of the chat screen.
        binding = ActivityChatScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Create a view modal.
        viewModel = new ContactViewModel(getApplicationContext(), mainUsername);
        ListView messages = binding.ChatMessagesLV;


    }
}