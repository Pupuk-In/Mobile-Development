package com.capstone.pupukdotin.ui.store

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Geocoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.capstone.pupukdotin.R
import com.capstone.pupukdotin.data.remote.network.NetworkResult
import com.capstone.pupukdotin.data.remote.payload.store.UpdateStoreDetailPayload
import com.capstone.pupukdotin.data.remote.response.common.Store
import com.capstone.pupukdotin.databinding.ActivityEditStoreProfileBinding
import com.capstone.pupukdotin.ui.ViewModelFactory
import com.capstone.pupukdotin.ui.common.BaseActivity
import com.capstone.pupukdotin.ui.maps.SelectMapsLocationActivity
import com.capstone.pupukdotin.utils.uriToFile
import java.io.IOException
import java.util.Locale

class EditStoreProfileActivity : BaseActivity<ActivityEditStoreProfileBinding>() {


    override fun getViewBinding(): ActivityEditStoreProfileBinding =
        ActivityEditStoreProfileBinding.inflate(layoutInflater)

    private var urlFile = ""
    private var lat = 0.0
    private var long = 0.0

    private val viewModel by viewModels<EditStoreViewModel> { ViewModelFactory(this) }

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
                val myFile = uriToFile(uri, this@EditStoreProfileActivity)
                viewModel.uploadImage(myFile)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.getOwnedDetailStore()

        setUpAction()
        setupViewModel()
    }

    private fun setupViewModel() {
        viewModel.ownedDetailStore.observe(this) { result ->
            when (result) {
                is NetworkResult.Loading -> {
                    showLoading(true)
                }

                is NetworkResult.Success -> {
                    showLoading(false)
                    setupContentSuccess(result.data.store)
                }

                is NetworkResult.Error -> {
                    showLoading(false)
                    showToast(result.error.toString())
                }
            }
        }

        viewModel.fileUploaded.observe(this) { result ->
            when (result) {
                is NetworkResult.Success -> {
                    showLoading(false)
                    urlFile = result.data.file?.url ?: ""
                    Glide.with(this@EditStoreProfileActivity)
                        .load(urlFile)
                        .placeholder(R.drawable.placeholder)
                        .into(binding.editProfileStorePicture)
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

        viewModel.editStoreMessage.observe(this) { result ->
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

    private fun setupContentSuccess(store: Store?) {
        with(binding) {
            Glide.with(this@EditStoreProfileActivity)
                .load(store?.picture)
                .placeholder(R.drawable.placeholder)
                .into(editProfileStorePicture)
            edEditNamaToko.setText(store?.name ?: "")
            edEditDeskripsiStore.setText(store?.description ?: "")
            edEditAlamatStore.setText(store?.address ?: "")
            if (store?.latitude != null && store.longitude != null) {
                setupMapAddress(store.latitude, store.longitude)
            } else {
                tvAlamatPadaPeta.text = "-"
            }
        }
    }

    private fun showLoading(value: Boolean) {
        binding.ubahProfilTokoProgressbar.isVisible = value
        binding.svContent.isVisible = !value
    }


    private fun setUpAction() {
        binding.toolbarUbahProfileToko.btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.buttonUnggahFotoStore.setOnClickListener {
            openGallery()
        }

        binding.buttonSimpanEditProfilStore.setOnClickListener {
            val dialogBinding =
                layoutInflater.inflate(R.layout.dialog_simpan_profil_store_confirmation, null)

            val simpanPofilStoreDialog = Dialog(this)
            simpanPofilStoreDialog.setContentView(dialogBinding)

            simpanPofilStoreDialog.setCancelable(false)
            simpanPofilStoreDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            simpanPofilStoreDialog.show()

            val yaBtn = dialogBinding.findViewById<Button>(R.id.ya_button)
            yaBtn.setOnClickListener {
                saveStoreProfile()
                simpanPofilStoreDialog.dismiss()
            }

            val tidakBtn = dialogBinding.findViewById<Button>(R.id.tidak_button)
            tidakBtn.setOnClickListener {
                simpanPofilStoreDialog.dismiss()
            }
        }

        binding.pilihLokasi.setOnClickListener {
            val intentResult = Intent(this, SelectMapsLocationActivity::class.java)
            resultLauncher.launch(intentResult)
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

    private fun openGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }
}