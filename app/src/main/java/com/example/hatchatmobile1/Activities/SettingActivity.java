package com.example.hatchatmobile1.Activities;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
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
import com.google.android.material.textfield.TextInputLayout;

public class SettingActivity extends AppCompatActivity {
    private ActivitySettingBinding binding;
    private TextInputLayout IPLayout;

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

        binding.returnButton.setOnClickListener(v -> finish());
        IPLayout = binding.IPLayout;
        binding.darkModeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Dark mode is enabled
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                // Dark mode is disabled
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
            recreate(); // Recreate the activity to apply the new theme
        });

        binding.IPSwitch.setOnClickListener(v -> validateIP());

    }

    private boolean isValidURL(String url) {
        if (!TextUtils.isEmpty(url)) {
            return Patterns.WEB_URL.matcher(url).matches();
        }
        return false;
    }

    @SuppressLint("StringFormatInvalid")
    private void validateIP() {
        String url = binding.IPText.getText() != null ? binding.IPText.getText().toString().trim() : "";

        if (isValidURL(url)) {
            // URL is valid, show green arrow icon
            IPLayout.setErrorEnabled(false);
            binding.IPText.setError(null);
            IPLayout.setEndIconCheckable(true);
            IPLayout.setEndIconDrawable(R.drawable.ic_checkmark);
            IPLayout.setEndIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));



            settingsViewModal.setSettings(new Settings(0,url,settingsViewModal.getSettings().isDayMode()));

            CharSequence text = "The new URL is : " + url;
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(getApplicationContext(), text, duration);
            toast.show();

            // Reset switch button and clear text field
            binding.IPSwitch.setChecked(false);
            binding.IPText.getText().clear();
        } else {
            // Invalid URL, show error
            binding.IPText.setError("Invalid URL");
            binding.IPSwitch.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            binding.IPSwitch.setChecked(false);
        }
    }
}
