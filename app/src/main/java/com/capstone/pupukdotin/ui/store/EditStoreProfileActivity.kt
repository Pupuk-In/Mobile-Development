package com.capstone.pupukdotin.ui.store

import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import com.capstone.pupukdotin.R
import com.capstone.pupukdotin.databinding.ActivityEditStoreProfileBinding
import com.capstone.pupukdotin.databinding.ActivityLihatProfileBinding
import com.capstone.pupukdotin.ui.common.BaseActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class EditStoreProfileActivity : BaseActivity<ActivityEditStoreProfileBinding>(),
    OnMapReadyCallback {

    private lateinit var currentLocation: Location
    private lateinit var fusedLocationProvider: FusedLocationProviderClient
    private val permissionCode = 101
    override fun getViewBinding(): ActivityEditStoreProfileBinding = ActivityEditStoreProfileBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setUpAction()

        fusedLocationProvider = LocationServices.getFusedLocationProviderClient(this)
        fetchLocation()

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

                val supportMapFragment = (supportFragmentManager.findFragmentById(R.id.ubah_profile_store_map) as
                        SupportMapFragment?)!!
                supportMapFragment.getMapAsync(this)
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

    private fun setUpAction() {
        binding.toolbarUbahProfileToko.btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}