package com.gadre.spotify.RoomDatabase_Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.gadre.spotify.RoomDatabase_DAO.BookmarkSongDAO;
import com.gadre.spotify.RoomDatabase_DAO.HighlightSongDAO;
import com.gadre.spotify.RoomDatabase_Entity.BookmarkEntity;
import com.gadre.spotify.RoomDatabase_Entity.HighlightSongEntity;

@Database(entities = {BookmarkEntity.class,HighlightSongEntity.class}, version = 2)
public abstract class BookmarkDatabase extends RoomDatabase {
    public abstract BookmarkSongDAO bookmarkDAO();
    public  abstract HighlightSongDAO highlightSongDAO();

    private static final String DB_NAME = "Bookmark";
    // object of current class
    private static BookmarkDatabase instance;
    //synchronized method for single entry at a time
    public static BookmarkDatabase getDatabase(final Context context) {
        if (instance == null) {
            synchronized (BookmarkDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                                    BookmarkDatabase.class, DB_NAME)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return instance;
    }

}


