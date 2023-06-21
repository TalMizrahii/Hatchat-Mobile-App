package com.example.hatchatmobile1.Activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.hatchatmobile1.DaoRelated.Settings;
import com.example.hatchatmobile1.R;
import com.example.hatchatmobile1.ViewModals.SettingsViewModal;
import com.example.hatchatmobile1.databinding.ActivitySettingBinding;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class SettingActivity extends AppCompatActivity {
    private ActivitySettingBinding binding;
    private TextInputLayout urlLayout;

    private TextInputEditText urlText;
    private SettingsViewModal settingsViewModal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        settingsViewModal = new SettingsViewModal(getApplicationContext());
        settingsViewModal.getSettingsLiveData().observe(this, settings -> {

        });
        if (!settingsViewModal.getSettings().isDayMode()){
            binding.darkModeSwitch.setChecked(true);
            darkMode();
        }

       binding.darkModeSwitch.setOnClickListener(v -> {
           if (binding.darkModeSwitch.isChecked()){
               settingsViewModal.setSettings(new Settings(0, settingsViewModal.getSettings().getBaseUrl(), false));
           }else {
               settingsViewModal.setSettings(new Settings(0, settingsViewModal.getSettings().getBaseUrl(), true));

           }
           darkMode();
       });

        binding.returnButton.setOnClickListener(v -> finish());
        urlLayout = binding.UrlLayout;
        urlText = binding.UrlText;


        urlText.setHint("The current URL: " + settingsViewModal.getSettings().getBaseUrl());

        binding.IPSwitch.setOnClickListener(v -> validateIP());


    }


    @Override
    protected void onRestart() {
        super.onRestart();
        darkMode();
    }

    private boolean isValidURL(String url) {
        if (!TextUtils.isEmpty(url)) {
            return Patterns.WEB_URL.matcher(url).matches();
        }
        return false;
    }

    @SuppressLint("StringFormatInvalid")
    private void validateIP() {
        String url = urlText.getText() != null ? urlText.getText().toString().trim() : "";

        if (isValidURL(url)) {
            // URL is valid, show green arrow icon
            urlLayout.setErrorEnabled(false);
            urlText.setError(null);
            settingsViewModal.setSettings(new Settings(0, url, settingsViewModal.getSettings().isDayMode()));

            // Reset switch button and clear text field
            binding.IPSwitch.setChecked(false);
            urlText.getText().clear();
            urlText.setHint("The current URL: " + settingsViewModal.getSettings().getBaseUrl());

            CharSequence text = "The URL has changed to: " + settingsViewModal.getSettings().getBaseUrl();
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(getApplicationContext(), text, duration);
            toast.show();
        } else {
            // Invalid URL, show error
            urlText.setError("Invalid URL");
            binding.IPSwitch.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            binding.IPSwitch.setChecked(false);
        }
    }

    private void darkMode() {
            if (!settingsViewModal.getSettings().isDayMode()) {

                setTheme(R.style.Theme_night);
                // Dark mode is enabled
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);



                recreate();
            } else {
                setTheme(R.style.Theme_HatchatMobile1);
                // Dark mode is disabled
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                recreate();
            }
    }
}

