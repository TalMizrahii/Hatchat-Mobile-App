package com.example.hatchatmobile1.Repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.hatchatmobile1.DaoRelated.AppDatabase;
import com.example.hatchatmobile1.DaoRelated.Contact;
import com.example.hatchatmobile1.DaoRelated.ContactDao;
import com.example.hatchatmobile1.DaoRelated.Message;
import com.example.hatchatmobile1.Entities.ContactChatResponse;
import com.example.hatchatmobile1.ServerAPI.ContactsAPI;
import com.example.hatchatmobile1.ViewModals.SettingsViewModal;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ContactRepository {
    private ContactDao contactDao;
    private ContactListData contactListData;
    private String mainUsername;
    private ContactsAPI contactsAPI;
    private String token;

    public ContactRepository(Context context, String mainUsername, String token) {
        this.mainUsername = mainUsername;
        this.token = token;
        AppDatabase appDatabase = Room.databaseBuilder(context, AppDatabase.class, "AppDatabase")
                .allowMainThreadQueries()
                .build();

        contactDao = appDatabase.getContactDao();
        contactListData = new ContactListData();
        SettingsViewModal settingsViewModal = new SettingsViewModal(context);

        contactsAPI = new ContactsAPI(settingsViewModal.getSettings().getBaseUrl(), token);
    }

    /**
     * LiveData class that holds a list of contacts.
     */
    class ContactListData extends MutableLiveData<List<Contact>> {

        public ContactListData() {
            super();
            List<Contact> allContacts = contactDao.getAllContacts();
            if (allContacts != null
                    && !allContacts.isEmpty()
                    && allContacts.get(0).getMainUser().equals(mainUsername)) {
                setValue(contactDao.getAllContacts());
            } else {
                contactDao.deleteAllContacts();
                setValue(contactDao.getAllContacts());
            }
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
     * @param contactUsername The contact's username to be added.
     */
    public void addContact(String contactUsername) {
        contactsAPI.postNewContactChat(contactUsername, new ContactsAPI.OnContactChatResponseListener() {
            @Override
            public void onResponse(ContactChatResponse contactChatResponse) {
                List<Message> messages = new ArrayList<>();
                Date date = new java.util.Date();
                DateFormat dateFormat = DateFormat.getDateInstance();
                String formattedDate = dateFormat.format(date);
                messages.add(new Message("hi from contact!", formattedDate, contactChatResponse.getUser().getUsername()));

                // Handle the response here
                // Insert the new contact into the database
                contactDao.insertContact(new Contact(contactChatResponse.getUser().getUsername(),
                        contactChatResponse.getUser().getDisplayName(),
                        contactChatResponse.getUser().getProfilePic(),
                        mainUsername,
                        messages)); // todo: make the request to the server for the messages.
                reload();
            }

            @Override
            public void onError(String error) {
                // Handle the error here
            }
        });
    }


    public void reEnterContactMessageAdd(Contact contact) {
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
