package com.capstone.pupukdotin.ui.store

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.pupukdotin.data.remote.network.NetworkResult
import com.capstone.pupukdotin.databinding.FragmentStoreProdukBinding
import com.capstone.pupukdotin.ui.ViewModelFactory
import com.capstone.pupukdotin.ui.adapter.TesDaftarProdukStoreAdapter
import com.capstone.pupukdotin.ui.common.BaseFragment

class StoreProdukFragment : BaseFragment<FragmentStoreProdukBinding>() {

    private val viewModel by viewModels<StoreProdukViewModel> { ViewModelFactory(requireContext()) }
    override fun getViewBinding(): FragmentStoreProdukBinding =
        FragmentStoreProdukBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getAllItems()

        setupViewModel()
        setupAdapter()

    }

    private fun setupAdapter() {
        binding.daftarProdukRecycleview.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = TesDaftarProdukStoreAdapter(emptyList())
        }
    }

    private fun setupViewModel() {
        viewModel.allItems.observe(viewLifecycleOwner) { result ->
            when (result) {
                is NetworkResult.Loading -> {
                    showLoading(true)
                }
                is NetworkResult.Success -> {
                    showLoading(false)
                    binding.daftarProdukRecycleview.adapter =
                        TesDaftarProdukStoreAdapter(result.data.item ?: emptyList())
                }
                is NetworkResult.Error -> {
                    showLoading(false)
                    showToast(result.error.toString())
                }
            }
        }
    }

    private fun showLoading(value: Boolean) {
        binding.daftarPesananProgressbar.isVisible = value
        binding.frameLayoutSearchContainer.isVisible = !value
        binding.buttonTambahProduk.isVisible = !value
        binding.nsvContent.isVisible = !value
    }

}