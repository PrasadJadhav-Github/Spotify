package com.gadre.spotify.Api_Implementation;

import com.gadre.spotify.ModelClass.SwayamRequestDataClass;
import com.gadre.spotify.ModelClass.SwayamResponseDataClass;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface SwayamApiServices {
    @POST("NoyifyMe/Dashboard/GetTodaysCheckInCheckOut")
    Call<SwayamResponseDataClass> getAllInfoFromSwayamApi(@Body SwayamRequestDataClass swayamRequestDataClass);

}
