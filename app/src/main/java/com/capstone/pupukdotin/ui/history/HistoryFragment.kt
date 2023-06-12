package com.capstone.pupukdotin.ui.history

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.pupukdotin.R
import com.capstone.pupukdotin.databinding.FragmentHistoryBinding
import com.capstone.pupukdotin.ui.adapter.TesHistoryAdapter
import com.capstone.pupukdotin.ui.common.BaseFragment


class HistoryFragment : BaseFragment<FragmentHistoryBinding>() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TesHistoryAdapter

    override fun getViewBinding(): FragmentHistoryBinding = FragmentHistoryBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.HistoryRecyclerView)
        adapter = TesHistoryAdapter()

        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        binding.tvNoHistoryData.isVisible = (recyclerView.adapter?.itemCount ?:0) == 0

    }


}