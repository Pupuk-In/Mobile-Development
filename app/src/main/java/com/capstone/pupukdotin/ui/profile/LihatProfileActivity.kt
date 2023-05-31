package com.capstone.pupukdotin.ui.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.capstone.pupukdotin.R
import com.capstone.pupukdotin.databinding.ActivityLihatProfileBinding

class LihatProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLihatProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLihatProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageView3.setOnClickListener{
            goBack(binding.root)
        }

    }

    fun goBack(view: View) {
        onBackPressed()
    }
}