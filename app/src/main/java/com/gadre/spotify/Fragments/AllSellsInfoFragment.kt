package com.gadre.spotify.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.gadre.spotify.Database.DBHelper
import com.gadre.spotify.Database.TCity
import com.gadre.spotify.Database.TSales
import com.gadre.spotify.Database.TSalesPerson
import com.gadre.spotify.databinding.FragmentAllSellsInfoBinding

class AllSellsInfoFragment : Fragment() {

    private lateinit var binding: FragmentAllSellsInfoBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllSellsInfoBinding.inflate(inflater, container, false)
        onsavebuttonclick()
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dbHelper = DBHelper(requireContext())

        // Fetch city data and display into dropdown
        val city = dbHelper.selectCity() ?: emptyList()
        val cityAdapter = ArrayAdapter<TCity>(requireContext(), android.R.layout.simple_spinner_item, city)
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCityName.adapter = cityAdapter

        //fetch person data and display into dropdown
        val person = dbHelper.selectpersonname() ?: emptyList()
        val personAdapter = ArrayAdapter<TSalesPerson>(requireContext(), android.R.layout.simple_spinner_item, person)
        personAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerPersonName.adapter = personAdapter

    }

    private fun onsavebuttonclick() {
        binding.btnSave.setOnClickListener {
            val month = binding.editTextMonth.text.toString().trim()
            val year = binding.editTextYear.text.toString().trim()
            val salesString = binding.editSale.text.toString().trim()
            val city = binding.spinnerCityName.selectedItem as? TCity
            val personName = binding.spinnerPersonName.selectedItem as? TSalesPerson

            // Check if required fields are not empty
            if (month.isNotEmpty() && year.isNotEmpty() && salesString.isNotEmpty() && city != null && personName != null) {
                try {
                    val sales = salesString.toDouble()
                    // Create a TSales object
                    val tsales = TSales(month, year, personName.personID!!, city.cityID!!, sales)

                    // Insert the sales record into the database
                    val dbHelper = DBHelper(requireContext())
                    dbHelper.insertSales(tsales)
                    Toast.makeText(requireContext(), "Data inserted successfully", Toast.LENGTH_SHORT).show()
                } catch (e: NumberFormatException) {
                    Toast.makeText(requireContext(), "Invalid sales amount", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show()
            }


        }
    }


}