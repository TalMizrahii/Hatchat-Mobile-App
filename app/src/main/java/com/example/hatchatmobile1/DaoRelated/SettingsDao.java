package com.example.hatchatmobile1.DaoRelated;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SettingsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Settings settings);

    @Delete
    void delete(Settings settings);

    @Query("DELETE FROM Settings")
    void deleteAll();

    @Query("SELECT * FROM Settings WHERE id = :id")
    Settings getById(int id);

    @Query("SELECT * FROM Settings")
    List<Settings> getAll();
}
