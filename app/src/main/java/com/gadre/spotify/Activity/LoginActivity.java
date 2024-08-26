package com.gadre.spotify.Activity;

import static java.security.AccessController.getContext;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.gadre.spotify.R;
import com.gadre.spotify.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.loginButton.setOnClickListener(view -> {
            String username = binding.usernameEditText.getText().toString().trim();
            String email = binding.emailEditText.getText().toString().trim();
            String password = binding.passwordEditText.getText().toString().trim();

            // Validate inputs
            if (username.isEmpty()) {
                showError("Enter Username");
                return;
            }

            if (email.isEmpty()) {
                showError("Enter Email");
                return;
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                showError("Enter a Valid Email");
                return;
            }

            if (password.isEmpty()) {
                showError("Enter Password");
                return;
            } else if (password.length() < 6) {
                showError("Password must be at least 6 characters");
                return;
            }

            showError("Login successful");

            Intent intent = new Intent(this, LauncherActivity.class);
            startActivity(intent);


        });

    }

    private void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}