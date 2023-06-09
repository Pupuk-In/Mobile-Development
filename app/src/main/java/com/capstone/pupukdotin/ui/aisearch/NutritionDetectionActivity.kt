package com.capstone.pupukdotin.ui.aisearch

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.capstone.pupukdotin.R
import com.capstone.pupukdotin.databinding.ActivityNutritionDetectionBinding
import com.capstone.pupukdotin.ui.common.BaseActivity
import com.capstone.pupukdotin.utils.uriToFile

class NutritionDetectionActivity : BaseActivity<ActivityNutritionDetectionBinding>() {

    private val viewModel: NutritionDetectionViewModel by viewModels()
    private var getFile = ""

    // Plant
    private lateinit var spinnerPlantAdapter: ArrayAdapter<String>
    private val listPlant = mutableListOf("Jagung", "Tomat", "Kentang", "Padi", "Terong")

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg = result.data?.data as Uri
            selectedImg.let { uri ->
                val myFile = uriToFile(uri, this@NutritionDetectionActivity)
                viewModel.putUrl(myFile.path)
            }
        }
    }

    override fun getViewBinding(): ActivityNutritionDetectionBinding =
        ActivityNutritionDetectionBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.topBar.root)
        binding.topBar.apply {
            tvTitleBar.text = getString(R.string.deteksi_keperluan_nutrisi)
            root.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
                finish()
            }
        }
        setupAction()
        setupAdapter()
        setupViewModel()
    }

    private fun setupViewModel() {
        viewModel.imageUploaded.observe(this) { url ->
            getFile = url

            val isUploaded = getFile.isNotBlank()
            binding.ivImage.isVisible = isUploaded
            binding.ivEmpty.isVisible = !isUploaded
            binding.tvUploadAnother.isVisible = isUploaded
            binding.cvChooseImage.isClickable = !isUploaded

            if (isUploaded) { binding.ivImage.setImageBitmap(BitmapFactory.decodeFile(getFile)) }
        }
    }

    private fun setupAdapter() {
        spinnerPlantAdapter = ArrayAdapter(this, R.layout.item_spinner, listPlant)
        spinnerPlantAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        binding.spProductPlant.adapter = spinnerPlantAdapter
    }

    private fun setupAction() {
        with(binding) {
            cvChooseImage.setOnClickListener { openGallery() }
            tvUploadAnother.setOnClickListener {
                reset()
            }
        }
    }

    private fun openGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }


    private fun reset() {
        binding.apply {
            viewModel.removeUrl()
            ivImage.isVisible = false
            ivEmpty.isVisible = true
            cvChooseImage.isClickable = true
            btnSave.text = getString(R.string.save)
        }
    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, NutritionDetectionActivity::class.java)
            context.startActivity(starter)
        }
    }
}