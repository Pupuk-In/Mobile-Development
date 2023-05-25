package com.capstone.pupukdotin.ui.authentication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.capstone.pupukdotin.data.remote.network.NetworkResult
import com.capstone.pupukdotin.data.remote.payload.RegisterPayload
import com.capstone.pupukdotin.data.remote.response.RegisterResponse
import com.capstone.pupukdotin.databinding.ActivityRegisterBinding
import com.capstone.pupukdotin.ui.ViewModelFactory
import com.capstone.pupukdotin.ui.common.BaseActivity

class RegisterActivity : BaseActivity<ActivityRegisterBinding>() {
    override fun getViewBinding(): ActivityRegisterBinding = ActivityRegisterBinding.inflate(layoutInflater)

    private val viewModel by viewModels<RegisterViewModel> { ViewModelFactory(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupAction()
        setupViewModel()
    }

    private fun setupViewModel() {
        viewModel.register.observe(this@RegisterActivity) { result ->
            when(result) {
                is NetworkResult.Success -> {
                    LoginActivity.start(this@RegisterActivity)
                    showLoading(false)
                }
                is NetworkResult.Loading -> {
                    resetError()
                    showLoading(true)
                }
                is NetworkResult.Error -> {
                    val errorResult = result.error
                    showLoading(false)
                    when(errorResult) {
                        is RegisterResponse -> {
                            val error = errorResult.errors
                            if(error?.email != null) {
                                binding.emailEditTextLayout.error = if (error.email[0].contains("taken")) {
                                    "Email Telah Terdaftar"
                                } else {
                                    "Email harus valid"
                                }
                            }
                            if(error?.username != null) {
                                binding.usernameEditTextLayout.error = if (error.username[0].contains("taken")) {
                                    "Username Telah Terdaftar"
                                } else {
                                    "Username harus valid"
                                }
                            }
                            showToast(errorResult.message)
                            Log.d("ini_log_register", errorResult.message)
                        }
                        else -> {
                            showToast(errorResult.toString())
                            Log.d("ini_log_register2", errorResult.toString())
                        }
                    }

                }
            }

        }
    }

    private fun resetError() {
        with(binding) {
            emailEditTextLayout.error = null
            usernameEditTextLayout.error = null
            passwordEditTextLayout.error = null
            passwordConfirmEditTextLayout.error = null
        }
    }

    private fun showLoading(value: Boolean) {
        with(binding) {
            registerProgressBar.isVisible = value
        }
    }

    private fun setupAction() {
        with(binding) {
            toLoginPage.setOnClickListener {
                LoginActivity.start(this@RegisterActivity)
            }

            RegisterButton.setOnClickListener {
                val username = edRegisterUsername.text.toString()
                val email = edRegisterEmail.text.toString()
                val password = edRegisterPassword.text.toString()
                val passwordConfirm = edRegisterPasswordConfirmation.text.toString()

                when {
                    username.isBlank() -> usernameEditTextLayout.error = "Username tidak boleh kosong"
                    email.isBlank() -> emailEditTextLayout.error = "Email tidak boleh kosong"
                    password != passwordConfirm -> passwordConfirmEditTextLayout.error = "Password tidak sesuai"
                    else -> {
                        viewModel.register(
                            RegisterPayload(
                                username = username,
                                email = email,
                                password = password,
                                passwordConfirmation = passwordConfirm
                            )
                        )
                    }
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, RegisterActivity::class.java)
            context.startActivity(starter)
        }
    }
}