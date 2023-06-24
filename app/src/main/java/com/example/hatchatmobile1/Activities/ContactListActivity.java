package com.example.hatchatmobile1.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hatchatmobile1.Adapters.ContactListAdapter;
import com.example.hatchatmobile1.DaoRelated.Contact;
import com.example.hatchatmobile1.R;
import com.example.hatchatmobile1.ViewModals.ContactViewModel;
import com.example.hatchatmobile1.databinding.ActivityContactListBinding;
import com.r0adkll.slidr.Slidr;

import java.util.ArrayList;
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
        Bitmap circularBitmap = getCircleBitmap(decodedBitmap);
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

    private Bitmap getCircleBitmap(Bitmap bitmap) {
        int diameter = desiredDiameter;
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, diameter, diameter, false);
        Bitmap circularBitmap = Bitmap.createBitmap(diameter, diameter, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(circularBitmap);
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, diameter, diameter);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawCircle(diameter / 2.0f, diameter / 2.0f, diameter / 2.0f, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        int left = (diameter - scaledBitmap.getWidth()) / 2;
        int top = (diameter - scaledBitmap.getHeight()) / 2;
        canvas.drawBitmap(scaledBitmap, left, top, paint);

        return circularBitmap;
    }


}
