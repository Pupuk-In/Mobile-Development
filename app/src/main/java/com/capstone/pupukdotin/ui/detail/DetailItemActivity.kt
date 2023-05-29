package com.capstone.pupukdotin.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.capstone.pupukdotin.R
import com.capstone.pupukdotin.data.remote.network.NetworkResult
import com.capstone.pupukdotin.data.remote.response.DetailItemResponse
import com.capstone.pupukdotin.databinding.ActivityDetailBinding
import com.capstone.pupukdotin.ui.ViewModelFactory
import com.capstone.pupukdotin.ui.common.BaseActivity

class DetailItemActivity : BaseActivity<ActivityDetailBinding>() {

    private val viewModel by viewModels<DetailItemViewModel> { ViewModelFactory(this) }
    private val idItem by lazy { intent.getIntExtra(ID_ITEM, 0) }
    private var itemCount = 0

    override fun getViewBinding(): ActivityDetailBinding =
        ActivityDetailBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.edStockCount.setText(itemCount.toString())

        setupAction()
        setupViewModel()
    }

    private fun setupAction() {
        viewModel.getDetailItem(idItem)
        
        binding.addStock.setOnClickListener {
            itemCount++
            binding.edStockCount.setText(itemCount.toString())
        }

        binding.subtractStock.setOnClickListener {
            if (itemCount > 0) itemCount--
            binding.edStockCount.setText(itemCount.toString())
        }
    }

    private fun setupViewModel() {
        viewModel.detailItem.observe(this@DetailItemActivity) { result ->
            when (result) {
                is NetworkResult.Success -> {
                    showLoading(false)
                    setupView(result.data, true)
                }

                is NetworkResult.Loading -> {
                    showLoading(true)
                }

                is NetworkResult.Error -> {
                    val errorResult = result.error
                    showLoading(false)
                    setupView(isSuccess = false)
                    showToast(errorResult.toString())
                    Log.d("ini_log_login", errorResult.toString())
                }
            }
        }
    }

    private fun showLoading(value: Boolean) {
        binding.pbLoadingScreen.isVisible = value
        binding.svDetailItem.isVisible = !value
    }

    private fun setupView(data: DetailItemResponse? = null, isSuccess: Boolean) {
        if (isSuccess) {
            if (data != null) {
                with(binding) {
                    Glide.with(baseContext)
                        .load(data.item?.picture)
                        .placeholder(R.drawable.placeholder)
                        .into(ivProductImage)

                    tvProductTitle.text = data.item?.name ?: ""
                    tvProductPrice.text = getString(R.string.price, data.item?.price ?: 0)
                    tvRating.text = data.item?.rating ?: "0.0"
                    tvProductSold.text =
                        getString(R.string.product_sold, data.item?.sold ?: 0)
                    tvProductDescription.text =
                        data.item?.description ?: "Tidak Ada Deskripsi"

                    tvStoreName.text = data.item?.storeId?.name ?: "Tidak Ada Nama"
                    tvStoreAddress.text = data.item?.storeId?.address ?: "Tidak Ada Alamat"
                    tvStoreRating.text = data.item?.storeId?.rating ?: "0.0"

                    Glide.with(baseContext)
                        .load(data.item?.storeId?.picture)
                        .placeholder(R.drawable.placeholder)
                        .into(ivStore)
                    tvProductStock.text = getString(R.string.product_stock, data.item?.stock ?: 0)
                }
            }
        } else {
            // TODO("Not yet implemented")
        }
    }

    companion object {
        @JvmStatic
        fun start(context: Context, idItems: Int) {
            val starter = Intent(context, DetailItemActivity::class.java)
                .putExtra(ID_ITEM, idItems)
            context.startActivity(starter)
        }

        private const val ID_ITEM = "id"
    }
}