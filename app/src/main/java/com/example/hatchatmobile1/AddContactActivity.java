package com.example.hatchatmobile1;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;

import com.example.hatchatmobile1.databinding.ActivityAddContactBinding;

/**
 * In this activity, the user can add a new contact to the list of contact.
 */
public class AddContactActivity extends AppCompatActivity {
    // The contact's database.
    private AppDB contactDB;
    // The Dao interface to communicate using the queries.
    private ContactDao contactDao;
    // The binding object to get the components from the layout.
    private ActivityAddContactBinding binding;

    /**
     * Overriding the onCreate.
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Call the onCreate that was override.
        super.onCreate(savedInstanceState);
        // Inflate the layout of this activity.
        binding = ActivityAddContactBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Create a dataBase.
        contactDB = Room.databaseBuilder(getApplicationContext(), AppDB.class, "ContactsDB")
                .allowMainThreadQueries()
                .build();
        // Get the database that was built.
        contactDao = contactDB.contactDao();
        // When the user presses the "Submit" button,
        // the data in the EditText will get the user from the database.
        binding.btnSubmit.setOnClickListener(view ->{
            // Creating a new contact from the user's input. todo: Change it to get it from the server/local DB.
            ContactInList contactInList = new ContactInList(0, binding.etContent.getText().toString());
            contactDao.insert(contactInList);
            // Finish the activity back to the contacts lists.
            finish();
        });
    }

}
