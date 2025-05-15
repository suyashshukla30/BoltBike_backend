package com.bike.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val uid: String,
    val phoneNumber: String,
    val name: String? = null,
    val email: String? = null,
    val hostel: String? = null,
    val createdAt: String = "",  
    val isAdmin: Boolean = false,
    val lastLat: Double? = null,
    val lastLng: Double? = null,
    val locationTimestamp: String? = null
)