package com.example.hatchatmobile1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.hatchatmobile1.databinding.ActivityContactListBinding;

public class ContactListActivity extends AppCompatActivity {
    private ActivityContactListBinding binding;

    private ContactListAdapter contactAdapter;

    private ListView lvContacts;

    private ContactsViewModel contactsViewModel;

    // The contact's database.
    private ContactDB contactDB;
    // The Dao interface to communicate using the queries.
    private ContactDao contactDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContactListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        // Create a dataBase.
//        contactDB = Room.databaseBuilder(getApplicationContext(), AppDB.class, "ContactsDB")
//                .allowMainThreadQueries()
//                .build();
//        // Get the database that was built.
//        contactDao = contactDB.contactDao();
//
//        binding.btnAddContact.setOnClickListener(view -> {
//            Intent addContactIntent = new Intent(this, AddContactActivity.class);
//            startActivity(addContactIntent);
//        });
        lvContacts = binding.ContactListView;
        contactAdapter = new ContactListAdapter(this, R.layout.contact_in_list);
        lvContacts.setAdapter(contactAdapter);
        contactsViewModel = new ContactsViewModel(contactDao);
        contactsViewModel.getContacts().observe(this, contactList -> {
            contactAdapter.setContacts(contactList);
            contactAdapter.notifyDataSetChanged();
        });
    }
}
