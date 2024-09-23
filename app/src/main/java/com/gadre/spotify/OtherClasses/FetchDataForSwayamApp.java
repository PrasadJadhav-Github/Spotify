package com.gadre.spotify.OtherClasses;

import android.util.Log;

import androidx.annotation.NonNull;

import com.gadre.spotify.Api_Implementation.SwayamApiServices;
import com.gadre.spotify.Api_Implementation.SwayamRetrofitClient;
import com.gadre.spotify.Interface.DisplaySwayamInterface;
import com.gadre.spotify.Interface.FetchSwayamInterface;
import com.gadre.spotify.ModelClass.SwayamRequestDataClass;
import com.gadre.spotify.ModelClass.SwayamResponseDataClass;

import java.text.SimpleDateFormat;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FetchDataForSwayamApp implements FetchSwayamInterface {
    private final DisplaySwayamInterface displaySwayamInterface;
    private final SwayamApiServices swayamApiServices;

    public FetchDataForSwayamApp(DisplaySwayamInterface displaySwayamInterface) {
        this.displaySwayamInterface = displaySwayamInterface;
        swayamApiServices=SwayamRetrofitClient.getSwayamData();
    }

    @Override
    public void fetchDataFromSwayamApi(String date) {
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String formattedDate = "";

        try {
            formattedDate = outputDateFormat.format(inputDateFormat.parse(date));
        } catch (Exception e) {
            Log.e("Date Formatting", "Error parsing date: " + e.getMessage());
        }
        SwayamRequestDataClass swayamRequestDataClass = new SwayamRequestDataClass();
        if (formattedDate != null) {
            swayamRequestDataClass.setDtRequested(formattedDate);
        }

        // Call the API with the request data object
        Call<SwayamResponseDataClass> call = swayamApiServices.getAllInfoFromSwayamApi(swayamRequestDataClass);
        call.enqueue(new Callback<SwayamResponseDataClass>() {
            @Override
            public void onResponse(@NonNull Call<SwayamResponseDataClass> call, Response<SwayamResponseDataClass> response) {
                if (response.isSuccessful() && response.body() != null) {
                    SwayamResponseDataClass swayamResponseDataClass = response.body();
                    displaySwayamInterface.displayDetailsFromSwayamApi(swayamResponseDataClass);
                    displaySwayamInterface.displayMessage("API Respond Successfully");
                    Log.d("API Response", "Success: " + swayamResponseDataClass);

                } else {
                    Log.d("API Response", "Failed: " + response.code());
                    displaySwayamInterface.displayMessage("API Reponse Fail");
                }
            }
            @Override
            public void onFailure(Call<SwayamResponseDataClass> call, Throwable t) {
                Log.e("Swayam", "API call failed: " + t.getMessage());
            }
        });
    }

}
