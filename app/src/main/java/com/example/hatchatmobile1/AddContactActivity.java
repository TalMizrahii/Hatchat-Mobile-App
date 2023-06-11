package com.example.hatchatmobile1;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.hatchatmobile1.databinding.ActivityAddContactBinding;

public class AddContactActivity extends AppCompatActivity {

    private ActivityAddContactBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddContactBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}
