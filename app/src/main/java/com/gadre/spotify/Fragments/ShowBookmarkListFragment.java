package com.gadre.spotify.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gadre.spotify.R;
import com.gadre.spotify.RoomDatabase_Database.BookmarkDatabase;
import com.gadre.spotify.databinding.FragmentShowBookmarkListBinding;

public class ShowBookmarkListFragment extends Fragment {

    private FragmentShowBookmarkListBinding binding;
    private BookmarkDatabase bookmarkDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentShowBookmarkListBinding.inflate(inflater, container, false);
        bookmarkDatabase = BookmarkDatabase.getDatabase(requireContext());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
