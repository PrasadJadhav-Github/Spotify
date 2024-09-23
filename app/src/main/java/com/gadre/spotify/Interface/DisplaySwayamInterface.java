package com.gadre.spotify.Interface;

import com.gadre.spotify.ModelClass.SwayamResponseDataClass;

public interface DisplaySwayamInterface {
    void displayDetailsFromSwayamApi(SwayamResponseDataClass swayamResponseDataClass);
    void displayMessage(String messages);
}
