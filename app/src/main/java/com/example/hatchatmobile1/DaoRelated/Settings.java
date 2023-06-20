package com.example.hatchatmobile1.DaoRelated;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Settings {
    @PrimaryKey
    private int id;

    private String baseUrl;

    private boolean isDayMode;

    public Settings(int id, String baseUrl, boolean isDayMode) {
        this.id = id;
        this.baseUrl = baseUrl;
        this.isDayMode = isDayMode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public boolean isDayMode() {
        return isDayMode;
    }

    public void setDayMode(boolean dayMode) {
        isDayMode = dayMode;
    }
}
