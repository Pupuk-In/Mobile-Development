package com.capstone.pupukdotin.ui.store

import android.content.Intent
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.capstone.pupukdotin.R
import com.capstone.pupukdotin.data.remote.network.NetworkResult
import com.capstone.pupukdotin.data.remote.response.common.Store
import com.capstone.pupukdotin.databinding.FragmentStoreHomeBinding
import com.capstone.pupukdotin.ui.ViewModelFactory
import com.capstone.pupukdotin.ui.common.BaseFragment
import java.io.IOException
import java.util.Locale


class StoreHomeFragment : BaseFragment<FragmentStoreHomeBinding>() {

    private val viewModel by viewModels<StoreHomeViewModel> { ViewModelFactory(requireContext()) }
    override fun getViewBinding(): FragmentStoreHomeBinding =
        FragmentStoreHomeBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getOwnedDetailStore()
        setUpAction()
        setupViewModel()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getOwnedDetailStore()
    }

    private fun setupViewModel() {
        viewModel.ownedDetailStore.observe(viewLifecycleOwner) { result ->
            when (result) {
                is NetworkResult.Loading -> {
                    showLoading(true)
                }

                is NetworkResult.Success -> {
                    showLoading(false)
                    setupContentSuccess(result.data.store)
                }

                is NetworkResult.Error -> {
                    showLoading(false)
                    showToast(result.error.toString())
                }
            }

        }
    }

    private fun setupContentSuccess(data: Store?) {
        with(binding) {
            Glide.with(requireContext())
                .load(data?.picture)
                .placeholder(R.drawable.placeholder)
                .into(profileStorePicture)
            tvNamaStore.text = data?.name ?: "Tidak Ada Nama Toko"
            deskripsiStore.text = data?.description ?:"Tidak ada Deskripsi"
            detailAlamatStore.text = data?.address ?:"Tidak ada alamat"

            if(data?.latitude != null && data.longitude != null) {
                mapDetailAlamatStore.text = setupMapAddress(data.latitude, data.longitude)
                textView.isVisible = true
            } else {
                mapDetailAlamatStore.text = "-"
                textView.isVisible = false
            }
        }
    }

    private fun setupMapAddress(latitude: Double, longitude: Double): String {
        var addressName: String? = null
        val geocoder = Geocoder(requireContext(), Locale("id", "ID"))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            geocoder.getFromLocation(latitude, longitude, 1) { list ->
                if (list.size != 0) {
                    addressName = list[0].getAddressLine(0)
                }
            }
        } else {
            try {
                @Suppress("DEPRECATION")
                val list = geocoder.getFromLocation(latitude, longitude, 1)
                if (list != null && list.size != 0) {
                    addressName = list[0].getAddressLine(0)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return addressName ?: ""
    }

    private fun showLoading(value: Boolean) {
        binding.storeHomeProgressbar.isVisible = value
        binding.nsvContent.isVisible = !value
        binding.buttonUbahProfilStore.isVisible = !value
    }


    private fun setUpAction() {
        binding.buttonUbahProfilStore.setOnClickListener {
            val intent = Intent(requireActivity(), EditStoreProfileActivity::class.java)
            startActivity(intent)
        }
    }
}