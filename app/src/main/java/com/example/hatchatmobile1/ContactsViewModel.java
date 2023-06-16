package com.example.hatchatmobile1;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class ContactsViewModel extends ViewModel {
    private ContactsRepository contactsRepository;
    private LiveData<List<ContactInList>> contacts;

    private ContactDao contactDao;

    public ContactsViewModel(ContactDao contactDao) {
        this.contactDao = contactDao;
        contactsRepository = new ContactsRepository(contactDao);
        contacts = contactsRepository.getAll();
    }

    public LiveData<List<ContactInList>> getContacts() {
        return contacts;
    }

//    public void addContact(ContactInList contact) {
//        contactsRepository.add(contact);
//    }
//
//    public void deleteContact(ContactInList contact) {
//        contactsRepository.delete(contact);
//    }
//
//    public void reloadContacts() {
//        contactsRepository.reload();
//    }
}
