package com.gadre.spotify.Activity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageButton;
import android.widget.SeekBar;
import androidx.appcompat.app.AppCompatActivity;
import com.gadre.spotify.ModelClass.MusicPlayerDataClass;
import com.gadre.spotify.OtherClasses.SongsUtil;
import com.gadre.spotify.R;
import com.gadre.spotify.databinding.ActivityMediaPlayerBinding;

import java.util.List;

public class MediaPlayerActivity extends AppCompatActivity {

    private ActivityMediaPlayerBinding binding;
    private MediaPlayer mediaPlayer;
    private List<MusicPlayerDataClass> songList;
    private int currentIndex;
    private Handler handler;
    private Runnable updateSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMediaPlayerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Get the list of songs from SongsUtil
        songList = SongsUtil.getRawSongList(this);
        currentIndex = getIntent().getIntExtra("SONG_POSITION", 0);

        if (currentIndex >= 0 && currentIndex < songList.size()) {
            playCurrentSong();
            setUpButtons();
            setUpSeekBar();
        }
    }

    private void playCurrentSong() {
        if (currentIndex >= 0 && currentIndex < songList.size()) {
            if (mediaPlayer != null) {
                mediaPlayer.release();
            }

            MusicPlayerDataClass currentSong = songList.get(currentIndex);
            mediaPlayer = MediaPlayer.create(this, currentSong.getId());

            if (mediaPlayer != null) {
                mediaPlayer.start();
                binding.songTitleTextView.setText(currentSong.getName());

                // Update SeekBar max value and time display after mediaPlayer starts
                SeekBar seekBar = binding.mediaPlayerseekBar;
                seekBar.setMax(mediaPlayer.getDuration());
                updateTimes();
            }
        }
    }

    private void setUpButtons() {
        ImageButton playPauseButton = binding.playPauseButton;
        ImageButton previousButton = binding.previousButton;
        ImageButton nextButton = binding.nextButton;

        playPauseButton.setOnClickListener(v -> {
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

        previousButton.setOnClickListener(view -> {
            if (currentIndex > 0) {
                currentIndex--;
                playCurrentSong();
            }
        });

        nextButton.setOnClickListener(view -> {
            if (currentIndex < songList.size() - 1) {
                currentIndex++;
                playCurrentSong();
            }
        });
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
        SeekBar seekBar = binding.mediaPlayerseekBar;
        handler = new Handler();

        // Runnable to update the SeekBar and time display
        updateSeekBar = new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    int currentPosition = mediaPlayer.getCurrentPosition();
                    seekBar.setProgress(currentPosition);
                    updateTimes();
                    handler.postDelayed(this, 1000);
                }
            }
        };

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser && mediaPlayer != null) {
                    mediaPlayer.seekTo(progress);
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
                    mediaPlayer.seekTo(seekBar.getProgress());
                    mediaPlayer.start();
                }
            }
        });

        // Start updating the SeekBar and time display
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
