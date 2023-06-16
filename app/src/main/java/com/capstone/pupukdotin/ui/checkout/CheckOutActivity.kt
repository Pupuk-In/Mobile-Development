package com.capstone.pupukdotin.ui.checkout

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.pupukdotin.R
import com.capstone.pupukdotin.data.remote.network.NetworkResult
import com.capstone.pupukdotin.data.remote.payload.transaction.CreateTransactionPayload
import com.capstone.pupukdotin.data.remote.response.carts.CartItemsResponse
import com.capstone.pupukdotin.data.remote.response.user.ProfileDetailResponse
import com.capstone.pupukdotin.databinding.ActivityCheckOutBinding
import com.capstone.pupukdotin.ui.ViewModelFactory
import com.capstone.pupukdotin.ui.adapter.TesCheckoutAdapter
import com.capstone.pupukdotin.ui.common.BaseActivity
import com.capstone.pupukdotin.ui.maps.SelectMapsLocationActivity
import java.io.IOException
import java.util.Locale

class CheckOutActivity : BaseActivity<ActivityCheckOutBinding>() {

    private val viewModel by viewModels<CheckOutViewModel> { ViewModelFactory(this) }

    private var totalProductPrice = 0

    private var lat = 0.0
    private var long = 0.0

    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK && result.data != null) {
            val latdata = result?.data!!.extras?.getDouble(SelectMapsLocationActivity.LAT_VALUE)
            val longdata = result?.data!!.extras?.getDouble(SelectMapsLocationActivity.LONG_VALUE)
            setupMapAddress(latdata ?: 0.0, longdata ?: 0.0)
        }
    }

    override fun getViewBinding(): ActivityCheckOutBinding =
        ActivityCheckOutBinding.inflate(layoutInflater)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.getCartItems()
        viewModel.getProfile()

        setUpMetodePengiriman()
        setUpRecycleView()
        setUpMetodePembayaran()
        setUpAction()
        setupViewModel()
    }

    private fun setupMapAddress(newLat: Double, newLong: Double) {
        lat = newLat
        long = newLong
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
        binding.edAlamatPenerima.setText(addressName ?: "")
        binding.alamatPenerimaMap.text = addressName ?: ""
    }

    private fun setupViewModel() {

        viewModel.profileDetail.observe(this) { result ->
            when (result) {
                is NetworkResult.Loading -> {
                    showLoading(true)
                }

                is NetworkResult.Success -> {
                    showLoading(false)
                    setupSuccessProfile(result.data.profile)
                }

                is NetworkResult.Error -> {
                    showLoading(false)
                }
            }
        }

        viewModel.cartItem.observe(this) { result ->
            when (result) {
                is NetworkResult.Loading -> {
                    showLoading(true)
                }

                is NetworkResult.Success -> {
                    showLoading(false)
                    setupSuccessView(result.data.cart)
                }

                is NetworkResult.Error -> {
                    showLoading(false)
                }
            }
        }

        viewModel.createTransactionResponse.observe(this) { result ->
            when (result) {
                is NetworkResult.Loading -> {
                    showLoading(true)
                }

                is NetworkResult.Success -> {
                    showLoading(false)
                    showDialogSuccess(result.data.transaction.id)
                }

                is NetworkResult.Error -> {
                    showLoading(false)
                    showToast(result.error.toString())
                }
            }
        }
    }

    private fun setupSuccessProfile(profile: ProfileDetailResponse.Profile?) {
        with(binding) {
            namaPenerimaTextInputEditText.setText(profile?.name ?: profile?.username ?: "")
            nomorTeleponPenerimaTextInputEditText.setText(profile?.phoneNumber ?:"")
            if (profile?.longitude != null && profile.latitude != null) {
                setupMapAddress(profile.latitude, profile.longitude)
                alamatPenerimaMap.isVisible = true
            } else {
                alamatPenerimaMap.isInvisible = false
            }
            edAlamatPenerima.setText(profile?.address ?: "Tidak Ada Alamat")
        }
    }

    private fun setupSuccessView(cart: CartItemsResponse.Cart?) {
        totalProductPrice = cart?.total ?: 0

        with(binding) {
            rvCheckoutItems.adapter = TesCheckoutAdapter(cart?.cartItem ?: emptyList())
            tvSubtotalPesanan.text =
                getString(R.string.subtotal_pesanan_produk_format, cart?.cartItem?.size)
            subtotalPesanan.text = getString(R.string.price_format, totalProductPrice)
            binding.subtotalHarga.text = getString(R.string.price_format, totalProductPrice)
            binding.checkoutTotalHarga.text = getString(R.string.price_format, totalProductPrice)
        }
    }

    private fun showLoading(value: Boolean) {
        binding.apply {
            checkoutProgressbar.isVisible = value
            nsvContent.isVisible = !value
        }
    }

    private fun setUpAction() {
        binding.toolbarCheckout.btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        //tombol pesan
        binding.checkoutButtonPesan.setOnClickListener {
            showTwoActionDialogWithSub(
                "Apakah Anda Yakin?",
                "Pesanan yang anda pilih tidak dapat diubah kembali",
                btnPositiveMesssage = "Ya",
                btnNegativeMesssage = "Tidak",
                onYesClickBtnClicked = { createNewTransaction() }
            )
        }

        binding.pilihLokasiPenerima.setOnClickListener {
            val intent = Intent(this, SelectMapsLocationActivity::class.java)
            resultLauncher.launch(intent)
        }
    }

    private fun createNewTransaction() {
        val recipientName = binding.namaPenerimaTextInputEditText.text.toString()
        val recipientPhone = binding.nomorTeleponPenerimaTextInputEditText.text.toString()
        val recipientAddress = binding.edAlamatPenerima.text.toString()
        val paymentMethod = 1

        viewModel.createNewTransaction(CreateTransactionPayload(
            paymentMethod, long, lat, recipientAddress, recipientName, recipientPhone
        ))
    }

    private fun showDialogSuccess(idTransaction: Int) {
        val dialogBinding = layoutInflater.inflate(R.layout.dialog_pesanan_berhasil, null)

        val pesananBerhasilDialog = Dialog(this)
        pesananBerhasilDialog.setContentView(dialogBinding)

        pesananBerhasilDialog.setCancelable(false)
        pesananBerhasilDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        pesananBerhasilDialog.show()

        val lihatStatusBtn = dialogBinding.findViewById<Button>(R.id.lihat_status_dialog_button)
        lihatStatusBtn.setOnClickListener {
            val navController = Navigation.findNavController(this, R.id.fragmentContainerView)
            navController.navigate(R.id.cartFragment)
        }

        val kembaliBtn = dialogBinding.findViewById<Button>(R.id.kembali_dialog_button)
        kembaliBtn.setOnClickListener {
            pesananBerhasilDialog.dismiss()
        }
    }

    private fun setUpMetodePembayaran() {
        binding.apply {
            metodePembayaranButton.setOnCheckedChangeListener { _, checkedId ->
                if (checkedId == R.id.button_tunai) {
                    buttonTunai.setTextColor(resources.getColor(R.color.white))
                    buttonDompetDigital.setTextColor(resources.getColor(R.color.green_13C193))
                } else {
                    buttonTunai.setTextColor(resources.getColor(R.color.green_13C193))
                    buttonDompetDigital.setTextColor(resources.getColor(R.color.white))
                    showToast("Coming Soon")
                    binding.metodePembayaranButton.check(R.id.button_tunai)
                }
            }
        }
    }

    private fun setUpRecycleView() {
        binding.rvCheckoutItems.apply {
            adapter = TesCheckoutAdapter(emptyList())
            layoutManager = LinearLayoutManager(this@CheckOutActivity)
        }
    }


    private fun setUpMetodePengiriman() {
        binding.apply {
            metodePengirimanButton.setOnCheckedChangeListener { group, checkedId ->
                if (checkedId == R.id.button_dikirim) {
                    buttonDikirim.setTextColor(resources.getColor(R.color.white))
                    buttonAmbilSendiri.setTextColor(resources.getColor(R.color.green_13C193))
                    subtotalOngkosKirim.text = getString(R.string.price_format, 0)
                } else {
                    buttonDikirim.setTextColor(resources.getColor(R.color.green_13C193))
                    buttonAmbilSendiri.setTextColor(resources.getColor(R.color.white))
                    subtotalOngkosKirim.text = getString(R.string.price_format, 0)
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, CheckOutActivity::class.java)
            context.startActivity(starter)
        }
    }
}