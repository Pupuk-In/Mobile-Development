package com.capstone.pupukdotin.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.pupukdotin.R
import com.capstone.pupukdotin.data.remote.response.carts.CartItemsResponse
import com.capstone.pupukdotin.databinding.ItemCheckoutProductBinding

class TesCheckoutAdapter(
    private val list: List<CartItemsResponse.CartItem>
): RecyclerView.Adapter<TesCheckoutAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val binding = ItemCheckoutProductBinding.bind(itemView)
        private val context = itemView.context
        fun bindItem(item: CartItemsResponse.CartItem) {
            with(binding) {
                Glide.with(itemView)
                    .load(item.item?.picture?.get(0)?.picture ?:"")
                    .placeholder(R.drawable.placeholder)
                    .into(checkoutImageProduct)

                productCheckoutName.text = item.item?.name ?:""
                productCheckoutStore.text = item.item?.store?.name ?:""
                productCheckoutPrice.text = context.getString(R.string.price_format, item.price ?:0)
                tvProductCheckoutCounter.text = context.getString(R.string.qty_format, item.quantity ?:0)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_checkout_product, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(list[position])
    }

    override fun getItemCount(): Int = list.size

}