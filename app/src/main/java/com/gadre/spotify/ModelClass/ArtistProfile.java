package com.gadre.spotify.ModelClass;

import com.google.gson.annotations.SerializedName;

public class ArtistProfile {
    @SerializedName("name")
    private String artistname;

    public String getArtistname() {
        return artistname;
    }

    public void setArtistname(String artistname) {
        this.artistname = artistname;
    }
}
