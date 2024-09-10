package com.gadre.spotify.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gadre.spotify.Activity.MediaPlayerActivity;
import com.gadre.spotify.Activity.PlaySongActivity;
import com.gadre.spotify.Activity.ShowBookmarkActivity;
import com.gadre.spotify.R;
import com.gadre.spotify.RoomDatabase_Entity.BookmarkEntity;

import java.util.List;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.BookmarkViewHolder> {
    private final List<BookmarkEntity> bookmarkEntityList;
    private final OnSongClickListener onSongClickListener;

    public interface OnSongClickListener {
        void onSongClick(BookmarkEntity bookmarkEntity);
    }
    public BookmarkAdapter(List<BookmarkEntity> bookmarkEntityList, OnSongClickListener onSongClickListener) {
        this.bookmarkEntityList = bookmarkEntityList;
        this.onSongClickListener = onSongClickListener;
    }


    @NonNull
    @Override
    public BookmarkAdapter.BookmarkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_showbookmark, parent, false);
        return new BookmarkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookmarkAdapter.BookmarkViewHolder holder, int position) {
        BookmarkEntity bookmarkEntity = bookmarkEntityList.get(position);
        holder.bookmarkTextView.setText(bookmarkEntity.getTitle());
        holder.textViewDuration.setText(formatTime(bookmarkEntity.getBookmarkposition()));

        holder.bookmarkTextView.setOnClickListener(view -> {
            onSongClickListener.onSongClick(bookmarkEntity);
        });

    }

    @Override
    public int getItemCount() {
        return bookmarkEntityList.size();
    }

    public void updateBookmarks(List<BookmarkEntity> newBookmarks) {
        bookmarkEntityList.clear();
        bookmarkEntityList.addAll(newBookmarks);
        notifyDataSetChanged();
    }

    private String formatTime(int milliseconds) {
        int minutes = (milliseconds / 1000) / 60; //get 1 minute
        int seconds = (milliseconds / 1000) % 60;//get 1 sec
        return String.format("%02d:%02d", minutes, seconds);
    }


    public class BookmarkViewHolder extends RecyclerView.ViewHolder {

        TextView bookmarkTextView;
        TextView textViewDuration;

        public BookmarkViewHolder(@NonNull View itemView) {
            super(itemView);
            bookmarkTextView = itemView.findViewById(R.id.textviewShowBookmark);
            textViewDuration = itemView.findViewById(R.id.textViewDuration);
        }
    }
}
