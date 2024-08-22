package com.gadre.spotify.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gadre.spotify.Database.TSalesPerson
import com.gadre.spotify.R
import com.gadre.spotify.databinding.SellsPersonRecyclerviewItemBinding

class PersonNameAdapter:RecyclerView.Adapter<PersonNameAdapter.PersonNameHolder> (){
    val personNamelist=ArrayList<TSalesPerson>()

    class PersonNameHolder (itemView: View ):RecyclerView.ViewHolder(itemView){
        val binding=SellsPersonRecyclerviewItemBinding.bind(itemView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonNameHolder {
       val view=LayoutInflater.from(parent.context).inflate(R.layout.sells_person_recyclerview_item,parent,false)
        return PersonNameHolder(view)
    }

    override fun getItemCount(): Int {
       return  personNamelist.size
    }

    override fun onBindViewHolder(holder: PersonNameHolder, position: Int) {
        holder.binding.textviewPersonName.text=personNamelist[position].personname
    }
}