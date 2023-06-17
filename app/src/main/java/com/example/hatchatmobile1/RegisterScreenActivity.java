package com.example.hatchatmobile1;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.TooltipCompat;

import com.example.hatchatmobile1.databinding.ActivityRegisterScreenBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;

public class RegisterScreenActivity extends AppCompatActivity {
    private ActivityRegisterScreenBinding binding;
    private TextInputEditText passwordInputText;
    private TextInputLayout passwordInputLayout;
    private static final int REQUEST_IMAGE_GALLERY = 1;

    private static final int REQUEST_IMAGE_CAPTURE = 2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterScreenBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        passwordInputText = binding.passwordInputText;
        passwordInputLayout = binding.passwordInputLayout;
        ImageView uploadImage = binding.uploadImage;
        View passwordRequirementsButton = binding.passwordRequirementsButton;
        uploadImage.setOnClickListener(v -> openImageSelectionOptions());


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
    private void openImageSelectionOptions() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Image Source");
        builder.setItems(new CharSequence[]{"Gallery", "Camera"}, (dialog, which) -> {
            switch (which) {
                case 0:
                    openGallery();
                    break;
                case 1:
                    openCamera();
                    break;
            }
        });
        builder.show();
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_IMAGE_GALLERY);
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                ImageView uploadImage = findViewById(R.id.uploadImage);
                uploadImage.setImageBitmap(imageBitmap);
            } else if (requestCode == REQUEST_IMAGE_GALLERY) {
                Uri selectedImageUri = data.getData();
                try {
                    Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                    ImageView uploadImage = findViewById(R.id.uploadImage);
                    uploadImage.setImageBitmap(imageBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
