package com.gadre.spotify.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gadre.spotify.ModelClass.AudioFileDataClass;
import com.gadre.spotify.ModelClass.MusicPlayerDataClass;
import com.gadre.spotify.R;

import java.util.List;

public class ExternalMediaPlayerAdapter  extends RecyclerView.Adapter<ExternalMediaPlayerAdapter.AudioFileViewHolder>{

    private List<AudioFileDataClass> audioFileDataClassList;
    private  final  OnExternalSongClickListener onExternalSongClickListener;

    public interface OnExternalSongClickListener {
        void onSongClick(AudioFileDataClass audioData);
    }

    public ExternalMediaPlayerAdapter(List<AudioFileDataClass> audioFileDataClassList, OnExternalSongClickListener onExternalSongClickListener) {
        this.audioFileDataClassList = audioFileDataClassList;
        this.onExternalSongClickListener = onExternalSongClickListener;
    }



    @NonNull
    @Override
    public ExternalMediaPlayerAdapter.AudioFileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_external_audio_file, parent, false);
        return new AudioFileViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ExternalMediaPlayerAdapter.AudioFileViewHolder holder, int position) {

        AudioFileDataClass audioFile = audioFileDataClassList.get(position);
        holder.songNameTextView.setText(audioFile.getName());
        holder.songNameTextView.setOnClickListener(view -> {
            onExternalSongClickListener.onSongClick(audioFile);

        });
    }

    @Override
    public int getItemCount() {
        return audioFileDataClassList.size();
    }

    public class AudioFileViewHolder extends  RecyclerView.ViewHolder{
        TextView songNameTextView;
        public AudioFileViewHolder(View itemView) {
            super(itemView);

            songNameTextView=itemView.findViewById(R.id.songNameTextView);
        }
    }
}
