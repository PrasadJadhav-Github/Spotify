package com.gadre.spotify.ModelClass;

import com.google.gson.annotations.SerializedName;

public class AlbumData {
    @SerializedName("uri")
    private String uri;
    @SerializedName("name")
    private String name;
    @SerializedName("artists")
    private  ArtistsList artistsList;
    @SerializedName("coverArt")
    private  CoverArt coverArt;
    @SerializedName("date")
    private ReleseDate releseDate;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArtistsList getArtistsList() {
        return artistsList;
    }

    public void setArtistsList(ArtistsList artistsList) {
        this.artistsList = artistsList;
    }

    public CoverArt getCoverArt() {
        return coverArt;
    }

    public void setCoverArt(CoverArt coverArt) {
        this.coverArt = coverArt;
    }

    public ReleseDate getReleseDate() {
        return releseDate;
    }

    public void setReleseDate(ReleseDate releseDate) {
        this.releseDate = releseDate;
    }
}
