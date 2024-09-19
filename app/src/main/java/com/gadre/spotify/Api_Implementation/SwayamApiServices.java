package com.gadre.spotify.Api_Implementation;

import com.gadre.spotify.ModelClass.SwayamResponseDataClass;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SwayamApiServices {
    @GET("NoyifyMe/Dashboard/")
    Call<SwayamResponseDataClass> getAllInfoFromSwayamApi();

}
