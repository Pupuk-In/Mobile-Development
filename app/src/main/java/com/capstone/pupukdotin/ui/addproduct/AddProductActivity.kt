package com.capstone.pupukdotin.ui.addproduct

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.pupukdotin.R
import com.capstone.pupukdotin.databinding.ActivityAddProductBinding
import com.capstone.pupukdotin.ui.common.BaseActivity
import com.capstone.pupukdotin.ui.store.StoreDetailOrderActivity
import com.capstone.pupukdotin.utils.uriToFile

class AddProductActivity : BaseActivity<ActivityAddProductBinding>(),
    InputPlantPartAdapter.OnItemDeleteListener, InputImageAdapter.OnItemDeleteListener {

    private val viewModel: AddProductViewModel by viewModels()

    // List Image
    private val listInputImage = mutableListOf<String>()
    private lateinit var imageInputAdapter: InputImageAdapter

    // Type
    private val listType = mutableListOf("NPK", "Urea", "Phonska", "KCL", "ZK", "Kosong")
    private lateinit var spinnerTypeArrayAdapter: ArrayAdapter<String>

    // Plant
    private val listInputPlant = mutableListOf<String>()
    private lateinit var plantArrayAdapter: ArrayAdapter<String>
    private val listPlant = mutableListOf("Jagung", "Tomat", "Kentang", "Padi", "Terong")
    private lateinit var plantInputAdapter: InputPlantPartAdapter

    // Plant Part
    private val listInputPlantPart = mutableListOf<String>()
    private lateinit var plantPartArrayAdapter: ArrayAdapter<String>
    private val listPlantPart = mutableListOf("Akar", "Bunga", "Daun", "Batang")
    private lateinit var plantPartInputAdapter: InputPlantPartAdapter

    // Intent Gallery
    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg = result.data?.data as Uri
            selectedImg.let { uri ->
                val myFile = uriToFile(uri, this@AddProductActivity)
                viewModel.addImage(myFile.path)
//                addStoryViewModel.setFile(myFile)
            }
        }
    }

    override fun getViewBinding(): ActivityAddProductBinding =
        ActivityAddProductBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.topBar.root)
        binding.topBar.apply {
            @SuppressLint("SetTextI18n")
            tvTitleBar.text = getString(R.string.add_product)
            root.setNavigationOnClickListener {
                StoreDetailOrderActivity.start(this@AddProductActivity)
            }
        }
        setupViewModel()
        setupAdapter()
        setupAction()

    }

    private fun setupViewModel() {
        viewModel.listImageUploaded.observe(this) {listImage ->
            imageInputAdapter.submitList(listImage.map { InputImageModel(it) })
        }
    }

    private fun setupAction() {
        with(binding) {
            cvUploadImage.setOnClickListener { openGallery() }
            btnSave.setOnClickListener {
//                val productName = edProductName.text.toString()
//                val productType = spProductType.selectedItem.toString()
//                TODO("Implement Save Data")
            }
        }
    }

    private fun setupAdapter() {
        // Image Input
        imageInputAdapter = InputImageAdapter(this)
//        imageInputAdapter.submitList(listInputImage.map { InputImageModel(it) })
        binding.rvListImage.apply {
            adapter = imageInputAdapter
            layoutManager = LinearLayoutManager(this@AddProductActivity, LinearLayoutManager.HORIZONTAL, false)
        }


        // Type Array
        spinnerTypeArrayAdapter = ArrayAdapter(this, R.layout.item_spinner, listType)
        spinnerTypeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spProductType.adapter = spinnerTypeArrayAdapter

        // Plant Input
        plantInputAdapter = InputPlantPartAdapter(this, "plant")
        plantInputAdapter.submitList(listInputPlant.map { InputPlantModel(it) })
        binding.rvListPlantInput.apply {
            adapter = plantInputAdapter
            layoutManager =
                LinearLayoutManager(this@AddProductActivity, LinearLayoutManager.HORIZONTAL, false)
        }

        // Plant Array
        plantArrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listPlant)
        binding.edProductPlant.apply {
            setAdapter(plantArrayAdapter)
            onItemClickListener =
                OnItemClickListener { _, _, position, _ ->
                    val value = plantArrayAdapter.getItem(position) ?: ""
                    showToast(value)
                    text = null
                    listInputPlant.add(value)
                    plantInputAdapter.submitList(listInputPlant.map { InputPlantModel(it) })
                }
        }

        // Plant-Part Input
        plantPartInputAdapter = InputPlantPartAdapter(this, "plant-part")
        plantPartInputAdapter.submitList(listInputPlantPart.map { InputPlantModel(it) })
        binding.rvListPlantPartInput.apply {
            adapter = plantPartInputAdapter
            layoutManager =
                LinearLayoutManager(this@AddProductActivity, LinearLayoutManager.HORIZONTAL, false)
        }

        // Plant-Part Array
        plantPartArrayAdapter =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, listPlantPart)
        binding.edProductPlantPart.apply {
            setAdapter(plantPartArrayAdapter)
            onItemClickListener =
                OnItemClickListener { _, _, position, _ ->
                    val value = plantPartArrayAdapter.getItem(position) ?: ""
                    showToast(value)
                    text = null
                    listInputPlantPart.add(value)
                    plantPartInputAdapter.submitList(listInputPlantPart.map { InputPlantModel(it) })
                }
        }
    }

    override fun deleteItemSelected(position: Int, input: String) {
        when (input) {
            "plant" -> {
                listInputPlant.removeAt(position)
                plantInputAdapter.submitList(listInputPlant.map { InputPlantModel(it) })
                showToast("test Remove $position")
            }

            "plant-part" -> {
                listInputPlantPart.removeAt(position)
                plantPartInputAdapter.submitList(listInputPlantPart.map { InputPlantModel(it) })
                showToast("test Remove part $position")
            }
        }
    }

    override fun deleteItemSelected(position: Int) {
        viewModel.removeImage(position)
    }

    private fun openGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, ActivityAddProductBinding::class.java)
            context.startActivity(starter)
        }
    }


}