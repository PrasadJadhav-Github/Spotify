package com.gadre.spotify.Activity;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.gadre.spotify.OtherClasses.LoadingDialog;
import com.gadre.spotify.R;
import com.gadre.spotify.WorkerManager.DownloadManagerWorker;
import com.gadre.spotify.databinding.ActivityDownloadManagerBinding;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownloadManagerActivity extends AppCompatActivity {

    private ActivityDownloadManagerBinding binding;
    private LoadingDialog loadingDialog;
    private OkHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDownloadManagerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loadingDialog = new LoadingDialog(this);
        downloadMultipleFileButtonListener();
        downloadSingleFileListener();
        downloadImagesUsingHttpClientListener();
    }

    private void downloadMultipleFileButtonListener() {
        binding.downloadMultipleFileButton.setOnClickListener(view -> {
            String urls = binding.urlEditText.getText().toString();
            // Arrays.asList() method in Java is used to convert an array into a List
            List<String> urlList = Arrays.asList(urls.split(","));
            //for loop iterate over arraylist to find image url
            for (String url : urlList) {
                downloadImages(url.trim());//trim() remove whitespaces
            }
        });
    }

    private void downloadSingleFileListener() {
        binding.downloadSingleFileButton.setOnClickListener(view -> {
            String urls = binding.urlEditText.getText().toString();
            downloadImages(urls);
        });
    }

    private void downloadImages(String imageURL) {
        try {
            DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            Uri downloadUri = Uri.parse(imageURL); // Directly parse the URL
            String fileName = Uri.parse(imageURL).getLastPathSegment(); // Get the last segment as the file name

            DownloadManager.Request request = new DownloadManager.Request(downloadUri);
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI)
                    .setTitle(fileName)
                    .setMimeType("image/jpeg")
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION)
                    .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, fileName);

            downloadManager.enqueue(request);

            Toast.makeText(this, "Downloading: " + fileName, Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(this, "Failed to download: " + imageURL, Toast.LENGTH_SHORT).show();
        }
    }


    private void downloadImagesUsingHttpClientListener() {
        binding.downloadFileUsingHttpClientButton.setOnClickListener(view -> {

            String urls = binding.urlEditText.getText().toString();
            try {
                downloadImagesUsingHttpClient(urls);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    //use workmanager to handle download process in background
    private void downloadImagesUsingHttpClient(String imageURL) {
        Data inputData = new Data.Builder()
                .putString("IMAGE_URL", imageURL)
                .build();

        OneTimeWorkRequest downloadWorkRequest = new OneTimeWorkRequest.Builder(DownloadManagerWorker.class)
                .setInputData(inputData)
                .build();

        WorkManager.getInstance(this).enqueue(downloadWorkRequest);
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(downloadWorkRequest.getId())
                .observe(this, workInfo -> {
                    if (workInfo != null && workInfo.getState().isFinished()) {
                        if (workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                            Toast.makeText(this, "Download Successful: " + imageURL, Toast.LENGTH_SHORT).show();
                        } else if (workInfo.getState() == WorkInfo.State.FAILED) {
                            Toast.makeText(this, "Download Failed: " + imageURL, Toast.LENGTH_SHORT).show();
                        }
                    }

                });


        Toast.makeText(this, "Starting Download: " + imageURL, Toast.LENGTH_SHORT).show();


    }

}
