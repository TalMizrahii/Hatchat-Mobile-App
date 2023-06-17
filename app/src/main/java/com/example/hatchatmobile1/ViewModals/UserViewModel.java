package com.example.hatchatmobile1.ViewModals;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.hatchatmobile1.DaoRelated.AppDatabase;
import com.example.hatchatmobile1.DaoRelated.User;
import com.example.hatchatmobile1.Repositories.UserRepository;

import java.util.List;

public class UserViewModel extends ViewModel {
    private UserRepository userRepository;
    private LiveData<List<User>> userListLiveData;

    public UserViewModel() {
    }

    public LiveData<List<User>> getUserListLiveData() {
        return userListLiveData;
    }

    public void insertUser(User user) {
        userRepository.insertUser(user);
    }

    public void deleteUser(User user) {
        userRepository.deleteUser(user);
    }
}