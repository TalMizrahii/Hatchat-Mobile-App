package com.example.hatchatmobile1.DaoRelated;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Settings {
    @PrimaryKey
    private int id;  // The primary key for the settings entity.

    private String baseUrl;  // The base URL for API requests.

    private boolean isDayMode;  // Indicates whether the app is in day mode or not.

    /**
     * Constructor for the Settings class.
     *
     * @param id        The ID of the settings entity.
     * @param baseUrl   The base URL for API requests.
     * @param isDayMode Indicates whether the app is in day mode or not.
     */
    public Settings(int id, String baseUrl, boolean isDayMode) {
        this.id = id;
        this.baseUrl = baseUrl;
        this.isDayMode = isDayMode;
    }

    /**
     * Retrieves the ID of the settings entity.
     *
     * @return The ID of the settings entity.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the settings entity.
     *
     * @param id The ID to be set for the settings entity.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retrieves the base URL for API requests.
     *
     * @return The base URL for API requests.
     */
    public String getBaseUrl() {
        return baseUrl;
    }

    /**
     * Sets the base URL for API requests.
     *
     * @param baseUrl The base URL to be set for API requests.
     */
    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    /**
     * Checks if the app is in day mode.
     *
     * @return true if the app is in day mode, false otherwise.
     */
    public boolean isDayMode() {
        return isDayMode;
    }

    /**
     * Sets the day mode status of the app.
     *
     * @param dayMode true to set the app in day mode, false to set it in night mode.
     */
    public void setDayMode(boolean dayMode) {
        isDayMode = dayMode;
    }
}
