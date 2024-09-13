package com.gadre.spotify.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gadre.spotify.R;
import com.gadre.spotify.RoomDatabase_Entity.BookmarkEntity;
import com.gadre.spotify.RoomDatabase_Entity.HighlightSongEntity;

import java.util.ArrayList;
import java.util.List;

public class HighlightAdapter extends RecyclerView.Adapter<HighlightAdapter.HighlightViewHolder> {
    List<HighlightSongEntity> highlightSongEntityList;
    private final OnSongClickListener onSongClickListener;

    public interface OnSongClickListener {
        void onSongClick(HighlightSongEntity highlightSongEntity);
    }

    public HighlightAdapter(OnSongClickListener onSongClickListener) {
        this.onSongClickListener = onSongClickListener;
        this.highlightSongEntityList = new ArrayList<>();
    }

    @NonNull
    @Override
    public HighlightAdapter.HighlightViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_showighlight, parent, false);
        return new HighlightViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HighlightAdapter.HighlightViewHolder holder, int position) {
        HighlightSongEntity highlightSongEntity = highlightSongEntityList.get(position);
        holder.highlightTextView.setText(highlightSongEntity.getTitle());
        holder.startPointTextView.setText("Start Time = "+formatTime( highlightSongEntity.getStartTime()));
        holder.endPointTextView.setText("End Time = "+formatTime(highlightSongEntity.getEndTime()));

        holder.highlightTextView.setOnClickListener(view -> {
            onSongClickListener.onSongClick(highlightSongEntity);
        });
    }


    public void updateHighlights(List<HighlightSongEntity> newHighlights) {
        highlightSongEntityList.clear();
        highlightSongEntityList.addAll(newHighlights);
        notifyDataSetChanged();
    }

    private String formatTime(int milliseconds) {
        int minutes = (milliseconds / 1000) / 60; //get 1 minute
        int seconds = (milliseconds / 1000) % 60;//get 1 sec
        return String.format("%02d:%02d", minutes, seconds);
    }

    @Override
    public int getItemCount() {
        return highlightSongEntityList.size();
    }

    public class HighlightViewHolder extends RecyclerView.ViewHolder {
        TextView highlightTextView;
        TextView startPointTextView;
        TextView endPointTextView;

        public HighlightViewHolder(@NonNull View itemView) {
            super(itemView);
            highlightTextView = itemView.findViewById(R.id.textviewShowHighlight);
            startPointTextView = itemView.findViewById(R.id.textViewStartPoint);
            endPointTextView = itemView.findViewById(R.id.textViewEndPoint);

        }
    }
}
