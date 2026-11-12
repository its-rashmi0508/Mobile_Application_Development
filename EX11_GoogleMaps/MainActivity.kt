package com.example.mygooglemap

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButtonToggleGroup
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.tileprovider.tilesource.XYTileSource
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.ScaleBarOverlay
import org.osmdroid.views.overlay.compass.CompassOverlay
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay

class MainActivity : AppCompatActivity() {

    private lateinit var mapView: MapView
    private var locationOverlay: MyLocationNewOverlay? = null

    // CartoDB Voyager (Normal) - Fast, high-resolution street tiles
    private val cartoDbVoyager = XYTileSource(
        "CartoDB_Voyager",
        0,
        19,
        256,
        ".png",
        arrayOf(
            "https://cartodb-basemaps-a.global.ssl.fastly.net/rastertiles/voyager/",
            "https://cartodb-basemaps-b.global.ssl.fastly.net/rastertiles/voyager/",
            "https://cartodb-basemaps-c.global.ssl.fastly.net/rastertiles/voyager/"
        )
    )

    // Esri World Imagery (Satellite)
    private val esriSatellite = XYTileSource(
        "EsriSatellite",
        0,
        19,
        256,
        ".jpg",
        arrayOf("https://server.arcgisonline.com/ArcGIS/rest/services/World_Imagery/MapServer/tile/")
    )

    // CartoDB Dark Matter (Hybrid / Dark View)
    private val cartoDbDark = XYTileSource(
        "CartoDB_Dark",
        0,
        19,
        256,
        ".png",
        arrayOf(
            "https://cartodb-basemaps-a.global.ssl.fastly.net/dark_all/",
            "https://cartodb-basemaps-b.global.ssl.fastly.net/dark_all/",
            "https://cartodb-basemaps-c.global.ssl.fastly.net/dark_all/"
        )
    )

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) ||
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                enableMyLocation()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set compliant User-Agent header required by tile servers
        val ctx = applicationContext
        Configuration.getInstance().userAgentValue = "MyGoogleMapApp/1.0 (Android; com.example.mygooglemap)"
        Configuration.getInstance().load(ctx, getSharedPreferences("osmdroid", MODE_PRIVATE))

        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        mapView = findViewById(R.id.map_view)
        mapView.setTileSource(cartoDbVoyager)
        mapView.setMultiTouchControls(true)

        // Configure Map Zoom & Center (India Gate, New Delhi)
        val mapController = mapView.controller
        mapController.setZoom(15.0)
        val defaultLocation = GeoPoint(28.6129, 77.2295)
        mapController.setCenter(defaultLocation)

        // Add Default Location Marker
        val marker = Marker(mapView)
        marker.position = defaultLocation
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        marker.title = getString(R.string.default_location_title)
        marker.snippet = getString(R.string.default_location_snippet)
        mapView.overlays.add(marker)

        // Add Compass Overlay
        val compassOverlay = CompassOverlay(this, InternalCompassOrientationProvider(this), mapView)
        compassOverlay.enableCompass()
        mapView.overlays.add(compassOverlay)

        // Add Scale Bar Overlay
        val scaleBarOverlay = ScaleBarOverlay(mapView)
        scaleBarOverlay.setCentred(true)
        scaleBarOverlay.setScaleBarOffset(200, 10)
        mapView.overlays.add(scaleBarOverlay)

        setupMapTypeToggle()
        checkLocationPermissions()
    }

    private fun setupMapTypeToggle() {
        val toggleGroup = findViewById<MaterialButtonToggleGroup>(R.id.map_type_toggle_group)
        toggleGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.btn_normal -> mapView.setTileSource(cartoDbVoyager)
                    R.id.btn_satellite -> mapView.setTileSource(esriSatellite)
                    R.id.btn_terrain -> mapView.setTileSource(TileSourceFactory.OpenTopo)
                    R.id.btn_hybrid -> mapView.setTileSource(cartoDbDark)
                    else -> mapView.setTileSource(cartoDbVoyager)
                }
                mapView.invalidate()
            }
        }
    }

    private fun checkLocationPermissions() {
        val hasFine = ContextCompat.checkSelfPermission(
            this, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val hasCoarse = ContextCompat.checkSelfPermission(
            this, Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (hasFine || hasCoarse) {
            enableMyLocation()
        } else {
            locationPermissionRequest.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    private fun enableMyLocation() {
        if (locationOverlay == null) {
            locationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(this), mapView)
            locationOverlay?.enableMyLocation()
            mapView.overlays.add(locationOverlay)
            mapView.invalidate()
        }
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }
}