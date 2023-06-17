package com.example.hatchatmobile1.DaoRelated;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "users")
public class User {
    @PrimaryKey
    @NonNull
    private String username;
    private String displayName;
    private int profilePic;
    private String password;
    private String token;

    /**
     * A full constructor with all fields.
     * @param username The username of the user.
     * @param displayName The display name of the user.
     * @param profilePic The profile picture of the user.
     * @param password The password of the user.
     * @param token The token associated with the user.
     */
    public User(@NonNull String username, String displayName, int profilePic, String password, String token) {
        this.username = username;
        this.displayName = displayName;
        this.profilePic = profilePic;
        this.password = password;
        this.token = token;
    }

    /**
     * A constructor with minimal fields, only the username, displayName, and profilePic.
     * @param username The username of the user.
     * @param displayName The display name of the user.
     * @param profilePic The profile picture of the user.
     */
    public User(@NonNull String username, String displayName, int profilePic) {
        this.username = username;
        this.displayName = displayName;
        this.profilePic = profilePic;
    }

    /**
     * A default constructor.
     */
    public User(){

    }

    /**
     * Getter for the username of the user.
     * @return The username of the user.
     */
    @NonNull
    public String getUsername() {
        return username;
    }

    /**
     * Setter for the username of the user.
     * @param username The username of the user.
     */
    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    /**
     * Getter for the display name of the user.
     * @return The display name of the user.
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Setter for the display name of the user.
     * @param displayName The display name of the user.
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Getter for the profile picture of the user.
     * @return The profile picture of the user.
     */
    public int getProfilePic() {
        return profilePic;
    }

    /**
     * Setter for the profile picture of the user.
     * @param profilePic The profile picture of the user.
     */
    public void setProfilePic(int profilePic) {
        this.profilePic = profilePic;
    }

    /**
     * Getter for the password of the user.
     * @return The password of the user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter for the password of the user.
     * @param password The password of the user.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter for the token associated with the user.
     * @return The token associated with the user.
     */
    public String getToken() {
        return token;
    }

    /**
     * Setter for the token associated with the user.
     * @param token The token associated with the user.
     */
    public void setToken(String token) {
        this.token = token;
    }
}
