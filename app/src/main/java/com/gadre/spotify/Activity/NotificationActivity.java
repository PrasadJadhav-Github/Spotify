package com.gadre.spotify.Activity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import com.gadre.spotify.R;
import com.gadre.spotify.databinding.ActivityNotificationBinding;

public class NotificationActivity extends AppCompatActivity {

    private ActivityNotificationBinding binding;
    private static final String CHANNEL_ID = "login_channel_id";
    private NotificationManager notificationManager;
    private final int progress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Create Notification Channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    " Notifications",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("Channel for  notifications");
            notificationManager.createNotificationChannel(channel);
        }

        binding.buttonBigTextNotification.setOnClickListener(view -> {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.notification)
                    .setContentTitle("Big Text Notification")
                    .setContentText("Tap to view more details.")
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText("The Bajaj Dominar 400 is powered by 373." +
                                    "3cc BS6 engine which develops a power of 39." +
                                    "42 bhp and a torque of 35 Nm. " +
                                    "With both front and rear disc brakes, Bajaj Dominar 400 comes up with anti-locking braking system." +
                                    " This Dominar 400 bike weighs 193 kg and has a fuel tank capacity of 13 liters."))

                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(true);

            // NotificationManager to display the notification.
            notificationManager.notify(generateRandomId(), builder.build());
        });


        binding.buttonBigPictureNotification.setOnClickListener(view -> {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.notification)
                    .setContentTitle("Big Picture Notification")
                    .setContentText("Swipe down to see the image.")
                    .setStyle(new NotificationCompat.BigPictureStyle()
                            .bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.notification)))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            notificationManager.notify(generateRandomId(), builder.build());

        });

        binding.buttonInboxStyleNotification.setOnClickListener(view -> {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.notification)
                    .setContentTitle("Multiple Notifications")
                    .setContentText("You have new messages.")
                    .setStyle(new NotificationCompat.InboxStyle()
                            .addLine("Good Morrning")
                            .addLine("How are you")
                            .addLine("Where are you")
                            .setBigContentTitle("New Messages"))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            notificationManager.notify(generateRandomId(), builder.build());
        });


        binding.buttonProgressNotification.setOnClickListener(view -> {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.notification)
                    .setContentTitle("Download in Progress")
                    .setContentText("Downloading file...")
                    .setProgress(100, progress, false)
                    .setPriority(NotificationCompat.PRIORITY_LOW);

            notificationManager.notify(generateRandomId(), builder.build());
        });


    }

    //founction for random id generation
    private int generateRandomId() {
        return (int) (Math.random() * Integer.MAX_VALUE);
    }
}