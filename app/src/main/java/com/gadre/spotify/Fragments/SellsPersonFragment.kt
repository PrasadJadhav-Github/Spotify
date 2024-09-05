package com.gadre.spotify.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.gadre.spotify.Adapter.PersonNameAdapter
import com.gadre.spotify.Database.DBHelper
import com.gadre.spotify.Database.TSalesPerson
import com.gadre.spotify.databinding.FragmentSellsPersonBinding


class SellsPersonFragment : Fragment() {
    private lateinit var binding: FragmentSellsPersonBinding
    private lateinit var personRecycerviewAdapter: PersonNameAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSellsPersonBinding.inflate(inflater, container, false)

        //set up for person recycler view
        personRecycerviewAdapter = PersonNameAdapter()
        binding.PersonrecyclerView.adapter = personRecycerviewAdapter
        binding.PersonrecyclerView.layoutManager = LinearLayoutManager(context)

        displayperson()
        insetPersonName()
        return binding.root
    }


    private fun insetPersonName() {
        binding.buttonAddSellsPerson.setOnClickListener {
            val sellspersonname = binding.editTextsellsperson.text.toString()
            val dbHelper = DBHelper(requireContext())
            if (sellspersonname.isNotEmpty()) {
                val isInseted = dbHelper.insertPersonName(TSalesPerson(sellspersonname))
                if (isInseted) {
                    Toast.makeText(requireContext(), "Sells person added successfully", Toast.LENGTH_SHORT).show()
                    displayperson()
                }
            }else{
                Toast.makeText(requireContext(), "Please Enter Sells Person Name ", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun displayperson() {
        val dbHelper = DBHelper(requireContext())
        val personname = dbHelper.selectpersonname()

        if (personname!= null){
            personRecycerviewAdapter.personNamelist.clear()
            personRecycerviewAdapter.personNamelist.addAll(personname)
            personRecycerviewAdapter.notifyDataSetChanged()
        }

    }

}