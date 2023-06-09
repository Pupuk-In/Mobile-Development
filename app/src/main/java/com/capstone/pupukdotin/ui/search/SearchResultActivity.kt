package com.capstone.pupukdotin.ui.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.capstone.pupukdotin.R
import com.capstone.pupukdotin.data.remote.network.NetworkResult
import com.capstone.pupukdotin.data.remote.payload.items.SearchItemsPayload
import com.capstone.pupukdotin.data.remote.response.items.SearchItemsResponse
import com.capstone.pupukdotin.databinding.ActivitySearchResultBinding
import com.capstone.pupukdotin.ui.ViewModelFactory
import com.capstone.pupukdotin.ui.common.BaseActivity

class SearchResultActivity : BaseActivity<ActivitySearchResultBinding>() {

    private lateinit var itemAdapter: ProductItemAdapter
    private lateinit var searchView: SearchView

    private val viewModel by viewModels<SearchResultViewModel> { ViewModelFactory(this) }
    override fun getViewBinding(): ActivitySearchResultBinding =
        ActivitySearchResultBinding.inflate(layoutInflater)

    private var queryText = ""
    private val plantId by lazy { intent?.getIntExtra(PLANT_ID, 0) }
    private val typeId by lazy { intent?.getIntExtra(TYPE_ID, 0) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        queryText = intent?.getStringExtra(QUERY) ?:""
        viewModel.searchItem(SearchItemsPayload(search = queryText, type = typeId, plant = plantId))
        searchView = binding.search
        searchView.clearFocus()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return if (query.isNullOrBlank()) {
                    false
                } else {
                    queryText = query
                    viewModel.searchItem(SearchItemsPayload(search = queryText))
                    true
                }
            }

            override fun onQueryTextChange(newText: String?): Boolean = false
        })
        setupAdapter()
        setupViewModel()
    }

    private fun setupViewModel() {
        viewModel.searchItem.observe(this) { result ->
            when (result) {
                is NetworkResult.Loading -> {
                    showLoading(true)
                }

                is NetworkResult.Success -> {
                    showLoading(false)
                    setupResultView(result.data.item?.data ?: emptyList())
                }

                is NetworkResult.Error -> {
                    showLoading(false)
                    val errorResult = result.error
                    Toast.makeText(this, errorResult.toString(), Toast.LENGTH_SHORT).show()
                    Log.d("ini_log_search", errorResult.toString())
                    binding.nsvContent.isVisible = false
                }
            }
        }
    }

    private fun setupResultView(item: List<SearchItemsResponse.DataItem>) {
        with(binding) {
            tvTextSearch.text = getString(R.string.search_result_format, queryText)
            itemAdapter = ProductItemAdapter(item)
            binding.rvProductItem.adapter = itemAdapter
            binding.tvNoData.isVisible = item.isEmpty()
        }
    }

    private fun showLoading(value: Boolean) {
        binding.nsvContent.isVisible = !value
        binding.pbLoadingScreen.isVisible = value
        binding.tvNoData.isVisible = !value
    }

    private fun setupAdapter() {
        itemAdapter = ProductItemAdapter(emptyList())

        val manager = GridLayoutManager(this, 2)
        binding.rvProductItem.apply {
            layoutManager = manager
            adapter = itemAdapter
        }
    }

    companion object {
        @JvmStatic
        fun start(
            context: Context,
            query: String? = null,
            typeId: Int? = null,
            plantId: Int? = null
        ) {
            val starter = Intent(context, SearchResultActivity::class.java)
                .putExtra(QUERY, query)
                .putExtra(TYPE_ID, typeId)
                .putExtra(PLANT_ID, plantId)
            context.startActivity(starter)
        }

        private const val QUERY = "query"
        private const val TYPE_ID = "type_id"
        private const val PLANT_ID = "plant_id"
    }
}