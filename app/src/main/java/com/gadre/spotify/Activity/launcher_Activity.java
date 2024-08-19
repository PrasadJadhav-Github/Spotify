package com.gadre.spotify.Activity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.gadre.spotify.R;
import com.gadre.spotify.databinding.ActivityLauncherBinding;
import com.gadre.spotify.databinding.ActivityMainBinding;

public class launcher_Activity extends AppCompatActivity {

    private ActivityLauncherBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityLauncherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}