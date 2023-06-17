package com.example.hatchatmobile1.Repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.hatchatmobile1.DaoRelated.Contact;
import com.example.hatchatmobile1.DaoRelated.ContactDao;

import java.util.List;

public class ContactRepository {
    private ContactDao contactDao;
    private MutableLiveData<List<Contact>> contactListLiveData;

    public ContactRepository(ContactDao contactDao) {
        this.contactDao = contactDao;
        contactListLiveData = new MutableLiveData<>();
        loadContacts(); // Load the initial data
    }

    public LiveData<List<Contact>> getContactListLiveData() {
        return contactListLiveData;
    }

    private void loadContacts() {
        contactListLiveData.postValue(contactDao.getAllContacts());
    }

    public void insertContact(Contact contact) {
        contactDao.insertContact(contact);
        loadContacts(); // Reload the data after insertion
    }

    public void deleteContact(Contact contact) {
        contactDao.deleteContact(contact);
        loadContacts(); // Reload the data after deletion
    }
}
