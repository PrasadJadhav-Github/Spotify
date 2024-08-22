package com.gadre.spotify.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gadre.spotify.Activity.SellsDetailsActivity;
import com.gadre.spotify.databinding.FragmentLoginBinding;

public class FragmentLogin extends Fragment {

    private FragmentLoginBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.loginButton.setOnClickListener(v -> {
            String username = binding.usernameEditText.getText().toString().trim();
            String email = binding.emailEditText.getText().toString().trim();
            String password = binding.passwordEditText.getText().toString().trim();

            // Validate inputs
            if (username.isEmpty()) {
                showError("Enter Username");
                return;
            }

            if (email.isEmpty()) {
                showError("Enter Email");
                return;
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                showError("Enter a Valid Email");
                return;
            }

            if (password.isEmpty()) {
                showError("Enter Password");
                return;
            } else if (password.length() < 6) {
                showError("Password must be at least 6 characters");
                return;
            }


            showError("Login successful");

            Intent intent = new Intent(getActivity(), SellsDetailsActivity.class);
            startActivity(intent);
        });
    }

    private void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
