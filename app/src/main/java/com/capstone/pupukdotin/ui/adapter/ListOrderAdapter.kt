package com.capstone.pupukdotin.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.pupukdotin.R
import com.capstone.pupukdotin.data.remote.response.transaction.GetDetailTransactionResponse
import com.capstone.pupukdotin.databinding.ItemOrderBinding


class ListOrderAdapter(private val list: List<GetDetailTransactionResponse.TransactionItemItem>) :
    RecyclerView.Adapter<ListOrderAdapter.ViewHolder>() {

    inner class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        private val binding = ItemOrderBinding.bind(itemview)


        @SuppressLint("SetTextI18n")
        fun bind(item: GetDetailTransactionResponse.TransactionItemItem) {
            val context = itemView.context
            with(binding) {
                Glide.with(itemView)
                    .load(item.itemHistory[0].picture[0])
                    .placeholder(R.drawable.placeholder)
                    .into(ivProductImage)

                tvProductName.text = item.itemHistory[0].name

                tvProductPrice.text =
                    "${context.getString(R.string.total_harga)} : " +
                        context.getString(R.string.price_format, item.subtotal)

                tvProductStock.text = context.getString(R.string.product_stock, item.quantity)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }


}