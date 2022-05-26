package com.example.app27_googlemap

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import com.example.app27_googlemap.databinding.ActivityMainBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var googleMap: GoogleMap
    val loc = LatLng(37.554752, 126.970631)
    val arrLoc = ArrayList<LatLng>()

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


    private fun initMap() {
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync {
            googleMap = it
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0F))
            googleMap.setMinZoomPreference(10.0f)
            googleMap.setMaxZoomPreference(18.0f)

            val options = MarkerOptions()
            options.position(loc)
            options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
            options.title("역")
            options.snippet("서울역")
            googleMap.addMarker(options)?.showInfoWindow()

            googleMap.setOnMapClickListener {
                arrLoc.add(it)
                googleMap.clear()

                val option2 = MarkerOptions()
                option2.position(it)
                options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                googleMap.addMarker(option2)


//                val option3 = PolylineOptions().color(Color.GREEN).addAll(arrLoc)
//                googleMap.addPolyline(option3)
//
                val option3 = PolygonOptions().fillColor(Color.argb(100,255,255,0))
                    .strokeColor(Color.GREEN).addAll(arrLoc)
                googleMap.addPolygon(option3)


            }

        }


    }


}