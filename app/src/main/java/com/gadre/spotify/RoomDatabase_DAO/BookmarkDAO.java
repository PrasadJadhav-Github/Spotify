package com.gadre.spotify.RoomDatabase_DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.gadre.spotify.RoomDatabase_Entity.BookmarkEntity;

import java.util.List;


@Dao
public interface BookmarkDAO {


    @Insert
    void insertBookmarkSong(BookmarkEntity bookmark);

    @Query("SELECT * FROM Bookmark")
     List<BookmarkEntity> getAllSongs();


}
