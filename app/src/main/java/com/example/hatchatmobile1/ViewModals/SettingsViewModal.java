package com.example.hatchatmobile1.ViewModals;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.hatchatmobile1.DaoRelated.Settings;
import com.example.hatchatmobile1.Repositories.SettingsRepository;

public class SettingsViewModal {

    private SettingsRepository settingsRepository;

    private LiveData<Settings> settingsLiveData;

    public SettingsViewModal(Context context) {
        settingsRepository = new SettingsRepository(context);
        settingsLiveData = settingsRepository.getSettingsLiveData();
    }

    public LiveData<Settings> getSettingsLiveData() {
        return settingsLiveData;
    }

    public void setSettings(Settings settings) {
        settingsRepository.setSettings(settings);
    }

    public Settings getSettings() {
        return settingsRepository.getSettings();
    }


    public void deleteSettings() {
        settingsRepository.deleteSettings();
    }
}
