package com.gadre.spotify.Api_Implementation;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SwayamRetrofitClient {

    private static SwayamApiServices apiService = null;
    public static SwayamApiServices getSwayamData() {
        if (apiService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://test.swayamess.in/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            apiService=retrofit.create(SwayamApiServices.class);
        }
        return apiService;
    }
}
