package com.gadre.spotify.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.gadre.spotify.OtherClasses.SongsUtil;
import com.gadre.spotify.R;
import com.gadre.spotify.databinding.ActivityLauncherBinding;
import com.gadre.spotify.databinding.ActivityPlaySongBinding;

import java.util.List;

public class PlaySongActivity extends AppCompatActivity {

    private ActivityPlaySongBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlaySongBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



       // Get the list of song names
        List<String> songList = SongsUtil.getRawSongList(this);

        // Find the TextView where you want to display the songs
        TextView songTextView = binding.songtextview;

        // Create a StringBuilder to format the song list
        StringBuilder songs = new StringBuilder();
        for (String song : songList) {
            songs.append(song).append("\n");
        }
        // Set the formatted song list to the TextView
        songTextView.setText(songs.toString());

    }
}