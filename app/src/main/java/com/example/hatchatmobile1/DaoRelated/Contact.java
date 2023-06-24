package com.example.hatchatmobile1.DaoRelated;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.Relation;
import androidx.room.TypeConverters;

import java.util.List;

/**
 * Entity class representing a contact.
 */
@Entity
public class Contact {
    @PrimaryKey
    @NonNull
    private String username;
    private String displayName;
    private String profilePic;
    private String bio;
    private String mainUser;
    private int id;
    @TypeConverters(Converters.class)
    private List<Message> messages;

    /**
     * Constructor for creating a Contact object.
     *
     * @param username    The username of the contact.
     * @param displayName The display name of the contact.
     * @param profilePic  The profile picture resource ID of the contact.
     * @param mainUser    The username of the main user.
     * @param bio         The biography of the contact.
     * @param messages    The list of messages associated with the contact.
     */
    public Contact(@NonNull String username, String displayName, String profilePic, String mainUser, String bio, int id, List<Message> messages) {
        this.username = username;
        this.displayName = displayName;
        this.profilePic = profilePic;
        this.bio = bio;
        this.mainUser = mainUser;
        this.messages = messages;
        this.id = id;
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
    public String getProfilePic() {
        return profilePic;
    }

    /**
     * Sets the profile picture resource ID of the contact.
     *
     * @param profilePic The profile picture resource ID of the contact.
     */
    public void setProfilePic(String profilePic) {
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

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
