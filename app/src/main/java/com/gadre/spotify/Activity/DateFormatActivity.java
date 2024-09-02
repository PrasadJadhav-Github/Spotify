package com.gadre.spotify.Activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.gadre.spotify.R;
import com.gadre.spotify.databinding.ActivityDateFormatBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormatActivity extends AppCompatActivity {

    private ActivityDateFormatBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDateFormatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonFormatDate.setOnClickListener(view -> {
            String enterDate = binding.editTextEnterDate.getText().toString();
            String enterTime = binding.editTextEnterTime.getText().toString();

            // Define the input and output formats for 12-hour format
            SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            SimpleDateFormat inputTimeFormat12 = new SimpleDateFormat("hh:mm a", Locale.getDefault());
            SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            SimpleDateFormat outputTimeFormat12 = new SimpleDateFormat("hh:mm:ss a", Locale.getDefault());

            try {
                // Parse the entered date and time
                Date date = inputDateFormat.parse(enterDate);
                Date time = inputTimeFormat12.parse(enterTime);

                if (date != null && time != null) {
                    // Format the date and time
                    String formattedDate = outputDateFormat.format(date);
                    String formattedTime = outputTimeFormat12.format(time);

                    // Display the formatted date and time
                    binding.textViewShowDate.setText("Date: " + formattedDate);
                    binding.textViewShowTime.setText("Time: " + formattedTime);

                    // Clear the input fields
                    binding.editTextEnterDate.getText().clear();
                    binding.editTextEnterTime.getText().clear();
                } else {
                    showToast("Invalid date or time format");
                }
            } catch (ParseException e) {
                e.printStackTrace();
                showToast("Invalid date or time format");
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
