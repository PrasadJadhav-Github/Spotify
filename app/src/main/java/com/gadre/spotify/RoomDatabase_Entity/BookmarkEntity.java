package com.gadre.spotify.RoomDatabase_Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Bookmark")
public class BookmarkEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "bookmarkposition")
    private int bookmarkposition;

    public BookmarkEntity(String title, int bookmarkposition) {
        this.title = title;
        this.bookmarkposition = bookmarkposition;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getBookmarkposition() {
        return bookmarkposition;
    }

    public void setBookmarkposition(int bookmarkposition) {
        this.bookmarkposition = bookmarkposition;
    }
}
