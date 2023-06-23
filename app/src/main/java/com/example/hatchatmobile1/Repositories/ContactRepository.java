package com.example.hatchatmobile1.Repositories;

import android.content.Context;
import android.widget.Toast;

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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Repository class for managing contact data and operations.
 */
public class ContactRepository {
    // Used to access the ContactDao interface, which provides methods for interacting with the contact table in the database.
    private ContactDao contactDao;

    // Stores the main user's username.
    private String mainUsername;

    // An instance of the ContactsAPI class, responsible for making API calls related to contacts.
    private ContactsAPI contactsAPI;

    // Stores the authentication token for making authenticated API requests.
    private String token;

    // Represents an ExecutorService with a single thread, used to perform database operations asynchronously.
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    // Holds the context of the application or activity where the ContactRepository is instantiated.
    private Context context;

    // An instance of the SettingsViewModal class, responsible for handling settings-related operations.
    private SettingsViewModal settingsViewModal;


    /**
     * Constructor for ContactRepository.
     *
     * @param context      The context.
     * @param mainUsername The main user's username.
     * @param token        The authentication token.
     */
    public ContactRepository(Context context, String mainUsername, String token) {
        this.settingsViewModal = new SettingsViewModal(context);
        this.context = context;
        this.mainUsername = mainUsername;
        this.token = token;
        AppDatabase appDatabase = Room.databaseBuilder(context, AppDatabase.class, "AppDatabase")
                .allowMainThreadQueries()
                .build();
        contactDao = appDatabase.getContactDao();
        SettingsViewModal settingsViewModal = new SettingsViewModal(context);
        contactsAPI = new ContactsAPI(settingsViewModal.getSettings().getBaseUrl(), token);
        settingsViewModal.getSettingsLiveData().observeForever(settings -> {
            if (settings != null) {
                contactsAPI.setBaseUrl(settings.getBaseUrl());
            }
        });
        getAllChatsFromServer();
    }

    /**
     * Retrieves the list of contacts from the local database.
     *
     * @return The list of contacts.
     */
    public List<Contact> getIndex() {
        return contactDao.index();
    }

    /**
     * Deletes all contacts from the local database.
     */
    public void deleteAllContacts() {
        executorService.execute(() -> {
            contactDao.deleteAllContacts();
        });
    }

    /**
     * Retrieves all chats from the server and inserts them into the local database.
     */
    private void getAllChatsFromServer() {
        contactsAPI.getAllChats(new ContactsAPI.OnGetAllChatsResponseListener() {
            @Override
            public void onResponse(List<AllChatResponse> chats) {
                List<Contact> convertedChats = convertToContacts(chats);
                for (Contact contact : convertedChats) {
                    contactDao.insertContact(contact);
                }
            }

            @Override
            public void onError(String error) {
                // Handle the error here
            }
        });
    }

    /**
     * Adds a new contact to the list.
     *
     * @param contactUsername The username of the contact to be added.
     */
    public void addContact(String contactUsername) {
        try {
            ContactChatResponse contactChatResponse = contactsAPI.postNewContactChat(contactUsername);
            Contact contact = new Contact(
                    contactChatResponse.getUser().getUsername(),
                    contactChatResponse.getUser().getDisplayName(),
                    contactChatResponse.getUser().getProfilePic(),
                    mainUsername,
                    "bio",
                    contactChatResponse.getId(),
                    new ArrayList<>()
            );
            contactDao.insertContact(contact);
        } catch (IOException e) {
            String error = e.getMessage();
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, error, duration);
            toast.show();
        }
    }

    /**
     * Retrieves the messages for a specific contact from the server.
     * <p>
     * Updates the Contact object in the database with the retrieved messages.
     *
     * @param contact The contact for which to retrieve messages.
     * @return The list of messages for the contact.
     */
    public List<Message> getMessagesForContact(Contact contact) {
        List<Message> messages = new ArrayList<>();
        getMessagesFromContactID(contact.getId(), new OnGetMessagesResponseListener() {
            @Override
            public void onResponse(List<Message> retrievedMessages) {
                messages.addAll(retrievedMessages);
                // Update the Contact object in the database with the retrieved messages.
                contact.setMessages(messages);
                executorService.execute(() -> contactDao.insertContact(contact));
            }

            @Override
            public void onError(String error) {
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, error, duration);
                toast.show();
            }
        });
        return messages;
    }

    /**
     * Retrieves the messages for a specific contact from the server.
     *
     * @param contactId The ID of the contact.
     * @param listener  The listener for handling the response.
     */
    public void getMessagesFromContactID(int contactId, final OnGetMessagesResponseListener listener) {
        contactsAPI.getMessagesForContact(contactId, new ContactsAPI.OnGetMessagesResponseListener() {
            @Override
            public void onResponse(List<MessageResponse> messages) {
                List<Message> convertedMessages = convertToMessages(messages);
                listener.onResponse(convertedMessages);
            }

            @Override
            public void onError(String error) {
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, error, duration);
                toast.show();
                listener.onError(error);
            }
        });

    }

    /**
     * Listener interface for handling message responses.
     */
    public interface OnGetMessagesResponseListener {
        void onResponse(List<Message> messages);

        void onError(String error);
    }

    /**
     * Converts a list of message responses to a list of messages.
     *
     * @param messageResponses The list of message responses.
     * @return The converted list of messages.
     */
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

    /**
     * Inserts a contact back into the database.
     *
     * @param contact The contact to be inserted.
     */
    public void reEnterContactMessageAdd(Contact contact) {
        executorService.execute(() -> {
            contactDao.insertContact(contact);
        });
    }

    /**
     * Deletes a contact from the database.
     *
     * @param contact The contact to be deleted.
     */
    public void deleteContact(Contact contact) {
        executorService.execute(() -> contactDao.deleteContact(contact));
    }

    /**
     * Retrieves a contact by username.
     *
     * @param username The username of the contact.
     * @return The contact object.
     */
    public Contact getContactByUsername(String username) {
        return contactDao.getContactByUsername(username);
    }

    /**
     * Converts a list of AllChatResponse objects to a list of Contact objects.
     *
     * @param chats The list of AllChatResponse objects.
     * @return The converted list of Contact objects.
     */
    private List<Contact> convertToContacts(List<AllChatResponse> chats) {
        List<Contact> convertedChats = new ArrayList<>();
        for (AllChatResponse chat : chats) {
            // Convert AllChatResponse to Contact if needed.
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

    /**
     * Deletes a contact from the database by username.
     *
     * @param username The username of the contact to be deleted.
     */
    public void deleteContactByUsername(String username) {
        executorService.execute(() -> {
            contactDao.deleteContactByUsername(username);
        });
    }
}