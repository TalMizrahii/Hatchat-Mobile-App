package com.example.hatchatmobile1.DaoRelated;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

/**
 * AppDatabase class for defining the database and its associated DAOs.
 */
@Database(entities = {Contact.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    /**
     * Retrieves the ContactDao for performing database operations on the Contact entity.
     *
     * @return The ContactDao object.
     */
    public abstract ContactDao getContactDao();
}
