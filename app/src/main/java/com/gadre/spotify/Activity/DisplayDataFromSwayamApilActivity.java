package com.gadre.spotify.Activity;

import android.app.DatePickerDialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gadre.spotify.Adapter.SwayamApiAdapter;
import com.gadre.spotify.Interface.DisplaySwayamInterface;
import com.gadre.spotify.ModelClass.InOutDataClass;
import com.gadre.spotify.ModelClass.SwayamResponseDataClass;
import com.gadre.spotify.OtherClasses.FetchDataForSwayamApp;
import com.gadre.spotify.DialogBox.LoadingDialog;
import com.gadre.spotify.databinding.ActivitySwyamApiCallBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class DisplayDataFromSwayamApilActivity extends AppCompatActivity implements DisplaySwayamInterface {
    private ActivitySwyamApiCallBinding binding;
    private RecyclerView recyclerView;
    private SwayamApiAdapter swayamApiAdapter;
    private FetchDataForSwayamApp fetchDataForSwayamApp;
    private LoadingDialog loadingDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySwyamApiCallBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        recyclerView = binding.swayamRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadingDialog = new LoadingDialog(this);

        fetchDataForSwayamApp = new FetchDataForSwayamApp(this);

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
                            loadingDialog.startAlertDialog();
                            new android.os.Handler().postDelayed(() -> {
                            fetchDataForSwayamApp.fetchDataFromSwayamApi(selectedDate);
                            }, 100);
                        }
                    },
                    year, month, day
            );
            datePickerDialog.show();

        });

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null) {
            actionBar.setTitle("Today's Check In - Check Out");
            //  actionBar.setHomeAsUpIndicator();
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void displayDetailsFromSwayamApi(SwayamResponseDataClass swayamResponseDataClass) {
        List<InOutDataClass> inOutDataClasses = swayamResponseDataClass.getListOfInOut();
        binding.inHoursTextView.setText(swayamResponseDataClass.getTotalInHours());
        binding.outHourTextView.setText(swayamResponseDataClass.getTotalOutHours());


        Calendar inTimeCal = Calendar.getInstance();
        Calendar outTimeCal=Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.ENGLISH);

        try {
            inTimeCal.setTime(sdf.parse(swayamResponseDataClass.getTotalInHours()));
            outTimeCal.setTime(sdf.parse(swayamResponseDataClass.getTotalOutHours()));
            inTimeCal.add(Calendar.HOUR,outTimeCal.get(Calendar.HOUR));
            inTimeCal.add(Calendar.MINUTE,outTimeCal.get(Calendar.MINUTE));
        } catch (ParseException e) {
         e.printStackTrace();
        }
        binding.totalhoursTextView.setText(String.format("%02d:%02d",inTimeCal.get(Calendar.HOUR),inTimeCal.get(Calendar.MINUTE)));
        swayamApiAdapter = new SwayamApiAdapter(inOutDataClasses);
        recyclerView.setAdapter(swayamApiAdapter);
        loadingDialog.closeAlertDialog();
    }

    @Override
    public void displayMessage(String messages) {
        Toast.makeText(this, "Api Fetch Successfully", Toast.LENGTH_SHORT).show();
    }



}
