package com.example.hatchatmobile1;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import java.util.LinkedList;
import java.util.List;

public class ContactsRepository {
    //    // The contact's database.
//    private AppDB contactDB;
//    // The Dao interface to communicate using the queries.
//    private ContactDao contactDao;
    private ContactsListData contactsListData;

    private ContactDao contactDao;

    public ContactsRepository(ContactDao contactDao) {
        this.contactDao = contactDao;
        contactsListData = new ContactsListData();
//        // Create a dataBase.
//        contactDB = Room.databaseBuilder(getApplicationContext(), AppDB.class, "ContactsDB")
//                .allowMainThreadQueries()
//                .build();
//        // Get the database that was built.
//        contactDao = contactDB.contactDao();
//        contactsViewModel = new ContactsViewModel();
    }


    class ContactsListData extends MutableLiveData<List<ContactInList>> {
        public ContactsListData() {
            super();
//            List<ContactInList> contacts = new LinkedList<ContactInList>();
//            contacts.add(new ContactInList(0, "tal", "tal", R.drawable.haticon, "123"));
            setValue(contactDao.index());
        }

        @Override
        protected void onActive() {
            super.onActive();
//            new Thread(() -> {
//                ContactInList.contactInListValue(dao.get());
//            }).start();

        }
    }

    public LiveData<List<ContactInList>> getAll() {
        return contactsListData;
    }
}
