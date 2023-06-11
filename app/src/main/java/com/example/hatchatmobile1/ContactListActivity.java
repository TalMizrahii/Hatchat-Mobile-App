package com.example.hatchatmobile1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.hatchatmobile1.databinding.ActivityContactListBinding;

public class ContactListActivity extends AppCompatActivity {

    private ActivityContactListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContactListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnAddContact.setOnClickListener(view ->{
            Intent addContactIntent = new Intent(this, AddContactActivity.class);
            startActivity(addContactIntent);
        });
    }
}
