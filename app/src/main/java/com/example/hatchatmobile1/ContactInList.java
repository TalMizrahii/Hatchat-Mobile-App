package com.example.hatchatmobile1;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "contacts", primaryKeys = {"id", "username"})
public class ContactInList {
    @PrimaryKey(autoGenerate = true)
    int id = 0;
    @NonNull
    private String username;
    private String displayName;
    private int profilePic;
    private String password;

    //    private String bio;
    @Ignore
    public ContactInList(int id, @NonNull String username, String displayName, int profilePic, String password) {
        this.id = id;
        this.username = username;
        this.displayName = displayName;
        this.profilePic = profilePic;
//        this.bio = "bio";
        this.password = password;
    }

    @Ignore
    public ContactInList(int id, @NonNull String username, String displayName, String password) {
        this.id = id;
        this.username = username;
        this.displayName = displayName;
        this.password = password;
        this.profilePic = R.drawable.haticon;
//        this.bio = "bio";
    }

    @Ignore
    public ContactInList(int id, @NonNull String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.displayName = "displayName";
        this.profilePic = R.drawable.haticon;
//        this.bio = "bio";
    }

    public ContactInList() {
        this.username = "No Name";
        this.displayName = "No Display Name";
        this.profilePic = R.drawable.haticon;
//        this.bio = "bio";
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
