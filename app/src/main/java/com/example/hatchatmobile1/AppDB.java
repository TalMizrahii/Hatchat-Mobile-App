package com.example.hatchatmobile1;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {ContactInList.class}, version = 1)
public abstract class AppDB  extends RoomDatabase {
    public abstract ContactDao contactDao();
}
