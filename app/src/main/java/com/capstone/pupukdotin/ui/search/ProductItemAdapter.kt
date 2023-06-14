package com.capstone.pupukdotin.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.pupukdotin.R
import com.capstone.pupukdotin.data.remote.response.common.DataItem
import com.capstone.pupukdotin.databinding.ItemProductBinding
import com.capstone.pupukdotin.ui.detail.DetailItemActivity

class ProductItemAdapter(
    private val list: List<DataItem>,
    private val state: String? = null
) : RecyclerView.Adapter<ProductItemAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding: ItemProductBinding = ItemProductBinding.bind(itemView)
        fun bindItem(item: DataItem) {
            with(binding) {
                Glide.with(itemView)
                    .load(item.picture?.get(0)?.picture ?:"")
                    .placeholder(R.drawable.placeholder)
                    .into(ivProductImage)

                tvProductReview.text = item.rating ?: "0"
                tvProductTitle.text = item.name ?: "Tidak ada nama Produk"
                tvStoreName.text = item.store?.name ?: "Tidak ada nama store"
                tvProductPrice.text =
                    itemView.context.getString(R.string.price_format, item.price ?: 0)
                binding.root.setOnClickListener {
                    if(state == "wishlist") {
                        DetailItemActivity.start(itemView.context, item.id ?:0, true)
                    } else {
                        DetailItemActivity.start(itemView.context, item.id ?:0)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(list[position])
    }
}