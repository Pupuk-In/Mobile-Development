package com.capstone.pupukdotin.ui.store

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.pupukdotin.R
import com.capstone.pupukdotin.databinding.FragmentStoreProdukBinding
import com.capstone.pupukdotin.ui.adapter.TesDaftarPesananAdapter
import com.capstone.pupukdotin.ui.adapter.TesDaftarProdukStoreAdapter
import com.capstone.pupukdotin.ui.common.BaseFragment

class StoreProdukFragment : BaseFragment<FragmentStoreProdukBinding>() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TesDaftarProdukStoreAdapter
    override fun getViewBinding(): FragmentStoreProdukBinding = FragmentStoreProdukBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.daftar_produk_recycleview)
        adapter = TesDaftarProdukStoreAdapter()

        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }

}