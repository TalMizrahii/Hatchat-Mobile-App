package com.example.hatchatmobile1;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ContactDao {

    @Query("SELECT * FROM contacts")
    List<ContactInList> index();

//    @Query("SELECT * FROM contactinlist WHERE id = :id")
//    ContactInList get(int id);

    @Query("SELECT * FROM contacts WHERE username = :username")
    ContactInList get(String username);
    @Insert
    void insert(ContactInList... contactInLists);

    @Delete
    void delete(ContactInList... contactInLists);

    @Update
    void update(ContactInList... contactInLists);
}
