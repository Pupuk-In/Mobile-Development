package com.capstone.pupukdotin.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.pupukdotin.R
import com.capstone.pupukdotin.data.remote.response.TypeItem
import com.capstone.pupukdotin.databinding.ItemTypeFertilizerBinding

class TypeFertilizerAdapter(
    private val typeList: List<TypeItem>,
    private val listener: OnUserItemClick? = null
) : RecyclerView.Adapter<TypeFertilizerAdapter.ViewHolder>() {

    interface OnUserItemClick {
        fun onUserItemClick()
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemTypeFertilizerBinding.bind(itemView)
        fun bindItem(item: TypeItem) {
            with(binding) {
                Glide.with(itemView)
                    .load(item.picture)
                    .placeholder(R.drawable.placeholder)
                    .into(ivLogoJenis)

                tvNamaJenis.text = item.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_type_fertilizer, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(typeList[position])
    }

    override fun getItemCount(): Int = typeList.size
}

