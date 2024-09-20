package com.gadre.spotify.ModelClass;

import com.google.gson.annotations.SerializedName;

public class SwayamRequestDataClass {
    @SerializedName("sTag")
    String tag;
    @SerializedName("sExtraInfo")
    String extraInfo;
    @SerializedName("dtRequested")
    String dtRequested;
    @SerializedName("jClientType")
    int clientType=2;
    @SerializedName("suidSession")
    String suidSession="335c9560fbc36766ced47bb04e28a737a1f90a8c4ce4c2aa963b1051bb33aecf";


    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }

    public String getDtRequested() {
        return dtRequested;
    }

    public void setDtRequested(String dtRequested) {
        this.dtRequested = dtRequested;
    }

    public int getClientType() {
        return clientType;
    }

    public void setClientType(int clientType) {
        this.clientType = clientType;
    }

    public String getSuidSession() {
        return suidSession;
    }

    public void setSuidSession(String suidSession) {
        this.suidSession = suidSession;
    }
}
