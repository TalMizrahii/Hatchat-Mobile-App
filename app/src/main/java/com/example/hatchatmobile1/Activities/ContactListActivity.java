package com.example.hatchatmobile1.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hatchatmobile1.Adapters.ContactListAdapter;
import com.example.hatchatmobile1.Adapters.Utils;
import com.example.hatchatmobile1.DaoRelated.Contact;
import com.example.hatchatmobile1.DaoRelated.Message;
import com.example.hatchatmobile1.R;
import com.example.hatchatmobile1.ViewModals.ContactViewModel;
import com.example.hatchatmobile1.ViewModals.FireBaseLiveData;
import com.example.hatchatmobile1.databinding.ActivityContactListBinding;
import com.r0adkll.slidr.Slidr;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Activity for displaying the list of contacts.
 * This activity provides a user interface to view, add, and delete contacts.
 */
public class ContactListActivity extends AppCompatActivity {
    private ActivityContactListBinding binding;
    private ContactListAdapter contactAdapter;
    private ListView lvContacts;
    private ContactViewModel contactsViewModel;
    private String mainUsername;

    private String token;
    private List<Contact> contacts;

    private String displayName;
    private String profilePic;

    // Set the desired diameter for circular images
    private int desiredDiameter = 300;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Slidr.attach(this);
        // Get the current connected user from the previous activity.
        Intent intent = getIntent();
        mainUsername = intent.getStringExtra("username");
        token = intent.getStringExtra("token");
        displayName = intent.getStringExtra("displayName");
        profilePic = trimString(intent.getStringExtra("profilePic"));
        binding = ActivityContactListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Decode the image.
        byte[] decodedBytes = Base64.decode(profilePic, Base64.DEFAULT);
        Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
        // Set it to the top screen bar.
        Bitmap circularBitmap = Utils.getCircleBitmap(decodedBitmap, desiredDiameter);
        binding.userImageInList.setImageBitmap(circularBitmap);

        // Inflate the layout using view binding.

        lvContacts = binding.ContactListView;
        // Set the values about the user to the top screen bar.
        binding.usernameInList.setText(mainUsername);
        contactsViewModel = ContactViewModel.getInstance(getApplicationContext(), mainUsername, token);
        // Open AddContactActivity when the Add Contact button is clicked.
        binding.btnAddContact.setOnClickListener(view -> {
            Intent addContactIntent = new Intent(this, AddContactActivity.class);
            addContactIntent.putExtra("username", mainUsername);
            addContactIntent.putExtra("token", token);
            startActivity(addContactIntent);
        });

        // Initiating the contacts array.
        contacts = new ArrayList<>();
        // Creating the adapter and setting it to the ListView.
        contactAdapter = new ContactListAdapter(this, R.layout.contact_in_list, contacts);
        lvContacts.setAdapter(contactAdapter);

        // Observe the contact list live data and update the list view when the data changes.
        contactsViewModel.getContactListLiveData().observe(this, contactsList -> refresh(contactsList, null));

        FireBaseLiveData fireBaseLiveData = FireBaseLiveData.getInstance();
        fireBaseLiveData.getLiveData().observe(this, firebaseIncomeMessage -> {
            List<Contact> contactList = contactsViewModel.handleFirebaseChange(firebaseIncomeMessage);
            refresh(contactList, firebaseIncomeMessage.getUsername());
        });

        // Delete a contact from the list.
        lvContacts.setOnItemLongClickListener((adapterView, view, i, l) -> {
            contactsViewModel.deleteContact(contacts.get(i));
            return true;
        });

        lvContacts.setOnItemClickListener((adapterView, view, i, l) -> {
            // Open the chat screen when a contact is clicked.
            Intent chatScreenIntent = new Intent(getApplicationContext(), ChatScreenActivity.class);
            chatScreenIntent.putExtra("username", contacts.get(i).getUsername());
            chatScreenIntent.putExtra("mainUsername", mainUsername);
            chatScreenIntent.putExtra("token", token);
            chatScreenIntent.putExtra("contactId", contacts.get(i).getId());
            startActivity(chatScreenIntent);
        });


        binding.settingsButton.setOnClickListener(v -> {
            // Open the settings activity when the settings button is clicked.
            Intent settingsIntent = new Intent(getApplicationContext(), SettingActivity.class);
            settingsIntent.putExtra("logoutBtnViability", true);
            startActivity(settingsIntent);
        });
    }

    /**
     * Trimming a string for the profile picture.
     *
     * @param input The string to trim.
     * @return The trimmed string.
     */
    public String trimString(String input) {
        int startIndex = input.indexOf(',');
        if (startIndex != -1) {
            return input.substring(startIndex + 1);
        } else {
            // Return the input string as is if '/' is not found
            return input;
        }
    }

    public void refresh(List<Contact> contactList, String username) {
        if (username != null) {
            // Find the contact with the matching username
            Contact matchingContact = null;
            for (Contact contact : contactList) {
                if (contact.getUsername().equals(username)) {
                    matchingContact = contact;
                    break;
                }
            }
            if(matchingContact != null){
                // Remove the matching contact from the list.
                contactList.remove(matchingContact);
                // Insert the matching contact at position 0.
                contactList.add(0, matchingContact);
            }
        }
        contacts.clear();
        contacts.addAll(contactList);
        contactAdapter.notifyDataSetChanged();
    }
}
