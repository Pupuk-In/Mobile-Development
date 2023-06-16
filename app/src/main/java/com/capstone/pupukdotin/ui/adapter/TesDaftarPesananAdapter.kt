package com.capstone.pupukdotin.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone.pupukdotin.R
import com.capstone.pupukdotin.data.remote.response.store.GetAllTransactionStoreResponse
import com.capstone.pupukdotin.databinding.ItemDaftarPesananProductBinding
import com.capstone.pupukdotin.ui.store.StoreDetailOrderActivity
import com.capstone.pupukdotin.utils.convertTime

class TesDaftarPesananAdapter(
    private val list: List<GetAllTransactionStoreResponse.TransactionsItem>
): RecyclerView.Adapter<TesDaftarPesananAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val binding = ItemDaftarPesananProductBinding.bind(itemView)
        private val context = itemView.context
        fun bindItem(item: GetAllTransactionStoreResponse.TransactionsItem) {
            with(binding) {
                productDaftarPesananAmount.text = item.invoice
                productDaftarPesananPrice.text = context.getString(R.string.price_format, item.total)
                textViewDate.text = item.createdAt.convertTime("dd MMMM yyyy")
                textViewTime.text = item.createdAt.convertTime("HH:mm")

                namaPenerimaPesanan.text = item.recipientName

                btnLihat.setOnClickListener {
                    StoreDetailOrderActivity.start(context, item.id)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_daftar_pesanan_product, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(list[position])
    }

    override fun getItemCount(): Int = list.size

}