package com.capstone.pupukdotin.ui.store

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.capstone.pupukdotin.R
import com.capstone.pupukdotin.databinding.ActivityEditStoreProfileBinding
import com.capstone.pupukdotin.databinding.ActivityLihatProfileBinding

class EditStoreProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditStoreProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditStoreProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            goBack(binding.root)
        }
    }

    fun goBack(view: View) {
        onBackPressed()
    }
}