package com.example.hatchatmobile1;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.TooltipCompat;

import com.example.hatchatmobile1.databinding.ActivityRegisterScreenBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterScreenActivity extends AppCompatActivity {
    private ActivityRegisterScreenBinding binding;
    private TextInputEditText passwordInputText;
    private TextInputLayout passwordInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterScreenBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        passwordInputText = binding.passwordInputText;
        passwordInputLayout = binding.passwordInputLayout;
        View passwordRequirementsButton = binding.passwordRequirementsButton;

        // Add tooltip to the password requirements button
        TooltipCompat.setTooltipText(passwordRequirementsButton, "Password Requirements!");

        // Add click listener to show password requirements dialog
        passwordRequirementsButton.setOnClickListener(v -> showPasswordRequirementsDialog());

        // Add text change listener to the password input field
        passwordInputText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not needed in this case
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validatePassword(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void showPasswordRequirementsDialog() {
        // Create and show the password requirements dialog
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setTitle("Password Requirements")
                .setMessage("At least 8 characters long.\n1 uppercase letter A - Z.\n1 lowercase letter a - z.\n1 number 0 - 9.\n1 special character (!, @, #, $, etc.).")
                .setPositiveButton("OK", null)
                .show();
    }

    private void validatePassword(String password) {
        boolean isPasswordValid = checkPasswordRequirements(password);

        if (isPasswordValid) {
            passwordInputLayout.setErrorEnabled(false);
            passwordInputLayout.setError(null);
            passwordInputLayout.setEndIconCheckable(true);
            passwordInputLayout.setEndIconDrawable(R.drawable.ic_checkmark);
            passwordInputLayout.setEndIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
        } else {
            passwordInputLayout.setError("Password requirements not met!");
            passwordInputLayout.setEndIconCheckable(false);
            passwordInputLayout.setEndIconDrawable(null);
            passwordInputLayout.setEndIconTintList(null);
        }
    }

    private boolean checkPasswordRequirements(String password) {
        // Add your password validation logic here using the provided regex patterns
        String regex1 = "^.{8,}$";
        String regex2 = "^(?=.*[A-Z]).+$";
        String regex3 = "^(?=.*[a-z]).+$";
        String regex4 = "^(?=.*\\d).+$";
        String regex5 = "^(?=.*[@$!%*?&]).+$";

        return password.matches(regex1) &&
                password.matches(regex2) &&
                password.matches(regex3) &&
                password.matches(regex4) &&
                password.matches(regex5);
    }
}
