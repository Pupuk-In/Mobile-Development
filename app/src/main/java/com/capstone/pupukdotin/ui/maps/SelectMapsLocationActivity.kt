package com.capstone.pupukdotin.ui.maps

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.os.Build
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.capstone.pupukdotin.R
import com.capstone.pupukdotin.databinding.ActivitySelectMapsLocationBinding
import com.capstone.pupukdotin.ui.common.BaseActivity
import com.capstone.pupukdotin.utils.PermissionUtils.PermissionDeniedDialog.Companion.newInstance
import com.capstone.pupukdotin.utils.PermissionUtils.isPermissionGranted
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import java.io.IOException
import java.util.Locale


class SelectMapsLocationActivity : BaseActivity<ActivitySelectMapsLocationBinding>(),
    OnMapReadyCallback, LocationListener, GoogleMap.OnCameraMoveListener,
    GoogleMap.OnCameraMoveStartedListener, GoogleMap.OnCameraIdleListener {

    private lateinit var mMap: GoogleMap
    private var permissionDenied = false

    private lateinit var currentLocation: Location
    private lateinit var fusedLocationProvider: FusedLocationProviderClient

    override fun getViewBinding(): ActivitySelectMapsLocationBinding =
        ActivitySelectMapsLocationBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.topBar.apply {
            tvTitleBar.text = getString(R.string.select_location)
            root.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }
        fusedLocationProvider = LocationServices.getFusedLocationProviderClient(this)
        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.map_view) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        setupAction()
    }

    private fun setupAction() {
        binding.btnSaveLocation.setOnClickListener {
            val value = mMap.cameraPosition.target
            val bundle = Bundle()
            bundle.putDouble(LAT_VALUE, value.latitude)
            bundle.putDouble(LONG_VALUE, value.longitude)

            val resultIntent = Intent()
            resultIntent.putExtras(bundle)
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnCameraMoveListener(this)
        mMap.setOnCameraMoveStartedListener(this)
        mMap.setOnCameraIdleListener(this)
        enableMyLocation()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            super.onRequestPermissionsResult(
                requestCode,
                permissions,
                grantResults
            )
            return
        }

        if (isPermissionGranted(permissions, grantResults, ACCESS_FINE_LOCATION)
            || isPermissionGranted(permissions, grantResults, ACCESS_COARSE_LOCATION)
        ) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation()
        } else {
            // Permission was denied. Display an error message
            // Display the missing permission error dialog when the fragments resume.
            permissionDenied = true
        }
    }

    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    @SuppressLint("MissingPermission")
    private fun enableMyLocation() {

        // 1. Check if permissions are granted, if so, enable the my location layer
        if (ContextCompat.checkSelfPermission(
                this,
                ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                this,
                ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true
            val task = fusedLocationProvider.lastLocation
            task.addOnSuccessListener { location ->
                if (location != null) {
                    currentLocation = location
                    setLocation()
                } else {
                    showToast("No location")
                }
            }
            return
        }


        // 2. Otherwise, request permission
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                ACCESS_FINE_LOCATION,
                ACCESS_COARSE_LOCATION
            ),
            LOCATION_PERMISSION_REQUEST_CODE
        )
    }

    private fun setLocation() {
        val latLng = LatLng(currentLocation.latitude, currentLocation.longitude)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20f))
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        if (permissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError()
            permissionDenied = false
        }
    }

    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private fun showMissingPermissionError() {
        newInstance(true).show(supportFragmentManager, "dialog")
    }

    override fun onLocationChanged(location: Location) {
        setAddress(getAddressName(location.latitude, location.longitude))
    }

    private fun getAddressName(latitude: Double, longitude: Double): String? {
        var addressName: String? = null
        val geocoder = Geocoder(this@SelectMapsLocationActivity, Locale("id", "ID"))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            geocoder.getFromLocation(latitude, longitude, 1) { list ->
                if (list.size != 0) {
                    addressName = list[0].getAddressLine(0)
                }
            }
        } else {
            try {
                @Suppress("DEPRECATION")
                val list = geocoder.getFromLocation(latitude, longitude, 1)
                if (list != null && list.size != 0) {
                    addressName = list[0].getAddressLine(0)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return addressName
    }

    private fun setAddress(address: String?) {
        binding.tvAddress.text = address ?: ""
    }

    override fun onCameraMove() {
        showLoading(true)
    }

    private fun showLoading(value: Boolean) {
        binding.pbLoadingAddress.isVisible = value
        binding.tvAddress.isVisible = !value
        binding.btnSaveLocation.isVisible = !value
    }

    override fun onCameraMoveStarted(p0: Int) {
        showLoading(true)
    }

    override fun onCameraIdle() {
        showLoading(false)
        val latitude = mMap.cameraPosition.target.latitude
        val longitude = mMap.cameraPosition.target.longitude
        setAddress(getAddressName(latitude, longitude))
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
        const val LAT_VALUE = "lat_value"
        const val LONG_VALUE = "long_value"
        const val EXTRA_ARRAY_DOUBLE = "extra_array_double"
        const val RESULT_CODE = 110
    }
}