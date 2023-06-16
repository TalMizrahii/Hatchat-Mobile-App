package com.example.hatchatmobile1;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class ContactsViewModel extends ViewModel {
    private ContactsRepository contactsRepository;
    private LiveData<List<ContactInList>> contacts;


    public ContactsViewModel() {
        contactsRepository = new ContactsRepository();
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
