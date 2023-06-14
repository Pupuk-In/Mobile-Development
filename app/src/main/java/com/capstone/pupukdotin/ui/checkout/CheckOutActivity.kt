package com.capstone.pupukdotin.ui.checkout

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.pupukdotin.R
import com.capstone.pupukdotin.data.remote.network.NetworkResult
import com.capstone.pupukdotin.data.remote.response.carts.CartItemsResponse
import com.capstone.pupukdotin.databinding.ActivityCheckOutBinding
import com.capstone.pupukdotin.ui.ViewModelFactory
import com.capstone.pupukdotin.ui.adapter.TesCheckoutAdapter
import com.capstone.pupukdotin.ui.common.BaseActivity

class CheckOutActivity : BaseActivity<ActivityCheckOutBinding>() {

    private val viewModel by viewModels<CheckOutViewModel> { ViewModelFactory(this) }


    override fun getViewBinding(): ActivityCheckOutBinding = ActivityCheckOutBinding.inflate(layoutInflater)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.getCartItems()

        setUpMetodePengiriman()
        setUpRecycleView()
        setUpMetodePembayaran()
        setUpAction()
        setupViewModel()
    }

    private fun setupViewModel() {
        viewModel.cartItem.observe(this) { result ->
            when(result) {
                is NetworkResult.Loading -> {
                    showLoading(true)
                }

                is NetworkResult.Success -> {
                    showLoading(false)
                    setupSuccessView(result.data.cart)
                }

                is NetworkResult.Error -> {
                    showLoading(false)
                }
            }
        }
    }

    private fun setupSuccessView(cart: CartItemsResponse.Cart?) {
        binding.rvCheckoutItems.adapter = TesCheckoutAdapter(cart?.cartItem ?: emptyList())
    }

    private fun showLoading(value: Boolean) {
        binding.apply {
            checkoutProgressbar.isVisible = value
            nsvContent.isVisible = !value
        }
    }

    private fun setUpAction() {
        binding.toolbarCheckout.btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        //tombol pesan
        binding.checkoutButtonPesan.setOnClickListener {

            val dialogConfirmBinding = layoutInflater.inflate(R.layout.dialog_confirmation, null)

            val confirmationDialog = Dialog(this)
            confirmationDialog.setContentView(dialogConfirmBinding)

            confirmationDialog.setCancelable(false)
            confirmationDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            confirmationDialog.show()

            val yaBtn = dialogConfirmBinding.findViewById<Button>(R.id.ya_button)
            yaBtn.setOnClickListener{
                val dialogBinding = layoutInflater.inflate(R.layout.dialog_pesanan_berhasil, null)

                val pesananBerhasilDialog = Dialog(this)
                pesananBerhasilDialog.setContentView(dialogBinding)

                pesananBerhasilDialog.setCancelable(false)
                pesananBerhasilDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                pesananBerhasilDialog.show()

                val lihatStatusBtn = dialogBinding.findViewById<Button>(R.id.lihat_status_dialog_button)
                lihatStatusBtn.setOnClickListener{
                    //isi ini untuk pindah ke halaman status
                }

                val kembaliBtn = dialogBinding.findViewById<Button>(R.id.kembali_dialog_button)
                kembaliBtn.setOnClickListener{
                    pesananBerhasilDialog.dismiss()
                    confirmationDialog.dismiss()
                }
            }

            val tidakBtn = dialogConfirmBinding.findViewById<Button>(R.id.tidak_button)
            tidakBtn.setOnClickListener{
                confirmationDialog.dismiss()
            }
        }
    }


    private fun setUpMetodePembayaran() {
        binding.apply {
            metodePembayaranButton.setOnCheckedChangeListener { group, checkedId ->
                if (checkedId == R.id.button_tunai){
                    buttonTunai.setTextColor(resources.getColor(R.color.white))
                    buttonDompetDigital.setTextColor(resources.getColor(R.color.green_13C193)) 
                }
                else{
                    buttonTunai.setTextColor(resources.getColor(R.color.green_13C193))
                    buttonDompetDigital.setTextColor(resources.getColor(R.color.white))
                }
            }
        }
    }

    private fun setUpRecycleView() {
        binding.rvCheckoutItems.apply {
            adapter = TesCheckoutAdapter(emptyList())
            layoutManager = LinearLayoutManager(this@CheckOutActivity)
        }
    }


    private fun setUpMetodePengiriman() {
        binding.apply {
            metodePengirimanButton.setOnCheckedChangeListener { group, checkedId ->
                if (checkedId == R.id.button_dikirim){
                    buttonDikirim.setTextColor(resources.getColor(R.color.white))
                    buttonAmbilSendiri.setTextColor(resources.getColor(R.color.green_13C193))
                }
                else{
                    buttonDikirim.setTextColor(resources.getColor(R.color.green_13C193))
                    buttonAmbilSendiri.setTextColor(resources.getColor(R.color.white))
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, CheckOutActivity::class.java)
            context.startActivity(starter)
        }
    }
}