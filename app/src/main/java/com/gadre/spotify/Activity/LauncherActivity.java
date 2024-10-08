package com.gadre.spotify.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.gadre.spotify.Adapter.LauncherActivityAdapter;
import com.gadre.spotify.DialogBox.LoadingDialog;
import com.gadre.spotify.databinding.ActivityLauncherBinding;

import java.util.ArrayList;

public class LauncherActivity extends AppCompatActivity {

    private ActivityLauncherBinding binding;
    private LoadingDialog loadingDialog;
    private LauncherActivityAdapter launcherActivityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize view binding
        binding = ActivityLauncherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        launcherActivityAdapter =new LauncherActivityAdapter(new ArrayList<>());
        binding.launcherActivityRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.launcherActivityRecyclerView.setAdapter(launcherActivityAdapter);



        loadingDialog = new LoadingDialog(this);
        loadingDialog.startAlertDialog();
        new android.os.Handler().postDelayed(() -> {
            binding.imageViewSpotifyList.setOnClickListener(v -> {
                Intent intent = new Intent(LauncherActivity.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(LauncherActivity.this, "Opening List of Songs", Toast.LENGTH_SHORT).show();
            });

            binding.imageViewPlaySong.setOnClickListener(v -> {
                Intent intent = new Intent(LauncherActivity.this, DisplaySongListActivity.class);
                startActivity(intent);
                Toast.makeText(LauncherActivity.this, "Opening  Songs ", Toast.LENGTH_SHORT).show();
            });

            binding.imageViewMediaPlayer.setOnClickListener(v -> {
                Intent intent = new Intent(LauncherActivity.this, FetchExternalSongsActivity.class);
                startActivity(intent);
                Toast.makeText(LauncherActivity.this, "Opening  Media Player ", Toast.LENGTH_SHORT).show();
            });

//            binding.imageViewBookmarkTab.setOnClickListener(view -> {
//                Fragment showBookmarkFragment = new ShowBookmarkListFragment();
//                FragmentManager fragmentManager = getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.fragment_container, showBookmarkFragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
//            });


            binding.imageViewSalesInformation.setOnClickListener(view -> {
                Intent intent = new Intent(LauncherActivity.this, SellsDetailsActivity.class);
                startActivity(intent);
                Toast.makeText(LauncherActivity.this, "Opening  Sales Details ", Toast.LENGTH_SHORT).show();
            });

            binding.imageViewFileManager.setOnClickListener(view -> {
                Intent intent = new Intent(LauncherActivity.this, FileManagerActivity.class);
                startActivity(intent);
                Toast.makeText(LauncherActivity.this, "Opening  File Manager ", Toast.LENGTH_SHORT).show();

            });

            binding.imageViewnotificationTab.setOnClickListener(view -> {
                Intent intent = new Intent(LauncherActivity.this, NotificationActivity.class);
                startActivity(intent);
                Toast.makeText(LauncherActivity.this, "Opening  Notification Tab ", Toast.LENGTH_SHORT).show();
            });


            binding.imageViewHighlightTab.setOnClickListener(view -> {
                Intent intent = new Intent(LauncherActivity.this, ShowHighlightActivity.class);
                startActivity(intent);
                Toast.makeText(LauncherActivity.this, "Opening  Highlight Tab ", Toast.LENGTH_SHORT).show();
            });

            binding.imageViewBookmarkTab.setOnClickListener(view -> {
                Intent intent = new Intent(LauncherActivity.this, ShowBookmarkActivity.class);
                startActivity(intent);
                Toast.makeText(LauncherActivity.this, "Opening  Bookmark Tab ", Toast.LENGTH_SHORT).show();
            });


            binding.imageViewDownload.setOnClickListener(view -> {
                Intent intent = new Intent(LauncherActivity.this, DownloadManagerActivity.class);
                startActivity(intent);
                Toast.makeText(LauncherActivity.this, "Opening  Download Manager ", Toast.LENGTH_SHORT).show();
            });

            binding.imageViewApiCall.setOnClickListener(view -> {
                Intent intent = new Intent(LauncherActivity.this, DisplayDataFromSwayamApilActivity.class);
                startActivity(intent);
                Toast.makeText(LauncherActivity.this, "Opening  Swyam ", Toast.LENGTH_SHORT).show();
            });

            binding.imageViewDateFormatTab.setOnClickListener(view -> {
                Intent intent = new Intent(LauncherActivity.this, DateFormatActivity.class);
                startActivity(intent);
                Toast.makeText(LauncherActivity.this, "Opening  Notification Tab ", Toast.LENGTH_SHORT).show();
            });
            loadingDialog.closeAlertDialog();
        }, 2000);


    }
}
