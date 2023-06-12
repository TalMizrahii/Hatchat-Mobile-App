package com.example.hatchatmobile1;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.TooltipCompat;

import com.example.hatchatmobile1.databinding.ActivityRegisterScreenBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

public class RegisterScreenActivity extends AppCompatActivity {
    private ActivityRegisterScreenBinding binding;
    // Regex patterns for password validation
    private String[] passwordRegexPatterns = {
            "^.{8,}$",  // At least 8 characters long
            "^(?=.*[A-Z]).+$",  // At least 1 uppercase letter A - Z
            "^(?=.*[a-z]).+$",  // At least 1 lowercase letter a - z
            "^(?=.*\\d).+$",  // At least 1 number 0 - 9
            "^(?=.*[@$!%*?&]).+$"  // At least 1 special character (!, @, #, $, etc.)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterScreenBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        TextInputEditText passwordInputText = binding.passwordInputText;
        View passwordRequirementsButton = binding.passwordRequirementsButton;

        // Add tooltip to the password requirements button
        TooltipCompat.setTooltipText(passwordRequirementsButton, "Password Requirements!");

        // Add click listener to show password requirements dialog
        passwordRequirementsButton.setOnClickListener(v -> showPasswordRequirementsDialog());
    }

    private void showPasswordRequirementsDialog() {
        // Create and show the password requirements dialog
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setTitle("Password Requirements")
                .setMessage("At least 8 characters long.\n1 uppercase letter A - Z.\n1 lowercase letter a - z.\n1 number 0 - 9.\n1 special character (!, @, #, $, etc.).")
                .setPositiveButton("OK", null)
                .show();
    }


}


