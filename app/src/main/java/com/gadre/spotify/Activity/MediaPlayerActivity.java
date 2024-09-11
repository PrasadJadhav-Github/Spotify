package com.gadre.spotify.Activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.gadre.spotify.ModelClass.MusicPlayerDataClass;
import com.gadre.spotify.OtherClasses.SongsUtil;
import com.gadre.spotify.R;
import com.gadre.spotify.RoomDatabase_Database.BookmarkDatabase;
import com.gadre.spotify.RoomDatabase_Entity.BookmarkEntity;
import com.gadre.spotify.databinding.ActivityMediaPlayerBinding;

import java.util.List;
import java.util.concurrent.Executors;

public class MediaPlayerActivity extends AppCompatActivity {

    private ActivityMediaPlayerBinding binding;
    private MediaPlayer mediaPlayer;
    private List<MusicPlayerDataClass> songList;
    private int currentIndex;
    private Handler handler;
    private Runnable updateSeekBar;
    private BookmarkDatabase bookmarkDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMediaPlayerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize the database
        bookmarkDatabase = BookmarkDatabase.getDatabase(this);

        // Retrieve data from the intent
        Intent intent = getIntent();
        String bookmarkName = intent.getStringExtra("bookmark_name");
        int bookmarkPosition = intent.getIntExtra("bookmark_point", 0);

        // Get the list of songs
        songList = SongsUtil.getRawSongList(this);

        // Find the current song index
        currentIndex = findSong(bookmarkName);

        if (currentIndex >= 0 && currentIndex < songList.size()) {
            playCurrentSong(bookmarkPosition);
            setUpButtons();
            setUpSeekBar();
            setUpBookmarkClickListener();
        }
    }

    //search for a specific song
    private int findSong(String songName) {
        for (int i = 0; i < songList.size(); i++) {
            if (songList.get(i).getName().equals(songName)) {
                return i;
            }
        }
        return 0;
    }
    //int i = 0 represents the current index in the songList
    // i++ This moves to the next song in the list.

    private void playCurrentSong(int startPosition) {
        if (currentIndex >= 0 && currentIndex < songList.size()) {
            if (mediaPlayer != null) {
                mediaPlayer.release();
            }

            MusicPlayerDataClass currentSong = songList.get(currentIndex);
            mediaPlayer = MediaPlayer.create(this, currentSong.getId());

            if (mediaPlayer != null) {
                mediaPlayer.start();
                binding.songTitleTextView.setText(currentSong.getName());
                mediaPlayer.seekTo(startPosition);
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
                playCurrentSong(0);
            }
        });

        nextButton.setOnClickListener(view -> {
            if (currentIndex < songList.size() - 1) {
                currentIndex++;
                playCurrentSong(0);
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

        handler.post(updateSeekBar);
    }

    private void setUpBookmarkClickListener() {
        binding.imageViewBookMark.setOnClickListener(view -> {
            if (mediaPlayer != null) {
                MusicPlayerDataClass currentSong = songList.get(currentIndex);
                String title = currentSong.getName();
                int bookmarkPosition = mediaPlayer.getCurrentPosition();

                BookmarkEntity bookmarkEntity = new BookmarkEntity(title, bookmarkPosition);
                Log.d("Bookmark", "Inserting bookmark with title: " + title + " at position: " + bookmarkPosition);
                runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Bookmark added: " + title, Toast.LENGTH_SHORT).show());
                Executors.newSingleThreadExecutor().execute(() -> {
                    bookmarkDatabase.bookmarkDAO().insertBookmarkSong(bookmarkEntity);
                    Log.d("Bookmark", "Bookmark inserted into database");
                });
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
            mediaPlayer = null;
        }
        if (handler != null) {
            handler.removeCallbacks(updateSeekBar);
        }
    }
}
