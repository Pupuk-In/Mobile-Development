package com.capstone.pupukdotin.ui.wishlist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.capstone.pupukdotin.R
import com.capstone.pupukdotin.data.remote.network.NetworkResult
import com.capstone.pupukdotin.data.remote.payload.items.SearchItemsPayload
import com.capstone.pupukdotin.databinding.ActivityWishlistBinding
import com.capstone.pupukdotin.ui.ViewModelFactory
import com.capstone.pupukdotin.ui.common.BaseActivity
import com.capstone.pupukdotin.ui.search.ProductItemAdapter

class WishlistActivity : BaseActivity<ActivityWishlistBinding>() {

    private val viewModel by viewModels<WishlistViewModel> { ViewModelFactory(this) }

    override fun getViewBinding(): ActivityWishlistBinding =
        ActivityWishlistBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupAction()
        setupAdapter()
        setupViewModel()
        viewModel.searchWishlistItem(SearchItemsPayload())

        binding.topbar.tvTitleBar.text = getString(R.string.wishlist)
    }

    private fun setupViewModel() {
        viewModel.searchWishlistItems.observe(this) { result ->
            when (result) {
                is NetworkResult.Loading -> {
                    showLoading(true)
                }
                is NetworkResult.Success -> {
                    showLoading(false)
                    binding.rvListProduct.adapter = ProductItemAdapter(result.data.wishlist?.data ?: emptyList(), "wishlist")
                }
                is NetworkResult.Error -> {
                    showLoading(false)
                    showToast(result.error.toString())
                }
            }
        }
    }

    private fun showLoading(value: Boolean) {
        binding.apply {
            pbLoadingScreen.isVisible = value
            rvListProduct.isVisible = !value
            edFindWishlist.isVisible = !value
        }
    }

    private fun setupAction() {
        binding.edFindWishlist.apply {
            setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                    || actionId == EditorInfo.IME_ACTION_DONE
                    || event?.action == KeyEvent.ACTION_DOWN
                    || event?.action == KeyEvent.KEYCODE_ENTER
                ) {
                    viewModel.searchWishlistItem(SearchItemsPayload(search = v?.text.toString()))
                }
                false
            }
        }
    }

    private fun setupAdapter() {
        val manager = GridLayoutManager(this, 2)
        binding.rvListProduct.apply {
            layoutManager = manager
            adapter = ProductItemAdapter(emptyList())
        }
    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, WishlistActivity::class.java)
            context.startActivity(starter)
        }
    }
}