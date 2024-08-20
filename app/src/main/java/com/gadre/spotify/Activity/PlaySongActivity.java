package com.gadre.spotify.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gadre.spotify.Adapter.SongAdapter;
import com.gadre.spotify.ModelClass.MusicPlayerDataClass;
import com.gadre.spotify.OtherClasses.SongsUtil;
import com.gadre.spotify.R;
import com.gadre.spotify.databinding.ActivityPlaySongBinding;

import java.util.List;

public class PlaySongActivity extends AppCompatActivity implements SongAdapter.OnSongClickListener {

    private ActivityPlaySongBinding binding;
    private RecyclerView recyclerView;
    private SongAdapter songAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlaySongBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.songsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Get the list of song names
        List<MusicPlayerDataClass> songList = SongsUtil.getRawSongList(this);

        //  set the adapter
        songAdapter = new SongAdapter(songList,this);
        recyclerView.setAdapter(songAdapter);

    }
    @Override
    public void onSongClick(MusicPlayerDataClass songData) {
        Intent intent = new Intent(PlaySongActivity.this, MediaPlayerActivity.class);
        intent.putExtra("SONG_ID", songData.getId());
        intent.putExtra("SONG_NAME", songData.getName());
        startActivity(intent);
    }
}