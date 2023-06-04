package com.capstone.pupukdotin.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone.pupukdotin.R

class ProductItemAdapter: RecyclerView.Adapter<ProductItemAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        //        TODO("Not yet implemented")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = 10

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        TODO("Not yet implemented")
    }
}