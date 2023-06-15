package com.capstone.pupukdotin.ui.addproduct

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.capstone.pupukdotin.R
import com.capstone.pupukdotin.databinding.ItemPlantInputBinding


data class InputPlantOrTypeOrPartModel(
    val name: String,
    val id: Int
)

data class PlantOrTypeOrPartModel(
    val name: String
)

class InputPlantPartAdapter(
    private val listener: OnItemDeleteListener? = null,
    private val input: String
) : ListAdapter<PlantOrTypeOrPartModel, InputPlantPartAdapter.ViewHolder>(DIFF_CALLBACK) {

    interface OnItemDeleteListener {
        fun deleteItemSelected(position: Int, input: String)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemPlantInputBinding.bind(itemView)
        fun bindItem(item: PlantOrTypeOrPartModel) {

            with(binding) {
                tvName.text = item.name
                ivDelete.setOnClickListener {
                    listener?.deleteItemSelected(adapterPosition, input)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_plant_input, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        holder.bindItem(data)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PlantOrTypeOrPartModel>() {
            override fun areItemsTheSame(
                oldItem: PlantOrTypeOrPartModel,
                newItem: PlantOrTypeOrPartModel
            ): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(
                oldItem: PlantOrTypeOrPartModel,
                newItem: PlantOrTypeOrPartModel
            ): Boolean =
                oldItem.name == newItem.name
        }
    }
}