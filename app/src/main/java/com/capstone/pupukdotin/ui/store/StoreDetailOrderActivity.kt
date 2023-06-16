package com.capstone.pupukdotin.ui.store

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.pupukdotin.R
import com.capstone.pupukdotin.data.remote.network.NetworkResult
import com.capstone.pupukdotin.data.remote.payload.transaction.UpdateTransactionPayload
import com.capstone.pupukdotin.data.remote.response.transaction.GetDetailTransactionResponse
import com.capstone.pupukdotin.databinding.ActivityStoreDetailOrderBinding
import com.capstone.pupukdotin.ui.ViewModelFactory
import com.capstone.pupukdotin.ui.adapter.ListOrderAdapter
import com.capstone.pupukdotin.ui.common.BaseActivity
import com.capstone.pupukdotin.utils.convertTime

class StoreDetailOrderActivity : BaseActivity<ActivityStoreDetailOrderBinding>() {

    private val idTransaction by lazy { intent.getIntExtra(ID_TRANSACTION, 0) }
    private val viewModel by viewModels<StoreDetailOrderViewModel> { ViewModelFactory(this) }

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

        viewModel.getDetailTransactionStore(idTransaction)
        setupViewModel()
        setupAdapter()
        setupAction()
    }

    private fun setupAction() {
        binding.btnCancelOrder.setOnClickListener {
            showTwoActionDialogWithSub(
                "Apakah anda yakin?",
                "Pesanan yang anda batalkan tidak dapat diubah kembali",
                "Ya",
                "Tidak",
                onYesClickBtnClicked = {
                    viewModel.updateStatusTransactionStore(
                        UpdateTransactionPayload(3), idTransaction
                    )
                },
                onNoClickBtnClicked = null
            )
        }

        binding.btnAcceptOrder.setOnClickListener {
            showTwoActionDialogWithSub(
                "Apakah anda yakin?",
                "Pastikan Pesanan tersebut telah sesuai",
                "Ya",
                "Tidak",
                onYesClickBtnClicked = {
                    viewModel.updateStatusTransactionStore(
                        UpdateTransactionPayload(7), idTransaction
                    )
                },
                onNoClickBtnClicked = null
            )
        }
    }

    private fun setupViewModel() {
        viewModel.getTransactionStoreResponse.observe(this) { result ->
            when (result) {
                is NetworkResult.Loading -> {
                    showLoading(true)
                }

                is NetworkResult.Success -> {
                    showLoading(false)
                    setupSuccessData(result.data.transactionDetail)
                }

                is NetworkResult.Error -> {
                    showLoading(false)
                    showToast(result.error.toString())
                }
            }
        }

        viewModel.updateStatusTransactionResponse.observe(this) { result ->
            when (result) {
                is NetworkResult.Loading -> {
                    showLoading(true)
                }

                is NetworkResult.Success -> {
                    showOneActionDialog("Transaksi Berhasil Diubah", "Okay") {
                        onBackPressedDispatcher.onBackPressed()
                    }
                    showLoading(false)
                }

                is NetworkResult.Error -> {
                    showLoading(false)
                    showToast(result.error.toString())
                }
            }
        }
    }

    private fun setupSuccessData(data: GetDetailTransactionResponse.TransactionDetail) {
        with(binding) {
            tvOrderNameDetail.text = data.invoice
            tvOrderDateDetail.text = data.createdAt.convertTime("dd MMMM yyyy, HH:mm")
            tvOrderBuyerDetail.text = data.transaction.recipientName
            tvOrderPaymentMethodDetail.text = getString(R.string.tunai)
            tvOrderAddressDetail.text = data.transaction.recipientAddress
            tvTotalPrice.text = getString(R.string.price_format, data.total)
            tvTotalProduct.text =
                getString(R.string.total_harga_format_produk, data.transactionItem.size)
            rvOrderListDetail.adapter = ListOrderAdapter(data.transactionItem)

            when (data.transactionStatusId) {
                1 -> {
                    tvStatus.isVisible = true
                    tvStatus.text = getString(R.string.belum_diproses)
                    tvStatus.setTextColor(ContextCompat.getColor(this@StoreDetailOrderActivity, R.color.orange_9D6129))
                }
                3 -> {
                    tvStatus.isVisible = true
                    tvStatus.text = getString(R.string.cancelled)
                    tvStatus.setTextColor(ContextCompat.getColor(this@StoreDetailOrderActivity, R.color.red_B00020))
                    btnAcceptOrder.isVisible = false
                    btnCancelOrder.isVisible = false
                }
                7 -> {
                    tvStatus.isVisible = true
                    tvStatus.text = getString(R.string.done)
                    tvStatus.setTextColor(ContextCompat.getColor(this@StoreDetailOrderActivity, R.color.green_13C193))
                    btnAcceptOrder.isVisible = false
                    btnCancelOrder.isVisible = false
                }
                else -> tvStatus.isVisible = false
            }
        }
    }

    private fun showLoading(value: Boolean) {
        binding.pbLoadingScreen.isVisible = value
        binding.nsvContent.isVisible = !value
    }

    private fun setupAdapter() {
        binding.rvOrderListDetail.apply {
            adapter = ListOrderAdapter(emptyList())
            layoutManager = LinearLayoutManager(
                this@StoreDetailOrderActivity,
                LinearLayoutManager.VERTICAL,
                false
            )
        }
    }

    companion object {
        @JvmStatic
        fun start(context: Context, idTransaction: Int) {
            val starter = Intent(context, StoreDetailOrderActivity::class.java)
                .putExtra(ID_TRANSACTION, idTransaction)
            context.startActivity(starter)
        }

        private const val ID_TRANSACTION = "id_transaction"
    }
}