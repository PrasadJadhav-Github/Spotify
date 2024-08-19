package com.gadre.spotify.ModelClass;

import com.google.gson.annotations.SerializedName;

public class ReleseDate {
    @SerializedName("year")
    private int year;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
