package com.gadre.spotify.RoomDatabase_DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.gadre.spotify.RoomDatabase_Entity.BookmarkEntity;
import com.gadre.spotify.RoomDatabase_Entity.HighlightSongEntity;

import java.util.List;

@Dao
public interface HighlightSongDAO {

    @Insert
    void insertHighlightPart(HighlightSongEntity highlightSongEntity);

    @Query("SELECT * FROM Highlight")
    List<HighlightSongEntity> getAllHighlightSong();




}
