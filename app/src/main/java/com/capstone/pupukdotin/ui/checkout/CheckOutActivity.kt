package com.capstone.pupukdotin.ui.checkout

import android.app.Dialog
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Location
import android.os.Bundle
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.pupukdotin.R
import com.capstone.pupukdotin.databinding.ActivityCheckOutBinding
import com.capstone.pupukdotin.ui.adapter.TesCheckoutAdapter
import com.capstone.pupukdotin.ui.common.BaseActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class CheckOutActivity : BaseActivity<ActivityCheckOutBinding>(), OnMapReadyCallback {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TesCheckoutAdapter

    override fun getViewBinding(): ActivityCheckOutBinding = ActivityCheckOutBinding.inflate(layoutInflater)

    private lateinit var currentLocation: Location
    private lateinit var fusedLocationProvider: FusedLocationProviderClient
    private val permissionCode = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setUpMetodePengiriman()
        setUpRecycleView()
        setUpMetodePembayaran()
        setUpAction()

        fusedLocationProvider = LocationServices.getFusedLocationProviderClient(this)
        fetchLocation()

    }

    private fun setUpAction() {
        binding.toolbarCheckout.btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        //tombol pesan
        binding.checkoutButtonPesan.setOnClickListener {

            val dialogConfirmBinding = layoutInflater.inflate(R.layout.dialog_confirmation, null)

            val confirmationDialog = Dialog(this)
            confirmationDialog.setContentView(dialogConfirmBinding)

            confirmationDialog.setCancelable(false)
            confirmationDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            confirmationDialog.show()

            val yaBtn = dialogConfirmBinding.findViewById<Button>(R.id.ya_button)
            yaBtn.setOnClickListener{
                val dialogBinding = layoutInflater.inflate(R.layout.dialog_pesanan_berhasil, null)

                val pesananBerhasilDialog = Dialog(this)
                pesananBerhasilDialog.setContentView(dialogBinding)

                pesananBerhasilDialog.setCancelable(false)
                pesananBerhasilDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                pesananBerhasilDialog.show()

                val lihatStatusBtn = dialogBinding.findViewById<Button>(R.id.lihat_status_dialog_button)
                lihatStatusBtn.setOnClickListener{
                    //isi ini untuk pindah ke halaman status
                }

                val kembaliBtn = dialogBinding.findViewById<Button>(R.id.kembali_dialog_button)
                kembaliBtn.setOnClickListener{
                    pesananBerhasilDialog.dismiss()
                    confirmationDialog.dismiss()
                }
            }

            val tidakBtn = dialogConfirmBinding.findViewById<Button>(R.id.tidak_button)
            tidakBtn.setOnClickListener{
                confirmationDialog.dismiss()
            }


        }
    }

    //atur map disini
    private fun fetchLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                permissionCode)
            return
        }

        val task = fusedLocationProvider.lastLocation
        task.addOnSuccessListener { location ->
            if (location != null){
                currentLocation = location

                val supportMapFragment = (supportFragmentManager.findFragmentById(R.id.alamat_penerima_map) as
                        SupportMapFragment?)!!
                supportMapFragment.getMapAsync(this)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){
            permissionCode -> if(grantResults.isEmpty() && grantResults[0] ==
                PackageManager.PERMISSION_GRANTED){
                fetchLocation()
            }
        }
    }

    override fun onMapReady(p0: GoogleMap) {
        val latLng = LatLng(currentLocation.latitude, currentLocation.longitude)
        val markerOptions = MarkerOptions().position(latLng).title("Your Location")
        p0.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        p0.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5f))
        p0.addMarker(markerOptions)
        p0.uiSettings.isZoomControlsEnabled = true
        p0.uiSettings.isIndoorLevelPickerEnabled = true
        p0.uiSettings.isCompassEnabled = true
        p0.uiSettings.isMapToolbarEnabled = true
    }
    //pengaturan map sampai disini

    private fun setUpMetodePembayaran() {
        binding.apply {
            metodePembayaranButton.setOnCheckedChangeListener { group, checkedId ->
                if (checkedId == R.id.button_tunai){
                    buttonTunai.setTextColor(resources.getColor(R.color.white))
                    buttonDompetDigital.setTextColor(resources.getColor(R.color.green_13C193))

                }
                else{
                    buttonTunai.setTextColor(resources.getColor(R.color.green_13C193))
                    buttonDompetDigital.setTextColor(resources.getColor(R.color.white))
                }
            }
        }
    }

    private fun setUpRecycleView() {
        recyclerView = findViewById(R.id.rv_checkout_items)
        adapter = TesCheckoutAdapter()

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }


    private fun setUpMetodePengiriman() {
        binding.apply {
            metodePengirimanButton.setOnCheckedChangeListener { group, checkedId ->
                if (checkedId == R.id.button_dikirim){
                    buttonDikirim.setTextColor(resources.getColor(R.color.white))
                    buttonAmbilSendiri.setTextColor(resources.getColor(R.color.green_13C193))

                }
                else{
                    buttonDikirim.setTextColor(resources.getColor(R.color.green_13C193))
                    buttonAmbilSendiri.setTextColor(resources.getColor(R.color.white))
                }
            }
        }
    }
}