package com.gadre.spotify.OtherClasses;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.gadre.spotify.ModelClass.AudioFileDataClass;

import java.util.ArrayList;
import java.util.List;

public class MediaStoreManager {
    private ContentResolver contentResolver;

    public MediaStoreManager(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }


    public List<AudioFileDataClass> getAudioFiles() {
        List<AudioFileDataClass> audioFileDataClasses = new ArrayList<>();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] display = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DISPLAY_NAME
        };

        Cursor cursor = contentResolver.query(uri, display, null, null, null);


        if (cursor != null) {
            while (cursor.moveToNext()) {
                long id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME));

                Uri audioUri = Uri.withAppendedPath(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, String.valueOf(id));
                AudioFileDataClass audioFileDataClass = new AudioFileDataClass(audioUri, name);
                audioFileDataClasses.add(audioFileDataClass);
            }
            cursor.close();
        }
        return audioFileDataClasses;
    }
}