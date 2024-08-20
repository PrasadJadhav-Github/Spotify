package com.gadre.spotify.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.gadre.spotify.R;
import com.gadre.spotify.databinding.ActivityLauncherBinding;

public class LauncherActivity extends AppCompatActivity {

    private ActivityLauncherBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize view binding
        binding = ActivityLauncherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.imageViewSpotifyList.setOnClickListener(v -> {
            Intent intent = new Intent(LauncherActivity.this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(LauncherActivity.this, "Opening List of Songs", Toast.LENGTH_SHORT).show();
        });

        binding.imageViewPlaySong.setOnClickListener(v -> {
            Intent intent = new Intent(LauncherActivity.this, PlaySongActivity.class);
            startActivity(intent);
            Toast.makeText(LauncherActivity.this, "Opening Play Song Activity", Toast.LENGTH_SHORT).show();
        });


    }
}
