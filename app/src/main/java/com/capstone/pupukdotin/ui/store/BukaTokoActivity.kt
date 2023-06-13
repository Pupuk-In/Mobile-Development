package com.capstone.pupukdotin.ui.store

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.pupukdotin.R
import com.capstone.pupukdotin.databinding.ActivityBukaTokoBinding
import com.capstone.pupukdotin.ui.common.BaseActivity

class BukaTokoActivity : BaseActivity<ActivityBukaTokoBinding>() {

    override fun getViewBinding(): ActivityBukaTokoBinding = ActivityBukaTokoBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setUpAction()
    }

    private fun setUpAction() {
        binding.apply {
            toolbarBukaToko.btnBack.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }


            buttonBukaToko.setOnClickListener {

                val dialog = Dialog(this@BukaTokoActivity)

                showTwoActionDialog(
                    "Apakah anda yakin ingin membuka toko?",
                    "Ya",
                    "Tidak",
                    {
                        // action untuk membuat toko
                    },
                    {
                        dialog.dismiss()
                    }

                )
            }
        }
    }
}