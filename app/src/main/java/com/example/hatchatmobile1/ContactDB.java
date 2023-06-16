package com.example.hatchatmobile1;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {ContactInList.class}, version = 3, exportSchema = false)
public abstract class ContactDB extends RoomDatabase {
    public abstract ContactDao contactDao();
}
