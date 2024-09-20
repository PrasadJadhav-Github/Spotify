package com.gadre.spotify.ModelClass;

import com.google.gson.annotations.SerializedName;

public class ApiError {
    @SerializedName("jErrorVal")
    int errorVal;

    @SerializedName("sErrorMessage")
    String errorMessage;

    public int getErrorVal() {
        return errorVal;
    }

    public void setErrorVal(int errorVal) {
        this.errorVal = errorVal;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
