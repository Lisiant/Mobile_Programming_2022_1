package com.example.app27_googlemap

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.example.app27_googlemap.databinding.ActivityMainBinding
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var googleMap: GoogleMap
    var loc = LatLng(37.554752, 126.970631)
    val arrLoc = ArrayList<LatLng>()

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    lateinit var locationRequest2: LocationRequest
    lateinit var locationCallback: LocationCallback
    var startUpdate = false


    val permissions = arrayOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION,
    )

    val activityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (checkGPSProvider()) {
//            getLastLocation()
            startLocationUpdates()
        } else {

            setCurrentLocation(loc)
        }

    }

    val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(
                android.Manifest.permission.ACCESS_FINE_LOCATION, false) ||
                    permissions.getOrDefault(android.Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
//                getLastLocation()
                startLocationUpdates()
            }
            else -> {
                setCurrentLocation(loc)
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun getLastLocation() {
        when{
            checkFineLocationPermission()->{
                if(!checkGPSProvider()){
                    showGPSSetting()
                }else{
                    fusedLocationProviderClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY,
                    object: CancellationToken(){
                        override fun onCanceledRequested(p0: OnTokenCanceledListener): CancellationToken {
                            return CancellationTokenSource().token
                        }

                        override fun isCancellationRequested(): Boolean {
                            return false
                        }

                    }).addOnSuccessListener {
                        if (it!=null){
                            loc = LatLng(it.latitude, it.longitude)
                        }
                        setCurrentLocation(loc)
                    }

//                    fusedLocationProviderClient.lastLocation.addOnSuccessListener {
//                        if (it!=null){
//                            loc = LatLng(it.latitude, it.longitude)
//                        }
//                        setCurrentLocation(loc)
//                    }
                }

            }

            checkCoarseLocationPermission() ->{
                fusedLocationProviderClient.getCurrentLocation(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY,
                    object: CancellationToken(){
                        override fun onCanceledRequested(p0: OnTokenCanceledListener): CancellationToken {
                            return CancellationTokenSource().token
                        }

                        override fun isCancellationRequested(): Boolean {
                            return false
                        }

                    }).addOnSuccessListener {
                    if (it!=null){
                        loc = LatLng(it.latitude, it.longitude)
                    }
                    setCurrentLocation(loc)

//                    fusedLocationProviderClient.lastLocation.addOnSuccessListener {
//                        if (it!=null){
//                            loc = LatLng(it.latitude, it.longitude)
//                        }
//                        setCurrentLocation(loc)
//                    }
                }
            }

            ActivityCompat.shouldShowRequestPermissionRationale(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION)->{
                showPermissionRequestDlg()
            }

            else ->{
                locationPermissionRequest.launch(permissions)
            }
        }
    }

    private fun showPermissionRequestDlg() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("위치 서비스 제공")
        builder.setMessage(
            "앱을 사용하기 위해서 위치 서비스가 필요합니다.\n" + "기기의 위치를 제공하도록 설정하시겠습니까?")

        builder.setPositiveButton("설정", DialogInterface.OnClickListener{ dialog, id ->
                locationPermissionRequest.launch(permissions)
        })

        builder.setNegativeButton("취소",
            DialogInterface.OnClickListener{dialog, id ->
                dialog.dismiss()
                setCurrentLocation(loc)
            })

        builder.create().show()
    }

    fun showGPSSetting() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("위치 서비스 비활성화")
        builder.setMessage(
            "앱을 사용하기 위해서 위치 서비스가 필요합니다.\n" + "위치 설정을 허용하시겠습니까?")

        builder.setPositiveButton("설정", DialogInterface.OnClickListener{ dialog, id ->
            val GpsSettingIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            activityResultLauncher.launch(GpsSettingIntent)
        })

        builder.setNegativeButton("취소",
        DialogInterface.OnClickListener{dialog, id ->
            dialog.dismiss()
            setCurrentLocation(loc)
        })

        builder.create().show()
    }

    private fun checkFineLocationPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(this,
            android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }


    private fun checkCoarseLocationPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(this,
            android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }


    fun checkGPSProvider(): Boolean {
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        return (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
    }


    fun setCurrentLocation(location: LatLng) {
        val option = MarkerOptions()
        option.position(loc)
        option.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
        googleMap.addMarker(option)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        initMap()
        initSpinner()
    }

    private fun initSpinner() {
        val adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            ArrayList<String>()
        )
        adapter.add("Hybrid")
        adapter.add("Normal")
        adapter.add("Satellite")
        adapter.add("Terrain")
        binding.apply {
            spinner.adapter = adapter
            spinner.setSelection(1)
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    p0: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long
                ) {
                    when (position) {
                        0 -> {
                            googleMap.mapType = GoogleMap.MAP_TYPE_HYBRID
                        }
                        1 -> {
                            googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL
                        }

                        2 -> {
                            googleMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
                        }

                        3 -> {
                            googleMap.mapType = GoogleMap.MAP_TYPE_TERRAIN
                        }

                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }


            }

        }


    }

    override fun onResume() {
        super.onResume()
        Log.i("location", "onResume()")
        if (!startUpdate)
            startLocationUpdates()
    }

    override fun onPause() {
        super.onPause()
        Log.i("location", "onPause()")
        stopLocationUpdate()

    }

    private fun stopLocationUpdate() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
        startUpdate = false
        Log.i("location", "stopLocationUpdate()")
    }


    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        when{
            checkFineLocationPermission() ->{
                if (!checkGPSProvider()){
                    showGPSSetting()
                }else{
                    startUpdate = true
                    fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
                    Log.i("location", "startLocationUpdates()")
                }
            }

            checkCoarseLocationPermission() -> {
                startUpdate = true
                fusedLocationProviderClient.requestLocationUpdates(
                    locationRequest2, locationCallback, Looper.getMainLooper()
                )
                Log.i("location", "startLocationUpdates()")
            }

            ActivityCompat.shouldShowRequestPermissionRationale(this,
            android.Manifest.permission.ACCESS_COARSE_LOCATION)->{
                showPermissionRequestDlg()
            }

            else->{
                locationPermissionRequest.launch(permissions)
            }
        }

    }

    private fun initMap() {
        initLocation()
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync {
            googleMap = it

//            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0F))
//            googleMap.setMinZoomPreference(10.0f)
//            googleMap.setMaxZoomPreference(18.0f)
//
//            val options = MarkerOptions()
//            options.position(loc)
//            options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
//            options.title("역")
//            options.snippet("서울역")
//            googleMap.addMarker(options)?.showInfoWindow()
//
//            googleMap.setOnMapClickListener {
//                arrLoc.add(it)
//                googleMap.clear()
//
//                val option2 = MarkerOptions()
//                option2.position(it)
//                options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
//                googleMap.addMarker(option2)
//
//
////                val option3 = PolylineOptions().color(Color.GREEN).addAll(arrLoc)
////                googleMap.addPolyline(option3)
////
//                val option3 = PolygonOptions().fillColor(Color.argb(100,255,255,0))
//                    .strokeColor(Color.GREEN).addAll(arrLoc)
//                googleMap.addPolygon(option3)
//        }
        }
    }

    fun initLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        locationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        locationRequest2 = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        }

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(location: LocationResult) {
                if(location.locations.size == 0) return
                loc = LatLng(location.locations[location.locations.size-1].latitude,
                    location.locations[location.locations.size-1].longitude)
                setCurrentLocation(loc)
                Log.i("location", "LocationCallback()")


            }

        }


//        getLastLocation()
    }

}