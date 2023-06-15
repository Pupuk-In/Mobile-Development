package com.capstone.pupukdotin.ui.store

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.capstone.pupukdotin.MainActivity
import com.capstone.pupukdotin.databinding.ActivityEmptyStoreBinding
import com.capstone.pupukdotin.ui.common.BaseActivity

class EmptyStoreActivity : BaseActivity<ActivityEmptyStoreBinding>() {

    override fun getViewBinding(): ActivityEmptyStoreBinding = ActivityEmptyStoreBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setUpAction()
    }

    private fun setUpAction() {
        binding.apply {
            btnBack.setOnClickListener {
                MainActivity.start(this@EmptyStoreActivity)
            }

            buttonBukaToko.setOnClickListener {
                val intent = Intent(this@EmptyStoreActivity, BukaTokoActivity::class.java)
                startActivity(intent)
            }
        }
    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, EmptyStoreActivity::class.java)
            context.startActivity(starter)
        }
    }
}