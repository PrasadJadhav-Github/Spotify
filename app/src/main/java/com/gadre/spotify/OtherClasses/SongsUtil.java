package com.gadre.spotify.OtherClasses;

import android.content.Context;
import android.content.res.Resources;

import com.gadre.spotify.ModelClass.MusicPlayerDataClass;
import com.gadre.spotify.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class SongsUtil {

    // Method to get a list of song names and their IDs in the 'raw' resource folder
    public static List<MusicPlayerDataClass> getRawSongList(Context context) {
        // Creates an empty ArrayList to store the MusicPlayerDataClass objects
        List<MusicPlayerDataClass> songList = new ArrayList<>();
        Resources resources = context.getResources();

        // Retrieve all fields from the Raw folder
        Field[] fields = R.raw.class.getFields();

        for (Field field : fields) {
            try {
                // Get the name of the raw resource
                String songName = field.getName();

                // Get the resource ID for the song
                int id = resources.getIdentifier(songName, "raw", context.getPackageName());

                // Create a new MusicPlayerDataClass object with the ID and name
                MusicPlayerDataClass songData = new MusicPlayerDataClass();
                songData.setId(id);
                songData.setName(songName);

                // Add the MusicPlayerDataClass object to the list
                songList.add(songData);
            } catch (Exception e) {
                // Handle the exception
                e.printStackTrace();
            }
        }
        return songList;
    }
}
