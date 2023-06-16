package com.capstone.pupukdotin.ui.store

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.capstone.pupukdotin.R
import com.capstone.pupukdotin.data.remote.network.NetworkResult
import com.capstone.pupukdotin.data.remote.payload.store.SearchCatalogPayload
import com.capstone.pupukdotin.data.remote.response.common.Store
import com.capstone.pupukdotin.data.remote.response.store.StoreDetailResponse
import com.capstone.pupukdotin.databinding.ActivityDetailStoreBinding
import com.capstone.pupukdotin.ui.ViewModelFactory
import com.capstone.pupukdotin.ui.common.BaseActivity
import com.capstone.pupukdotin.ui.search.ProductItemAdapter

class DetailStoreActivity : BaseActivity<ActivityDetailStoreBinding>() {

    private val idStore by lazy { intent.getIntExtra(ID_STORE, 0) }
    private val viewModel by viewModels<DetailStoreViewModel> { ViewModelFactory(this) }

    override fun getViewBinding(): ActivityDetailStoreBinding =
        ActivityDetailStoreBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel.getDetailStore(payload = SearchCatalogPayload(storeId = idStore), idStore)
        setupViewModel()
        setupAdapter()
    }

    private fun setupAdapter() {
        val manager = GridLayoutManager(this, 2)
        binding.rvProductList.apply {
            layoutManager = manager
            adapter = ProductItemAdapter(emptyList())
        }
    }

    private fun setupViewModel() {
        viewModel.detailStore.observe(this) { result ->
            when(result) {
                is NetworkResult.Loading -> {
                    showLoading(true)
                }
                is NetworkResult.Success -> {
                    showLoading(false)
                    result.data.store?.let { setupStore(it) }
                    result.data.catalog?.let { setupCatalog(it) }
                }
                is NetworkResult.Error -> {
                    showLoading(false)
                    val errorResult = result.error.toString()
                    showToast(errorResult)
                    Log.d("ini_log_login", errorResult)
                    binding.nsvContent.isVisible = false
                }
            }
        }
    }

    private fun setupStore(store: Store) {
        with(binding) {
            Glide.with(this@DetailStoreActivity)
                .load(store.picture ?: "")
                .placeholder(R.drawable.placeholder)
                .into(ivStoreImage)
            tvStoreName.text = store.name ?: "Tidak ada nama toko"
            tvStoreLocation.text = store.address ?: "Tidak ada alamat"
            tvStoreAbout.text = store.description ?: "Tidak ada deskripsi toko"
            tvRating.text = store.rating ?: "0"
        }
    }

    private fun setupCatalog(catalog: StoreDetailResponse.Catalog) {
        binding.rvProductList.adapter = ProductItemAdapter(catalog.data ?: emptyList())
    }

    private fun showLoading(value: Boolean) {
        binding.apply {
            pbLoadingScreen.isVisible = value
            pbLoadingProduct.isVisible = value
            nsvContent.isVisible = !value
        }

    }


    companion object {
        @JvmStatic
        fun start(context: Context, idStore: Int) {
            val starter = Intent(context, DetailStoreActivity::class.java)
                .putExtra(ID_STORE, idStore)
            context.startActivity(starter)
        }

        private const val ID_STORE = "id_store"
    }
}