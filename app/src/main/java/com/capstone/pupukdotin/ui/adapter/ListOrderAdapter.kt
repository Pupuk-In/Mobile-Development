package com.capstone.pupukdotin.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.pupukdotin.R
import com.capstone.pupukdotin.databinding.ItemOrderBinding


data class DummyOrderModel(
    val uri: String,
    val name: String,
    val stock: Int,
    val price: Int
)

class ListOrderAdapter(private val list: List<DummyOrderModel>) :
    RecyclerView.Adapter<ListOrderAdapter.ViewHolder>() {

    inner class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        private val binding = ItemOrderBinding.bind(itemview)

        @SuppressLint("SetTextI18n")
        fun bind(item: DummyOrderModel) {
            val context = itemView.context
            with(binding) {
                Glide.with(itemView)
                    .load(item.uri)
                    .placeholder(R.drawable.placeholder)
                    .into(ivProductImage)

                tvProductName.text = item.name

                tvProductPrice.text =
                    "${context.getString(R.string.total_harga)} : " +
                        context.getString(R.string.price_format, item.price)

                tvProductStock.text = context.getString(R.string.product_stock, item.stock)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
        )
    }

    override fun getItemCount(): Int = 3

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.bind(list[position])
    }


}