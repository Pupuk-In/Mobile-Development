package com.capstone.pupukdotin.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.pupukdotin.R
import com.capstone.pupukdotin.data.remote.response.PlantItem
import com.capstone.pupukdotin.databinding.ItemPlantFertilizerBinding

class PlantFertilizerAdapter(
    private val plantList: List<PlantItem>,
    private val listener: OnUserItemClick? = null
) : RecyclerView.Adapter<PlantFertilizerAdapter.ViewHolder>() {

    interface OnUserItemClick {
        fun onUserItemClick()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemPlantFertilizerBinding.bind(itemView)
        fun bindItem(item: PlantItem) {
            with(binding) {
                Glide.with(itemView)
                    .load(item.picture)
                    .placeholder(R.drawable.placeholder)
                    .into(ivPlant)

                tvPlant.text = item.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_plant_fertilizer, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(plantList[position])
    }

    override fun getItemCount(): Int = plantList.size
}