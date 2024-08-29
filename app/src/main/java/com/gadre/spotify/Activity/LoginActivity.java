package com.gadre.spotify.Activity;

import static java.security.AccessController.getContext;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.gadre.spotify.R;
import com.gadre.spotify.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private static final String CHANNEL_ID = "login_channel_id";
    private NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Create Notification Channel for manage the behavior of notifications
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Login Notifications",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("Channel for login notifications");
            notificationManager.createNotificationChannel(channel);
        }

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

            // Show success message
            showError("Login successful");


            // Build and show the notification
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.notification)
                    .setContentTitle("Login Successful")
                    .setContentText("You have successfully logged in.")
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                    .setAutoCancel(true);

            // NotificationManager to display the notification.
            notificationManager.notify(1, builder.build());

            // Start the LauncherActivity
            Intent intent = new Intent(this, LauncherActivity.class);
            startActivity(intent);
        });
    }

    private void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
