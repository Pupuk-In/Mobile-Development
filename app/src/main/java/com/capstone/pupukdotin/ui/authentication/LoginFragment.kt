package com.capstone.pupukdotin.ui.authentication

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.capstone.pupukdotin.MainActivity
import com.capstone.pupukdotin.data.local.pref.UserModel
import com.capstone.pupukdotin.data.remote.network.NetworkResult
import com.capstone.pupukdotin.data.remote.payload.LoginPayload
import com.capstone.pupukdotin.databinding.FragmentLoginBinding
import com.capstone.pupukdotin.ui.ViewModelFactory
import com.capstone.pupukdotin.ui.common.BaseFragment

class LoginFragment : BaseFragment<FragmentLoginBinding>() {


    private val viewModel by viewModels<LoginViewModel> { ViewModelFactory(requireActivity()) }
    override fun getViewBinding(): FragmentLoginBinding = FragmentLoginBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupAction()
    }

    private fun setupAction() {
        with(binding) {
            loginButton.setOnClickListener {
                val email = edLoginEmail.text.toString()
                val password = edLoginPassword.text.toString()

                viewModel.login(LoginPayload(email = email, password = password))
            }
        }
    }

    private fun setupViewModel() {
        viewModel.login.observe(viewLifecycleOwner) { result ->
            when (result) {
                is NetworkResult.Success -> {
                    showLoading(false)
                    viewModel.saveUser(
                        UserModel(
                            tokenAuth = result.data.user?.accessToken ?: "",
                            isLogin = true
                        )
                    )
                    Toast.makeText(requireContext(), "Login Berhasil", Toast.LENGTH_SHORT).show()
                    MainActivity.start(requireContext())
                    activity?.finish()
                }
                is NetworkResult.Loading -> {
                    showLoading(true)
                }

                is NetworkResult.Error -> {
                    val errorResult = result.error
                    showLoading(false)
                    Toast.makeText(requireContext(), errorResult.toString(), Toast.LENGTH_SHORT).show()
//                    showToast(errorResult.toString())
                    Log.d("ini_log_login", errorResult.toString())
                }
            }
        }
    }

    private fun showLoading(value: Boolean) {
        binding.loginButton.isInvisible = value
        binding.pbLoadingScreen.isVisible = value
    }

    companion object {
        @JvmStatic
        fun newInstance() = LoginFragment()
    }
}