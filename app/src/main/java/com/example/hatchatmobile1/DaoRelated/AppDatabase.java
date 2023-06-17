package com.example.hatchatmobile1.DaoRelated;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {User.class, Contact.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao getUserDao();
}
