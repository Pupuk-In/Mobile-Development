package com.capstone.pupukdotin.ui.search

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.capstone.pupukdotin.databinding.ActivitySearchResultBinding
import com.capstone.pupukdotin.ui.common.BaseActivity

class SearchResultActivity : BaseActivity<ActivitySearchResultBinding>() {

    private lateinit var itemAdapter: ProductItemAdapter

    override fun getViewBinding(): ActivitySearchResultBinding = ActivitySearchResultBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupAdapter()
    }

    private fun setupAdapter() {
        itemAdapter = ProductItemAdapter()

        val manager = GridLayoutManager(this, 2)
        binding.rvProductItem.apply {
            layoutManager = manager
            adapter = itemAdapter
        }
    }
}