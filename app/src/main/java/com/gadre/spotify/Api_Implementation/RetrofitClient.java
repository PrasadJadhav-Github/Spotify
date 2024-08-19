package com.gadre.spotify.Api_Implementation;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static ApiServices apiService = null;

    public static ApiServices getSpotifyData() {
        if (apiService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://spotify23.p.rapidapi.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            apiService=retrofit.create(ApiServices.class);
        }
        return apiService;
    }
}
