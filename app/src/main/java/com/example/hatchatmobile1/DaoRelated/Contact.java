package com.example.hatchatmobile1.DaoRelated;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Entity class representing a contact.
 */
@Entity
public class Contact {
    @PrimaryKey
    @NonNull
    private String username;
    private String displayName;
    private int profilePic;
    private String bio;
    private String mainUser;

    /**
     * Constructor for creating a Contact object.
     *
     * @param username    The username of the contact.
     * @param displayName The display name of the contact.
     * @param profilePic  The profile picture resource ID of the contact.
     * @param mainUser    The username of the main user.
     * @param bio         The biography of the contact.
     */
    @Ignore
    public Contact(@NonNull String username, String displayName, int profilePic, String mainUser, String bio) {
        this.username = username;
        this.displayName = displayName;
        this.profilePic = profilePic;
        this.bio = bio;
        this.mainUser = mainUser;
    }

    /**
     * Constructor for creating a Contact object with a default bio value.
     *
     * @param username    The username of the contact.
     * @param displayName The display name of the contact.
     * @param profilePic  The profile picture resource ID of the contact.
     * @param mainUser    The username of the main user.
     */
    public Contact(@NonNull String username, String displayName, int profilePic, String mainUser) {
        this.username = username;
        this.displayName = displayName;
        this.profilePic = profilePic;
        this.bio = "bio";
        this.mainUser = mainUser;
    }

    /**
     * Retrieves the username of the contact.
     *
     * @return The username of the contact.
     */
    @NonNull
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the contact.
     *
     * @param username The username of the contact.
     */
    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    /**
     * Retrieves the display name of the contact.
     *
     * @return The display name of the contact.
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Sets the display name of the contact.
     *
     * @param displayName The display name of the contact.
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Retrieves the profile picture resource ID of the contact.
     *
     * @return The profile picture resource ID of the contact.
     */
    public int getProfilePic() {
        return profilePic;
    }

    /**
     * Sets the profile picture resource ID of the contact.
     *
     * @param profilePic The profile picture resource ID of the contact.
     */
    public void setProfilePic(int profilePic) {
        this.profilePic = profilePic;
    }

    /**
     * Retrieves the biography of the contact.
     *
     * @return The biography of the contact.
     */
    public String getBio() {
        return bio;
    }

    /**
     * Sets the biography of the contact.
     *
     * @param bio The biography of the contact.
     */
    public void setBio(String bio) {
        this.bio = bio;
    }

    /**
     * Retrieves the username of the main user.
     *
     * @return The username of the main user.
     */
    public String getMainUser() {
        return mainUser;
    }

    /**
     * Sets the username of the main user.
     *
     * @param mainUser The username of the main user.
     */
    public void setMainUser(String mainUser) {
        this.mainUser = mainUser;
    }
}
