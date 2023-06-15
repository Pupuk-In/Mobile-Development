package com.capstone.pupukdotin.ui.history

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.pupukdotin.data.remote.network.NetworkResult
import com.capstone.pupukdotin.data.remote.response.transaction.GetAllTransactionResponse
import com.capstone.pupukdotin.databinding.FragmentHistoryBinding
import com.capstone.pupukdotin.ui.ViewModelFactory
import com.capstone.pupukdotin.ui.adapter.TesHistoryAdapter
import com.capstone.pupukdotin.ui.common.BaseFragment


class HistoryFragment : BaseFragment<FragmentHistoryBinding>() {

    private val viewModel by viewModels<HistoryViewModel> { ViewModelFactory(requireContext()) }

    override fun getViewBinding(): FragmentHistoryBinding = FragmentHistoryBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAllTransaction()

        setupAdapter()
        setupViewModel()
    }

    private fun setupViewModel() {
        viewModel.getAllTransactionResponse.observe(viewLifecycleOwner) { result ->
            when (result) {
                is NetworkResult.Loading -> {
                    showLoading(true)
                }

                is NetworkResult.Success -> {
                    showLoading(false)
                    setupResultView(result.data.transaction)
                }

                is NetworkResult.Error -> {
                    showLoading(false)
                    val errorResult = result.error
                    showToast(errorResult.toString())
                    Log.d("ini_log_search", errorResult.toString())
                }
            }
        }
    }

    private fun setupResultView(transaction: List<GetAllTransactionResponse.TransactionItem>) {
        binding.HistoryRecyclerView.apply {
            adapter = TesHistoryAdapter(transaction)
        }
        binding.tvNoHistoryData.isVisible = transaction.isEmpty()
    }

    private fun showLoading(value: Boolean) {
        binding.riwayatPembelianProgreessbar.isVisible = value
        binding.HistoryRecyclerView.isVisible = !value
        binding.tvNoHistoryData.isVisible = !value
    }

    private fun setupAdapter() {
        binding.HistoryRecyclerView.apply {
            adapter = TesHistoryAdapter(emptyList())
            layoutManager = LinearLayoutManager(context)
        }
    }


}