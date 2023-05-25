package com.capstone.pupukdotin.ui.authentication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.capstone.pupukdotin.MainActivity
import com.capstone.pupukdotin.data.local.pref.UserModel
import com.capstone.pupukdotin.data.remote.network.NetworkResult
import com.capstone.pupukdotin.data.remote.payload.LoginPayload
import com.capstone.pupukdotin.databinding.ActivityLoginBinding
import com.capstone.pupukdotin.ui.ViewModelFactory
import com.capstone.pupukdotin.ui.common.BaseActivity

class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    private val viewModel by viewModels<LoginViewModel> { ViewModelFactory(this) }

    override fun getViewBinding(): ActivityLoginBinding =
        ActivityLoginBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupAction()
        setupViewModel()
    }

    private fun setupViewModel() {
        viewModel.login.observe(this@LoginActivity) { result ->
            when (result) {
                is NetworkResult.Success -> {
                    showLoading(false)
                    viewModel.saveUser(
                        UserModel(
                            tokenAuth = result.data.user?.accessToken ?: "",
                            isLogin = true
                        )
                    )
                    MainActivity.start(this@LoginActivity)
                    finish()
                }

                is NetworkResult.Loading -> {
                    showLoading(true)
                }

                is NetworkResult.Error -> {
                    val errorResult = result.error
                    showLoading(false)
                    showToast(errorResult.toString())
                    Log.d("ini_log_login", errorResult.toString())
                }
            }
        }
    }

    private fun showLoading(value: Boolean) {
        with(binding) {
            loginProgressBar.isVisible = value
        }
    }

    private fun setupAction() {
        with(binding) {
            toRegisterPage.setOnClickListener {
                RegisterActivity.start(this@LoginActivity)
            }
            loginButton.setOnClickListener {
                val email = edLoginEmail.text.toString()
                val password = edLoginPassword.text.toString()

                viewModel.login(LoginPayload(email = email, password = password))
            }

        }
    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, LoginActivity::class.java)
            context.startActivity(starter)
        }
    }
}