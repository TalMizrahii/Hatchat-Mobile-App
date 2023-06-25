package com.example.hatchatmobile1.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hatchatmobile1.ViewModals.ContactViewModel;
import com.example.hatchatmobile1.databinding.ActivityAddContactBinding;
import com.r0adkll.slidr.Slidr;


/**
 * In this activity, the user can add a new contact to the list of contacts.
 */
public class AddContactActivity extends AppCompatActivity {
    // The binding object to get the components from the layout.
    private ActivityAddContactBinding binding;

    private ContactViewModel contactViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the username and token from the previous activity.
        Intent intent = getIntent();
        String mainUsername = intent.getStringExtra("username");
        String token = intent.getStringExtra("token");
        // Inflate the layout for this activity.
        binding = ActivityAddContactBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Slidr.attach(this);
        // Get the instance of the contactViewModal.
        contactViewModel = ContactViewModel.getInstance(getApplicationContext(), mainUsername, token);
        // Set the click listener for the submit button.
        binding.btnSubmit.setOnClickListener(view -> {
            contactViewModel.addContact(binding.getContent.getText().toString());
            // Finish the activity and go back to the contacts list.
            finish();
        });
    }
}