package com.gadre.spotify.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gadre.spotify.Database.SalesInfo
import com.gadre.spotify.R
import com.gadre.spotify.databinding.DisplayRecyclerviewItemBinding

class DisplayAdapter():RecyclerView.Adapter<DisplayAdapter.DisplayDetailsHolder> (){
    val dislaylist=ArrayList<SalesInfo>()

    class DisplayDetailsHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val binding =DisplayRecyclerviewItemBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DisplayDetailsHolder {
       val view=LayoutInflater.from(parent.context).inflate(R.layout.display_recyclerview_item,parent,false)
        return DisplayDetailsHolder(view)
    }

    override fun getItemCount(): Int {
       return dislaylist.size
    }

    override fun onBindViewHolder(holder: DisplayDetailsHolder, position: Int) {
        holder.binding.textViewCityName.text=dislaylist[position].cityName
        holder.binding.textViewSalesPersonName.text=dislaylist[position].salesPersonName
        holder.binding.textViewSales.text=dislaylist[position].sales.toString()
    }
}