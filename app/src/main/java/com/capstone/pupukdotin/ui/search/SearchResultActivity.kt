package com.capstone.pupukdotin.ui.search

import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import com.capstone.pupukdotin.databinding.ActivitySearchResultBinding
import com.capstone.pupukdotin.ui.common.BaseActivity

class SearchResultActivity : BaseActivity<ActivitySearchResultBinding>() {

    private lateinit var itemAdapter: ProductItemAdapter
    private lateinit var searchView: SearchView

    override fun getViewBinding(): ActivitySearchResultBinding = ActivitySearchResultBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        searchView = binding.search
        searchView.clearFocus()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                showToast(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean = false
        })
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