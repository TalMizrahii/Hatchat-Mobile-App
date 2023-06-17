package com.example.hatchatmobile1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.example.hatchatmobile1.DaoRelated.AppDatabase;
import com.example.hatchatmobile1.ViewModals.ContactViewModel;
import com.example.hatchatmobile1.databinding.ActivityContactListBinding;

public class ContactListActivity extends AppCompatActivity {
    private ActivityContactListBinding binding;

    private ContactListAdapter contactAdapter;

    private ListView lvContacts;

    private ContactViewModel contactsViewModel;

    private AppDatabase appDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContactListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        lvContacts = binding.ContactListView;
        contactAdapter = new ContactListAdapter(this, R.layout.contact_in_list);
        appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "AppDatabase")
                .allowMainThreadQueries()
                .build();
        contactsViewModel = new ContactViewModel(appDatabase);

        lvContacts.setAdapter(contactAdapter);
        // Create a dataBase.

        binding.btnAddContact.setOnClickListener(view -> {
            Intent addContactIntent = new Intent(this, AddContactActivity.class);
            startActivity(addContactIntent);
        });

        contactsViewModel.getContactListLiveData().observe(this, contactList -> {
            contactAdapter.setContacts(contactList);
            contactAdapter.notifyDataSetChanged();
        });
    }
}
