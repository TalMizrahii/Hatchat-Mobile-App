package com.example.hatchatmobile1.DaoRelated;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.hatchatmobile1.R;

import java.util.List;


@Entity(tableName = "contact",
        foreignKeys = @ForeignKey(entity = User.class,
                parentColumns = "username",
                childColumns = "username",
                onDelete = ForeignKey.CASCADE))
public class Contact {
    @PrimaryKey
    @NonNull
    private String username;
    private String displayName;
    private int profilePic;
    private String bio;
    // Remove the messages field from the Contact class.

    public Contact(@NonNull String username, String displayName, int profilePic, String bio) {
        this.username = username;
        this.displayName = displayName;
        this.profilePic = profilePic;
        this.bio = bio;
    }

    public Contact(@NonNull String username, String displayName, int profilePic) {
        this.username = username;
        this.displayName = displayName;
        this.profilePic = profilePic;
        this.bio = "bio";
    }

    public Contact() {
        this.username = "No Name";
        this.displayName = "No Display Name";
        this.profilePic = R.drawable.haticon;
        this.bio = "bio";
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
}
