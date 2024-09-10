package com.gadre.spotify.Activity;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.gadre.spotify.Adapter.BookmarkAdapter;
import com.gadre.spotify.RoomDatabase_DAO.BookmarkSongDAO;
import com.gadre.spotify.RoomDatabase_Database.BookmarkDatabase;
import com.gadre.spotify.RoomDatabase_Entity.BookmarkEntity;
import com.gadre.spotify.databinding.ActivityShowBookmarkBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ShowBookmarkActivity extends AppCompatActivity implements BookmarkAdapter.OnSongClickListener {

    private ActivityShowBookmarkBinding binding;
    private BookmarkSongDAO bookmarkDAO;
    private BookmarkAdapter bookmarkAdapter;
    private ExecutorService executorService;
    private Handler mainHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShowBookmarkBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize the database and DAO
        BookmarkDatabase bookmarkDatabase = BookmarkDatabase.getDatabase(this);
        bookmarkDAO = bookmarkDatabase.bookmarkDAO();

        // Initialize RecyclerView and Adapter
        bookmarkAdapter = new BookmarkAdapter(new ArrayList<>(),this);
        binding.showBookmarkRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.showBookmarkRecyclerView.setAdapter(bookmarkAdapter);

        executorService = Executors.newSingleThreadExecutor();
        mainHandler = new Handler(Looper.getMainLooper());

        fetchAndDisplayBookmarks();


    }

    //method to fetch and display the bookmark songs
    private void fetchAndDisplayBookmarks() {
        executorService.execute(() -> {
                // Fetch data from the database
                List<BookmarkEntity> bookmarks = bookmarkDAO.getAllSongs();
                mainHandler.post(() -> {
                    if (bookmarks != null && !bookmarks.isEmpty()) {
                        bookmarkAdapter.updateBookmarks(bookmarks);
                    } else {
                        Log.d(TAG, "No bookmarks found.");
                    }
                });
        });
    }


    @Override
    public void onSongClick(BookmarkEntity bookmarkEntity) {
        Intent intent = new Intent(ShowBookmarkActivity.this, MediaPlayerActivity.class);
        intent.putExtra("bookmark_name", bookmarkEntity.getTitle());
        intent.putExtra("bookmark_point", bookmarkEntity.getBookmarkposition());
        startActivity(intent);
    }
}



