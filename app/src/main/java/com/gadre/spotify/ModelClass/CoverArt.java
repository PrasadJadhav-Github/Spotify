package com.gadre.spotify.ModelClass;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CoverArt {
    @SerializedName("sources")
    private List<ImageSouce> imageSouces;

    public List<ImageSouce> getImageSouces() {
        return imageSouces;
    }

    public void setImageSouces(List<ImageSouce> imageSouces) {
        this.imageSouces = imageSouces;
    }
}
