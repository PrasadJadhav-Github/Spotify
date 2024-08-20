package com.gadre.spotify.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gadre.spotify.ModelClass.MusicPlayerDataClass;
import com.gadre.spotify.R;

import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder> {

    private final List<MusicPlayerDataClass> songList;
    private final OnSongClickListener onSongClickListener;

    // Interface for handling item clicks
    public interface OnSongClickListener {
        void onSongClick(MusicPlayerDataClass songData);
    }

    public SongAdapter(List<MusicPlayerDataClass> songList, OnSongClickListener onSongClickListener) {
        this.songList = songList;
        this.onSongClickListener = onSongClickListener;
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_songs, parent, false);
        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
        MusicPlayerDataClass songData = songList.get(position);
        holder.songNameTextView.setText(songData.getName());

        holder.songNameTextView.setOnClickListener(v -> {
                onSongClickListener.onSongClick(songData);
        });
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    public static class SongViewHolder extends RecyclerView.ViewHolder {
        TextView songNameTextView;

        public SongViewHolder(@NonNull View itemView) {
            super(itemView);
            songNameTextView = itemView.findViewById(R.id.songNameTextView);
        }
    }
}
