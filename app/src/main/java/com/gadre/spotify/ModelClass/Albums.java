package com.gadre.spotify.ModelClass;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Albums {
    @SerializedName("totalCount")
    private int totalCount;
    @SerializedName("items")
    private List<Item> itemList;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }
}
