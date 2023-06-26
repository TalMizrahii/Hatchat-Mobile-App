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
        contactsViewModel.getContactListLiveData().observe(this, this::refresh);

        FireBaseLiveData fireBaseLiveData = FireBaseLiveData.getInstance();
        fireBaseLiveData.getLiveData().observe(this, firebaseIncomeMessage -> {
            List<Contact> contactList = contactsViewModel.handleFirebaseChange(firebaseIncomeMessage);
            refresh(contactList);
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

    public void refresh(List<Contact> contactList) {
        // Sort the contactList based on the last message's timeAndDate
        Collections.sort(contactList, new Comparator<Contact>() {
            @Override
            public int compare(Contact contact1, Contact contact2) {
                // Get the last messages from each contact
                List<Message> messages1 = contact1.getMessages();
                List<Message> messages2 = contact2.getMessages();

                // Check if messages lists are empty
                if (messages1.isEmpty() && messages2.isEmpty()) {
                    return 0; // Both lists are empty, no change in order
                } else if (messages1.isEmpty()) {
                    return 1; // Only messages1 list is empty, contact2 should come first
                } else if (messages2.isEmpty()) {
                    return -1; // Only messages2 list is empty, contact1 should come first
                }

                // Get the last messages from each contact
                Message lastMessage1 = messages1.get(messages1.size() - 1);
                Message lastMessage2 = messages2.get(messages2.size() - 1);

                // Parse the timeAndDate strings into date objects for comparison
                DateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
                try {
                    Date date1 = dateFormat.parse(lastMessage1.getTimeAndDate());
                    Date date2 = dateFormat.parse(lastMessage2.getTimeAndDate());

                    // Compare the parsed date objects in reverse order
                    return date1.compareTo(date2);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                return 0; // If there is an error in parsing, assume equal dates
            }
        });

        Collections.reverse(contactList); // Reverse the sorted list to get the reverse order

        contacts.clear();
        contacts.addAll(contactList);
        contactAdapter.notifyDataSetChanged();
    }


}
