package com.gadre.spotify.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.gadre.spotify.ModelClass.AlbumData;
import com.gadre.spotify.ModelClass.Artists;
import com.gadre.spotify.ModelClass.ImageSouce;
import com.gadre.spotify.R;

import java.util.List;

public class AlbumApapter extends RecyclerView.Adapter<AlbumApapter.AlbumViewHolder> {

    private List<AlbumData> albumList;

    public AlbumApapter(List<AlbumData> albumList) {
        this.albumList = albumList;
    }

    @NonNull
    @Override
    public AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_album, parent, false);
        return new AlbumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumViewHolder holder, int position) {
        AlbumData albumData = albumList.get(position);
        holder.textViewAlbumName.setText(albumData.getName());

        //function to load image using glide
        if (albumData.getCoverArt() != null) {
            List<ImageSouce> imageSouceList = albumData.getCoverArt().getImageSouces();
            if (imageSouceList != null && !imageSouceList.isEmpty()) {
                String imageUrl = imageSouceList.get(0).getUrl();
                if (imageUrl != null && !imageUrl.isEmpty()) {
                    Glide.with(holder.itemView.getContext())
                            .load(imageUrl)
                            .placeholder(R.mipmap.ic_launcher)
                            .error(R.mipmap.ic_launcher)
                            .into(holder.imageViewCoverArt);
                }
            }
        }

        //display artis name and song name using string builder
        if (albumData.getArtistsList() != null && !albumData.getArtistsList().getArtists().isEmpty()) {
            StringBuilder artistNames = new StringBuilder();
            for (Artists artist : albumData.getArtistsList().getArtists()) {
                artistNames.append(artist.getArtistProfile().getArtistname()).append("\n");
            }
            holder.textViewArtistName.setText(artistNames.toString().trim());
        }
    }

    @Override
    public int getItemCount() {
        return albumList != null ? albumList.size() : 0;
    }

    static class AlbumViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewCoverArt;
        TextView textViewAlbumName;
        TextView textViewArtistName;

        AlbumViewHolder(View itemView) {
            super(itemView);
            imageViewCoverArt = itemView.findViewById(R.id.imageViewCoverArt);
            textViewAlbumName = itemView.findViewById(R.id.textViewAlbumName);
            textViewArtistName = itemView.findViewById(R.id.textViewArtistName);
        }
    }
}
