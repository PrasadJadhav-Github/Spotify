package com.gadre.spotify.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.gadre.spotify.Interface.DisplayDataInterface;
import com.gadre.spotify.ModelClass.AlbumData;
import com.gadre.spotify.ModelClass.AlbumJSON;
import com.gadre.spotify.ModelClass.Artists;
import com.gadre.spotify.ModelClass.CoverArt;
import com.gadre.spotify.ModelClass.ImageSouce;
import com.gadre.spotify.ModelClass.Item;
import com.gadre.spotify.OtherClasses.DisplaySpotifyData;
import com.gadre.spotify.R;
import com.gadre.spotify.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity implements DisplayDataInterface {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DisplaySpotifyData displaySpotifyData = new DisplaySpotifyData(this);
        displaySpotifyData.fetchDataFromSpotifyApi();
    }


    @Override
    public void displayDataOfSpotify(AlbumJSON albumJSON) {
        // Check if the albumJSON object and its nested Albums object are not null
        if (albumJSON != null && albumJSON.getAlbums() != null) {
            // Create a StringBuilder to accumulate album and artist details
            StringBuilder albumAndArtistDetails = new StringBuilder();


            // Retrieve the list of items from the Albums object
            List<Item> items = albumJSON.getAlbums().getItemList();
            if (items != null) {
                for (Item item : items) {
                    // Retrieve the AlbumData object from the current item
                    AlbumData albumData = item.getData();
                    if (albumData != null) {
                        // Append album name
                        albumAndArtistDetails.append("Album Name: ").append(albumData.getName()).append("\n");
                        if (albumData.getArtistsList() != null) {
                            List<Artists> artists = albumData.getArtistsList().getArtists();
                            if (artists != null) {
                                for (Artists artist : artists) {
                                    // Append each artist's name
                                    albumAndArtistDetails.append("Artist: ").append(artist.getArtistProfile().getArtistname()).append("\n").append("\n");
                                }
                            }
                        }


                        List<ImageSouce> imageSouceList = albumData.getCoverArt().getImageSouces();
                        if (imageSouceList != null && !imageSouceList.isEmpty()) {
                            ImageSouce firstImageSource = imageSouceList.get(0);
                            String imageUrl = firstImageSource.getUrl();
                            if (imageUrl != null && !imageUrl.isEmpty()) {
                                Glide.with(this)
                                        .load(imageUrl)
                                        .placeholder(R.mipmap.ic_launcher)
                                        .error(R.mipmap.ic_launcher)
                                        .into(binding.imageViewCoverArt);
                            }

                        }
                    }
                }
                binding.textViewAlbumNames.setText(albumAndArtistDetails.toString().trim());
            } else {
                binding.textViewAlbumNames.setText("No data available.");
            }
        }

    }
}