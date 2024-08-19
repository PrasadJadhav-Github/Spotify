package com.gadre.spotify.ModelClass;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ArtistsList {
    @SerializedName("items")
    private List<Artists> artists;

    public List<Artists> getArtists() {
        return artists;
    }

    public void setArtists(List<Artists> artists) {
        this.artists = artists;
    }
}
