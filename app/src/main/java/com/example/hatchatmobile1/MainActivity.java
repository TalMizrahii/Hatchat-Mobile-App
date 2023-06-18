package com.example.hatchatmobile1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hatchatmobile1.databinding.ActivityMainBinding;

import java.util.List;
import java.util.Objects;

/**
 * The main activity to act as the login screen of the app.
 */
public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.loginBtn.setOnClickListener(v -> {
            String username = Objects.requireNonNull(binding.usernameInputText.getText()).toString();
            String password = Objects.requireNonNull(binding.passwordInputText.getText()).toString();

            if (isValidCredentials(username, password)) {
                CharSequence text = "Welcome back " + username + "!";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(this, text, duration);
                toast.show();

                // Valid credentials, start ContactListActivity
                Intent contactListIntent = new Intent(this, ContactListActivity.class);
                contactListIntent.putExtra("username", username);
                startActivity(contactListIntent);
            } else {
                // Invalid credentials, show toast message
                CharSequence text = "Invalid Username/Password!";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(this, text, duration);
                toast.show();
            }
        });

        binding.linkToChat.setOnClickListener(v -> {
            Intent registerScreen = new Intent(this, RegisterScreenActivity.class);
            startActivity(registerScreen);
        });
    }
    private boolean isValidCredentials(String username, String password) {

        return (username.equals("admin") && password.equals("password"));
    }
}
