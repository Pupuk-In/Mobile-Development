package com.capstone.pupukdotin.ui.store

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.pupukdotin.databinding.ActivityStoreDetailOrderBinding
import com.capstone.pupukdotin.ui.adapter.DummyOrderModel
import com.capstone.pupukdotin.ui.adapter.ListOrderAdapter
import com.capstone.pupukdotin.ui.common.BaseActivity

class StoreDetailOrderActivity : BaseActivity<ActivityStoreDetailOrderBinding>() {

    private val listOrder = mutableListOf<DummyOrderModel>()
    private lateinit var orderAdapter: ListOrderAdapter

    override fun getViewBinding(): ActivityStoreDetailOrderBinding =
        ActivityStoreDetailOrderBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.topBar.root)
        binding.topBar.apply {
            @SuppressLint("SetTextI18n")
            tvTitleBar.text = "Detail Pemesanan"
            root.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
                finish()
            }
        }
        setupView()
        setupAdapter()
    }

    @SuppressLint("SetTextI18n")
    private fun setupView() {
        with(binding) {
            tvOrderNameDetail.text = "INV/01/NamaStore/050623"
            tvOrderDateDetail.text = "5 Juni 2023, 17:59"
            tvOrderBuyerDetail.text = "Farrel Ibrahim"
//            tvOrderList.isVisible = (rvOrderListDetail.adapter?.itemCount ?: 0) > 0
            tvOrderPaymentMethodDetail.text = "Tunai"
            tvOrderAddressDetail.text = "Jl. Intan RSPP Utara, Kec. Cilandak, Kota Jakarta Selatan, Daerah Khusus Ibukota Jakarta 12430"
            tvTotalPrice.text = "Rp300.000"
            tvTotalProduct.text = "Total Harga (3 Produk)"
        }
    }

    private fun setupAdapter() {
        orderAdapter = ListOrderAdapter(listOrder)
        binding.rvOrderListDetail.apply {
            adapter = orderAdapter
            layoutManager = LinearLayoutManager(
                this@StoreDetailOrderActivity,
                LinearLayoutManager.VERTICAL,
                false
            )
        }
    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, StoreDetailOrderActivity::class.java)
            context.startActivity(starter)
        }
    }
}