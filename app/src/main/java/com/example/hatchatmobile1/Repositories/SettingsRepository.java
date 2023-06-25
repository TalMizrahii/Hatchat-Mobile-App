package com.example.hatchatmobile1.Repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.hatchatmobile1.DaoRelated.AppDatabase;
import com.example.hatchatmobile1.DaoRelated.Contact;
import com.example.hatchatmobile1.DaoRelated.Settings;
import com.example.hatchatmobile1.DaoRelated.SettingsDao;

import java.util.List;
import java.util.concurrent.Executors;

public class SettingsRepository {

    private SettingsDao settingsDao;
    private SettingsLiveData settingsLiveData;

    public SettingsRepository(Context context) {
        AppDatabase appDatabase = Room.databaseBuilder(context, AppDatabase.class, "AppDatabase")
                .allowMainThreadQueries()
                .build();
        this.settingsDao = appDatabase.getSettingsDao();
        this.settingsLiveData = new SettingsLiveData();
    }

    /**
     * LiveData class that holds an instance of settings.
     */
    class SettingsLiveData extends MutableLiveData<Settings> {

        public SettingsLiveData() {
            super();
            setValue(settingsDao.getById(0));
        }

        @Override
        protected void onActive() {
            super.onActive();
            new Thread(() -> settingsLiveData.postValue(settingsDao.getById(0))).start();
        }
    }

    public void reload() {
        settingsLiveData.setValue(settingsDao.getById(0));
    }

    public LiveData<Settings> getSettingsLiveData() {
        return settingsLiveData;
    }

    public Settings getSettings() {
        return settingsDao.getById(0);
    }

    public void setSettings(Settings settings) {
        Executors.newSingleThreadExecutor().execute(() -> {
            settingsDao.insert(settings);
            reload();
        });
    }

    public void deleteSettings() {
        settingsDao.deleteAll();
        reload();
    }
}
