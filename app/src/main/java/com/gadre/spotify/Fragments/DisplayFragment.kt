package com.gadre.spotify.Fragments
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.gadre.spotify.Adapter.DisplayAdapter
import com.gadre.spotify.Database.DBHelper
import com.gadre.spotify.databinding.FragmentDisplayBinding

class DisplayFragment : Fragment() {

    private lateinit var binding: FragmentDisplayBinding
    private var dbHelper: DBHelper? = null
    private val recyclerViewAdapter: DisplayAdapter = DisplayAdapter()

    private val years = arrayOf("2020", "2021", "2022", "2023", "2024", "2025")
    private val months = arrayOf(
        "apr", "may", "jun", "july", "august", "september",
        "oct", "nov", "dec", "jan", "feb", "mar"
    )
    private val filterOptions =
        arrayOf("City Wise Data", "Month Wise Data", "Sales Person Wise Data")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dbHelper = DBHelper(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDisplayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupSpinners()
        setupSortButton()
    }

    private fun setupRecyclerView() {
        binding.recyclerViewSalesData.adapter = recyclerViewAdapter
        binding.recyclerViewSalesData.layoutManager = LinearLayoutManager(context)
    }

    private fun setupSpinners() {
        val monthAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, months)
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerDisplayMonth.adapter = monthAdapter

        val yearAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, years)
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerDisplayYear.adapter = yearAdapter

        val filterAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, filterOptions)
        filterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerDisplayFilter.adapter = filterAdapter
    }

    private fun setupSortButton() {
        binding.buttonSortData.setOnClickListener {
            val selectedMonth = binding.spinnerDisplayMonth.selectedItem.toString()
            val selectedYear = binding.spinnerDisplayYear.selectedItem.toString()
            val selectedFilter = binding.spinnerDisplayFilter.selectedItem.toString()

            when (selectedFilter) {
                "City Wise Data" -> cityWiseData(selectedMonth, selectedYear)
                "Month Wise Data" -> monthWiseData(selectedMonth, selectedYear)
                "Sales Person Wise Data" -> sellsPersonWiseData(selectedMonth, selectedYear)
            }
        }
    }

    private fun monthWiseData(month: String, year: String) {
        val salesData = dbHelper?.selectSalesDetails(month, year)
        if (salesData != null && salesData.isNotEmpty()) {
            recyclerViewAdapter.dislaylist.clear()
            recyclerViewAdapter.dislaylist.addAll(salesData)
            recyclerViewAdapter.notifyDataSetChanged()

            binding.recyclerViewSalesData.visibility = View.VISIBLE
            binding.textViewNoData.visibility = View.GONE
            binding.textviewTopSellingCity.visibility = View.GONE
            binding.textviewLowSellingCity.visibility = View.GONE
            binding.textviewToppersonName.visibility = View.GONE
            binding.textviewAmount.visibility = View.GONE
        } else {
            binding.recyclerViewSalesData.visibility = View.GONE
            binding.textViewNoData.visibility = View.VISIBLE
            binding.textviewTopSellingCity.visibility = View.GONE
            binding.textviewLowSellingCity.visibility = View.GONE
            binding.textviewToppersonName.visibility = View.GONE
            binding.textviewAmount.visibility = View.GONE
        }
    }

    private fun cityWiseData(month: String, year: String) {
        val salesData = dbHelper?.selectSalesDetails(month, year)
        if (salesData != null && salesData.isNotEmpty()) {
            val citySalesMap = mutableMapOf<String, Double>()

            for (sales in salesData) {
                citySalesMap[sales.cityName] = citySalesMap.getOrDefault(sales.cityName, 0.0) + sales.sales
            }

            // Find top and low selling cities
            val topSellingCity = citySalesMap.maxByOrNull { it.value }
            val lowSellingCity = citySalesMap.minByOrNull { it.value }

            // Display top and low selling cities
            if (topSellingCity != null && lowSellingCity != null && topSellingCity.key != lowSellingCity.key) {
                binding.textviewTopSellingCity.visibility = View.VISIBLE
                binding.textviewLowSellingCity.visibility = View.VISIBLE

                binding.textviewTopSellingCity.text = "Top Selling City: ${topSellingCity.key} (${topSellingCity.value})"
                binding.textviewLowSellingCity.text = "Low Selling City: ${lowSellingCity.key} (${lowSellingCity.value})"
            } else {
                // Handle case where top and low cities are the same or data is insufficient
                binding.textviewTopSellingCity.visibility = View.VISIBLE
                binding.textviewLowSellingCity.visibility = View.VISIBLE

                binding.textviewTopSellingCity.text = "Top Selling City: No Data Available"
                binding.textviewLowSellingCity.text = "Low Selling City: No Data Available"
            }

            binding.recyclerViewSalesData.visibility = View.GONE
            binding.textViewNoData.visibility = View.GONE
            binding.textviewToppersonName.visibility = View.GONE
            binding.textviewAmount.visibility = View.GONE
        } else {
            binding.textviewTopSellingCity.visibility = View.VISIBLE
            binding.textviewLowSellingCity.visibility = View.VISIBLE
            binding.textviewTopSellingCity.text = "No Data Available"
            binding.textviewLowSellingCity.text = "No Data Available"

            binding.recyclerViewSalesData.visibility = View.GONE
            binding.textViewNoData.visibility = View.GONE
            binding.textviewToppersonName.visibility = View.GONE
            binding.textviewAmount.visibility = View.GONE
        }
    }

    private fun sellsPersonWiseData(month: String, year: String) {
        val salesPersonData = dbHelper?.selectSalesDetails(month, year)
        if (salesPersonData != null && salesPersonData.isNotEmpty()) {
            val personSalesMap = mutableMapOf<String, Double>()

            for (person in salesPersonData) {
                personSalesMap[person.salesPersonName] = personSalesMap.getOrDefault(person.salesPersonName, 0.0) + person.sales
            }

            // Find top and low selling salespersons
            val topSellingPerson = personSalesMap.maxByOrNull { it.value }
            val lowSellingPerson = personSalesMap.minByOrNull { it.value }

            // Display top and low selling salespersons
            if (topSellingPerson != null && lowSellingPerson != null && topSellingPerson.key != lowSellingPerson.key) {
                binding.textviewToppersonName.visibility = View.VISIBLE
                binding.textviewAmount.visibility = View.VISIBLE

                binding.textviewToppersonName.text = "Top Selling Salesperson: ${topSellingPerson.key} (${topSellingPerson.value})"
                binding.textviewAmount.text = "Low Selling Salesperson: ${lowSellingPerson.key} (${lowSellingPerson.value})"
            } else {
                binding.textviewToppersonName.visibility = View.VISIBLE
                binding.textviewAmount.visibility = View.VISIBLE

                binding.textviewToppersonName.text = "No Data Available"
                binding.textviewAmount.text = "No Data Available"
            }

            // Hide other views
            binding.recyclerViewSalesData.visibility = View.GONE
            binding.textViewNoData.visibility = View.GONE
            binding.textviewTopSellingCity.visibility = View.GONE
            binding.textviewLowSellingCity.visibility = View.GONE
        } else {
            binding.textviewToppersonName.visibility = View.VISIBLE
            binding.textviewAmount.visibility = View.VISIBLE
            binding.textviewToppersonName.text = "No Data Available"
            binding.textviewAmount.text = "No Data Available"

            // Hide other views
            binding.recyclerViewSalesData.visibility = View.GONE
            binding.textViewNoData.visibility = View.GONE
            binding.textviewTopSellingCity.visibility = View.GONE
            binding.textviewLowSellingCity.visibility = View.GONE
        }
    }


}
