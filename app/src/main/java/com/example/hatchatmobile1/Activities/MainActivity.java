package com.example.hatchatmobile1.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.hatchatmobile1.Adapters.Utils;
import com.example.hatchatmobile1.Entities.UsersResponse;
import com.example.hatchatmobile1.ServerAPI.LoginUserAPI;
import com.example.hatchatmobile1.ServerAPI.ServerResponse;
import com.example.hatchatmobile1.ServerAPI.UsersAPI;
import com.example.hatchatmobile1.ViewModals.SettingsViewModal;
import com.example.hatchatmobile1.databinding.ActivityMainBinding;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.Objects;

/**
 * The main activity to act as the login screen of the app.
 */
public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private LoginUserAPI loginUserAPI;
    private UsersAPI usersAPI;
    private SettingsViewModal settingsViewModal;
    private String androidToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);



        loginUserAPI = new LoginUserAPI(getApplicationContext());

        usersAPI = new UsersAPI(getApplicationContext());

        settingsViewModal = SettingsViewModal.getInstance(getApplicationContext());


        settingsViewModal.getSettingsLiveData().observe(this, settings -> {
            loginUserAPI.setBaseUrl(settings.getBaseUrl());
            usersAPI.setBaseUrl(settings.getBaseUrl());
            if (settings.isDayMode()) {
                // Dark mode is disabled
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            } else {
                // Dark mode is enabled
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
        });

        binding.loginBtn.setOnClickListener(v -> {
            serverResponse();
        });

        binding.settingsButton.setOnClickListener(v -> {
            // Settings button click logic
            Intent settingsIntent = new Intent(MainActivity.this, SettingActivity.class);
            settingsIntent.putExtra("logoutBtnViability", false);
            startActivity(settingsIntent);
        });
        binding.linkToChat.setOnClickListener(v -> {
            Intent registerScreen = new Intent(this, RegisterScreenActivity.class);
            startActivity(registerScreen);
        });


        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(MainActivity.this, instanceIdResult -> {
            androidToken = instanceIdResult.getToken();
        });
    }

    private void serverResponse() {
        String username = Objects.requireNonNull(binding.usernameInputText.getText()).toString();
        String password = Objects.requireNonNull(binding.passwordInputText.getText()).toString();


        loginUserAPI.getToken(username, password, androidToken, new ServerResponse<String, String>() {
            @Override
            public void onServerResponse(String token) {
                if (token != null && !token.isEmpty()) {

                    token = "Bearer " + token;

                    // Call getUserByUsername API
                    getUserByUsername(username, token);

                } else {
                    // Token is null or empty, show toast message
                    CharSequence text = "Invalid token";
                    Utils.showShortToast(getApplicationContext(), text);
                }
            }

            @Override
            public void onServerErrorResponse(String error) {
                // Token retrieval error, show toast message
                Utils.showShortToast(getApplicationContext(), error);
            }
        });
    }

    private void getUserByUsername(String username, String token) {
        usersAPI.getUserByUsername(username, token, new ServerResponse<UsersResponse, String>() {
            @Override
            public void onServerResponse(UsersResponse userResponse) {
                CharSequence text = "Welcome back " + username + "!";
                Utils.showShortToast(getApplicationContext(), text);
                Intent contactListIntent = new Intent(MainActivity.this, ContactListActivity.class);
                contactListIntent.putExtra("username", userResponse.getUsername());
                contactListIntent.putExtra("displayName", userResponse.getDisplayName());
                contactListIntent.putExtra("profilePic", userResponse.getProfilePic());
                contactListIntent.putExtra("token", token);
                binding.passwordInputText.setText("");
                startActivity(contactListIntent);
            }

            @Override
            public void onServerErrorResponse(String error) {
                // Handle the error while retrieving user data
                Utils.showShortToast(getApplicationContext(), error);
            }
        });
    }
}

