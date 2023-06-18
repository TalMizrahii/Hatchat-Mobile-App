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
        // Get the current connected user from the previous activity.
        Intent intent = getIntent();
        mainUsername = intent.getStringExtra("username");

        binding = ActivityContactListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        lvContacts = binding.ContactListView;
        binding.usernameInList.setText(mainUsername);
        contactsViewModel = new ContactViewModel(getApplicationContext(), mainUsername);

        // Open AddContactActivity when the Add Contact button is clicked.
        binding.btnAddContact.setOnClickListener(view -> {
            Intent addContactIntent = new Intent(this, AddContactActivity.class);
            addContactIntent.putExtra("username", mainUsername);
            startActivity(addContactIntent);
        });

        // Initiating the contacts array.
        contacts = new ArrayList<>();
        // Creating the adapter and setting it to the ListView.
        contactAdapter = new ContactListAdapter(this, R.layout.contact_in_list, contacts);
        lvContacts.setAdapter(contactAdapter);

        // Observe the contact list live data and update the list view when the data changes.
        contactsViewModel.getContactListLiveData().observe(this, contactList -> {
            contacts.clear();
            contacts.addAll(contactList);
            contactAdapter.notifyDataSetChanged();
        });

        // Delete a contact from the list.
        lvContacts.setOnItemLongClickListener((adapterView, view, i, l) -> {
            contactsViewModel.deleteContact(contacts.get(i));
            return true;
        });
    }
}
