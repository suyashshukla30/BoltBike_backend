package com.bike.infrastructure.database.dao

import com.bike.domain.models.User
import com.bike.infrastructure.database.tables.UserTable
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.insertIgnore
import org.jetbrains.exposed.sql.transactions.transaction

object UserDao {
    fun insertUser(user: User) {
        transaction {
            UserTable.insertIgnore {
                it[uid] = user.uid
                it[phoneNumber] = user.phoneNumber
                it[name] = user.name
                it[email] = user.email
                it[hostel] = user.hostel
                it[createdAt] = user.createdAt
                it[isAdmin] = user.isAdmin
                it[lastLat] = user.lastLat
                it[lastLng] = user.lastLng
                it[locationTimestamp] = user.locationTimestamp
            }
        }
    }
}
