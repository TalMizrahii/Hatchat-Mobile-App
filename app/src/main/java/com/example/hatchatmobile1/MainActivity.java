package com.example.hatchatmobile1;

        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Intent;
        import android.os.Bundle;

        import com.example.hatchatmobile1.databinding.ActivityMainBinding;

/**
 * The main activity to act as the login screen of the app.
 */
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    /**
     * The onCreate override for the main activity.
     * @param savedInstanceState If the activity is being re-initialized after
     * previously being shut down then this Bundle contains the data it most
     * recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.loginBtn.setOnClickListener(v -> {
            Intent contactListIntent = new Intent(this, ContactListActivity.class);
            startActivity(contactListIntent);
        });
    }
}
