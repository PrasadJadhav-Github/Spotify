package com.gadre.spotify.ModelClass;

import com.google.gson.annotations.SerializedName;

public class SwayamRequestDataClass {
    @SerializedName("tag")
    String tag;
    @SerializedName("extraInfo")
    String extraInfo;
    @SerializedName("dtRequested")
    String dtRequested;
    @SerializedName("clientType")
    int clientType;
    @SerializedName("suidSession")
    String suidSession;

    public SwayamRequestDataClass(String tag, String extraInfo, String dtRequested, int clientType, String suidSession) {
        this.tag = tag;
        this.extraInfo = extraInfo;
        this.dtRequested = dtRequested;
        this.clientType = clientType;
        this.suidSession = suidSession;
    }

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
