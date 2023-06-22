package com.example.hatchatmobile1.Entities;

public class PostUserRequest {
    private String username;
    private String password;
    private String displayName;
    private String profilePic;

    public PostUserRequest(String username, String password, String displayName, String profilePic) {
        this.username = username;
        this.password = password;
        this.displayName = displayName;
        this.profilePic = profilePic;
    }

}
