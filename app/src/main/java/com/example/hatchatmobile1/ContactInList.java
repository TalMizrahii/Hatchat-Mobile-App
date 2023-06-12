package com.example.hatchatmobile1;

public class ContactInList {
    String username;
    String displayName;
    int profilePic = R.drawable.haticon;

    public ContactInList(String username, String displayName, int profilePic) {
        this.username = username;
        this.displayName = displayName;
        this.profilePic = profilePic;
    }

    public ContactInList(String username, String displayName) {
        this.username = username;
        this.displayName = displayName;
    }

    public ContactInList() {
        this.username = "No Name";
        this.displayName = "No Display Name";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
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
}
