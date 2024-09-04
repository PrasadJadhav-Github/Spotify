package com.gadre.spotify.OtherClasses;

import android.util.Log;
import android.widget.Toast;

import com.gadre.spotify.Api_Implementation.ApiServices;
import com.gadre.spotify.Api_Implementation.RetrofitClient;
import com.gadre.spotify.Interface.DisplayDataInterface;
import com.gadre.spotify.Interface.FetchDataInterface;
import com.gadre.spotify.ModelClass.AlbumJSON;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DisplaySpotifyData implements FetchDataInterface {

    private final DisplayDataInterface displayDataInterface;
    private final ApiServices apiServices;


    public DisplaySpotifyData(DisplayDataInterface displayDataInterface) {
        this.displayDataInterface=displayDataInterface;
        apiServices= RetrofitClient.getSpotifyData();

    }


    @Override
    public void fetchDataFromSpotifyApi() {
        Call<AlbumJSON> call = apiServices.spotifyData("summer","multi",0,50,5);
        call.enqueue(new Callback<AlbumJSON>() {
            @Override
            public void onResponse(Call<AlbumJSON> call, Response<AlbumJSON> response) {
                if (response.isSuccessful() && response.body()!=null){
                    AlbumJSON albumJSON=response.body();
                   displayDataInterface.displayDataOfSpotify(albumJSON);
                }

            }

            @Override
            public void onFailure(Call<AlbumJSON> call, Throwable t) {

                Log.e("SpotifyData", "API call failed: " + t.getMessage());
            }
        });

    }
}
