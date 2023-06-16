package com.capstone.pupukdotin.ui.store

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.capstone.pupukdotin.MainActivity
import com.capstone.pupukdotin.R
import com.capstone.pupukdotin.data.remote.network.NetworkResult
import com.capstone.pupukdotin.data.remote.response.common.Store
import com.capstone.pupukdotin.databinding.FragmentStoreProfileBinding
import com.capstone.pupukdotin.ui.ViewModelFactory
import com.capstone.pupukdotin.ui.authentication.AuthenticationActivity
import com.capstone.pupukdotin.ui.common.BaseFragment
import com.capstone.pupukdotin.utils.convertTime

class StoreProfileFragment : BaseFragment<FragmentStoreProfileBinding>() {

    private val viewModel by viewModels<StoreProfileViewModel> { ViewModelFactory(requireContext()) }
    override fun getViewBinding(): FragmentStoreProfileBinding =
        FragmentStoreProfileBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupAction()

        viewModel.getProfile()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getProfile()
    }

    private fun setupViewModel() {
        viewModel.getprofile.observe(viewLifecycleOwner) { result ->
            when(result) {
                is NetworkResult.Loading -> {
                    showLoading(true)
                }
                is NetworkResult.Success -> {
                    showLoading(false)
                    setupSuccessView(result.data.store)
                }
                is NetworkResult.Error -> {
                    showLoading(false)
                }
            }

        }
    }

    private fun setupSuccessView(store: Store?) {
        with(binding) {
            Glide.with(this@StoreProfileFragment)
                .load(store?.picture ?:"")
                .placeholder(R.drawable.placeholder)
                .into(storeProfilePicture)

            profileStoreFullName.text = store?.name ?:"Tidak Ada Nama Store"
            profileStoreTanggalBergabung.text = getString(R.string.joined_since,
                store?.createdAt?.convertTime("yyyy")
            )
        }
    }

    private fun showLoading(value: Boolean) {
        binding.pbLoadingScreen.isVisible = value
        binding.svContent.isVisible = !value

    }

    private fun setupAction() {
        binding.buttonBeralihPembeli.setOnClickListener {
            MainActivity.start(requireActivity())
            activity?.finish()
        }

        binding.keluarStore.setOnClickListener {
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