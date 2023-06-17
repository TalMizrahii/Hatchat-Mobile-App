package com.example.hatchatmobile1.ViewModals;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.hatchatmobile1.DaoRelated.AppDatabase;
import com.example.hatchatmobile1.DaoRelated.Contact;
import com.example.hatchatmobile1.Repositories.ContactRepository;

import java.util.List;

public class ContactViewModel extends ViewModel {
    private ContactRepository contactRepository;
    private LiveData<List<Contact>> contactListLiveData;

    public ContactViewModel(AppDatabase appDatabase) {
        contactRepository = new ContactRepository(appDatabase.getContactDao());
        contactListLiveData = contactRepository.getContactListLiveData();
    }

    public LiveData<List<Contact>> getContactListLiveData() {
        return contactListLiveData;
    }

    public void insertContact(Contact contact) {
        contactRepository.insertContact(contact);
    }

    public void deleteContact(Contact contact) {
        contactRepository.deleteContact(contact);
    }
}