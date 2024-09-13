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
import android.os.Handler;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.gadre.spotify.ModelClass.AudioFileDataClass;
import com.gadre.spotify.OtherClasses.MediaStoreManager;
import com.gadre.spotify.R;
import com.gadre.spotify.RoomDatabase_Database.BookmarkDatabase;
import com.gadre.spotify.RoomDatabase_Entity.HighlightSongEntity;
import com.gadre.spotify.databinding.ActivityPlayMusicFromExternalDeviceBinding;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executors;

public class PlayMusicFromExternalDevice extends AppCompatActivity {
    private ActivityPlayMusicFromExternalDeviceBinding binding;
    private MediaPlayer mediaPlayer;
    private List<AudioFileDataClass> externalsongList;
    private int currentSongIndex;
    private static final String CHANNEL_ID = "music_channel_id";
    private NotificationManager notificationManager;
    private Handler handler;
    private Runnable updateSeekBar;
    private BookmarkDatabase bookmarkDatabase;
    private int highlightStart = 0;
    private int highlightEnd = currentSongIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlayMusicFromExternalDeviceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        MediaStoreManager mediaStoreManager = new MediaStoreManager(getContentResolver());
        externalsongList = mediaStoreManager.getAudioFiles();
        bookmarkDatabase = BookmarkDatabase.getDatabase(this);
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Play Music",
                    NotificationManager.IMPORTANCE_LOW
            );
            channel.setDescription("Channel for notifications");
            notificationManager.createNotificationChannel(channel);
        }

        Intent intent = getIntent();
        if (intent.hasExtra("SONG_POSITION")) {
            currentSongIndex = intent.getIntExtra("SONG_POSITION", 0);
        } else if (intent.hasExtra("name")) {
            String songName = intent.getStringExtra("name");
            highlightStart = intent.getIntExtra("startPoint", 0);
            highlightEnd = intent.getIntExtra("endPoint", 0);
            currentSongIndex = findSong(songName);

        }
        mediaPlayer = new MediaPlayer();

        if (currentSongIndex >= 0 && currentSongIndex < externalsongList.size()) {
            try {
                playCurrentSong(highlightStart, highlightEnd);
            } catch (IOException e) {
                e.printStackTrace();
            }
            setUpButtons();
            setUpSeekBar();
            buttonStartHighlight();
        } else {
            Toast.makeText(this, "Invalid song index", Toast.LENGTH_SHORT).show();
        }
    }

    private int findSong(String songName) {
        for (int i = 0; i < externalsongList.size(); i++) {
            if (externalsongList.get(i).getName().equals(songName)) {
                return i;
            }
        }
        return -1; // Return -1 if song is not found
    }
    //int i = 0 represents the current index in the songList
    // i++ This moves to the next song in the list.


    private void playCurrentSong(int highlightStart, int highlightEnd) throws IOException {
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

                // Set the start position
                //This is used from a specific point if a highlight has been set.
                if (highlightStart > 0) {
                    mediaPlayer.seekTo(highlightStart);
                }
                mediaPlayer.start();
                binding.songTitleTextView.setText(currentSong.getName());

                if (highlightEnd > 0) {
                    binding.externalMediaPlayerseekBar.setMax(mediaPlayer.getDuration());

                    binding.endTimeTextView.setText(formatTime(highlightEnd));

                }

                showNotification(currentSong.getName());
                updateTimes();
            }
        }
    }

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
        ImageButton previousSongButton = binding.externalDevicePreviousButton;

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
                        playCurrentSong(highlightStart, highlightEnd);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        previousSongButton.setOnClickListener(view -> {
            if (externalsongList != null && !externalsongList.isEmpty()) {
                if (currentSongIndex > 0) {
                    currentSongIndex--;
                    try {
                        playCurrentSong(highlightStart, highlightEnd);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void buttonStartHighlight() {
        binding.imageViewStartHighlight.setOnClickListener(view -> {
            if (mediaPlayer != null) {
                if (highlightStart == 0) {
                    highlightStart = mediaPlayer.getCurrentPosition();
                    Toast.makeText(this, "Starting highlight at " + formatTime(highlightStart), Toast.LENGTH_SHORT).show();
                } else {
                    highlightEnd = mediaPlayer.getCurrentPosition();
                    Toast.makeText(this, "Highlight ended at " + formatTime(highlightEnd), Toast.LENGTH_SHORT).show();
                    saveHighlightInDatabase();
                    highlightStart = 0;
                    highlightEnd = 0;
                }
            }
        });
    }

    private void saveHighlightInDatabase() {
        if (highlightStart != 0 && highlightEnd != 0) {
            AudioFileDataClass currentSong = externalsongList.get(currentSongIndex);
            String title = currentSong.getName();
            HighlightSongEntity highlightSongEntity = new HighlightSongEntity(title, highlightStart, highlightEnd);
            Log.d("Highlight", "Inserting Highlight with title: " + title + " at start point: " + highlightStart + " end at " + highlightEnd);
            Executors.newSingleThreadExecutor().execute(() -> {
                bookmarkDatabase.highlightSongDAO().insertHighlightPart(highlightSongEntity);
                Log.d("Highlight", "Highlight inserted into database");
            });
        } else {
            Log.d("Highlight", "Fail to insert into database");
        }
    }

    private void updateTimes() {
        if (mediaPlayer != null) {
            int currentPosition = mediaPlayer.getCurrentPosition();
            int duration = mediaPlayer.getDuration();

            binding.startTimeTextView.setText(formatTime(currentPosition));
            binding.endTimeTextView.setText(formatTime(duration));
        }
    }

    private String formatTime(int milliseconds) {
        int minutes = (milliseconds / 1000) / 60;
        int seconds = (milliseconds / 1000) % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    private void setUpSeekBar() {
        SeekBar seekBar = binding.externalMediaPlayerseekBar;
        handler = new Handler();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser && mediaPlayer != null) {

                    if (progress >= highlightEnd && highlightEnd>0) {
                        mediaPlayer.seekTo(highlightEnd);
                    } else {
                        mediaPlayer.seekTo(progress);
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                }
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (mediaPlayer != null) {
                    if (seekBar.getProgress() >= highlightEnd && highlightEnd >0) {
                        mediaPlayer.seekTo(highlightEnd);
                        mediaPlayer.pause();
                    } else {
                        mediaPlayer.seekTo(seekBar.getProgress());
                        mediaPlayer.start();
                    }
                }
            }
        });

        updateSeekBar = new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    int currentPosition = mediaPlayer.getCurrentPosition();
                    if (currentPosition >= highlightEnd && highlightEnd >0) {
                        mediaPlayer.seekTo(highlightEnd);
                        mediaPlayer.pause();
                    }
                    binding.externalMediaPlayerseekBar.setProgress(currentPosition);
                    updateTimes();
                    handler.postDelayed(this, 1000);
                }
            }
        };
        handler.post(updateSeekBar);
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
            mediaPlayer = null;
        }
        if (handler != null) {
            handler.removeCallbacks(updateSeekBar);
        }
    }
}
