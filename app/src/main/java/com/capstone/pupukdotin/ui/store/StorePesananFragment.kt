package com.capstone.pupukdotin.ui.store

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.pupukdotin.R
import com.capstone.pupukdotin.databinding.FragmentStorePesananBinding
import com.capstone.pupukdotin.ui.adapter.TesDaftarPesananAdapter
import com.capstone.pupukdotin.ui.adapter.TesHistoryAdapter
import com.capstone.pupukdotin.ui.common.BaseFragment

class StorePesananFragment : BaseFragment<FragmentStorePesananBinding>() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TesDaftarPesananAdapter
    override fun getViewBinding(): FragmentStorePesananBinding = FragmentStorePesananBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.daftar_pesanan_recycleview)
        adapter = TesDaftarPesananAdapter()

        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }

}