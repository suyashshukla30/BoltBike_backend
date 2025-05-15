package com.bike.infrastructure.database.tables

import org.jetbrains.exposed.sql.Table

object BikesTable : Table("bikes") {
    val bikeId = text("bike_id")
    val model = text("model")
    val isAvailable = bool("is_available").default(true)
    val pricePerKm = double("price_per_km")
    val currentLat = double("current_lat").nullable()
    val currentLng = double("current_lng").nullable()
    val lastUpdatedAt = text("last_updated_at").nullable()
    val type = text("type")
    val numberPlate = text("number_plate")
    val locationName = text("location_name")
    val status = text("status")
    val imageUrl = text("image_url")

    override val primaryKey = PrimaryKey(bikeId)
}
