package com.gadre.spotify.ModelClass;

import android.net.Uri;

public class AudioFileDataClass {
    private Uri uri;
    private String name;

    public AudioFileDataClass(Uri uri, String name) {
        this.uri = uri;
        this.name = name;
    }


    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
