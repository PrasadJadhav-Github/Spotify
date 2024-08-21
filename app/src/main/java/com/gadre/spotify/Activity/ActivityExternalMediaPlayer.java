package com.gadre.spotify.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.gadre.spotify.Adapter.ExternalMediaPlayerAdapter;
import com.gadre.spotify.ModelClass.AudioFileDataClass;
import com.gadre.spotify.OtherClasses.MediaStoreManager;
import com.gadre.spotify.databinding.ActivityExternalMediaPlayerBinding;

import java.util.List;

public class ActivityExternalMediaPlayer extends AppCompatActivity implements ExternalMediaPlayerAdapter.OnExternalSongClickListener {

    private ActivityExternalMediaPlayerBinding binding;
    private MediaStoreManager mediaStoreManager;
 //   private RecyclerView recyclerView;
    private ExternalMediaPlayerAdapter audioFileAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityExternalMediaPlayerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        mediaStoreManager = new MediaStoreManager(getContentResolver());
        //initialize recyclerview
        binding.externalMediaPlayerRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Check for permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
        } else {
            fetchAudioFiles();
        }
    }

    //  fetch audio files
    private void fetchAudioFiles() {
        List<AudioFileDataClass> audioFiles = mediaStoreManager.getAudioFiles();
        audioFileAdapter = new ExternalMediaPlayerAdapter(audioFiles,this);
       binding.externalMediaPlayerRecyclerView .setAdapter(audioFileAdapter);
    }

    // Register for permission result
    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    fetchAudioFiles();
                } else {
                    Toast.makeText(this, "Permission denied to read external storage.", Toast.LENGTH_SHORT).show();
                }
            });


    @Override
    public void onSongClick(AudioFileDataClass audioData) {

        Intent intent = new Intent(ActivityExternalMediaPlayer.this, PlayMusicFromExternalDevice.class);
        intent.putExtra("SONG_URI",audioData.getUri() );
        intent.putExtra("SONG_NAME", audioData.getName());
        startActivity(intent);
    }
}
