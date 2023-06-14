package com.capstone.pupukdotin.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.pupukdotin.R
import com.capstone.pupukdotin.data.remote.response.store.StoreAllItemsResponse
import com.capstone.pupukdotin.databinding.ItemDaftarProductBinding

class TesDaftarProdukStoreAdapter(
    private val listProduct: List<StoreAllItemsResponse.Item>,
    private val listener: ItemSelectedListener? = null
) : RecyclerView.Adapter<TesDaftarProdukStoreAdapter.ViewHolder>() {

    interface ItemSelectedListener {
        fun onChangeClickListener(position: Int)
        fun onDeleteClickListener(position: Int)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemDaftarProductBinding.bind(itemView)
        private val context = itemView.context

        fun bindItem(item: StoreAllItemsResponse.Item) {
            with(binding) {
                Glide.with(itemView)
                    .load(item.picture?.get(0)?.picture ?: "")
                    .placeholder(R.drawable.placeholder)
                    .into(daftarProdukImageProduct)

                daftarProdukName.text = item.name ?: "Tidak Ada Nama Produk"
                productCheckoutPrice.text =
                    context.getString(R.string.price_format, item.price ?: 0)
                daftarProdukRating.text = item.rating ?: "0.0"
                daftarProdukStok.text = item.stock.toString()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_daftar_product, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(listProduct[position])
    }

    override fun getItemCount(): Int = listProduct.size
}