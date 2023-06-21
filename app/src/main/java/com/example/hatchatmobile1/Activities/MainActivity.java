package com.example.hatchatmobile1.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hatchatmobile1.ServerAPI.LoginUserAPI;
import com.example.hatchatmobile1.ViewModals.SettingsViewModal;
import com.example.hatchatmobile1.databinding.ActivityMainBinding;

import java.util.Objects;

/**
 * The main activity to act as the login screen of the app.
 */
public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private LoginUserAPI loginUserAPI;
    private SettingsViewModal settingsViewModal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        loginUserAPI = new LoginUserAPI(getApplicationContext());

        settingsViewModal = new SettingsViewModal(getApplicationContext());

        settingsViewModal.getSettingsLiveData().observe(this, settings -> {
            loginUserAPI.setBaseUrl(settings.getBaseUrl());
        });

        binding.loginBtn.setOnClickListener(v -> {
            String username = Objects.requireNonNull(binding.usernameInputText.getText()).toString();
            String password = Objects.requireNonNull(binding.passwordInputText.getText()).toString();
            loginUserAPI.getToken(username, password, new LoginUserAPI.TokenCallback() {
                @Override
                public void onTokenReceived(String token) {
                    if (token != null && !token.isEmpty()) {
                        CharSequence text = "Welcome back " + username + "!";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(MainActivity.this, text, duration);
                        toast.show();
                        // Valid token, start ContactListActivity
                        Intent contactListIntent = new Intent(MainActivity.this, ContactListActivity.class);
                        contactListIntent.putExtra("username", username);
                        contactListIntent.putExtra("token", token);
                        startActivity(contactListIntent);
                    } else {
                        // Token is null or empty, show toast message
                        CharSequence text = "Invalid token";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(MainActivity.this, text, duration);
                        toast.show();
                    }
                }
                @Override
                public void onTokenError(String error) {
                    // Token retrieval error, show toast message
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(MainActivity.this, error, duration);
                    toast.show();
                }
            });
        });

        binding.settingsButton.setOnClickListener(v -> {
            // Settings button click logic
            Intent settingsIntent = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(settingsIntent);
        });
        binding.linkToChat.setOnClickListener(v -> {
            Intent registerScreen = new Intent(this, RegisterScreenActivity.class);
            startActivity(registerScreen);
        });
    }
}

