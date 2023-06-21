package com.example.hatchatmobile1.Entities;

public class AllChatResponse {
    private int id;
    private User user;
    private LastMessage lastMessage;


    public AllChatResponse(int id, User user, LastMessage lastMessage) {
        this.id = id;
        this.user = user;
        this.lastMessage = lastMessage;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LastMessage getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(LastMessage lastMessage) {
        this.lastMessage = lastMessage;
    }
}
