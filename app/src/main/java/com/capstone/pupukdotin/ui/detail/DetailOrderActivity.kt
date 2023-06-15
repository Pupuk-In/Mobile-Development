package com.capstone.pupukdotin.ui.detail

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.pupukdotin.MainActivity
import com.capstone.pupukdotin.R
import com.capstone.pupukdotin.data.remote.network.NetworkResult
import com.capstone.pupukdotin.data.remote.response.transaction.GetDetailTransactionResponse
import com.capstone.pupukdotin.databinding.ActivityDetailOrderBinding
import com.capstone.pupukdotin.ui.ViewModelFactory
import com.capstone.pupukdotin.ui.adapter.ListOrderAdapter
import com.capstone.pupukdotin.ui.common.BaseActivity
import com.capstone.pupukdotin.ui.store.StoreDetailOrderActivity
import com.capstone.pupukdotin.utils.convertTime

class DetailOrderActivity : BaseActivity<ActivityDetailOrderBinding>() {



    private val viewModel by viewModels<DetailOrderViewModel> { ViewModelFactory(this) }

    private val idTransaction by lazy { intent.getIntExtra(ID_TRANSACTION, 0) }

    override fun getViewBinding(): ActivityDetailOrderBinding = ActivityDetailOrderBinding.inflate(layoutInflater)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.topBar.root)
        binding.topBar.apply {
            @SuppressLint("SetTextI18n")
            tvTitleBar.text = "Detail Pemesanan"
            root.setNavigationOnClickListener {
                StoreDetailOrderActivity.start(this@DetailOrderActivity)
            }
        }
        viewModel.getDetailTransaction(idTransaction)

        setupViewModel()
        setupAdapter()
        setupAction()
    }

    private fun setupViewModel() {
        viewModel.getTransactionResponse.observe(this) {result ->
            when(result) {
                is NetworkResult.Loading -> {
                    showLoading(true)
                }
                is NetworkResult.Success -> {
                    showLoading(false)
                    setupSuccessView(result.data.transactionDetail)
                }
                is NetworkResult.Error -> {
                    showLoading(false)
                    showToast(result.error.toString())
                }
            }

        }
    }

    private fun showLoading(value: Boolean) {
        binding.pbLoadingScreen.isVisible = value
        binding.nsvContent.isVisible = !value
    }

    private fun setupSuccessView(data: GetDetailTransactionResponse.TransactionDetail) {
        with(binding) {
            tvOrderNameDetail.text = data.invoice
            tvOrderDateDetail.text = data.createdAt.convertTime("dd MMMM yyyy, HH:mm")
            tvOrderBuyerDetail.text = data.transaction.recipientName
            tvOrderPaymentMethodDetail.text = getString(R.string.tunai)
            tvOrderAddressDetail.text = data.transaction.recipientAddress
            tvTotalPrice.text = getString(R.string.price_format, data.total)
            tvTotalProduct.text = getString(R.string.total_harga_format_produk, data.transactionItem.size)
            rvOrderListDetail.adapter = ListOrderAdapter(data.transactionItem)
        }
    }

    private fun setupAction() {
        binding.btnCancelOrder.setOnClickListener {
            showTwoActionDialogWithSub(
                "Apakah anda yakin?",
                "Pesanan yang anda batalkan tidak dapat diubah kembali",
                "Ya",
                "Tidak",
                onYesClickBtnClicked = {
                    showOneActionDialog("Pesanan Berhasil Dibatalkan", "Okay") {
                        MainActivity.start(this@DetailOrderActivity)
                        finish()
                    }
                }
            )
        }
    }



    private fun setupAdapter() {
        binding.rvOrderListDetail.apply {
            adapter = ListOrderAdapter(emptyList())
            layoutManager = LinearLayoutManager(
                this@DetailOrderActivity,
                LinearLayoutManager.VERTICAL,
                false
            )
        }
    }

    companion object {
        @JvmStatic
        fun start(context: Context, idTransaction: Int) {
            val starter = Intent(context, DetailOrderActivity::class.java)
                .putExtra(ID_TRANSACTION, idTransaction)
            context.startActivity(starter)
        }

        private const val ID_TRANSACTION = "id_transaction"
    }
}