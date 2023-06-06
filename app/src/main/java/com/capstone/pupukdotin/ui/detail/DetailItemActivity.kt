package com.capstone.pupukdotin.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.capstone.pupukdotin.R
import com.capstone.pupukdotin.data.fakesource.FakeDetailProduct
import com.capstone.pupukdotin.data.model.StoreModel
import com.capstone.pupukdotin.data.remote.network.NetworkResult
import com.capstone.pupukdotin.data.remote.response.DetailItemResponse
import com.capstone.pupukdotin.databinding.ActivityDetailBinding
import com.capstone.pupukdotin.ui.ViewModelFactory
import com.capstone.pupukdotin.ui.adapter.PlantSmallAdapter
import com.capstone.pupukdotin.ui.common.BaseActivity
import com.capstone.pupukdotin.ui.store.DetailStoreActivity
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager

class DetailItemActivity : BaseActivity<ActivityDetailBinding>() {

    private val viewModel by viewModels<DetailItemViewModel> { ViewModelFactory(this) }
    private val idItem by lazy { intent.getIntExtra(ID_ITEM, 10) }
    private var itemCount = 0

    override fun getViewBinding(): ActivityDetailBinding =
        ActivityDetailBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.topBar.tvTitleBar.text = getString(R.string.detail_produk)

        binding.edStockCount.setText(itemCount.toString())
        binding.rvListPlant.apply {
            adapter = PlantSmallAdapter(listOf())
            layoutManager = FlexboxLayoutManager(this@DetailItemActivity, FlexDirection.ROW)
        }

        binding.rvListPlantPart.apply {
            adapter = PlantSmallAdapter(listOf())
            layoutManager = FlexboxLayoutManager(this@DetailItemActivity, FlexDirection.ROW)
        }
        setupAction()
        setupViewModel()
    }

    private fun setupAction() {
//        setupFakeView(FakeDataSource.getFakeDetailProduct())
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
                        .load(data.item?.picture?.get(0))
                        .placeholder(R.drawable.placeholder)
                        .into(ivProductImage)

                    tvProductTitle.text = data.item?.name ?: "Tidak Ada Nama Produk"
                    tvProductPrice.text = getString(R.string.price, data.item?.price)
                    tvRating.text = data.item?.rating ?: "0.0"
                    tvProductSold.text =
                        getString(R.string.product_sold, data.item?.sold ?: 0)
                    tvProductDescription.text =
                        data.item?.description ?: "Tidak Ada Deskripsi"

                    tvStoreName.text = data.item?.store?.name ?: "Tidak Ada Nama Toko"
                    tvStoreAddress.text = data.item?.store?.address ?: "Tidak Ada Alamat Toko"
                    tvStoreRating.text = data.item?.store?.rating ?: "0.0"

                    rvListPlant.adapter =
                        PlantSmallAdapter(data.item?.plant?.map { it.name } ?: emptyList())

                    rvListPlantPart.adapter =
                        PlantSmallAdapter(data.item?.plantPart?.map { it.name } ?: emptyList())

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
            // TODO("Not yet implemented")
        }
    }

    private fun setupFakeView(data: FakeDetailProduct) {
        with(binding) {
            Glide.with(baseContext)
                .load(data.image)
                .placeholder(R.drawable.placeholder)
                .into(ivProductImage)

            tvProductTitle.text = data.name
            tvProductPrice.text = getString(R.string.price, data.price)
            tvRating.text = data.rating
            tvProductSold.text =
                getString(R.string.product_sold, data.sold)
            tvProductDescription.text =
                data.description

            tvStoreName.text = data.store.name
            tvStoreAddress.text = data.store.alamat
            tvStoreRating.text = data.store.rating

            rvListPlant.adapter = PlantSmallAdapter(data.plant)
            rvListPlantPart.adapter = PlantSmallAdapter(data.plantFor)

            Glide.with(baseContext)
                .load(data.store.image)
                .placeholder(R.drawable.placeholder)
                .into(ivStore)
            tvProductStock.text = getString(R.string.product_stock, data.sold)
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