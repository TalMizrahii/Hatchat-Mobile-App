package com.example.hatchatmobile1.Entities;

import java.util.List;

public class AllMessagesResponse {
    private int id;
    private List<UsersResponse> users;
    private List<MessageForFullChat> messages;

    public AllMessagesResponse(int id, List<UsersResponse> users, List<MessageForFullChat> messages) {
        this.id = id;
        this.users = users;
        this.messages = messages;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<UsersResponse> getUsers() {
        return users;
    }

    public void setUsers(List<UsersResponse> users) {
        this.users = users;
    }

    public List<MessageForFullChat> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageForFullChat> messages) {
        this.messages = messages;
    }
}
