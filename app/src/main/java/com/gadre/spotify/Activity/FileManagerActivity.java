package com.gadre.spotify.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.DocumentsProvider;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.gadre.spotify.R;
import com.gadre.spotify.databinding.ActivityFileManagerBinding;

import java.io.IOException;
import java.io.OutputStream;

public class FileManagerActivity extends AppCompatActivity {

    private ActivityFileManagerBinding binding;
    private ActivityResultLauncher<Intent> createFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFileManagerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize button listeners
        buttonListeners();

        // Initialize file creation launcher
        initFileCreationLauncher();
    }

    private void buttonListeners() {
        binding.buttonCreate.setOnClickListener(view -> {
            String fileName = binding.editTextFileName.getText().toString();
            String fileContent = binding.editTextInputText.getText().toString();

            if (fileName.isEmpty() && fileContent.isEmpty()) {
                Toast.makeText(this, "Please enter a file name", Toast.LENGTH_SHORT).show();
                return;
            }

            // Create the intent to user choose a location
            Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TITLE, fileName);

            createFile.launch(intent);
        });
    }

    private void initFileCreationLauncher() {
        createFile = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                Uri uri = result.getData().getData();
                if (uri != null) {
                    writeFile(uri);
                }
            }
        });
    }

    private void writeFile(Uri uri) {
        String fileContent = binding.editTextInputText.getText().toString();
        try (OutputStream outputStream = getContentResolver().openOutputStream(uri)) {
            if (outputStream != null) {
                outputStream.write(fileContent.getBytes());
                outputStream.flush();
                Toast.makeText(this, "File created successfully", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error creating file", Toast.LENGTH_SHORT).show();
        }
    }
}
