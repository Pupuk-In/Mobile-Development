package com.capstone.pupukdotin.ui.store

import android.app.Activity
import android.content.Intent
import android.location.Geocoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.capstone.pupukdotin.R
import com.capstone.pupukdotin.data.remote.network.NetworkResult
import com.capstone.pupukdotin.data.remote.payload.store.UpdateStoreDetailPayload
import com.capstone.pupukdotin.databinding.ActivityBukaTokoBinding
import com.capstone.pupukdotin.ui.ViewModelFactory
import com.capstone.pupukdotin.ui.common.BaseActivity
import com.capstone.pupukdotin.ui.maps.SelectMapsLocationActivity
import com.capstone.pupukdotin.utils.uriToFile
import java.io.IOException
import java.util.Locale

class BukaTokoActivity : BaseActivity<ActivityBukaTokoBinding>() {

    private var urlFile = ""
    private var lat = 0.0
    private var long = 0.0

    private val viewModel by viewModels<BukaTokoViewModel> { ViewModelFactory(this) }

    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK && result.data != null) {
            val latdata = result?.data!!.extras?.getDouble(SelectMapsLocationActivity.LAT_VALUE)
            val longdata = result?.data!!.extras?.getDouble(SelectMapsLocationActivity.LONG_VALUE)
            setupMapAddress(latdata ?: 0.0, longdata ?: 0.0)
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg = result.data?.data as Uri
            selectedImg.let { uri ->
                val myFile = uriToFile(uri, this@BukaTokoActivity)
                viewModel.uploadImage(myFile)
            }
        }
    }


    override fun getViewBinding(): ActivityBukaTokoBinding =
        ActivityBukaTokoBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setUpAction()
        setupViewModel()
    }

    private fun setupViewModel() {
        viewModel.fileUploaded.observe(this) { result ->
            when (result) {
                is NetworkResult.Success -> {
                    showLoading(false)
                    urlFile = result.data.file?.url ?: ""
                    Glide.with(this@BukaTokoActivity)
                        .load(urlFile)
                        .placeholder(R.drawable.placeholder)
                        .into(binding.bukaTokoProfilePicture)
                }

                is NetworkResult.Loading -> {
                    showLoading(true)
                }

                is NetworkResult.Error -> {
                    val errorResult = result.error
                    showLoading(false)
                    showToast(errorResult.toString())
                }
            }
        }

        viewModel.createNewStoreMessage.observe(this) { result ->
            when (result) {
                is NetworkResult.Loading -> {
                    showLoading(true)
                }

                is NetworkResult.Success -> {
                    showLoading(false)
                    showToast("Profil Berhasil Diubah")
                    onBackPressedDispatcher.onBackPressed()
                }

                is NetworkResult.Error -> {
                    showLoading(false)
                    showToast(result.error.toString())
                }
            }
        }
        onBackPressedDispatcher.addCallback {
            finish()
        }
    }

    private fun showLoading(value: Boolean) {
        binding.bukaTokoProgressbar.isVisible = value
        binding.svContent.isVisible = !value
    }

    private fun setUpAction() {
        binding.apply {
            toolbarBukaToko.btnBack.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
            buttonBukaToko.setOnClickListener {
                showTwoActionDialog(
                    "Apakah anda yakin ingin membuka toko?",
                    "Ya",
                    "Tidak",
                    onYesClickBtnClicked = { saveStoreProfile() }
                )
            }
            buttonUnggahFotoStore.setOnClickListener {
                openGallery()
            }
            pilihLokasi.setOnClickListener {
                val intentResult =
                    Intent(this@BukaTokoActivity, SelectMapsLocationActivity::class.java)
                resultLauncher.launch(intentResult)
            }
        }
    }

    private fun saveStoreProfile() {
        with(binding) {
            val storeName = edEditNamaToko.text.toString()
            val descStore = edEditDeskripsiStore.text.toString()
            val addressStore = edEditAlamatStore.text.toString()

            viewModel.editOwnedDetailStore(
                UpdateStoreDetailPayload(
                    name = storeName.ifBlank { null },
                    picture = urlFile.ifBlank { null },
                    address = addressStore.ifBlank { null },
                    latitude = lat.takeIf { it != 0.0 },
                    longitude = long.takeIf { it != 0.0 },
                    description = descStore.ifBlank { null }
                )
            )
        }
    }

    private fun openGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private fun setupMapAddress(latitude: Double, longitude: Double) {
        lat = latitude
        long = longitude
        var addressName: String? = null
        val geocoder = Geocoder(this, Locale("id", "ID"))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            geocoder.getFromLocation(lat, long, 1) { list ->
                if (list.size != 0) {
                    addressName = list[0].getAddressLine(0)
                }
            }
        } else {
            try {
                @Suppress("DEPRECATION")
                val list = geocoder.getFromLocation(lat, long, 1)
                if (list != null && list.size != 0) {
                    addressName = list[0].getAddressLine(0)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        binding.tvAlamatPadaPeta.text = addressName ?: ""
    }
}
