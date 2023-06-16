package com.capstone.pupukdotin.ui.addproduct

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.pupukdotin.R
import com.capstone.pupukdotin.data.remote.network.NetworkResult
import com.capstone.pupukdotin.data.remote.payload.items.CreateNewItemPayload
import com.capstone.pupukdotin.data.remote.response.PlantPartResponse
import com.capstone.pupukdotin.data.remote.response.PlantResponse
import com.capstone.pupukdotin.data.remote.response.common.TypeItem
import com.capstone.pupukdotin.databinding.ActivityAddProductBinding
import com.capstone.pupukdotin.ui.ViewModelFactory
import com.capstone.pupukdotin.ui.common.BaseActivity
import com.capstone.pupukdotin.utils.uriToFile

class AddProductActivity : BaseActivity<ActivityAddProductBinding>(),
    InputPlantPartAdapter.OnItemDeleteListener, InputImageAdapter.OnItemDeleteListener {

    private val viewModel by viewModels<AddProductViewModel> { ViewModelFactory(this) }

    // List Image
    private val listInputImage = mutableListOf<String>()
    private lateinit var imageInputAdapter: InputImageAdapter

    // Type
    private val listType = mutableListOf<InputPlantOrTypeOrPartModel>()
    private lateinit var spinnerTypeArrayAdapter: ArrayAdapter<String>

    // Plant
    private val listInputPlant = mutableListOf<InputPlantOrTypeOrPartModel>()
    private lateinit var plantArrayAdapter: ArrayAdapter<String>
    private val listPlant = mutableListOf<InputPlantOrTypeOrPartModel>()
    private lateinit var plantInputAdapter: InputPlantPartAdapter

    // Plant Part
    private val listInputPlantPart = mutableListOf<InputPlantOrTypeOrPartModel>()
    private lateinit var plantPartArrayAdapter: ArrayAdapter<String>
    private val listPlantPart = mutableListOf<InputPlantOrTypeOrPartModel>()
    private lateinit var plantPartInputAdapter: InputPlantPartAdapter

    // Intent Gallery
    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg = result.data?.data as Uri
            selectedImg.let { uri ->
                val myFile = uriToFile(uri, this@AddProductActivity)
                viewModel.uploadImage(myFile)
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
            tvTitleBar.text = getString(R.string.add_product)
            root.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }


        viewModel.getAllTypes()
        viewModel.getAllPlants()
        viewModel.getAllPlantParts()

        setupViewModel()
        setupAdapter()
        setupAction()
    }

    private fun setupViewModel() {
        viewModel.listImageUploaded.observe(this) { listImage ->
            imageInputAdapter.submitList(listImage.map { InputImageModel(it) })
        }

        viewModel.fileUploaded.observe(this) { result ->
            when (result) {
                is NetworkResult.Loading -> {
                    showImageLoading(true)
                }

                is NetworkResult.Success -> {
                    viewModel.addImage(result.data.file?.url ?: "")
                    showImageLoading(false)
                }

                is NetworkResult.Error -> {
                    showImageLoading(false)
                    showToast(result.error.toString())
                }
            }
        }

        viewModel.allPlantParts.observe(this) { result ->
            when (result) {
                is NetworkResult.Loading -> {
                    showLoading(true)
                }

                is NetworkResult.Success -> {
                    setupPlantPartList(result.data.plantPart)
                    showLoading(false)
                }

                is NetworkResult.Error -> {
                    showLoading(false)
                }
            }
        }

        viewModel.allPlants.observe(this) { result ->
            when (result) {
                is NetworkResult.Loading -> {
                    showLoading(true)
                }

                is NetworkResult.Success -> {
                    setupPlantList(result.data.plant)
                    showLoading(false)
                }

                is NetworkResult.Error -> {
                    showLoading(false)
                }
            }
        }

        viewModel.allTypes.observe(this) { result ->
            when (result) {
                is NetworkResult.Loading -> {
                    showLoading(true)
                }

                is NetworkResult.Success -> {
                    showLoading(false)
                    setupTypeList(result.data.type)
                }

                is NetworkResult.Error -> {
                    showLoading(false)
                }
            }
        }

        viewModel.createProduct.observe(this) { result ->
            when (result) {
                is NetworkResult.Loading -> {
                    showLoading(true)
                }

                is NetworkResult.Success -> {
                    showLoading(false)
                    showOneActionDialogWithSub(
                        "Berhasil",
                        "Produk berhasil ditambahkan",
                        btnPositiveMesssage = "Ya",
                        onYesClickBtnClicked = {
                            onBackPressedDispatcher.onBackPressed()
                        }
                    )
                }

                is NetworkResult.Error -> {
                    showLoading(false)
                }
            }
        }
    }

    private fun showImageLoading(value: Boolean) {
        binding.pbImageLoading.isVisible = value
    }


    private fun setupTypeList(type: List<TypeItem>?) {
        listType.clear()
        listType.addAll(type?.map { InputPlantOrTypeOrPartModel(it.name ?: "", it.id ?: 0) }
            ?: emptyList())
        // Type Array
        spinnerTypeArrayAdapter =
            ArrayAdapter(this, R.layout.item_spinner, listType.map { it.name })
        spinnerTypeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spProductType.adapter = spinnerTypeArrayAdapter
    }

    private fun setupPlantList(plant: List<PlantResponse.PlantItem>?) {
        listPlant.clear()
        listPlant.addAll(plant?.map { InputPlantOrTypeOrPartModel(it.name ?: "", it.id ?: 0) }
            ?: emptyList())
        plantArrayAdapter =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, listPlant.map { it.name })
        binding.edProductPlant.apply {
            setAdapter(plantArrayAdapter)
            onItemClickListener =
                OnItemClickListener { _, _, position, _ ->
                    val value = plantArrayAdapter.getItem(position)
                    val getPlant = listPlant.filter { it.name == value }
                    text = null
                    listInputPlant.add(getPlant[0])
                    plantInputAdapter.submitList(listInputPlant.map { PlantOrTypeOrPartModel(it.name) })
                }
        }
    }

    private fun setupPlantPartList(plantPart: List<PlantPartResponse.PlantPartItem>?) {
        listPlantPart.clear()
        listPlantPart.addAll(plantPart?.map { InputPlantOrTypeOrPartModel(it.name, it.id) }
            ?: emptyList())
        plantPartArrayAdapter =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, listPlantPart.map { it.name })
        binding.edProductPlantPart.apply {
            setAdapter(plantPartArrayAdapter)
            onItemClickListener =
                OnItemClickListener { _, _, position, _ ->
                    val value = plantPartArrayAdapter.getItem(position)
                    val getPlant = listPlantPart.filter { it.name == value }
                    text = null
                    listInputPlantPart.add(getPlant[0])
                    plantPartInputAdapter.submitList(listInputPlantPart.map {
                        PlantOrTypeOrPartModel(
                            it.name
                        )
                    })
                }
        }
    }

    private fun showLoading(value: Boolean) {
        binding.apply {
            pbLoadingScreen.isVisible = value
            nsvContent.isVisible = !value
        }
    }

    private fun setupAction() {
        with(binding) {
            cvUploadImage.setOnClickListener { openGallery() }
            btnSave.setOnClickListener {
                val productName = edProductName.text.toString()
                val productType = spProductType.selectedItemPosition + 1
                val description = edProductDescription.text.toString()
                val price = edProductPrice.text.toString().toInt()
                val stock = edProductStock.text.toString().toInt()
                val plantId: List<Int> = listInputPlant.map { it.id }
                val plantPartId: List<Int> = listInputPlantPart.map { it.id }

                viewModel.createNewItem(
                    CreateNewItemPayload(
                        name = productName,
                        description = description,
                        typeId = productType,
                        price = price,
                        stock = stock,
                        plantId = plantId,
                        plantPartId = plantPartId
                    )
                )
            }
        }
    }

    private fun setupAdapter() {
        // Image Input
        imageInputAdapter = InputImageAdapter(this)
//        imageInputAdapter.submitList(listInputImage.map { InputImageModel(it) })
        binding.rvListImage.apply {
            adapter = imageInputAdapter
            layoutManager =
                LinearLayoutManager(this@AddProductActivity, LinearLayoutManager.HORIZONTAL, false)
        }

        // Type Array
        spinnerTypeArrayAdapter = ArrayAdapter(this, R.layout.item_spinner, emptyList())
        binding.spProductType.adapter = spinnerTypeArrayAdapter

        // Plant Input
        plantInputAdapter = InputPlantPartAdapter(this, "plant")
        plantInputAdapter.submitList(emptyList())
        binding.rvListPlantInput.apply {
            adapter = plantInputAdapter
            layoutManager =
                LinearLayoutManager(this@AddProductActivity, LinearLayoutManager.HORIZONTAL, false)
        }

        // Plant Array
        plantArrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, emptyList())
        binding.edProductPlant.setAdapter(plantArrayAdapter)

        // Plant-Part Input
        plantPartInputAdapter = InputPlantPartAdapter(this, "plant-part")
        plantPartInputAdapter.submitList(emptyList())
        binding.rvListPlantPartInput.apply {
            adapter = plantPartInputAdapter
            layoutManager =
                LinearLayoutManager(this@AddProductActivity, LinearLayoutManager.HORIZONTAL, false)
        }

        // Plant-Part Array
        plantPartArrayAdapter =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, emptyList())
        binding.edProductPlantPart.setAdapter(plantPartArrayAdapter)
    }

    override fun deleteItemSelected(position: Int, input: String) {
        when (input) {
            "plant" -> {
                listInputPlant.removeAt(position)
                plantInputAdapter.submitList(listInputPlant.map { PlantOrTypeOrPartModel(it.name) })
                showToast("test Remove $position")
            }

            "plant-part" -> {
                listInputPlantPart.removeAt(position)
                plantPartInputAdapter.submitList(listInputPlantPart.map { PlantOrTypeOrPartModel(it.name) })
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
            val starter = Intent(context, AddProductActivity::class.java)
            context.startActivity(starter)
        }
    }


}