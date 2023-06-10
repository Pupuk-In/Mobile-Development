package com.capstone.pupukdotin.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.pupukdotin.R
import com.capstone.pupukdotin.data.remote.response.carts.CartItemsResponse
import com.capstone.pupukdotin.databinding.ItemCartProductBinding

class TesCartAdapter(
    private val list: List<CartItemsResponse.CartItem>,
    private val listener: OnItemChecked? = null
) : RecyclerView.Adapter<TesCartAdapter.ViewHolder>() {

    interface OnItemChecked {
        fun onItemDelete(itemId: Int)
        fun addQuantity(position: Int)
        fun subtractQuantity(position: Int)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemCartProductBinding.bind(itemView)
        private val context = itemView.context
        fun bindItem(item: CartItemsResponse.CartItem) {
            with(binding) {
                Glide.with(itemView)
                    .load(item.item?.picture?.get(0)?.picture ?: "")
                    .placeholder(R.drawable.placeholder)
                    .into(cartImageProduct)
                productName.text = item.item?.name ?: "Tidak ada nama"
                productCartStore.text = item.item?.store?.name ?: "Tidak ada nama toko"
                productPrice.text = context.getString(R.string.price_format, item.item?.price ?:0)
                tvProductCounter.text = (item.quantity ?:0).toString()

                binding.minusProductCart.setOnClickListener {
                    listener?.subtractQuantity(adapterPosition)
                }

                binding.plusProductCart.setOnClickListener {
                    listener?.addQuantity(adapterPosition)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_cart_product, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(list[position])
    }

}