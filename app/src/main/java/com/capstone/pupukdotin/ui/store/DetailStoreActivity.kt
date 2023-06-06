package com.capstone.pupukdotin.ui.store

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import com.bumptech.glide.Glide
import com.capstone.pupukdotin.R
import com.capstone.pupukdotin.data.model.StoreModel
import com.capstone.pupukdotin.databinding.ActivityDetailStoreBinding
import com.capstone.pupukdotin.ui.common.BaseActivity

class DetailStoreActivity : BaseActivity<ActivityDetailStoreBinding>() {

    private var storeData: StoreModel? = null

    override fun getViewBinding(): ActivityDetailStoreBinding =
        ActivityDetailStoreBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        storeData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(STORE_DATA, StoreModel::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(STORE_DATA)
        }
        setupView()
    }

    private fun setupView() {
        binding.apply {
            Glide.with(this@DetailStoreActivity)
                .load(storeData?.picture)
                .placeholder(R.drawable.placeholder)
                .into(ivStoreImage)
            tvStoreName.text = storeData?.name ?: ""
            tvRating.text = storeData?.rating ?: "0.0"
            tvStoreLocation.text = storeData?.address ?: ""
            tvStoreAbout.text = storeData?.description ?: ""
        }
    }

    companion object {
        @JvmStatic
        fun start(context: Context, storeData: StoreModel) {
            val starter = Intent(context, DetailStoreActivity::class.java)
                .putExtra(STORE_DATA, storeData)
            context.startActivity(starter)
        }

        private const val STORE_DATA = "store_data"
    }
}