package com.capstone.pupukdotin.ui.addproduct

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.setPadding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.capstone.pupukdotin.R
import com.capstone.pupukdotin.databinding.ItemListImageBinding

data class InputImageModel(
    val url: String
)

class InputImageAdapter(
    private val listener: OnItemDeleteListener? = null
) : ListAdapter<InputImageModel, InputImageAdapter.ViewHolder>(DIFF_CALLBACK) {

    interface OnItemDeleteListener {
        fun deleteItemSelected(position: Int)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemListImageBinding.bind(itemView)
        fun bindItem(item: InputImageModel) {

            with(binding) {
//                Glide.with(itemView)
//                    .load(item.url)
//                    .placeholder(R.drawable.placeholder)
//                    .into(ivProductImage)

                ivProductImage.setImageBitmap(BitmapFactory.decodeFile(item.url))
                ivProductImage.setPadding(0)
                ivDeleteImage.setOnClickListener {
                    listener?.deleteItemSelected(adapterPosition)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_list_image, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        holder.bindItem(data)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<InputImageModel>() {
            override fun areItemsTheSame(
                oldItem: InputImageModel,
                newItem: InputImageModel
            ): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(
                oldItem: InputImageModel,
                newItem: InputImageModel
            ): Boolean =
                oldItem.url == newItem.url
        }
    }
}