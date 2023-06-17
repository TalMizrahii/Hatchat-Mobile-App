package com.example.hatchatmobile1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.example.hatchatmobile1.DaoRelated.AppDatabase;
import com.example.hatchatmobile1.DaoRelated.Contact;
import com.example.hatchatmobile1.DaoRelated.ContactDao;
import com.example.hatchatmobile1.ViewModals.ContactViewModel;
import com.example.hatchatmobile1.databinding.ActivityContactListBinding;

import java.util.List;


public class ContactListActivity extends AppCompatActivity {
    private ActivityContactListBinding binding;

    private ContactListAdapter contactAdapter;

    private ListView lvContacts;

    private ContactViewModel contactsViewModel;

    private AppDatabase appDatabase;
    private ContactDao contactDao;
    private String mainUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        mainUsername = intent.getStringExtra("username");

        binding = ActivityContactListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        lvContacts = binding.ContactListView;
        contactAdapter = new ContactListAdapter(this, R.layout.contact_in_list);

        createDatabase();
        contactsViewModel = new ContactViewModel(contactDao, mainUsername);
        binding.btnAddContact.setOnClickListener(view -> {
            Intent addContactIntent = new Intent(this, AddContactActivity.class);
            startActivity(addContactIntent);
        });

        lvContacts.setAdapter(contactAdapter);
        contactsViewModel.getContactListLiveData().observe(this, contactList -> {
            contactAdapter.setContacts(contactList);
            contactAdapter.notifyDataSetChanged();
        });

    }

    public void createDatabase(){
        // Create a dataBase.
        appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "AppDatabase")
                .allowMainThreadQueries()
                .build();
        // Get the database that was built.
        contactDao = appDatabase.getContactDao();
    }
}
