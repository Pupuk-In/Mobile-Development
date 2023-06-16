package com.capstone.pupukdotin.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone.pupukdotin.R
import com.capstone.pupukdotin.databinding.ItemPlantBinding

class PlantSmallAdapter(private val listItem: List<String>) :
    RecyclerView.Adapter<PlantSmallAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemPlantBinding.bind(itemView)

        fun bind(item: String) {
            binding.tvPlant.text = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_plant, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listItem[position])
    }

    override fun getItemCount(): Int = listItem.size

}
