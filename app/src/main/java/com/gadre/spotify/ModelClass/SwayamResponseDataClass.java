package com.gadre.spotify.ModelClass;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SwayamResponseDataClass {
    @SerializedName("liTodaysCheckInCheckOut")
    List<InOutDataClass> listOfInOut;
    @SerializedName("sTotalOutHours")
    String totalOutHours;
    @SerializedName("sTotalInHours")
    String totalInHours;

    public ApiError getApiError() {
        return apiError;
    }

    public void setApiError(ApiError apiError) {
        this.apiError = apiError;
    }

    @SerializedName( "apiError")
    ApiError apiError;

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
