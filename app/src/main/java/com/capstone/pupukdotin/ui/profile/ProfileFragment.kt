package com.capstone.pupukdotin.ui.profile

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.capstone.pupukdotin.R
import com.capstone.pupukdotin.data.remote.network.NetworkResult
import com.capstone.pupukdotin.data.remote.response.user.ProfileDetailResponse
import com.capstone.pupukdotin.databinding.FragmentProfileBinding
import com.capstone.pupukdotin.ui.ViewModelFactory
import com.capstone.pupukdotin.ui.authentication.AuthenticationActivity
import com.capstone.pupukdotin.ui.common.BaseFragment
import com.capstone.pupukdotin.ui.store.StoreActivity
import com.capstone.pupukdotin.utils.convertTime

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    private val viewModel by viewModels<ProfileViewModel> { ViewModelFactory(requireContext()) }
    override fun getViewBinding(): FragmentProfileBinding =
        FragmentProfileBinding.inflate(layoutInflater)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getProfile()
        setupAction()
        setupViewModel()
    }

    private fun setupViewModel() {
        viewModel.profile.observe(viewLifecycleOwner) { result ->
            when (result) {
                is NetworkResult.Success -> {
                    showLoading(false)
                    setupProfile(result.data.profile)
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
                    Log.d("ini_log_fragment", errorResult.toString())
                }
            }
        }

        viewModel.logout.observe(viewLifecycleOwner) { result ->
            when (result) {
                is NetworkResult.Success -> {
                    Toast.makeText(requireContext(), result.data.message, Toast.LENGTH_SHORT)
                        .show()
                    AuthenticationActivity.start(requireContext())
                    activity?.finish()
                }

                is NetworkResult.Loading -> {
                    showLoading(true)
                }

                is NetworkResult.Error -> {
                    val errorResult = result.error
                    showLoading(false)
                    Toast.makeText(requireContext(), errorResult.toString(), Toast.LENGTH_SHORT)
                        .show()
                    Log.d("ini_log_fragment", errorResult.toString())
                }
            }
        }
    }

    private fun setupProfile(data: ProfileDetailResponse.Profile?) {
        binding.apply {
            Glide.with(requireView())
                .load(data?.picture)
                .placeholder(R.drawable.placeholder)
                .into(profilePicture)
            profileFullName.text = data?.name ?:"Tidak Ada Nama Lengkap"
            profileUsername.text = data?.username ?:"Tidak Ada Username"
            profileTanggalBergabung.text = getString(R.string.joined_since, data?.createdAt?.convertTime("yyyy"))
        }
    }

    private fun showLoading(value: Boolean) {
        binding.apply {
            pbLoadingScreen.isVisible = value
            svContent.isVisible = !value
        }
    }

    private fun setupAction() {
        binding.buttonLihatProfil.setOnClickListener {
            LihatProfileActivity.start(requireActivity())
        }

        binding.buttonLihatToko.setOnClickListener {
            StoreActivity.start(requireActivity())
        }

        binding.keluar.setOnClickListener {

            val logoutBinding = layoutInflater.inflate(R.layout.dialog_logout_confirmation, null)

            val logoutDialog = Dialog(requireContext())
            logoutDialog.setContentView(logoutBinding)

            logoutDialog.setCancelable(false)
            logoutDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            logoutDialog.show()

            val yesBtn = logoutBinding.findViewById<Button>(R.id.ya_button)
            yesBtn.setOnClickListener{
                viewModel.logoutUser()
                AuthenticationActivity.start(requireContext())
                activity?.finish()
            }

            val tidakBtn = logoutBinding.findViewById<Button>(R.id.tidak_button)
            tidakBtn.setOnClickListener{
                logoutDialog.dismiss()
            }
        }

    }
}