package com.example.hatchatmobile1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.hatchatmobile1.DaoRelated.Contact;
import com.example.hatchatmobile1.ViewModals.ContactViewModel;
import com.example.hatchatmobile1.databinding.ActivityContactListBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity for displaying the list of contacts.
 */
public class ContactListActivity extends AppCompatActivity {
    private ActivityContactListBinding binding;
    private ContactListAdapter contactAdapter;
    private ListView lvContacts;
    private ContactViewModel contactsViewModel;
    private String mainUsername;
    private List<Contact> contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        mainUsername = intent.getStringExtra("username");

        binding = ActivityContactListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        lvContacts = binding.ContactListView;

        contactsViewModel = new ContactViewModel(getApplicationContext(), mainUsername);

        // Open AddContactActivity when the Add Contact button is clicked
        binding.btnAddContact.setOnClickListener(view -> {
            Intent addContactIntent = new Intent(this, AddContactActivity.class);
            startActivity(addContactIntent);
        });

        contacts = new ArrayList<>();
        contactAdapter = new ContactListAdapter(this, R.layout.contact_in_list, contacts);
        lvContacts.setAdapter(contactAdapter);

        // Observe the contact list live data and update the list view when the data changes
        contactsViewModel.getContactListLiveData().observe(this, contactList -> {
            contacts.clear();
            contacts.addAll(contactList);
            contactAdapter.notifyDataSetChanged();
        });
    }
}
