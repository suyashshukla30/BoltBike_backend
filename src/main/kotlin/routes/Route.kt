package com.bike.routes

import com.bike.domain.models.Bike
import com.bike.domain.models.User
import com.bike.infrastructure.database.dao.BikesDao
import com.bike.infrastructure.database.dao.UserDao
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json

fun Route.userRoutes() {
    post("/registerUser") {
        try {
            val raw = call.receiveText()
            println("RAW BODY: $raw")

            val user = Json.decodeFromString<User>(raw)
            UserDao.insertUser(user)
            call.respondText("✅ User ${user.uid} registered successfully!")
        } catch (e: Exception) {
            e.printStackTrace()
            call.respond(HttpStatusCode.BadRequest, "❌ Manual parse failed: ${e.message}")
        }
    }
}

fun Route.bikeRoutes() {
    post("/addBike") {
        try {
            val raw = call.receiveText()
            val bike = Json.decodeFromString<List<Bike>>(raw)
            bike.forEach { bike ->
                BikesDao.insertBikes(bike)
            }
            call.respondText("✅ Bike ${bike.size} added successfully!")
        } catch (e: Exception) {
            e.printStackTrace()
            call.respond(HttpStatusCode.BadRequest, "❌ Invalid request: ${e.message}")
        }
    }
    get("/getBikeList") {
        try {
            val bikes = BikesDao.getAllBikes()
            call.respond(bikes)
        } catch (e: Exception) {
            e.printStackTrace()
            call.respond(HttpStatusCode.InternalServerError, "❌ Failed to fetch bikes: ${e.message}")
        }
    }
}

