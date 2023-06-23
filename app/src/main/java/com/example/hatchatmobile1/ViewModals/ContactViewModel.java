package com.example.hatchatmobile1.ViewModals;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hatchatmobile1.DaoRelated.Contact;
import com.example.hatchatmobile1.DaoRelated.ContactDao;
import com.example.hatchatmobile1.DaoRelated.Message;
import com.example.hatchatmobile1.Repositories.ContactListData;
import com.example.hatchatmobile1.Repositories.ContactRepository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * ContactViewModel class for managing the contact-related data and operations.
 */
public class ContactViewModel extends ViewModel {
    private static ContactViewModel instance;

    private ContactRepository contactRepository;
    private ContactListData contactListData;
    private String mainUsername;

    // Private constructor to prevent direct instantiation
    private ContactViewModel(Context context, String mainUsername, String token) {
        this.mainUsername = mainUsername;
        contactRepository = new ContactRepository(context, mainUsername, token);
        contactListData = new ContactListData();
    }

    /**
     * Method to get the singleton instance of ContactViewModel.
     * @param context The context of the app.
     * @param mainUsername The current user's username.
     * @param token The user's token for authorization to the server.
     * @return An singleton instance of the class.
     */
    public static ContactViewModel getInstance(Context context, String mainUsername, String token) {
        if (instance == null) {
            synchronized (ContactViewModel.class) {
                if (instance == null) {
                    instance = new ContactViewModel(context.getApplicationContext(), mainUsername, token);
                }
            }
        }
        return instance;
    }

    /**
     * LiveData subclass for holding the list of contacts.
     */
    public class ContactListData extends MutableLiveData<List<Contact>> {
        public ContactListData() {
            super();
            loadChatsFromDatabase();
        }

        /**
         * Loads the chats from the database.
         */
        private void loadChatsFromDatabase() {
            List<Contact> allContacts = contactRepository.getIndex();
            if (allContacts != null
                    && !allContacts.isEmpty()
                    && allContacts.get(0).getMainUser().equals(mainUsername)) {
                setValue(allContacts);
            } else {
                contactRepository.deleteAllContacts();
                setValue(new ArrayList<>());
            }
        }
        @Override
        protected void onActive() {
            super.onActive();
            new Thread(() -> this.postValue(contactRepository.getIndex())).start();
        }
    }

    /**

     Reloads the contact list from the repository.
     */
    public void reload(){
        List<Contact> contacts = contactRepository.getIndex();
        contactListData.setValue(contacts);
    }
    /**

     Retrieves the LiveData object containing the list of contacts.
     @return The LiveData object containing the list of contacts.
     */
    public LiveData<List<Contact>> getAll() {
        return contactListData;
    }
    /**

     Retrieves the LiveData object containing the list of contacts.
     @return The LiveData object containing the list of contacts.
     */
    public LiveData<List<Contact>> getContactListLiveData() {
        return contactListData;
    }
    /**

     Adds a new contact.
     @param username The contact's username to be added.
     */
    public void addContact(String username) {
        contactRepository.addContact(username);
        reload();
    }
    /**

     Adds a new contact message.
     @param contact The contact to whom the message is added.
     */
    public void reEnterContactMessageAdd(Contact contact) {
        contactRepository.reEnterContactMessageAdd(contact);
        reload();
    }
    /**

     Deletes a contact.
     @param contact The contact to be deleted.
     */
    public void deleteContact(Contact contact) {
        contactRepository.deleteContact(contact);
        reload();
    }
    /**

     Retrieves a contact by username.
     @param username The username of the contact.
     @return The Contact object matching the username.
     */
    public Contact getContactByUsername(String username) {
        return contactRepository.getContactByUsername(username);
    }
    /**

     Retrieves the list of messages for a contact.
     @param contact The contact.
     @return The list of messages for the contact.
     */
    public List<Message> getMessagesForContact(Contact contact) {
        return contactRepository.getMessagesForContact(contact);
    }
}