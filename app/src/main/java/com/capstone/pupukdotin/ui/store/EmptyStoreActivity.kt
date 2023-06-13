package com.capstone.pupukdotin.ui.store

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.pupukdotin.R
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
                onBackPressedDispatcher.onBackPressed()
            }

            buttonBukaToko.setOnClickListener {
                val intent = Intent(this@EmptyStoreActivity, BukaTokoActivity::class.java)
                startActivity(intent)
            }
        }
    }

}