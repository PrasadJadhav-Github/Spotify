package com.gadre.spotify.Activity;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.gadre.spotify.Adapter.BookmarkAdapter;
import com.gadre.spotify.R;
import com.gadre.spotify.RoomDatabase_DAO.BookmarkDAO;
import com.gadre.spotify.RoomDatabase_Database.BookmarkDatabase;
import com.gadre.spotify.RoomDatabase_Entity.BookmarkEntity;
import com.gadre.spotify.databinding.ActivityShowBookmarkBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ShowBookmarkActivity extends AppCompatActivity {

    private ActivityShowBookmarkBinding binding;
    private BookmarkDAO bookmarkDAO;
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
        bookmarkAdapter = new BookmarkAdapter(new ArrayList<>());
        binding.showBookmarkRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.showBookmarkRecyclerView.setAdapter(bookmarkAdapter);

        executorService = Executors.newSingleThreadExecutor();
        mainHandler = new Handler(Looper.getMainLooper());

        fetchAndDisplayBookmarks();


    }

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
}



