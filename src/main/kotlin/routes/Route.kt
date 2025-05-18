package com.bike.routes

import com.bike.domain.models.Bike
import com.bike.domain.models.User
import com.bike.infrastructure.database.dao.BikesDao
import com.bike.infrastructure.database.dao.UserDao
import com.google.firebase.auth.FirebaseAuth
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json

fun Route.userRoutes() {
    post("/updateUser") {
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
    post("/registerNewUser") {
        val authHeader = call.request.header("Authorization")
        val idToken = authHeader?.removePrefix("Bearer ")

        if (idToken.isNullOrBlank()) {
            call.respond(HttpStatusCode.Unauthorized, "Missing Firebase token")
            return@post
        }

        val decodedToken = try {
            FirebaseAuth.getInstance().verifyIdToken(idToken)
        } catch (e: Exception) {
            call.respond(HttpStatusCode.Unauthorized, "Invalid Firebase token: ${e.message}")
            return@post
        }

        val uid = decodedToken.uid
        val request = call.receive<User>()

        val user = User(
            uid = uid,
            phoneNumber = request.phoneNumber,
            name = request.name,
            email = request.email,
            hostel = request.hostel,
            createdAt = java.time.Instant.now().toString(),
            isAdmin = false,
            lastLat = request.lastLat,
            lastLng = request.lastLng,
            locationTimestamp = request.locationTimestamp
        )

        try {
            UserDao.insertUser(user)
            call.respond(HttpStatusCode.OK, "✅ User $uid registered successfully!")
        } catch (e: Exception) {
            e.printStackTrace()
            call.respond(HttpStatusCode.InternalServerError, "❌ Failed to save user: ${e.message}")
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

