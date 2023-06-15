package com.capstone.pupukdotin.ui.store

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.pupukdotin.data.remote.network.NetworkResult
import com.capstone.pupukdotin.data.remote.response.store.GetAllTransactionStoreResponse
import com.capstone.pupukdotin.databinding.FragmentStorePesananBinding
import com.capstone.pupukdotin.ui.ViewModelFactory
import com.capstone.pupukdotin.ui.adapter.TesDaftarPesananAdapter
import com.capstone.pupukdotin.ui.common.BaseFragment

class StorePesananFragment : BaseFragment<FragmentStorePesananBinding>() {

    private val listOrderDone = mutableListOf<GetAllTransactionStoreResponse.TransactionsItem>()
    private val listOrderOnProgress = mutableListOf<GetAllTransactionStoreResponse.TransactionsItem>()

    private val viewModel by viewModels<StorePesananViewModel> { ViewModelFactory(requireContext()) }

    override fun getViewBinding(): FragmentStorePesananBinding = FragmentStorePesananBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAllTransactionStore()
        setupAdapter()
        setupViewModel()
    }

    private fun setupViewModel() {
        viewModel.getAllTransactionStoreResponse.observe(viewLifecycleOwner) { result ->
            when(result) {
                is NetworkResult.Loading -> {
                    showLoading(true)
                }
                is NetworkResult.Success -> {
                    showLoading(false)
                    setupSuccessData(result.data)
                }
                is NetworkResult.Error -> {
                    showLoading(false)
                    showToast(result.error.toString())
                }
            }
        }
    }

    private fun setupSuccessData(data: GetAllTransactionStoreResponse) {
        data.transaction.forEach {
            if (it.status == "belum konfirmasi") listOrderOnProgress.addAll(it.transactions)
            else listOrderDone.addAll(it.transactions)
        }

        with(binding) {
            daftarPesananRecycleview.adapter = TesDaftarPesananAdapter(listOrderOnProgress)
            daftarPesananDoneRecycleview.adapter = TesDaftarPesananAdapter(listOrderDone)

            tidakAdaPesananOndone.isVisible = listOrderDone.isEmpty()
            tidakAdaPesananOnprogress.isVisible = listOrderOnProgress.isEmpty()
        }
    }


    private fun showLoading(value: Boolean) {
        binding.daftarPesananProgressbar.isVisible = value
        binding.nsvContent.isVisible = !value
    }

    private fun setupAdapter() {
        binding.daftarPesananRecycleview.apply {
            adapter = TesDaftarPesananAdapter(emptyList())
            layoutManager = LinearLayoutManager(context)
        }

        binding.daftarPesananDoneRecycleview.apply {
            adapter = TesDaftarPesananAdapter(emptyList())
            layoutManager = LinearLayoutManager(context)
        }
    }

}