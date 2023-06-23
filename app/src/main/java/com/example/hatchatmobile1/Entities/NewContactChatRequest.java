package com.example.hatchatmobile1.Entities;

public class NewContactChatRequest {
    private String username;

    public NewContactChatRequest(String username){
        this.username = username;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
