package com.example.hatchatmobile1.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import com.r0adkll.slidr.Slidr;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Base64;
import android.view.KeyEvent;

import android.widget.Button;
import android.widget.EditText;

import com.example.hatchatmobile1.DaoRelated.Contact;
import com.example.hatchatmobile1.DaoRelated.Message;
import com.example.hatchatmobile1.Adapters.MessageAdapter;
import com.example.hatchatmobile1.ViewModals.ContactViewModel;
import com.example.hatchatmobile1.databinding.ActivityChatScreenBinding;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

/**
 * This activity represents the chat screen where users can exchange messages with a contact.
 */
public class ChatScreenActivity extends AppCompatActivity {

    private ActivityChatScreenBinding binding;
    private ContactViewModel viewModel;
    private String contactUsername;

    private String mainUsername;
    private Contact contact;

    private List<Message> messages;
    private MessageAdapter messageAdapter;

    private String token;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChatScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Slidr.attach(this);
        // Retrieve the contact username and the main user username from the intent.
        Intent intent = getIntent();
        contactUsername = intent.getStringExtra("username");
        mainUsername = intent.getStringExtra("mainUsername");
        token = intent.getStringExtra("token");
        int contactId = intent.getIntExtra("contactId", -1);

        // Create an instance of the ContactViewModel using the application context and the main user username.
        viewModel = ContactViewModel.getInstance(getApplicationContext(), mainUsername, token);
        contact = viewModel.getContactByUsername(contactUsername);
        messages = viewModel.getMessagesForContact(contact);

        // Set up the RecyclerView for displaying the messages.
        RecyclerView recyclerView = binding.ChatMessagesRV;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        messageAdapter = new MessageAdapter(messages, contactUsername);
        recyclerView.setAdapter(messageAdapter);

        EditText messageInputBar = binding.messageInputBar;
        Button sendButton = binding.sendBtn;

        // Set the contact's username as the title of the chat screen.
        binding.ContactInChatName.setText(contactUsername);

        // Convert the base64 string to a bitmap
        byte[] decodedBytes = Base64.decode(contact.getProfilePic(), Base64.DEFAULT);
        Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);

        // Set the contact's profile picture.
        binding.ContactImageInChat.setImageBitmap(decodedBitmap);

        // Send a message when the user presses the Enter key.
        messageInputBar.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                String messageText = messageInputBar.getText().toString().trim();
                if (!messageText.isEmpty()) {
                    sendMessage(messageText);
                    messageInputBar.setText("");
                }
                return true;
            }
            return false;
        });

        // Send a message when the user clicks the send button.
        sendButton.setOnClickListener(v -> {
            String messageText = messageInputBar.getText().toString().trim();
            if (!messageText.isEmpty()) {
                // Send the message.
                sendMessage(messageText);
                // Clear the input bar after sending.
                messageInputBar.setText("");
            }
        });

        // Observe changes in the contact list and update the messages if the current contact has new messages.
        viewModel.getContactListLiveData().observe(this, updatedContacts -> {
            for (Contact updatedContact : updatedContacts) {
                if (updatedContact.getUsername().equals(contactUsername)) {
                    messages.clear();
                    messages.addAll(updatedContact.getMessages());
                    messageAdapter.notifyDataSetChanged();
                    break;
                }
            }
        });

        binding.settingsButton.setOnClickListener(v -> {
            // Settings button click logic
            Intent settingsIntent = new Intent(getApplicationContext(), SettingActivity.class);
            settingsIntent.putExtra("logoutBtnViability", true);
            startActivity(settingsIntent);
        });
    }

    /**
     * Send a message to the contact.
     *
     * @param textMessage The text of the message to be sent.
     */
    private void sendMessage(String textMessage) {
        Date date = new java.util.Date();
        DateFormat dateFormat = DateFormat.getDateInstance();
        String formattedDate = dateFormat.format(date);
        contact.getMessages().add(new Message(textMessage, formattedDate, mainUsername));
        viewModel.reEnterContactMessageAdd(contact);
    }
}
