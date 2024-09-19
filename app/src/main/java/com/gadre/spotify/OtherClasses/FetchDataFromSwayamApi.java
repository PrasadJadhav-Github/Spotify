package com.gadre.spotify.OtherClasses;

import android.util.Log;

import com.gadre.spotify.Api_Implementation.SwayamApiServices;
import com.gadre.spotify.Interface.DisplaySwayamInterface;
import com.gadre.spotify.Interface.FetchSwayamInterface;
import com.gadre.spotify.ModelClass.SwayamResponseDataClass;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FetchDataFromSwayamApi implements FetchSwayamInterface {
    private final DisplaySwayamInterface displaySwayamInterface;
    private final SwayamApiServices swayamApiServices;

    public FetchDataFromSwayamApi(DisplaySwayamInterface displaySwayamInterface, SwayamApiServices swayamApiServices) {
        this.displaySwayamInterface = displaySwayamInterface;
        this.swayamApiServices = swayamApiServices;
    }

    @Override
    public void fetchDataFromSwayamApi() {
        Call<SwayamResponseDataClass> call = swayamApiServices.getAllInfoFromSwayamApi();
        call.enqueue(new Callback<SwayamResponseDataClass>() {
            @Override
            public void onResponse(Call<SwayamResponseDataClass> call, Response<SwayamResponseDataClass> response) {
                if (response.isSuccessful() && response.body() != null) {
                    SwayamResponseDataClass swayamResponseDataClass = response.body();
                    displaySwayamInterface.DisplayDetailsFromSwayamApi(swayamResponseDataClass);
                }
            }

            @Override
            public void onFailure(Call<SwayamResponseDataClass> call, Throwable t) {
                Log.e("Swayam", "API call failed: " + t.getMessage());
            }
        });

    }
}
