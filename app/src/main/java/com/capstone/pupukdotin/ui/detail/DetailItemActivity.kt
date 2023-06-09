package com.capstone.pupukdotin.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.capstone.pupukdotin.R
import com.capstone.pupukdotin.data.model.StoreModel
import com.capstone.pupukdotin.data.remote.network.NetworkResult
import com.capstone.pupukdotin.data.remote.response.items.DetailItemResponse
import com.capstone.pupukdotin.databinding.ActivityDetailBinding
import com.capstone.pupukdotin.ui.ViewModelFactory
import com.capstone.pupukdotin.ui.adapter.PlantSmallAdapter
import com.capstone.pupukdotin.ui.common.BaseActivity
import com.capstone.pupukdotin.ui.store.DetailStoreActivity
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager


class DetailItemActivity : BaseActivity<ActivityDetailBinding>() {

    private val viewModel by viewModels<DetailItemViewModel> { ViewModelFactory(this) }
    private val idItem by lazy { intent.getIntExtra(ID_ITEM, 0) }
    private var stock: Int = 0

    override fun getViewBinding(): ActivityDetailBinding =
        ActivityDetailBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.topBar.tvTitleBar.text = getString(R.string.detail_produk)

        setupAdapter()
        setupAction()
        setupViewModel()
    }

    private fun setupAdapter() {
        binding.rvListPlant.apply {
            adapter = PlantSmallAdapter(listOf())
            layoutManager = FlexboxLayoutManager(this@DetailItemActivity, FlexDirection.ROW)
        }

        binding.rvListPlantPart.apply {
            adapter = PlantSmallAdapter(listOf())
            layoutManager = FlexboxLayoutManager(this@DetailItemActivity, FlexDirection.ROW)
        }
    }

    private fun setupAction() {
        viewModel.getDetailItem(idItem)
        binding.addStock.setOnClickListener {
            viewModel.addItemCount(stock)
        }

        binding.subtractStock.setOnClickListener {
            viewModel.subtractItemCount()
        }

        binding.edStockCount.setOnEditorActionListener { v, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_DONE) {
                binding.edStockCount.clearFocus()
                afterEditAction(v)
            }
            false
        }
    }

    private fun afterEditAction(v: TextView) {
        val count = v.text.toString().toInt()
        if(count < stock)
            viewModel.setItemCount(count, stock)
        else
            viewModel.setItemCount(stock, stock)
    }

    private fun setupViewModel() {
        viewModel.itemCount.observe(this) {
            binding.edStockCount.setText(it.toString())
        }

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
                stock = data.item?.stock ?: 0

                with(binding) {
                    Glide.with(baseContext)
                        .load(data.item?.picture?.get(0)?.picture ?: "")
                        .placeholder(R.drawable.placeholder)
                        .into(ivProductImage)

                    tvProductTitle.text = data.item?.name ?: "Tidak Ada Nama Produk"
                    tvProductPrice.text = getString(R.string.price_format, data.item?.price)
                    tvRating.text = data.item?.rating ?: "0.0"
                    tvProductSold.text =
                        getString(R.string.product_sold, data.item?.sold ?: 0)
                    tvProductDescription.text =
                        data.item?.description ?: "Tidak Ada Deskripsi"

                    tvStoreName.text = data.item?.store?.name ?: "Tidak Ada Nama Toko"
                    tvStoreAddress.text = data.item?.store?.address ?: "Tidak Ada Alamat Toko"
                    tvStoreRating.text = data.item?.store?.rating ?: "0.0"

                    val listPlant = data.item?.plant?.mapNotNull { it.name }
                    val listPlantPlant = data.item?.plantPart?.mapNotNull { it.name }

                    rvListPlant.adapter =
                        PlantSmallAdapter(listPlant ?: emptyList())

                    rvListPlantPart.adapter =
                        PlantSmallAdapter(listPlantPlant ?: emptyList())

                    Glide.with(baseContext)
                        .load(data.item?.store?.picture)
                        .placeholder(R.drawable.placeholder)
                        .into(ivStore)
                    tvProductStock.text =
                        getString(R.string.product_stock, data.item?.stock ?: 0)

                    tvStoreName.setOnClickListener {
                        DetailStoreActivity.start(
                            this@DetailItemActivity,
                            StoreModel(
                                id = data.item?.store?.id ?: 0,
                                name = data.item?.store?.name ?: "",
                                address = data.item?.store?.address ?: "",
                                rating = data.item?.store?.rating ?: "0.0",
                                description = data.item?.store?.description ?: "",
                                picture = data.item?.store?.picture ?: ""
                            )
                        )
                    }
                }
            }
        } else {
            binding.svDetailItem.isVisible = false
            binding.bottomCard.isVisible = false
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