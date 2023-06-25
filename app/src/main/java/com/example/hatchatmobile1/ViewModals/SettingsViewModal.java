package com.example.hatchatmobile1.ViewModals;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.hatchatmobile1.DaoRelated.Settings;
import com.example.hatchatmobile1.Repositories.SettingsRepository;

public class SettingsViewModal {

    private static SettingsViewModal instance;
    private SettingsRepository settingsRepository;
    private LiveData<Settings> settingsLiveData;

    /**
     * Private constructor to prevent direct instantiation.
     *
     * @param context The context of the app.
     */
    private SettingsViewModal(Context context) {
        settingsRepository = new SettingsRepository(context);
        settingsLiveData = settingsRepository.getSettingsLiveData();
    }

    /**
     * Retrieves the singleton instance of SettingsViewModal.
     *
     * @param context The context of the app.
     * @return The singleton instance of SettingsViewModal.
     */
    public static  SettingsViewModal getInstance(Context context) {
        if (instance == null) {
            synchronized (SettingsViewModal.class) {
                if (instance == null) {
                    instance = new SettingsViewModal(context.getApplicationContext());
                }
            }
        }
        return instance;
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
