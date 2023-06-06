package com.capstone.pupukdotin.ui.wishlist

import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.GridLayoutManager
import com.capstone.pupukdotin.R
import com.capstone.pupukdotin.databinding.ActivityWishlistBinding
import com.capstone.pupukdotin.ui.common.BaseActivity
import com.capstone.pupukdotin.ui.search.ProductItemAdapter

class WishlistActivity : BaseActivity<ActivityWishlistBinding>() {
    private lateinit var itemAdapter: ProductItemAdapter


    override fun getViewBinding(): ActivityWishlistBinding =
        ActivityWishlistBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupAdapter()

        binding.topbar.tvTitleBar.text = getString(R.string.wishlist)

        binding.edFindWishlist.apply {
            setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                    || actionId == EditorInfo.IME_ACTION_DONE
                    || event?.action == KeyEvent.ACTION_DOWN
                    || event?.action == KeyEvent.KEYCODE_ENTER
                ) {
                    showToast(v?.text.toString())
                }
                false
            }
        }
    }

    private fun setupAdapter() {
        itemAdapter = ProductItemAdapter()

        val manager = GridLayoutManager(this, 2)
        binding.rvListProduct.apply {
            layoutManager = manager
            adapter = itemAdapter
        }
    }
}