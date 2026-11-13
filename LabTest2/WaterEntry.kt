package com.example.ecotrack

data class WaterEntry(
    val id: Long = 0,
    val amount: Int,
    val timestamp: String,
    val date: String,
    val latitude: Double,
    val longitude: Double,
    val drinkType: String = "Water",
    val locationName: String = "Unknown Location"
)
