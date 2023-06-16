package com.capstone.pupukdotin.ui.aisearch

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.capstone.pupukdotin.R
import com.capstone.pupukdotin.data.remote.network.NetworkResult
import com.capstone.pupukdotin.data.remote.response.ai.NutritionDetectionResponse
import com.capstone.pupukdotin.databinding.ActivityNutritionDetectionBinding
import com.capstone.pupukdotin.ui.ViewModelFactory
import com.capstone.pupukdotin.ui.common.BaseActivity
import com.capstone.pupukdotin.ui.search.SearchResultActivity
import com.capstone.pupukdotin.utils.uriToFile

class NutritionDetectionActivity : BaseActivity<ActivityNutritionDetectionBinding>() {

    private val viewModel by viewModels<NutritionDetectionViewModel> { ViewModelFactory(this) }
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
                viewModel.putFile(myFile)
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
        viewModel.getAllPlants()
        binding.spProductPlant.isEnabled = false

        setupAction()
        setupAdapter(listPlant)
        setupViewModel()
    }

    private fun setupViewModel() {
        viewModel.imageUploaded.observe(this) { url ->
            getFile = url.path

            val isUploaded = getFile.isNotBlank()
            binding.ivImage.isVisible = isUploaded
            binding.ivEmpty.isVisible = !isUploaded
            binding.tvUploadAnother.isVisible = isUploaded
            binding.cvChooseImage.isClickable = !isUploaded
            binding.btnSave.isVisible = true

            if (isUploaded) {
                binding.ivImage.setImageBitmap(BitmapFactory.decodeFile(getFile))
            }
        }

        viewModel.allPlants.observe(this) { result ->
            when (result) {
                is NetworkResult.Loading -> {
                    showLoading(true)
                }

                is NetworkResult.Success -> {
                    showLoading(false)
                    setupAdapter(result.data.plant?.mapNotNull { it.name } ?: emptyList())
                }

                is NetworkResult.Error -> {
                    showLoading(false)
                    showToast(result.error.toString())
                }
            }
        }

        viewModel.detectionResult.observe(this) { result ->
            when (result) {
                is NetworkResult.Loading -> {
                    showDetectionLoading(true)
                }

                is NetworkResult.Success -> {
                    showDetectionLoading(false)
                    setupDetectionResult(result.data)
                }

                is NetworkResult.Error -> {
                    showDetectionLoading(false)
                    setupErrorDetectionResult()
                }
            }
        }
    }

    private fun showDetectionLoading(value: Boolean) {
        binding.btnSave.apply {
            isEnabled = !value
            text = if (value) getString(R.string.image_process_loading)
            else getString(R.string.save)
        }
    }

    private fun setupDetectionResult(data: NutritionDetectionResponse) {
        with(binding) {
            tvDetectionResult.isVisible = true
            tvResultDetail.apply {
                isVisible = true
                text = data.description
            }
            btnSave.isInvisible = true
            btnFindItem.isVisible = true

            btnFindItem.setOnClickListener {
                SearchResultActivity.start(
                    this@NutritionDetectionActivity,
                    query = data.jsonMemberClass
                )
            }
        }
    }

    private fun setupErrorDetectionResult() {
        with(binding) {
            tvDetectionResult.isVisible = true
            tvResultDetail.apply {
                isVisible = true
                text = getString(R.string.error_detection_message)
            }
            btnSave.isVisible = false
            btnFindItem.isVisible = false
        }
    }

    private fun showLoading(value: Boolean) {
        binding.pbLoadingScreen.isVisible = value
        binding.clContent.isVisible = !value
    }

    private fun setupAdapter(list: List<String>) {
        spinnerPlantAdapter = ArrayAdapter(this, R.layout.item_spinner, list)
        spinnerPlantAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        binding.spProductPlant.adapter = spinnerPlantAdapter
    }

    private fun setupAction() {
        with(binding) {
            cvChooseImage.setOnClickListener { openGallery() }
            tvUploadAnother.setOnClickListener { reset() }
            btnSave.setOnClickListener { viewModel.startDetection() }
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
            viewModel.removeFile()
            ivImage.isVisible = false
            ivEmpty.isVisible = true
            cvChooseImage.isClickable = true
            btnFindItem.isVisible = false
            tvResultDetail.isVisible = false
            tvDetectionResult.isVisible = false
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