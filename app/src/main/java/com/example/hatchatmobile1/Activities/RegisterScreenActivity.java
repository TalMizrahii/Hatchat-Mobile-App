package com.example.hatchatmobile1.Activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.TooltipCompat;

import com.example.hatchatmobile1.Adapters.ToastUtils;
import com.example.hatchatmobile1.Entities.UsersResponse;
import com.example.hatchatmobile1.R;
import com.example.hatchatmobile1.ServerAPI.ServerResponse;
import com.example.hatchatmobile1.ServerAPI.UsersAPI;
import com.example.hatchatmobile1.ViewModals.SettingsViewModal;
import com.example.hatchatmobile1.databinding.ActivityRegisterScreenBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.r0adkll.slidr.Slidr;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Objects;

public class RegisterScreenActivity extends AppCompatActivity {
    private ActivityRegisterScreenBinding binding;
    private TextInputEditText passwordInputText;
    private TextInputLayout passwordInputLayout;

    private TextInputLayout confirmPasswordInputLayout;

    private TextInputEditText confirmPasswordInputText;

    private SettingsViewModal settingsViewModal;
    private Bitmap userImage;

    private boolean hasSelectedImage = false;

    private static final int REQUEST_IMAGE_GALLERY = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private float rotationAngle = 0.0f;
    private Bitmap uploadedImage = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterScreenBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Slidr.attach(this);

        passwordInputText = binding.passwordInputText;
        passwordInputLayout = binding.passwordInputLayout;

        confirmPasswordInputText = binding.confirmPasswordInputText;
        confirmPasswordInputLayout = binding.confirmPasswordInputLayout;


        ImageButton rotateLeftButton = binding.rotateLeftButton;
        ImageButton rotateRightButton = binding.rotateRightButton;


        ImageView uploadImage = binding.uploadImage;

        uploadImage.setOnClickListener(v -> openImageSelectionOptions());

        // Add tooltip to the password requirements button
        TooltipCompat.setTooltipText(binding.passwordRequirementsButton, "Password Requirements!");

        // Add click listener to show password requirements dialog
        binding.passwordRequirementsButton.setOnClickListener(v -> showPasswordRequirementsDialog());

        settingsViewModal = SettingsViewModal.getInstance(getApplicationContext());
        ;


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

        confirmPasswordInputText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not needed in this case
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                confirmPasswordMatch(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        rotateLeftButton.setOnClickListener(v -> rotateImage(-90));
        rotateRightButton.setOnClickListener(v -> rotateImage(90));

        UsersAPI usersAPI = new UsersAPI(getApplicationContext());


        binding.loginBtn.setOnClickListener(v -> {
            if (validFields()) {
                serverResponse(usersAPI);
            } else {
                CharSequence text = "Please fill all the fields";
                ToastUtils.showShortToast(getApplicationContext(), text);

            }
        });


        binding.settingsButton.setOnClickListener(v -> {
            // Settings button click logic
            Intent settingsIntent = new Intent(RegisterScreenActivity.this, SettingActivity.class);
            settingsIntent.putExtra("logoutBtnViability", false);
            startActivity(settingsIntent);
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
            passwordInputText.setError(null);
            passwordInputLayout.setEndIconCheckable(true);
            passwordInputLayout.setEndIconDrawable(R.drawable.ic_checkmark);
            passwordInputLayout.setEndIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
        } else {
            passwordInputText.setError("Password requirements not met!     Click me to see the password.");
            passwordInputLayout.setEndIconCheckable(false);
            passwordInputLayout.setEndIconDrawable(null);
        }
    }

    private boolean checkPasswordRequirements(String password) {
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
        hasSelectedImage = true;
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                Bundle extras = data.getExtras();
                userImage = (Bitmap) extras.get("data");
                uploadedImage = userImage;
                binding.uploadImage.setImageBitmap(userImage);
            } else if (requestCode == REQUEST_IMAGE_GALLERY) {
                Uri selectedImageUri = data.getData();
                try {
                    userImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                    uploadedImage = userImage;
                    binding.uploadImage.setImageBitmap(userImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void rotateImage(float angle) {
        if (uploadedImage != null) {
            rotationAngle += angle;
            Matrix matrix = new Matrix();
            matrix.postRotate(rotationAngle);
            Bitmap rotatedBitmap = Bitmap.createBitmap(uploadedImage, 0, 0, uploadedImage.getWidth(), uploadedImage.getHeight(), matrix, true);
            binding.uploadImage.setImageBitmap(rotatedBitmap);
        }
    }

    private void confirmPasswordMatch(String confirmPassword) {
        if (Objects.equals(confirmPassword, Objects.requireNonNull(passwordInputText.getText()).toString())) {
            confirmPasswordInputLayout.setErrorEnabled(false);
            confirmPasswordInputText.setError(null);
            confirmPasswordInputLayout.setEndIconCheckable(true);
            confirmPasswordInputLayout.setEndIconDrawable(R.drawable.ic_checkmark);
            confirmPasswordInputLayout.setEndIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
        } else {
            confirmPasswordInputText.setError("Password does not match!");
            confirmPasswordInputLayout.setEndIconCheckable(false);
            confirmPasswordInputLayout.setEndIconDrawable(null);
//            passwordInputLayout.setEndIconTintList(null);
        }
    }

    private boolean validFields() {
        return !Objects.requireNonNull(passwordInputText.getText()).toString().equals("")
                && !Objects.requireNonNull(binding.usernameInputText.getText()).toString().equals("")
                && !Objects.requireNonNull(confirmPasswordInputText.getText()).toString().equals("")
                && !Objects.requireNonNull(binding.fullnameInputText.getText()).toString().equals("");
    }

    private void serverResponse(UsersAPI usersAPI) {
        String username = Objects.requireNonNull(binding.fullnameInputText.getText()).toString();
        String password = Objects.requireNonNull(binding.passwordInputText.getText()).toString();
        String profilePic = "";
        if (hasSelectedImage) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            this.uploadedImage.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            profilePic = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(byteArray);
        }
        String displayName = Objects.requireNonNull(binding.usernameInputText.getText()).toString();

        usersAPI.postNewUser(username, password, displayName, profilePic, new ServerResponse<UsersResponse, String>() {
            @Override
            public void onServerResponse(UsersResponse userResponse) {
                CharSequence text = "User created successfully : " + userResponse.getDisplayName();
                ToastUtils.showShortToast(getApplicationContext(), text);

                // User created, finish this activity and return to the previous activity
                finish();

            }

            @Override
            public void onServerErrorResponse(String error) {
                // User creation error, show toast message
                ToastUtils.showShortToast(getApplicationContext(), error);

            }
        });
    }
}
