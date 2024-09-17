package com.gadre.spotify.Activity;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.gadre.spotify.OtherClasses.LoadingDialog;
import com.gadre.spotify.R;
import com.gadre.spotify.databinding.ActivityDownloadManagerBinding;

import java.io.File;

public class DownloadManagerActivity extends AppCompatActivity {

    private ActivityDownloadManagerBinding binding;
    private LoadingDialog loadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDownloadManagerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loadingDialog = new LoadingDialog(this);
        downloadButtonListenar();
    }

    private void  downloadButtonListenar(){
        binding.downloadButton.setOnClickListener(view -> {
                String url = binding.urlEditText.getText().toString();
                downloadImages("Download images", url);
        });

    }

    private  void  downloadImages(String fileName,String imageURL){

        try {
            DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            Uri downloaduri=Uri.parse(imageURL);


            DownloadManager.Request request = new DownloadManager.Request(downloaduri);
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI)
                    .setTitle(fileName)
                    .setMimeType("image/jpeg")
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION)
                    .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, File.separator+fileName+".jpeg");
            downloadManager.enqueue(request);

            Toast.makeText(this,"Image Download done",Toast.LENGTH_SHORT).show();

        }catch (Exception e){
            Toast.makeText(this, "Image Downloading Fail", Toast.LENGTH_SHORT).show();
        }
    }
}