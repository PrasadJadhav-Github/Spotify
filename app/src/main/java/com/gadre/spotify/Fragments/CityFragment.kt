package com.gadre.spotify.Fragments

import android.annotation.SuppressLint
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.gadre.spotify.Adapter.CityAdapter
import com.gadre.spotify.Database.DBHelper
import com.gadre.spotify.Database.TCity
import com.gadre.spotify.databinding.FragmentCityBinding
import com.google.android.material.snackbar.Snackbar


class CityFragment : Fragment() {

    private lateinit var binding: FragmentCityBinding
    private lateinit var recyclerAdapter: CityAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCityBinding.inflate(inflater, container, false)

        //recycler view implementation
        recyclerAdapter = CityAdapter()
        binding.cityrecyclerView.adapter = recyclerAdapter
        binding.cityrecyclerView.layoutManager = LinearLayoutManager(context)


        insertDataintosqlite()
        displayDatafromsqlite()
        return binding.root
    }


    //function to  insert city into sqlite database
    private fun insertDataintosqlite() {
        binding.buttonAddCity.setOnClickListener {
            val cityName = binding.editTextAddCity.text.toString()
            val dbHelper = DBHelper(requireContext())
            if (cityName.isNotEmpty()) {
                val isInseted = dbHelper.insertCity(TCity(cityName))
                if (isInseted) {
                    Toast.makeText(requireContext(), "City added successfully", Toast.LENGTH_SHORT)
                        .show()
                    displayDatafromsqlite()
                }
            }
        }

    }


    @SuppressLint("SuspiciousIndentation")
    private fun displayDatafromsqlite() {
        val dbHelper = DBHelper(requireContext())
        var cityList = dbHelper.selectCity()?: emptyList()
            recyclerAdapter.cityList.clear()
            recyclerAdapter.cityList.addAll(cityList)
            recyclerAdapter.notifyDataSetChanged()



    }

}
