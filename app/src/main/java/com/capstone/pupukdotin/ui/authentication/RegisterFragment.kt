package com.capstone.pupukdotin.ui.authentication

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.capstone.pupukdotin.data.remote.network.NetworkResult
import com.capstone.pupukdotin.data.remote.payload.RegisterPayload
import com.capstone.pupukdotin.data.remote.response.RegisterResponse
import com.capstone.pupukdotin.databinding.FragmentRegisterBinding
import com.capstone.pupukdotin.ui.ViewModelFactory

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<RegisterViewModel> { ViewModelFactory(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAction()
        setupViewModel()
    }

    private fun setupViewModel() {
        viewModel.register.observe(viewLifecycleOwner) { result ->
            when(result) {
                is NetworkResult.Success -> {
                    showLoading(false)
                    val mf = activity as AuthenticationAdapter.MoveFragment
                    mf.moveFragment(1)
                    emptyEditText()
                    Toast.makeText(requireContext(), "Berhasil Daftar", Toast.LENGTH_SHORT).show()
                }

                is NetworkResult.Loading -> {
                    showLoading(true)
                    resetError()
                }

                is NetworkResult.Error -> {
                    showLoading(false)
                    when (val errorResult = result.error) {
                        is RegisterResponse -> {
                            val error = errorResult.errors
                            if (error?.email != null) {
                                binding.emailEditTextLayout.error =
                                    if (error.email[0].contains("taken")) {
                                        "Email Telah Terdaftar"
                                    } else {
                                        "Email harus valid"
                                    }
                            }
                            if (error?.username != null) {
                                binding.usernameEditTextLayout.error =
                                    if (error.username[0].contains("taken")) {
                                        "Username Telah Terdaftar"
                                    } else {
                                        "Username harus valid"
                                    }
                            }
                            Log.d("ini_log_register", errorResult.message)
                        }

                        else -> {
                            Log.d("ini_log_register2", errorResult.toString())
                        }
                    }
                }
            }
        }
    }

    private fun emptyEditText() {
        with(binding) {
            edRegisterUsername.setText(String())
            edRegisterEmail.setText(String())
            edRegisterPassword.setText(String())
        }
    }

    private fun showLoading(value: Boolean) {
        binding.pbLoadingScreen.isVisible = value
        binding.registerButton.isInvisible = value
    }

    private fun setupAction() {
        with(binding) {
            registerButton.setOnClickListener {
                val username = edRegisterUsername.text.toString()
                val email = edRegisterEmail.text.toString()
                val password = edRegisterPassword.text.toString()

                when {
                    username.isBlank() -> usernameEditTextLayout.error =
                        "Username tidak boleh kosong"

                    email.isBlank() -> emailEditTextLayout.error = "Email tidak boleh kosong"
                    password.isBlank() -> passwordEditTextLayout.error =
                        "Password tidak boleh kosong"

                    else -> {
                        viewModel.register(
                            RegisterPayload(
                                username = username,
                                email = email,
                                password = password
                            )
                        )
                    }
                }
            }
        }
    }

    private fun resetError() {
        with(binding) {
            emailEditTextLayout.isErrorEnabled = false
            usernameEditTextLayout.isErrorEnabled = false
            passwordEditTextLayout.isErrorEnabled = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = RegisterFragment()
    }
}