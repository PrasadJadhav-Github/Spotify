package com.gadre.spotify.OtherClasses;

import android.content.Context;
import android.content.res.Resources;

import com.gadre.spotify.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class SongsUtil {

    // Method to get a list of song names in the 'raw' resource folder
    public static List<String> getRawSongList(Context context) {
        List<String> songList = new ArrayList<>();
        Resources resources = context.getResources();
        Field[] fields = R.raw.class.getFields();

        for (Field field : fields) {
            try {
                String songName = field.getName();
                songList.add(songName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return songList;
    }
}
