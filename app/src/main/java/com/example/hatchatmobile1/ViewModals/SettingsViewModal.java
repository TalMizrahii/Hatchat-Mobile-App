package com.example.hatchatmobile1.ViewModals;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.hatchatmobile1.DaoRelated.Settings;
import com.example.hatchatmobile1.Repositories.SettingsRepository;

public class SettingsViewModal {

    private SettingsRepository settingsRepository;

    private LiveData<Settings> settingsLiveData;

    /**
     * A constructor for the class.
     *
     * @param context The context of the app.
     */
    public SettingsViewModal(Context context) {
        settingsRepository = new SettingsRepository(context);
        settingsLiveData = settingsRepository.getSettingsLiveData();
    }

    /**
     * Retrieves the LiveData object containing the settings.
     *
     * @return The LiveData object containing the settings.
     */
    public LiveData<Settings> getSettingsLiveData() {
        return settingsLiveData;
    }

    /**
     * Sets the settings.
     *
     * @param settings The settings to be set.
     */
    public void setSettings(Settings settings) {
        settingsRepository.setSettings(settings);
    }

    /**
     * Retrieves the current settings.
     * If no settings are found, default settings will be set and returned.
     *
     * @return The current settings.
     */
    public Settings getSettings() {
        if (settingsRepository.getSettings() == null) {
            settingsRepository.setSettings(new Settings(0, "http://10.0.2.2:5000/api/", true));
        }
        return settingsRepository.getSettings();
    }

    /**
     * Deletes the settings.
     */
    public void deleteSettings() {
        settingsRepository.deleteSettings();
    }
}
