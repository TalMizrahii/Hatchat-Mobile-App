package com.example.hatchatmobile1.Entities;

public class MessageResponse {
    private int id;

    private String created;
    private String content;
    private Sender sender;

    public MessageResponse(int id, String created, String content, Sender sender) {
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

    public Sender getSender() {
        return sender;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }
}
