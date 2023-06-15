package com.capstone.pupukdotin.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.capstone.pupukdotin.R
import com.capstone.pupukdotin.data.remote.response.transaction.GetAllTransactionResponse
import com.capstone.pupukdotin.databinding.ItemHistoryProductBinding
import com.capstone.pupukdotin.ui.detail.DetailOrderActivity
import com.capstone.pupukdotin.utils.convertTime

class TesHistoryAdapter(
    private val list: List<GetAllTransactionResponse.TransactionItem>
) : RecyclerView.Adapter<TesHistoryAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemHistoryProductBinding.bind(itemView)
        private val context = itemView.context
        fun bindItem(item: GetAllTransactionResponse.TransactionItem) {
            with(binding) {
                productAmount.text = item.invoice
                productHistoryPrice.text = context.getString(R.string.price_format, item.total)
                textViewDate.text = item.createdAt.convertTime("dd MMMM yyyy")
                textViewTime.text = item.createdAt.convertTime("HH:mm")

                berhasilTextView.isInvisible = true
                prosesTextView.isInvisible = true
                gagalTextView.isInvisible = true

                when(item.transactionStatusId) {
                    1 -> prosesTextView.isVisible = true
                    3 -> gagalTextView.isVisible = true
                    7 -> berhasilTextView.isVisible = true
                }

                btnLihat.setOnClickListener {
                    DetailOrderActivity.start(context, item.id)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_history_product, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(list[position])
    }

    override fun getItemCount(): Int = list.size

}