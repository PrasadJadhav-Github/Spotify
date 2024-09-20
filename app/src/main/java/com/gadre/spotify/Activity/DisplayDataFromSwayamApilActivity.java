package com.gadre.spotify.Activity;

import android.app.DatePickerDialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gadre.spotify.Adapter.SwayamApiAdapter;
import com.gadre.spotify.Api_Implementation.SwayamApiServices;
import com.gadre.spotify.Interface.DisplaySwayamInterface;
import com.gadre.spotify.ModelClass.InOutDataClass;
import com.gadre.spotify.ModelClass.SwayamResponseDataClass;
import com.gadre.spotify.OtherClasses.FetchDataForSwayamApp;
import com.gadre.spotify.databinding.ActivitySwyamApiCallBinding;

import java.util.List;

public class DisplayDataFromSwayamApilActivity extends AppCompatActivity implements DisplaySwayamInterface {
    private ActivitySwyamApiCallBinding binding;
    private RecyclerView recyclerView;
    private SwayamApiAdapter swayamApiAdapter;
    private FetchDataForSwayamApp fetchDataForSwayamApp;
    private SwayamApiServices swayamApiServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySwyamApiCallBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        recyclerView = binding.swayamRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        fetchDataForSwayamApp =new FetchDataForSwayamApp(this);

        binding.calenderButton.setOnClickListener(view -> {
            // Get the current date
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            String selectedDate = String.format("%02d-%02d-%d", dayOfMonth, month + 1, year);
                            binding.calenderButton.setText(selectedDate);
                            fetchDataForSwayamApp.fetchDataFromSwayamApi(selectedDate);
                        }
                    },
                    year, month, day
            );
            datePickerDialog.show();
        });


    }

    @Override
    public void DisplayDetailsFromSwayamApi(SwayamResponseDataClass swayamResponseDataClass) {
        List<InOutDataClass> inOutDataClasses = swayamResponseDataClass.getListOfInOut();
        swayamApiAdapter = new SwayamApiAdapter(inOutDataClasses);
        recyclerView.setAdapter(swayamApiAdapter);
    }
}
