package com.gadre.spotify.WorkerManager;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownloadManagerWorker extends Worker {
    public DownloadManagerWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        String imageURL = getInputData().getString("IMAGE_URL");

        if (imageURL != null) {
            try {
                 downloadImage(imageURL);
            } catch (IOException e) {
                e.printStackTrace();
                return Result.failure();
            }
        }
        return Result.failure();
    }

    //use this method to download images
    private Result downloadImage(String imageURL) throws IOException {
        String fileName = Uri.parse(imageURL).getLastPathSegment();
        File outputFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), fileName);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(imageURL).build();
        Response response = client.newCall(request).execute();


        if (!response.isSuccessful()) {
            throw new IOException("Failed to download file: " + response);
        }

        FileOutputStream fos = new FileOutputStream(outputFile);
        fos.write(response.body().bytes());
        fos.close();

        return Result.success();
    }
}
