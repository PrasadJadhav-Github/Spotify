package com.gadre.spotify.ModelClass;

import com.google.gson.annotations.SerializedName;

public class AlbumJSON {
    @SerializedName("albums")
    private  Albums albums;

    public Albums getAlbums() {
        return albums;
    }

    public void setAlbums(Albums albums) {
        this.albums = albums;
    }
}
