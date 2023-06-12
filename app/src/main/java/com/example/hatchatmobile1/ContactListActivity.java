package com.example.hatchatmobile1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.hatchatmobile1.databinding.ActivityContactListBinding;

import java.util.List;

public class ContactListActivity extends AppCompatActivity {
    List<ContactInList> contactsData;
    private ActivityContactListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContactListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnAddContact.setOnClickListener(view -> {
            Intent addContactIntent = new Intent(this, AddContactActivity.class);
            startActivity(addContactIntent);
        });

        ListView lvContacts = binding.ContactListView;
        ArrayAdapter<ContactInList> contactAdapter = new ArrayAdapter<ContactInList>(this,
                                                                        R.layout.contact_in_list,
                                                                        contactsData);


    }
}
