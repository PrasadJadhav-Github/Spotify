package com.gadre.spotify.ModelClass;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SwayamResponseDataClass {
    @SerializedName("listOfInOut")
    List<InOutDataClass> listOfInOut;
    @SerializedName("totalOutHours")
    String totalOutHours;
    @SerializedName("totalInHours")
    String totalInHours;

    public SwayamResponseDataClass(List<InOutDataClass> listOfInOut, String totalOutHours, String totalInHours) {
        this.listOfInOut = listOfInOut;
        this.totalOutHours = totalOutHours;
        this.totalInHours = totalInHours;
    }

    public List<InOutDataClass> getListOfInOut() {
        return listOfInOut;
    }

    public void setListOfInOut(List<InOutDataClass> listOfInOut) {
        this.listOfInOut = listOfInOut;
    }

    public String getTotalOutHours() {
        return totalOutHours;
    }

    public void setTotalOutHours(String totalOutHours) {
        this.totalOutHours = totalOutHours;
    }

    public String getTotalInHours() {
        return totalInHours;
    }

    public void setTotalInHours(String totalInHours) {
        this.totalInHours = totalInHours;
    }
}
