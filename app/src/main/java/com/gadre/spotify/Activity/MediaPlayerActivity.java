package com.gadre.spotify.Activity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Toast;
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

    private void setUpSeekBar() {
        SeekBar seekBar = binding.mediaPlayerseekBar;
        handler = new Handler();

        // Set the max value for the SeekBar
        if (mediaPlayer != null) {
            seekBar.setMax(mediaPlayer.getDuration());
        }

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

        // Runnable to update the SeekBar position
        updateSeekBar = new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    int currentPosition = mediaPlayer.getCurrentPosition();
                    seekBar.setProgress(currentPosition);
                    handler.postDelayed(this, 1000);
                }
            }
        };        handler.post(updateSeekBar);
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
