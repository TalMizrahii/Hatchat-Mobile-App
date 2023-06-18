package com.example.hatchatmobile1.Repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.hatchatmobile1.DaoRelated.AppDatabase;
import com.example.hatchatmobile1.DaoRelated.Contact;
import com.example.hatchatmobile1.DaoRelated.ContactDao;

import java.util.List;

public class ContactRepository {
    private ContactDao contactDao;
    private ContactListData contactListData;
    private String mainUsername;

    public ContactRepository(Context context, String mainUsername) {
        this.mainUsername = mainUsername;
        // Create a database.
        AppDatabase appDatabase = Room.databaseBuilder(context, AppDatabase.class, "AppDatabase")
                .allowMainThreadQueries()
                .build();
        // Get the database that was built.
        contactDao = appDatabase.getContactDao();

        contactListData = new ContactListData();
    }

    /**
     * LiveData class that holds a list of contacts.
     */
    class ContactListData extends MutableLiveData<List<Contact>> {

        public ContactListData() {
            super();
            setValue(contactDao.getAllContacts());
        }

        @Override
        protected void onActive() {
            super.onActive();
            new Thread(() -> contactListData.postValue(contactDao.getAllContacts())).start();
        }
    }

    /**
     * Retrieves all contacts as LiveData.
     *
     * @return LiveData object holding a list of contacts.
     */
    public LiveData<List<Contact>> getAll() {
        return contactListData;
    }

    /**
     * Adds a new contact to the database and reloads the contact list.
     *
     * @param contact The contact to be added.
     */
    public void addContact(Contact contact) {
        //
        contactDao.insertContact(contact);
        reload();
    }

    /**
     * Deletes a contact from the database and reloads the contact list.
     *
     * @param contact The contact to be deleted.
     */
    public void deleteContact(Contact contact) {
        contactDao.deleteContact(contact);
        reload();
    }

    /**
     * Deletes a contact from the database by their username and reloads the contact list.
     *
     * @param username The username of the contact to be deleted.
     */
    public void deleteContactByUsername(String username) {
        contactDao.deleteContactByUsername(username);
        reload();
    }

    /**
     * Retrieves a contact from the database by their username.
     *
     * @param username The username of the contact to be retrieved.
     * @return The contact object if found, null otherwise.
     */
    public Contact getContactByUsername(String username) {
        return contactDao.getContactByUsername(username);
    }

    /**
     * Reloads the contact list by updating the LiveData object with the latest data from the database.
     */
    public void reload() {
        contactListData.setValue(contactDao.getAllContacts());
    }
}
