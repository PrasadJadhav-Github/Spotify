package com.gadre.spotify.Activity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.gadre.spotify.R;
import com.gadre.spotify.databinding.ActivityMediaPlayerBinding;

public class MediaPlayerActivity extends AppCompatActivity {

    private ActivityMediaPlayerBinding binding;
    private MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMediaPlayerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        int songId = getIntent().getIntExtra("SONG_ID", -1);
        String songName = getIntent().getStringExtra("SONG_NAME");

        if (songId != -1) {
            mediaPlayer = MediaPlayer.create(this, songId);
            if (mediaPlayer != null) {
                setUpButtons();
                mediaPlayer.start();

                Toast.makeText(this, songName != null ? "Playing: " + songName : "No song selected", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "No song selected", Toast.LENGTH_SHORT).show();
            }


        }
    }

    private void setUpButtons() {
        ImageButton playPauseButton = binding.playPauseButton;

        playPauseButton.setOnClickListener(v -> {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                playPauseButton.setImageResource(android.R.drawable.ic_media_play);
            } else {
                mediaPlayer.start();
                playPauseButton.setImageResource(android.R.drawable.ic_media_pause);
            }
        });
    }
}

