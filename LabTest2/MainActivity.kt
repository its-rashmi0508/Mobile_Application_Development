package com.example.ecotrack

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.text.InputType
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var goalPreferences: GoalPreferences
    private lateinit var dbHelper: WaterDatabaseHelper
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private var googleMap: GoogleMap? = null
    private var currentLocation: Location? = null
    private var locationCallback: LocationCallback? = null

    // Navigation & Views
    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var layoutHome: ScrollView
    private lateinit var layoutAddWater: ScrollView
    private lateinit var layoutMap: View
    private lateinit var layoutHistory: View

    // Home Tab UI
    private lateinit var progressBarCircular: ProgressBar
    private lateinit var tvProgressAmountCenter: TextView
    private lateinit var tvProgressGoalSubtext: TextView
    private lateinit var tvConsumedHome: TextView
    private lateinit var tvRemainingHome: TextView
    private lateinit var tvPercentHome: TextView

    // Add Water Tab UI
    private lateinit var chipGroupCategory: ChipGroup
    private lateinit var etWaterAmountTab: TextInputEditText
    private lateinit var tvCurrentLocationTab: TextView

    // History Tab UI
    private lateinit var rvHistoryTab: RecyclerView
    private lateinit var tvEmptyHistoryTab: TextView
    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var tvMapPinCount: TextView

    private val locationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val fineGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false
        val coarseGranted = permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false

        if (fineGranted || coarseGranted) {
            startLocationUpdates()
            enableMapLocation()
        } else {
            Toast.makeText(
                this,
                "Location permission denied. Default location will be used.",
                Toast.LENGTH_SHORT
            ).show()
            tvCurrentLocationTab.text = "GPS: Permission denied (0.0, 0.0)"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        try {
            MapsInitializer.initialize(applicationContext)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        goalPreferences = GoalPreferences(this)
        dbHelper = WaterDatabaseHelper(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        initViews()
        setupBottomNavigation()
        setupListeners()
        setupRecyclerView()
        setupLocationCallback()
        checkLocationPermissions()

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapFragmentTab) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        updateProgressUI()
    }

    private fun initViews() {
        bottomNavigation = findViewById(R.id.bottomNavigation)
        layoutHome = findViewById(R.id.layoutHome)
        layoutAddWater = findViewById(R.id.layoutAddWater)
        layoutMap = findViewById(R.id.layoutMap)
        layoutHistory = findViewById(R.id.layoutHistory)

        // Home UI
        progressBarCircular = findViewById(R.id.progressBarCircular)
        tvProgressAmountCenter = findViewById(R.id.tvProgressAmountCenter)
        tvProgressGoalSubtext = findViewById(R.id.tvProgressGoalSubtext)
        tvConsumedHome = findViewById(R.id.tvConsumedHome)
        tvRemainingHome = findViewById(R.id.tvRemainingHome)
        tvPercentHome = findViewById(R.id.tvPercentHome)

        // Add Water UI
        chipGroupCategory = findViewById(R.id.chipGroupCategory)
        etWaterAmountTab = findViewById(R.id.etWaterAmountTab)
        tvCurrentLocationTab = findViewById(R.id.tvCurrentLocationTab)

        // History UI
        rvHistoryTab = findViewById(R.id.rvHistoryTab)
        tvEmptyHistoryTab = findViewById(R.id.tvEmptyHistoryTab)
        tvMapPinCount = findViewById(R.id.tvMapPinCount)
    }

    private fun setupBottomNavigation() {
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    showTab(layoutHome)
                    true
                }
                R.id.nav_add_water -> {
                    showTab(layoutAddWater)
                    true
                }
                R.id.nav_map -> {
                    showTab(layoutMap)
                    updateMapMarkers()
                    true
                }
                R.id.nav_history -> {
                    showTab(layoutHistory)
                    updateHistoryList()
                    true
                }
                else -> false
            }
        }
    }

    private fun showTab(activeTab: View) {
        layoutHome.visibility = if (activeTab == layoutHome) View.VISIBLE else View.GONE
        layoutAddWater.visibility = if (activeTab == layoutAddWater) View.VISIBLE else View.GONE
        layoutMap.visibility = if (activeTab == layoutMap) View.VISIBLE else View.GONE
        layoutHistory.visibility = if (activeTab == layoutHistory) View.VISIBLE else View.GONE
    }

    private fun setupListeners() {
        // Feature Grid Cards Navigation
        findViewById<MaterialCardView>(R.id.cardWaterTracker).setOnClickListener {
            bottomNavigation.selectedItemId = R.id.nav_add_water
        }
        findViewById<MaterialCardView>(R.id.cardLocationTracker).setOnClickListener {
            bottomNavigation.selectedItemId = R.id.nav_map
        }
        findViewById<MaterialCardView>(R.id.cardProgressOverview).setOnClickListener {
            showSetGoalDialog()
        }
        findViewById<MaterialCardView>(R.id.cardHistoryOverview).setOnClickListener {
            bottomNavigation.selectedItemId = R.id.nav_history
        }

        findViewById<MaterialButton>(R.id.btnSetGoalHome).setOnClickListener {
            showSetGoalDialog()
        }

        findViewById<MaterialButton>(R.id.btnQuick250Add).setOnClickListener {
            recordWaterConsumption(250)
        }
        findViewById<MaterialButton>(R.id.btnQuick500Add).setOnClickListener {
            recordWaterConsumption(500)
        }
        findViewById<MaterialButton>(R.id.btnQuick750Add).setOnClickListener {
            recordWaterConsumption(750)
        }

        findViewById<MaterialButton>(R.id.btnSaveWaterEntry).setOnClickListener {
            val inputText = etWaterAmountTab.text?.toString()?.trim()
            if (!inputText.isNullOrEmpty()) {
                val amount = inputText.toIntOrNull()
                if (amount != null && amount > 0) {
                    recordWaterConsumption(amount)
                    etWaterAmountTab.text?.clear()
                } else {
                    Toast.makeText(this, "Please enter a valid amount in ml", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please enter amount of water", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupRecyclerView() {
        rvHistoryTab.layoutManager = LinearLayoutManager(this)
        historyAdapter = HistoryAdapter(emptyList()) { entry ->
            dbHelper.deleteEntry(entry.id)
            Toast.makeText(this, "Entry deleted", Toast.LENGTH_SHORT).show()
            refreshData()
        }
        rvHistoryTab.adapter = historyAdapter
        refreshData()
    }

    private fun setupLocationCallback() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                val loc = result.lastLocation ?: return
                currentLocation = loc
                val locationName = getGeocodedAddress(loc.latitude, loc.longitude)
                tvCurrentLocationTab.text = "GPS: $locationName (Lat %.4f, Lng %.4f)".format(loc.latitude, loc.longitude)
            }
        }
    }

    private fun checkLocationPermissions() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            startLocationUpdates()
        } else {
            locationPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 4000)
            .setMinUpdateIntervalMillis(2000)
            .build()

        locationCallback?.let { callback ->
            fusedLocationClient.requestLocationUpdates(locationRequest, callback, Looper.getMainLooper())
        }

        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
            .addOnSuccessListener { loc ->
                if (loc != null) {
                    currentLocation = loc
                    val locationName = getGeocodedAddress(loc.latitude, loc.longitude)
                    tvCurrentLocationTab.text = "GPS: $locationName (Lat %.4f, Lng %.4f)".format(loc.latitude, loc.longitude)
                }
            }
    }

    private fun getGeocodedAddress(lat: Double, lng: Double): String {
        if (lat == 0.0 && lng == 0.0) return "Unknown Location"
        return try {
            val geocoder = Geocoder(this, Locale.getDefault())
            @Suppress("DEPRECATION")
            val addresses = geocoder.getFromLocation(lat, lng, 1)
            if (!addresses.isNullOrEmpty()) {
                val address = addresses[0]
                val locality = address.locality ?: address.subLocality ?: address.featureName
                val adminArea = address.adminArea ?: ""
                if (!locality.isNullOrEmpty()) "$locality, $adminArea".trim(',', ' ')
                else address.getAddressLine(0) ?: "Lat %.2f, Lng %.2f".format(lat, lng)
            } else {
                "Lat %.4f, Lng %.4f".format(lat, lng)
            }
        } catch (e: Exception) {
            "Lat %.4f, Lng %.4f".format(lat, lng)
        }
    }

    private fun getSelectedDrinkType(): String {
        val selectedChipId = chipGroupCategory.checkedChipId
        if (selectedChipId != View.NO_ID) {
            val chip = findViewById<Chip>(selectedChipId)
            val text = chip?.text?.toString() ?: ""
            if (text.contains("Tea")) return "Tea"
            if (text.contains("Juice")) return "Juice"
            if (text.contains("Coffee")) return "Coffee"
        }
        return "Water"
    }

    private fun recordWaterConsumption(amount: Int) {
        val drinkType = getSelectedDrinkType()
        val todayDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        val timestamp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())

        if (currentLocation != null) {
            saveEntryAndNotify(amount, drinkType, todayDate, timestamp, currentLocation!!)
        } else {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
                    .addOnSuccessListener { loc ->
                        if (loc != null) {
                            currentLocation = loc
                            saveEntryAndNotify(amount, drinkType, todayDate, timestamp, loc)
                        } else {
                            fusedLocationClient.lastLocation.addOnSuccessListener { lastLoc ->
                                val finalLoc = lastLoc ?: Location("").apply {
                                    latitude = 12.9716
                                    longitude = 77.5946
                                }
                                saveEntryAndNotify(amount, drinkType, todayDate, timestamp, finalLoc)
                            }
                        }
                    }
                    .addOnFailureListener {
                        val fallbackLoc = Location("").apply {
                            latitude = 12.9716
                            longitude = 77.5946
                        }
                        saveEntryAndNotify(amount, drinkType, todayDate, timestamp, fallbackLoc)
                    }
            } else {
                val fallbackLoc = Location("").apply {
                    latitude = 12.9716
                    longitude = 77.5946
                }
                saveEntryAndNotify(amount, drinkType, todayDate, timestamp, fallbackLoc)
            }
        }
    }

    private fun saveEntryAndNotify(
        amount: Int,
        drinkType: String,
        todayDate: String,
        timestamp: String,
        location: Location
    ) {
        val locationName = getGeocodedAddress(location.latitude, location.longitude)

        dbHelper.insertEntry(
            amount = amount,
            timestamp = timestamp,
            date = todayDate,
            latitude = location.latitude,
            longitude = location.longitude,
            drinkType = drinkType,
            locationName = locationName
        )

        Toast.makeText(this, "Logged $amount ml $drinkType at $locationName!", Toast.LENGTH_SHORT).show()
        refreshData()
    }

    private fun showSetGoalDialog() {
        val input = EditText(this).apply {
            inputType = InputType.TYPE_CLASS_NUMBER
            hint = "Enter daily target in ml (e.g., 2500)"
            setText(goalPreferences.dailyGoal.toString())
        }

        AlertDialog.Builder(this)
            .setTitle("Set Daily Goal")
            .setMessage("Enter your target water consumption for each day (in ml):")
            .setView(input)
            .setPositiveButton("Save") { _, _ ->
                val newGoalStr = input.text.toString().trim()
                val newGoal = newGoalStr.toIntOrNull()
                if (newGoal != null && newGoal > 0) {
                    goalPreferences.dailyGoal = newGoal
                    updateProgressUI()
                    Toast.makeText(this, "Daily target updated to $newGoal ml", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Invalid goal target entered", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun refreshData() {
        updateProgressUI()
        updateHistoryList()
        updateMapMarkers()
    }

    private fun updateProgressUI() {
        val todayDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        val consumedToday = dbHelper.getTodayTotalConsumption(todayDate)
        val dailyGoal = goalPreferences.dailyGoal

        tvProgressAmountCenter.text = "$consumedToday ml"
        tvProgressGoalSubtext.text = "of $dailyGoal ml"
        tvConsumedHome.text = "$consumedToday ml"

        val remaining = (dailyGoal - consumedToday).coerceAtLeast(0)
        tvRemainingHome.text = "$remaining ml"

        progressBarCircular.max = dailyGoal
        progressBarCircular.progress = consumedToday.coerceAtMost(dailyGoal)

        val percentage = if (dailyGoal > 0) {
            ((consumedToday.toDouble() / dailyGoal) * 100).toInt()
        } else {
            0
        }
        tvPercentHome.text = "$percentage%"
    }

    private fun updateHistoryList() {
        val entries = dbHelper.getAllEntries()
        historyAdapter.updateData(entries)

        if (entries.isEmpty()) {
            tvEmptyHistoryTab.visibility = View.VISIBLE
            rvHistoryTab.visibility = View.GONE
        } else {
            tvEmptyHistoryTab.visibility = View.GONE
            rvHistoryTab.visibility = View.VISIBLE
        }
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        googleMap?.mapType = GoogleMap.MAP_TYPE_NORMAL
        googleMap?.uiSettings?.apply {
            isZoomControlsEnabled = true
            isMyLocationButtonEnabled = true
            isCompassEnabled = true
            isMapToolbarEnabled = true
        }
        enableMapLocation()
        updateMapMarkers()
    }

    @SuppressLint("MissingPermission")
    private fun enableMapLocation() {
        if (googleMap == null) return
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            try {
                googleMap?.isMyLocationEnabled = true
            } catch (e: SecurityException) {
                e.printStackTrace()
            }
        }
    }

    private fun updateMapMarkers() {
        val map = googleMap ?: return
        map.clear()

        val entries = dbHelper.getAllEntries()
        tvMapPinCount.text = "${entries.size} drinking location pins on map"

        if (entries.isEmpty()) {
            val defaultLatLng = LatLng(
                currentLocation?.latitude ?: 12.9716,
                currentLocation?.longitude ?: 77.5946
            )
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLatLng, 12f))
            return
        }

        val builder = LatLngBounds.Builder()
        var validLocationCount = 0
        var latestMarker: com.google.android.gms.maps.model.Marker? = null

        for (entry in entries) {
            if (entry.latitude != 0.0 || entry.longitude != 0.0) {
                val point = LatLng(entry.latitude, entry.longitude)
                val marker = map.addMarker(
                    MarkerOptions()
                        .position(point)
                        .title("${entry.drinkType}: ${entry.amount} ml")
                        .snippet("${entry.locationName} | ${entry.timestamp}")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                )
                if (latestMarker == null) {
                    latestMarker = marker
                }
                builder.include(point)
                validLocationCount++
            }
        }

        if (validLocationCount > 0) {
            val bounds = builder.build()
            if (validLocationCount == 1) {
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(bounds.center, 14f))
            } else {
                map.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 120))
            }
            latestMarker?.showInfoWindow()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        locationCallback?.let { fusedLocationClient.removeLocationUpdates(it) }
    }
}
