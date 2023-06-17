package com.example.hatchatmobile1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;

import com.example.hatchatmobile1.DaoRelated.AppDatabase;
import com.example.hatchatmobile1.DaoRelated.Contact;
import com.example.hatchatmobile1.ViewModals.ContactViewModel;
import com.example.hatchatmobile1.databinding.ActivityAddContactBinding;

/**
 * In this activity, the user can add a new contact to the list of contact.
 */
public class AddContactActivity extends AppCompatActivity {

    // The binding object to get the components from the layout.
    private ActivityAddContactBinding binding;

    private ContactViewModel contactsViewModel;

    private AppDatabase appDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Call the onCreate that was override.
        super.onCreate(savedInstanceState);
        // Inflate the layout of this activity.
        binding = ActivityAddContactBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Retrieve the contactsViewModel from the Intent
        appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "AppDatabase")
                .allowMainThreadQueries()
                .build();
        contactsViewModel = new ContactViewModel(appDatabase);

        binding.btnSubmit.setOnClickListener(view -> {
            // Creating a new contact from the user's input.
//            Contact contact = new Contact(binding.etContent.getText().toString(), "123");
//            contactsViewModel.insertContact(contact);
            // Finish the activity back to the contacts lists.
            finish();
        });
    }
}
