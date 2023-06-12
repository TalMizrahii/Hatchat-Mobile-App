package com.example.hatchatmobile1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.hatchatmobile1.databinding.ActivityContactListBinding;

import java.util.List;

public class ContactListActivity extends AppCompatActivity {
    List<ContactInList> contactsDataArray;
    private ActivityContactListBinding binding;
    // The contact's database.
    private AppDB contactDB;
    // The Dao interface to communicate using the queries.
    private ContactDao contactDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContactListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Set the transaction to the addContact activity by pressing the floating button.
        binding.btnAddContact.setOnClickListener(view -> {
            // Create the new intent of the add contact activity.
            Intent addContactIntent = new Intent(this, AddContactActivity.class);
            // Start it.
            startActivity(addContactIntent);
        });
        // Create a dataBase.
        contactDB = Room.databaseBuilder(getApplicationContext(), AppDB.class, "ContactsDB")
                .allowMainThreadQueries()
                .build();
        // Get the database that was built.
        contactDao = contactDB.contactDao();
        // Get the array from the DB.
        contactsDataArray = contactDao.index();
        // Get the listView from the layout.
        ListView lvContacts = binding.ContactListView;
        // Set the contacts to the adapter.
        ArrayAdapter<ContactInList> contactAdapter = new ArrayAdapter<ContactInList>(this,
                R.layout.contact_in_list,
                contactsDataArray);
        // Insert all contacts to the ListView.
        lvContacts.setAdapter(contactAdapter);
    }
}
