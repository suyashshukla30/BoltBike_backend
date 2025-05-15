package com.bike.infrastructure.database.dao

import com.bike.domain.models.Bike
import com.bike.infrastructure.database.tables.BikesTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object BikesDao {
    fun insertBikes(bike: Bike) {
        transaction {
            BikesTable.insert {
                it[bikeId] = bike.bikeId
                it[model] = bike.model
                it[isAvailable] = bike.isAvailable
                it[pricePerKm] = bike.pricePerKm
                it[currentLat] = bike.currentLat
                it[currentLng] = bike.currentLng
                it[lastUpdatedAt] = bike.lastUpdatedAt
                it[type] = bike.type
                it[numberPlate] = bike.bikeId
                it[locationName] = bike.locationName
                it[status] = bike.status
                it[imageUrl] = bike.imageUrl
            }
        }
    }

    fun getAllBikes(): List<Bike> {
        return transaction {
            BikesTable.selectAll().map { rowToBike(it) }
        }
    }
    private fun rowToBike(row: ResultRow): Bike {
        return Bike(
            bikeId = row[BikesTable.bikeId],
            model = row[BikesTable.model],
            isAvailable = row[BikesTable.isAvailable],
            pricePerKm = row[BikesTable.pricePerKm],
            currentLat = row[BikesTable.currentLat],
            currentLng = row[BikesTable.currentLng],
            lastUpdatedAt = row[BikesTable.lastUpdatedAt],
            type = row[BikesTable.type],
            numberPlate = row[BikesTable.numberPlate],
            locationName = row[BikesTable.locationName],
            status = row[BikesTable.status],
            imageUrl = row[BikesTable.imageUrl]
        )
    }
}