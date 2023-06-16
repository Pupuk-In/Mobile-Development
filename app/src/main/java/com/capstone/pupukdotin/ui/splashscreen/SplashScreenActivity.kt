package com.capstone.pupukdotin.ui.splashscreen

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import com.capstone.pupukdotin.MainActivity
import com.capstone.pupukdotin.MainViewModel
import com.capstone.pupukdotin.data.local.pref.UserPreference
import com.capstone.pupukdotin.data.remote.network.NetworkResult
import com.capstone.pupukdotin.databinding.ActivitySplashScreenBinding
import com.capstone.pupukdotin.ui.ViewModelFactory
import com.capstone.pupukdotin.ui.authentication.AuthenticationActivity
import com.capstone.pupukdotin.ui.common.BaseActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : BaseActivity<ActivitySplashScreenBinding>() {

    private val activityScope = CoroutineScope(Dispatchers.Main)

    override fun getViewBinding(): ActivitySplashScreenBinding = ActivitySplashScreenBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val mainViewModel by viewModels<MainViewModel> { ViewModelFactory(this) }
        mainViewModel.getUser().observe(this) { model ->
            UserPreference.setToken(model.tokenAuth)
        }

        mainViewModel.authCheck.observe(this) { result ->
            when(result) {
                is NetworkResult.Loading -> {

                }

                is NetworkResult.Success -> {
                    MainActivity.start(this)
                    finish()
                }

                is NetworkResult.Error -> {
                    AuthenticationActivity.start(this)
                    finish()
                }
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            @Suppress("DEPRECATION")
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        activityScope.launch {
            delay(2000)
            runOnUiThread {
                mainViewModel.checkAuth()
            }
        }


    }
}