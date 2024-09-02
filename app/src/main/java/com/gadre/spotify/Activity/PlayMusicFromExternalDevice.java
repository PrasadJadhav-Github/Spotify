package com.gadre.spotify.Activity;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import com.gadre.spotify.ModelClass.AudioFileDataClass;
import com.gadre.spotify.OtherClasses.MediaStoreManager;
import com.gadre.spotify.R;
import com.gadre.spotify.databinding.ActivityPlayMusicFromExternalDeviceBinding;
import java.io.IOException;
import java.util.List;

public class PlayMusicFromExternalDevice extends AppCompatActivity {
    private ActivityPlayMusicFromExternalDeviceBinding binding;
    private MediaPlayer mediaPlayer;
    private List<AudioFileDataClass> externalsongList;
    private int currentSongIndex;
    private static final String CHANNEL_ID = "music_channel_id";
    private NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlayMusicFromExternalDeviceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        MediaStoreManager mediaStoreManager = new MediaStoreManager(getContentResolver());
        externalsongList = mediaStoreManager.getAudioFiles();

        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        //Notification Cannel for manage notification
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {//This line checks if the Android device's API level
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Play Music",
                    NotificationManager.IMPORTANCE_LOW
            );
            channel.setDescription("Channel for notifications");
            notificationManager.createNotificationChannel(channel);
        }

        currentSongIndex = getIntent().getIntExtra("SONG_INDEX", 0);
        mediaPlayer = new MediaPlayer();

        if (currentSongIndex >= 0 && currentSongIndex < externalsongList.size()) {
            try {
                playCurrentSong();
            } catch (IOException e) {
                e.printStackTrace();
            }
            setUpButtons();
        }
    }

    private void playCurrentSong() throws IOException {
        if (currentSongIndex >= 0 && currentSongIndex < externalsongList.size()) {
            AudioFileDataClass currentSong = externalsongList.get(currentSongIndex);

            if (mediaPlayer != null) {
                mediaPlayer.reset();
                mediaPlayer.setAudioAttributes(new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build());
                mediaPlayer.setDataSource(this, currentSong.getUri());
                mediaPlayer.prepare();
                mediaPlayer.start();
                String songName = currentSong.getName();
                binding.songTitleTextView.setText(songName);

                showNotification(songName);
            }
        }
    }


     //display notification when song is playing
    private void showNotification(String songTitle) {
        PendingIntent prevIntent = PendingIntent.getBroadcast(this, 0, new Intent(), PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent playPauseIntent = PendingIntent.getBroadcast(this, 1, new Intent(), PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent nextIntent = PendingIntent.getBroadcast(this, 2, new Intent(), PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.musicnode)
                .setContentTitle("Playing Music")
                .setContentText(songTitle)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setOngoing(true)
                .setAutoCancel(false)
                .addAction(R.drawable.previous, "Previous", prevIntent)
                .addAction(R.drawable.pause, "Pause/Play", playPauseIntent)
                .addAction(R.drawable.next, "Next", nextIntent);

        notificationManager.notify(1, builder.build());
    }


    private void setUpButtons() {
        ImageButton playPauseButton = binding.externalDevicePlayPauseButton;
        ImageButton nextSongButton = binding.externalDeviceNextButton;
        ImageButton prevousSongButton = binding.externalDevicePreviousButton;

        playPauseButton.setOnClickListener(view -> {
            if (mediaPlayer != null) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    playPauseButton.setImageResource(android.R.drawable.ic_media_play);
                } else {
                    mediaPlayer.start();
                    playPauseButton.setImageResource(android.R.drawable.ic_media_pause);
                }
            }
        });

        nextSongButton.setOnClickListener(view -> {
            if (externalsongList != null && !externalsongList.isEmpty()) {
                if (currentSongIndex < externalsongList.size() - 1) {
                    currentSongIndex++;
                    try {
                        playCurrentSong();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        prevousSongButton.setOnClickListener(view -> {
            if (externalsongList != null && !externalsongList.isEmpty()) {
                if (currentSongIndex > 0) {
                    currentSongIndex--;
                    try {
                        playCurrentSong();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }



    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        notificationManager.cancel(1);
    }
}
