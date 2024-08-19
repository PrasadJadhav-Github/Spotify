package com.gadre.spotify.ModelClass;

import com.google.gson.annotations.SerializedName;

public class Artists {
    @SerializedName("uri")
    private String uri;
    @SerializedName("profile")
    private  ArtistProfile artistProfile;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public ArtistProfile getArtistProfile() {
        return artistProfile;
    }

    public void setArtistProfile(ArtistProfile artistProfile) {
        this.artistProfile = artistProfile;
    }
}
