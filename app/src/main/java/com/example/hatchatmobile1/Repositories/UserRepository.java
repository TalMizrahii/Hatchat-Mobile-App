package com.example.hatchatmobile1.Repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.hatchatmobile1.DaoRelated.User;
import com.example.hatchatmobile1.DaoRelated.UserDao;

import java.util.List;

public class UserRepository {
    private UserDao userDao;
    private MutableLiveData<List<User>> userListLiveData;

    public UserRepository(UserDao userDao) {
        this.userDao = userDao;
        userListLiveData = new MutableLiveData<>();
        loadUsers(); // Load the initial data
    }

    public LiveData<List<User>> getUserListLiveData() {
        return userListLiveData;
    }

    private void loadUsers() {
        List<User> userList = userDao.getAllUsers();
        userListLiveData.setValue(userList);
    }

    public void insertUser(User user) {
        userDao.insertUser(user);
        loadUsers(); // Reload the data after insertion
    }

    public void deleteUser(User user) {
        userDao.deleteUser(user);
        loadUsers(); // Reload the data after deletion
    }
}