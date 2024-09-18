package com.gadre.spotify.Activity;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.gadre.spotify.OtherClasses.LoadingDialog;
import com.gadre.spotify.R;
import com.gadre.spotify.databinding.ActivityDownloadManagerBinding;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class DownloadManagerActivity extends AppCompatActivity {

    private ActivityDownloadManagerBinding binding;
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDownloadManagerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loadingDialog = new LoadingDialog(this);
        downloadButtonListener();
    }

    private void downloadButtonListener() {
        binding.downloadButton.setOnClickListener(view -> {
            String urls = binding.urlEditText.getText().toString();
            List<String> urlList = Arrays.asList(urls.split(","));// Arrays.asList() method in Java is used to convert an array into a List
            for (String url : urlList) {
                downloadImages(url.trim());
            }
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
}
