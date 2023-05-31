package com.capstone.pupukdotin.ui.authentication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.capstone.pupukdotin.databinding.ActivityAuthenticationBinding
import com.capstone.pupukdotin.ui.common.BaseActivity
import com.google.android.material.tabs.TabLayoutMediator

class AuthenticationActivity : BaseActivity<ActivityAuthenticationBinding>(),
    AuthenticationAdapter.MoveFragment {

    private lateinit var viewPager: ViewPager2

    override fun getViewBinding(): ActivityAuthenticationBinding =
        ActivityAuthenticationBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initPager()
    }

    private fun initPager() {
        val sectionsPagerAdapter = AuthenticationAdapter(this)
        viewPager = binding.vpAuthentication
        val tabs = binding.tlAuthentication
        viewPager.adapter = sectionsPagerAdapter

        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = TAB_TITLES[position]
        }.attach()
    }


    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, AuthenticationActivity::class.java)
            context.startActivity(starter)
        }

        private val TAB_TITLES = arrayListOf(
            "Daftar",
            "Masuk"
        )
    }

    override fun moveFragment(destination: Int) {
        if (destination < TAB_TITLES.size && destination >= 0) viewPager.currentItem = destination
    }
}