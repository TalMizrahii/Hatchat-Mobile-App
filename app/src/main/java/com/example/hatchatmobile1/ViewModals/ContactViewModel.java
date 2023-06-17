package com.example.hatchatmobile1.ViewModals;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.hatchatmobile1.DaoRelated.Contact;
import com.example.hatchatmobile1.DaoRelated.ContactDao;
import com.example.hatchatmobile1.Repositories.ContactRepository;

import java.util.List;

public class ContactViewModel extends ViewModel {
    private ContactRepository contactRepository;
    private LiveData<List<Contact>> contactListLiveData;

    public ContactViewModel(ContactDao contactDao, String mainUsername) {
        contactRepository = new ContactRepository(contactDao, mainUsername);
        contactListLiveData = contactRepository.getContactListLiveData();
    }
    public LiveData<List<Contact>> getContactListLiveData() {
        return contactRepository.getContactListLiveData();
    }

    public Contact getContactByUsername(String username){
        return contactRepository.getContactByUsername(username);
    }

    public void deleteContactByUsername(String username){
        contactRepository.deleteContactByUsername(username);
    }

    public void insertContact(Contact contact) {
        contactRepository.insertContact(contact);
    }

    public void deleteContact(Contact contact) {
        contactRepository.deleteContact(contact);
    }
}