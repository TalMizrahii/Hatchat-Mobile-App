package com.example.hatchatmobile1.DaoRelated;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ContactDao {
    @Query("SELECT * FROM contact WHERE username = :username")
    List<Contact> getContactsByUsername(String username);

    @Query("SELECT * FROM contact WHERE username = :username AND displayName = :displayName")
    Contact getContactByUsernameAndDisplayName(String username, String displayName);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertContact(Contact contact);

    @Delete
    void deleteContact(Contact contact);

    @Query("DELETE FROM contact WHERE username = :username")
    void deleteAllContactsByUsername(String username);
}
