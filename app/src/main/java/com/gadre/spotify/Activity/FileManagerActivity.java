package com.gadre.spotify.Activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.gadre.spotify.R;
import com.gadre.spotify.databinding.ActivityFileManagerBinding;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileManagerActivity extends AppCompatActivity {

    private static final int REQUEST_WRITE_PERMISSION = 786;
    private ActivityFileManagerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFileManagerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize button listeners
        buttonListeners();
    }

    private void buttonListeners() {
        binding.buttonCreate.setOnClickListener(view -> {
            if (ContextCompat.checkSelfPermission(FileManagerActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(FileManagerActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
            } else {
                saveTextToFile();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_WRITE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                saveTextToFile();
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void saveTextToFile() {
        String text = binding.editTextInputText.getText().toString();
        String fileName = binding.editTextFileName.getText().toString();

        if (text.isEmpty() || fileName.isEmpty()) {
            Toast.makeText(this, "Please enter text and file name", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create folder and file path
        File baseDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "Android Text Files");
        if (!baseDir.exists()) {
            if (!baseDir.mkdirs()) {
                Toast.makeText(this, "Failed to create directory", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        File file = new File(baseDir, fileName + ".txt");

        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(text.getBytes());
            fos.close();
            Toast.makeText(this, "File saved to: " + file.getAbsolutePath(), Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to save file: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
