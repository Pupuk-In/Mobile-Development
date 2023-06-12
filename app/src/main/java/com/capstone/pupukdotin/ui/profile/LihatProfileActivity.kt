package com.capstone.pupukdotin.ui.profile

import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.capstone.pupukdotin.R
import com.capstone.pupukdotin.data.remote.network.NetworkResult
import com.capstone.pupukdotin.data.remote.response.user.ProfileDetailResponse
import com.capstone.pupukdotin.databinding.ActivityLihatProfileBinding
import com.capstone.pupukdotin.ui.ViewModelFactory
import com.capstone.pupukdotin.ui.common.BaseActivity
import java.io.IOException
import java.util.Locale

class LihatProfileActivity : BaseActivity<ActivityLihatProfileBinding>() {

    private val viewModel by viewModels<LihatProfileViewModel> { ViewModelFactory(this) }
    override fun getViewBinding(): ActivityLihatProfileBinding =
        ActivityLihatProfileBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel.getProfile()
        setUpAction()
        setupViewModel()
    }

    private fun setupViewModel() {
        viewModel.profile.observe(this) { result ->
            when (result) {
                is NetworkResult.Success -> {
                    showLoading(false)
                    setupProfile(result.data.profile as ProfileDetailResponse.Profile)
                }

                is NetworkResult.Loading -> {
                    showLoading(true)
                }

                is NetworkResult.Error -> {
                    val errorResult = result.error
                    showLoading(false)
                    showToast(errorResult.toString())
                    Log.d("ini_log_fragment", errorResult.toString())
                }
            }
        }
    }

    private fun setupMapAddress(newLat: Double, newLong: Double) : String{
        var addressName: String? = null
        val geocoder = Geocoder(this, Locale("id", "ID"))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            geocoder.getFromLocation(newLat, newLong, 1) { list ->
                if (list.size != 0) {
                    addressName = list[0].getAddressLine(0)
                }
            }
        } else {
            try {
                @Suppress("DEPRECATION")
                val list = geocoder.getFromLocation(newLat, newLong, 1)
                if (list != null && list.size != 0) {
                    addressName = list[0].getAddressLine(0)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return addressName ?: ""
    }

    private fun setupProfile(profile: ProfileDetailResponse.Profile) {
        with(binding) {
            Glide.with(this@LihatProfileActivity)
                .load(profile.picture)
                .placeholder(R.drawable.placeholder)
                .into(profileDetailPicture)

            if(profile.latitude != null && profile.longitude != null) {
                tvAlamatMapsDetail.text = setupMapAddress(profile.latitude, profile.longitude)
                tvLihatPeta.isVisible = true
            } else {
                tvAlamatMapsDetail.text = "-"
                tvLihatPeta.isVisible = false
            }
            tvNamaLengkapDetail.text = profile.name ?: "Tidak ada Nama Lengkap"
            tvAlamatDetail.text = profile.address ?:"Tidak ada alamat"
            tvNomorTeleponDetail.text = profile.phoneNumber ?:"Tidak ada Nomor Telepon"
            tvTanggalLahirDetail.text = profile.birthDate ?:"2001-01-01"
        }
    }

    private fun showLoading(value: Boolean) {
        binding.apply {
            lihatProfileProgressbar.isVisible = value
            svContent.isVisible = !value
            include.buttonUbahProfil.isVisible = !value
        }

    }

    private fun setUpAction() {
        binding.include.btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
            finish()
        }

        binding.include.buttonUbahProfil.setOnClickListener {
            UbahProfileActivity.start(this@LihatProfileActivity)
        }
    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, LihatProfileActivity::class.java)
            context.startActivity(starter)
        }
    }
}