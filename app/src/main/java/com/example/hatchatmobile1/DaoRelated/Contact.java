package com.example.hatchatmobile1.DaoRelated;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Contact {
    @PrimaryKey
    @NonNull
    private String username;
    private String displayName;
    private int profilePic;
    private String bio;

    private String mainUser;

    @Ignore
    public Contact(@NonNull String username, String displayName, int profilePic, String mainUser, String bio) {
        this.username = username;
        this.displayName = displayName;
        this.profilePic = profilePic;
        this.bio = bio;
        this.mainUser = mainUser;
    }

    public Contact(@NonNull String username, String displayName, int profilePic, String mainUser) {
        this.username = username;
        this.displayName = displayName;
        this.profilePic = profilePic;
        this.bio = "bio";
        this.mainUser = mainUser;
    }

    @NonNull
    public String getUsername() {
        return username;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public int getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(int profilePic) {
        this.profilePic = profilePic;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getMainUser() {
        return mainUser;
    }

    public void setMainUser(String mainUser) {
        this.mainUser = mainUser;
    }
}
