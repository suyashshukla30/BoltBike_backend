package com.bike.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Bike(
    val bikeId: String,
    val model: String,
    val isAvailable: Boolean = true,
    val pricePerKm: Double,
    val currentLat: Double? = null,
    val currentLng: Double? = null,
    val lastUpdatedAt: String? = null,
    val type: String,
    val numberPlate: String,
    val locationName: String,
    val status: String,
    val imageUrl: String
)
