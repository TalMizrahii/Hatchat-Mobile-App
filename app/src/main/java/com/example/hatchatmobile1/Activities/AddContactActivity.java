package com.example.hatchatmobile1.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;

import com.example.hatchatmobile1.DaoRelated.AppDatabase;
import com.example.hatchatmobile1.DaoRelated.Contact;
import com.example.hatchatmobile1.DaoRelated.ContactDao;
import com.example.hatchatmobile1.DaoRelated.Message;
import com.example.hatchatmobile1.R;
import com.example.hatchatmobile1.ViewModals.ContactViewModel;
import com.example.hatchatmobile1.databinding.ActivityAddContactBinding;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * In this activity, the user can add a new contact to the list of contacts.
 */
public class AddContactActivity extends AppCompatActivity {
    // The binding object to get the components from the layout.
    private ActivityAddContactBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String mainUsername = intent.getStringExtra("username");
        String token = intent.getStringExtra("token");

        // Inflate the layout for this activity.
        binding = ActivityAddContactBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ContactViewModel viewModel = new ContactViewModel(getApplicationContext(), mainUsername, token);


        binding.btnSubmit.setOnClickListener(view -> {
            viewModel.addContact(binding.etContent.getText().toString());
            // Finish the activity and go back to the contacts list.
            finish();
        });
    }
}
