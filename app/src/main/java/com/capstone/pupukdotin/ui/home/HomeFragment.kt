package com.capstone.pupukdotin.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.pupukdotin.data.remote.network.NetworkResult
import com.capstone.pupukdotin.data.remote.response.PlantResponse
import com.capstone.pupukdotin.data.remote.response.TypeResponse
import com.capstone.pupukdotin.databinding.FragmentHomeBinding
import com.capstone.pupukdotin.ui.ViewModelFactory
import com.capstone.pupukdotin.ui.adapter.PlantFertilizerAdapter
import com.capstone.pupukdotin.ui.adapter.TypeFertilizerAdapter
import com.capstone.pupukdotin.ui.common.BaseFragment
import com.capstone.pupukdotin.ui.search.SearchResultActivity
import com.capstone.pupukdotin.ui.wishlist.WishlistActivity

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private lateinit var typeAdapter: TypeFertilizerAdapter

    private lateinit var plantAdapter: PlantFertilizerAdapter

    private val viewModel by viewModels<HomeViewModel> { ViewModelFactory(requireContext()) }
    override fun getViewBinding(): FragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupViewModel()
        setupAction()
    }

    private fun setupView() {
        viewModel.getTypes()
        viewModel.getPlants()
    }

    private fun setupAction() {
        binding.homeSearch.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                SearchResultActivity.start(requireActivity(), query = query)
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean = false
        })

        binding.ivWishlist.setOnClickListener {
            WishlistActivity.start(requireActivity())
        }
    }

    private fun setupViewModel() {
        viewModel.types.observe(viewLifecycleOwner) { result ->
            when (result) {
                is NetworkResult.Success -> {
                    showLoading(false)
                    setTypesAdapter(result.data)
                }

                is NetworkResult.Loading -> {
                    showLoading(true)
                }

                is NetworkResult.Error -> {
                    val errorResult = result.error
                    showLoading(false)
                    Toast.makeText(requireContext(), errorResult.toString(), Toast.LENGTH_SHORT)
                        .show()
                    Log.d("ini_log_login", errorResult.toString())
                }
            }
        }


        viewModel.plants.observe(viewLifecycleOwner) { result ->
            when (result) {
                is NetworkResult.Success -> {
                    showLoading(false)
                    setPlantAdapter(result.data)
                }

                is NetworkResult.Loading -> {
                    showLoading(true)
                }

                is NetworkResult.Error -> {
                    val errorResult = result.error
                    showLoading(false)
                    Toast.makeText(requireContext(), errorResult.toString(), Toast.LENGTH_SHORT)
                        .show()
//                    showToast(errorResult.toString())
                    Log.d("ini_log_login", errorResult.toString())
                }
            }
        }
    }

    private fun showLoading(value: Boolean) {
        binding.apply {
            pbLoadingScreen.isVisible = value
            nsvContent.isVisible = !value
        }
    }

    private fun setTypesAdapter(types: TypeResponse) {
        typeAdapter = TypeFertilizerAdapter(types.type ?: emptyList())
        binding.rvJenisPupuk.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = typeAdapter
        }
    }

    private fun setPlantAdapter(plants: PlantResponse) {
        plantAdapter = PlantFertilizerAdapter(plants.plant ?: emptyList())
        binding.rvJenisTanaman.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = plantAdapter
        }
    }
}