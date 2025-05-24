package com.bike.plugins

import com.bike.infrastructure.database.tables.BikesTable
import com.bike.routes.bikeRoutes
import com.bike.routes.userRoutes
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureRouting() {
    routing {
        userRoutes()
        bikeRoutes()
        //------A simple health Check------//
         get("/") {
             call.respondText("OK")
         }
        //------XXX------//
    }
}
