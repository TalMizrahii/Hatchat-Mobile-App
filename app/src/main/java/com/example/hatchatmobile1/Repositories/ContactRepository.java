package com.example.hatchatmobile1.Repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.hatchatmobile1.DaoRelated.AppDatabase;
import com.example.hatchatmobile1.DaoRelated.Contact;
import com.example.hatchatmobile1.DaoRelated.ContactDao;
import com.example.hatchatmobile1.DaoRelated.Message;
import com.example.hatchatmobile1.Entities.AllChatResponse;
import com.example.hatchatmobile1.Entities.ContactChatResponse;
import com.example.hatchatmobile1.Entities.MessageResponse;
import com.example.hatchatmobile1.ServerAPI.ContactsAPI;
import com.example.hatchatmobile1.ViewModals.SettingsViewModal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ContactRepository {
    private ContactDao contactDao;
    private ContactListData contactListData;
    private String mainUsername;
    private ContactsAPI contactsAPI;
    private String token;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    public ContactRepository(Context context, String mainUsername, String token) {
        this.mainUsername = mainUsername;
        this.token = token;
        AppDatabase appDatabase = Room.databaseBuilder(context, AppDatabase.class, "AppDatabase")
                .allowMainThreadQueries()
                .build();

        contactDao = appDatabase.getContactDao();
        SettingsViewModal settingsViewModal = new SettingsViewModal(context);
        contactsAPI = new ContactsAPI(settingsViewModal.getSettings().getBaseUrl(), token);
        contactListData = new ContactListData();
        contactListData.setValue(contactDao.getAllContacts()); // Initialize with existing contacts
        getAllChatsFromServer();
    }


    class ContactListData extends MutableLiveData<List<Contact>> {

        public ContactListData() {
            super();
            loadChatsFromDatabase();
        }

        private void loadChatsFromDatabase() {
            List<Contact> allContacts = contactDao.getAllContacts();
            if (allContacts != null
                    && !allContacts.isEmpty()
                    && allContacts.get(0).getMainUser().equals(mainUsername)) {
                postValue(allContacts);
                getAllChatsFromServer();
            } else {
                contactDao.deleteAllContacts();
                postValue(new ArrayList<>());
            }
        }
    }
    private void getAllChatsFromServer() {
        contactsAPI.getAllChats(new ContactsAPI.OnGetAllChatsResponseListener() {
            @Override
            public void onResponse(List<AllChatResponse> chats) {
                List<Contact> convertedChats = convertToContacts(chats);
                for (Contact contact : convertedChats) {
                    contactDao.insertContact(contact);
                    reload();
                }
            }

            @Override
            public void onError(String error) {
                // Handle the error here
            }
        });
    }
    public LiveData<List<Contact>> getAll() {
        return contactListData;
    }

    public void addContact(String contactUsername) {
        contactsAPI.postNewContactChat(contactUsername, new ContactsAPI.OnContactChatResponseListener() {
            @Override
            public void onResponse(ContactChatResponse contactChatResponse) {
                contactDao.insertContact(new Contact(contactChatResponse.getUser().getUsername(),
                        contactChatResponse.getUser().getDisplayName(),
                        contactChatResponse.getUser().getProfilePic(),
                        mainUsername,
                        "bio",
                        contactChatResponse.getId(),
                        new ArrayList<>()
                ));
                reload();
            }

            @Override
            public void onError(String error) {
                // Handle the error here
            }
        });
    }

    public List<Message> getMessagesForContact(Contact contact) {
        List<Message> messages = new ArrayList<>();
        getMessagesFromContactID(contact.getId(), new OnGetMessagesResponseListener() {
            @Override
            public void onResponse(List<Message> retrievedMessages) {
                messages.addAll(retrievedMessages);
                // Update the Contact object in the database with the retrieved messages
                contact.setMessages(messages);
                executorService.execute(() -> contactDao.insertContact(contact));
                reload();
            }
            @Override
            public void onError(String error) {
                // Handle the error here
            }
        });
        reload();
        return messages;
    }

    public void getMessagesFromContactID(int contactId, final OnGetMessagesResponseListener listener) {
        contactsAPI.getMessagesForContact(contactId, new ContactsAPI.OnGetMessagesResponseListener() {
            @Override
            public void onResponse(List<MessageResponse> messages) {
                List<Message> convertedMessages = convertToMessages(messages);
                listener.onResponse(convertedMessages);
            }

            @Override
            public void onError(String error) {
                listener.onError(error);
            }
        });

    }

    public interface OnGetMessagesResponseListener {
        void onResponse(List<Message> messages);

        void onError(String error);
    }

    private List<Message> convertToMessages(List<MessageResponse> messageResponses) {
        List<Message> convertedMessages = new ArrayList<>();
        for (MessageResponse msgResponse : messageResponses) {
            String content = msgResponse.getContent();
            String timeAndDate = msgResponse.getCreated();
            String sender = msgResponse.getSender().getUsername();

            Message message = new Message(content, timeAndDate, sender);
            convertedMessages.add(message);
        }
        return convertedMessages;
    }

    public void reEnterContactMessageAdd(Contact contact) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                contactDao.insertContact(contact);
                reload();
            }
        });
    }

    public void deleteContact(Contact contact) {
        executorService.execute(() -> contactDao.deleteContact(contact));
    }



    public Contact getContactByUsername(String username) {
        return contactDao.getContactByUsername(username);
    }

    private List<Contact> convertToContacts(List<AllChatResponse> chats) {
        List<Contact> convertedChats = new ArrayList<>();
        for (AllChatResponse chat : chats) {
            // Convert AllChatResponse to Contact if needed
            Contact contact = new Contact(
                    chat.getUser().getUsername(),
                    chat.getUser().getDisplayName(),
                    chat.getUser().getProfilePic(),
                    mainUsername,
                    "bio",
                    chat.getId(),
                    new ArrayList<>()
            );
            convertedChats.add(contact);
        }
        return convertedChats;
    }

    public void reload(){
        contactListData.setValue(contactDao.getAllContacts());
    }


    public void deleteContactByUsername(String username) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                contactDao.deleteContactByUsername(username);
                reload();
            }
        });
    }
}
