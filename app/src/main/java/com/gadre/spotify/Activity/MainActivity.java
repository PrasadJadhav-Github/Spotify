package com.gadre.spotify.Activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.gadre.spotify.Adapter.SpotifyAlbumApapter;
import com.gadre.spotify.Interface.DisplayDataInterface;
import com.gadre.spotify.ModelClass.AlbumJSON;
import com.gadre.spotify.ModelClass.AlbumData;
import com.gadre.spotify.ModelClass.Item;
import com.gadre.spotify.OtherClasses.FetchSpotifyDataFromApi;
import com.gadre.spotify.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DisplayDataInterface {

    private ActivityMainBinding binding;
    private SpotifyAlbumApapter albumAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //initialize recycler view
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        albumAdapter = new SpotifyAlbumApapter(new ArrayList<>());
        binding.recyclerView.setAdapter(albumAdapter);

        //instance of FetchSpotifyDataFromApi holds api data
        FetchSpotifyDataFromApi fetchSpotifyDataFromApi = new FetchSpotifyDataFromApi(this);
        fetchSpotifyDataFromApi.fetchDataFromSpotifyApi();
    }

    @Override
    public void displayDataOfSpotify(AlbumJSON albumJSON) {
        if (albumJSON != null && albumJSON.getAlbums() != null) {
            List<AlbumData> albumDataList = new ArrayList<>();
            List<Item> items = albumJSON.getAlbums().getItemList();
            if (items != null) {
                for (Item item : items) {
                    AlbumData albumData = item.getData();
                    if (albumData != null) {
                        albumDataList.add(albumData);
                    }
                }
            }
            albumAdapter = new SpotifyAlbumApapter(albumDataList);
            binding.recyclerView.setAdapter(albumAdapter);

            //set binding to hide the text message when data is display
            binding.textViewPlaceholder.setVisibility(View.GONE);
            binding.recyclerView.setVisibility(View.VISIBLE);
        } else {
            // no data is available
            albumAdapter = new SpotifyAlbumApapter(new ArrayList<>());
            binding.recyclerView.setAdapter(albumAdapter);

            //set binding if no data is available
            binding.textViewPlaceholder.setText("No data available.");
            binding.textViewPlaceholder.setVisibility(View.VISIBLE);
            binding.recyclerView.setVisibility(View.GONE);
        }
    }
}
