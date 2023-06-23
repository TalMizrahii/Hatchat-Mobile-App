package com.example.hatchatmobile1.Repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.hatchatmobile1.DaoRelated.Contact;
import com.example.hatchatmobile1.DaoRelated.ContactDao;

import java.util.ArrayList;
import java.util.List;

public class ContactListData extends MutableLiveData<List<Contact>> {
    private ContactDao contactDao;
    private String mainUsername;

    public ContactListData(ContactDao contactDao, String mainUsername) {
        super();
        this.mainUsername = mainUsername;
        this.contactDao = contactDao;
        loadChatsFromDatabase();
    }

    private void loadChatsFromDatabase() {
        List<Contact> allContacts = contactDao.index();
        if (allContacts != null
                && !allContacts.isEmpty()
                && allContacts.get(0).getMainUser().equals(mainUsername)) {
            setValue(allContacts);
        } else {
            contactDao.deleteAllContacts();
            setValue(new ArrayList<>());
        }
    }

    @Override
    protected void onActive() {
        super.onActive();
        new Thread(() -> this.postValue(contactDao.index())).start();
    }

    public String getMainUsername() {
        return mainUsername;
    }

    public void setMainUsername(String mainUsername) {
        this.mainUsername = mainUsername;
    }

    public ContactDao getContactDao() {
        return contactDao;
    }

    public void setContactDao(ContactDao contactDao) {
        this.contactDao = contactDao;
    }
}