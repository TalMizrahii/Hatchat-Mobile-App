package com.example.hatchatmobile1.DaoRelated;

import androidx.room.TypeConverters;

import java.util.List;
@TypeConverters(Converters.class)
public class Chat {

    private List<Message> messages;

    public Chat(List<Message> messages) {
        this.messages = messages;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public void addToEnd(Message message) {
        messages.add(message);
    }
}
