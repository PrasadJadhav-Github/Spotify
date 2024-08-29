package com.gadre.spotify.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
            Toast.makeText(LauncherActivity.this, "Opening  Songs ", Toast.LENGTH_SHORT).show();
        });

        binding.imageViewMediaPlayer.setOnClickListener(v -> {
            Intent intent = new Intent(LauncherActivity.this, ActivityExternalMediaPlayer.class);
            startActivity(intent);
            Toast.makeText(LauncherActivity.this, "Opening  Media Player ", Toast.LENGTH_SHORT).show();
        });

//        binding.imageViewFileManager.setOnClickListener(view -> {
//
//            Fragment fileManagerFragment = new FileManagerFragment();
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            fragmentTransaction.replace(R.id.fileManagerFragmentContainer, fileManagerFragment);
//            fragmentTransaction.addToBackStack(null);
//            fragmentTransaction.commit();
//
//        });

        binding.imageViewSalesInformation.setOnClickListener(view -> {
            Intent intent = new Intent(LauncherActivity.this, SellsDetailsActivity.class);
            startActivity(intent);
            Toast.makeText(LauncherActivity.this, "Opening  Sales Details ", Toast.LENGTH_SHORT).show();
        });

        binding.imageViewFileManager.setOnClickListener(view -> {
            Intent intent =new Intent(LauncherActivity.this,FileManagerActivity.class);
            startActivity(intent);
            Toast.makeText(LauncherActivity.this, "Opening  File Manager ", Toast.LENGTH_SHORT).show();

        });

        binding.imageViewnotificationTab.setOnClickListener(view -> {
            Intent intent =new Intent(LauncherActivity.this,NotificationActivity.class);
            startActivity(intent);
            Toast.makeText(LauncherActivity.this, "Opening  Notification Tab ", Toast.LENGTH_SHORT).show();
        });


    }
}
