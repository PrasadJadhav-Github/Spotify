package com.gadre.spotify.Activity;

import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlayMusicFromExternalDeviceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        MediaStoreManager mediaStoreManager = new MediaStoreManager(getContentResolver());
        externalsongList = mediaStoreManager.getAudioFiles();

        currentSongIndex = getIntent().getIntExtra("SONG_INDEX", 0);
       // Uri songUri = getIntent().getParcelableExtra("SONG_URI");
        //String songName = getIntent().getStringExtra("SONG_NAME");

        mediaPlayer = new MediaPlayer();

        if (currentSongIndex >= 0 && currentSongIndex < externalsongList.size()) {
            try {
                playCurrentSong();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            setUpButtons();
        }

        //     binding.songTitleTextView.setText(songName);
        //mediaPlayer.start();
        //setUpButtons();
    }


    private void playCurrentSong() throws IOException {
        if (currentSongIndex >= 0 && currentSongIndex < externalsongList.size()) {
//            if (mediaPlayer != null) {
//                //mediaPlayer.release();
//            }

            AudioFileDataClass currentSong = externalsongList.get(currentSongIndex);
//            mediaPlayer = MediaPlayer.create(this, currentSong.getUri());
            if (mediaPlayer != null) {
                mediaPlayer.reset();
                mediaPlayer.setAudioAttributes( new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build());
                mediaPlayer.setDataSource(this,currentSong.getUri());
                mediaPlayer.prepare();
                mediaPlayer.start();
                String songName = currentSong.getName();
                binding.songTitleTextView.setText(songName);
            }
        }
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
                        throw new RuntimeException(e);
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
                        throw new RuntimeException(e);
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
    }
}