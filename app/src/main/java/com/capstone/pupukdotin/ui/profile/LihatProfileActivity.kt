package com.capstone.pupukdotin.ui.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.capstone.pupukdotin.data.remote.network.NetworkResult
import com.capstone.pupukdotin.data.remote.response.user.ProfileDetailResponse
import com.capstone.pupukdotin.databinding.ActivityLihatProfileBinding
import com.capstone.pupukdotin.ui.ViewModelFactory
import com.capstone.pupukdotin.ui.common.BaseActivity

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

    private fun setupProfile(profile: ProfileDetailResponse.Profile) {
        with(binding) {
            if(profile.latitude != null && profile.longitude != null) {
                tvAlamatMapsDetail.text = "-"
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