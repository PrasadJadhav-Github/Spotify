package com.gadre.spotify.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.gadre.spotify.R;
import com.gadre.spotify.databinding.ActivitySellsDetailsBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SellsDetailsActivity extends AppCompatActivity {

    private ActivitySellsDetailsBinding binding;
    //private NavController navController;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySellsDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        NavController  navController = Navigation.findNavController(this, R.id.fragmentC);
        BottomNavigationView bottomNavigationView = findViewById(R.id.navBottomMenu);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }
}