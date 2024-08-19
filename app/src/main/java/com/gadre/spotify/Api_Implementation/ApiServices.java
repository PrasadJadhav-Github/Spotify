package com.gadre.spotify.Api_Implementation;

import com.gadre.spotify.ModelClass.AlbumJSON;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface ApiServices {
    @GET("search/")
    @Headers({
            "x-rapidapi-host:spotify23.p.rapidapi.com",
            "x-rapidapi-key:e0f9a64743mshfe7dfd4eb3b02aap16072cjsn5355eb0c5746"
    })

    Call<AlbumJSON>spotifyData(@Query("q")String query,
                               @Query("type") String type,
                               @Query("offset") int offset,
                               @Query("limit") int limit,
                               @Query("numberOfTopResults") int numberOfTopResults);


}
