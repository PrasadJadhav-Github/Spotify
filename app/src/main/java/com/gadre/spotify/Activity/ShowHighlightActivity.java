package com.gadre.spotify.Activity;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.gadre.spotify.Adapter.BookmarkAdapter;
import com.gadre.spotify.Adapter.HighlightAdapter;
import com.gadre.spotify.R;
import com.gadre.spotify.RoomDatabase_DAO.HighlightSongDAO;
import com.gadre.spotify.RoomDatabase_Database.BookmarkDatabase;
import com.gadre.spotify.RoomDatabase_Entity.BookmarkEntity;
import com.gadre.spotify.RoomDatabase_Entity.HighlightSongEntity;
import com.gadre.spotify.databinding.ActivityShowHighlightBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ShowHighlightActivity extends AppCompatActivity implements HighlightAdapter.OnSongClickListener {

    private ActivityShowHighlightBinding binding;
    private HighlightSongDAO highlightSongDAO;
    private HighlightAdapter highlightAdapter;
    private ExecutorService executorService;
    private Handler mainHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityShowHighlightBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BookmarkDatabase bookmarkDatabase=BookmarkDatabase.getDatabase(this);
        highlightSongDAO=bookmarkDatabase.highlightSongDAO();

        // Initialize RecyclerView and Adapter
        highlightAdapter = new HighlightAdapter( this);
        binding.showHighlightRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.showHighlightRecyclerView.setAdapter(highlightAdapter);

        executorService = Executors.newSingleThreadExecutor();
        mainHandler = new Handler(Looper.getMainLooper());

        fetchAndDisplayHighlight();

    }

    private  void  fetchAndDisplayHighlight(){
        executorService.execute(() -> {
            // Fetch data from the database
            List<HighlightSongEntity> highlightSongEntityList = highlightSongDAO.getAllHighlightSong();
            mainHandler.post(() -> {
                if (highlightSongEntityList != null && !highlightSongEntityList.isEmpty()) {
                    highlightAdapter.updateHighlights(highlightSongEntityList);
                } else {
                    Log.d(TAG, "No highlight found.");
                }
            });
        });

    }

    @Override
    public void onSongClick(HighlightSongEntity highlightSongEntity) {
        Intent intent = new Intent(ShowHighlightActivity.this, PlayMusicFromExternalDevice.class);
        intent.putExtra("name", highlightSongEntity.getTitle());
        intent.putExtra("startPoint", highlightSongEntity.getStartTime());
        intent.putExtra("endPoint", highlightSongEntity.getEndTime());
        startActivity(intent);

        Log.d("PlayMusic", "Song Name: ");
    }


}