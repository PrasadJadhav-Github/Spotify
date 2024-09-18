package com.gadre.spotify.ModelClass;

public class LauncherActivityDataClass {
    private int imageResId;
    private String name;

    public LauncherActivityDataClass(int imageResId, String name) {
        this.imageResId = imageResId;
        this.name = name;
    }


    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
