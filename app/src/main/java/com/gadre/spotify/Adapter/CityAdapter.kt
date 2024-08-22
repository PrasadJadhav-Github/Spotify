package com.gadre.spotify.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gadre.spotify.Database.TCity
import com.gadre.spotify.R
import com.gadre.spotify.databinding.CityRecyclerviewItemBinding

class CityAdapter():RecyclerView.Adapter<CityAdapter.CityHolder>() {
    val cityList = ArrayList<TCity>()


    class CityHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = CityRecyclerviewItemBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.city_recyclerview_item,parent,false)
        return CityHolder(view)
    }

    override fun getItemCount(): Int {
       return cityList.size
    }

    override fun onBindViewHolder(holder: CityHolder, position: Int) {
        holder.binding.textviewCityName.text = cityList[position].cityname
    }
}