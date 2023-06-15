package com.capstone.pupukdotin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.capstone.pupukdotin.databinding.ActivityMainBinding
import com.capstone.pupukdotin.ui.aisearch.NutritionDetectionActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        binding.bottomNavigationView.apply {
            background = null
            menu.getItem(2).isEnabled = false
        }

        setContentView(binding.root)

//        val mainViewModel by viewModels<MainViewModel> { ViewModelFactory(this@MainActivity) }
//        mainViewModel.getUser().observe(this) { model ->
//            if(!model.isLogin) {
//                AuthenticationActivity.start(this@MainActivity)
//                finish()
//            } else {
//                UserPreference.setToken(model.tokenAuth)
//                setContentView(binding.root)
//                Log.d("ini_log", model.tokenAuth)
//            }
//        }

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)

        binding.fab.setOnClickListener {
            NutritionDetectionActivity.start(this)
        }
    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, MainActivity::class.java)
            starter.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            context.startActivity(starter)
        }
    }
}