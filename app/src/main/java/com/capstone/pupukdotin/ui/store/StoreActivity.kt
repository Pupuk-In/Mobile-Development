package com.capstone.pupukdotin.ui.store

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.capstone.pupukdotin.R
import com.capstone.pupukdotin.data.remote.network.NetworkResult
import com.capstone.pupukdotin.databinding.ActivityStoreBinding
import com.capstone.pupukdotin.ui.ViewModelFactory
import com.capstone.pupukdotin.ui.common.BaseActivity

class StoreActivity : BaseActivity<ActivityStoreBinding>() {

    private val viewModel by  viewModels<StoreViewModel> { ViewModelFactory(this) }
    override fun getViewBinding(): ActivityStoreBinding = ActivityStoreBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getOwnedDetailStore()
        setupViewModel()

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentStoreContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        binding.storeBottomNavigationView.setupWithNavController(navController)
    }

    private fun setupViewModel() {
        viewModel.ownedDetailStore.observe(this) { result ->
            when (result) {
                is NetworkResult.Loading -> {

                }

                is NetworkResult.Success -> {
                    setContentView(binding.root)
                }

                is NetworkResult.Error -> {
                    val errorResult = result.error.toString()
                    if(errorResult.contains("Store Not Found", true)){
                        EmptyStoreActivity.start(this@StoreActivity)
                        finish()
                    }
                }
            }

        }
    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, StoreActivity::class.java)
            starter.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            context.startActivity(starter)
        }
    }
}