package com.gadre.spotify.Activity;

import static java.nio.file.Files.createDirectory;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import androidx.documentfile.provider.DocumentFile;

import com.gadre.spotify.OtherClasses.LoadingDialog;
import com.gadre.spotify.R;
import com.gadre.spotify.databinding.ActivityFileManagerBinding;

import java.io.IOException;
import java.io.OutputStream;

public class FileManagerActivity extends AppCompatActivity {

    private ActivityFileManagerBinding binding;
    private ActivityResultLauncher<Intent> createFile;
    private ActivityResultLauncher<Intent> creatNewFile;
    private SharedPreferences sharedPreferences;
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFileManagerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loadingDialog = new LoadingDialog(this);
        sharedPreferences = getSharedPreferences("FileManagerPrefs", MODE_PRIVATE);

        // Initialize methods
        buttonListeners();
        activityLauncher();

    }

    //store files in which ever folder you want
    private void buttonListeners() {
        loadingDialog.startAlertDialog();
        new android.os.Handler().postDelayed(() -> {
        //button for save file in different folder
        binding.buttonCreate.setOnClickListener(view -> {
            String fileName = binding.editTextFileName.getText().toString();
            String fileType = binding.editTextInputText.getText().toString();

            if (fileName.isEmpty() || fileType.isEmpty()) {
                Toast.makeText(this, "Please enter a file name", Toast.LENGTH_SHORT).show();
                return;
            }

            // Create the intent to user choose a location
            Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);//starts an activity to create a new document.
            intent.addCategory(Intent.CATEGORY_OPENABLE);// open file manager from device to select folder
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TITLE, fileName);
            createFile.launch(intent);
        });


        //button for create new file directory
        binding.buttonCreateNewFile.setOnClickListener(view -> {
            String fileName = binding.editTextFileName.getText().toString();
            String fileType = binding.editTextInputText.getText().toString();

            if (fileName.isEmpty() && fileType.isEmpty()) {
                Toast.makeText(this, "Please enter a file name", Toast.LENGTH_SHORT).show();
                return;
            }

            String uriString = sharedPreferences.getString("filestorageuri", null);

            if (uriString != null) {
                Uri uri = Uri.parse(uriString);
                DocumentFile documentFile = DocumentFile.fromTreeUri(this, uri);

                DocumentFile newDocumentCreationFileName = documentFile.createFile("text/plain", fileName);
                if (newDocumentCreationFileName != null) {
                    writeFile(newDocumentCreationFileName.getUri());
                    binding.editTextFileName.getText().clear();
                    binding.editTextInputText.getText().clear();
                }
            } else {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);//starts an activity to create a new document.
                creatNewFile.launch(intent);
            }
        });
            loadingDialog.closeAlertDialog();
        }, 2000);
    }


    private void activityLauncher() {
        //Activity launcher for create file
        createFile = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                Uri uri = result.getData().getData();
                if (uri != null) {
                    writeFile(uri);
                    binding.editTextFileName.getText().clear();
                    binding.editTextInputText.getText().clear();

                }
            }
        });


        //Activity launcher for save file in new directory
        creatNewFile = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                Uri uri = result.getData().getData();
                if (uri != null) {
                    final int takeFlags = (Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    getContentResolver().takePersistableUriPermission(uri, takeFlags);

                    //SharedPreferences preferences = getSharedPreferences("Permission", Context.MODE_PRIVATE);
                    sharedPreferences.edit().putString("filestorageuri", uri.toString()).apply();


                    DocumentFile documentFile = DocumentFile.fromTreeUri(this, uri);


                    if (documentFile != null && documentFile.isDirectory()) {
                        String fileName = binding.editTextFileName.getText().toString();
                        String fileType = binding.editTextInputText.getText().toString();


                        DocumentFile directory = documentFile.createDirectory("Android Files");
                        if (directory != null) {
                            DocumentFile documentFileName = directory.createFile("text/plain", fileName);
                            if (documentFileName != null) {
                                writeFile(documentFileName.getUri());
                            }
                        }
                        Toast.makeText(this, "Folder selected and permission granted", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Selected URI is not a directory", Toast.LENGTH_SHORT).show();
                    }
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

