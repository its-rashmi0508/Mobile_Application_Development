package com.example.mygooglemap

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButtonToggleGroup
import org.maplibre.android.MapLibre
import org.maplibre.android.camera.CameraPosition
import org.maplibre.android.geometry.LatLng
import org.maplibre.android.maps.MapView
import org.maplibre.android.maps.MapLibreMap
import org.maplibre.android.maps.Style
import org.maplibre.android.annotations.MarkerOptions

class MainActivity : AppCompatActivity() {

    private lateinit var mapView: MapView
    private var map: MapLibreMap? = null

    private val normalStyle =
        "https://tiles.openfreemap.org/styles/liberty"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MapLibre.getInstance(this)

        setContentView(R.layout.activity_main)

        mapView = findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)

        mapView.getMapAsync { mapLibreMap ->

            map = mapLibreMap

            mapLibreMap.uiSettings.isZoomGesturesEnabled = true
            mapLibreMap.uiSettings.isCompassEnabled = true
            mapLibreMap.uiSettings.isZoomControlsEnabled = true

            val defaultLocation = LatLng(28.6129, 77.2295)

            mapLibreMap.cameraPosition = CameraPosition.Builder()
                .target(defaultLocation)
                .zoom(12.0)
                .build()

            mapLibreMap.setStyle(normalStyle) { style ->

                mapLibreMap.addMarker(
                    MarkerOptions()
                        .position(defaultLocation)
                        .title("India Gate")
                        .snippet("New Delhi, India")
                )
            }

            setupMapTypeToggle()
        }
    }

    private fun setupMapTypeToggle() {

        val toggleGroup =
            findViewById<MaterialButtonToggleGroup>(
                R.id.map_type_toggle_group
            )

        toggleGroup.addOnButtonCheckedListener {
                _, checkedId, isChecked ->

            if (!isChecked) return@addOnButtonCheckedListener

            when (checkedId) {

                R.id.btn_normal -> {
                    loadMapStyle(normalStyle)
                }

                R.id.btn_satellite -> {
                    showStyleMessage("Satellite imagery requires a suitable imagery provider.")
                }

                R.id.btn_terrain -> {
                    showStyleMessage("Configure a terrain map style to use this option.")
                }

                R.id.btn_hybrid -> {
                    showStyleMessage("Configure a satellite and labels style to use this option.")
                }
            }
        }
    }

    private fun loadMapStyle(styleUrl: String) {
        map?.setStyle(styleUrl)
    }

    private fun showStyleMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        mapView.onPause()
        super.onPause()
    }

    override fun onStop() {
        mapView.onStop()
        super.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onDestroy() {
        mapView.onDestroy()
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }
}
