package com.example.hatchatmobile1.Repositories;

import android.content.Context;
import android.icu.text.SimpleDateFormat;

import androidx.lifecycle.LifecycleOwner;
import androidx.room.Room;

import com.example.hatchatmobile1.Adapters.Utils;
import com.example.hatchatmobile1.DaoRelated.AppDatabase;
import com.example.hatchatmobile1.DaoRelated.Contact;
import com.example.hatchatmobile1.DaoRelated.ContactDao;
import com.example.hatchatmobile1.DaoRelated.Message;
import com.example.hatchatmobile1.Entities.AllChatResponse;
import com.example.hatchatmobile1.Entities.AllMessagesResponse;
import com.example.hatchatmobile1.Entities.ContactChatResponse;
import com.example.hatchatmobile1.Entities.MessageForFullChat;
import com.example.hatchatmobile1.Entities.MessageRequest;
import com.example.hatchatmobile1.Entities.MessageResponse;
import com.example.hatchatmobile1.ServerAPI.ContactsAPI;
import com.example.hatchatmobile1.ServerAPI.ServerResponse;
import com.example.hatchatmobile1.ViewModals.SettingsViewModal;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
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
        this.settingsViewModal = SettingsViewModal.getInstance(context);

        this.context = context;
        this.mainUsername = mainUsername;
        this.token = token;
        AppDatabase appDatabase = Room.databaseBuilder(context, AppDatabase.class, "AppDatabase")
                .allowMainThreadQueries()
                .build();
        contactDao = appDatabase.getContactDao();
        SettingsViewModal settingsViewModal = SettingsViewModal.getInstance(context);
        contactsAPI = new ContactsAPI(settingsViewModal.getSettings().getBaseUrl(), token);
        settingsViewModal.getSettingsLiveData().observeForever(settings -> {
            if (settings != null) {
                contactsAPI.setBaseUrl(settings.getBaseUrl());
            }
        });
        settingsViewModal.getSettingsLiveData().observe((LifecycleOwner) context, settings -> {
            contactsAPI.setBaseUrl(settings.getBaseUrl());
        });
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
    public void getAllChatsFromServer(final OnGetAllChatsResponseListener listener) {
        contactsAPI.getAllChats(new ContactsAPI.OnGetAllChatsResponseListener() {
            @Override
            public void onResponse(List<AllChatResponse> chats) {
                List<Contact> convertedChats = convertToContacts(chats);
                for (Contact contact : convertedChats) {
                    contactDao.insertContact(contact);
                }
                if (listener != null) {
                    listener.onResponse();
                }
            }

            @Override
            public void onError(String error) {
                if (listener != null) {
                    listener.onError(error);
                }
            }
        });
    }


    /**
     * Listener interface for getAllChatsFromServer method.
     */
    public interface OnGetAllChatsResponseListener {
        void onResponse();

        void onError(String error);
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
                    trimString(contactChatResponse.getUser().getProfilePic()),
                    mainUsername,
                    "",
                    contactChatResponse.getId(),
                    new ArrayList<>()
            );
            contactDao.insertContact(contact);
        } catch (IOException e) {
            String error = e.getMessage();
            Utils.showShortToast(context, error);
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
                Utils.showShortToast(context, error);
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
                Utils.showShortToast(context, error);
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
    public void reEnterContactMessageAdd(Message message, Contact contact) {
        MessageRequest messageRequest = new MessageRequest(message.getContent());
        contactDao.insertContact(contact);
        contactsAPI.AddMessage(messageRequest, contact.getId(), new ServerResponse<MessageResponse, String>() {
            @Override
            public void onServerResponse(MessageResponse response) {

            }

            @Override
            public void onServerErrorResponse(String error) {
                Utils.showShortToast(context, error);
            }
        });
    }

    public void fetchAllMessages(Contact contact, int chatId) {
        contactsAPI.GetAllMessagesById(chatId, new ServerResponse<AllMessagesResponse, String>() {
            @Override
            public void onServerResponse(AllMessagesResponse response) {
                List<Message> messages = convertAllMessages(response);
                contact.getMessages().clear();
                contact.getMessages().addAll(messages);
                if (messages.size() > 0) {
                    contact.setBio(trimBio(messages.get(messages.size() - 1).getContent()));
                } else {
                    contact.setBio("");
                }
                contactDao.insertContact(contact);
            }

            @Override
            public void onServerErrorResponse(String error) {
                Utils.showShortToast(context, error);
            }
        });
    }


    /**
     * Converts a list of MessageForFullChat objects to a list of Message objects.
     *
     * @param messageResponse The AllMessagesResponse containing the list of MessageForFullChat objects.
     * @return The converted list of Message objects.
     */
    public List<Message> convertAllMessages(AllMessagesResponse messageResponse) {
        List<Message> convertedMessages = new ArrayList<>();

        for (MessageForFullChat message : messageResponse.getMessages()) {
            String content = message.getContent();
            String timeAndDate = message.getCreated();
            String sender = message.getSender().getUsername();

            // Convert the timestamp format
            String formattedDate = convertTimestamp(timeAndDate);

            // Create a new Message object with the extracted information
            Message convertedMessage = new Message(content, formattedDate, sender);
            // Add the converted Message object to the list.
            convertedMessages.add(convertedMessage);
        }

        return convertedMessages;
    }

    /**
     * Converts the timestamp format from "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'" to "MMM dd, yyyy".
     *
     * @param timestamp The input timestamp string.
     * @return The formatted date string.
     */
    private String convertTimestamp(String timestamp) {
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("MMM dd, yyyy");

        try {
            // Parse the input timestamp
            Date date = inputDateFormat.parse(timestamp);
            // Format the date to the desired output format
            return outputDateFormat.format(date);
        } catch (ParseException e) {
            // Handle any parsing errors here
            e.printStackTrace();
        }

        return ""; // Return an empty string if the conversion fails
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
                    trimString(chat.getUser().getProfilePic()),
                    mainUsername,
                    "",
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

    /**
     * Trimming a string for the profile picture.
     *
     * @param input The string to trim.
     * @return The trimmed string.
     */
    public String trimString(String input) {
        int startIndex = input.indexOf(',');
        if (startIndex != -1) {
            return input.substring(startIndex + 1);
        } else {
            // Return the input string as is if '/' is not found
            return input;
        }
    }

    /**
     * Trims the given text message to a maximum of 11 characters followed by three dots (...),
     * if the length of the text message is greater than 11.
     *
     * @param textMessage The text message to be trimmed.
     * @return The trimmed text message.
     */
    private String trimBio(String textMessage) {
        if (textMessage.length() > 11) {
            textMessage = textMessage.substring(textMessage.length() - 11) + "...";
        }
        return textMessage;
    }
}
