package com.example.hatchatmobile1.Entities;

public class FirebaseIncomeMessage {
    private int chaId;
    private String username;
    private String created;
    private String content;

    public FirebaseIncomeMessage(int chaId, String username, String created, String content) {
        this.chaId = chaId;
        this.username = username;
        this.created = created;
        this.content = content;
    }

    public int getChaId() {
        return chaId;
    }

    public void setChaId(int chaId) {
        this.chaId = chaId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
}
