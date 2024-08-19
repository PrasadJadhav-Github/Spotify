package com.gadre.spotify.ModelClass;

import com.google.gson.annotations.SerializedName;

public class Item {
    @SerializedName("data")
    private AlbumData data;

    public AlbumData getData() {
        return data;
    }

    public void setData(AlbumData data) {
        this.data = data;
    }
}
