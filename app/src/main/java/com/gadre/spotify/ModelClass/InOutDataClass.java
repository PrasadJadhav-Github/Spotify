package com.gadre.spotify.ModelClass;

import com.google.gson.annotations.SerializedName;

public class InOutDataClass {
    @SerializedName("inTime")
    String inTime;
    @SerializedName("outTime")
    String outTime;
    @SerializedName("suidOut")
    String suidOut;
    @SerializedName("reason")
    int reason;
    @SerializedName("sReason")
    String sReason;
    @SerializedName("approvalStatus")
    int approvalStatus;
    @SerializedName("rejectedReason")
    String rejectedReason;

    public InOutDataClass(String inTime, String outTime, String suidOut, int reason, String sReason, int approvalStatus, String rejectedReason) {
        this.inTime = inTime;
        this.outTime = outTime;
        this.suidOut = suidOut;
        this.reason = reason;
        this.sReason = sReason;
        this.approvalStatus = approvalStatus;
        this.rejectedReason = rejectedReason;
    }

    public String getInTime() {
        return inTime;
    }

    public void setInTime(String inTime) {
        this.inTime = inTime;
    }

    public String getOutTime() {
        return outTime;
    }

    public void setOutTime(String outTime) {
        this.outTime = outTime;
    }

    public String getSuidOut() {
        return suidOut;
    }

    public void setSuidOut(String suidOut) {
        this.suidOut = suidOut;
    }

    public int getReason() {
        return reason;
    }

    public void setReason(int reason) {
        this.reason = reason;
    }

    public String getsReason() {
        return sReason;
    }

    public void setsReason(String sReason) {
        this.sReason = sReason;
    }

    public int getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(int approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getRejectedReason() {
        return rejectedReason;
    }

    public void setRejectedReason(String rejectedReason) {
        this.rejectedReason = rejectedReason;
    }
}
