package com.example.hatchatmobile1.ViewModals;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.hatchatmobile1.DaoRelated.Contact;
import com.example.hatchatmobile1.Repositories.ContactRepository;

import java.util.List;

/**
 * ContactViewModel class for managing the contact-related data and operations.
 */
public class ContactViewModel extends ViewModel {
    private ContactRepository contactRepository;
    private LiveData<List<Contact>> contactListLiveData;

    /**
     * Constructor for ContactViewModel.
     *
     * @param context      The current context.
     * @param mainUsername The main username associated with the contacts.
     */
    public ContactViewModel(Context context, String mainUsername) {
        contactRepository = new ContactRepository(context, mainUsername);
        contactListLiveData = contactRepository.getAll();
    }

    /**
     * Retrieves the LiveData object containing the list of contacts.
     *
     * @return The LiveData object containing the list of contacts.
     */
    public LiveData<List<Contact>> getContactListLiveData() {
        return contactListLiveData;
    }

    /**
     * Adds a new contact.
     *
     * @param contact The contact to be added.
     */
    public void addContact(Contact contact) {
        contactRepository.addContact(contact);
    }

    /**
     * Deletes a contact.
     *
     * @param contact The contact to be deleted.
     */
    public void deleteContact(Contact contact) {
        contactRepository.deleteContact(contact);
    }

    /**
     * Retrieves a contact by username.
     *
     * @param username The username of the contact.
     * @return The Contact object matching the username.
     */
    public Contact getContactByUsername(String username) {
        return contactRepository.getContactByUsername(username);
    }
}
