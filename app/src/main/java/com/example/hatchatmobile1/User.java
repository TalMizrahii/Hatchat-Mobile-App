package com.example.hatchatmobile1;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class User {
    @PrimaryKey(autoGenerate = true)
    int id = 0;

    private String username;
    private String password;


}