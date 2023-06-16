package com.capstone.pupukdotin.ui.profile

import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.icu.util.Calendar
import android.location.Geocoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.capstone.pupukdotin.R
import com.capstone.pupukdotin.data.remote.network.NetworkResult
import com.capstone.pupukdotin.data.remote.payload.profile.UpdateProfilePayload
import com.capstone.pupukdotin.data.remote.response.user.ProfileDetailResponse
import com.capstone.pupukdotin.databinding.ActivityUbahProfileBinding
import com.capstone.pupukdotin.ui.ViewModelFactory
import com.capstone.pupukdotin.ui.common.BaseActivity
import com.capstone.pupukdotin.ui.maps.SelectMapsLocationActivity
import com.capstone.pupukdotin.utils.uriToFile
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Calendar as JavaCalendar

class UbahProfileActivity : BaseActivity<ActivityUbahProfileBinding>() {

    private val viewModel by viewModels<LihatProfileViewModel> { ViewModelFactory(this) }

    @RequiresApi(Build.VERSION_CODES.N)
    private val myCalendar: Calendar = Calendar.getInstance()
    private val myCalendarJava: JavaCalendar = JavaCalendar.getInstance()

    private var urlFile = ""
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

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg = result.data?.data as Uri
            selectedImg.let { uri ->
                val myFile = uriToFile(uri, this@UbahProfileActivity)
                viewModel.uploadImage(myFile)
            }
        }
    }


    override fun getViewBinding(): ActivityUbahProfileBinding =
        ActivityUbahProfileBinding.inflate(layoutInflater)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel.getProfile()
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
        binding.ubahProfileMap.text = addressName ?: ""
    }

    private fun setupViewModel() {
        viewModel.profile.observe(this) { result ->
            when (result) {
                is NetworkResult.Success -> {
                    showLoading(false)
                    setupProfile(result.data.profile as ProfileDetailResponse.Profile)
                }

                is NetworkResult.Loading -> {
                    showLoading(true)
                }

                is NetworkResult.Error -> {
                    val errorResult = result.error
                    showLoading(false)
                    showToast(errorResult.toString())
                    Log.d("ini_log_fragment", errorResult.toString())
                }
            }
        }

        viewModel.fileUploaded.observe(this) { result ->
            when (result) {
                is NetworkResult.Success -> {
                    showLoading(false)
                    urlFile = result.data.file?.url ?: ""
                    Glide.with(this@UbahProfileActivity)
                        .load(urlFile)
                        .placeholder(R.drawable.placeholder)
                        .into(binding.profileDetailPicture)
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

        viewModel.editProfile.observe(this) { result ->
            when (result) {
                is NetworkResult.Success -> {
                    showLoading(false)
                    showToast("Profil Berhasil Diubah")
                    onBackPressedDispatcher.onBackPressed()
                }

                is NetworkResult.Loading -> {
                    showLoading(true)
                }

                is NetworkResult.Error -> {
                    val errorResult = result.error
                    showLoading(false)
                    showToast(errorResult.toString())
                    Log.d("ini_log_fragment", errorResult.toString())
                }
            }
        }
        onBackPressedDispatcher.addCallback {
            finish()
        }
    }

    private fun setupProfile(profile: ProfileDetailResponse.Profile) {
        with(binding) {
            urlFile = profile.picture ?: ""

            Glide.with(this@UbahProfileActivity)
                .load(urlFile)
                .placeholder(R.drawable.placeholder)
                .into(profileDetailPicture)

            namaLengkapTextInputEditText.setText(profile.name ?: "")
            tvTanggalLahirDetail.text = profile.birthDate ?: "2000-01-01"

            nomorTeleponTextInputEditText.setText(profile.phoneNumber ?: "")
            alamatTextInputEditText.setText(profile.address ?: "")

            if (profile.latitude != null && profile.longitude != null) {
                lat = profile.latitude
                long = profile.longitude
                setupMapAddress(lat, long)
            } else {
                ubahProfileMap.text = "-"
            }

        }
    }

    private fun showLoading(value: Boolean) {
        binding.apply {
            pbLoadingScreen.isVisible = value
            svContent.isVisible = !value
        }

    }

    private fun setUpAction() {
        binding.apply {
            toolbarUbahProfile.btnBack.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }

            buttonSimpanEditProfil.setOnClickListener {
                showDialog()
            }

            binding.cvTanggalLahir.setOnClickListener { openCalendar(binding.tvTanggalLahirDetail.text.toString()) }
            pilihLokasiButton.setOnClickListener {
                val moveForResultIntent =
                    Intent(this@UbahProfileActivity, SelectMapsLocationActivity::class.java)
                resultLauncher.launch(moveForResultIntent)
            }

            binding.tvUploadImage.setOnClickListener { openGallery() }
        }
    }

    private fun openGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private fun openCalendar(dateString: String) {

        val date = DatePickerDialog.OnDateSetListener { _, year, month, day ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                myCalendar[Calendar.YEAR] = year
                myCalendar[Calendar.MONTH] = month
                myCalendar[Calendar.DAY_OF_MONTH] = day
            } else {
                myCalendarJava[JavaCalendar.YEAR] = year
                myCalendarJava[JavaCalendar.MONTH] = month
                myCalendarJava[JavaCalendar.DAY_OF_MONTH] = day
            }
            updatelabel()
        }
        val datePicker: DatePickerDialog
        val dateFormatted = SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(dateString)
        val dateFormattedJava = SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(dateString)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            myCalendar.time = dateFormatted
            datePicker = DatePickerDialog(
                this,
                date,
                myCalendar[Calendar.YEAR],
                myCalendar[Calendar.MONTH],
                myCalendar[Calendar.DAY_OF_MONTH]
            )
        } else {
            myCalendarJava.time = dateFormattedJava ?: JavaCalendar.getInstance().time
            datePicker = DatePickerDialog(
                this,
                date,
                myCalendarJava[JavaCalendar.YEAR],
                myCalendarJava[JavaCalendar.MONTH],
                myCalendarJava[JavaCalendar.DAY_OF_MONTH]
            )
        }
        datePicker.datePicker.maxDate = System.currentTimeMillis()
        datePicker.show()
    }

    private fun updatelabel() {
        val myFormat = "yyyy-MM-dd"
        val dateFormat = SimpleDateFormat(myFormat, Locale.US)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            binding.tvTanggalLahirDetail.text = dateFormat.format(myCalendar.time)
        } else {
            binding.tvTanggalLahirDetail.text = dateFormat.format(myCalendarJava.time)
        }
    }

    private fun showDialog() {
        val dialogBinding = layoutInflater.inflate(R.layout.dialog_simpan_profil_confirmation, null)

        val simpanPofilDialog = Dialog(this@UbahProfileActivity)
        simpanPofilDialog.setContentView(dialogBinding)

        simpanPofilDialog.setCancelable(false)
        simpanPofilDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        simpanPofilDialog.show()

        val yaBtn = dialogBinding.findViewById<Button>(R.id.ya_button)
        yaBtn.setOnClickListener{
            val name = binding.namaLengkapTextInputEditText.text.toString()
            val birthDate = binding.tvTanggalLahirDetail.text.toString()
            val phoneNumber = binding.nomorTeleponTextInputEditText.text.toString()
            val address = binding.alamatTextInputEditText.text.toString()

            viewModel.editProfile(
                UpdateProfilePayload(
                    name = name.ifBlank { null },
                    birthDate = birthDate.ifBlank { null },
                    phoneNumber = phoneNumber.ifBlank { null },
                    address = address.ifBlank { null },
                    picture = urlFile.ifBlank { null },
                    age = null,
                    latitude = lat.takeIf { it != 0.0 },
                    longitude = long.takeIf { it != 0.0 }
                )
            )
            simpanPofilDialog.dismiss()
        }

        val tidakBtn = dialogBinding.findViewById<Button>(R.id.tidak_button)
        tidakBtn.setOnClickListener{
            simpanPofilDialog.dismiss()
        }
    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, UbahProfileActivity::class.java)
            context.startActivity(starter)
        }
    }
}