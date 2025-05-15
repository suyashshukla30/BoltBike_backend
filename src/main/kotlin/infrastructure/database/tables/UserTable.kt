package com.bike.infrastructure.database.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime

object UserTable: Table("user"){

    val uid = text("uid")
    val phoneNumber = text("phone_number")
    val name = text("name").nullable()
    val email = text("email").nullable()
    val hostel = text("hostel").nullable()
    val createdAt = text("created_at")
    val isAdmin = bool("is_admin").default(false)
    val lastLat = double("last_lat").nullable()
    val lastLng = double("last_lng").nullable()
    val locationTimestamp = text("location_timestamp").nullable()
    override val primaryKey = PrimaryKey(uid)

}