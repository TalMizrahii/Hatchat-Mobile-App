package com.example.hatchatmobile1.Entities;

public class MessageForFullChat {
    private int id;
    private String created;
    private String content;
    private UsersResponse sender;

    public MessageForFullChat(int id, String created, String content, UsersResponse sender) {
        this.id = id;
        this.created = created;
        this.content = content;
        this.sender = sender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UsersResponse getSender() {
        return sender;
    }

    public void setSender(UsersResponse sender) {
        this.sender = sender;
    }
}
