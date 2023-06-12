package com.capstone.pupukdotin.ui.store

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.capstone.pupukdotin.R
import com.capstone.pupukdotin.databinding.ActivityStoreBinding
import com.capstone.pupukdotin.ui.common.BaseActivity

class StoreActivity : BaseActivity<ActivityStoreBinding>() {
    override fun getViewBinding(): ActivityStoreBinding = ActivityStoreBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentStoreContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        binding.storeBottomNavigationView.setupWithNavController(navController)

    }
}