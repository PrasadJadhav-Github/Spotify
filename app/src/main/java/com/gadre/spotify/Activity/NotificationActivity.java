package com.gadre.spotify.Activity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.session.MediaButtonReceiver;
import androidx.media3.session.MediaSession;
import androidx.media3.session.MediaStyleNotificationHelper;
import androidx.media3.session.legacy.MediaSessionCompat;

import com.gadre.spotify.R;
import com.gadre.spotify.databinding.ActivityNotificationBinding;

public class NotificationActivity extends AppCompatActivity {

    private ActivityNotificationBinding binding;
    private static final String CHANNEL_ID = "channel_id";
    private NotificationManager notificationManager;
    private final int progress = 0;


    @UnstableApi
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
                    "Notifications",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("Channel for notifications");
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
                            .addLine("Good Morning")
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

    // Function for random id generation
    private int generateRandomId() {
        return (int) (Math.random() * Integer.MAX_VALUE);
    }


}
