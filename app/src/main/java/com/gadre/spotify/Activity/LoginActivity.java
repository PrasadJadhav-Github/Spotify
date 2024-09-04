package com.gadre.spotify.Activity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.gadre.spotify.OtherClasses.LoadingDialog;
import com.gadre.spotify.R;
import com.gadre.spotify.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private static final String CHANNEL_ID = "login_channel_id";
    private NotificationManager notificationManager;
    private SharedPreferences sharedPreferences;
    private LoadingDialog loadingDialog;  // Add LoadingDialog field

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loadingDialog = new LoadingDialog(this); // Initialize LoadingDialog
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        checkLoginStatus();
        loginButton();

        // Create Notification Channel for managing the behavior of notifications
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Login Notifications",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("Channel for login notifications");
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void loginButton() {
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

            // Show the loading dialog
            loadingDialog.startAlertDialog();
            new android.os.Handler().postDelayed(() -> {
                // Save user data
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("username", username);
                editor.putString("email", email);
                editor.putString("password", password);
                editor.apply();

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

                // NotificationManager to display the notification
                notificationManager.notify(1, builder.build());

                // Start the LauncherActivity
                Intent intent = new Intent(this, LauncherActivity.class);
                startActivity(intent);
                finish();

                // Dismiss the loading dialog
                loadingDialog.closeAlertDialog();
            }, 2000); // Simulate a delay of 2 seconds
        });
    }

    private void checkLoginStatus() {
        String username = sharedPreferences.getString("username", null);
        String email = sharedPreferences.getString("email", null);
        String password = sharedPreferences.getString("password", null);

        if (username != null && email != null && password != null) {
            Intent intent = new Intent(this, LauncherActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
